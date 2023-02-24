/**
 * Dealer Manage : 经销商管理
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/schl/v1/channel/dealer',
  items: [
    /**
     * 新增经销商
     */
    { key: 'addDealer', url: '/add', method: 'POST' },
    /**
     * 删除经销商信息
     */
    { key: 'deleteDealer', url: '/delete', method: 'POST', type: 'query' },
    /**
     * 根据Id获取经销商信息信息
     */
    { key: 'getDealerDetails', url: '/details', method: 'GET' },
    /**
     * 分页获取信息
     */
    { key: 'getDealerPage', url: '/list', method: 'GET' },
    /**
     * 修改经销商信息
     */
    { key: 'updateDealer', url: '/update', method: 'POST' },
    /**
     * 禁用 OR 启用
     */
    { key: 'operationDealer', url: '/operation', method: 'POST', type: 'query' },
    /**
     * 批量删除数据
     */
    { key: 'batchDeleteDealer', url: '/deletes', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
