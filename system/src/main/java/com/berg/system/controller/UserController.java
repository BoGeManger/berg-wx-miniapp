package com.berg.system.controller;

import com.berg.base.BaseController;
import com.berg.dao.page.PageInfo;
import com.berg.message.Result;
import com.berg.system.service.system.UserService;
import com.berg.vo.common.EntityIdVo;
import com.berg.vo.system.UserEditVo;
import com.berg.vo.system.UserVo;
import com.berg.vo.system.in.GetUserPageInVo;
import com.berg.vo.system.in.UpdatePasswordInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @ApiOperation("获取用户分页列表")
    @GetMapping(value = "getUserPage")
    public Result<PageInfo<UserVo>> getUserPage(@Validated GetUserPageInVo input){
        return getSuccessResult("请求成功",userService.getUserPage(input));
    }

    @ApiOperation("获取用户")
    @GetMapping(value = "getUser")
    public Result<UserEditVo> getUser(@ApiParam(value = "表id",required = true) @RequestParam Integer id){
        return getSuccessResult("请求成功",userService.getUser(id));
    }

    @ApiOperation("新增用户")
    @PostMapping(value = "addUser")
    public Result<Integer> addUser(@RequestBody @Validated UserEditVo input){
        return getSuccessResult("请求成功", userService.addUser(input));
    }

    @ApiOperation("修改用户")
    @PutMapping(value = "updateUser")
    public Result<Integer> updateUser(@RequestBody @Validated UserEditVo input){
        return getSuccessResult("请求成功",userService.updateUser(input));
    }

    @ApiOperation("删除用户")
    @DeleteMapping(value = "delUser")
    public Result<Boolean> delUser(@RequestBody EntityIdVo<Integer> input){
        userService.delUser(input.getId());
        return getSuccessResult("请求成功",true);
    }

    @ApiOperation("锁定用户")
    @PutMapping(value = "lockUser")
    public Result<Boolean> lockUser(@RequestBody EntityIdVo<Integer> input){
        userService.lockUser(input.getId());
        return getSuccessResult("请求成功",true);
    }

    @ApiOperation("解锁用户")
    @PutMapping(value = "unlockUser")
    public Result<Boolean> unlockUser(@RequestBody EntityIdVo<Integer> input){
        userService.unlockUser(input.getId());
        return getSuccessResult("请求成功",true);
    }

    @ApiOperation("更新用户密码")
    @PutMapping(value = "updatePassword")
    public Result<Boolean> updatePassword(@RequestBody @Validated UpdatePasswordInVo input){
        userService.updatePassword(input);
        return getSuccessResult("请求成功",true);
    }
}
