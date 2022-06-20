/*
 * @Author: your name
 * @Date: 2021-10-19 10:27:23
 * @LastEditTime: 2021-11-12 17:30:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\internal.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/sord/v1',
  items: [
    /**   预占管理   */
    // 列表
    { key: 'getPreemptionOrders', url: '/preemp-notice/preemp-page', method: 'POST' },
    // 详情-基本信息
    {
      key: 'getPreemptionOrderDetail',
      url: '/preemp-notice/preemp-notice-details/',
      method: 'GET',
    },
    // 详情-SKU汇总
    {
      key: 'getPreemptionOrderSkuListByOrder',
      url: '/preemp-notice/items-page',
      method: 'POST',
    },
    // 详情-商品行预占统计
    {
      key: 'getPreemptionOrderSkuCountByOrder',
      url: '/preemp-notice/item-lines-count',
      method: 'POST',
      loadingHide: true,
    },
    // 详情-商品行列表
    {
      key: 'getPreemptionOrderSkuLineListByOrder',
      url: '/preemp-notice/item-lines-page',
      method: 'POST',
    },
    //  详情-展开行：根据itemId获取平摊商品行列表（某订单的某SKU的商品行列表）
    {
      key: 'getPreemptionOrderSkuLineListBySku',
      url: '/preemp-notice/item-lines/{itemId}',
      method: 'GET',
      loadingHide: true,
    },
    //  重新预占
    {
      key: 'rePreemptOrderByNoticeId',
      url: '/preemp-notice/re-preemption?noticeId={noticeId}', // noticeId
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
