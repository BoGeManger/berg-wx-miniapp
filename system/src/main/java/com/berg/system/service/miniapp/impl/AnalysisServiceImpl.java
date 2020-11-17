package com.berg.system.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.analysis.*;
import cn.hutool.core.date.DateUtil;
import com.berg.exception.FailException;
import com.berg.system.service.miniapp.AnalysisService;
import com.berg.vo.miniapp.*;
import com.berg.vo.miniapp.in.*;
import com.berg.vo.miniapp.out.*;
import com.berg.wx.miniapp.utils.WxMaUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    //region 访问留存
    /**
     * 获取用户访问小程序日留存
     * @param input
     * @return
     */
    @Override
    public MaGetDailyRetainOutVo getDailyRetain(MaGetDailyRetainInVo input){
        MaGetDailyRetainOutVo result = new MaGetDailyRetainOutVo();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            WxMaRetainInfo wxMaRetainInfo = wxService.getAnalysisService().getDailyRetainInfo(beginDate,endDate);
            BeanUtils.copyProperties(wxMaRetainInfo,result);
        }catch (Exception ex){
            throw new FailException("调用获取用户访问小程序日留存接口getDailyRetain失败:"+ex.getMessage());
        }
        return result;
    }

    /**
     * 获取用户访问小程序月留存
     * @param input
     * @return
     */
    @Override
    public MaGetMonthlyRetainOutVo getMonthlyRetain(MaGetMonthlyRetainInVo input){
        MaGetMonthlyRetainOutVo result = new MaGetMonthlyRetainOutVo();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            WxMaRetainInfo wxMaRetainInfo = wxService.getAnalysisService().getMonthlyRetainInfo(beginDate,endDate);
            BeanUtils.copyProperties(wxMaRetainInfo,result);
        }catch (Exception ex){
            throw new FailException("调用获取用户访问小程序月留存接口getMonthlyRetain失败:"+ex.getMessage());
        }
        return result;
    }

    /**
     * 获取用户访问小程序周留存
     * @param input
     * @return
     */
    @Override
    public MaGetWeeklyRetainOutVo getWeeklyRetain(MaGetWeeklyRetainInVo input){
        MaGetWeeklyRetainOutVo result = new MaGetWeeklyRetainOutVo();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            WxMaRetainInfo wxMaRetainInfo = wxService.getAnalysisService().getWeeklyRetainInfo(beginDate,endDate);
            BeanUtils.copyProperties(wxMaRetainInfo,result);
        }catch (Exception ex){
            throw new FailException("调用获取用户访问小程序周留存接口getWeeklyRetain失败:"+ex.getMessage());
        }
        return result;
    }
    //endregion

    //region 访问趋势
    /**
     * 获取用户访问小程序数据日趋势
     * @param input
     * @return
     */
    @Override
    public List<MaGetDailyVisitTrendVo> getDailyVisitTrend(MaGetDailyVisitTrendInVo input){
        List<MaGetDailyVisitTrendVo> list = new ArrayList<>();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            List<WxMaVisitTrend> wxMaVisitTrendList = wxService.getAnalysisService().getDailyVisitTrend(beginDate,endDate);
            wxMaVisitTrendList.forEach(item->{
                MaGetDailyVisitTrendVo maGetDailyVisitTrendVo = new MaGetDailyVisitTrendVo();
                BeanUtils.copyProperties(item,maGetDailyVisitTrendVo);
                list.add(maGetDailyVisitTrendVo);
            });
        }catch (Exception ex){
            throw new FailException("调用获取用户访问小程序数据日趋势接口getDailyVisitTrend失败:"+ex.getMessage());
        }
        return list;
    }

    /**
     * 获取用户访问小程序数据月趋势(能查询到的最新数据为上一个自然月的数据)
     * @param input
     * @return
     */
    @Override
    public List<MaGetMonthlyVisitTrendVo> getMonthlyVisitTrend(MaGetMonthlyVisitTrendInVo input){
        List<MaGetMonthlyVisitTrendVo> list = new ArrayList<>();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            List<WxMaVisitTrend> wxMaVisitTrendList = wxService.getAnalysisService().getMonthlyVisitTrend(beginDate,endDate);
            wxMaVisitTrendList.forEach(item->{
                MaGetMonthlyVisitTrendVo maGetMonthlyVisitTrendVo = new MaGetMonthlyVisitTrendVo();
                BeanUtils.copyProperties(item,maGetMonthlyVisitTrendVo);
                list.add(maGetMonthlyVisitTrendVo);
            });
        }catch (Exception ex){
            throw new FailException("调用获取用户访问小程序数据月趋势接口getMonthlyVisitTrend失败:"+ex.getMessage());
        }
        return list;
    }

    /**
     * 获取用户访问小程序数据周趋势
     * @param input
     */
    @Override
    public List<MaGetWeeklyVisitTrendVo> getWeeklyVisitTrend(MaGetWeeklyVisitTrendInVo input){
        List<MaGetWeeklyVisitTrendVo> list = new ArrayList<>();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            List<WxMaVisitTrend> wxMaVisitTrendList = wxService.getAnalysisService().getWeeklyVisitTrend(beginDate,endDate);
            wxMaVisitTrendList.forEach(item->{
                MaGetWeeklyVisitTrendVo maGetWeeklyVisitTrendVo = new MaGetWeeklyVisitTrendVo();
                BeanUtils.copyProperties(item,maGetWeeklyVisitTrendVo);
                list.add(maGetWeeklyVisitTrendVo);
            });
        }catch (Exception ex){
            throw new FailException("调用获取用户访问小程序数据周趋势接口getWeeklyVisitTrend失败:"+ex.getMessage());
        }
        return list;
    }
    //endregion

    /**
     * 获取用户访问小程序数据概况
     * @param input
     * @return
     */
    @Override
    public List<MaGetDailySummaryVo> getDailySummary(MaGetDailySummaryInVo input){
        List<MaGetDailySummaryVo> list = new ArrayList<>();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            List<WxMaSummaryTrend> wxMaSummaryTrendList = wxService.getAnalysisService().getDailySummaryTrend(beginDate,endDate);
            wxMaSummaryTrendList.forEach(item->{
                MaGetDailySummaryVo maGetDailySummaryVo = new MaGetDailySummaryVo();
                BeanUtils.copyProperties(item,maGetDailySummaryVo);
                list.add(maGetDailySummaryVo);
            });
        }catch (Exception ex){
            throw new FailException("调用获取用户访问小程序数据周趋势接口getDailySummary失败:"+ex.getMessage());
        }
        return list;
    }

    /**
     * 获取小程序新增或活跃用户的画像分布数据。时间范围支持昨天、最近7天、最近30天。其中，新增用户数为时间范围内首次访问小程序的去重用户数，活跃用户数为时间范围内访问过小程序的去重用户数
     * @param input
     * @return
     */
    @Override
    public MaGetUserPortraitOutVo getUserPortrait(MaGetUserPortraitInVo input){
        MaGetUserPortraitOutVo result = new MaGetUserPortraitOutVo();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            WxMaUserPortrait wxMaUserPortrait = wxService.getAnalysisService().getUserPortrait(beginDate,endDate);
            BeanUtils.copyProperties(wxMaUserPortrait,result);
        }catch (Exception ex){
            throw new FailException("调用获取小程序新增或活跃用户的画像分布数据接口getUserPortrait失败:"+ex.getMessage());
        }
        return result;
    }

    /**
     * 获取用户小程序访问分布数据
     * @param input
     * @return
     */

    public MaGetVisitDistributionOutVo getVisitDistribution(MaGetVisitDistributionInVo input){
        MaGetVisitDistributionOutVo result = new MaGetVisitDistributionOutVo();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            WxMaVisitDistribution wxMaVisitDistribution = wxService.getAnalysisService().getVisitDistribution(beginDate,endDate);
            BeanUtils.copyProperties(wxMaVisitDistribution,result);
        }catch (Exception ex){
            throw new FailException("调用获取用户小程序访问分布数据接口getVisitDistribution失败:"+ex.getMessage());
        }
        return result;
    }

    /**
     * 访问页面。目前只提供按 page_visit_pv 排序的 top200
     * @param input
     * @return
     */
    @Override
    public List<MaGetVisitPageVo> getVisitPage(MaGetVisitPageInVo input){
        List<MaGetVisitPageVo> list = new ArrayList<>();
        try {
            Date beginDate = DateUtil.parse(input.getBeginDate());
            Date endDate = DateUtil.parse(input.getEndDate());
            WxMaService wxService = WxMaUtil.getService(input.getAppId());
            List<WxMaVisitPage> wxMaVisitPageList = wxService.getAnalysisService().getVisitPage(beginDate,endDate);
            wxMaVisitPageList.forEach(item->{
                MaGetVisitPageVo maGetVisitPageVo = new MaGetVisitPageVo();
                BeanUtils.copyProperties(item,maGetVisitPageVo);
                list.add(maGetVisitPageVo);
            });
        }catch (Exception ex){
            throw new FailException("调用访问页面接口getVisitPage失败:"+ex.getMessage());
        }
        return list;
    }
}
