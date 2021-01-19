package com.berg.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComponentVo {

    @ApiModelProperty(value = "系统组件表id")
    Integer id;
    @ApiModelProperty(value = "父组件id")
    Integer parentId;
    @ApiModelProperty(value = "组件名称")
    String name;
    @ApiModelProperty(value = "权限标识")
    String perms;
    @ApiModelProperty(value = "组件描述")
    String remark;
    @ApiModelProperty(value = "组件类型(0=菜单,1=按钮)")
    Integer type;
    @ApiModelProperty(value = "排序")
    Integer no;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    LocalDateTime createTime;
    @ApiModelProperty(value = "创建用户")
    String createUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    LocalDateTime modifyTime;
    @ApiModelProperty(value = "修改用户")
    String modifyUser;
}
