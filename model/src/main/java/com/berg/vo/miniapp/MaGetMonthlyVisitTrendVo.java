package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaGetMonthlyVisitTrendVo {

    @ApiModelProperty(value = "时间，格式为 yyyymm，如：\"201702\"")
    String refDate;
    @ApiModelProperty(value = "打开次数（自然月内汇总）")
    Long sessionCnt;
    @ApiModelProperty(value = "访问次数（自然月内汇总）")
    Long visitPv;
    @ApiModelProperty(value = "访问人数（自然月内去重）")
    Long visitUv;
    @ApiModelProperty(value = "新用户数（自然月内去重）")
    Long visitUvNew;
    @ApiModelProperty(value = "人均停留时长 (浮点型，单位：秒)")
    Float stayTimeUv;
    @ApiModelProperty(value = "次均停留时长 (浮点型，单位：秒)")
    Float stayTimeSession;
    @ApiModelProperty(value = "平均访问深度 (浮点型)")
    Float visitDepth;
}
