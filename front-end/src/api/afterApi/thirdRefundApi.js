/*
 * @Author: your name
 * @Date: 2021-11-03 17:08:17
 * @LastEditTime: 2022-02-24 13:28:45
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: 三方售后订单
 * @FilePath: \newBj-admin\src\api\orderApi\audit.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/sord/v1',
  items: [
    // 创建第三方售后单头
    {
      key: 'addThirdRefundOrder',
      url: '/third-party-after-sale-headers/create-thirdParty-refund',
      method: 'POST',
      loadingMore: 0,
    },

    // 第三方售后单头列表
    {
      key: 'getThirdRefundOrder',
      url: '/third-party-after-sale-headers/order-page',
      method: 'POST',
      loadingMore: 0,
    },
    // 第三方售后单明细-退货/退款
    {
      key: 'detailThirdRefundOrder',
      url: '/third-party-after-sale-headers/{id}',
      method: 'GET',
      loadingMore: 0,
    },

    // 第三方售后单明细-退货/退款
    {
      key: 'detailThirdRefundOrder',
      url: '/third-party-after-sale-headers/order-base-detail-by-id-code',
      method: 'POST',
      loadingMore: 0,
    },
  ],
};

export default {
  ...generate(config),
};
