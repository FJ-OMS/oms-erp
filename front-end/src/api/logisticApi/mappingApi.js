/* jshint esversion: 9 */
/**
 * logistics-mapping : 物流商管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit/v1/logistics/mappings',
  items: [
    { key: 'addLogisticsMapping', url: '', method: 'POST' },
    { key: 'deleteLogisticsMapping', url: '/deletes', method: 'PATCH' },
    { key: 'updateLogisticsMapping', url: '', method: 'PUT' },
    { key: 'getLogisticsMapping', url: '/page', method: 'GET' },
    { key: 'detailLogisticsMapping', url: '/', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
