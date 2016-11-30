-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 28, 2016 at 11:23 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `videomarketing`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(100) DEFAULT NULL,
  `username` text,
  `password` text,
  `password_changed_at` datetime DEFAULT NULL,
  `num_wrong_logins` int(10) UNSIGNED DEFAULT '0',
  `blocked` tinyint(4) DEFAULT '0',
  `superadmin` tinyint(4) DEFAULT '0',
  `email` varchar(250) DEFAULT NULL,
  `activation_link` text,
  `deblocking_link` text,
  `num_deblocking` int(10) UNSIGNED DEFAULT '0',
  `activated` tinyint(4) DEFAULT '0',
  `deleted` tinyint(4) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `activation_sent_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `application_logs`
--

CREATE TABLE `application_logs` (
  `id` int(10) UNSIGNED NOT NULL,
  `type` tinyint(3) UNSIGNED DEFAULT '3' COMMENT '1-database operations, 2 - login / logout, 3 - other',
  `success` tinyint(4) DEFAULT '0' COMMENT '-1 - failurte, 0 - neutral, 1 - success',
  `user` int(10) UNSIGNED DEFAULT NULL,
  `admin` tinyint(4) DEFAULT '0',
  `ip_address` varchar(250) DEFAULT NULL,
  `script` text,
  `text` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `geographic_units`
--

CREATE TABLE `geographic_units` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `geographic_units`
--

INSERT INTO `geographic_units` (`id`, `name`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 'Zagrebačka', '2016-10-30 15:39:11', NULL, NULL, 0),
(2, 'Krapinsko-zagorska', '2016-10-30 15:39:11', NULL, NULL, 0),
(3, 'Sisačko-moslavačka', '2016-10-30 15:39:11', NULL, NULL, 0),
(4, 'Karlovačka', '2016-10-30 15:39:11', NULL, NULL, 0),
(5, 'Varaždinska', '2016-10-30 15:39:11', NULL, NULL, 0),
(6, 'Koprivničko-križevačka', '2016-10-30 15:39:11', NULL, NULL, 0),
(7, 'Bjelovarsko-bilogorska', '2016-10-30 15:39:11', NULL, NULL, 0),
(8, 'Primorsko-goranska', '2016-10-30 15:39:11', NULL, NULL, 0),
(9, 'Ličko-senjska', '2016-10-30 15:39:11', NULL, NULL, 0),
(10, 'Virovitičko-podravska', '2016-10-30 15:39:11', NULL, NULL, 0),
(11, 'Požeško-slavonska', '2016-10-30 15:39:11', NULL, NULL, 0),
(12, 'Brodsko-posavska', '2016-10-30 15:39:11', NULL, NULL, 0),
(13, 'Zadarska', '2016-10-30 15:39:11', NULL, NULL, 0),
(14, 'Osječko-baranjska', '2016-10-30 15:39:11', NULL, NULL, 0),
(15, 'Šibensko-kninska', '2016-10-30 15:39:11', NULL, NULL, 0),
(16, 'Vukovarsko-srijemska', '2016-10-30 15:39:11', NULL, NULL, 0),
(17, 'Splitsko-dalmatinska', '2016-10-30 15:39:11', NULL, NULL, 0),
(18, 'Istarska', '2016-10-30 15:39:11', NULL, NULL, 0),
(19, 'Dubrovačko-neretvanska', '2016-10-30 15:39:11', NULL, NULL, 0),
(20, 'Međimurska', '2016-10-30 15:39:11', NULL, NULL, 0),
(21, 'Grad Zagreb', '2016-10-30 15:39:11', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `pinned_videos`
--

CREATE TABLE `pinned_videos` (
  `video` int(10) UNSIGNED NOT NULL,
  `pin_offer` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `pin_offers`
--

CREATE TABLE `pin_offers` (
  `id` int(10) UNSIGNED NOT NULL,
  `views_goal` int(10) UNSIGNED DEFAULT NULL,
  `time_interval` float DEFAULT NULL COMMENT 'Initially represents number of days... IT should be calculated on server side.',
  `user` int(10) UNSIGNED DEFAULT NULL,
  `active` tinyint(4) DEFAULT '0',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `cancelled` tinyint(4) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `type` int(10) UNSIGNED DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tags`
