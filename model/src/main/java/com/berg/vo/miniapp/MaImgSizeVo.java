package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaImgSizeVo {

    @ApiModelProperty(value = "宽")
    Integer w;
    @ApiModelProperty(value = "高")
    Integer h;

}
