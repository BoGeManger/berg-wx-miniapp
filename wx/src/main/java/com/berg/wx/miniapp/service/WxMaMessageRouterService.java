package com.berg.wx.miniapp.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;

public interface WxMaMessageRouterService {

    WxMaMessageRouter createRouter(WxMaService service);
}
