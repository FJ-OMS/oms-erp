/* jshint esversion: 9 */
/**
 * Warehouse Relation Manage : 仓库关系管理
POST /add
接收仓添加

POST /all
仓库结构关系图

POST /channel/add
渠道仓添加

POST /channel/detail
渠道仓添加页面详情

POST /detail
接收仓添加页面详情

POST /interline/add
中转仓添加

POST /interline/detail
中转仓添加页面详情

POST /setting/all
租户配置比率关系图(租户级)

POST /setting/ratio
设置仓库比例值（租户级）

 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1/warehouse/relation',
  items: [
    /** 仓库关系添加 */
    { key: 'addWarehouseStructuresRelation', url: '/add', method: 'post' },
    { key: 'getWarehouseStructuresDetail', url: '/all', method: 'post' },

    /** 渠道仓关系添加 */
    {
      key: 'addWarehouseStructuresChannel',
      url: '/channel/add',
      method: 'post',
    },
    /** 渠道仓关系详情 */
    {
      key: 'getWarehouseStructuresChannelDetail',
      url: '/channel/detail',
      method: 'post',
    },
    /** 接收仓关系详情 */
    {
      key: 'getWarehouseStructureslReceiveDetail',
      url: '/detail',
      method: 'post',
    },

    /** 中转虚仓关系添加 */
    {
      key: 'addWarehouseStructuresInterline',
      url: '/interline/add',
      method: 'post',
    },
    /** 中转虚仓关系详情 */
    {
      key: 'getWarehouseStructureslInterlineDetail',
      url: '/interline/detail',
      method: 'post',
    },
    /** 中转仓取消渠道仓关系校验 */
    {
      key: 'checkoutWarehouseCancelChannel',
      url: '/cancel/channel',
      method: 'post',
    },

    /** 根据供货仓及接货仓类型获取有效的接收仓列表 */
    {
      key: 'getReceiveWarehouse',
      url: '/query/receive/warehouse',
      method: 'post',
    },

    /** 租户配置比率关系图(租户级) */
    {
      key: 'addWarehouseStructuresAll',
      url: '/setting/all',
      method: 'post',
    },

    /** 设置仓库比例值 */
    {
      key: 'addWarehouseStructuresRatio',
      url: '/setting/ratio',
      method: 'post',
    },
    // 查询出供应仓对应的接收仓列表
    { key: 'getWarehouserBySupport', url: '/warehouse-by-support', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
