/**
 * goods-spu-son : 子母商品管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/other/info',
  items: [
    /**
     * 新增
     */
    { key: 'addOtherInfo', url: '/save', method: 'POST' },
    /**
     * 删除
     */
    { key: 'deleteOtherInfo', url: '/delete', method: 'POST' },

    /**
     * 分页获取
     */
    { key: 'getOtherInfo', url: '/page', method: 'POST' },
    /**
     * 修改
     */
    { key: 'updateOtherInfo', url: '/save', method: 'POST' },
    /**
     * 修改
     */
    { key: 'detailsOtherInfo', url: '/details', method: 'GET' },

    /**
     * 禁用 OR 启用
     */
    { key: 'operationOtherInfo', url: '/operation', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
