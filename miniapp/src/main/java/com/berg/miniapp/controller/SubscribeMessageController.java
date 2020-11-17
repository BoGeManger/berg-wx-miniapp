package com.berg.miniapp.controller;

import com.berg.base.BaseController;
import com.berg.message.Result;
import com.berg.miniapp.service.miniapp.SubscribeMessageService;
import com.berg.vo.miniapp.MaTemplateInfoVo;
import com.berg.vo.miniapp.in.MaSendTemplateInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribeMessage")
@Api(tags = "微信小程序订阅模板消息")
public class SubscribeMessageController extends BaseController {

    @Autowired
    SubscribeMessageService subscribeMessageService;

    @ApiOperation(value = "获取当前帐号下的个人模板列表")
    @GetMapping(value = "getTemplateList")
    public Result<List<MaTemplateInfoVo>> getTemplateList(){
        return getSuccessResult("请求成功",subscribeMessageService.getTemplateList());
    }

    @ApiOperation(value = "发送订阅消息")
    @PostMapping(value = "send")
    public Result<Boolean> send(@RequestBody @Validated MaSendTemplateInVo input){
        subscribeMessageService.send(input);
        return getSuccessResult("请求成功",true);
    }
}
