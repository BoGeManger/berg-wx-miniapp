package com.berg.vo.miniapp.out;

import com.berg.vo.miniapp.MaGetUserPortraitItemVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaGetUserPortraitOutVo {

    @ApiModelProperty(value = "时间范围，如：\"20170611-20170617\"")
    String refDate;
    @ApiModelProperty(value = "新用户画像")
    MaGetUserPortraitItemVo visitUvNew;
    @ApiModelProperty(value = "活跃用户画像")
    MaGetUserPortraitItemVo visitUv;

}
