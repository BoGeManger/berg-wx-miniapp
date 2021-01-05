CREATE TABLE `sys_file_tbl` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统文件表id',
`name` varchar(255) DEFAULT NULL COMMENT '名称',
`code` varchar(255) DEFAULT NULL COMMENT '业务编码',
`path` text NOT NULL COMMENT '文件路径',
`full_path` text NOT NULL COMMENT '文件全路径',
`type` int(11) DEFAULT NULL COMMENT '类型',
`status` tinyint(5) NOT NULL COMMENT '状态(0 正在上传 1 已上传)',
`create_time` datetime NOT NULL COMMENT '创建时间',
`create_user` varchar(20) NOT NULL COMMENT '创建用户',
`modify_time` datetime NOT NULL COMMENT '更新时间',
`modify_user` varchar(20) NOT NULL COMMENT '修改用户',
`del_time` datetime DEFAULT NULL COMMENT '删除时间',
`del_user` varchar(20) DEFAULT NULL COMMENT '删除用户',
`isdel` tinyint(5) unsigned zerofill NOT NULL COMMENT '是否删除(0 否,1 是)',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统文件表';