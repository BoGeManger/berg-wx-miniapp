package com.berg.dao.system.mb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会员小程序绑定表
 * </p>
 *
 * @author 
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mb_ma_bind_tbl")
public class MaBindTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员小程序绑定表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 微信小程序appId
     */
    private String appId;

    /**
     * 微信开放平台unionId
     */
    private String unionId;

    /**
     * 微信小程序openId
     */
    private String openId;

    /**
     * 会员表id
     */
    private String memberId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 显示country,province,city所用的语言(en,zh_CN,zh_TW)
     */
    private String language;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;


}
