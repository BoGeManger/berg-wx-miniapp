package com.berg.vo.miniapp.in;

import com.berg.vo.miniapp.MaSendTemplateDataVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MaSendTemplateInVo {

    @NotBlank(message = "所需下发的模板消息的id不能为空")
    @ApiModelProperty(value = "所需下发的模板消息的id")
    String templateId;
    @NotBlank(message = "模板卡片后的跳转页面不能为空")
    @ApiModelProperty(value = "模板卡片后的跳转页面")
    String page;
    @ApiModelProperty(value = "跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版")
    String miniprogramState;
    @ApiModelProperty(value = "进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN")
    String lang="zh_CN";
    @Valid
    @NotNull(message = "模板内容不能为空")
    @Size(min = 1, message = "模板内容不能为空")
    @ApiModelProperty(value = "模板内容")
    List<MaSendTemplateDataVo> data;
    @ApiModelProperty(value = "描述")
    String remark;
}
