/*
 * @Author: your name
 * @Date: 2021-07-29 14:15:07
 * @LastEditTime: 2021-08-02 14:49:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\goodsApi\skuApi.js
 */
/**
 * goods-sku : 商品SKU
 *
 * 值集头创建  POST /v1/price/item/setting/add-head
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/goods/sku',
  items: [
    /**
     * 新增
     */
    {
      key: 'addSku',
      url: '',
      method: 'POST',
      loadingMore: 999,
    },
    /**
     * 分页
     */
    { key: 'getSku', url: '/page', method: 'POST' },
    /**
     * 修改
     */
    {
      key: 'updateSku',
      url: '/update',
      method: 'PUT',
    },
    /**
     * 根据属性创建商品sku列表
     */
    {
      key: 'createSku',
      url: '/createSkuList',
      method: 'POST',
    },

    /**
     * SKU详情
     */
    { key: 'detailSku', url: '/{id}', method: 'GET' },
    /**
     * 根据商品id获取已经生成的sku
     */
    { key: 'getOldSkuListBySpuId', url: '/goods/', method: 'GET' },
    /**
     * 批量 提交 审核
     */
    { key: 'skuBatchSubmitAudit', url: '/batchAudit', method: 'PUT' },
    /**
     * 删除
     */
    {
      key: 'deleteSku',
      url: '',
      method: 'DELETE',
    },

    // 修改商品sku显示状态
    {
      key: 'updateSkuStatus',
      url: '/updateShowFlag',
      method: 'PUT',
    },

    // 编辑商品价格
    {
      key: 'updateSkuPrice',
      url: '/sku-price',
      method: 'PUT',
    },

    // 批量获取套餐明细列表  Array[ skuCode]
    {
      key: 'getMealsGoodsList',
      url: '/meals-goods-list',
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
