package com.berg.vo.system.in;

import com.berg.vo.common.PageInVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetJobPageInVo extends PageInVo {

    @ApiModelProperty(value = "任务名称")
    String name;
    @ApiModelProperty(value = "任务类名")
    String jobClassName;
    @ApiModelProperty(value = "状态(-1 全部,0 进行中,1 已暂停)")
    Integer status=-1;
}
