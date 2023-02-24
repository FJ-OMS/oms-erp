/*
 * @Author: your name
 * @Date: 2021-10-19 10:27:23
 * @LastEditTime: 2022-02-18 10:47:15
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\internal.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: 'stock/v1',
  items: [
    /** 根据订单信息获取相关的逻辑仓集合 */
    { key: 'getOrderSourcingLogicWarehouse', url: '/sourcing/get/logic/warehouse', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
