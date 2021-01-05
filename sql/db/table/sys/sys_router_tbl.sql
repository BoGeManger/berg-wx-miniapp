CREATE TABLE `sys_router_tbl` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统路由表id',
`component_id` int(11) NOT NULL COMMENT '系统组件表id',
`component` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '前端绑定组件',
`path` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '对应路由地址',
`icon` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '图标',
`redirect` varchar(255) DEFAULT NULL COMMENT '重定向地址, 访问这个路由时,自定进行重定向',
`hidden` tinyint(5) DEFAULT '0' COMMENT '控制路由和子路由是否显示在 sidebar(0 否 1 是)',
`hide_childrenIn_menu` tinyint(5) DEFAULT '0' COMMENT '强制菜单显示为Item而不是SubItem(配合 meta.hidden)(0 否 1 是)',
`keep_alive` tinyint(5) DEFAULT '1' COMMENT '缓存该路由 (开启 multi-tab 是默认值为 true)(0 否 1 是)',
`hidden_header_content` tinyint(5) DEFAULT '0' COMMENT '特殊 隐藏 PageHeader 组件中的页面带的 面包屑和页面标题栏(0 否 1 是)',
`target` varchar(255) DEFAULT NULL COMMENT '菜单链接跳转目标（参考 html a 标记）',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统路由表';