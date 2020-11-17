package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaAiCropResultVo {

    @ApiModelProperty(value = "top")
    Integer cropTop;
    @ApiModelProperty(value = "bottom")
    Integer cropBottom;
    @ApiModelProperty(value = "left")
    Integer cropLeft;
    @ApiModelProperty(value = "right")
    Integer cropRight;
}
