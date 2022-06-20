/*
 * @Author: your name
 * @Date: 2021-10-19 10:27:23
 * @LastEditTime: 2022-04-24 09:56:32
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\internal.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/sord/v1',
  items: [
    // 创建第三方订单头
    { key: 'addThirdOrderByHand', url: '/third-order/create', method: 'POST' },
    // 获取第三方订单列表
    { key: 'getThirdOrderPage', url: '/third-order/page', method: 'POST' },
    // 获取第三方订单详情
    { key: 'getThirdOrderDetail', url: '/third-order/detail', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
