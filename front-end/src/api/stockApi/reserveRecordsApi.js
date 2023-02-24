/* jshint esversion: 9 */
/**
 * reserve-record-controller :

 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1/reserve-records',
  items: [
    // 创建或者更新
    { key: 'addReserveRecords', url: '/reserve', method: 'POST' },
    // 分页
    { key: 'getReserveRecords', url: '/page', method: 'POST' },
    // 审核  审核状态 STOCK.RECORD_STATUS ,typeCode (string, optional): 值集 STOCK.RECORD_TYPE
    { key: 'statusReserveRecords', url: '/reserve-status', method: 'POST' },
    // 详情
    { key: 'detailReserveRecords', url: '/detail/', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
