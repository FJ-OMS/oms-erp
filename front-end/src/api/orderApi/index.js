/*
 * @Author: 订单模块
 * @Date: 2021-10-19 10:27:31
 * @LastEditTime: 2022-04-21 15:36:00
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\index.js
 */
import internalApi from './internalApi';
import stockoutApi from './stockoutApi';
import auditApi from './auditApi';
import processApi from './processApi';
import sourcingApi from './sourcingApi';
import preemptApi from './preemptApi';
import itemPriceApi from './itemPriceApi';
import thirdApi from './thirdApi';

export default {
  ...internalApi,
  ...stockoutApi,
  ...processApi,
  ...auditApi,
  ...sourcingApi,
  ...preemptApi,
  ...itemPriceApi,
  ...thirdApi,
};
