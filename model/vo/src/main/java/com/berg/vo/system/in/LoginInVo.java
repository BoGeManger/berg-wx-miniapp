package com.berg.vo.system.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginInVo {

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{1,19}$",message = "用户名必须字母开头，允许2到20个字符，允许字母数字下划线")
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    String username;
    @Size(max = 20, message = "密码长度不能超过20个字符")
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    String password;
}
