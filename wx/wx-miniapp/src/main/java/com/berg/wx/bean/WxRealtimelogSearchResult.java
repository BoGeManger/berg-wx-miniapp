package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.util.List;

@Data
public class WxRealtimelogSearchResult {

    /**
     * 总条数
     */
    Integer total;
    /**
     * 数据列表
     */
    List<WxRealtimelogSearch> list;

    public static WxRealtimelogSearchResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxRealtimelogSearchResult.class);
    }
}
