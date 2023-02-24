/* jshint esversion: 9 */
/**
 * allot record : 调拨单
 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1',
  items: [
    // 创建或者更新调整单
    { key: 'addAllotRecords', url: '/allot-records/allot-record', method: 'PUT' },
    // 调拨单分页
    { key: 'getAllotRecords', url: '/allot-records/page', method: 'POST' },
    // 审核调整单   审核状态 0待审核1审核成功2审核失败
    // {"id":0,"status":0,"typeCode":"string"}
    { key: 'statusAllAllotRecords', url: '/allot-records/allot-record-status', method: 'PATCH' },
    // 详情
    { key: 'detailAllotRecords', url: '/allot-records/detail/', method: 'GET' },

    /** 新版接口开发者：许荣发 */
    // 调拨单详情
    { key: 'getAllotRecordsDetail', url: '/allot-records/detail/{allotId}', method: 'GET' },
    // 创建更新调拨单
    { key: 'saveAllotRecords', url: '/allot-records/save', method: 'PUT' },
    // 获取商品对应逻辑仓库存信息
    { key: 'getAllotRecordsSkuStock', url: '/allot-records/list/sku/allot/stock', method: 'POST' },
    // 取消
    { key: 'cancelAllotRecords', url: '/allot-records/cancel', method: 'PATCH', type: 'query' },
    // 作废
    { key: 'invalidAllotRecords', url: '/allot-records/invalid', method: 'PATCH', type: 'query' },
    // 重置
    { key: 'resetAllotRecords', url: '/allot-records/reset', method: 'PATCH', type: 'query' },
    // 执行/再次执行
    { key: 'executeAllotRecords', url: '/allot-records/execute', method: 'PATCH', type: 'query' },
    // 审核
    { key: 'auditAllotRecords', url: '/allot-records/audit', method: 'PATCH' },

    /* **********************
      分仓计划部分
     *************************/

    // 分仓计划列表
    { key: 'getDistributePlansPage', url: '/distribute/plans/page', method: 'POST' },
    // 分仓计划详情
    { key: 'getDistributePlansDetail', url: '/distribute/plans/detail/{id}', method: 'GET' },
    // 创建或者更新分仓计划
    { key: 'saveDistributePlans', url: '/distribute/plans/save', method: 'PUT' },
    // 获取商品对应库存信息
    { key: 'getDistributePlansSkuStock', url: '/distribute/plans/list/sku/stock', method: 'POST' },
    // 取消
    {
      key: 'cancelDistributePlan',
      url: '/distribute/plans/cancel',
      method: 'PATCH',
      type: 'query',
    },
    // 作废
    {
      key: 'invalidDistributePlan',
      url: '/distribute/plans/invalid',
      method: 'PATCH',
      type: 'query',
    },
    // 重置
    { key: 'resetDistributePlan', url: '/distribute/plans/reset', method: 'PATCH', type: 'query' },
    // 审核
    { key: 'auditDistributePlan', url: '/distribute/plans/audit', method: 'PATCH' },

    /* **********************
       虚仓移仓部分部分
    *************************/

    // 虚仓移仓列表
    { key: 'getDistributeVirtualsPage', url: '/transfer-records/page', method: 'POST' },
    // 虚仓移仓详情
    { key: 'getDistributeVirtualsDetail', url: '/transfer-records/detail/{id}', method: 'GET' },
    // 创建或者更新虚仓移仓
    { key: 'saveDistributeVirtuals', url: '/transfer-records/save', method: 'PUT' },
    // 获取商品对应库存信息
    {
      key: 'getDistributeVirtualsSkuStock',
      url: '/transfer-records/list/sku/transfer/stock',
      method: 'POST',
    },
    // 取消
    {
      key: 'cancelDistributeVirtuals',
      url: '/transfer-records/cancel',
      method: 'PATCH',
      type: 'query',
    },
    // 作废
    {
      key: 'invalidDistributeVirtuals',
      url: '/transfer-records/invalid',
      method: 'PATCH',
      type: 'query',
    },
    // 重置
    {
      key: 'resetDistributeVirtuals',
      url: '/transfer-records/reset',
      method: 'PATCH',
      type: 'query',
    },
    // 执行/再次执行
    {
      key: 'executeDistributeVirtuals',
      url: '/transfer-records/execute',
      method: 'PATCH',
      type: 'query',
    },
    // 审核
    {
      key: 'auditDistributeVirtuals',
      url: '/transfer-records/audit',
      method: 'PATCH',
    },

    // // 查询分仓计划缺货订单
    // {
    //   key: 'getDistributePlanStockoutList',
    //   url: '/stockout/list/order/stockout',
    //   method: 'POST',
    // },

    // 分仓计划缺货订单管理
    {
      key: 'getDistributePlanShopStockoutList',
      url: '/distribute/plan/shop/stockout/list',
      method: 'POST',
    },
    // 虚仓移仓详情管理
    {
      key: 'getTransferStockoutDetailsList',
      url: '/transfer-stockout-details/list',
      method: 'POST',
    },
  ],
};
export default {
  ...generate(config),
};
