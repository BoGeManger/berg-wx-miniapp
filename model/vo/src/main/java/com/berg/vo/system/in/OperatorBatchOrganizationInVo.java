package com.berg.vo.system.in;

import com.berg.vo.system.OperatorBatchOrganizationVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OperatorBatchOrganizationInVo {

    @ApiModelProperty(value = "删除组织id集合")
    List<Integer> delIds;
    @Size(min = 1, message = "组织集合不能小于1")
    @ApiModelProperty(value = "组织集合")
    List<OperatorBatchOrganizationVo> organizations;
}
