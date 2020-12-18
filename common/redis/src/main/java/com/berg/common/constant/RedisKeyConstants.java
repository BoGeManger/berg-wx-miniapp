package com.berg.common.constant;

public class RedisKeyConstants {

    public static final class System {
        //授权TOKEN(token)
        public static final String SYSTEM_TOKEN = "system_token_%s";
        //用户角色信息(username)
        public static final String USER_ROLE = "user_role_%s";
        //用户组件权限信息(username)
        public static final String USER_PERMS = "user_perms_%s";
    }

    public static final class Ma{
        //微信小程序授权校验(appId,openId)
        public static final String MA_TOKEN = "ma_token_%s_%s";
        //微信订阅消息列表(apId)
        public static final String MA_TEMPLATE_LIST = "ma_template__list_%s";
    }

}