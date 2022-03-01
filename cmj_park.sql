/*
 Navicat Premium Data Transfer

 Source Server         : xxgg
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : cmj_park

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 27/02/2022 16:32:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登录名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员密码',
  `realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'admin', '$2a$10$wtbwrUZmv4WYsL7V.8mWEuFU8c31fbIt.A02/YiEX.hNHWNE2RJEe', '测试', '12345678910');

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(0) NOT NULL COMMENT 'admin_id',
  `role_id` int(0) NOT NULL COMMENT 'role_id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `admin_id`(`admin_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `t_admin_role_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `t_admin` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_admin_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin_role
-- ----------------------------
INSERT INTO `t_admin_role` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for t_announ
-- ----------------------------
DROP TABLE IF EXISTS `t_announ`;
CREATE TABLE `t_announ`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告内容',
  `updateTime` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_announ
-- ----------------------------
INSERT INTO `t_announ` VALUES (1, '最新的停车收费公告', '  为了规范车辆管理，根据停车收费规范的要求，并结合实际情况，用户车辆均入地下车库统一停放，现将相关事项通知如下：\n\n  1.机动车统一按照规定进行停车，并且进入相应的停车位置，提供有偿服务，具体收费标准如下：\n\n  2.按照时段进行收费，每15分钟进行一次计费，不满15分钟的按15分钟计算，不同区域和车位可能存在不同价格，但是计费时间按以上规定。\n\n  请各位业主从即日起，速到小区物业服务客户中心办理相关交费手续。\n\n  非本小区住户原则上不得进入小区停放车辆，请各位住户理解及支持。\n\n特此通知！\n\ncmj客户服务中心', '2022-01-25 20:09:27');

-- ----------------------------
-- Table structure for t_car_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_car_msg`;
CREATE TABLE `t_car_msg`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `car_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车牌号',
  `car_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车名',
  `car_photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车照路径',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `car_owner` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车主',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `car_number`(`car_number`) USING BTREE,
  INDEX `car_owner`(`car_owner`) USING BTREE,
  CONSTRAINT `car_owner` FOREIGN KEY (`car_owner`) REFERENCES `t_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_car_msg
-- ----------------------------
INSERT INTO `t_car_msg` VALUES (14, '粤A66666', '劳斯莱斯', 'e83d4ac191df486fb2ef9909cbdce7d4.jpg', '2233', 'user');
INSERT INTO `t_car_msg` VALUES (15, '粤A88888', '劳斯莱斯', '42f9ce6973714013a8995a16743e88cc.jpg', '11', '令狐冲1');

-- ----------------------------
-- Table structure for t_card_money
-- ----------------------------
DROP TABLE IF EXISTS `t_card_money`;
CREATE TABLE `t_card_money`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card_number` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡号',
  `card_money` double UNSIGNED NOT NULL DEFAULT 0 COMMENT '卡余额',
  `card_owner` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡主人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userName`(`card_owner`) USING BTREE,
  CONSTRAINT `userName` FOREIGN KEY (`card_owner`) REFERENCES `t_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_card_money
-- ----------------------------
INSERT INTO `t_card_money` VALUES (1, '1564683o', 9995.25, 'user');
INSERT INTO `t_card_money` VALUES (2, '21a30687', 1.41, '令狐冲1');
INSERT INTO `t_card_money` VALUES (6, 'fcee9393', 0, '令狐冲');

-- ----------------------------
-- Table structure for t_park_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_park_msg`;
CREATE TABLE `t_park_msg`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `park_number` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车位号',
  `park_area` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区域',
  `park_fee` int(0) NOT NULL COMMENT '每分钟收费',
  `park_note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `park_car_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停车车牌号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_park_msg_ibfk_1`(`park_car_number`) USING BTREE,
  CONSTRAINT `t_park_msg_ibfk_1` FOREIGN KEY (`park_car_number`) REFERENCES `t_car_msg` (`car_number`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_park_msg
-- ----------------------------
INSERT INTO `t_park_msg` VALUES (1, 'A00', 'A区', 4, 'A00', NULL);
INSERT INTO `t_park_msg` VALUES (2, 'B00', 'B区', 4, 'B00', NULL);
INSERT INTO `t_park_msg` VALUES (8, 'D00', 'D区', 3, 'D00', '粤A66666');
INSERT INTO `t_park_msg` VALUES (9, 'E00', 'E区', 4, 'E00', NULL);
INSERT INTO `t_park_msg` VALUES (23, 'A01', 'A区', 4, 'A01', NULL);

-- ----------------------------
-- Table structure for t_park_record
-- ----------------------------
DROP TABLE IF EXISTS `t_park_record`;
CREATE TABLE `t_park_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `record_car_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车牌号',
  `record_time_start` datetime(0) NOT NULL COMMENT '停车开始时间',
  `record_time_end` datetime(0) NOT NULL COMMENT '结束时间',
  `record_hour_money` double UNSIGNED NOT NULL COMMENT '每小时收费',
  `record_time` double NOT NULL COMMENT '停车时长',
  `record_money` double NOT NULL COMMENT '收费金额',
  `record_owner` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '被收费人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `record_owner`(`record_owner`) USING BTREE,
  CONSTRAINT `t_park_record_ibfk_1` FOREIGN KEY (`record_owner`) REFERENCES `t_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_park_record
-- ----------------------------
INSERT INTO `t_park_record` VALUES (1, '粤A66666', '2022-01-15 12:14:00', '2022-01-15 15:14:00', 4, 3, 12, 'user');
INSERT INTO `t_park_record` VALUES (2, '粤A88888', '2022-01-15 12:14:00', '2022-01-15 15:14:00', 4, 3, 12, '令狐冲1');
INSERT INTO `t_park_record` VALUES (3, '粤A88888', '2022-01-24 13:29:00', '2022-01-24 14:29:00', 3, 1, 3, '令狐冲1');
INSERT INTO `t_park_record` VALUES (4, '粤A88888', '2022-01-24 14:33:00', '2022-01-24 15:33:00', 4, 1, 4, '令狐冲1');
INSERT INTO `t_park_record` VALUES (5, '粤A88888', '2022-01-24 14:35:00', '2022-01-24 15:35:00', 4, 4, 4, '令狐冲1');
INSERT INTO `t_park_record` VALUES (6, '粤A88888', '2022-01-24 14:38:00', '2022-01-24 15:39:00', 4, 4, 1, '令狐冲1');
INSERT INTO `t_park_record` VALUES (7, '粤A88888', '2022-01-24 14:58:00', '2022-01-24 15:58:00', 3, 1, 3, '令狐冲1');
INSERT INTO `t_park_record` VALUES (8, '粤A66666', '2022-01-24 21:47:00', '2022-01-24 22:47:00', 4, 1, 4, 'user');
INSERT INTO `t_park_record` VALUES (9, '粤A66666', '2022-01-26 14:00:00', '2022-01-26 14:01:00', 3, 1, 0.75, 'user');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(0) NOT NULL COMMENT 'role_id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限代号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '管理员', 'ROLE_ADMIN_SUPER');
INSERT INTO `t_role` VALUES (2, '用户', 'ROLE_USER_COMMON');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (19, 'user', '测试名', '$2a$10$plWIX42DDhDHoqBppmeWee5dzqvarqpe9mTY3Kh8QkFTZQRTFpwlS', '13245678913', '592655567@qq.com');
INSERT INTO `t_user` VALUES (20, '令狐冲1', '测试名', '$2a$10$jld3oNGt2zORaKxptzJEJexJ8L9dw47ODkgACPkPNSKd56hxWZpEu', '12345678911', '1233@qq.com');
INSERT INTO `t_user` VALUES (22, '令狐冲', '测试', '$2a$10$PuJKpMzrGC.gBMayh1RXWuCQhAM.aU4lgLp7qk03MPiX93KgQSpia', '13245678911', '');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(0) NOT NULL COMMENT 'user_id',
  `role_id` int(0) NOT NULL COMMENT 'role_id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 19, 2);
INSERT INTO `t_user_role` VALUES (2, 20, 2);
INSERT INTO `t_user_role` VALUES (3, 22, 2);

SET FOREIGN_KEY_CHECKS = 1;
