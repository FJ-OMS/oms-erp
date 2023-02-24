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

 Date: 10/06/2022 16:06:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_result_record
-- ----------------------------
DROP TABLE IF EXISTS `sstock_result_record`;
CREATE TABLE `sstock_result_record`  (
  `id` bigint(20) UNSIGNED NOT NULL COMMENT '主键',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人ID',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '租户Id',
  `object_version_number` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '版本号 ',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `order_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `result_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果单编码',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态 0全部出库',
  `receive_warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '收货逻辑仓id',
  `receive_warehouse_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货逻辑仓编码',
  `receive_warehouse_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货逻辑仓名称',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `odo_id` bigint(20) NULL DEFAULT NULL COMMENT '出库通知单id(sstock_outbound_delivery_order.id)',
  `odo_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出库通知单号',
  `wms_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'wms单号',
  `shipping_no` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运单号',
  `delivery_type` tinyint(2) NULL DEFAULT NULL COMMENT '收发类型 0:发1:收',
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人地址',
  `receiver_tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人手机',
  `receiver_country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `receiver_province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `receiver_city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `receiver_district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `out_logic_warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '发货逻辑仓ID',
  `out_logic_warehouse_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货逻辑仓名称',
  `out_logic_warehouse_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货逻辑仓编码',
  `logistic_company` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司',
  `logistic_company_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流编号',
  `logistic_company_id` bigint(20) NULL DEFAULT NULL COMMENT '物流ID',
  `logistic_business` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流业务',
  `logistic_business_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流业务编码',
  `logistic_business_id` bigint(20) NULL DEFAULT NULL COMMENT '物流业务ID',
  `shop_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺ID',
  `shop_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺编码',
  `shop_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `channel_type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道类型',
  `out_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出库类型',
  `package_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '包裹单号',
  `postage` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '运费',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收发货结果单' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
