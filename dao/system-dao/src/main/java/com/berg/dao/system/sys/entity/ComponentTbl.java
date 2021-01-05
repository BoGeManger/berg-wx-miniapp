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
 * 系统组件表
 * </p>
 *
 * @author
 * @since 2020-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_component_tbl")
public class ComponentTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统组件表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父组件id
     */
    private Integer parentId;

    /**
     * 组件名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 组件描述
     */
    private String remark;

    /**
     * 组件类型(0=菜单,1=按钮)
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer no;

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
