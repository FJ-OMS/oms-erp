/**
 *
 * after-sales-reason-controller
 *
 *
POST /v1/after-sales-reasons/create
创建售后原因

DELETE /v1/after-sales-reasons/delete
删除售后原因

GET /v1/after-sales-reasons/detail/{id}
售后原因明细

GET /v1/after-sales-reasons/list
售后原因列表

GET /v1/after-sales-reasons/page
售后原因分页

PUT /v1/after-sales-reasons/update
修改售后原因
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/sord/v1/after-sales-reasons',
  items: [
    { key: 'getAfterSalesReasonsList', url: '/list', method: 'GET' },
    { key: 'getAfterSalesReasons', url: '/page', method: 'GET' },
    { key: 'addAfterSalesReasons', url: '/create', method: 'POST' },
    { key: 'deleteAfterSalesReasons', url: '/delete', method: 'DELETE' },
    { key: 'detailAfterSalesReasons', url: '/detail/{id}', method: 'GET' },
    { key: 'updateAfterSalesReasons', url: '/update', method: 'PUT' },
  ],
};

export default {
  ...generate(config),
};
