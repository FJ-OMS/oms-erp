/**
 * SMS Server : 短信配置
 *

 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/smsg/v1',
  items: [
    /**
     *  发送一条短信消息，指定模板和参数
     */
    { key: 'sendSMS', url: '/{organizationId}/messages/call/template', method: 'POST' },
    // 列表
    { key: 'getSMS', url: '/{organizationId}/sms/servers', method: 'GET' },
    // 创建短信服务
    { key: 'addSMS', url: '/{organizationId}/sms/servers', method: 'POST' },
    // 删除短信配置
    { key: 'deleteSMS', url: '/{organizationId}/sms/servers', method: 'DELETE' },
    /**
     * 修改短信服务
     */
    { key: 'updateSMS', url: '/{organizationId}/sms/servers', method: 'PUT' },
    /**
     * 查询短信服务配置
     */
    { key: 'detailSMS', url: '/{organizationId}/sms/servers/{serverId}', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
