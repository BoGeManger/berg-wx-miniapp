package com.berg.wx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {

    /**
     * 配置列表
     */
    List<Config> configs;

    @Data
    public static class Config {
        /**
         * 设置微信公众号或者小程序等的appid
         */
        String appId;
        /**
         * 微信支付商户号
         */
        String mchId;
        /**
         * 微信支付商户密钥
         */
        String mchKey;
        /**
         * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
         */
        String subAppId;
        /**
         * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
         */
        String subMchId;
        /**
         * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
         */
        String keyPath;
        /**
         * 指定是否使用沙箱环境
         */
        Boolean useSandboxEnv=false;
    }

}