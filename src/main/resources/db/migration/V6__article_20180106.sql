CREATE TABLE `article` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `text` longtext NOT NULL,
  `nature` varchar(255) NOT NULL COMMENT '原创 | 转载 | 翻译',
  `labels` varchar(255) DEFAULT NULL,
  `is_private` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否公开',
  `type` varchar(255) NOT NULL,
  `is_anonymous` tinyint(4) NOT NULL DEFAULT '0' COMMENT '匿名发布',
  `sort_id` int(11) DEFAULT '0' COMMENT '排列序号',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览次数',
  `can_comment` tinyint(4) DEFAULT '1' COMMENT '是否可以评论',
  `status` varchar(255) NOT NULL COMMENT '1:草稿 2：审批中 3：已审批 4：已删除',
  `creater` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

