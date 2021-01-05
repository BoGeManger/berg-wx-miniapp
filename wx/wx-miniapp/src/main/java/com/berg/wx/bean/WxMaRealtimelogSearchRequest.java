package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import lombok.Data;

@Data
public class WxMaRealtimelogSearchRequest {

    /**
     * YYYYMMDD格式的日期，仅支持最近7天
     */
    String date;
    /**
     * 开始时间，必须是date指定日期的时间
     */
    Integer begintime;
    /**
     * 结束时间，必须是date指定日期的时间
     */
    Integer endtime;
    /**
     * 开始返回的数据下标，用作分页，默认为0
     */
    Integer start;
    /**
     * 返回的数据条数，用作分页，默认为20
     */
    Integer limit;
    /**
     * 小程序启动的唯一ID，按TraceId查询会展示该次小程序启动过程的所有页面的日志。
     */
    String traceId;
    /**
     * 小程序页面路径，例如pages/index/index
     */
    String url;
    /**
     * 用户微信号或者OpenId
     */
    String id;
    /**
     * 开发者通过setFileterMsg/addFilterMsg指定的filterMsg字段
     */
    String filterMsg;
    /**
     * 日志等级，返回大于等于level等级的日志，level的定义为2（Info）、4（Warn）、8（Error），如果指定为4，则返回大于等于4的日志，即返回Warn和Error日志。
     */
    Integer level;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
