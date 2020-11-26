package com.berg.vo.member.in;

import com.berg.vo.common.PageInVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetMemberPageInVo extends PageInVo {

    @ApiModelProperty(value = "会员手机号码")
    String phone;
}
