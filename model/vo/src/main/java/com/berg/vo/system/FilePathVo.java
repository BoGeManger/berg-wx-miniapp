package com.berg.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FilePathVo {

    @ApiModelProperty(value = "文件名称")
    String name;
    @ApiModelProperty(value = "路径")
    String path;
    @ApiModelProperty(value = "全路径")
    String fullPath;
}
