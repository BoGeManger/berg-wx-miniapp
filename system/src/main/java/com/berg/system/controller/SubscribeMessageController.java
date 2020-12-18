package com.berg.system.controller;

import com.berg.common.base.BaseController;
import com.berg.dao.page.PageInfo;
import com.berg.common.constant.Result;
import com.berg.system.service.miniapp.SubscribeMessageService;
import com.berg.vo.miniapp.MsgRecordVo;
import com.berg.vo.miniapp.in.GetMsgRecordPageInVo;
import com.berg.vo.miniapp.in.MaDelTemplateCacheInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribeMessage")
@Api(tags = "微信小程序订阅模板消息")
public class SubscribeMessageController extends BaseController {

    @Autowired
    SubscribeMessageService subscribeMessageService;

    @ApiOperation("获取模板消息发送记录分页列表")
    @GetMapping(value = "getMsgRecordPage")
    public Result<PageInfo<MsgRecordVo>> getMsgRecordPage(@Validated GetMsgRecordPageInVo input){
        return getSuccessResult("请求成功",subscribeMessageService.getMsgRecordPage(input));
    }

    @ApiOperation("删除模板缓存")
    @DeleteMapping(value = "delTemplateCache")
    public Result<Boolean> delTemplateCache(@RequestBody MaDelTemplateCacheInVo input){
        subscribeMessageService.delTemplateCache(input.getAppId());
        return getSuccessResult("请求成功",true);
    }
}
