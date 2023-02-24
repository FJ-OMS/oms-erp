/**
 * Tenant(Site Level) : 租户管理(平台级)
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/iam',
  items: [
    // 租户列表  ///skyer/v1/users/self-tenants/page
    { key: 'getTenants', url: '/v1/{organizationId}/tenants/paging-tenant', method: 'get' },
    { key: 'addTenants', url: '/v1/tenants-manager', method: 'POST' },
    { key: 'userTenants', url: '/v1/tenants-manager/assign-to-user', method: 'POST' },
    { key: 'detailTenants', url: '/v1/tenants-manager/', method: 'GET' },
    { key: 'updateTenants', url: '/v1/tenants-manager/', method: 'PUT' },
  ],
};

export default {
  ...generate(config),
};
