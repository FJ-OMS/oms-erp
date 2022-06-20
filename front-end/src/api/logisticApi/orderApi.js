/* jshint esversion: 9 */
/**
 * Logistics Orders : 物流商管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit/v1/logistics-orders',
  items: [
    { key: 'addLogisticsOrder', url: '', method: 'POST' },
    { key: 'deleteLogisticsOrder', url: '', method: 'DELETE' },
    { key: 'updateLogisticsOrder', url: '', method: 'PUT' },
    { key: 'getLogisticsOrderSales', url: '/sales', method: 'GET' },
    { key: 'getLogisticsOrderAfter', url: '/customer-service', method: 'GET' },
    { key: 'detailLogisticsOrder', url: '/', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
