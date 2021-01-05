package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WxMaJsErrSearchInforResult {

    /**
     * 总条数
     */
    Integer total;
    /**
     * 错误列表
     */
    List<Map<String,Object>> list;

    public static WxMaJsErrSearchInforResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaJsErrSearchInforResult.class);
    }
}
