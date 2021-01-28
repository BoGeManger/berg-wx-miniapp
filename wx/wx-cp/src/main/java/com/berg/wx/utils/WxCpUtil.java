package com.berg.wx.utils;

import com.berg.wx.exception.WxCpException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;

import java.util.HashMap;
import java.util.Map;

public class WxCpUtil {

    public static Map<String, WxCpService> services = new HashMap<>();
    public static Map<String, WxCpMessageRouter> routers = new HashMap<>();

    public static WxCpService getService(String corpId) throws WxCpException {
        WxCpService wxService = services.get(corpId);
        if (wxService == null) {
            throw new WxCpException("获取微信WxCpService失败,未找到对应的企业微信应用corpId:"+corpId);
        }
        return wxService;
    }

    public static WxCpMessageRouter getRouter(String corpId) throws WxCpException{
        WxCpMessageRouter route = routers.get(corpId);
        if (route == null) {
            throw new WxCpException("获取微信WxCpMessageRouter失败,未找到对应的企业微信应用corpId:"+corpId);
        }
        return route;
    }
}
