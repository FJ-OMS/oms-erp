/**
 * Attribute Manage : 渠道仓库管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/goods/attribute/relation',
  items: [
    /**
     * 新增
     */
    { key: 'addAttributeRelation', url: '/attribute-relation', method: 'POST' },

    /**
     * 分页
     */
    { key: 'getPageAttributeRelation', url: '/page-attribute-relation', method: 'POST' },
    /**
     * 修改
     */
    { key: 'updateAttributeRelation', url: '/attribute-relation', method: 'PUT' },
    /**
     * 批量删除
     */
    { key: 'batchDeleteAttributeRelation', url: '/attribute-relation', method: 'PATCH' },
    /**
     * 通过mainId查询当前属性
     */
    { key: 'getListAttributeRelation', url: '/list-attribute-relation/', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
