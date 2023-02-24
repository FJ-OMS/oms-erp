/*
 * @Author: 出/入库管理
 * @Date: 2021-08-27 14:48:35
 * @LastEditTime: 2021-11-03 14:45:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\stockApi\record.js
 */
/** 出入库管理 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1',
  items: [
    /** 出入库通知单 */
    { key: 'getNoticRecords', url: '/outbound/delivery/order/page', method: 'POST' },
    /** 出入库通知单详情 */
    { key: 'getNoticRecordsDetail', url: '/outbound/delivery/order/detail/{id}', method: 'POST' },
    /** 出入库结果单详情 */
    { key: 'getResultRecordsDetail', url: '/result-records/detail/{resultCode}', method: 'get' },
    /** 内部订单详情中的发货单列表 */
    { key: 'getOrderNoticRecords', url: '/outbound/delivery/order/order', method: 'POST', type: 'form' },
  ],
};
export default {
  ...generate(config),
};
