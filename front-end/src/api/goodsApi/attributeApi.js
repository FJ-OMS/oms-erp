/**
 * Attribute Manage : 渠道仓库管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/goods/attribute',
  items: [
    /**
     * 新增
     */
    { key: 'addAttribute', url: '/attribute', method: 'POST' },
    // /**
    //  * 删除
    //  */
    // { key: 'deleteAttribute', url: '/delete', method: 'DELETE', type: 'query' },
    /**
     * 分页
     */
    { key: 'getPageAttribute', url: '/page', method: 'POST' },
    /**
     * 修改
     */
    { key: 'updateAttribute', url: '/attribute', method: 'PUT' },
    /**
     * 根据Id获取
     */
    { key: 'getDetailAttribute', url: '/detail/', method: 'GET' },
    /**
     * 批量删除
     */
    { key: 'batchDeleteAttribute', url: '/batch-delete-attribute', method: 'PATCH' },
    /**
     * 更新状态
     */
    { key: 'patchAttributeStatus', url: '/status', method: 'PATCH' },

    /** ************************* */
    /**
     * 根据分类或者商品查询所有属性及对应属性值
     * @goodsId
     */
    {
      key: 'getAttributeValueByGoodsId',
      url: '/all-attribute-value-by-main/',
      method: 'GET',
    },
    /**
     * 对属性添加对应属性值
     */
    { key: 'addAttributeValue', url: '/attribute-value', method: 'POST' },
    /**
     * 查询属性对应的属性值
     * @attributeId
     */
    { key: 'getAttributeValueByAttributeId', url: '/attribute-value/', method: 'GET' },
    /**
     * 对属性更新对应属性值
     */
    { key: 'updateAttributeValue', url: '/attribute-value', method: 'POST' },
    /**
     * 对属性更新对应属性值(单个)
     */
    { key: 'updateSingleAttributeValue', url: '/single-attribute-value', method: 'PUT' },
  ],
};

export default {
  ...generate(config),
};
