package com.berg.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileVo {

    @ApiModelProperty(value = "文件表id")
    Integer id;
    @ApiModelProperty(value = "名称")
    String name;
    @ApiModelProperty(value = "业务编码")
    String code;
    @ApiModelProperty(value = "文件路径")
    String path;
    @ApiModelProperty(value = "文件全路径")
    String fullPath;
    @ApiModelProperty(value = "创建人")
    String createUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    LocalDateTime createTimestamp;
}
