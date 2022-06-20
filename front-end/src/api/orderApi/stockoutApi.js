/*
 * @Author: your name
 * @Date: 2021-10-28 10:59:27
 * @LastEditTime: 2022-04-25 16:57:39
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\stockoutApi.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/stock/v1',
  items: [
    /** 缺货管理列表 */
    { key: 'getStockoutManageList', url: '/stockout/manage/page', method: 'POST' },
    /** 缺货订单列表 */
    { key: 'getStockoutOrderList', url: '/stockout/order/page', method: 'POST' },
    /** 缺货店铺列表 */
    { key: 'getStockoutShopList', url: '/stockout/shop/page', method: 'POST' },
    /** 批量保存 */
    { key: 'saveStockoutList', url: '/stockout/batch/setting', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
