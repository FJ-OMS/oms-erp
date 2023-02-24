/**
 * PasswordPolicy : 密码策略
 *

 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/iam',
  items: [
    /**
     * 查询组织的密码策略
     */
    { key: 'getPasswordPolicy', url: '/v1/{tenantId}/password-policies', method: 'Get' },
    /**
     * 修改组织的密码策略
     */
    { key: 'updatPasswordPolicy', url: '/v1/{tenantId}/password-policies', method: 'PUT' },
    /**
     * 查询密码策略
     */
    { key: 'getPasswordPolicyQuery', url: '/v1/{tenantId}/password-policies/query', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
