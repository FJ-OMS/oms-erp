/**
 * Dealer Manage : 经销商管理
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/spfm/v1',
  items: [
    /**
     * 根据lovCode查询单个值集头
     */
    {
      key: 'getLovHeadersByLovCode',
      url: '/{organizationId}/lov-headers-code/{lovCode}',
      method: 'GET',
    },

    /**
     * 根据值集头ID分页查询值集值
     */
    {
      key: 'getLovValuesByLovId',
      url: '/{organizationId}/lov-headers/{lovId}/values',
      method: 'GET',
    },
    /**
     * 批量删除值集值
     */
    { key: 'deleteLovValues', url: '/{organizationId}/lov-values', method: 'DELETE' },

    /**
     * 插入值集值
     */
    { key: 'addLovValues', url: '/{organizationId}/lov-values', method: 'POST' },

    /**
     * 更新值集值
     */
    { key: 'updateLovValues', url: '/{organizationId}/lov-values', method: 'PUT' },
  ],
};

export default {
  ...generate(config),
};
