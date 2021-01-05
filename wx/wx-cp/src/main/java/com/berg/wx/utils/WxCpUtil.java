package com.berg.wx.utils;

import com.berg.wx.exception.WxCpException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;

import java.util.HashMap;
import java.util.Map;

public class WxCpUtil {

    public static Map<Integer, WxCpService> services = new HashMap<>();
    public static Map<Integer, WxCpMessageRouter> routers = new HashMap<>();

    public static WxCpService getService(Integer agentid) throws WxCpException {
        WxCpService wxService = services.get(agentid);
        if (wxService == null) {
            throw new WxCpException("获取微信WxCpService失败,未找到对应的企业微信应用agentid:"+agentid);
        }
        return wxService;
    }

    public static WxCpMessageRouter getRouter(Integer agentid) throws WxCpException{
        WxCpMessageRouter route = routers.get(agentid);
        if (route == null) {
            throw new WxCpException("获取微信WxCpMessageRouter失败,未找到对应的企业微信应用agentid:"+agentid);
        }
        return route;
    }
}
