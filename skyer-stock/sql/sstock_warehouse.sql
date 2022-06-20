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

 Date: 10/06/2022 16:06:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `sstock_warehouse`;
CREATE TABLE `sstock_warehouse`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态 1删除',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人ID',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '租户Id',
  `object_version_number` bigint(20) UNSIGNED NULL DEFAULT 1 COMMENT '版本号',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名',
  `code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库类型',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 1启用 0禁用',
  `structure_id` bigint(20) NULL DEFAULT NULL COMMENT '结构ID',
  `x` decimal(10, 2) NULL DEFAULT NULL COMMENT '前端显示坐标',
  `y` decimal(10, 2) NULL DEFAULT NULL COMMENT '前端显示坐标',
  `system_flag` tinyint(1) NULL DEFAULT 0 COMMENT '系统生成 1是 0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓库表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
