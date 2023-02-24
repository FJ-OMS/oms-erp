/**
 *
 * rule-controller : Rule Controller
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1',
  items: [
    // 库存规则明细
    { key: 'detailRules', url: '/rules/detail', method: 'GET' },

    // 创建与修改库存规则
    { key: 'updateRules', url: '/rules/save', method: 'POST' },

    /** Surplus Qty Replenish Manage : 剩余数量优先补货管理 */

    { key: 'deleteSurplusQtyReplenish', url: '/surplus/qty/replenishs', method: 'DELETE' },

    { key: 'getSurplusQtyReplenish', url: '/surplus/qty/replenishs', method: 'get' },

    { key: 'addSurplusQtyReplenish', url: '/surplus/qty/replenishs', method: 'POST' },

    {
      key: 'operationSurplusQtyReplenish',
      url: '/surplus/qty/replenishs/operation',
      method: 'POST',
      type: 'query',
    },
    /** 仓库结构规则管理*/

    //  仓库结构规则明
    {
      key: 'detailWarehouseStructuralRules',
      url: '/warehouse/structural/rule/detail',
      method: 'GET',
    },
    {
      key: 'saveWarehouseStructuralRules',
      url: '/warehouse/structural/rule/save',
      method: 'post',
    },
    /** 店铺补货优先管理 */
    // 店铺补货优先级列表
    {
      key: 'getShopReplenishPriority',
      url: '/shop/replenish/prioritys',
      method: 'GET',
    },
    // 店铺补货优先级保存接口
    {
      key: 'saveShopReplenishPriority',
      url: '/shop/replenish/prioritys/save',
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
