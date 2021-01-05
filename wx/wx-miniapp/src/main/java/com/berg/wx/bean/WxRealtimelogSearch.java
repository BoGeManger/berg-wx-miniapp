package com.berg.wx.bean;

import lombok.Data;

import java.util.List;

@Data
public class WxRealtimelogSearch {

    /**
     * 日志等级，是msg数组里面的所有level字段的或操作得到的结果。例如msg数组里有两条日志，Info（值为2）和Warn（值为4），则level值为6
     */
    Integer level;
    /**
     * 基础库版本
     */
    String libraryVersion;
    /**
     * 客户端版本
     */
    String clientVersion;
    /**
     * 微信用户OpenID
     */
    String id;
    /**
     * 打日志的Unix时间戳
     */
    Integer timestamp;
    /**
     * 1 安卓 2 IOS
     */
    String platform;
    /**
     * 小程序页面链接
     */
    String url;
    /**
     * 日志内容数组，log.info等的内容存在这里
     */
    List<WxRealtimelogSearchMsg> msg;
    /**
     * 小程序启动的唯一ID，按TraceId查询会展示该次小程序启动过程的所有页面的日志。
     */
    String traceid;
    /**
     * 微信用户OpenID
     */
    String filterMsg;
}
