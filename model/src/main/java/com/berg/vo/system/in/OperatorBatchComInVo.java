package com.berg.vo.system.in;

import com.berg.vo.system.OperatorBatchComVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OperatorBatchComInVo {

    @ApiModelProperty(value = "删除组件id集合")
    List<Integer> delIds;
    @Size(min = 1, message = "组件集合不能小于1")
    @ApiModelProperty(value = "组件集合")
    List<OperatorBatchComVo> coms;
}
