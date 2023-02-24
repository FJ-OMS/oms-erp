/* jshint esversion: 9 */
/**
 * move record : 移仓单
 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1/move-records',
  items: [
    // 创建或者更新调整单
    { key: 'addMoveRecords', url: '/move-record', method: 'PUT' },
    // 分页
    { key: 'getMoveRecords', url: '/page', method: 'POST' },
    // 审核调整单   审核状态 0待审核1审核成功2审核失败
    // {"id":0,"status":0,"typeCode":"string"}
    { key: 'statusAllMoveRecords', url: '/move-record-status', method: 'PATCH' },
    // [{"id":0,"status":0,"typeCode":"string"}]
    { key: 'statusOneMoveRecords', url: '/move-record-detail-status', method: 'PATCH' },
    // 详情
    { key: 'detailMoveRecords', url: '/detail/', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
