/**
 * sku-group

 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/sku-groups',
  items: [
    /**
     * 新增
     */
    { key: 'addSkuGroup', url: '/sku-group', method: 'POST' },
    /**
     * 删除
     */
    { key: 'deleteSkuGroup', url: '/group', method: 'DELETE' },

    /**
     * 分页获取
     */
    { key: 'getSkuGroup', url: '/page', method: 'POST' },
    /**
     * 修改
     */
    { key: 'updateSkuGroup', url: '/sku-group', method: 'POST' },
    /**
     * 根据Id获取
     */
    { key: 'detailSkuGroup', url: '/detail/{groupId}', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
