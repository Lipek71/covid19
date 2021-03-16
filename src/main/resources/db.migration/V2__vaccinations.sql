CREATE TABLE `vaccinations` (
	`vaccination_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`citizen_id` BIGINT(20) UNSIGNED NOT NULL,
	`vaccination_date` DATETIME NOT NULL,
	`status` VARCHAR(15) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`note` VARCHAR(200) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`vaccination_type` VARCHAR(20) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`vaccination_id`) USING BTREE,
	INDEX `FK__citizens_citizen_id` (`citizen_id`) USING BTREE,
	CONSTRAINT `FK__citizens_citizen_id` FOREIGN KEY (`citizen_id`) REFERENCES `covid19`.`citizens` (`citizen_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14
;
