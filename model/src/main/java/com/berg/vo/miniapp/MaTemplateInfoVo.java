package com.berg.vo.miniapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaTemplateInfoVo {

    @ApiModelProperty(value = "添加至帐号下的模板 id，发送小程序订阅消息时所需")
    String priTmplId;
    @ApiModelProperty(value = "模版标题")
    String title;
    @ApiModelProperty(value = "模版内容")
    String content;
    @ApiModelProperty(value = "模板内容示例")
    String example;
    @ApiModelProperty(value = "模版类型，2 为一次性订阅，3 为长期订阅")
    Integer type;
}
