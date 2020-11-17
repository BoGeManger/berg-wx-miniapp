package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaCoordinateVo {

    @ApiModelProperty(value = "x")
    Integer x;
    @ApiModelProperty(value = "y")
    Integer y;
}
