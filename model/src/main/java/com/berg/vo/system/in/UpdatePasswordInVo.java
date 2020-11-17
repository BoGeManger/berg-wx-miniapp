package com.berg.vo.system.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdatePasswordInVo {

    @Min(value = 0,message = "用户id不能小于0")
    @ApiModelProperty(value = "用户id")
    Integer id;
    @Size(max = 20, message = "密码长度不能超过20个字符")
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    String password;
}
