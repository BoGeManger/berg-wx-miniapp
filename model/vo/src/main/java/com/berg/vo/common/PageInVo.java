package com.berg.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PageInVo {

    @Min(value = 1,message = "当前页码不能小于1")
    @ApiModelProperty(value = "当前页码")
    int pageIndex;
    @Min(value = 1,message = "每页个数不能小于1")
    @ApiModelProperty(value = "每页个数")
    int pageSize;
}
