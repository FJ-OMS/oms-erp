/**
 * Price: 价格管理
 *
 * 值集头创建  POST /v1/price/item/setting/add-head
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1',
  items: [
    /** * SKU价格 */

    // 查看商品价格列表
    {
      key: 'getSkuPrices',
      url: '/goods/prices/{skuCode}',
      method: 'GET',
    },
    {
      key: 'addSkuPrices',
      url: '/goods/prices',
      method: 'POST',
    },
    {
      key: 'updateSkuPrices',
      url: '/goods/prices',
      method: 'PUT',
    },
    {
      key: 'deleteSkuPrices',
      url: '/goods/prices',
      method: 'DELETE',
    },

    /** *  价格项 */

    /**
     * 新增
     */
    {
      key: 'addPriceItem',
      url: '/price/item/setting/addValue',
      method: 'POST',
    },
    /**
     * 分页
     */
    { key: 'getPagePriceItem', url: '/price/item/setting/page', method: 'GET' },
    /**
     * 修改
     */
    {
      key: 'updatePriceItem',
      url: '/price/item/setting/value-update',
      method: 'PUT',
    },
    /**
     * 租户价格项值集查询(所有数据列表)
     */
    { key: 'getListPriceItem', url: '/price/item/setting', method: 'GET' },
    /**
     * 删除
     */
    {
      key: 'deletePriceItem',
      url: '/price/item/setting/value-delete/',
      method: 'DELETE',
    },
  ],
};

export default {
  ...generate(config),
};
