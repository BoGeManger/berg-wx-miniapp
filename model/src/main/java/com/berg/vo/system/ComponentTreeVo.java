package com.berg.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ComponentTreeVo {

    @ApiModelProperty(value = "组件id")
    Integer id;
    @ApiModelProperty(value = "父组件id")
    Integer parentId;
    @ApiModelProperty(value = "组件名称")
    String name;
    @ApiModelProperty(value = "权限标识")
    String perms;
    @ApiModelProperty(value = "对应路由地址")
    String path;
    @ApiModelProperty(value = "图标")
    String icon;
    @ApiModelProperty(value = "组件描述")
    String remark;
    @ApiModelProperty(value = "组件类型(0=菜单,1=按钮)")
    Integer type;
    @ApiModelProperty(value = "子组件集合")
    List<ComponentTreeVo> childs;
}
