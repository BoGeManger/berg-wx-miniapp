package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WxMaPerformanceRequest {

    /**
     * 可选值 1（启动总耗时）， 2（下载耗时），3（初次渲染耗时）
     */
    @SerializedName(value = "costTimeType", alternate = "cost_time_type")
    Integer costTimeType;
    /**
     * 查询开始时间
     */
    @SerializedName(value = "defaultStartTime", alternate = "default_start_time")
    Integer defaultStartTime;
    /**
     * 查询结束时间
     */
    @SerializedName(value = "defaultEndTime", alternate = "default_end_time")
    Integer defaultEndTime;
    /**
     * 系统平台，可选值 "@_all:"（全部），1（IOS）， 2（android）
     */
    String device;
    /**
     * 是否下载代码包，当 type 为 1 的时候才生效，可选值 "@_all:"（全部），1（是）， 2（否）
     */
    @SerializedName(value = "isdownloadCode", alternate = "is_download_code")
    String isdownloadCode;
    /**
     * 访问来源，当 type 为 1 或者 2 的时候才生效，通过 getSceneList 接口获取
     */
    String scene;
    /**
     * 网络环境, 当 type 为 2 的时候才生效，可选值 "@_all:"，wifi, 4g, 3g, 2g
     */
    String networktype;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
