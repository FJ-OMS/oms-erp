/**
 * Online Shop Manage : 网店管理
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/schl/v1/online/shop',
  items: [
    /**
     * 查询列表
     */
    { key: 'getOnlineShopPage', url: '/page', method: 'get' },
    /**
     * 网店设置
     */
    { key: 'setOnlineShop', url: '/setting', method: 'POST' },

    /**
     * 查询网店设置
     */
    { key: 'getOnlineShopSetting', url: '/get/setting', method: 'GET' },

    /**
     * 新增与修改
     */
    { key: 'saveOnlineShop', url: '/save', method: 'POST' },
    /**
     * 禁用 OR 启用
     */
    { key: 'operationOnlineShop', url: '/operation', method: 'POST', type: 'query' },
    /**
     * 查询详情
     */
    { key: 'getOnlineShopDetails', url: '/details', method: 'GET' },
    /**
     * 删除数据
     */
    { key: 'deleteOnlineShop', url: '/delete', method: 'POST', type: 'query' },
    /**
     * 批量删除数据
     */
    { key: 'batchDeleteOnlineShop', url: '/deletes', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
