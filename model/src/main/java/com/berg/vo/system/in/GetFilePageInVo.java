package com.berg.vo.system.in;

import com.berg.vo.common.PageInVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetFilePageInVo extends PageInVo {

    @ApiModelProperty(value = "名称")
    String name;
    @ApiModelProperty(value = "编码")
    String code;
    @ApiModelProperty(value = "类型(0 模板文件 1 其他 -1 全部)")
    Integer type;
}
