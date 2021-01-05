package com.berg.miniapp.controller;

import com.berg.common.controller.AbstractController;
import com.berg.common.constant.Result;
import com.berg.miniapp.service.miniapp.PortalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portal/{appId}")
@Api(tags = "微信小程序消息")
public class PortalController extends AbstractController {

    @Autowired
    PortalService portalService;

    @GetMapping(value = "get",produces = "text/plain;charset=utf-8")
    public Result<Boolean> get(@PathVariable String appId,
                                   @RequestParam(name = "signature", required = false) String signature,
                                   @RequestParam(name = "timestamp", required = false) String timestamp,
                                   @RequestParam(name = "nonce", required = false) String nonce,
                                   @RequestParam(name = "echostr", required = false) String echostr){
        portalService.get(appId,signature,timestamp,nonce,echostr);
        return getSuccessResult("请求成功",true);
    }

    @PostMapping(value = "post",produces = "application/xml; charset=UTF-8")
    public Result<Boolean> post(@PathVariable String appId,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                       @RequestParam(name = "encrypt_type", required = false) String encryptType,
                       @RequestParam(name = "signature", required = false) String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestBody String requestBody) {
        portalService.poet(appId,msgSignature,encryptType,signature,timestamp,nonce,requestBody);
        return getSuccessResult("请求成功",true);
    }
}
