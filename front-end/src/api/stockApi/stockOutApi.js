/*
 * @Author: your name
 * @Date: 2021-10-26 10:11:25
 * @LastEditTime: 2022-04-25 16:59:08
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\stockApi\structureApi.js
 */
/**
 *
 * StockOut Manage : 缺货管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1',
  items: [
    // 缺货店铺列表
    { key: 'getStockOutShopPage', url: '/stockout/shop/page', method: 'POST' },
    // 缺货订单列表
    { key: 'getStockOutOrderPage', url: '/stockout/order/page', method: 'POST' },
    // 缺货订单待发货列表
    { key: 'getStockOutWaitPage', url: '/stockout/wait/page', method: 'POST' },
    // 缺货订单——发货
    { key: 'sendStockOutWait', url: '/stockout/send/{id}', method: 'POST' },
    // 批量设置保存
    { key: 'stockOutBatSetting', url: '/stockout/batch/setting', method: 'POST' },
    // 添加补货店铺数据
    { key: 'stockOutAdd', url: '/stockout/add', method: 'POST' },
    /** 发货汇总 */
    { key: 'getReportLogisticsResult', url: '/report/form/logistics/result', method: 'POST' },
  ],
};

// POST /v1/stockout/manage/page
// 缺货管理列表

export default {
  ...generate(config),
};
