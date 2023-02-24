/*
 Navicat Premium Data Transfer

 Source Server         : 豹骥开发环境
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.124.199:30336
 Source Schema         : skyer_channel

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 28/06/2021 11:26:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for channel_dealer
-- ----------------------------
DROP TABLE IF EXISTS `channel_dealer`;
CREATE TABLE `channel_dealer`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dealer_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经销商编码',
  `dealer_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经销商名称',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户Id',
  `contact_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人电话',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 0启用 1禁用',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '1删除',
  `description` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `object_version_number` bigint(20) NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 231616564653 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '经销商表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel_online_shop
-- ----------------------------
DROP TABLE IF EXISTS `channel_online_shop`;
CREATE TABLE `channel_online_shop`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `object_version_number` bigint(20) NULL DEFAULT NULL COMMENT '版本号',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网店名称',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户Id',
  `channel_type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道类型',
  `online_store_type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网店类型',
  `manager` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网店地址(url)',
  `sales_organizing_id` bigint(20) NULL DEFAULT NULL COMMENT '销售组织',
  `invoice_type_code` varbinary(60) NULL DEFAULT NULL COMMENT '发票种类',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 0启用 1禁用',
  `auto_confirm_day` tinyint(4) NULL DEFAULT NULL COMMENT '自动确认发货（天）',
  `auto_download_goods` tinyint(1) NULL DEFAULT NULL COMMENT '自动下载商品',
  `auto_sync_inventory` tinyint(1) NULL DEFAULT NULL COMMENT '自动同步库存',
  `channel_storehouse_status` tinyint(1) NULL DEFAULT NULL COMMENT '0否 1是',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网店' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel_physical_shop
-- ----------------------------
DROP TABLE IF EXISTS `channel_physical_shop`;
CREATE TABLE `channel_physical_shop`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `object_version_number` bigint(20) NULL DEFAULT NULL COMMENT '版本号',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店名称',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户Id',
  `type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店类型',
  `manager` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `sales_organizing_id` bigint(20) NULL DEFAULT NULL COMMENT '销售组织',
  `invoice_type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票种类',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `country_id` bigint(20) NULL DEFAULT NULL COMMENT '国家ID',
  `province_id` bigint(20) NULL DEFAULT NULL COMMENT '省ID',
  `city_id` bigint(20) NULL DEFAULT NULL COMMENT '市ID',
  `county_id` bigint(20) NULL DEFAULT NULL COMMENT '县ID',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 0启用 1禁用',
  `channel_storehouse_status` tinyint(1) NULL DEFAULT NULL COMMENT '0否 1是',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 197290938807984129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '门店' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel_sales_organization
-- ----------------------------
DROP TABLE IF EXISTS `channel_sales_organization`;
CREATE TABLE `channel_sales_organization`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织编码',
  `org_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `shop_permit_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '营业执照名称',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户Id',
  `legal_person` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '法人姓名',
  `uniform_social_credit_code` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '统一社会信用代码',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 0启用 1禁用',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '1删除',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `object_version_number` bigint(20) NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售组织表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for channel_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `channel_warehouse`;
CREATE TABLE `channel_warehouse`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ware_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编码',
  `ware_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户Id',
  `contact_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人电话',
  `country_id` bigint(20) NOT NULL COMMENT '国家ID',
  `province_id` bigint(20) NOT NULL COMMENT '省ID ',
  `city_id` bigint(20) NOT NULL COMMENT '市ID ',
  `county_id` bigint(20) NOT NULL COMMENT '县ID',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址  ',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态 0启用 1禁用',
  `delete_flag` tinyint(1) NULL DEFAULT NULL COMMENT '1删除',
  `description` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `object_version_number` bigint(20) NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '渠道仓库表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
