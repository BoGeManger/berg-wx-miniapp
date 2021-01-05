package com.berg.wx.bean;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WxMaJsErrSearchInfoRequest {

    /**
     * 错误关键字(必填)
     */
    @SerializedName(value = "errmsgKeyword", alternate = "errmsg_keyword")
    String errmsgKeyword;
    /**
     * 查询类型，1 为客户端， 2为服务直达(必填)
     */
    Integer type;
    /**
     * 客户端版本，可以通过 getVersionList 接口拉取, 不传或者传空代表所有版本(必填)
     */
    @SerializedName(value = "clientVersion", alternate = "client_version")
    String clientVersion;

    /**
     * 开始时间(必填)
     */
    @SerializedName(value = "startTime", alternate = "start_time")
    Integer startTime;

    /**
     * 结束时间(必填)
     */
    @SerializedName(value = "endTime", alternate = "end_time")
    Integer endTime;
    /**
     * 分页起始值(必填)
     */
    Integer start;

    /**
     * 一次拉取最大值(必填)
     */
    Integer limit;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
