package com.berg.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class JobEditVo {

    @Min(value = 0,message = "角色id不能小于0")
    @ApiModelProperty(value = "系统定时任务表id")
    Integer id;
    @Size(max = 100, message = "任务名称长度不能超过100个字符")
    @NotBlank(message = "任务名称不能为空")
    @ApiModelProperty(value = "任务名称")
    String name;
    @ApiModelProperty(value = "任务描述")
    String remark;
    @Size(max = 255, message = "任务类名长度不能超过255个字符")
    @NotBlank(message = "任务类名不能为空")
    @ApiModelProperty(value = "任务类名")
    String jobClassName;
    @Size(max = 255, message = "cron表达式长度不能超过255个字符")
    @NotBlank(message = "cron表达式不能为空")
    @ApiModelProperty(value = "cron表达式")
    String cronExpression;
    @Size(max = 255, message = "参数长度不能超过255个字符")
    @ApiModelProperty(value = "参数")
    String parameter;
}
