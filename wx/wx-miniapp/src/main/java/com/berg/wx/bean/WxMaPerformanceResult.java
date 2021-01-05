package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WxMaPerformanceResult {

    @SerializedName(value = "defaultTimeData", alternate = "default_time_data")
    String defaultTimeData;
    @SerializedName(value = "compareTimeData", alternate = "compare_time_data")
    String compareTimeData;

    public static WxMaPerformanceResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaPerformanceResult.class);
    }
}
