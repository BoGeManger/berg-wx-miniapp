CREATE TABLE `sys_user_tbl` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统用户信息表id',
`username` varchar(20) NOT NULL COMMENT '账号',
`password` varchar(100) NOT NULL COMMENT '密码',
`realname` varchar(20) DEFAULT NULL COMMENT '真实姓名',
`last_login_time` datetime NOT NULL COMMENT '最后登录时间',
`lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
`lock_user` varchar(20) DEFAULT NULL COMMENT '锁定人',
`islock` tinyint(5) NOT NULL COMMENT '是否锁定(0 否,1 是)',
`create_time` datetime NOT NULL COMMENT '创建时间',
`create_user` varchar(20) NOT NULL COMMENT '创建用户',
`modify_time` datetime NOT NULL COMMENT '更新时间',
`modify_user` varchar(20) NOT NULL COMMENT '修改用户',
`del_time` datetime DEFAULT NULL COMMENT '删除时间',
`del_user` varchar(20) DEFAULT NULL COMMENT '删除用户',
`isdel` tinyint(5) unsigned zerofill NOT NULL COMMENT '是否删除(0 否,1 是)',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统用户信息表';