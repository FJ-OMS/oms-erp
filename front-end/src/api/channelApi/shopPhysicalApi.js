/**
 * Physical Shop Manage : 门店管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/schl/v1/physical/shop',
  items: [
    /**
     * 查询列表
     */
    { key: 'getPhysicalShopPage', url: '/page', method: 'get' },
    /**
     * 网店设置
     */
    { key: 'setPhysicalShop', url: '/setting', method: 'POST' },
    /**
     * 新增与修改
     */
    { key: 'addPhysicalShop', url: '/save', method: 'POST' },
    /**
     * 禁用 OR 启用
     */
    { key: 'operationPhysicalShop', url: '/operation', method: 'POST', type: 'query' },
    /**
     * 查询详情
     */
    { key: 'getPhysicalShopDetails', url: '/details', method: 'GET' },
    /**
     * 删除数据
     */
    { key: 'deletePhysicalShop', url: '/delete', method: 'POST', type: 'query' },
    /**
     * 批量删除数据
     */
    { key: 'batchDeletePhysicalShop', url: '/deletes', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
