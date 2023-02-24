/*
 * @Author: your name
 * @Date: 2021-10-19 10:27:23
 * @LastEditTime: 2022-04-25 15:36:26
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\internal.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/sord/v1',
  items: [
    // 创建手工单
    { key: 'addInternalOrderByHand', url: '/order/hand-order', method: 'POST', loadingMore: 999 },
    // 复制手工单
    {
      key: 'addInternalOrderByCopy',
      url: '/order/copy-order?innerOrderNo={innerOrderNo}',
      method: 'GET',
    }, // innerOrderNo
    // 内部订单列表
    { key: 'getInternalPage', url: '/order/order-page', method: 'POST' },
    // 订单详情订单基本信息
    { key: 'getInternalDetail', url: '/order/order-base-detail/{orderId}', method: 'GET' },
    // 订单详情订单基本信息{"orderCode": "string","orderId": 0}
    {
      key: 'getInternalDetailByIdCode',
      url: '/order/order-base-detail-by-id-code',
      method: 'POST',
    },
    // 获取订单Item列表（某订单的SKU汇总）
    {
      key: 'getInternalSkuListByOrder',
      url: '/order/order-item-page',
      method: 'POST',
    },
    //  获取订单平摊行列表（某订单的商品行列表）
    { key: 'getInternalSkuLineListByOrder', url: '/order/item-line-page', method: 'POST' },
    //  展开行：根据itemId获取平摊商品行列表（某订单的某SKU的商品行列表）

    {
      key: 'getInternalSkuLineListBySku',
      url: '/order/item-line-details/{itemId}',
      method: 'GET',
      loadingHide: true,
    },
    /** 订单审核（通过，拒绝） */
    { key: 'auditOrder', url: '/order-header/audit-order', method: 'PUT' },
    /** 订单商品行审核(通过, 拒绝) */
    { key: 'auditGoodsLineAuditOrder', url: '/order-header/goods-line-audit-order', method: 'PUT' },
    /** 订单挂起/解挂 */
    { key: 'upOrderHandUpOrder', url: '/order-header/order-hand-up', method: 'PUT' },
    /** 订单商品行挂起/解挂 */
    { key: 'upGoodsLineHandUpOrder', url: '/order-header/goods-line-hand-up', method: 'PUT' },

    /** 订单基础信息的修改 */
    { key: 'updateOrderBase', url: '/order/order-base', method: 'PUT' },

    /** 订单取消 普通订单 */
    { key: 'cancelInternalOrder', url: '/order/cancel-order', method: 'PATCH' },
    /** 订单取消 O2O */
    { key: 'cancelInternalO2oOrder', url: '/order/cancel-o2o-order', method: 'PATCH' },
    /** 订单级挂起 */
    { key: 'suspendOrderByOrderId', url: '/order/suspend-order', method: 'POST' },
    /** 订单级解挂 */
    { key: 'unsuspendOrderByOrderId', url: '/order/unsuspend-order', method: 'POST' },
    /** 商品行级挂起/解挂 */
    { key: 'suspendOrderByItemLine', url: '/order/itemLine-suspend-order', method: 'POST' },
    /** 商品行级预占/解占 */
    { key: 'preemptionOrderByItemLine', url: '/order/re-preemption', method: 'POST' },

    /** 订单级-确认收货 普通订单 */
    { key: 'confirmReceiptByOrderId', url: '/order/confirm-receipt/', method: 'PATCH' },

    /** 订单级-确认收货 o2o */
    { key: 'o2oConfirmReceiptByOrderId', url: '/order/o2o-confirm-receipt', method: 'POST' },

    /** 商品行级-确认收货 */
    { key: 'confirmReceiptByItemLine', url: '/order/item-confirm-receipt', method: 'POST' },

    // 内部订单缺货管理列表
    { key: 'getInternalStockoutPage', url: '/order/stockout-page', method: 'POST' },

    /** 缺货统计列表 */
    { key: 'getOrderStatsStockout', url: '/order/stats/stockout', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
