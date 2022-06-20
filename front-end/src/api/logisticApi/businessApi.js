/* jshint esversion: 9 */
/**
 * Logistics Businesss : 物流业务管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit/v1/logistics-businesss',
  items: [
    { key: 'addLogisticsBusiness', url: '', method: 'POST' },
    { key: 'deleteLogisticsBusiness', url: '', method: 'DELETE' },
    { key: 'updateLogisticsBusiness', url: '', method: 'PUT' },
    { key: 'getLogisticsBusiness', url: '', method: 'GET' },
    { key: 'detailLogisticsBusiness', url: '/', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
