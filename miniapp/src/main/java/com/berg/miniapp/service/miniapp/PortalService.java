package com.berg.miniapp.service.miniapp;

public interface PortalService {

    void get(String appId,String signature,String timestamp,String nonce,String echostr);

    void poet(String appId,String msgSignature,String encryptType,String signature,String timestamp,String nonce,String requestBody);
}
