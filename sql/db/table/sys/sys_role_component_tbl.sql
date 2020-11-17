CREATE TABLE `sys_role_component_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统角色组件表id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `com_id` int(11) NOT NULL COMMENT '组件id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(20) NOT NULL COMMENT '创建用户',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `del_user` varchar(20) DEFAULT NULL COMMENT '删除用户',
  `isdel` tinyint(5) unsigned zerofill NOT NULL COMMENT '是否删除(0 否,1 是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色组件表';