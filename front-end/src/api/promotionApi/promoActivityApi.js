/**
 * File : 文件
 *

 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/spro/v1',
  items: [
    // 赠品活动列表
    { key: 'getPromoActivityPage', url: '/activity/page', method: 'POST' },
    // 赠品活动详情
    { key: 'getPromoActivityDetail', url: '/activity/detail', method: 'GET' },
    // 新建赠品活动
    { key: 'addPromoActivity', url: '/activity/create', method: 'POST' },
    // 修改赠品活动
    { key: 'updatePromoActivity', url: '/activity/update', method: 'PUT' },
    // 修改赠品活动状态
    { key: 'updatePromoActivityStatus', url: '/activity/updateStatus', method: 'PUT' },
    // 参与活动商品sku
    {
      key: 'getPromoActivitySkuCodeMap',
      url: '/activity/sku/skuCodeMap',
      method: 'POST',
      loadingHide: true,
    },

    /**
     * 随品规则
     */
    // 随品规则 新增
    { key: 'addPromotionGiftRules', url: '/gift/rules/create', method: 'POST' },
    // 随品规则 详情
    { key: 'detailPromotionGiftRules', url: '/gift/rules/detail/', method: 'GET' },
    // 随品规则 列表
    { key: 'pagePromotionGiftRules', url: '/gift/rules/page', method: 'POST' },
    // 随品规则 删除
    { key: 'removePromotionGiftRules', url: '/gift/rules/remove/', method: 'DELETE' },
    // 随品规则 更新
    { key: 'updatePromotionGiftRules', url: '/gift/rules/update', method: 'PUT' },
    // 已有随品规则的skuCodeList
    {
      key: 'getPromotionGiftRulesSkuCodeMap',
      url: '/gift/rules/skuCodeList',
      method: 'POST',
      loadingHide: true,
    },
  ],
};
export default {
  ...generate(config),
};
