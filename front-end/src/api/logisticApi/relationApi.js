/* jshint esversion: 9 */
/**
 * logistics-mapping : 物流商管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit/v1/logistics/relations',
  items: [
    { key: 'addLogisticsRelation', url: '', method: 'POST' },
    { key: 'deleteLogisticsRelation', url: '/deletes', method: 'PATCH' },
    { key: 'updateLogisticsRelation', url: '/update', method: 'POST' },
    { key: 'getLogisticsRelation', url: '', method: 'GET' },
    { key: 'detailLogisticsRelation', url: '/', method: 'GET' },
    { key: 'getLogisticsWarehouse', url: '/warehouse', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
