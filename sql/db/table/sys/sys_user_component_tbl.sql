CREATE TABLE `sys_user_component_tbl` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统用户组件表id',
`user_id` int(11) NOT NULL COMMENT '用户id',
`com_id` int(11) NOT NULL COMMENT '组件id',
`create_time` datetime NOT NULL COMMENT '创建时间',
`create_user` varchar(20) NOT NULL COMMENT '创建用户',
`del_time` datetime DEFAULT NULL COMMENT '删除时间',
`del_user` varchar(20) DEFAULT NULL COMMENT '删除用户',
`isdel` tinyint(5) unsigned zerofill NOT NULL COMMENT '是否删除(0 否,1 是)',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='系统用户组件表';