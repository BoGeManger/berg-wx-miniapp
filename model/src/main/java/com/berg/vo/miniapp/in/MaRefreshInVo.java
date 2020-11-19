package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MaRefreshInVo {

    @ApiModelProperty(value = "微信小程序code(sessionKey过期或需要获取微信最新信息时必填)")
    String code;
}
