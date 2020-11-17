CREATE TABLE `mb_member_tbl` (
  `id` varchar(120) NOT NULL COMMENT '会员表id',
  `phone` varchar(120) NOT NULL COMMENT '会员手机号码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime NOT NULL COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';