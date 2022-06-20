/*
 * @Author: your name
 * @Date: 2021-11-03 17:08:17
 * @LastEditTime: 2022-02-24 13:28:45
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\audit.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/sord/v1/',
  items: [
    { key: 'getWaitAuditOrders', url: '/order-header/wait-audit-orders', method: 'GET' },
    {
      key: 'getWaitAuditOrdersTabs',
      url: '/order-flow-config/get-order-flow-confiy-by-tenantId/{tenantId}',
      method: 'GET',
    },
    // { key: 'AuditWaitAuditOrders', url: '/order-header/audit-order', method: 'PUT' },
    /** 订单审核列表 */
    { key: 'getOrderAuditPage', url: '/order/order-audit-page', method: 'POST' },
    /** 订单审核 */
    { key: 'orderAuditdeal', url: '/audit/deal', method: 'POST', loadingMore: 999 },

    // 获取订单Item列表（某订单的SKU汇总）
    {
      key: 'getInternalSkuListByAuditId',
      url: '/audit/order-item-page',
      method: 'POST',
    },
    //  获取订单平摊行列表（某订单的商品行列表）
    { key: 'getInternalSkuLineListByAuditId', url: '/audit/item-line-page', method: 'POST' },
    //  展开行：根据itemId获取平摊商品行列表（某订单的某SKU的商品行列表）
    {
      key: 'getInternalSkuLineListByAuditIdSku',
      url: '/audit/item-line-details/{itemId}/{auditId}',
      method: 'GET',
      loadingHide: true,
    },
  ],
};

export default {
  ...generate(config),
};
