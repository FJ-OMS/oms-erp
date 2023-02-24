/* jshint esversion: 9 */
/**
 * Logistics Companys : 物流商管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit/v1/logistics-companys',
  items: [
    { key: 'addLogisticsCompany', url: '', method: 'POST' },
    { key: 'deleteLogisticsCompany', url: '', method: 'DELETE' },
    { key: 'updateLogisticsCompany', url: '', method: 'PUT' },
    { key: 'getLogisticsCompany', url: '', method: 'GET' },
    { key: 'detailLogisticsCompany', url: '/', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
