package com.berg.system.controller;

import com.berg.base.BaseController;
import com.berg.message.Result;
import com.berg.system.service.miniapp.AppService;
import com.berg.vo.miniapp.MaAppVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/maapp")
@Api(tags = "微信小程序应用")
public class MaAppController extends BaseController {

    @Autowired
    AppService appService;

    @ApiOperation("获取小程序应用列表")
    @GetMapping(value = "getAppList")
    public Result<List<MaAppVo>> getAppList(){
        return getSuccessResult("请求成功",appService.getAppList());
    }
}
