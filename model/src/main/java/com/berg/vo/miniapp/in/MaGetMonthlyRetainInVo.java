package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MaGetMonthlyRetainInVo {

    @NotBlank(message = "微信小程序appId不能为空")
    @ApiModelProperty(value = "微信小程序appId")
    String appId;
    @NotBlank(message = "开始日期不能为空")
    @ApiModelProperty(value = "开始日期，为自然月第一天")
    String beginDate;
    @NotBlank(message = "结束日期不能为空")
    @ApiModelProperty(value = "结束日期，为自然月最后一天，限定查询一个月数据")
    String endDate;
}
