/* jshint esversion: 9 */
/**
 * reserve-record-controller :

 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1/change-logs',
  items: [
    // 创建或者更新
    { key: 'addChangeRecords', url: '/reserve', method: 'POST' },
    // 分页
    { key: 'getChangeRecords', url: '/page', method: 'POST' },
    // 审核  审核状态 STOCK.RECORD_STATUS ,typeCode (string, optional): 值集 STOCK.RECORD_TYPE
    { key: 'statusChangeRecords', url: '/reserve-status', method: 'POST' },
    // 详情
    { key: 'detailChangeRecords', url: '/detail/', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
