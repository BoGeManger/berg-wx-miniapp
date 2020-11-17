CREATE TABLE `sys_quartz_job_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统定时任务表id',
  `name` varchar(100) NOT NULL COMMENT '任务名称',
  `remark` text COMMENT '任务描述',
  `job_class_name` varchar(255) NOT NULL COMMENT '任务类名',
  `cron_expression` varchar(255) NOT NULL COMMENT 'cron表达式',
  `parameter` varchar(255) DEFAULT NULL COMMENT '参数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(20) NOT NULL COMMENT '创建用户',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
  `modify_user` varchar(20) NOT NULL COMMENT '修改用户',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `del_user` varchar(20) DEFAULT NULL COMMENT '删除用户',
  `isdel` tinyint(5) unsigned zerofill NOT NULL COMMENT '是否删除(0 否,1 是)',
  `status` int(11) NOT NULL COMMENT '状态(0 进行中,1 已暂停)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统定时任务表';