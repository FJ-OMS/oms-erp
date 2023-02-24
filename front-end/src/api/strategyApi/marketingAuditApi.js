/*
 * @Author: your name
 * @Date: 2021-10-28 20:58:39
 * @LastEditTime: 2021-11-01 17:26:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\strategyApi\marketingAuditApi.js
 */
/**
 * Postage Template : 销售审核策略
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: 'sord/v1',
  items: [
    /** 订单审核列表 */
    { key: 'getOrderAuditSetting', url: '/order-audit-setting', method: 'GET' },
    /** 删除订单审核 */
    { key: 'deleteOrderAuditSetting', url: '/order-audit-setting', method: 'DELETE' },
    /** 创建订单审核 */
    { key: 'addOrderAuditSetting', url: '/order-audit-setting', method: 'POST' },
    /** 修改订单审核 */
    { key: 'putOrderAuditSetting', url: '/order-audit-setting', method: 'PUT' },
    /** 订单审核明细 */
    { key: 'getOrderAuditSettingDetail', url: '/order-audit-setting/{id}', method: 'GET' },

    /**
     * 商品行部分
     */

    /** 获取商品行审核列表 */
    { key: 'getGoodLineAuditSetting', url: '/goods-line-audit-setting', method: 'GET' },
    /** 创建商品行审核  */
    { key: 'addGoodLineAuditSetting', url: '/goods-line-audit-setting', method: 'POST' },
    /** 删除商品行审核 */
    { key: 'deleteGoodLineAuditSetting', url: '/goods-line-audit-setting', method: 'DELETE' },
    /** 修改商品行审核 */
    { key: 'putGoodLineAuditSetting', url: '/goods-line-audit-setting', method: 'PUT' },
    /** 商品行审核明细 */
    { key: 'getGoodLineAuditSettingDetail', url: '/goods-line-audit-setting/{id}', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
