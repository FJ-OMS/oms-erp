/**
 *
 * Category Manage : 商品分类管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1',
  items: [
    /**
     * 新增
     */
    { key: 'addCategory', url: '/categorys/save', method: 'POST' },
    /**
     * 分页
     */
    { key: 'getPageCategory', url: '/categorys/page', method: 'POST' },
    /**
     * 修改
     */
    { key: 'updateCategory', url: '/categorys/save', method: 'POST' },
    /**
     * 状态
     */
    { key: 'operationCategory', url: '/categorys/update/status', method: 'POST' },
    /**
     * 批量删除
     */
    { key: 'batchDeleteCategory', url: '/categorys/batch/delete', method: 'DELETE' },
    /** ************* */
    /**
     * 统计分类
     */
    { key: 'getTotalCategory', url: '/total-category', method: 'POST' },
    /**
     * 根据父级获取子级id
     */
    { key: 'getSonCategory', url: '/list-by-front', method: 'GET' },
    /**
     * 刷新分类redis
     */
    { key: 'refreshCategory', url: '/category-refresh', method: 'GET' },
    /**
     * 获取全部分类
     */
    { key: 'getAllCategory', url: '/categorys/tree', method: 'POST' },
    /**
     * 复制上级
     */
    {
      key: 'getPrentAttribute',
      url: '/category/attributes/list/category/attribute',
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
