package com.berg.miniapp.controller;

import com.berg.base.BaseController;
import com.berg.message.Result;
import com.berg.miniapp.service.miniapp.LoginService;
import com.berg.vo.miniapp.in.MaLoginInVo;
import com.berg.vo.miniapp.in.MaRefreshInVo;
import com.berg.vo.miniapp.out.MaLoginOutVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(tags = "微信小程序用户登录")
public class LoginController extends BaseController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "小程序登录",notes = "生成登录授权并返回最新用户信息")
    @PostMapping(value = "login")
    public Result<MaLoginOutVo> login(@RequestBody @Validated MaLoginInVo input){
        return getSuccessResult("请求成功",loginService.login(input));
    }

    @ApiOperation(value = "刷新登录校验",notes = "刷新登录授权并返回最新用户信息,新用户code必填,旧用户sessionKey过期code必填")
    @PostMapping(value = "refresh")
    public Result<MaLoginOutVo> refresh(@RequestBody @Validated MaRefreshInVo input){
        return getSuccessResult("请求成功",loginService.refresh(input));
    }

    @ApiOperation(value = "小程序退出登录",notes = "删除缓存登录授权")
    @PostMapping(value = "logout")
    public Result<Boolean> logout(){
        loginService.logout();
        return getSuccessResult("请求成功",true);
    }
}
