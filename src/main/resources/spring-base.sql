/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50633
 Source Host           : localhost
 Source Database       : spring-base

 Target Server Type    : MySQL
 Target Server Version : 50633
 File Encoding         : utf-8

 Date: 05/28/2017 10:25:52 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `pwd_is_default_pwd` bit(1) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `expired` datetime DEFAULT NULL,
  `forgot_password_code` varchar(255) DEFAULT NULL,
  `code_expired_at` datetime DEFAULT NULL,
  `is_valid_code` bit(1) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
