/*
 * @Author: your name
 * @Date: 2021-11-19 15:40:59
 * @LastEditTime: 2021-11-19 15:44:47
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \newBj-admin\src\api\stockApi\abnormalApi.js
 */
/* jshint esversion: 9 */
/**
 *
 *  adjust record : 库存管理->异常库存管理
 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1',
  items: [
    /* 快速补货 */
    { key: 'processServiceFastReplenish', url: '/process/service/fast/replenish', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
