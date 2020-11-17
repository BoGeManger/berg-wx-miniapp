package com.berg.wx.cp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "wx.cp")
public class WxCpProperties {

    /**
     * 配置列表
     */
    List<Config> configs;

    /**
     * 缓存前缀
     */
    String keyPrefix="wx_cp_";

    @Data
    public class Config{
        /**
         * 设置企业微信应用的AgentId
         */
        Integer agentId;
        /**
         * 设置企业微信的corpId
         */
        String corpId;
        /**
         * 设置企业微信应用的Secret
         */
        String secret;
        /**
         * 设置企业微信应用的token
         */
        String token;
        /**
         * 设置企业微信应用的EncodingAESKey
         */
        String aesKey;
    }
  
}
