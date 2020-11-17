package com.berg.vo.miniapp.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class MaGetDailyRetainOutVo {

    @ApiModelProperty(value = "日期")
    String refDate;
    @ApiModelProperty(value = "新增用户留存(key:标识，0开始，表示当天，1表示1天后。依此类推，key取值分别是：0,1,2,3,4,5,6,7,14,30;value:key对应日期的新增用户数/活跃用户数（key=0时）或留存用户数（k>0时）)")
    Map<Integer, Integer> visitUvNew;
    @ApiModelProperty(value = "活跃用户留存(key:标识，0开始，表示当天，1表示1天后。依此类推，key取值分别是：0,1,2,3,4,5,6,7,14,30;value:key对应日期的新增用户数/活跃用户数（key=0时）或留存用户数（k>0时）)")
    Map<Integer,Integer> visitUv;
}
