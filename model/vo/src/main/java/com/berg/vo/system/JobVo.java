package com.berg.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobVo {
    
    @ApiModelProperty(value = "系统定时任务表id")
    Integer id;
    @ApiModelProperty(value = "任务名称")
    String name;
    @ApiModelProperty(value = "任务描述")
    String remark;
    @ApiModelProperty(value = "任务类名")
    String jobClassName;
    @ApiModelProperty(value = "cron表达式")
    String cronExpression;
    @ApiModelProperty(value = "参数")
    String parameter;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    LocalDateTime createTime;
    @ApiModelProperty(value = "创建用户")
    String createUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    LocalDateTime modifyTime;
    @ApiModelProperty(value = "修改用户")
    String modifyUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "删除时间")
    LocalDateTime delTime;
    @ApiModelProperty(value = "删除用户")
    String delUser;
    @ApiModelProperty(value = "是否删除(0 否,1 是)")
    Integer isdel;
    @ApiModelProperty(value = "状态(0 进行中,1 已暂停)")
    Integer status;
}
