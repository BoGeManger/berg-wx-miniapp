package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaGetDailyVisitTrendVo {

    @ApiModelProperty(value = "日期，格式为 yyyymmdd")
    String refDate;
    @ApiModelProperty(value = "打开次数")
    Long sessionCnt;
    @ApiModelProperty(value = "访问次数")
    Long visitPv;
    @ApiModelProperty(value = "访问人数")
    Long visitUv;
    @ApiModelProperty(value = "新用户数")
    Long visitUvNew;
    @ApiModelProperty(value = "人均停留时长 (浮点型，单位：秒)")
    Float stayTimeUv;
    @ApiModelProperty(value = "次均停留时长 (浮点型，单位：秒)")
    Float stayTimeSession;
    @ApiModelProperty(value = "平均访问深度 (浮点型)")
    Float visitDepth;
}
