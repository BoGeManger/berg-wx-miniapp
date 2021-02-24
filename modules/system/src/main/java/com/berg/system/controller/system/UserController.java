package com.berg.system.controller.system;

import com.berg.common.controller.AbstractController;
import com.berg.dao.page.PageInfo;
import com.berg.common.constant.Result;
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
public class UserController extends AbstractController {

    @Autowired
    UserService userService;

    @ApiOperation("获取用户分页列表")
    @GetMapping(value = "getUserPage")
    public Result<PageInfo<UserVo>> getUserPage(@Validated GetUserPageInVo input){
        return success("请求成功",()->userService.getUserPage(input));
    }

    @ApiOperation("获取用户")
    @GetMapping(value = "getUser")
    public Result<UserEditVo> getUser(@ApiParam(value = "表id",required = true) @RequestParam Integer id){
        return success("请求成功",()->userService.getUser(id));
    }

    @ApiOperation("新增用户")
    @PostMapping(value = "addUser")
    public Result<Integer> addUser(@RequestBody @Validated UserEditVo input){
        return success("请求成功", ()->userService.addUser(input));
    }

    @ApiOperation("修改用户")
    @PutMapping(value = "updateUser")
    public Result<Integer> updateUser(@RequestBody @Validated UserEditVo input){
        return success("请求成功",()->userService.updateUser(input));
    }

    @ApiOperation("删除用户")
    @DeleteMapping(value = "delUser")
    public Result<Void> delUser(@RequestBody EntityIdVo<Integer> input){
        return success("请求成功",()->userService.delUser(input.getId()));
    }

    @ApiOperation("锁定用户")
    @PutMapping(value = "lockUser")
    public Result<Void> lockUser(@RequestBody EntityIdVo<Integer> input){
        return success("请求成功",()->userService.lockUser(input.getId()));
    }

    @ApiOperation("解锁用户")
    @PutMapping(value = "unlockUser")
    public Result<Void> unlockUser(@RequestBody EntityIdVo<Integer> input){
        return success("请求成功",()->userService.unlockUser(input.getId()));
    }

    @ApiOperation("更新用户密码")
    @PutMapping(value = "updatePassword")
    public Result<Void> updatePassword(@RequestBody @Validated UpdatePasswordInVo input){
        return success("请求成功",()->userService.updatePassword(input));
    }
}
