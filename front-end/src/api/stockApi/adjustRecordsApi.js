/* jshint esversion: 9 */
/**
 *
 *  adjust record : 调整单
 *
 *  数据同步管理

GET /v1/adjust-records
查看调整单详情

POST /v1/adjust-records/page
调整单分页
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1/adjust-records',
  items: [
    // 创建或者更新调整单
    { key: 'addAdjustRecords', url: '/adjust-record', method: 'PUT' },
    // 分页
    { key: 'getAdjustRecords', url: '/page', method: 'POST' },
    // 审核调整单
    { key: 'statusAdjustRecords', url: '/adjust-status', method: 'POST' },
    // 详情
    { key: 'detailAdjustRecords', url: '/detail/', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
