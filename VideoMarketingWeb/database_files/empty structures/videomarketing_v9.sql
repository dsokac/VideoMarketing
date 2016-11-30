-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `geographic_units`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `geographic_units` ;

CREATE TABLE IF NOT EXISTS `geographic_units` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users` ;

CREATE TABLE IF NOT EXISTS `users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `surname` VARCHAR(100) NULL,
  `birthday` DATE NULL,
  `gender` CHAR NULL COMMENT 'M (male) or F (female)',
  `email` VARCHAR(250) NULL,
  `username` TEXT NULL,
  `password` TEXT NULL,
  `password_changed_at` DATETIME NULL,
  `num_wrong_logins` INT UNSIGNED NULL DEFAULT 0,
  `blocked` TINYINT NULL DEFAULT 0,
  `deactivated` TINYINT NULL DEFAULT 0,
  `geographic_unit` INT UNSIGNED NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  `deblocking_link` TEXT NULL DEFAULT NULL,
  `activation_link` TEXT NULL DEFAULT NULL,
  `activation_sent_at` DATETIME NULL DEFAULT NULL,
  `mobile_number` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_geographic_unit_FK_idx` (`geographic_unit` ASC),
  CONSTRAINT `users_geographic_unit_FK`
    FOREIGN KEY (`geographic_unit`)
    REFERENCES `geographic_units` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `admins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `admins` ;

CREATE TABLE IF NOT EXISTS `admins` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `surname` VARCHAR(100) NULL,
  `username` TEXT NULL,
  `password` TEXT NULL,
  `password_changed_at` DATETIME NULL,
  `num_wrong_logins` INT UNSIGNED NULL DEFAULT 0,
  `blocked` TINYINT NULL DEFAULT 0,
  `superadmin` TINYINT NULL DEFAULT 0,
  `email` VARCHAR(250) NULL,
  `activation_link` TEXT NULL,
  `deblocking_link` TEXT NULL,
  `num_deblocking` INT UNSIGNED NULL DEFAULT 0,
  `activated` TINYINT NULL DEFAULT 0,
  `deleted` TINYINT NULL DEFAULT 0,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL DEFAULT NULL,
  `activation_sent_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telecomm_operators`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `telecomm_operators` ;

CREATE TABLE IF NOT EXISTS `telecomm_operators` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NULL,
  `user_page_url` TEXT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  `code` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telecomm_users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `telecomm_users` ;

