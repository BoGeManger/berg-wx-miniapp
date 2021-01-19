package com.berg.vo.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaBindVo {

    @ApiModelProperty(value = "会员小程序绑定表id")
    Integer id;
    @ApiModelProperty(value = "微信小程序appId")
    String appId;
    @ApiModelProperty(value = "微信开放平台unionId")
    String unionId;
    @ApiModelProperty(value = "微信小程序openId")
    String openId;
    @ApiModelProperty(value = "会员表id")
    String memberId;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    LocalDateTime createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    LocalDateTime modifyTime;
}
