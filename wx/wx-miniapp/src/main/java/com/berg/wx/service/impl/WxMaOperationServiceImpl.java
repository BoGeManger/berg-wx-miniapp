package com.berg.wx.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.berg.wx.bean.*;
import com.berg.wx.service.WxMaOperationService;
import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class WxMaOperationServiceImpl implements WxMaOperationService {
    final WxMaService wxMaService;

    /**
     * 获取用户反馈列表
     * @param page
     * @param num
     * @param type
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMaFeedbackResult getFeedback(Integer page, Integer num, Integer type) throws WxErrorException {
        Map<String,Object> params = new HashMap<String,Object>(){{
            put("page",page);
            put("num",num);
            if(type!=null){
                put("type",type);
            }
        }};
        String responseContent = this.wxMaService.get(GET_FEED_BACK_URL, Joiner.on("&").withKeyValueSeparator("=").join(params));
        return WxMaFeedbackResult.fromJson(responseContent);
    }

    /**
     * 错误查询
     * @param wxMaJsErrSearchInfoRequest
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMaJsErrSearchInforResult getJsErrSearch(WxMaJsErrSearchInfoRequest wxMaJsErrSearchInfoRequest) throws WxErrorException{
        String responseContent = this.wxMaService.post(GET_JSERR_SEARCH_URL,wxMaJsErrSearchInfoRequest.toJson());
        return WxMaJsErrSearchInforResult.fromJson(responseContent);
    }

    /**
     * 性能监控
     * @param wxMaPerformanceRequest
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMaPerformanceResult getPerformance(WxMaPerformanceRequest wxMaPerformanceRequest) throws WxErrorException{
        String responseContent = this.wxMaService.post(GET_PERFORMANCE_URL,wxMaPerformanceRequest.toJson());
        return WxMaPerformanceResult.fromJson(responseContent);
    }


    /**
     * 获取访问来源
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMaSceneListResult getSceneList() throws WxErrorException{
        String responseContent = this.wxMaService.get(GET_SCENE_URL,null);
        return WxMaSceneListResult.fromJson(responseContent);
    }

    /**
     * 获取客户端版本
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMaVersionListResult getVersionList() throws WxErrorException{
        String responseContent = this.wxMaService.get(GET_CLIENT_VERSION_URL,null);
        return WxMaVersionListResult.fromJson(responseContent);
    }

    /**
     * 实时日志查询
     * @param wxMaRealtimelogSearchRequest
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxRealtimelogSearchResult realtimelogSearch(WxMaRealtimelogSearchRequest wxMaRealtimelogSearchRequest) throws WxErrorException{
        String responseContent = this.wxMaService.post(GET_USERLOG_SEARCH_URL,wxMaRealtimelogSearchRequest.toJson());
        return WxRealtimelogSearchResult.fromJson(responseContent);
    }
}
