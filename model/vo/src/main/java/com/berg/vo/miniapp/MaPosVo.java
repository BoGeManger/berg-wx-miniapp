package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaPosVo {

    @ApiModelProperty(value = "leftTop")
    MaCoordinateVo leftTop;
    @ApiModelProperty(value = "rightTop")
    MaCoordinateVo rightTop;
    @ApiModelProperty(value = "rightBottom")
    MaCoordinateVo rightBottom;
    @ApiModelProperty(value = "leftBottom")
    MaCoordinateVo leftBottom;
}
