package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WxMaFeedbackResult {

    /**
     * 总条数
     */
    @SerializedName(value = "totalNum", alternate = "total_num")
    Integer totalNum;
    /**
     * 反馈列表
     */
    List<Map<String,Object>> list;

    public static WxMaFeedbackResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaFeedbackResult.class);
    }
}
