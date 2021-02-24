package com.berg.system.controller.system;

import com.berg.common.controller.AbstractController;
import com.berg.dao.page.PageInfo;
import com.berg.common.constant.Result;
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
public class QuartzJobController extends AbstractController {

    @Autowired
    QuartzJobService quartzJobService;

    @ApiOperation("获取定时任务分页")
    @GetMapping(value = "getJobPage")
    public Result<PageInfo<JobVo>> getJobPage(@Validated GetJobPageInVo input){
        return success("请求成功",()->quartzJobService.getJobPage(input));
    }

    @ApiOperation("获取定时任务")
    @GetMapping(value = "getJob")
    public Result<JobEditVo> getJob(@ApiParam(value = "表id",required = true) @RequestParam Integer id){
        return success("请求成功",()->quartzJobService.getJob(id));
    }

    @ApiOperation("新增定时任务")
    @PostMapping(value = "addJob")
    public Result<Integer> addJob(@RequestBody @Validated JobEditVo input){
        return success("请求成功", ()->quartzJobService.addJob(input));
    }

    @ApiOperation("删除定时任务")
    @DeleteMapping(value = "delJob")
    public Result<Void> delJob(@RequestBody EntityIdVo<Integer> input){
        return success("请求成功",()->quartzJobService.delJob(input.getId()));
    }

    @ApiOperation("暂停定时任务")
    @PutMapping(value = "pauseJob")
    public Result<Void> pauseJob(@RequestBody EntityIdVo<Integer> input){
        return success("请求成功",()->quartzJobService.pauseJob(input.getId()));
    }

    @ApiOperation("启动定时任务")
    @PutMapping(value = "resumeJob")
    public Result<Void> resumeJob(@RequestBody EntityIdVo<Integer> input){
        return success("请求成功",()->quartzJobService.resumeJob(input.getId()));
    }
}
