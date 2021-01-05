package com.berg.wx.service;

import com.berg.wx.bean.*;
import me.chanjar.weixin.common.error.WxErrorException;

public interface WxMaOperationService {
    String GET_FEED_BACK_URL = "https://api.weixin.qq.com/wxaapi/feedback/list";
    String GET_JSERR_SEARCH_URL = "https://api.weixin.qq.com/wxaapi/log/jserr_search";
    String GET_PERFORMANCE_URL = "https://api.weixin.qq.com/wxaapi/log/get_performance";
    String GET_SCENE_URL = "https://api.weixin.qq.com/wxaapi/log/get_scene";
    String GET_CLIENT_VERSION_URL = "https://api.weixin.qq.com/wxaapi/log/get_client_version";
    String GET_USERLOG_SEARCH_URL = "https://api.weixin.qq.com/wxaapi/userlog/userlog_search";

    WxMaFeedbackResult getFeedback(Integer page, Integer num, Integer type) throws WxErrorException;

    WxMaJsErrSearchInforResult getJsErrSearch(WxMaJsErrSearchInfoRequest wxMaJsErrSearchInfoRequest)throws WxErrorException;

    WxMaPerformanceResult getPerformance(WxMaPerformanceRequest wxMaPerformanceRequest)throws WxErrorException;

    WxMaSceneListResult getSceneList()throws WxErrorException;

    WxMaVersionListResult getVersionList() throws WxErrorException;

    WxRealtimelogSearchResult realtimelogSearch(WxMaRealtimelogSearchRequest wxMaRealtimelogSearchRequest) throws WxErrorException;
}
