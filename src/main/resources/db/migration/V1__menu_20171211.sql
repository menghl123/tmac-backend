CREATE TABLE `menu` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) NOT NULL,
  `sort_id` int(11) NOT NULL,
  `function_id` char(6) NOT NULL,
  `creater` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `function` (
  `id` char(6) NOT NULL,
  `name` varchar(255) NOT NULL,
  `creater` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;