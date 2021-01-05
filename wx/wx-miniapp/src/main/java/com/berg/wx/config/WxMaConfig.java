package com.berg.wx.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisBetterConfigImpl;
import com.berg.wx.exception.WxMiniappException;
import com.berg.wx.properties.WxMaProperties;
import com.berg.wx.service.WxMaExtendService;
import com.berg.wx.service.WxMaMessageRouterService;
import com.berg.wx.service.impl.WxMaExtendServiceImpl;
import com.berg.wx.utils.WxMaUtil;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "wx.miniapp", name ="configs[0].appId")
@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfig {

    @Autowired
    WxMaProperties properties;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired(required = false)
    WxMaMessageRouterService wxMaMessageRouterService;

    @PostConstruct
    public void init() throws WxMiniappException{
        List<WxMaProperties.Config> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new WxMiniappException("微信小程序配置为空");
        }
        WxMaUtil.services = configs.stream()
                .map(a -> {
                    WxMaDefaultConfigImpl config = wxMaRedisTemplateConfigStorage();
                    config.setAppid(a.getAppId());
                    config.setSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
                    config.setMsgDataFormat(a.getMsgDataFormat());
                    WxMaService service = new WxMaServiceImpl();
                    service.setWxMaConfig(config);
                    if(wxMaMessageRouterService!=null){
                        WxMaUtil.routers.put(a.getAppId(),wxMaMessageRouterService.createRouter(service));
                    }
                    //自定义扩展实现
                    WxMaExtendService wxMaExtendService = new WxMaExtendServiceImpl(service);
                    WxMaUtil.extendService.put(a.getAppId(),wxMaExtendService);
                    return service;
                }).collect(Collectors.toMap(s -> s.getWxMaConfig().getAppid(), a -> a));
    }

    WxMaDefaultConfigImpl wxMaRedisTemplateConfigStorage() {
        WxRedisOps redisOps = new RedisTemplateWxRedisOps(stringRedisTemplate);
        return new WxMaRedisBetterConfigImpl(redisOps,properties.getKeyPrefix());
    }

}
