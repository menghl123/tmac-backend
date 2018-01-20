CREATE TABLE `comment` (
  `id` varchar(255) NOT NULL,
  `text` text NOT NULL,
  `dest_id` varchar(255) NOT NULL,
  `floor` int(11) NOT NULL DEFAULT '1',
  `extra` varchar(255) DEFAULT NULL,
  `comment_type` varchar(255) NOT NULL,
  `creater` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
