package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MaUserPhoneInVo {

    @NotBlank(message = "校验用户签名不能为空")
    @ApiModelProperty(value = "校验用户签名")
    String signature;
    @NotBlank(message = "计算签名原始数据字符串不能为空")
    @ApiModelProperty(value = "计算签名原始数据字符串")
    String rawData;
    @NotBlank(message = "完整用户信息的加密数据不能为空")
    @ApiModelProperty(value = "完整用户信息的加密数据")
    String encryptedData;
    @NotBlank(message = "加密算法的初始向量不能为空")
    @ApiModelProperty(value = "加密算法的初始向量")
    String iv;
}
