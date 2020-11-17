package com.berg.system.controller;

import com.berg.base.BaseController;
import com.berg.message.Result;
import com.berg.system.service.miniapp.SubscribeMessageService;
import com.berg.vo.miniapp.in.MaDelTemplateCacheInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscribeMessage")
@Api(tags = "微信小程序订阅模板消息")
public class SubscribeMessageController extends BaseController {

    @Autowired
    SubscribeMessageService subscribeMessageService;

    @ApiOperation("删除模板缓存")
    @DeleteMapping(value = "delTemplateCache")
    public Result<Boolean> delTemplateCache(@RequestBody MaDelTemplateCacheInVo input){
        subscribeMessageService.delTemplateCache(input.getAppId());
        return getSuccessResult("请求成功",true);
    }
}
