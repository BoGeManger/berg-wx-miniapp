package com.berg.wx.config;

import com.berg.wx.exception.WxMpException;
import com.berg.wx.properties.WxMpProperties;
import com.berg.wx.service.WxMpMessageRouterService;
import com.berg.wx.utils.WxMpUtil;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "wx.mp", name ="configs[0].appId")
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfig {

    @Autowired
    WxMpProperties properties;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired(required = false)
    WxMpMessageRouterService wxMpMessageRouterService;

    @PostConstruct
    public void init() throws WxMpException {
        List<WxMpProperties.Config> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new WxMpException("微信公众号配置为空");
        }
        WxMpUtil.services = configs.stream()
                .map(a -> {
                    WxMpDefaultConfigImpl config = wxMpRedisTemplateConfigStorage();
                    config.setAppId(a.getAppId());
                    config.setSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
                    WxMpService service = new WxMpServiceImpl();
                    service.setWxMpConfigStorage(config);
                    if(wxMpMessageRouterService!=null){
                        WxMpUtil.routers.put(a.getAppId(),wxMpMessageRouterService.createRouter(service));
                    }
                    return service;
                }).collect(Collectors.toMap(s -> s.getWxMpConfigStorage().getAppId(), a -> a));
    }

    WxMpDefaultConfigImpl wxMpRedisTemplateConfigStorage() {
        WxRedisOps redisOps = new RedisTemplateWxRedisOps(stringRedisTemplate);
        return new WxMpRedisConfigImpl(redisOps, properties.getKeyPrefix());
    }

}
