package com.berg.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVo {

    @ApiModelProperty(value = "用户信息表id")
    Integer id;
    @ApiModelProperty(value = "用户名")
    String username;
    @ApiModelProperty(value = "真实姓名")
    String realname;
    @ApiModelProperty(value = "所属组织表id")
    Integer organizationId;
    @ApiModelProperty(value = "所属组织名称")
    String organizationName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "锁定时间")
    LocalDateTime lockTime;
    @ApiModelProperty(value = "锁定人")
    String lockUser;
    @ApiModelProperty(value = "是否锁定(0 否,1 是)")
    Integer islock;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后登录时间")
    LocalDateTime lastLoginTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    LocalDateTime createTime;
    @ApiModelProperty(value = "创建人")
    String createUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    LocalDateTime modifyTime;
    @ApiModelProperty(value = "更新人")
    String modifyUser;

}
