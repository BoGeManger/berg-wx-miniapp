package com.berg.vo.miniapp.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaUserPhoneOutVo {

    @ApiModelProperty(value = "用户绑定的手机号")
    String phone;
}
