package com.berg.wx.service;

import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;

public interface WxMpMessageRouterService {

    WxMpMessageRouter createRouter(WxMpService service);
}
