/**
 * Material Manage : 管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/goods/material',
  items: [
    /**
     * 新增
     */
    { key: 'addMaterial', url: '/add', method: 'POST' },
    /**
     * 删除
     */
    { key: 'deleteMaterial', url: '/delete', method: 'DELETE', type: 'query' },

    /**
     * 分页获取
     */
    { key: 'getPageMaterial', url: '/list', method: 'GET' },
    /**
     * 修改
     */
    { key: 'updateMaterial', url: '/edit', method: 'PUT' },
    /**
     * 根据Id获取
     */
    { key: 'getDetailMaterial', url: '/', method: 'GET' },
    /**
     * 批量删除
     */
    { key: 'batchDeleteMaterial', url: '/batch-delete', method: 'PATCH' },
  ],
};

export default {
  ...generate(config),
};
