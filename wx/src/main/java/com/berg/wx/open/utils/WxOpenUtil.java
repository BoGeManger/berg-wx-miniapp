package com.berg.wx.open.utils;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;

import java.util.HashMap;
import java.util.Map;

public class WxOpenUtil {

    public static Map<String,WxOpenService> services = new HashMap<>();
    public static Map<String, WxOpenMessageRouter> routers = new HashMap<>();

    public static WxOpenService getService(String appid) throws Exception{
        WxOpenService wxService = services.get(appid);
        if (wxService == null) {
            throw new WxErrorException("获取微信WxOpenService失败,未找到对应的开放平台appid:"+appid);
        }
        return wxService;
    }

    public static WxOpenMessageRouter getRouter(String appid) throws Exception{
        WxOpenMessageRouter route = routers.get(appid);
        if (route == null) {
            throw new WxErrorException("获取微信WxOpenMessageRouter失败,未找到对应的开放平台appid:"+appid);
        }
        return route;
    }
}
