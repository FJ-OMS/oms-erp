/*
 * @Author: your name
 * @Date: 2021-08-06 13:55:19
 * @LastEditTime: 2021-08-24 17:52:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\stockApi\warehouseApi.js
 */
/**
 *
 *  Warehouse Manage : 仓库管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1',
  items: [
    //
    { key: 'addWarehouses', url: '/warehouses/save', method: 'POST' },

    { key: 'updateWarehouses', url: '/warehouses/save', method: 'POST' },

    { key: 'detailWarehouses', url: '/warehouses/detail/{id}', method: 'get' },

    { key: 'getWarehousesList', url: '/warehouses/list-warehouse', method: 'POST' },
    { key: 'getWarehousesPage', url: '/warehouses/page-warehouse', method: 'POST' },

    { key: 'deleteWarehouses', url: '/warehouses/remove', method: 'DELETE' },

    { key: 'getWarehousesTypePage', url: '/warehouses/type/page', method: 'POST' },
    { key: 'getWarehouses', url: '/warehouses/list', method: 'get' },
    /** 删除仓库 */
    { key: 'deleteWarehouses', url: '/warehouses/remove', method: 'DELETE' },
    /** 保存 */
    { key: 'saveWarehouses', url: '/warehouses/save/coord', method: 'POST' },
    /** ***   Warehouse Structure  */

    { key: 'addWarehouseStructures', url: '/warehouse/structures', method: 'POST' },

    {
      key: 'statusWarehouseStructures',
      url: '/warehouse/structures/operation',
      method: 'POST',
      type: 'query',
    },

    // { key: 'detailWarehouseStructures', url: '/warehouses/detail/{id}', method: 'get' },

    { key: 'getWarehouseStructures', url: '/warehouse/structures', method: 'get' },

    { key: 'deleteWarehouseStructures', url: '/warehouse/structures', method: 'DELETE' },
    // 根据登录租户查询结构ID
    {
      key: 'getWarehouseStructureId',
      url: '/warehouse/structures/query/structural/id',
      method: 'POST',
    },

    /** 添加/修改仓库 */
    { key: 'updataWarehouseStructures', url: '/warehouses/save', method: 'post' },
  ],
};

export default {
  ...generate(config),
};
