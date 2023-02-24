/**
 * File : 文件
 *

 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/smsg/v1',
  items: [
    /**
     * 获取列表
     */

    { key: 'getEmail', url: '/{organizationId}/email/servers', method: 'GET' },
    { key: 'addEmail', url: '/{organizationId}/email/servers', method: 'POST' },
    { key: 'updateEmail', url: '/{organizationId}/email/servers', method: 'PUT' },
    { key: 'deleteEmail', url: '/{organizationId}/email/servers', method: 'DELETE' },
    // 查询邮箱服务明细
    { key: 'detailEmail', url: '/{organizationId}/email/servers/{serverId}', method: 'Get' },
    {
      key: 'detailEmailConfig',
      url: '/{organizationId}/email/servers/{serverId}/properties',
      method: 'Get',
    },
    // Email Filter : 邮箱账户黑白名单
    { key: 'getEmailFilters', url: '/{organizationId}/email-filters', method: 'GET' },
    { key: 'addEmailFilters', url: '/{organizationId}/email-filters', method: 'POST' },
    { key: 'updateEmailFilters', url: '/{organizationId}/email-filters', method: 'POST' },
    { key: 'deleteEmailFilters', url: '/{organizationId}/email-filters', method: 'DELETE' },
  ],
};

export default {
  ...generate(config),
};
