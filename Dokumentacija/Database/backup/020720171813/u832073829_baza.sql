
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Računalo: localhost
-- Vrijeme generiranja: Srp 02, 2017 u 05:15 PM
-- Verzija poslužitelja: 10.1.24-MariaDB
-- PHP verzija: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza podataka: `u832073829_baza`
--

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `admins`
--

DROP TABLE IF EXISTS `admins`;
CREATE TABLE IF NOT EXISTS `admins` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(100) DEFAULT NULL,
  `username` text,
  `password` text,
  `password_changed_at` datetime DEFAULT NULL,
  `num_wrong_logins` int(10) unsigned DEFAULT '0',
  `blocked` tinyint(4) DEFAULT '0',
  `superadmin` tinyint(4) DEFAULT '0',
  `email` varchar(250) DEFAULT NULL,
  `activation_link` text,
  `deblocking_link` text,
  `num_deblocking` int(10) unsigned DEFAULT '0',
  `activated` tinyint(4) DEFAULT '0',
  `deleted` tinyint(4) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `activation_send_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `application_logs`
--

DROP TABLE IF EXISTS `application_logs`;
CREATE TABLE IF NOT EXISTS `application_logs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(3) unsigned DEFAULT '3' COMMENT '1-database operations, 2 - login / logout, 3 - other',
  `success` tinyint(4) DEFAULT '0' COMMENT '-1 - failurte, 0 - neutral, 1 - success',
  `user` int(10) unsigned DEFAULT NULL,
  `admin` tinyint(4) DEFAULT '0',
  `ip_address` varchar(250) DEFAULT NULL,
  `script` text,
  `text` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `application_settings`
--

