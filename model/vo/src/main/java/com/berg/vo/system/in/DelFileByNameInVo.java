package com.berg.vo.system.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DelFileByNameInVo {

    @NotBlank(message = "名称为空")
    @ApiModelProperty(value = "名称")
    String name;
}
