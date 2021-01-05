package com.berg.dao.system.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统路由表
 * </p>
 *
 * @author 
 * @since 2020-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_router_tbl")
public class RouterTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统路由表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 系统组件表id
     */
    private Integer componentId;

    /**
     * 前端绑定组件
     */
    private String component;

    /**
     * 对应路由地址
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 重定向地址, 访问这个路由时,自定进行重定向
     */
    private String redirect;

    /**
     * 控制路由和子路由是否显示在 sidebar(0 否 1 是)
     */
    private Integer hidden;

    /**
     * 强制菜单显示为Item而不是SubItem(配合 meta.hidden)(0 否 1 是)
     */
    @TableField("hide_childrenIn_menu")
    private Integer hideChildreninMenu;

    /**
     * 缓存该路由 (开启 multi-tab 是默认值为 true)(0 否 1 是)
     */
    private Integer keepAlive;

    /**
     * 特殊 隐藏 PageHeader 组件中的页面带的 面包屑和页面标题栏(0 否 1 是)
     */
    private Integer hiddenHeaderContent;


    /**
     * 菜单链接跳转目标（参考 html a 标记）
     */
    private String target;
}