DROP TABLE IF EXISTS `application_settings`;
CREATE TABLE IF NOT EXISTS `application_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `maintenance` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Izbacivanje podataka za tablicu `application_settings`
--

INSERT INTO `application_settings` (`id`, `maintenance`) VALUES
(1, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `geographic_units`
--

DROP TABLE IF EXISTS `geographic_units`;
CREATE TABLE IF NOT EXISTS `geographic_units` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=22 ;

--
-- Izbacivanje podataka za tablicu `geographic_units`
--

INSERT INTO `geographic_units` (`id`, `name`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 'Zagrebačka', '2016-10-30 16:39:11', NULL, NULL, 0),
(2, 'Krapinsko-zagorska', '2016-10-30 16:39:11', NULL, NULL, 0),
(3, 'Sisačko-moslavačka', '2016-10-30 16:39:11', NULL, NULL, 0),
(4, 'Karlovačka', '2016-10-30 16:39:11', NULL, NULL, 0),
(5, 'Varaždinska', '2016-10-30 16:39:11', NULL, NULL, 0),
(6, 'Koprivničko-križevačka', '2016-10-30 16:39:11', NULL, NULL, 0),
(7, 'Bjelovarsko-bilogorska', '2016-10-30 16:39:11', NULL, NULL, 0),
(8, 'Primorsko-goranska', '2016-10-30 16:39:11', NULL, NULL, 0),
(9, 'Ličko-senjska', '2016-10-30 16:39:11', NULL, NULL, 0),
(10, 'Virovitičko-podravska', '2016-10-30 16:39:11', NULL, NULL, 0),
(11, 'Požeško-slavonska', '2016-10-30 16:39:11', NULL, NULL, 0),
(12, 'Brodsko-posavska', '2016-10-30 16:39:11', NULL, NULL, 0),
(13, 'Zadarska', '2016-10-30 16:39:11', NULL, NULL, 0),
(14, 'Osječko-baranjska', '2016-10-30 16:39:11', NULL, NULL, 0),
(15, 'Šibensko-kninska', '2016-10-30 16:39:11', NULL, NULL, 0),
(16, 'Vukovarsko-srijemska', '2016-10-30 16:39:11', NULL, NULL, 0),
(17, 'Splitsko-dalmatinska', '2016-10-30 16:39:11', NULL, NULL, 0),
(18, 'Istarska', '2016-10-30 16:39:11', NULL, NULL, 0),
(19, 'Dubrovačko-neretvanska', '2016-10-30 16:39:11', NULL, NULL, 0),
(20, 'Međimurska', '2016-10-30 16:39:11', NULL, NULL, 0),
(21, 'Grad Zagreb', '2016-10-30 16:39:11', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `pinned_videos`
--

DROP TABLE IF EXISTS `pinned_videos`;
CREATE TABLE IF NOT EXISTS `pinned_videos` (
  `video` int(10) unsigned NOT NULL,
  `pin_offer` int(10) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`video`,`pin_offer`),
  KEY `pinned_video_pinned_offer_idx` (`pin_offer`),
  KEY `pinned_video_video_idx` (`video`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Izbacivanje podataka za tablicu `pinned_videos`
--

INSERT INTO `pinned_videos` (`video`, `pin_offer`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 1, '2016-11-12 08:54:03', NULL, NULL, 0),
(1, 7, '2016-11-12 11:47:53', NULL, NULL, 0),
(1, 12, '2016-11-12 12:13:30', NULL, NULL, 0),
(3, 13, '2016-11-12 12:14:05', NULL, NULL, 0),
(11, 16, '2016-11-12 12:15:01', NULL, NULL, 0),
(1, 17, '2017-03-09 11:45:39', NULL, NULL, 0),
(12, 18, '2017-06-25 22:43:07', NULL, NULL, 0),
(13, 18, '2017-06-25 22:43:07', NULL, NULL, 0),
(14, 18, '2017-06-25 22:43:07', NULL, NULL, 0),
(15, 18, '2017-06-25 22:43:07', NULL, NULL, 0),
(16, 18, '2017-06-25 22:43:07', NULL, NULL, 0),
(17, 18, '2017-06-25 22:43:07', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `pin_offers`
--

DROP TABLE IF EXISTS `pin_offers`;
CREATE TABLE IF NOT EXISTS `pin_offers` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `views_goal` int(10) unsigned DEFAULT NULL,
  `views_current` int(10) unsigned NOT NULL DEFAULT '0',
  `time_interval` int(10) unsigned DEFAULT NULL COMMENT 'Initially represents number of days... IT should be calculated on server side.',
  `sponsor` varchar(150) DEFAULT NULL COMMENT 'Name of sponsor which has sponsored its video',
  `active` tinyint(4) DEFAULT '0',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `cancelled` tinyint(4) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Izbacivanje podataka za tablicu `pin_offers`
--

INSERT INTO `pin_offers` (`id`, `views_goal`, `views_current`, `time_interval`, `sponsor`, `active`, `start_time`, `end_time`, `cancelled`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 7, 4, 22, 'Konzum d.o.o', 0, '2016-11-11 00:00:00', '2016-12-03 00:00:00', 0, '2016-11-12 08:53:17', '2016-12-03 16:32:29', NULL, 0),
(7, 500, 0, 10, 'Konzum', 0, '2016-11-11 00:00:00', '2016-11-21 00:00:00', 0, '2016-11-12 10:19:01', '2016-11-29 20:14:24', NULL, 0),
(8, 500, 0, 40, 'Konzum', 1, '2016-11-11 00:00:00', '2016-12-21 00:00:00', 0, '2016-11-12 11:38:35', NULL, NULL, 0),
(12, 500, 0, 10, 'Konzum', 0, '2016-11-11 00:00:00', '2016-11-21 00:00:00', 0, '2016-11-12 12:13:30', '2017-06-25 22:44:01', NULL, 0),
(13, 500, 0, 10, 'Konzum', 0, '2016-11-11 00:00:00', '2016-11-21 00:00:00', 0, '2016-11-12 12:14:05', '2017-06-25 22:43:57', NULL, 0),
(16, 500, 0, 10, 'Konzum', 0, '2016-11-11 00:00:00', '2016-11-21 00:00:00', 0, '2016-11-12 12:15:01', '2016-11-21 13:09:23', NULL, 0),
(17, 30, 5, 10, '', 0, '2017-03-07 00:00:00', '2017-03-17 00:00:00', 0, '2017-03-09 11:45:18', '2017-03-23 16:14:37', NULL, 0),
(18, 1000, 0, 30, 'Hrvatski Telekom', 1, '2017-06-26 00:00:00', '2017-07-26 00:00:00', 0, '2017-06-25 22:41:32', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `tags`
--

DROP TABLE IF EXISTS `tags`;
CREATE TABLE IF NOT EXISTS `tags` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `type` int(10) unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `tags_typeIDX` (`type`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Izbacivanje podataka za tablicu `tags`
--

INSERT INTO `tags` (`id`, `name`, `description`, `type`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 'žene', 'Oznaka za sve osobe ženskog spola', 1, '2016-10-17 07:30:52', NULL, NULL, 0),
(2, 'muškarci', 'Oznaka za sve osobe muškog spola', 1, '2016-10-17 07:30:52', NULL, NULL, 0),
(3, '22-30', 'Sve osobe od 22 godine do 30 godina', 2, '2016-10-17 08:40:03', NULL, NULL, 0),
(4, '30-40', 'Sve osobe od 30 do 40 godina', 2, '2016-10-17 08:40:25', NULL, NULL, 0),
(5, 'geounit_5', 'Varaždinska županija', 3, '2016-10-17 08:43:37', '2016-10-30 16:40:46', NULL, 0),
(6, 'geounit_21', 'Grad Zagreb', 3, '2016-10-17 08:43:57', '2016-10-30 16:40:51', NULL, 0),
(7, 'geounit_1', 'Zagrebačka županija', 3, '2016-10-24 07:43:32', '2016-10-30 16:41:10', NULL, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `tag_types`
--

DROP TABLE IF EXISTS `tag_types`;
CREATE TABLE IF NOT EXISTS `tag_types` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Izbacivanje podataka za tablicu `tag_types`
--

INSERT INTO `tag_types` (`id`, `name`, `description`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 'gender', 'The gender used for filter videos to target certain people ', '2016-10-23 13:46:26', NULL, NULL, 0),
(2, 'age', 'Age ranges which apply for certain people age ranges.', '2016-10-23 13:46:26', NULL, NULL, 0),
(3, 'geounit', 'Geographic unit that are relevant for certain city area.', '2016-10-23 13:46:26', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `telecomm_operators`
--

DROP TABLE IF EXISTS `telecomm_operators`;
CREATE TABLE IF NOT EXISTS `telecomm_operators` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `user_page_url` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Izbacivanje podataka za tablicu `telecomm_operators`
--

INSERT INTO `telecomm_operators` (`id`, `name`, `code`, `user_page_url`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 'Hrvatski Telekom', '21901', 'https://moj.hrvatskitelekom.hr', '2016-10-07 19:52:32', '2016-11-01 20:47:10', NULL, 0),
(2, 'VipNET', '21910', 'https://moj.vip.hr/', '2016-10-10 07:22:25', '2016-11-01 20:47:22', NULL, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `telecomm_users`
--

DROP TABLE IF EXISTS `telecomm_users`;
CREATE TABLE IF NOT EXISTS `telecomm_users` (
  `user` int(10) unsigned NOT NULL,
  `telecomm_operator` int(10) unsigned NOT NULL,
  `points` int(10) unsigned DEFAULT '0',
  `activated` tinyint(4) DEFAULT '1',
  `activated_at` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `spent_points` int(10) unsigned DEFAULT '0',
  `user_id_remote` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user`,`telecomm_operator`),
  KEY `telecomm_user_telecomm_operator_idx` (`telecomm_operator`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Izbacivanje podataka za tablicu `telecomm_users`
--

INSERT INTO `telecomm_users` (`user`, `telecomm_operator`, `points`, `activated`, `activated_at`, `created_at`, `updated_at`, `deleted_at`, `deleted`, `spent_points`, `user_id_remote`) VALUES
(1, 1, 2, 1, '2016-10-31 20:14:43', '2016-10-10 07:20:50', '2016-10-31 19:14:43', NULL, 0, 0, 'danisoka@t-com.hr'),
(1, 2, 2, 0, '2016-10-31 02:03:42', '2016-10-10 07:24:33', '2016-10-31 18:44:18', NULL, 0, 0, '34'),
(2, 1, 1, 1, '2016-10-25 00:00:00', '2016-10-25 15:15:10', '2016-10-25 15:16:36', NULL, 0, 0, 'aanic'),
(3, 1, 1, 1, NULL, '2016-11-30 15:16:31', '2016-12-01 07:47:09', NULL, 0, 0, 'remote_user_3'),
(37, 2, 1, 1, '2016-12-25 12:34:52', '2016-12-25 12:35:36', '2016-12-25 12:37:57', NULL, 0, 0, NULL),
(0, 0, 0, 1, '2016-12-01 13:10:15', '2016-12-01 13:10:24', NULL, NULL, 0, 0, NULL),
(36, 1, 2, 1, '2016-12-18 17:20:52', '2016-12-18 17:21:25', '2016-12-18 19:13:58', NULL, 0, 0, NULL),
(28, 2, 3, 1, '2016-12-01 17:07:47', '2016-12-01 17:07:56', '2016-12-01 17:20:34', NULL, 0, 0, NULL),
(0, 1, 0, 0, NULL, '2016-12-18 16:11:42', NULL, NULL, 0, 0, NULL),
(33, 2, 6, 1, '2016-12-12 17:08:25', '2016-12-12 17:08:50', '2016-12-21 10:03:26', NULL, 0, 0, NULL),
(32, 1, 4, 1, '2016-12-04 15:02:52', '2016-12-04 15:03:06', '2016-12-08 17:57:20', NULL, 0, 0, NULL);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `telecomm_users_spending_logs`
--

DROP TABLE IF EXISTS `telecomm_users_spending_logs`;
CREATE TABLE IF NOT EXISTS `telecomm_users_spending_logs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` int(10) unsigned DEFAULT NULL,
  `telecomm_operator` int(10) unsigned DEFAULT NULL,
  `points_spent` int(10) unsigned DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `telecomm_users_spending_logs_user_FK_idx` (`user`),
  KEY `telecomm_users_spending_logs_telecomm_operator_FK_idx` (`telecomm_operator`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(100) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` char(1) DEFAULT NULL COMMENT 'M (male) or F (female)',
  `email` varchar(250) DEFAULT NULL,
  `mobile_number` varchar(20) DEFAULT NULL,
  `username` text,
  `password` text,
  `password_changed_at` datetime DEFAULT NULL,
  `num_wrong_logins` int(10) unsigned DEFAULT '0',
  `blocked` tinyint(4) DEFAULT '0',
  `deactivated` tinyint(4) DEFAULT '0',
  `geographic_unit` int(10) unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `deblocking_link` text,
  `activation_link` text,
  `activation_send_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_geographic_unit_FK_idx` (`geographic_unit`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=38 ;

--
-- Izbacivanje podataka za tablicu `users`
--

INSERT INTO `users` (`id`, `name`, `surname`, `birthday`, `gender`, `email`, `mobile_number`, `username`, `password`, `password_changed_at`, `num_wrong_logins`, `blocked`, `deactivated`, `geographic_unit`, `created_at`, `updated_at`, `deleted_at`, `deleted`, `deblocking_link`, `activation_link`, `activation_send_at`) VALUES
(1, 'Danijel', 'Sokač', '1992-10-20', 'M', 'dsokac@foi.hr', '+386987654321', 'danisoka', 'danisoka992', '2016-10-26 09:07:41', 0, 0, 0, 1, '2016-10-09 17:55:02', '2016-11-30 20:53:32', NULL, 0, NULL, 'localhost:8080/VideoMarketingWeb/web_files/webservices/activate_user.php?time=20161026101847&id=1', '2016-10-26 10:18:47'),
(2, 'Ana', 'Anić', '1980-05-22', 'F', 'aanic@gmail.com', NULL, 'aanic', 'aanic1980', NULL, 0, 0, 0, 2, '2016-10-25 15:13:41', NULL, NULL, 0, NULL, NULL, NULL),
(3, 'Ivo', 'Ivić', '1999-11-11', 'M', 'iivic@gmail.com', NULL, 'iivic', 'iivic1999', NULL, 0, 0, 0, 1, '2016-10-26 06:41:13', NULL, NULL, 0, NULL, NULL, NULL),
(37, 'Lucija', 'Sokač', '1996-06-22', 'F', 'luci.soka@gmail.com', '+385919148+94', 'lux96', 'domagojlakus', NULL, 0, 0, 0, 5, '2016-12-25 12:35:35', NULL, NULL, 0, NULL, NULL, NULL),
(33, 'Kristijan', 'Frkovic', '1972-07-18', 'M', 'frx@inet.hr', '+38591662+200', 'frx001', 'frx01frx01', NULL, 0, 0, 0, 1, '2016-12-12 17:08:31', NULL, NULL, 0, NULL, NULL, NULL),
(32, 'Davor', 'Bagaric', '1994-12-30', 'M', 'davorbager@gmail.com', '+3859941565112', 'bager94', 'bager94', '2016-12-06 21:38:38', 0, 0, 0, 14, '2016-12-04 15:02:28', '2016-12-18 15:36:57', NULL, 0, NULL, NULL, NULL),
(36, 'Davor', 'Bagarić', '1994-12-30', 'M', 'davorbager@yahoo.com', '+385994156511', 'bagy94', 'bagy94', NULL, 0, 0, 0, 14, '2016-12-18 17:21:25', NULL, NULL, 0, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `videos`
--

DROP TABLE IF EXISTS `videos`;
CREATE TABLE IF NOT EXISTS `videos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `link` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `points` int(10) unsigned DEFAULT '1',
  `likes` int(10) unsigned DEFAULT '0',
  `dislikes` int(10) unsigned NOT NULL DEFAULT '0',
  `sponsored` tinyint(4) NOT NULL DEFAULT '0',
  `thumbnail_url` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=18 ;

--
-- Izbacivanje podataka za tablicu `videos`
--

INSERT INTO `videos` (`id`, `title`, `link`, `created_at`, `updated_at`, `deleted_at`, `deleted`, `points`, `likes`, `dislikes`, `sponsored`, `thumbnail_url`) VALUES
(1, 'They got what they deserve', 'http://www.dailymotion.com/video/x4x5il7_they-got-what-they-deserve_news', '2016-10-12 19:38:00', '2017-03-23 16:14:37', '2016-10-17 07:10:44', 0, 1, 2, 0, 0, 'http://s2.dmcdn.net/c9th8.jpg'),
(3, 'John Wick: Chapter 2 Official Teaser TRAILER Movie (2017) Keanu Reeves', 'http://www.dailymotion.com/video/x4wqgfo_john-wick-chapter-2-official-teaser-trailer-movie-2017-keanu-reeves_shortfilms', '2016-10-24 07:01:10', '2016-12-08 17:58:40', NULL, 0, 1, 2, 0, 0, 'http://s2.dmcdn.net/c2Ozv.jpg'),
(4, 'Burger King Funny Commercial', 'http://www.dailymotion.com/video/x406czu', '2016-10-30 16:51:51', '2016-12-12 19:33:53', NULL, 0, 1, 2, 0, 0, 'http://s2.dmcdn.net/U68YD.jpg'),
(5, 'Burger King - Chicken Fries Commercial', 'http://www.dailymotion.com/video/x2p5byz', '2016-10-30 16:52:56', '2016-12-18 19:07:41', NULL, 0, 1, 0, 2, 0, 'http://s2.dmcdn.net/KXGDP.jpg'),
(6, 'Commercial Playstation "Mental Wealth" (Chris Cunningham)', 'http://www.dailymotion.com/video/x20s3q_commercial-playstation-mental-wealt_news', '2016-10-30 16:54:03', '2016-12-12 19:32:42', NULL, 0, 1, 1, 0, 1, 'http://s2.dmcdn.net/AcgiE.jpg'),
(7, 'Another Beautiful Ramzan Commercial Rafhan Desserts ! Loving it 3', 'http://www.dailymotion.com/video/x4fzsam_another-beautiful-ramzan-commercial-rafhan-desserts-loving-it-3_news', '2016-10-30 16:55:24', '2016-12-03 16:02:40', NULL, 0, 1, 0, 0, 0, 'http://s2.dmcdn.net/Yamqg.jpg'),
(8, 'banned commercial for glasses !', 'http://www.dailymotion.com/video/xorx6a_banned-commercial-for-glasses_news', '2016-10-30 17:03:46', '2016-12-12 19:53:41', NULL, 0, 1, 1, 0, 0, 'http://s2.dmcdn.net/AZJ7R.jpg'),
(9, 'How to be quiet around a Bison', 'http://www.dailymotion.com/video/x17xiu6_how-to-be-quiet-around-a-bison_animals', '2016-10-30 17:05:07', '2016-12-03 16:04:34', NULL, 0, 1, 0, 0, 0, '\nhttp://s2.dmcdn.net/DHRIW.jpg'),
(10, 'Eva Green Heineken Commercial', 'http://www.dailymotion.com/video/xrtsf_eva-green-heineken-commercial_news', '2016-10-30 17:06:05', '2016-12-03 16:05:23', NULL, 0, 1, 0, 0, 0, 'http://s2.dmcdn.net/Aaj93.jpg'),
(11, 'Shaq''s Gold Bond Commercial', 'http://www.dailymotion.com/video/x2guv2u', '2016-10-30 17:07:52', '2016-12-06 21:38:38', NULL, 0, 1, 2, 0, 0, 'http://s2.dmcdn.net/I5kyq.jpg'),
(12, 'Gledajte novi MAXtv vani kao i kod kuće!', 'http://www.dailymotion.com/video/x5rwm3o', '2017-06-25 22:09:57', NULL, NULL, 0, 1, 0, 0, 0, 'http://s2.dmcdn.net/knILj/x240-owo.jpg'),
(13, 'Doživi više uz novu Tarifu za mlade', 'http://www.dailymotion.com/video/x5rwmti_dozivi-vise-uz-novu-tarifu-za-mlade_fun', '2017-06-25 22:12:57', NULL, NULL, 0, 1, 0, 0, 0, 'http://s1.dmcdn.net/knIwe/x240-J-j.jpg'),
(14, 'MAXtv - nova generacija televizije', 'http://www.dailymotion.com/video/x5rwmp6_maxtv-nova-generacija-televizije_fun', '2017-06-25 22:15:00', NULL, NULL, 0, 1, 0, 0, 0, 'http://s1.dmcdn.net/knIpg/x240-1QX.jpg'),
(15, 'Citi “Dress for Work” Commercial', 'http://www.dailymotion.com/video/x549ha7_citi-dress-for-work-commercial_fun', '2017-06-25 22:27:08', NULL, NULL, 0, 1, 0, 0, 0, 'http://s1.dmcdn.net/fCugm/x240-OGH.jpg'),
(16, 'Asger Leth, redatelj filma Move On', 'http://www.dailymotion.com/video/x5rwml5_asger-leth-redatelj-filma-move-on_fun', '2017-06-25 22:28:30', NULL, NULL, 0, 1, 0, 0, 0, 'http://s1.dmcdn.net/knIjh/x240-v5J.jpg'),
(17, 'Move On u Hrvatskoj', 'http://www.dailymotion.com/video/x5rwmh0_move-on-u-hrvatskoj_fun', '2017-06-25 22:30:33', NULL, NULL, 0, 1, 0, 0, 0, 'http://s1.dmcdn.net/knIdZ/x240-cS6.jpg');

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `videotags`
--

DROP TABLE IF EXISTS `videotags`;
CREATE TABLE IF NOT EXISTS `videotags` (
  `video` int(10) unsigned NOT NULL,
  `tag` int(10) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`video`,`tag`),
  KEY `videotags_tag_FK_idx` (`tag`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Izbacivanje podataka za tablicu `videotags`
--

INSERT INTO `videotags` (`video`, `tag`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 2, '2016-10-17 09:16:12', NULL, NULL, 0),
(1, 3, '2016-10-17 09:16:12', '2016-11-20 21:01:05', NULL, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `video_comments`
--

DROP TABLE IF EXISTS `video_comments`;
CREATE TABLE IF NOT EXISTS `video_comments` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `author` int(10) unsigned DEFAULT NULL,
  `video` int(10) unsigned DEFAULT NULL,
  `content` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `video_comments_author_FK_idx` (`author`),
  KEY `video_comments_video_FK_idx` (`video`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=18 ;

--
-- Izbacivanje podataka za tablicu `video_comments`
--

INSERT INTO `video_comments` (`id`, `author`, `video`, `content`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 1, 1, 'Ovo je testni komentar na video', '2016-10-25 07:25:40', NULL, NULL, 0),
(2, 1, 1, 'Neki drugi komentar', '2016-10-25 07:26:20', NULL, NULL, 0),
(3, 1, 1, 'Testni komentar preko web servisa', '2016-10-25 12:25:56', NULL, NULL, 0),
(4, 1, 1, 'Testni komentar preko web servisa', '2016-10-25 12:28:16', NULL, NULL, 0),
(5, 1, 1, 'Testni komentar preko web servisa', '2016-10-25 12:28:35', NULL, NULL, 0),
(12, 32, 3, 'Bezvezni_komentar', '2016-12-06 21:41:13', NULL, NULL, 0),
(11, 32, 11, 'komentar_na_video', '2016-12-05 19:00:02', NULL, NULL, 0),
(10, 32, 3, 'komentar_', '2016-12-05 18:50:28', NULL, NULL, 0),
(13, 33, 5, 'blesavo', '2016-12-12 19:35:30', NULL, NULL, 0),
(14, 33, 11, 'cool', '2016-12-12 19:37:25', NULL, NULL, 0),
(15, 33, 11, 'super_je', '2016-12-13 13:08:05', NULL, NULL, 0),
(16, 36, 3, 'test', '2016-12-18 19:13:59', NULL, NULL, 0),
(17, 33, 5, 'ne_svidja_mi_se', '2016-12-21 10:00:48', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Tablična struktura za tablicu `video_views`
--

DROP TABLE IF EXISTS `video_views`;
CREATE TABLE IF NOT EXISTS `video_views` (
  `user` int(10) unsigned NOT NULL,
  `video` int(10) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `count` int(10) unsigned DEFAULT '1',
  `videolike` tinyint(4) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user`,`video`),
  KEY `video_views_user_FK_idx` (`user`),
  KEY `video_views_video_FK_idx` (`video`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Izbacivanje podataka za tablicu `video_views`
--

INSERT INTO `video_views` (`user`, `video`, `created_at`, `updated_at`, `count`, `videolike`, `deleted_at`, `deleted`) VALUES
(1, 1, '2016-10-25 12:11:50', '2016-10-25 15:17:13', 3, 1, NULL, 0),
(2, 1, '2016-10-25 15:16:36', '2016-11-20 21:03:33', 1, 1, NULL, 0),
(33, 5, '2016-12-12 19:35:07', '2016-12-12 19:35:31', 1, -1, NULL, 0),
(33, 11, '2016-12-12 19:37:11', NULL, 1, 0, NULL, 0),
(33, 8, '2016-12-12 19:53:34', '2016-12-12 19:53:41', 1, 1, NULL, 0),
(0, 5, '2016-11-30 23:07:03', '2016-12-01 07:46:07', 5, 0, NULL, 0),
(36, 5, '2016-12-18 18:55:13', '2016-12-18 19:07:41', 1, -1, NULL, 0),
(36, 3, '2016-12-18 19:13:58', NULL, 1, 0, NULL, 0),
(0, 3, '2016-12-01 07:46:11', NULL, 1, 0, NULL, 0),
(3, 3, '2016-12-01 07:47:09', NULL, 1, 0, NULL, 0),
(33, 3, '2016-12-21 10:03:26', NULL, 1, 0, NULL, 0),
(37, 11, '2016-12-25 12:37:57', NULL, 1, 0, NULL, 0),
(33, 4, '2016-12-12 19:33:44', '2016-12-12 19:33:53', 1, 1, NULL, 0),
(33, 6, '2016-12-12 19:29:43', '2016-12-12 19:32:42', 1, 1, NULL, 0),
(32, 11, '2016-12-05 18:59:28', '2016-12-06 21:38:38', 1, 1, NULL, 0),
(32, 8, '2016-12-08 17:57:20', NULL, 1, 0, NULL, 0),
(28, 1, '2016-12-01 17:12:40', NULL, 1, 0, NULL, 0),
(28, 3, '2016-12-01 17:15:53', NULL, 1, 0, NULL, 0),
(28, 4, '2016-12-01 17:20:34', NULL, 1, 0, NULL, 0),
(32, 4, '2016-12-05 18:47:16', NULL, 1, 0, NULL, 0),
(32, 3, '2016-12-04 15:51:21', '2016-12-08 17:58:40', 1, 1, NULL, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
