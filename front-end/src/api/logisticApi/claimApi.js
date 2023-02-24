/*
 * @Author: your name
 * @Date: 2021-09-06 14:05:56
 * @LastEditTime: 2021-09-14 22:05:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\logisticApi\claim.js
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit/v1/breakage/claims',
  items: [
    /** 破损理赔单列表 */
    { key: 'getLogisticClaim', url: '/page', method: 'get' },
    /** 破损理赔单明细 */
    { key: 'getLogisticClaimDetail', url: '/detail/{serialNum}', method: 'get' },
    /** 修改理赔单状态 */
    { key: 'updataLogisticClaimStatus', url: '/orderStatus', method: 'PATCH' },
    /** 修改理赔单 */
    { key: 'updataLogisticClaim', url: '/update', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
