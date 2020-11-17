package com.berg.system.controller;

import com.berg.base.BaseController;
import com.berg.message.Result;
import com.berg.system.service.miniapp.AnalysisService;
import com.berg.vo.miniapp.*;
import com.berg.vo.miniapp.in.*;
import com.berg.vo.miniapp.out.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analysis")
@Api(tags = "微信小程序数据分析")
public class AnalysisController extends BaseController {

    @Autowired
    AnalysisService analysisService;

    @ApiOperation("获取用户访问小程序日留存")
    @GetMapping(value = "getDailyRetain")
    public Result<MaGetDailyRetainOutVo> getDailyRetain(@Validated MaGetDailyRetainInVo input){
        return getSuccessResult("请求成功",analysisService.getDailyRetain(input));
    }

    @ApiOperation("获取用户访问小程序月留存")
    @GetMapping(value = "getMonthlyRetain")
    public Result<MaGetMonthlyRetainOutVo> getMonthlyRetain(@Validated MaGetMonthlyRetainInVo input){
        return getSuccessResult("请求成功",analysisService.getMonthlyRetain(input));
    }

    @ApiOperation("获取用户访问小程序周留存")
    @GetMapping(value = "getWeeklyRetain")
    public Result<MaGetWeeklyRetainOutVo> getWeeklyRetain(@Validated MaGetWeeklyRetainInVo input){
        return getSuccessResult("请求成功",analysisService.getWeeklyRetain(input));
    }

    @ApiOperation("获取用户访问小程序数据日趋势")
    @GetMapping(value = "getDailyVisitTrend")
    public Result<List<MaGetDailyVisitTrendVo>> getDailyVisitTrend(@Validated MaGetDailyVisitTrendInVo input){
        return getSuccessResult("请求成功",analysisService.getDailyVisitTrend(input));
    }

    @ApiOperation(value = "获取用户访问小程序数据月趋势",notes = "能查询到的最新数据为上一个自然月的数据")
    @GetMapping(value = "getMonthlyVisitTrend")
    public Result<List<MaGetMonthlyVisitTrendVo>> getMonthlyVisitTrend(@Validated MaGetMonthlyVisitTrendInVo input){
        return getSuccessResult("请求成功",analysisService.getMonthlyVisitTrend(input));
    }

    @ApiOperation(value = "获取用户访问小程序数据周趋势")
    @GetMapping(value = "getWeeklyVisitTrend")
    public Result<List<MaGetWeeklyVisitTrendVo>> getWeeklyVisitTrend(@Validated MaGetWeeklyVisitTrendInVo input){
        return getSuccessResult("请求成功",analysisService.getWeeklyVisitTrend(input));
    }

    @ApiOperation(value = "获取用户访问小程序数据概况")
    @GetMapping(value = "getDailySummary")
    public Result<List<MaGetDailySummaryVo>> getDailySummary(@Validated MaGetDailySummaryInVo input){
        return getSuccessResult("请求成功",analysisService.getDailySummary(input));
    }

    @ApiOperation(value = "获取小程序新增或活跃用户的画像分布数据",notes = "时间范围支持昨天、最近7天、最近30天。其中，新增用户数为时间范围内首次访问小程序的去重用户数，活跃用户数为时间范围内访问过小程序的去重用户数")
    @GetMapping(value = "getUserPortrait")
    public Result<MaGetUserPortraitOutVo> getUserPortrait(@Validated MaGetUserPortraitInVo input){
        return getSuccessResult("请求成功",analysisService.getUserPortrait(input));
    }

    @ApiOperation(value = "获取用户小程序访问分布数据")
    @GetMapping(value = "getVisitDistribution")
    public Result<MaGetVisitDistributionOutVo> getVisitDistribution(@Validated MaGetVisitDistributionInVo input){
        return getSuccessResult("请求成功",analysisService.getVisitDistribution(input));
    }

    @ApiOperation(value = "访问页面",notes = "目前只提供按 page_visit_pv 排序的 top200")
    @GetMapping(value = "getVisitPage")
    public Result<List<MaGetVisitPageVo>> getVisitPage(@Validated MaGetVisitPageInVo input){
        return getSuccessResult("请求成功",analysisService.getVisitPage(input));
    }
}
