package com.berg.miniapp.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.berg.common.exception.FailException;
import com.berg.common.exception.ParamException;
import com.berg.miniapp.service.miniapp.PortalService;
import com.berg.wx.miniapp.utils.WxMaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PortalServiceImpl implements PortalService {

    @Override
    public void get(String appId,String signature,String timestamp,String nonce,String echostr){
//        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
//            throw new ParamException("请求参数非法，请核实!");
//        }
//        try{
//            WxMaService wxMaService = WxMaUtil.getService(appId);
//            if (wxMaService.checkSignature(timestamp, nonce, signature)) {
//                //处理消息
//            }
//        }catch (Exception ex){
//            throw new FailException("接收微信服务器认证消息失败:"+ex.getMessage());
//        }
    }

    @Override
    public void poet(String appId,String msgSignature,String encryptType,String signature,String timestamp,String nonce,String requestBody){
//        try{
//            WxMaService wxService = WxMaUtil.getService(appId);
//            boolean isJson = Objects.equals(wxService.getWxMaConfig().getMsgDataFormat(),
//                    WxMaConstants.MsgDataFormat.JSON);
//            WxMaMessageRouter router = WxMaUtil.getRouter(appId);
//            if (StringUtils.isBlank(encryptType)) {
//                // 明文传输的消息
//                WxMaMessage inMessage;
//                if (isJson) {
//                    inMessage = WxMaMessage.fromJson(requestBody);
//                } else {//xml
//                    inMessage = WxMaMessage.fromXml(requestBody);
//                }
//                router.route(inMessage);
//            }else if("aes".equals(encryptType)) {
//                // 是aes加密的消息
//                WxMaMessage inMessage;
//                if (isJson) {
//                    inMessage = WxMaMessage.fromEncryptedJson(requestBody, wxService.getWxMaConfig());
//                } else {//xml
//                    inMessage = WxMaMessage.fromEncryptedXml(requestBody, wxService.getWxMaConfig(),
//                            timestamp, nonce, msgSignature);
//                }
//                router.route(inMessage);
//            }else{
//                throw new FailException("不可识别的加密类型：" + encryptType);
//            }
//        }catch (Exception ex){
//            throw new FailException("接收微信请求失败:"+ex.getMessage());
//        }
    }
}
