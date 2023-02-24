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

 Date: 10/06/2022 16:06:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_storage_area
-- ----------------------------
DROP TABLE IF EXISTS `sstock_storage_area`;
CREATE TABLE `sstock_storage_area`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人ID',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '租户Id',
  `object_version_number` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '版本号',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_german2_ci NOT NULL COMMENT '仓储名称',
  `code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `province_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '省份编码',
  `province_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '省份名称',
  `city_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '城市编码',
  `city_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '城市名称',
  `district_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '区县编码',
  `district_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '区县名称',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细地址 ',
  `lng` decimal(11, 6) NULL DEFAULT NULL COMMENT '经度',
  `lat` decimal(11, 6) NULL DEFAULT NULL COMMENT '纬度',
  `contact_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人电话',
  `description` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `enable_flag` tinyint(1) NOT NULL COMMENT '可用标志位：1启用，0禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓储区域表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
