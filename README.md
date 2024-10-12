테이블정보

CREATE TABLE `menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `menu_nm` varchar(100) NOT NULL,
  `menu_srt_ord` bigint DEFAULT NULL,
  `menu_up_id` bigint DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
)

CREATE TABLE `program` (
  `program_id` bigint NOT NULL AUTO_INCREMENT,
  `program_nm` varchar(100) NOT NULL,
  `program_url` varchar(100) NOT NULL,
  `use_yn` varchar(100) DEFAULT NULL,
  `program_srt_ord` bigint DEFAULT NULL,
  PRIMARY KEY (`program_id`)
)

CREATE TABLE `menu_program` (
`menu_id` varchar(100) NOT NULL,
`program_id` varchar(100) NOT NULL,
PRIMARY KEY (`menu_id`,`program_id`)
)

CREATE TABLE `user` (
`user_id` varchar(100) NOT NULL,
`user_pw` varchar(100) NOT NULL,
`user_nm` varchar(100) NOT NULL,
PRIMARY KEY (`user_id`)
)

CREATE TABLE `role` (
`role_id` bigint(20) NOT NULL AUTO_INCREMENT,
`role_nm` varchar(100) NOT NULL,
`role_desc` varchar(100) DEFAULT NULL,
PRIMARY KEY (`role_id`)
)

CREATE TABLE `role_user` (
`role_id` bigint NOT NULL,
`user_id` varchar(100) NOT NULL,
PRIMARY KEY (`role_id`,`user_id`)
)

CREATE TABLE `role_program` (
`role_id` bigint NOT NULL,
`program_id` bigint NOT NULL,
PRIMARY KEY (`program_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;