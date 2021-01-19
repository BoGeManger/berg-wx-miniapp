package com.berg.vo.miniapp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MsgRecordVo {

    @ApiModelProperty(value = "小程序模板消息记录表id")
    Integer id;
    @ApiModelProperty(value = "微信小程序openId")
    String openId;
    @ApiModelProperty(value = "模板消息内容")
    String data;
    @ApiModelProperty(value = "模板消息id")
    String templateId;
    @ApiModelProperty(value = "模板消息跳转页面")
    String page;
    @ApiModelProperty(value = "跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版")
    String miniappState;
    @ApiModelProperty(value = "语言类型")
    String lang;
    @ApiModelProperty(value = "描述")
    String remark;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    LocalDateTime createTime;
}
