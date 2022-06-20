/*
 Navicat Premium Data Transfer

 Source Server         : 开发环境
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.124.200:3306
 Source Schema         : skyer_open_source_stock

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 10/06/2022 16:06:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_result_detail
-- ----------------------------
DROP TABLE IF EXISTS `sstock_result_detail`;
CREATE TABLE `sstock_result_detail`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `result_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '结果单id',
  `real_num` decimal(12, 0) NULL DEFAULT NULL COMMENT '已经出入库数量',
  `sku_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SKU编码',
  `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SKU名称',
  `spu_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `spu_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 314392288259284993 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收发货结果单详情' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
