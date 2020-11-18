package com.berg.miniapp.handler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QrcodeHandler implements WxMaMessageHandler {

    @Override
    public WxMaXmlOutMessage handle(WxMaMessage wxMaMessage, Map<String, Object> map, WxMaService wxMaService, WxSessionManager wxSessionManager) throws WxErrorException {
//        final File file = wxMaService.getQrcodeService().createQrcode("123", 430);
//        WxMediaUploadResult uploadResult = wxMaService.getMediaService().uploadMedia("image", file);
//        wxMaService.getMsgService().sendKefuMsg(
//                WxMaKefuMessage
//                        .newImageBuilder()
//                        .mediaId(uploadResult.getMediaId())
//                        .toUser(wxMaMessage.getFromUser())
//                        .build());
        return null;
    }
}
