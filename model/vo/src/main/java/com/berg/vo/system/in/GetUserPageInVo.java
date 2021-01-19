package com.berg.vo.system.in;

import com.berg.vo.common.PageInVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetUserPageInVo extends PageInVo {

    @ApiModelProperty(value = "用户名")
    String username;
}
