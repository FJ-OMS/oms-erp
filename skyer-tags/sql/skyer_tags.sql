/*
 Navicat Premium Data Transfer

 Source Server         : root豹骥开发环境
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.124.199:3306
 Source Schema         : skyer_tags

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 22/01/2022 14:17:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tags_label
-- ----------------------------
DROP TABLE IF EXISTS `tags_label`;
CREATE TABLE `tags_label`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `label_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `label_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签类型',
  `module_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块',
  `label_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签code',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `text_content` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签文本内容',
  `text_color` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签文本颜色',
  `system_label_flag` tinyint(1) NULL DEFAULT NULL COMMENT '是否系统标签',
  `change_flag` tinyint(1) NULL DEFAULT NULL COMMENT '是否可挪移',
  `creation_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `created_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人ID',
  `last_updated_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人ID',
  `object_version_number` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 272729207143043073 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (1, '手工单', '0', NULL, 'SY001', 1, '手工单', 'orange', 1, 0, '2022-01-22 13:18:04', '2022-01-24 16:26:40', 1, 1, 1);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (2, '复制单', '1', NULL, 'SY002', 1, '复制单', 'orange', 1, 0, '2022-01-22 14:04:12', '2022-01-22 14:04:12', 1, 1, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (3, '补发单', '2', NULL, 'SY003', 1, '补发单', 'orange', 1, 0, '2022-01-22 14:04:14', '2022-01-22 14:04:14', 1, 1, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (4, '退货订单', '3', NULL, 'SY004', 1, '退货订单', 'orange', 1, 0, '2022-01-22 14:04:15', '2022-01-22 14:04:15', 1, 1, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (5, '退款订单', '4', NULL, 'SY005', 1, '退款订单', 'orange', 1, 0, '2022-01-22 14:04:45', '2022-01-24 11:22:29', 1, 198487575806873600, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (6, '订单换货', '5', NULL, 'SY006', 1, '订单换货', 'orange', 1, 0, '2022-01-22 13:18:04', '2022-01-22 15:56:43', 1, 1, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (7, '订单缺货', '6', NULL, 'SY007', 1, '订单缺货', 'orange', 1, 0, '2022-01-22 13:18:04', '2022-01-22 15:56:43', 1, 1, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (8, '订单异常', '7', NULL, 'SY008', 1, '订单异常', 'orange', 1, 0, '2022-01-22 13:18:04', '2022-01-22 15:56:43', 1, 1, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (9, '订单异常', '7', NULL, 'SY009', 0, '订单异常', 'orange', 1, 0, '2022-01-22 13:18:04', '2022-01-24 17:09:11', 1, 1, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (10, '赠品', '8', NULL, 'SY010', 0, '赠品', 'orange', 1, 0, '2022-01-22 13:18:04', '2022-01-24 17:09:11', 1, 1, NULL);
INSERT INTO `skyer_tags`.`tags_label`(`id`, `label_name`, `label_type`, `module_code`, `label_code`, `status`, `text_content`, `text_color`, `system_label_flag`, `change_flag`, `creation_date`, `last_update_date`, `created_by`, `last_updated_by`, `object_version_number`) VALUES (11, '随品', '8', NULL, 'SY011', 0, '随品', 'orange', 1, 0, '2022-01-22 13:18:04', '2022-01-24 17:09:11', 1, 1, NULL);
