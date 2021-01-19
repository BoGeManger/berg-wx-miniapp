package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MaDelTemplateCacheInVo {

    @NotBlank(message = "微信小程序appId不能为空")
    @ApiModelProperty(value = "微信小程序appId")
    String appId;

}
