package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MaMsgSecCheckInVo {

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "内容")
    String content;
}
