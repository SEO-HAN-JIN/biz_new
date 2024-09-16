테이블정보

CREATE TABLE `menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `menu_nm` varchar(100) NOT NULL,
  `menu_srt_ord` bigint DEFAULT NULL,
  `menu_up_id` bigint DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `program` (
  `program_id` bigint NOT NULL AUTO_INCREMENT,
  `program_nm` varchar(100) NOT NULL,
  `program_url` varchar(100) NOT NULL,
  `use_yn` varchar(100) DEFAULT NULL,
  `program_srt_ord` bigint DEFAULT NULL,
  PRIMARY KEY (`program_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `menu_program` (
  `menu_id` varchar(100) DEFAULT NULL,
  `program_id` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
