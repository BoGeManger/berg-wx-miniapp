package com.berg.system.controller;

import com.berg.base.BaseController;
import com.berg.message.Result;
import com.berg.system.service.system.ComponentService;
import com.berg.vo.common.ListVo;
import com.berg.vo.system.ComponentEditVo;
import com.berg.vo.system.ComponentTreeVo;
import com.berg.vo.system.in.OperatorBatchComInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/com")
@Api(tags = "组件管理")
public class ComponentController extends BaseController {

    @Autowired
    ComponentService componentService;

    @ApiOperation("获取组件树形列表")
    @GetMapping(value = "getComTree")
    public Result<ListVo<ComponentTreeVo>> getComTree(){
        return getSuccessResult("请求成功",componentService.getComTree());
    }

    @ApiOperation("获取组件")
    @GetMapping(value = "getCom")
    public Result<ComponentEditVo> getCom(@ApiParam(value = "表id",required = true) @RequestParam Integer id){
        return getSuccessResult("请求成功",componentService.getCom(id));
    }

    @ApiOperation("批量操作组件(新增,修改,删除)")
    @PostMapping(value = "operatorBatchCom")
    public Result<Boolean> operatorBatchCom(@RequestBody @Validated OperatorBatchComInVo input){
        componentService.operatorBatchCom(input);
        return getSuccessResult("请求成功",true);
    }

}
