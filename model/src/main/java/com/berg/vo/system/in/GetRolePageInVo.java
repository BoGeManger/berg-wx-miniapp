package com.berg.vo.system.in;

import com.berg.vo.common.PageInVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetRolePageInVo extends PageInVo {

    @ApiModelProperty(value = "角色名称")
    String name;
}
