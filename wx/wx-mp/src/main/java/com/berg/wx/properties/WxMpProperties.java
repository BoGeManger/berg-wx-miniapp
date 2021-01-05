package com.berg.wx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {

    /**
     * 配置列表
     */
    List<Config> configs;

    /**
     * 缓存前缀
     */
    String keyPrefix = "wx_mp_";

    @Data
    public static class Config {
        /**
         * 设置微信公众号的appid.
         */
        String appId;
        /**
         * 设置微信公众号的app secret.
         */
        String secret;
        /**
         * 设置微信公众号的token.
         */
        String token;
        /**
         * 设置微信公众号的EncodingAESKey.
         */
        String aesKey;
        /**
         * 设置微信公众号名称
         */
        String name;
    }

}
