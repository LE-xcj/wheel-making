/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50554
 Source Host           : localhost:3306
 Source Schema         : wheel-making

 Target Server Type    : MySQL
 Target Server Version : 50554
 File Encoding         : 65001

 Date: 16/05/2019 22:46:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_single_message
-- ----------------------------
DROP TABLE IF EXISTS `t_single_message`;
CREATE TABLE `t_single_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sources` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `target` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send_date` datetime NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
