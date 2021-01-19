package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class MaQRCodeGetInVo {

    @Size(max = 128, message = "扫码进入的小程序页面路径不能超过128个字符")
    @NotBlank(message = "扫码进入的小程序页面路径不能为空")
    @ApiModelProperty(value = "扫码进入的小程序页面路径，最大长度 128 字节，不能为空")
    String path;
    @Min(value = 280,message = "二维码的宽度不能小于280")
    @Max(value = 1280,message = "二维码的宽度不能大于1280")
    @NotNull(message = "二维码的宽度不能为空")
    @ApiModelProperty(value = "二维码的宽度，单位 px。最小 280px，最大 1280px")
    Integer width=280;
    @ApiModelProperty(value = "自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调")
    Boolean autoColor=false;
    @ApiModelProperty(value = "auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {\"r\":\"xxx\",\"g\":\"xxx\",\"b\":\"xxx\"} 十进制表示")
    String lineColor="{\"r\":0,\"g\":0,\"b\":0}";
    @ApiModelProperty(value = "是否需要透明底色，为 true 时，生成透明底色的小程序码")
    Boolean ishyaline=false;
}
