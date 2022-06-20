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

 Date: 10/06/2022 16:05:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_adjust_detail
-- ----------------------------
DROP TABLE IF EXISTS `sstock_adjust_detail`;
CREATE TABLE `sstock_adjust_detail`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人ID',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '租户Id',
  `object_version_number` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '版本号',
  `adjust_id` bigint(20) NULL DEFAULT NULL COMMENT '调整单id',
  `sku_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品code',
  `type` smallint(5) NULL DEFAULT NULL COMMENT '类型1增加 2 减少',
  `plan_num` decimal(12, 0) NULL DEFAULT NULL COMMENT '计划调整数量',
  `reality_num` decimal(12, 0) NULL DEFAULT NULL COMMENT '实际调整数量',
  `cancel_num` decimal(12, 0) NULL DEFAULT NULL COMMENT '取消数量',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存调整单详情' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
