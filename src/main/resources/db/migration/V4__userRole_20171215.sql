CREATE TABLE `user_role` (
  `user_id` VARCHAR (255) NOT NULL,
  `role_id` char(6) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;