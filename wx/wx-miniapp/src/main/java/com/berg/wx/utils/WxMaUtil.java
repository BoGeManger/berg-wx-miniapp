package com.berg.wx.utils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.berg.wx.exception.WxMiniappException;
import com.berg.wx.service.WxMaExtendService;

import java.util.HashMap;
import java.util.Map;

public class WxMaUtil {

    public static Map<String, WxMaService> services = new HashMap<>();
    public static Map<String, WxMaMessageRouter> routers = new HashMap<>();
    public static Map<String, WxMaExtendService> extendService =  new HashMap<>();

    public static WxMaService getService(String appid) throws WxMiniappException{
        WxMaService wxService = services.get(appid);
        if (wxService == null) {
            throw new WxMiniappException("获取微信WxMaService失败,未找到对应的小程序appid:"+appid);
        }
        return wxService;
    }

    public static WxMaMessageRouter getRouter(String appid) throws WxMiniappException{
        WxMaMessageRouter route = routers.get(appid);
        if (route == null) {
            throw new WxMiniappException("获取微信WxMaMessageRouter失败,未找到对应的小程序appid:"+appid);
        }
        return route;
    }

    public static WxMaExtendService getExtendService(String appid)throws Exception{
        WxMaExtendService wxMaExtendService = extendService.get(appid);
        if (wxMaExtendService == null) {
            throw new WxMiniappException("获取微信WxMaExtendService失败,未找到对应的小程序appid:"+appid);
        }
        return wxMaExtendService;
    }
}
