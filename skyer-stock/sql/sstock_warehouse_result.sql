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

 Date: 10/06/2022 16:06:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_warehouse_result
-- ----------------------------
DROP TABLE IF EXISTS `sstock_warehouse_result`;
CREATE TABLE `sstock_warehouse_result`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人ID',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '租户Id',
  `object_version_number` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '版本号 ',
  `code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入库结果单号',
  `warehouse_warrant_id` bigint(20) NULL DEFAULT NULL COMMENT '入库通知单id',
  `warehouse_warrant_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入库通知单号',
  `platform_order_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '三方订单号',
  `order_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内部单号',
  `input_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入库类型 STOCK.INPUT_TYPE',
  `status` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入库状态 STOCK.INPUT_RESULT_STATUS',
  `support_logic_warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '供货逻辑仓ID',
  `support_logic_warehouse_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供货逻辑仓名称',
  `support_logic_warehouse_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供货逻辑仓编码',
  `receiving_logic_warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '收货逻辑仓ID',
  `receiving_logic_warehouse_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货逻辑仓名称',
  `receiving_logic_warehouse_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货逻辑仓编码',
  `store_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
  `store_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺名',
  `channel_type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道类型 SCHL.CHANNEL_TYPE',
  `returns_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人姓名',
  `returns_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人地址',
  `returns_tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人电话',
  `returns_mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人手机',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `logistics_company_id` bigint(60) NULL DEFAULT NULL COMMENT '退货物流公司id',
  `logistics_company_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货物流公司名称',
  `logistics_no` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货物流单号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 315147193173512193 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '入库结果单' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
