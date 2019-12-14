/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : robbad

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 14/12/2019 10:01:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_building
-- ----------------------------
DROP TABLE IF EXISTS `t_building`;
CREATE TABLE `t_building`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floor` int(11) NULL DEFAULT NULL,
  `room_number` int(11) NULL DEFAULT NULL,
  `building_id` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `owner` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `images` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `beds` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_building
-- ----------------------------
INSERT INTO `t_building` VALUES (1, 1, 11, 1, 0, NULL, NULL, '1');
INSERT INTO `t_building` VALUES (2, 1, 11, 1, 0, '0', NULL, '2');
INSERT INTO `t_building` VALUES (3, 1, 11, 1, 0, '0', NULL, '3');
INSERT INTO `t_building` VALUES (4, 1, 11, 1, 0, '0', NULL, '4');
INSERT INTO `t_building` VALUES (5, 1, 12, 1, 1, '10112345151451', NULL, '1');
INSERT INTO `t_building` VALUES (6, 1, 12, 1, 1, '1234567891011', NULL, '2');
INSERT INTO `t_building` VALUES (7, 1, 12, 1, 0, '0', NULL, '3');
INSERT INTO `t_building` VALUES (8, 1, 12, 1, 0, '0', NULL, '4');
INSERT INTO `t_building` VALUES (9, 1, 13, 1, 0, '0', NULL, '1');
INSERT INTO `t_building` VALUES (10, 1, 13, 1, 0, '0', NULL, '2');
INSERT INTO `t_building` VALUES (11, 1, 13, 1, 0, '0', NULL, '3');
INSERT INTO `t_building` VALUES (12, 1, 13, 1, 0, '0', NULL, '4');
INSERT INTO `t_building` VALUES (13, 2, 14, 1, 0, '0', NULL, '1');
INSERT INTO `t_building` VALUES (14, 2, 14, 1, 0, '0', NULL, '2');
INSERT INTO `t_building` VALUES (15, 2, 14, 1, 0, '0', NULL, '3');
INSERT INTO `t_building` VALUES (16, 2, 14, 1, 0, '0', NULL, '4');
INSERT INTO `t_building` VALUES (17, 2, 15, 1, 0, '', NULL, '1');
INSERT INTO `t_building` VALUES (18, 2, 15, 1, 0, '0', NULL, '2');
INSERT INTO `t_building` VALUES (19, 2, 15, 1, 0, '0', NULL, '3');
INSERT INTO `t_building` VALUES (20, 2, 15, 1, 0, '0', NULL, '4');
INSERT INTO `t_building` VALUES (21, 2, 16, 1, 0, '0', NULL, '1');
INSERT INTO `t_building` VALUES (22, 2, 16, 1, 0, '0', NULL, '2');
INSERT INTO `t_building` VALUES (23, 2, 16, 1, 0, '0', NULL, '3');
INSERT INTO `t_building` VALUES (24, 2, 16, 1, 0, '0', NULL, '4');
INSERT INTO `t_building` VALUES (25, 2, 17, 1, 0, '0', NULL, '1');
INSERT INTO `t_building` VALUES (26, 1, 11, 2, 0, '0', NULL, '1');
INSERT INTO `t_building` VALUES (27, 1, 11, 2, 0, '0', NULL, '2');
INSERT INTO `t_building` VALUES (28, 1, 11, 2, 0, '0', NULL, '3');
INSERT INTO `t_building` VALUES (29, 1, 11, 2, 0, '0', NULL, '4');
INSERT INTO `t_building` VALUES (30, 1, 12, 2, 0, '0', NULL, '1');
INSERT INTO `t_building` VALUES (31, 1, 12, 2, 0, '0', NULL, '2');
INSERT INTO `t_building` VALUES (32, 1, 12, 2, 0, '0', NULL, '3');
INSERT INTO `t_building` VALUES (33, 1, 12, 2, 0, '0', NULL, '4');
INSERT INTO `t_building` VALUES (34, 1, 13, 2, 0, NULL, NULL, '1');
INSERT INTO `t_building` VALUES (35, 1, 13, 2, 0, NULL, NULL, '2');
INSERT INTO `t_building` VALUES (36, 1, 13, 2, 0, NULL, NULL, '3');
INSERT INTO `t_building` VALUES (37, 1, 13, 2, 0, NULL, NULL, '4');
INSERT INTO `t_building` VALUES (38, 1, 14, 2, 0, NULL, NULL, '1');
INSERT INTO `t_building` VALUES (39, 1, 14, 2, 0, NULL, NULL, '2');
INSERT INTO `t_building` VALUES (40, 1, 14, 2, 0, NULL, NULL, '3');
INSERT INTO `t_building` VALUES (41, 1, 14, 2, 0, NULL, NULL, '4');
INSERT INTO `t_building` VALUES (42, 1, 15, 2, 0, NULL, NULL, '1');
INSERT INTO `t_building` VALUES (43, 1, 15, 2, 0, NULL, NULL, '2');
INSERT INTO `t_building` VALUES (44, 1, 15, 2, 0, NULL, NULL, '3');
INSERT INTO `t_building` VALUES (45, 1, 15, 2, 0, NULL, NULL, '4');
INSERT INTO `t_building` VALUES (46, 1, 16, 2, 0, NULL, NULL, '1');
INSERT INTO `t_building` VALUES (47, 1, 16, 2, 0, NULL, NULL, '2');
INSERT INTO `t_building` VALUES (48, 1, 16, 2, 0, NULL, NULL, '3');
INSERT INTO `t_building` VALUES (49, 1, 16, 2, 0, NULL, NULL, '4');
INSERT INTO `t_building` VALUES (50, 1, 17, 2, 0, NULL, NULL, '1');

-- ----------------------------
-- Table structure for t_building_name
-- ----------------------------
DROP TABLE IF EXISTS `t_building_name`;
CREATE TABLE `t_building_name`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `building` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex_building` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_building_name
-- ----------------------------
INSERT INTO `t_building_name` VALUES (1, 'A1号楼', 0);
INSERT INTO `t_building_name` VALUES (2, 'A2号楼', 0);
INSERT INTO `t_building_name` VALUES (3, 'A3号楼', 0);
INSERT INTO `t_building_name` VALUES (4, 'B1号楼', 1);
INSERT INTO `t_building_name` VALUES (5, 'B2号楼', 1);
INSERT INTO `t_building_name` VALUES (6, 'B3号楼', 1);
INSERT INTO `t_building_name` VALUES (7, 'C1号楼', 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `studentNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('男', 1, '王润发', 'e10adc3949ba59abbe56e057f20f883e', '10112345151451', '2019-12-13 18:43:25', '18385295580');

SET FOREIGN_KEY_CHECKS = 1;
