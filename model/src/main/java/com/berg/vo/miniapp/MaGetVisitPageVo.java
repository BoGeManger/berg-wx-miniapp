package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaGetVisitPageVo {

    @ApiModelProperty(value = "页面路径")
    String pagePath;
    @ApiModelProperty(value = "访问次数")
    Long pageVisitPv;
    @ApiModelProperty(value = "访问人数")
    Long pageVisitUv;
    @ApiModelProperty(value = "次均停留时长")
    Float pageStayTimePv;
    @ApiModelProperty(value = "进入页次数")
    Long entryPagePv;
    @ApiModelProperty(value = "退出页次数")
    Long exitPagePv;
    @ApiModelProperty(value = "转发次数")
    Long pageSharePv;
    @ApiModelProperty(value = "转发人数")
    Long pageShareUv;
}
