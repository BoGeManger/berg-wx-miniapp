package com.berg.wx.config;

import com.berg.wx.exception.WxOpenException;
import com.berg.wx.properties.WxOpenProperties;
import com.berg.wx.service.WxOpenMessageRouterService;
import com.berg.wx.utils.WxOpenUtil;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "wx.open", name ="configs[0].appId")
@Configuration
@EnableConfigurationProperties(WxOpenProperties.class)
public class WxOpenConfig {

    @Autowired
    WxOpenProperties properties;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired(required = false)
    WxOpenMessageRouterService wxOpenMessageRouterService;

    @PostConstruct
    public void init() throws WxOpenException{
        List<WxOpenProperties.Config> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new WxOpenException( "微信开放平台配置为空");
        }
        WxOpenUtil.services = configs.stream()
                .map(a -> {
                    WxOpenConfigStorage config = getWxOpenInRedisTemplateConfigStorage();
                    config.setWxOpenInfo(a.getAppId(), a.getSecret(), a.getToken(), a.getAesKey());
                    WxOpenService service = new WxOpenServiceImpl();
                    service.setWxOpenConfigStorage(config);
                    if(wxOpenMessageRouterService!=null){
                        WxOpenUtil.routers.put(a.getAppId(),wxOpenMessageRouterService.createRouter(service));
                    }
                    return service;
                }).collect(Collectors.toMap(s -> s.getWxOpenConfigStorage().getComponentAppId(), a -> a));
    }

    WxOpenInRedisConfigStorage getWxOpenInRedisTemplateConfigStorage() {
        WxRedisOps redisOps = new RedisTemplateWxRedisOps(stringRedisTemplate);
        WxOpenInRedisConfigStorage config = new WxOpenInRedisConfigStorage(redisOps,properties.getKeyPrefix());
        return config;
    }

}
