package com.berg.miniapp.service.handler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import com.google.common.collect.Lists;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SubscribeMsgHandler implements WxMaMessageHandler {

    @Override
    public WxMaXmlOutMessage handle(WxMaMessage wxMaMessage, Map<String, Object> map, WxMaService wxMaService, WxSessionManager wxSessionManager) throws WxErrorException {
//        wxMaService.getMsgService().sendSubscribeMsg(WxMaSubscribeMessage.builder()
//                .templateId("此处更换为自己的模板id")
//                .data(Lists.newArrayList(
//                        new WxMaSubscribeMessage.Data("keyword1", "339208499")))
//                .toUser(wxMaMessage.getFromUser())
//                .build());
        return null;
    }
}
