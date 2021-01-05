package com.berg.wx.utils;

import com.berg.wx.exception.WxMpException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;

import java.util.HashMap;
import java.util.Map;

public class WxMpUtil {

    public static Map<String, WxMpService> services = new HashMap<>();
    public static Map<String, WxMpMessageRouter> routers = new HashMap<>();

    public static WxMpService getService(String appid) throws WxMpException{
        WxMpService wxService = services.get(appid);
        if (wxService == null) {
            throw new WxMpException("获取微信WxMpService失败,未找到对应的公众号appid:"+appid);
        }
        return wxService;
    }

    public static WxMpMessageRouter getRouter(String appid) throws WxMpException{
        WxMpMessageRouter route = routers.get(appid);
        if (route == null) {
            throw new WxMpException("获取微信WxMpMessageRouter失败,未找到对应的公众号appid:"+appid);
        }
        return route;
    }
}
