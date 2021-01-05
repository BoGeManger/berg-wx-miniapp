package com.berg.wx.config;

import com.berg.wx.exception.WxPayException;
import com.berg.wx.properties.WxPayProperties;
import com.berg.wx.utils.WxPayUtil;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "wx.pay", name ="configs[0].appId")
@Configuration
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfig {

    @Autowired
    WxPayProperties properties;

    @PostConstruct
    public void init() throws WxPayException{
        List<WxPayProperties.Config> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new WxPayException("微信支付配置为空");
        }
        WxPayUtil.services = configs.stream()
                .map(a -> {
                    com.github.binarywang.wxpay.config.WxPayConfig payConfig = new com.github.binarywang.wxpay.config.WxPayConfig();
                    payConfig.setAppId(a.getAppId());
                    payConfig.setMchId(a.getMchId());
                    payConfig.setMchKey(a.getMchKey());
                    payConfig.setSubAppId(a.getSubAppId());
                    payConfig.setSubMchId(a.getSubMchId());
                    payConfig.setKeyPath(a.getKeyPath());
                    // 可以指定是否使用沙箱环境
                    payConfig.setUseSandboxEnv(a.getUseSandboxEnv());
                    WxPayService service = new WxPayServiceImpl();
                    service.setConfig(payConfig);
                    return service;
                }).collect(Collectors.toMap(s -> s.getConfig().getAppId(), a -> a));
    }

}
