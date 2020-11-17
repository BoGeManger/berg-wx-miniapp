package com.berg.system.service.miniapp;

import com.berg.vo.miniapp.*;
import com.berg.vo.miniapp.in.*;
import com.berg.vo.miniapp.out.*;

import java.util.List;

public interface AnalysisService {

    MaGetDailyRetainOutVo getDailyRetain(MaGetDailyRetainInVo input);

    MaGetMonthlyRetainOutVo getMonthlyRetain(MaGetMonthlyRetainInVo input);

    MaGetWeeklyRetainOutVo getWeeklyRetain(MaGetWeeklyRetainInVo input);

    List<MaGetDailyVisitTrendVo> getDailyVisitTrend(MaGetDailyVisitTrendInVo input);

    List<MaGetMonthlyVisitTrendVo> getMonthlyVisitTrend(MaGetMonthlyVisitTrendInVo input);

    List<MaGetWeeklyVisitTrendVo> getWeeklyVisitTrend(MaGetWeeklyVisitTrendInVo input);

    List<MaGetDailySummaryVo> getDailySummary(MaGetDailySummaryInVo input);

    MaGetUserPortraitOutVo getUserPortrait(MaGetUserPortraitInVo input);

    MaGetVisitDistributionOutVo getVisitDistribution(MaGetVisitDistributionInVo input);

    List<MaGetVisitPageVo> getVisitPage(MaGetVisitPageInVo input);
}
