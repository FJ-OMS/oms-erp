/**
 *
 *Code Rule Manager : 编码规则管理

DELETE /v1/{organizationId}/code-rule-dist
租户级删除编码规则分配

POST /v1/{organizationId}/code-rule-dist
租户级新增和更新编码规则分配

GET /v1/{organizationId}/code-rule/one
查询获取编码规则、编码规则分配、编码规则明细

GET /v1/{organizationId}/code-rule/rule-code
查询获取编码规则、编码规则分配、编码规则明细

 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/spfm/v1',
  items: [
    { key: 'getCodeRule', url: '/{organizationId}/code-rule', method: 'GET' },
    { key: 'addCodeRule', url: '/{organizationId}/code-rule', method: 'POST' },
    { key: 'deleteCodeRule', url: '/{organizationId}/code-rule', method: 'DELETE' },
    { key: 'detailCodeRule', url: '/{organizationId}/code-rule/{ruleId}', method: 'GET' },
    {
      key: 'detailCodeRuleDistId',
      url: '/{organizationId}/code-rule-detail/{ruleDistId}',
      method: 'GET',
    },
    // 租户级新增和更新编码规则明细
    { key: 'addCodeRuleDetail', url: '/{organizationId}/code-rule-detail', method: 'POST' },
    { key: 'deleteCodeRuleDetail', url: '/{organizationId}/code-rule-detail', method: 'DELETE' },
  ],
};

export default {
  ...generate(config),
};
