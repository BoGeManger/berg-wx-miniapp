package com.berg.wx.miniapp.utils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.berg.wx.miniapp.service.WxMaExtendService;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.HashMap;
import java.util.Map;

public class WxMaUtil {

    public static Map<String, WxMaService> services = new HashMap<>();
    public static Map<String, WxMaMessageRouter> routers = new HashMap<>();
    public static Map<String, WxMaExtendService> extendService =  new HashMap<>();

    public static WxMaService getService(String appid) throws Exception{
        WxMaService wxService = services.get(appid);
        if (wxService == null) {
            throw new WxErrorException("获取微信WxMaService失败,未找到对应的小程序appid:"+appid);
        }
        return wxService;
    }

    public static WxMaMessageRouter getRouter(String appid) throws Exception{
        WxMaMessageRouter route = routers.get(appid);
        if (route == null) {
            throw new WxErrorException("获取微信WxMaMessageRouter失败,未找到对应的小程序appid:"+appid);
        }
        return route;
    }

    public static WxMaExtendService getExtendService(String appid)throws Exception{
        WxMaExtendService wxMaExtendService = extendService.get(appid);
        if (wxMaExtendService == null) {
            throw new WxErrorException("获取微信WxMaExtendService失败,未找到对应的小程序appid:"+appid);
        }
        return wxMaExtendService;
    }
}
