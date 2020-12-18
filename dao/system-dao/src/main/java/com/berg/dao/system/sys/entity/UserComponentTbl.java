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
 * 系统用户组件表
 * </p>
 *
 * @author 
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_component_tbl")
public class UserComponentTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统用户组件表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 组件id
     */
    private Integer comId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建用户
     */
    private String createUser;

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
