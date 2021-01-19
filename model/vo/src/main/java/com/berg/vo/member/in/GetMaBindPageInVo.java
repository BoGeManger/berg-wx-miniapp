package com.berg.vo.member.in;

import com.berg.vo.common.PageInVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetMaBindPageInVo extends PageInVo {

    @ApiModelProperty(value = "微信小程序appId")
    String appId;
    @ApiModelProperty(value = "昵称")
    String nickName;
}
