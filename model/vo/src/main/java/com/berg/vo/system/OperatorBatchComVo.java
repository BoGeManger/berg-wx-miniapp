package com.berg.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OperatorBatchComVo extends ComponentEditVo{

    @ApiModelProperty(value = "子组件集合")
    List<OperatorBatchComVo> childs;
}