CREATE TABLE IF NOT EXISTS `telecomm_users` (
  `user` INT UNSIGNED NOT NULL,
  `telecomm_operator` INT UNSIGNED NOT NULL,
  `points` INT UNSIGNED NULL DEFAULT 0,
  `activated` TINYINT NULL DEFAULT 1,
  `activated_at` DATETIME NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  `spent_points` INT UNSIGNED NULL DEFAULT 0,
  `user_id_remote` VARCHAR(50) NULL,
  PRIMARY KEY (`user`, `telecomm_operator`),
  INDEX `telecomm_user_telecomm_operator_idx` (`telecomm_operator` ASC),
  CONSTRAINT `telecomm_users_user_FK`
    FOREIGN KEY (`user`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `telecomm_users_telecomm_operator`
    FOREIGN KEY (`telecomm_operator`)
    REFERENCES `telecomm_operators` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `videos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `videos` ;

CREATE TABLE IF NOT EXISTS `videos` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL,
  `link` TEXT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  `points` INT UNSIGNED NULL DEFAULT 1,
  `likes` INT UNSIGNED NULL DEFAULT 0,
  `dislikes` INT UNSIGNED NULL DEFAULT 0,
  `sponsored` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tag_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tag_types` ;

CREATE TABLE IF NOT EXISTS `tag_types` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL DEFAULT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tags`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tags` ;

CREATE TABLE IF NOT EXISTS `tags` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `description` VARCHAR(100) NULL,
  `type` INT UNSIGNED NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `tags_typeFK_idx` (`type` ASC),
  CONSTRAINT `tags_typeFK`
    FOREIGN KEY (`type`)
    REFERENCES `tag_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `videotags`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `videotags` ;

CREATE TABLE IF NOT EXISTS `videotags` (
  `video` INT UNSIGNED NOT NULL,
  `tag` INT UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`video`, `tag`),
  INDEX `videotags_tag_FK_idx` (`tag` ASC),
  CONSTRAINT `videotags_tag_FK`
    FOREIGN KEY (`tag`)
    REFERENCES `tags` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `videotags_video_FK`
    FOREIGN KEY (`video`)
    REFERENCES `videos` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telecomm_users_spending_logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `telecomm_users_spending_logs` ;

CREATE TABLE IF NOT EXISTS `telecomm_users_spending_logs` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user` INT UNSIGNED NULL,
  `telecomm_operator` INT UNSIGNED NULL,
  `points_spent` INT UNSIGNED NULL DEFAULT 0,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `telecomm_users_spending_logs_user_FK_idx` (`user` ASC),
  INDEX `telecomm_users_spending_logs_telecomm_operator_FK_idx` (`telecomm_operator` ASC),
  CONSTRAINT `telecomm_users_spending_logs_user_FK`
    FOREIGN KEY (`user`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `telecomm_users_spending_logs_telecomm_operator_FK`
    FOREIGN KEY (`telecomm_operator`)
    REFERENCES `telecomm_operators` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `application_logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `application_logs` ;

CREATE TABLE IF NOT EXISTS `application_logs` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` TINYINT UNSIGNED NULL DEFAULT 3 COMMENT '1-database operations, 2 - login / logout, 3 - other',
  `success` TINYINT NULL DEFAULT 0 COMMENT '-1 - failurte, 0 - neutral, 1 - success',
  `user` INT UNSIGNED NULL,
  `admin` TINYINT NULL DEFAULT 0,
  `ip_address` VARCHAR(250) NULL,
  `script` TEXT NULL,
  `text` TEXT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  `deleted_at` DATETIME NULL DEFAULT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `video_comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `video_comments` ;

CREATE TABLE IF NOT EXISTS `video_comments` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `author` INT UNSIGNED NULL,
  `video` INT UNSIGNED NULL,
  `content` TEXT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `video_comments_author_FK_idx` (`author` ASC),
  INDEX `video_comments_video_FK_idx` (`video` ASC),
  CONSTRAINT `video_comments_author_FK`
    FOREIGN KEY (`author`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `video_comments_video_FK`
    FOREIGN KEY (`video`)
    REFERENCES `videos` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `video_views`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `video_views` ;

CREATE TABLE IF NOT EXISTS `video_views` (
  `user` INT UNSIGNED NOT NULL,
  `video` INT UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `count` INT UNSIGNED NULL DEFAULT 1,
  `videolike` TINYINT NULL DEFAULT 0 COMMENT '-1 = dislike, 0 = neutral, 1 = like',
  `deleted_at` DATETIME NULL DEFAULT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`user`, `video`),
  INDEX `video_views_user_FK_idx` (`user` ASC),
  INDEX `video_views_video_FK_idx` (`video` ASC),
  CONSTRAINT `video_views_user_FK`
    FOREIGN KEY (`user`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `video_views_video_FK`
    FOREIGN KEY (`video`)
    REFERENCES `videos` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pin_offers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pin_offers` ;

CREATE TABLE IF NOT EXISTS `pin_offers` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `views_goal` INT UNSIGNED NULL,
  `time_interval` INT UNSIGNED NULL COMMENT 'Initially represents number of days... IT should be calculated on server side.',
  `sponsor` VARCHAR(150) NULL COMMENT 'Name of sponsor which sponosored its video.',
  `active` TINYINT NULL DEFAULT 0,
  `start_time` DATETIME NULL DEFAULT NULL,
  `end_time` DATETIME NULL DEFAULT NULL,
  `cancelled` TINYINT NULL DEFAULT 0,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL DEFAULT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pinned_videos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pinned_videos` ;

CREATE TABLE IF NOT EXISTS `pinned_videos` (
  `video` INT UNSIGNED NOT NULL,
  `pin_offer` INT UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL DEFAULT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`video`, `pin_offer`),
  INDEX `pinned_video_pinned_offer_idx` (`pin_offer` ASC),
  CONSTRAINT `pinned_videos_video_FK`
    FOREIGN KEY (`video`)
    REFERENCES `videos` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `pinned_video_pin_offer_FK`
    FOREIGN KEY (`pin_offer`)
    REFERENCES `pin_offers` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
