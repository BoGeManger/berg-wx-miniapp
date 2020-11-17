package com.berg.miniapp.service.handler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PicHandler implements WxMaMessageHandler {

    @Override
    public WxMaXmlOutMessage handle(WxMaMessage wxMaMessage, Map<String, Object> map, WxMaService wxMaService, WxSessionManager wxSessionManager) throws WxErrorException {
//        WxMediaUploadResult uploadResult = wxMaService.getMediaService()
//                .uploadMedia("image", "png",
//                        ClassLoader.getSystemResourceAsStream("tmp.png"));
//        wxMaService.getMsgService().sendKefuMsg(
//                WxMaKefuMessage
//                        .newImageBuilder()
//                        .mediaId(uploadResult.getMediaId())
//                        .toUser(wxMaMessage.getFromUser())
//                        .build());
        return null;
    }
}
