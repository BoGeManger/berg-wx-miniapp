CREATE TABLE `ma_msg_record_tbl` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '小程序模板消息记录表id',
`app_id` varchar(120) NOT NULL COMMENT '微信小程序appId',
`open_id` varchar(120) NOT NULL COMMENT '微信小程序openId',
`data` text NOT NULL COMMENT '模板消息内容',
`template_id` varchar(120) NOT NULL COMMENT '模板消息id',
`page` varchar(255) DEFAULT NULL COMMENT '模板消息跳转页面',
`miniapp_state` varchar(20) DEFAULT NULL COMMENT '跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版',
`lang` varchar(10) DEFAULT NULL COMMENT '语言类型',
`create_time` datetime NOT NULL COMMENT '创建时间',
`remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序模板消息记录表';