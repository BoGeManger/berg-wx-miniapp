package com.berg.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OperatorBatchOrganizationVo extends OrganizationEditVo{

    @ApiModelProperty(value = "子组织集合")
    List<OperatorBatchOrganizationVo> childs;
}
