CREATE TABLE `mb_ma_bind_tbl` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员小程序绑定表id',
`app_id` varchar(120) NOT NULL COMMENT '微信小程序appId',
`union_id` varchar(120) DEFAULT NULL COMMENT '微信开放平台unionId',
`open_id` varchar(120) NOT NULL COMMENT '微信小程序openId',
`member_id` varchar(120) DEFAULT NULL COMMENT '会员表id',
`nick_name` varchar(120) DEFAULT NULL COMMENT '昵称',
`avatar_url` varchar(255) DEFAULT NULL COMMENT '头像链接',
`gender` tinyint(5) DEFAULT NULL COMMENT '性别',
`country` varchar(20) DEFAULT NULL COMMENT '国家',
`province` varchar(20) DEFAULT NULL COMMENT '省',
`city` varchar(20) DEFAULT NULL COMMENT '市',
`language` varchar(10) DEFAULT NULL COMMENT '显示country,province,city所用的语言(en,zh_CN,zh_TW)',
`create_time` datetime NOT NULL COMMENT '创建时间',
`modify_time` datetime NOT NULL COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员小程序绑定表';