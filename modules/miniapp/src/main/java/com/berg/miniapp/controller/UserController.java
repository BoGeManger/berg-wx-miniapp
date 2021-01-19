package com.berg.miniapp.controller;

import com.berg.common.constant.Result;
import com.berg.common.controller.AbstractController;
import com.berg.miniapp.service.miniapp.UserService;
import com.berg.vo.miniapp.in.MaSetUserInfoInVo;
import com.berg.vo.miniapp.in.MaUserPhoneInVo;
import com.berg.vo.miniapp.out.MaUserPhoneOutVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "微信小程序用户")
public class UserController extends AbstractController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取用户手机号码",notes = "获取用户手机号码并注册,可重新调用refresh重新生成请求校验保证用户信息为最新")
    @PostMapping(value = "phone")
    public Result<MaUserPhoneOutVo> phone(@RequestBody @Validated MaUserPhoneInVo input){
        return getSuccessResult("请求成功",userService.phone(input));
    }

    @ApiOperation(value = "保存用户信息",notes = "可重新调用refresh重新生成请求校验保证用户信息为最新")
    @PostMapping(value = "setUserInfo")
    public Result<Boolean> setUserInfo(@RequestBody @Validated MaSetUserInfoInVo input){
        userService.setUserInfo(input);
        return getSuccessResult("请求成功",true);
    }
}
