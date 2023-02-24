/*
 * @Author: your name
 * @Date: 2021-10-19 10:27:23
 * @LastEditTime: 2022-04-14 10:53:09
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\orderApi\internal.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/sourcing/v1',
  items: [
    /** 寻源列表 */
    { key: 'getOrderSourcingPage', url: '/sourcing/page', method: 'POST' },
    /** 寻源详情 */
    { key: 'getOrderSourcingDetail', url: '/sourcing/detail', method: 'GET' },

    /** 寻源详情  按商品行汇总 sourcingId */
    { key: 'getOrderSourcingSkuListByOrder', url: '/sourcing/goods/page/spu', method: 'GET' },

    /** 寻源详情  按照商品行分页查询列表 sourcingId */
    { key: 'getOrderSourcingSkuLineListByOrder', url: '/sourcing/goods/page', method: 'GET' },

    /** 寻源详情  商品行汇总展开列表 skuCode */
    {
      key: 'getOrderSourcingSkuLineListBySku',
      url: '/sourcing/goods/listBySkuCode',
      method: 'GET',
      loadingHide: true,
    },
    /** 创建寻源包裹 */
    {
      key: 'addSourcingPackages',
      url: '/sourcing/packages',
      method: 'POST',
    },
    /** 重新寻源 */
    {
      key: 'reAutoSource',
      url: '/sourcing/re-auto-source/{sourceId}',
      method: 'GET',
    },
    {
      key: 'reAutoSourceBatch',
      url: '/sourcing/re-auto-source',
      method: 'PATCH',
    },

    /** ****  配置  ******** */
    // 寻源策略-寻源规则 列表
    {
      key: 'getSouringStrategyConfigs',
      url: '/souring-strategy-configs',
      method: 'GET',
      loadingHide: true,
    },
    // 寻源策略-寻源规则   修改
    {
      key: 'updateSouringStrategyConfigs',
      url: '/souring-strategy-configs',
      method: 'PUT',
      loadingHide: true,
    },
    // 寻源策略-寻源规则   批量修改
    {
      key: 'updateSouringStrategyConfigsBatch',
      url: '/souring-strategy-configs/batch-update',
      method: 'PATCH',
      loadingHide: true,
    },

    // 寻源策略-包裹规则  列表
    {
      key: 'getSouringStrategyPackage',
      url: '/package-strategy',
      method: 'GET',
    },
    // 寻源策略-包裹规则  批量删除
    {
      key: 'deleteSouringStrategyPackageBatch',
      url: '/package-strategy',
      method: 'DELETE',
    },
    // 寻源策略-包裹规则 批量创建
    {
      key: 'addSouringStrategyPackageBatch',
      url: '/package-strategy',
      method: 'POST',
    },
    // 寻源策略-包裹规则 批量修改
    {
      key: 'updateSouringStrategyPackageBatch',
      url: '/package-strategy/batch-update-split',
      method: 'PATCH',
    },
    // 寻源策略-包裹规则 排除条件配置列表
    {
      key: 'getSouringStrategyPackageExcludes',
      url: '/souring-exclude-strategy',
      method: 'GET',
    },
    // 寻源策略-包裹规则 排除条件配置列表
    {
      key: 'updateSouringStrategyPackageExcludes',
      url: '/souring-exclude-strategy',
      method: 'PUT',
    },
    // 寻源策略-包裹规则 排除条件配置列表
    {
      key: 'addSouringStrategyPackageExcludes',
      url: '/souring-exclude-strategy',
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
