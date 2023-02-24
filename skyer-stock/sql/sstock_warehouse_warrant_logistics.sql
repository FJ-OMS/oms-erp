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

 Date: 10/06/2022 16:07:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_warehouse_warrant_logistics
-- ----------------------------
DROP TABLE IF EXISTS `sstock_warehouse_warrant_logistics`;
CREATE TABLE `sstock_warehouse_warrant_logistics`  (
  `id` bigint(20) NOT NULL,
  `logistics_company_id` bigint(60) NULL DEFAULT NULL COMMENT '退货物流公司id',
  `logistics_company_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货物流公司名称',
  `logistics_no` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货物流单号',
  `warehouse_warrant_id` bigint(20) NULL DEFAULT NULL COMMENT '入库通知单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '入库单物流信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
