/*
 * @Author: your name
 * @Date: 2021-09-08 17:20:28
 * @LastEditTime: 2022-02-18 15:26:59
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\logisticApi\freightApi.js
 */
/* jshint esversion: 9 */
/**
 * Postage Template : 运费模板
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit/v1/postage-templates',
  items: [
    { key: 'addLogisticsFreightTemplate', url: '', method: 'POST' },
    { key: 'deleteLogisticsFreightTemplate', url: '', method: 'DELETE' },
    { key: 'updateLogisticsFreightTemplate', url: '', method: 'PUT' },
    { key: 'getLogisticsFreightTemplate', url: '', method: 'GET' },
    { key: 'detailLogisticsFreightTemplate', url: '/', method: 'GET' },
    { key: 'operationLogisticsFreightTemplate', url: '/enabled', method: 'PUT' },

    /** 模板适用物流业务、仓库 */
    {
      key: 'getLogisticsFreightTemplateBusinessStorehouse',
      url: '/business-storehouse',
      method: 'GET',
    },
    /** 查询模板已经绑定的仓库 */
    {
      key: 'getBusinessStorehouse',
      url: '/business-storehouse',
      method: 'GET',
    },
    /** 根据运费模板关联关系查物流商和物业业务 */
    {
      key: 'getQueryLogistics',
      url: '/query/logistics',
      method: 'POST',
    },
  ],
};
export default {
  ...generate(config),
};
