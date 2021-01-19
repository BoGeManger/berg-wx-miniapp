package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class MaGetUserPortraitItemVo {

    @ApiModelProperty(value = "省份，如北京、广东等")
    Map<String,Long> province;
    @ApiModelProperty(value = "城市，如北京、广州等")
    Map<String,Long> city;
    @ApiModelProperty(value = "性别，包括男、女、未知")
    Map<String,Long> genders;
    @ApiModelProperty(value = "终端类型，包括 iPhone，android，其他")
    Map<String,Long> platforms;
    @ApiModelProperty(value = "机型，如苹果 iPhone 6，OPPO R9 等")
    Map<String,Long> devices;
    @ApiModelProperty(value = "年龄，包括17岁以下、18-24岁等区间")
    Map<String,Long> ages;
}
