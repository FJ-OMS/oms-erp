/* jshint esversion: 9 */
/**
 * Freight Space Manage : 仓位管理
 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1/freight/spaces',
  items: [
    // 分页
    { key: 'getDistributeSpaces', url: '/page', method: 'POST' },
    { key: 'getDistributeSpacesRatio', url: '/ratio/page', method: 'POST' },

    // 创建或者更新
    { key: 'addDistributeSpacesQty', url: '/qty/save', method: 'POST' },
    { key: 'addDistributeSpacesRatio', url: '/ratio/save', method: 'POST' },
    // 批量保存
    { key: 'addBatchDistributeSpacesQty', url: '/qty/batch/save', method: 'POST' },
    { key: 'addBatchDistributeSpacesRatio', url: '/ratio/batch/save', method: 'POST' },

    // 详情
    { key: 'detailDistributeSpaces', url: '/detail/', method: 'POST' },

    // 仓位分配-可配置商品信息列表
    {
      key: 'getMayDistributeSpacesGoods',
      url: '/may/setting/goods/page',
      method: 'POST',
    },
    // 仓位分配-已分配商品信息列表
    {
      key: 'getDistributeSpacesGoods',
      url: '/setting/goods/page',
      method: 'POST',
    },
    // 仓位分配->批量删除商品和仓库的关联明细
    {
      key: 'deleteDistributeSpacesGoods',
      url: '/delete/goods',
      method: 'POST',
    },
    // 仓位分配->批量分配->已分配接收仓列表
    { key: 'getDistributeSpacesWarehouse', url: '/receive/warehouse/page', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
