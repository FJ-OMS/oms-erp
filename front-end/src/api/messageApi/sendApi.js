/**
 *   Template Maintain : 邮箱账户与模板关系维护
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/smsg/v1',
  items: [
    /**
     * 获取列表
     */

    { key: 'getSend', url: '/{organizationId}/template-servers', method: 'GET' },
    { key: 'addSend', url: '/{organizationId}/template-servers', method: 'POST' },
    { key: 'updateSend', url: '/{organizationId}/template-servers/{tempServerId}', method: 'PUT' },
    {
      key: 'deleteSend',
      url: '/{organizationId}/template-servers/{tempServerId}',
      method: 'DELETE',
    },
    // 查询邮箱服务明细
    { key: 'detailSend', url: '/{organizationId}/template-servers/detail', method: 'Get' },
    // 消息模板账户关联：查询模板行
    {
      key: 'detailLineSend',
      url: '/{organizationId}/template-servers/detail/line',
      method: 'Get',
    },
    // 消息模板账户关联：查询消息模板关联的服务
    {
      key: 'getTempServerList',
      url: '/{organizationId}/template-servers/line/{tempServerId}',
      method: 'Get',
    },
    // 删除消息模板账户行
    {
      key: 'deleteLineSend',
      url: '/{organizationId}/template-servers/line/{tempServerLineId}',
      method: 'DELETE',
    },
    // 复制消息发送配置
    {
      key: 'addSendFilters',
      url: '/{organizationId}/template-servers/{tempServerId}/copy',
      method: 'GET',
    },

    /** *  Relation Send Message : 关联发送消息 */
    // 关联发送消息，邮件/短信/站内信
    {
      key: 'sendMessageRelevance',
      url: '/{organizationId}/message/relevance',
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
