/*
 * @Author: your name
 * @Date: 2021-09-08 11:16:34
 * @LastEditTime: 2021-09-14 19:44:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\logisticApi\abnormalApi.js
 */
/** 物流异常 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit/v1/logistics/abnormal',
  items: [
      { key: 'getLogisticAbnormal', url: '', method: 'get' },
      { key: 'getLogisticRefreshConfig', url: '/getRefreshConfig', method: 'get' },
      { key: 'updateRefreshConfig', url: '/updateRefreshConfig', method: 'PUT' },
    ],
};
export default {
  ...generate(config),
};
