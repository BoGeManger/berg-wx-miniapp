package com.berg.miniapp.controller;

import com.berg.common.controller.AbstractController;
import com.berg.common.constant.Result;
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
public class LoginController extends AbstractController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "小程序登录",notes = "前端未存在请求校验时调用,生成请求校验并返回最新用户信息")
    @PostMapping(value = "login")
    public Result<MaLoginOutVo> login(@RequestBody @Validated MaLoginInVo input){
        return getSuccessResult("请求成功",loginService.login(input));
    }

    @ApiOperation(value = "刷新请求校验",notes = "前端存在请求校验时调用,刷新请求校验并返回最新用户信息")
    @PostMapping(value = "refresh")
    public Result<MaLoginOutVo> refresh(@RequestBody @Validated MaRefreshInVo input){
        return getSuccessResult("请求成功",loginService.refresh(input));
    }

    @ApiOperation(value = "小程序退出登录",notes = "删除缓存请求校验")
    @PostMapping(value = "logout")
    public Result<Boolean> logout(){
        loginService.logout();
        return getSuccessResult("请求成功",true);
    }
}
