package com.berg.miniapp.service.miniapp.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.berg.auth.miniapp.auth.JWTToken;
import com.berg.common.constant.RedisKeyConstants;
import com.berg.dao.system.mb.entity.MaBindTbl;
import com.berg.dao.system.mb.service.MaBindTblDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAsyncTask {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MaBindTblDao maBindTblDao;

    /**
     * 新增绑定信息
     *
     * @param appId
     * @param openId
     * @param unionId
     */
    @Async
    public void addMaBind(String appId, String openId, String unionId) {
        LocalDateTime now = LocalDateTime.now();
        MaBindTbl maBindTbl = new MaBindTbl();
        maBindTbl.setAppId(appId);
        maBindTbl.setUnionId(unionId);
        maBindTbl.setOpenId(openId);
        maBindTbl.setCreateTime(now);
        maBindTbl.setModifyTime(now);
        maBindTblDao.save(maBindTbl);
    }

    /**
     * 更新绑定变更的unionId
     *
     * @param appId
     * @param openId
     * @param unionId
     */
    @Async
    public void updateMaBindUnionId(String appId, String openId, String unionId) {
        LambdaQueryWrapper query = new LambdaQueryWrapper<MaBindTbl>()
                .eq(MaBindTbl::getAppId, appId)
                .eq(MaBindTbl::getOpenId, openId)
                .ne(MaBindTbl::getUnionId, "")
                .ne(MaBindTbl::getUnionId, unionId)
                .isNotNull(MaBindTbl::getUnionId);
        MaBindTbl maBindTbl = new MaBindTbl();
        maBindTbl.setUnionId(unionId);
        maBindTbl.setModifyTime(LocalDateTime.now());
        maBindTblDao.update(maBindTbl, query);
    }

    /**
     * 设置授权校验缓存
     *
     * @param jwtToken
     */
    @Async
    public void setTokenCache(JWTToken jwtToken) {
        String key = String.format(RedisKeyConstants.Ma.MA_TOKEN,jwtToken.getAppId().toLowerCase(), jwtToken.getOpenId().toLowerCase());
        stringRedisTemplate.opsForValue().set(key, jwtToken.getToken(),jwtToken.getExipreAt(), TimeUnit.DAYS);
    }

    /**
     * 删除授权校验缓存
     *
     * @param openId
     */
    @Async
    public void delTokenCache(String openId) {
        if(StringUtils.isNotBlank(openId)) {
            String key = String.format(RedisKeyConstants.Ma.MA_TOKEN, openId);
            stringRedisTemplate.delete(key);
        }
    }
}
