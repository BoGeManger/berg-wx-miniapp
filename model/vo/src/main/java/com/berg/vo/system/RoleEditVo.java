package com.berg.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RoleEditVo {

    @NotNull(message = "角色id不能为空")
    @Min(value = 0,message = "角色id不能小于0")
    @ApiModelProperty(value = "角色id")
    Integer id;
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 100, message = "角色名称长度不能超过100个字符")
    @ApiModelProperty(value = "角色名称")
    String name;
    @Size(max = 255, message = "角色描述长度不能超过255个字符")
    @ApiModelProperty(value = "角色描述")
    String remark;
    @ApiModelProperty(value = "组件id集合")
    List<Integer> comIds;
}
