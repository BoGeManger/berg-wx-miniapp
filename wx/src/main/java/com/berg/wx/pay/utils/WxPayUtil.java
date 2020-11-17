package com.berg.wx.pay.utils;

import com.github.binarywang.wxpay.service.WxPayService;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.HashMap;
import java.util.Map;

public class WxPayUtil {

    public static Map<String,WxPayService> services = new HashMap<>();

    public static WxPayService getService(String appid) throws Exception{
        WxPayService wxService = services.get(appid);
        if (wxService == null) {
            throw new WxErrorException("获取微信支付WxPayService失败,未找到对应的微信公众号或者小程序appid:"+appid);
        }
        return wxService;
    }
}
