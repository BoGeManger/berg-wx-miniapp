package com.berg.vo.system.out;

import com.berg.vo.system.UserVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class LoginOutVo {

    @ApiModelProperty(value = "授权校验")
    String token;
    @ApiModelProperty(value = "过期时间戳")
    String exipreTime;
    @ApiModelProperty(value = "用户角色集合")
    Set<String> roles;
    @ApiModelProperty(value = "用户权限集合")
    Set<String> permissions;
    @ApiModelProperty(value = "用户信息")
    UserVo user;
}
