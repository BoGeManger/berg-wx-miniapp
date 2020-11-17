package com.berg.vo.miniapp.out;

import com.berg.vo.miniapp.MaUserInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaLoginOutVo {

    @ApiModelProperty(value = "微信小程序appId")
    String appId;
    @ApiModelProperty(value = "微信小程序openId")
    String openId;
    @ApiModelProperty(value = "微信小程序unionId")
    String unionId;
    @ApiModelProperty(value = "会员id")
    String memberId;
    @ApiModelProperty(value = "会员小程序绑定id")
    Integer maBindId;
    @ApiModelProperty(value = "用户绑定时间")
    LocalDateTime createTime;
    @ApiModelProperty(value = "用户修改时间")
    LocalDateTime modifyTime;
    @ApiModelProperty(value = "用户信息")
    MaUserInfoVo userinfo;
    @ApiModelProperty(value = "微信小程序sessionKey")
    String sessionKey;
    @ApiModelProperty(value = "登录校验")
    String token;
}
