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

 Date: 10/06/2022 16:06:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_warehouse_warrant_detail
-- ----------------------------
DROP TABLE IF EXISTS `sstock_warehouse_warrant_detail`;
CREATE TABLE `sstock_warehouse_warrant_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `warehouse_warrant_id` bigint(20) NULL DEFAULT NULL COMMENT '入库通知单ID',
  `sku_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品SKU编码',
  `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品SKU名称',
  `spu_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `spu_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `planned_qty` decimal(12, 0) UNSIGNED NULL DEFAULT 0 COMMENT '计划数量',
  `already_input_qty` decimal(12, 0) NULL DEFAULT 0 COMMENT '已入库数量',
  `wait_input_qty` decimal(12, 0) NULL DEFAULT 0 COMMENT '待入库数量',
  `cancel_qty` decimal(12, 0) NULL DEFAULT 0 COMMENT '取消数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 315147190040367105 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '入库商品详情' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
