package com.berg.vo.miniapp.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaSetUserInfoInVo {

    @ApiModelProperty(value = "昵称")
    String nickName;
    @ApiModelProperty(value = "头像链接")
    String avatarUrl;
    @ApiModelProperty(value = "性别")
    Integer gender;
    @ApiModelProperty(value = "国家")
    String country;
    @ApiModelProperty(value = "省")
    String province;
    @ApiModelProperty(value = "市")
    String city;
    @ApiModelProperty(value = "显示country,province,city所用的语言(en,zh_CN,zh_TW)")
    String language;
}
