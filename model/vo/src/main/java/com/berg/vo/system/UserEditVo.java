package com.berg.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class UserEditVo {

    @NotNull(message = "用户id不能为空")
    @Min(value = 0,message = "用户id不能小于0")
    @ApiModelProperty(value = "用户id")
    Integer id;
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{1,19}$",message = "用户名必须字母开头，允许2到20个字符，允许字母数字下划线")
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    String username;
    @Size(max = 20, message = "密码长度不能超过20个字符")
    @NotBlank(message = "真实姓名不能为空")
    String realname;
    @ApiModelProperty(value = "所属组织id集合")
    List<Integer> organizationIds;
    @ApiModelProperty(value = "权限id集合")
    List<Integer> roldIds;
    @ApiModelProperty(value = "组件id集合")
    List<Integer> comIds;
}
