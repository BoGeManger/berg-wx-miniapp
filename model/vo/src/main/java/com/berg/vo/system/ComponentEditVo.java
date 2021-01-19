package com.berg.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ComponentEditVo {

    @NotNull(message = "组件id不能为空")
    @Min(value = 0,message = "组件id不能小于0")
    @ApiModelProperty(value = "组件id")
    Integer id;
    @Min(value = 0,message = "父组件id不能小于0")
    @ApiModelProperty(value = "父组件id")
    Integer parentId;
    @Size(max = 100, message = "组件名称长度不能超过100个字符")
    @NotBlank(message = "组件名称不能为空")
    @ApiModelProperty(value = "组件名称")
    String name;
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    @ApiModelProperty(value = "权限标识")
    String perms;
    @Size(max = 255, message = "组件描述不能超过255个字符")
    @ApiModelProperty(value = "组件描述")
    String remark;
    @NotNull(message = "组件类型不能为空")
    @Min(value = 0,message = "组件类型不能小于0")
    @ApiModelProperty(value = "组件类型(0 路由,1 按钮)")
    Integer type;
    @NotNull(message = "排序不能为空")
    @Min(value = 0,message = "排序不能小于0")
    @ApiModelProperty(value = "排序")
    Integer no;
    @ApiModelProperty(value = "路由信息")
    RouterEditVo router;
}
