/**
 * SalesOrganization Manage : 销售组织管理
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/schl/v1/channel/salesOrganization',
  items: [
    /**
     * 新增经销商
     */
    { key: 'addSalesOrganization', url: '/add', method: 'POST' },
    /**
     * 删除经销商信息
     */
    { key: 'deleteSalesOrganization', url: '/delete', method: 'POST' },
    /**
     * 根据Id获取经销商信息信息
     */
    {
      key: 'getSalesOrganizationDetails',
      url: '/details',
      method: 'GET',
    },
    /**
     * 分页获取信息
     */
    { key: 'getSalesOrganizationPage', url: '/list', method: 'GET' },
    /**
     * 修改销售组织
     */
    { key: 'updateSalesOrganization', url: '/update', method: 'POST' },
    /**
     * 禁用 OR 启用
     */
    { key: 'operationSalesOrganization', url: '/operation', method: 'POST', type: 'query' },
    /**
     * 根据租户Id获取销售组织信息
     */
    { key: 'getSalesOrganizationByTenantId', url: '/getByTenantId', method: 'GET' },
    /**
     * 批量删除数据
     */
    { key: 'batchDeleteSalesOrganization', url: '/deletes', method: 'POST' },
    /**
     * 公司列表树
     */
    { key: 'getSalesOrganizationTree', url: '/listOfTree', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
