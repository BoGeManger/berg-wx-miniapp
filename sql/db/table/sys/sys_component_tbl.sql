CREATE TABLE `sys_component_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统组件表id',
  `parent_id` int(11) NOT NULL COMMENT '父组件id',
  `name` varchar(100) NOT NULL COMMENT '组件名称',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `path` varchar(255) DEFAULT NULL COMMENT '对应路由地址',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `remark` varchar(255) COMMENT '组件描述',
  `type` int(11) NOT NULL COMMENT '组件类型(0=菜单,1=按钮)',
  `no` int(11) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(20) NOT NULL COMMENT '创建用户',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  `modify_user` varchar(20) NOT NULL COMMENT '修改用户',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `del_user` varchar(20) DEFAULT NULL COMMENT '删除用户',
  `isdel` tinyint(5) unsigned zerofill NOT NULL COMMENT '是否删除(0 否,1 是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统组件表';