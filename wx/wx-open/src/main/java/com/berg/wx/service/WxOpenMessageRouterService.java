package com.berg.wx.service;

import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;

public interface WxOpenMessageRouterService {

    WxOpenMessageRouter createRouter(WxOpenService service);
}
