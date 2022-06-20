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

 Date: 10/06/2022 16:06:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sstock_warehouse_warrant
-- ----------------------------
DROP TABLE IF EXISTS `sstock_warehouse_warrant`;
CREATE TABLE `sstock_warehouse_warrant`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `object_version_number` bigint(20) NULL DEFAULT NULL COMMENT '版本号',
  `code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
  `tenant_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '租户Id',
  `input_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入库类型 STOCK.INPUT_TYPE',
  `returns_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人姓名',
  `returns_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人地址',
  `returns_tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人电话',
  `returns_mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人手机',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `sor_buyer_nike_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '买家昵称',
  `order_msg` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `buy_msg` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户留言',
  `support_logic_warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '供货逻辑仓ID',
  `support_logic_warehouse_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供货逻辑仓名称',
  `support_logic_warehouse_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供货逻辑仓编码',
  `receiving_logic_warehouse_id` bigint(20) NULL DEFAULT NULL COMMENT '收货逻辑仓ID',
  `receiving_logic_warehouse_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货逻辑仓名称',
  `receiving_logic_warehouse_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货逻辑仓编码',
  `store_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺id',
  `store_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售店名',
  `channel_type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道类型 SCHL.CHANNEL_TYPE',
  `real_notice_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收发货通知单编号',
  `front_record_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '前置业务单号类型',
  `front_record_id` bigint(20) NULL DEFAULT NULL COMMENT '前置业务id',
  `front_record_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '前置业务单号',
  `platform_order_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '三方订单号',
  `order_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内部单号',
  `result_code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '入库结果单编码(多条结果单按照,隔开)',
  `status` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态 STOCK.INPUT_STATUS',
  `failure_status` tinyint(1) NULL DEFAULT NULL COMMENT '出库失败附加状态 0退换货时 1中止发货时',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 315147189277003777 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '入库通知单' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
