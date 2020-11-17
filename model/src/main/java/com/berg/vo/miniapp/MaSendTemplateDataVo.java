package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MaSendTemplateDataVo {

    @NotBlank(message = "name不能为空")
    @ApiModelProperty(value = "name")
    String name;
    @ApiModelProperty(value = "value")
    @NotBlank(message = "value不能为空")
    String value;
}
