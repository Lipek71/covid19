CREATE TABLE `cities` (
	`city_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`zip` CHAR(4) NOT NULL COLLATE 'utf8_general_ci',
	`city` VARCHAR(40) NOT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`city_id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
