/**
 * 示例
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/simp/v1/{organizationId}',
  items: [
    /**
     * 导出Csv模板
     *  @organizationId : 0
     */
    { key: 'importTemplateCSV', url: '/import/template/{templateCode}/csv', method: 'GET' },
    /**
     * 导出Excel模板
     *  @organizationId : 0
     */
    {
      key: 'importTemplateEXCEL',
      url: '/import/template/{templateCode}/excel',
      method: 'GET',
      rType: 2,
    },
    /**
     * 获取模板头行详细信息
     *  @organizationId : 0
     */
    { key: 'importTemplateInfo', url: '/import/template/{templateCode}/info', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
