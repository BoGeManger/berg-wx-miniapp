package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaGetDailySummaryVo {

    @ApiModelProperty(value = "日期，格式为 yyyymmdd")
    String refDate;
    @ApiModelProperty(value = "累计用户数")
    Long visitTotal;
    @ApiModelProperty(value = "转发次数")
    Long sharePv;
    @ApiModelProperty(value = "转发人数")
    Long shareUv;
}
