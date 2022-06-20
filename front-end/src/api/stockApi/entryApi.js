/* jshint esversion: 9 */
/**
 * reserve-record-controller :

 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1',
  items: [
    // 入库通知单详情
    { key: 'detailEntryInform', url: '/warehouse/warrants/detail', method: 'GET' },

    // 入库通知单列表
    { key: 'getEntryInform', url: '/warehouse/warrants/page', method: 'POST' },

    // 入库通知单商品列表
    { key: 'getEntryInformDetailGoods', url: '/warehouse/warrant/details/page', method: 'POST' },

    // 入库结果单详情
    { key: 'detailEntryResult', url: '/warehouse-results/detail', method: 'GET' },

    // 入库结果单列表
    { key: 'getEntryResult', url: '/warehouse-results/page', method: 'POST' },

    // 入库结果单商品列表
    { key: 'getEntryResultDetailGoods', url: '/warehouse-result-details/page', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
