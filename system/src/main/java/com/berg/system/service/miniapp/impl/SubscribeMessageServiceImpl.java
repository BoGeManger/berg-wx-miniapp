package com.berg.system.service.miniapp.impl;

import com.berg.constant.RedisKeyConstants;
import com.berg.system.service.miniapp.SubscribeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubscribeMessageServiceImpl implements SubscribeMessageService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 删除模板缓存
     * @param appId
     */
    @Override
    public void delTemplateCache(String appId){
        String key = String.format(RedisKeyConstants.Ma.MA_TEMPLATE_LIST,appId);
        stringRedisTemplate.delete(key);
    }
}
