/**
 * Brand Manage : 渠道仓库管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/goods/brand',
  items: [
    /**
     * 新增
     */
    { key: 'addBrand', url: '/brand', method: 'POST' },
    /**
     * 分页
     */
    { key: 'getPageBrand', url: '/page', method: 'post' },
    /**
     * 修改
     */
    { key: 'updateBrand', url: '/brand', method: 'PUT' },
    /**
     * 状态
     */
    { key: 'operationBrand', url: '/status', method: 'PATCH' },
    /**
     * 批量删除
     */
    { key: 'batchDeleteBrand', url: '/batch-delete-brand', method: 'PATCH' },
  ],
};

export default {
  ...generate(config),
};
