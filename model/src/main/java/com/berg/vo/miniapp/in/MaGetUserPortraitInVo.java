package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MaGetUserPortraitInVo {

    @NotBlank(message = "微信小程序appId不能为空")
    @ApiModelProperty(value = "微信小程序appId")
    String appId;
    @NotBlank(message = "开始日期不能为空")
    @ApiModelProperty(value = "开始日期。格式为 yyyymmdd")
    String beginDate;
    @NotBlank(message = "结束日期不能为空")
    @ApiModelProperty(value = "结束日期，开始日期与结束日期相差的天数限定为0/6/29，分别表示查询最近1/7/30天数据，允许设置的最大值为昨日。格式为 yyyymmdd")
    String endDate;
}
