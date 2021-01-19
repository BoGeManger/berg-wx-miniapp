package com.berg.vo.miniapp.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class MaGetVisitDistributionOutVo {

    @ApiModelProperty(value = "日期，格式为 yyyymmdd")
    String refDate;
    @ApiModelProperty(value = "数据列表")
    Map<String, Map<Integer, Integer>> list;
}
