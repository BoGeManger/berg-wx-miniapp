package com.berg.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class RedisDistributedLock {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final String UNLOCK_LUA;

    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /**
     * 获取锁(独占锁，不失败重试)
     * @param key
     * @param expire 锁自动释放时间，毫秒
     * @return boolean
     */
    public boolean lockByAQS(String key, long expire) {
        String uuid = UUID.randomUUID().toString();
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                lockFlag.set(uuid);
                return commands.set(key, uuid, "NX", "PX", expire);
            };
            String result = redisTemplate.execute(callback);

            return !StringUtils.isEmpty(result);
        } catch (Exception e) {
            log.error("set redis occured an exception", e);
        }
        return false;
    }

    /**
     * 获取锁(自旋乐观锁，失败重试)
     * @param key 锁
     * @param expire 锁自动释放时间，毫秒
     * @return
     */
    public boolean lock(String key,long expire) {
        boolean result = lock(key,expire,10,500);
        return result;
    }

    /**
     * 获取锁(自旋乐观锁，失败重试)
     *
     * @param key          锁
     * @param expire 锁有效期
     * @param retry   失败尝试次数
     * @param sleep  失败间隔
     * @return boolean
     */
    public boolean lock(String key, long expire, int retry, long sleep) {
        boolean result = setRedis(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retry-- > 0) {
            try {
                log.debug("lock failed, retrying..." + retry);
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                return false;
            }
            result = setRedis(key, expire);
        }
        return result;
    }

    private boolean setRedis(String key, long expire) {
        try {
            String result = redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                    String uuid = UUID.randomUUID().toString();
                    lockFlag.set(uuid);
                    return commands.set(key, uuid, "NX", "PX", expire);
                }
            });
            return !StringUtils.isEmpty(result);
        } catch (Exception e) {
            log.error("set redis occured an exception", e);
        }
        return false;
    }

    public String get(String key) {
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.get(key);
            };
            String result = redisTemplate.execute(callback);
            return result;
        } catch (Exception e) {
            log.error("get redis occured an exception", e);
        }
        return "";
    }

    /**
     * 释放锁
     * @param key
     * @return
     */
    public boolean unLock(String key) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<String>();
            keys.add(key);
            List<String> args = new ArrayList<String>();
            args.add(lockFlag.get());
            lockFlag.remove();
            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            Long result = redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    Object nativeConnection = connection.getNativeConnection();
                    // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                    // 集群模式
                    if (nativeConnection instanceof JedisCluster) {
                        log.info("lock集群模式");
                        return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                    }

                    // 单机模式
                    else if (nativeConnection instanceof Jedis) {
                        log.info("单机集群模式");
                        return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                    }
                    return 0L;
                }
            });

            return result != null && result > 0;
        } catch (Exception e) {
            log.error("release lock occured an exception", e);
        }
        return false;
    }


}
