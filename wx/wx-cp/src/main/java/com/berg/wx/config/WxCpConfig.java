package com.berg.wx.config;

import com.berg.wx.exception.WxCpException;
import com.berg.wx.properties.WxCpProperties;
import com.berg.wx.service.WxCpMessageRouterService;
import com.berg.wx.utils.WxCpUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.config.impl.WxCpRedissonConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "wx.cp", name ="configs[0].appId")
@Configuration
@EnableConfigurationProperties(WxCpProperties.class)
public class WxCpConfig {

    @Autowired
    WxCpProperties properties;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired(required = false)
    WxCpMessageRouterService wxCpMessageRouterService;

    @PostConstruct
    public void init() throws WxCpException{
        List<WxCpProperties.Config> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new WxCpException("微信小程序配置为空");
        }
        WxCpUtil.services = configs.stream()
                .map(a -> {
                    WxCpDefaultConfigImpl config = wxCpRedisTemplateConfigStorage();
                    config.setCorpId(a.getCorpId());
                    config.setAgentId(a.getAgentId());
                    config.setCorpSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
                    WxCpService service = new WxCpServiceImpl();
                    service.setWxCpConfigStorage(config);
                    if(wxCpMessageRouterService!=null){
                        WxCpUtil.routers.put(a.getCorpId(),wxCpMessageRouterService.createRouter(service));
                    }
                    return service;
                }).collect(Collectors.toMap(s -> s.getWxCpConfigStorage().getCorpId(), a -> a));
    }

    WxCpDefaultConfigImpl wxCpRedisTemplateConfigStorage() {
        WxRedisOps redisOps = new RedisTemplateWxRedisOps(stringRedisTemplate);
        return new WxCpRedissonConfigImpl(redisOps,properties.getKeyPrefix());
    }

}
