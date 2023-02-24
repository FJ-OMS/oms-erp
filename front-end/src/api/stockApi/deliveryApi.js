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
    // 出库通知单列表
    { key: 'getOutboundDelivery', url: '/outbound/delivery/order/page', method: 'POST' },
    // 出库通知单详情
    {
      key: 'getOutboundDeliveryDetail',
      url: '/outbound/delivery/order/detail/{id}',
      method: 'GET',
    },
    // 出库通知单商品信息分页
    {
      key: 'getOutboundDeliveryDetailPage',
      url: '/outbound/delivery/order/detail/page',
      method: 'POST',
    },
    // 统计出库通知单发货及已发货数量
    {
      key: 'getOutboundDeliveryDetailStats',
      url: '/outbound/delivery/order/detail/stats/',
      method: 'POST',
      type: 'query',
    },
    // 出库结果单分页
    {
      key: 'getResultRecordsPage',
      url: '/result-records/page',
      method: 'POST',
    },
    // 出库结果单详情
    {
      key: 'getResultRecordsDetail',
      url: '/result-records/{id}',
      method: 'GET',
    },
    // 出库结果单商品信息分页
    {
      key: 'getResultDetailPage',
      url: '/result-detail/page',
      method: 'POST',
    },
    // 统计出库通知单发货及已发货数量
    {
      key: 'getResultDetailStatus',
      url: '/result-detail/stats/{resultId}',
      method: 'GET',
    },
  ],
};
export default {
  ...generate(config),
};
