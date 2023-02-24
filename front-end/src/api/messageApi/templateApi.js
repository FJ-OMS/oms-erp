/**
 * File : 文件
 *

 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/smsg/v1',
  items: [
    { key: 'getTemplates', url: '/{organizationId}/message/templates', method: 'GET' },
    { key: 'deleteTemplate', url: '/{organizationId}/message/templates', method: 'DELETE' },
    { key: 'addTemplate', url: '/{organizationId}/message/templates', method: 'POST' },
    { key: 'updateTemplate', url: '/{organizationId}/message/templates', method: 'PUT' },
    {
      key: 'detailTemplate',
      url: '/{organizationId}/message/templates/{templateId}',
      method: 'GET',
    },

    /** * */

    // 查询消息模板参数
    {
      key: 'argsTemplate',
      url: '/{organizationId}/message/templates/template-args',
      method: 'GET',
    },

    // 根据编码查询消息模板明细
    {
      key: 'codeTemplate',
      url: '/{organizationId}/message/templates/template-code',
      method: 'GET',
    },

    // 查询消息模板语言
    {
      key: 'langTemplate',
      url: '/{organizationId}/message/templates/template-lang',
      method: 'GET',
    },

    // 消息模板复制
    {
      key: 'copyTemplate',
      url: '/{organizationId}/message/templates/{templateId}/copy',
      method: 'GET',
    },

    /** *  Template Arg  消息模板参数管理*/
    {
      key: 'getTemplateArgs',
      url: '/{organizationId}/template-args/{templateId}',
      method: 'GET',
    },

    {
      key: 'deleteTemplateArgs',
      url: '/{organizationId}/template-args',
      method: 'DELETE',
    },
    {
      key: 'updateTemplateArgs',
      url: '/{organizationId}/template-args',
      method: 'PUT',
    },
    // 初始化消息模板参数列表
    {
      key: 'initTemplateArgs',
      url: '/{organizationId}/template-args/init',
      type: 'query',
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
