package com.berg.system.controller.system;

import com.berg.common.controller.AbstractController;
import com.berg.common.constant.Result;
import com.berg.system.service.system.OrganizationService;
import com.berg.vo.common.ListVo;
import com.berg.vo.system.OrganizationEditVo;
import com.berg.vo.system.OrganizationTreeVo;
import com.berg.vo.system.in.OperatorBatchOrganizationInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
@Api(tags = "组织管理")
public class OrganizationController extends AbstractController {

    @Autowired
    OrganizationService organizationService;

    @ApiOperation("获取组织树形列表")
    @GetMapping(value = "getOrganizationTree")
    public Result<ListVo<OrganizationTreeVo>> getOrganizationTree(){
        return success("请求成功",()->organizationService.getOrganizationTree());
    }

    @ApiOperation("获取组织")
    @GetMapping(value = "getOrganization")
    public Result<OrganizationEditVo> getOrganization(@ApiParam(value = "表id",required = true) @RequestParam Integer id){
        return success("请求成功",()->organizationService.getOrganization(id));
    }

    @ApiOperation("新增组织")
    @PostMapping(value = "addOrganization")
    public Result<Integer> addOrganization(@RequestBody @Validated OrganizationEditVo input){
        return success("请求成功",()->organizationService.addOrganization(input));
    }

    @ApiOperation("修改组织")
    @PutMapping(value = "updateOrganization")
    public Result<Integer> updateOrganization(@RequestBody @Validated OrganizationEditVo input){
        return success("请求成功",()->organizationService.updateOrganization(input));
    }

    @ApiOperation("批量操作组织(新增,修改,删除)")
    @PostMapping(value = "operatorBatchOrganization")
    public Result<Void> operatorBatchOrganization(@RequestBody @Validated OperatorBatchOrganizationInVo input){
        return success("请求成功",()->organizationService.operatorBatchOrganization(input));
    }

}
