package com.berg.dao.system.sys.entity;

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
 * 系统用户信息表
 * </p>
 *
 * @author 
 * @since 2020-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_tbl")
public class UserTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统用户信息表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 所属组织id
     */
    private Integer organizationId;

    /**
     * 所属组织名称
     */
    private String organizationName;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 锁定时间
     */
    private LocalDateTime lockTime;

    /**
     * 锁定人
     */
    private String lockUser;

    /**
     * 是否锁定(0 否,1 是)
     */
    private Integer islock;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;

    /**
     * 修改用户
     */
    private String modifyUser;

    /**
     * 删除时间
     */
    private LocalDateTime delTime;

    /**
     * 删除用户
     */
    private String delUser;

    /**
     * 是否删除(0 否,1 是)
     */
    private Integer isdel;


}