--

INSERT INTO `tags` (`id`, `name`, `description`, `type`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 'žene', 'Oznaka za sve osobe ženskog spola', 1, '2016-10-17 05:30:52', NULL, NULL, 0),
(2, 'muškarci', 'Oznaka za sve osobe muškog spola', 1, '2016-10-17 05:30:52', NULL, NULL, 0),
(3, '22-30', 'Sve osobe od 22 godine do 30 godina', 2, '2016-10-17 06:40:03', NULL, NULL, 0),
(4, '30-40', 'Sve osobe od 30 do 40 godina', 2, '2016-10-17 06:40:25', NULL, NULL, 0),
(5, 'geounit_5', 'Varaždinska županija', 3, '2016-10-17 06:43:37', '2016-10-30 15:40:46', NULL, 0),
(6, 'geounit_21', 'Grad Zagreb', 3, '2016-10-17 06:43:57', '2016-10-30 15:40:51', NULL, 0),
(7, 'geounit_1', 'Zagrebačka županija', 3, '2016-10-24 05:43:32', '2016-10-30 15:41:10', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tag_types`
--

CREATE TABLE `tag_types` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tag_types`
--

INSERT INTO `tag_types` (`id`, `name`, `description`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 'gender', 'The gender used for filter videos to target c', '2016-10-23 11:46:26', NULL, NULL, 0),
(2, 'age', 'Age ranges which apply for certain people age', '2016-10-23 11:46:26', NULL, NULL, 0),
(3, 'geounit', 'Geographic unit that are relevant for certain', '2016-10-23 11:46:26', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `telecomm_operators`
--

CREATE TABLE `telecomm_operators` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `user_page_url` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `code` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `telecomm_operators`
--

INSERT INTO `telecomm_operators` (`id`, `name`, `user_page_url`, `created_at`, `updated_at`, `deleted_at`, `deleted`, `code`) VALUES
(1, 'Hrvatski Telekom', 'https://moj.hrvatskitelekom.hr', '2016-10-07 17:52:32', '2016-11-01 19:47:10', NULL, 0, '21901'),
(2, 'VipNET', 'https://moj.vip.hr/', '2016-10-10 05:22:25', '2016-11-01 19:47:22', NULL, 0, '21910');

-- --------------------------------------------------------

--
-- Table structure for table `telecomm_users`
--

CREATE TABLE `telecomm_users` (
  `user` int(10) UNSIGNED NOT NULL,
  `telecomm_operator` int(10) UNSIGNED NOT NULL,
  `points` int(10) UNSIGNED DEFAULT '0',
  `activated` tinyint(4) DEFAULT '1',
  `activated_at` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `spent_points` int(10) UNSIGNED DEFAULT '0',
  `user_id_remote` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `telecomm_users`
--

INSERT INTO `telecomm_users` (`user`, `telecomm_operator`, `points`, `activated`, `activated_at`, `created_at`, `updated_at`, `deleted_at`, `deleted`, `spent_points`, `user_id_remote`) VALUES
(1, 1, 2, 1, '2016-10-31 20:14:43', '2016-10-10 05:20:50', '2016-10-31 18:14:43', NULL, 0, 0, 'danisoka@t-com.hr'),
(1, 2, 2, 0, '2016-10-31 02:03:42', '2016-10-10 05:24:33', '2016-10-31 17:44:18', NULL, 0, 0, '34'),
(2, 1, 1, 1, '2016-10-25 00:00:00', '2016-10-25 13:15:10', '2016-10-25 13:16:36', NULL, 0, 0, 'aanic');

-- --------------------------------------------------------

--
-- Table structure for table `telecomm_users_spending_logs`
--

CREATE TABLE `telecomm_users_spending_logs` (
  `id` int(10) UNSIGNED NOT NULL,
  `user` int(10) UNSIGNED DEFAULT NULL,
  `telecomm_operator` int(10) UNSIGNED DEFAULT NULL,
  `points_spent` int(10) UNSIGNED DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(100) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` char(1) DEFAULT NULL COMMENT 'M (male) or F (female)',
  `email` varchar(250) DEFAULT NULL,
  `username` text,
  `password` text,
  `password_changed_at` datetime DEFAULT NULL,
  `num_wrong_logins` int(10) UNSIGNED DEFAULT '0',
  `blocked` tinyint(4) DEFAULT '0',
  `deactivated` tinyint(4) DEFAULT '0',
  `geographic_unit` int(10) UNSIGNED DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `deblocking_link` text,
  `activation_link` text,
  `activation_sent_at` datetime DEFAULT NULL,
  `mobile_number` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `surname`, `birthday`, `gender`, `email`, `username`, `password`, `password_changed_at`, `num_wrong_logins`, `blocked`, `deactivated`, `geographic_unit`, `created_at`, `updated_at`, `deleted_at`, `deleted`, `deblocking_link`, `activation_link`, `activation_sent_at`, `mobile_number`) VALUES
(1, 'Danijel', 'Sokač', '1992-10-20', 'M', 'dsokac@foi.hr', 'danisoka', 'danisoka992', '2016-10-26 09:07:41', 0, 0, 1, 1, '2016-10-09 15:55:02', '2016-11-28 10:06:24', NULL, 0, NULL, 'localhost:8080/VideoMarketingWeb/web_files/webservices/activate_user.php?time=20161026101847&id=1', '2016-10-26 10:18:47', '+385987654321'),
(2, 'Ana', 'Anić', '1980-05-22', 'F', 'aanic@gmail.com', 'aanic', 'aanic1980', NULL, 0, 0, 0, 2, '2016-10-25 13:13:41', NULL, NULL, 0, NULL, NULL, NULL, NULL),
(3, 'Ivo', 'Ivić', '1999-11-11', 'M', 'iivic@gmail.com', 'iivic', 'iivic1999', NULL, 0, 0, 0, 1, '2016-10-26 04:41:13', NULL, NULL, 0, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

CREATE TABLE `videos` (
  `id` int(10) UNSIGNED NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `link` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `points` int(10) UNSIGNED DEFAULT '1',
  `likes` int(10) UNSIGNED DEFAULT '0',
  `dislikes` int(10) UNSIGNED DEFAULT '0',
  `sponsored` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videos`
--

INSERT INTO `videos` (`id`, `title`, `link`, `created_at`, `updated_at`, `deleted_at`, `deleted`, `points`, `likes`, `dislikes`, `sponsored`) VALUES
(1, 'They got what they deserve', 'http://www.dailymotion.com/video/x4x5il7_they-got-what-they-deserve_news', '2016-10-12 17:38:00', '2016-10-30 15:45:56', '2016-10-17 07:10:44', 0, 1, 2, 0, 0),
(3, 'John Wick: Chapter 2 Official Teaser TRAILER Movie (2017) Keanu Reeves', 'http://www.dailymotion.com/video/x4wqgfo_john-wick-chapter-2-official-teaser-trailer-movie-2017-keanu-reeves_shortfilms', '2016-10-24 05:01:10', '2016-10-30 15:49:01', NULL, 0, 1, 0, 0, 0),
(4, 'Burger King Funny Commercial', 'http://www.dailymotion.com/video/x406czu', '2016-10-30 15:51:51', NULL, NULL, 0, 1, 0, 0, 0),
(5, 'Burger King - Chicken Fries Commercial', 'http://www.dailymotion.com/video/x2p5byz', '2016-10-30 15:52:56', NULL, NULL, 0, 1, 0, 0, 0),
(6, 'Commercial Playstation "Mental Wealth" (Chris Cunningham)', 'http://www.dailymotion.com/video/x20s3q_commercial-playstation-mental-wealt_news', '2016-10-30 15:54:03', NULL, NULL, 0, 1, 0, 0, 0),
(7, 'Another Beautiful Ramzan Commercial Rafhan Desserts ! Loving it 3', 'http://www.dailymotion.com/video/x4fzsam_another-beautiful-ramzan-commercial-rafhan-desserts-loving-it-3_news', '2016-10-30 15:55:24', NULL, NULL, 0, 1, 0, 0, 0),
(8, 'banned commercial for glasses !', 'http://www.dailymotion.com/video/xorx6a_banned-commercial-for-glasses_news', '2016-10-30 16:03:46', NULL, NULL, 0, 1, 0, 0, 0),
(9, 'How to be quiet around a Bison', 'http://www.dailymotion.com/video/x17xiu6_how-to-be-quiet-around-a-bison_animals', '2016-10-30 16:05:07', NULL, NULL, 0, 1, 0, 0, 0),
(10, 'Eva Green Heineken Commercial', 'http://www.dailymotion.com/video/xrtsf_eva-green-heineken-commercial_news', '2016-10-30 16:06:05', NULL, NULL, 0, 1, 0, 0, 0),
(11, 'Shaq''s Gold Bond Commercial', 'http://www.dailymotion.com/video/x2guv2u', '2016-10-30 16:07:52', NULL, NULL, 0, 1, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `videotags`
--

CREATE TABLE `videotags` (
  `video` int(10) UNSIGNED NOT NULL,
  `tag` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `videotags`
--

INSERT INTO `videotags` (`video`, `tag`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 2, '2016-10-17 07:16:12', NULL, NULL, 0),
(1, 4, '2016-10-17 07:16:12', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `video_comments`
--

CREATE TABLE `video_comments` (
  `id` int(10) UNSIGNED NOT NULL,
  `author` int(10) UNSIGNED DEFAULT NULL,
  `video` int(10) UNSIGNED DEFAULT NULL,
  `content` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `video_comments`
--

INSERT INTO `video_comments` (`id`, `author`, `video`, `content`, `created_at`, `updated_at`, `deleted_at`, `deleted`) VALUES
(1, 1, 1, 'Ovo je testni komentar na video', '2016-10-25 05:25:40', NULL, NULL, 0),
(2, 1, 1, 'Neki drugi komentar', '2016-10-25 05:26:20', NULL, NULL, 0),
(3, 1, 1, 'Testni komentar preko web servisa', '2016-10-25 10:25:56', NULL, NULL, 0),
(4, 1, 1, 'Testni komentar preko web servisa', '2016-10-25 10:28:16', NULL, NULL, 0),
(5, 1, 1, 'Testni komentar preko web servisa', '2016-10-25 10:28:35', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `video_views`
--

CREATE TABLE `video_views` (
  `user` int(10) UNSIGNED NOT NULL,
  `video` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `count` int(10) UNSIGNED DEFAULT '1',
  `videolike` tinyint(4) DEFAULT '0' COMMENT '-1 = dislike, 0 = neutral, 1 = like',
  `deleted_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `video_views`
--

INSERT INTO `video_views` (`user`, `video`, `created_at`, `updated_at`, `count`, `videolike`, `deleted_at`, `deleted`) VALUES
(1, 1, '2016-10-25 10:11:50', '2016-10-25 13:17:13', 3, 1, NULL, 0),
(2, 1, '2016-10-25 13:16:36', '2016-10-25 13:17:03', 1, 1, NULL, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `application_logs`
--
ALTER TABLE `application_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `geographic_units`
--
ALTER TABLE `geographic_units`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pinned_videos`
--
ALTER TABLE `pinned_videos`
  ADD PRIMARY KEY (`video`,`pin_offer`),
  ADD KEY `pinned_video_pinned_offer_idx` (`pin_offer`);

--
-- Indexes for table `pin_offers`
--
ALTER TABLE `pin_offers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pin_offers_user_FK_idx` (`user`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tags_typeFK_idx` (`type`);

--
-- Indexes for table `tag_types`
--
ALTER TABLE `tag_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `telecomm_operators`
--
ALTER TABLE `telecomm_operators`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `telecomm_users`
--
ALTER TABLE `telecomm_users`
  ADD PRIMARY KEY (`user`,`telecomm_operator`),
  ADD KEY `telecomm_user_telecomm_operator_idx` (`telecomm_operator`);

--
-- Indexes for table `telecomm_users_spending_logs`
--
ALTER TABLE `telecomm_users_spending_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `telecomm_users_spending_logs_user_FK_idx` (`user`),
  ADD KEY `telecomm_users_spending_logs_telecomm_operator_FK_idx` (`telecomm_operator`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_geographic_unit_FK_idx` (`geographic_unit`);

--
-- Indexes for table `videos`
--
ALTER TABLE `videos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `videotags`
--
ALTER TABLE `videotags`
  ADD PRIMARY KEY (`video`,`tag`),
  ADD KEY `videotags_tag_FK_idx` (`tag`);

--
-- Indexes for table `video_comments`
--
ALTER TABLE `video_comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `video_comments_author_FK_idx` (`author`),
  ADD KEY `video_comments_video_FK_idx` (`video`);

--
-- Indexes for table `video_views`
--
ALTER TABLE `video_views`
  ADD PRIMARY KEY (`user`,`video`),
  ADD KEY `video_views_user_FK_idx` (`user`),
  ADD KEY `video_views_video_FK_idx` (`video`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `application_logs`
--
ALTER TABLE `application_logs`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `geographic_units`
--
ALTER TABLE `geographic_units`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `pin_offers`
--
ALTER TABLE `pin_offers`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `tag_types`
--
ALTER TABLE `tag_types`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `telecomm_operators`
--
ALTER TABLE `telecomm_operators`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `telecomm_users_spending_logs`
--
ALTER TABLE `telecomm_users_spending_logs`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `videos`
--
ALTER TABLE `videos`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `video_comments`
--
ALTER TABLE `video_comments`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `pinned_videos`
--
ALTER TABLE `pinned_videos`
  ADD CONSTRAINT `pinned_video_pin_offer` FOREIGN KEY (`pin_offer`) REFERENCES `pin_offers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pinned_videos_video_FK` FOREIGN KEY (`video`) REFERENCES `videos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pin_offers`
--
ALTER TABLE `pin_offers`
  ADD CONSTRAINT `pin_offers_user_FK` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tags`
--
ALTER TABLE `tags`
  ADD CONSTRAINT `tags_typeFK` FOREIGN KEY (`type`) REFERENCES `tag_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `telecomm_users`
--
ALTER TABLE `telecomm_users`
  ADD CONSTRAINT `telecomm_users_telecomm_operator` FOREIGN KEY (`telecomm_operator`) REFERENCES `telecomm_operators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `telecomm_users_user_FK` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `telecomm_users_spending_logs`
--
ALTER TABLE `telecomm_users_spending_logs`
  ADD CONSTRAINT `telecomm_users_spending_logs_telecomm_operator_FK` FOREIGN KEY (`telecomm_operator`) REFERENCES `telecomm_operators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `telecomm_users_spending_logs_user_FK` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_geographic_unit_FK` FOREIGN KEY (`geographic_unit`) REFERENCES `geographic_units` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `videotags`
--
ALTER TABLE `videotags`
  ADD CONSTRAINT `videotags_tag_FK` FOREIGN KEY (`tag`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `videotags_video_FK` FOREIGN KEY (`video`) REFERENCES `videos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `video_comments`
--
ALTER TABLE `video_comments`
  ADD CONSTRAINT `video_comments_author_FK` FOREIGN KEY (`author`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `video_comments_video_FK` FOREIGN KEY (`video`) REFERENCES `videos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `video_views`
--
ALTER TABLE `video_views`
  ADD CONSTRAINT `video_views_user_FK` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `video_views_video_FK` FOREIGN KEY (`video`) REFERENCES `videos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
