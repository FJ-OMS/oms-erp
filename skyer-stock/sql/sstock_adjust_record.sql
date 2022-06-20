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

 Date: 10/06/2022 16:05:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_adjust_record
-- ----------------------------
DROP TABLE IF EXISTS `sstock_adjust_record`;
CREATE TABLE `sstock_adjust_record`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人ID',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '租户Id',
  `object_version_number` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '版本号',
  `adjust_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调整单号',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库编码',
  `audit_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态STOCK.RECORD_STATUS',
  `logic_status` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑仓增加状态 1已执行 0未执行',
  `real_status` tinyint(1) NULL DEFAULT NULL COMMENT '实仓增加状态 1已执行 0未执行',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存调整单' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
