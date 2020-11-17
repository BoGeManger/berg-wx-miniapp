package com.berg.system.controller;

import com.berg.base.BaseController;
import com.berg.message.Result;
import com.berg.system.service.system.LoginService;
import com.berg.vo.system.in.LoginInVo;
import com.berg.vo.system.out.LoginOutVo;
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
@Api(tags = "用户登录")
public class LoginController extends BaseController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "用户登录",notes = "无需登录校验")
    @PostMapping(value = "login")
    public Result<LoginOutVo> login(@RequestBody @Validated LoginInVo input){
        return getSuccessResult("请求成功",loginService.login(input));
    }

    @ApiOperation(value = "退出登录")
    @PostMapping(value = "logout")
    public Result logout(){
        loginService.logout();
        return getSuccessResult("请求成功",null);
    }
}
