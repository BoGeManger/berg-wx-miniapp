package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class MaCreateQRCodeInVo {

    @Size(max = 128, message = "扫码进入的小程序页面路径不能超过128个字符")
    @NotBlank(message = "扫码进入的小程序页面路径不能为空")
    @ApiModelProperty(value = "扫码进入的小程序页面路径，最大长度 128 字节，不能为空")
    String path;
    @Min(value = 280,message = "二维码的宽度不能小于280")
    @Max(value = 1280,message = "二维码的宽度不能大于1280")
    @NotNull(message = "二维码的宽度不能为空")
    @ApiModelProperty(value = "二维码的宽度，单位 px。最小 280px，最大 1280px")
    Integer width=280;
}
