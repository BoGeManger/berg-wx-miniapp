package com.berg.wx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaProperties {

    /**
     * 配置列表
     */
    List<Config> configs;

    /**
     * 缓存前缀
     */
    String keyPrefix="wx_ma_";

    @Data
    public static class Config {
        /**
         * 设置微信小程序的appid
         */
        String appId;
        /**
         * 设置微信小程序的Secret
         */
        String secret;
        /**
         * 设置微信小程序消息服务器配置的token
         */
        String token;
        /**
         * 设置微信小程序消息服务器配置的EncodingAESKey
         */
        String aesKey;
        /**
         * 消息格式，XML或者JSON
         */
        String msgDataFormat;
        /**
         * 设置微信小程序名称
         */
        String name;
    }

}
