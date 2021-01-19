package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaScanQRCodeResultVo {

    @ApiModelProperty(value = "typeName")
    String typeName;
    @ApiModelProperty(value = "data")
    String data;
    @ApiModelProperty(value = "pos")
    MaPosVo pos;
}
