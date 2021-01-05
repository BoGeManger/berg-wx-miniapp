package com.berg.wx.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;

/**
 * 微信消息路由(引用模块实现)
 */
public interface WxMaMessageRouterService {

    WxMaMessageRouter createRouter(WxMaService service);
}
