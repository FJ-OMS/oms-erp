/*
 * @Author: your name
 * @Date: 2021-06-29 09:18:37
 * @LastEditTime: 2021-10-12 11:41:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\channelApi\warehouseApi.js
 */
/**
 * Warehouse Manage : 渠道仓库管理
 */
import { generate } from '@/utils/request';
const config = {
  // prefix: '/schl/v1/channel/warehouse',
  prefix: 'stock/v1/storage/area',
  items: [
    /**
     * 新增渠道仓库
     */
    { key: 'addWarehouse', url: '', method: 'POST' },
    /**
     * 根据Id获取渠道仓库信息
     */
    { key: 'getWarehouseDetails', url: '/{id}', method: 'GET' },
    /**
     * 分页获取渠道仓库信息
     */
    { key: 'getWarehousePage', url: '', method: 'GET' },
    /**
     * 修改渠道仓库
     */
    { key: 'updateWarehouse', url: '', method: 'PUT' },
    /**
     * 禁用 OR 启用
     */
    { key: 'operationWarehouse', url: '/operation', method: 'POST', type: 'query' },
    /**
     * 批量删除数据
     */
    { key: 'batchDeleteWarehouse', url: '/batchDelete', method: 'PATCH' },
  ],
};

export default {
  ...generate(config),
};
