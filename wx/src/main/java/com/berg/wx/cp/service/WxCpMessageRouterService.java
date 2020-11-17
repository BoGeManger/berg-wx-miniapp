package com.berg.wx.cp.service;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;

public interface WxCpMessageRouterService {

    WxCpMessageRouter createRouter(WxCpService service);
}
