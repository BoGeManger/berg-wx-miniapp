package com.berg.system.controller;

import com.berg.base.BaseController;
import com.berg.dao.page.PageInfo;
import com.berg.message.Result;
import com.berg.system.service.system.RoleService;
import com.berg.vo.common.EntityIdVo;
import com.berg.vo.system.RoleEditVo;
import com.berg.vo.system.RoleVo;
import com.berg.vo.system.in.GetRolePageInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController  extends BaseController {

    @Autowired
    RoleService roleService;

    @ApiOperation("获取角色分页列表")
    @GetMapping(value = "getRolePage")
    public Result<PageInfo<RoleVo>> getRolePage(@Validated GetRolePageInVo input){
        return getSuccessResult("请求成功",roleService.getRolePage(input));
    }

    @ApiOperation("获取角色")
    @GetMapping(value = "getRole")
    public Result<RoleEditVo> getRole(@ApiParam(value = "表id",required = true) @RequestParam Integer id){
        return getSuccessResult("请求成功",roleService.getRole(id));
    }

    @ApiOperation("新增角色")
    @PostMapping(value = "addRole")
    public Result<Integer> addRole(@RequestBody @Validated RoleEditVo input){
        return getSuccessResult("请求成功",roleService.addRole(input));
    }

    @ApiOperation("修改角色")
    @PutMapping(value = "updateRole")
    public Result<Integer> updateRole(@RequestBody @Validated RoleEditVo input){
        return getSuccessResult("请求成功",roleService.updateRole(input));
    }

    @ApiOperation("删除角色")
    @DeleteMapping(value = "delRole")
    public Result<Boolean> delRole(@RequestBody EntityIdVo<Integer> input){
        roleService.delRole(input.getId());
        return getSuccessResult("请求成功",true);
    }
}
