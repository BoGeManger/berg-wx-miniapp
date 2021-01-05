package com.berg.wx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "wx.open")
public class WxOpenProperties {

    /**
     * 配置列表
     */
    List<Config> configs;

    /**
     * 缓存前缀
     */
    String keyPrefix="wx_open_";

    @Data
    public static class Config {
        /**
         * 设置微信开放平台的appid.
         */
        String appId;
        /**
         * 设置微信开放平台的app secret.
         */
        String secret;
        /**
         * 设置微信开放平台的token.
         */
        String token;
        /**
         * 设置微信开放平台的EncodingAESKey.
         */
        String aesKey;
    }

}
