package com.berg.miniapp.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.berg.miniapp.service.handler.*;
import com.berg.wx.miniapp.service.WxMaMessageRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信小程序客服接入示例
 */
@Service
public class WxMaMessageRouterServiceImpl implements WxMaMessageRouterService {

    @Autowired
    LogHandler logHandler;
    @Autowired
    SubscribeMsgHandler subscribeMsgHandler;
    @Autowired
    TextHandler textHandler;
    @Autowired
    PicHandler picHandler;
    @Autowired
    QrcodeHandler qrcodeHandler;

    @Override
    public WxMaMessageRouter createRouter(WxMaService service) {
        WxMaMessageRouter router = new WxMaMessageRouter(service);
        router
                .rule().handler(logHandler).next()
                .rule().async(false).content("订阅消息").handler(subscribeMsgHandler).end()
                .rule().async(false).content("文本").handler(textHandler).end()
                .rule().async(false).content("图片").handler(picHandler).end()
                .rule().async(false).content("二维码").handler(qrcodeHandler).end();
        return router;
    }
}
