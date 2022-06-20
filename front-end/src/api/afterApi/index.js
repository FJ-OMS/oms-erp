/*
 * @Author: 订单模块
 * @Date: 2021-10-19 10:27:31
 * @LastEditTime: 2021-11-03 20:08:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\index.js
 */
import internalRefundApi from './internalRefundApi';
import thirdRefundApi from './thirdRefundApi';

export default {
  ...internalRefundApi,
  ...thirdRefundApi,
};
