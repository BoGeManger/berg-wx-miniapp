package com.berg.system.controller;

import com.berg.base.BaseController;
import com.berg.dao.page.PageInfo;
import com.berg.message.Result;
import com.berg.system.service.system.QuartzJobService;
import com.berg.vo.common.EntityIdVo;
import com.berg.vo.system.JobEditVo;
import com.berg.vo.system.JobVo;
import com.berg.vo.system.in.GetJobPageInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quartzjob")
@Api(tags = "定时任务管理")
public class QuartzJobController extends BaseController {

    @Autowired
    QuartzJobService quartzJobService;

    @ApiOperation("获取定时任务分页")
    @GetMapping(value = "getJobPage")
    public Result<PageInfo<JobVo>> getJobPage(@Validated GetJobPageInVo input){
        return getSuccessResult("请求成功",quartzJobService.getJobPage(input));
    }

    @ApiOperation("获取定时任务")
    @GetMapping(value = "getJob")
    public Result<JobEditVo> getJob(@ApiParam(value = "表id",required = true) @RequestParam Integer id){
        return getSuccessResult("请求成功",quartzJobService.getJob(id));
    }

    @ApiOperation("新增定时任务")
    @PostMapping(value = "addJob")
    public Result<Integer> addJob(@RequestBody @Validated JobEditVo input){
        return getSuccessResult("请求成功", quartzJobService.addJob(input));
    }

    @ApiOperation("删除定时任务")
    @DeleteMapping(value = "delJob")
    public Result<Boolean> delJob(@RequestBody EntityIdVo<Integer> input){
        quartzJobService.delJob(input.getId());
        return getSuccessResult("请求成功",true);
    }

    @ApiOperation("暂停定时任务")
    @PutMapping(value = "pauseJob")
    public Result<Boolean> pauseJob(@RequestBody EntityIdVo<Integer> input){
        quartzJobService.pauseJob(input.getId());
        return getSuccessResult("请求成功",true);
    }

    @ApiOperation("启动定时任务")
    @PutMapping(value = "resumeJob")
    public Result<Boolean> resumeJob(@RequestBody EntityIdVo<Integer> input){
        quartzJobService.resumeJob(input.getId());
        return getSuccessResult("请求成功",true);
    }
}
