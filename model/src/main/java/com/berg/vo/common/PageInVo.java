package com.berg.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageInVo {
    @ApiModelProperty(value = "当前页码")
    int pageIndex;

    @ApiModelProperty(value = "每页个数")
    int pageSize;
}
