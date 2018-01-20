CREATE TABLE `role_function` (
  `role_id` char(6) NOT NULL,
  `function_id` char(6) NOT NULL,
  PRIMARY KEY (`role_id`,`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;