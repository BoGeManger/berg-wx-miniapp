package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WxMaVersionListResult {

    /**
     * 版本列表
     */
    List<Map<String,Object>> cvlist;

    public static WxMaVersionListResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaVersionListResult.class);
    }
}
