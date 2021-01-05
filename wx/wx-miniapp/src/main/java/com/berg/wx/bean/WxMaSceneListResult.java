package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WxMaSceneListResult {

    /**
     * 访问来源
     */
    List<Map<String,Object>> scene;

    public static WxMaSceneListResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaSceneListResult.class);
    }
}
