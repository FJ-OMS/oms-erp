/**
 *
 * goods Manage :  商品管理

 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/goods/manager',
  items: [
    /**
     * 新增
     */
    { key: 'add', url: '/goods', method: 'POST' },
    /**
     * 分页
     */
    { key: 'getPage', url: '/page', method: 'post' },
    /**
     * 修改
     */
    { key: 'update', url: '/goods', method: 'PUT' },
    /**
     * 状态
     */
    { key: 'operation', url: '/goods', method: 'PATCH' },
    /**
     * 详情
     */
    { key: 'detail', url: '/detail/', method: 'Get' },
    /**
     * 批量删除
     */
    { key: 'batchDelete', url: '/batch-delete-goods', method: 'PATCH' },
  ],
};

export default {
  ...generate(config),
};
