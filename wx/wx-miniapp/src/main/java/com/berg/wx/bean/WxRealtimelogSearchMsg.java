package com.berg.wx.bean;

import lombok.Data;

import java.util.List;

@Data
public class WxRealtimelogSearchMsg {

    /**
     * log.info调用的时间
     */
    Integer time;
    /**
     * log.info调用的内容，每个参数分别是数组的一项
     */
    List<String> msg;
    /**
     * log.info调用的日志等级
     */
    Integer level;
}
