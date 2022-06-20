/* jshint esversion: 9 */
/**
 * Initialize Manage : 库存初始化管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1',
  items: [
    // 创建或者更新
    { key: 'addInitialize', url: '/initialize/create', method: 'POST' },
    // 分页
    { key: 'getInitialize', url: '/initialize/page', method: 'POST' },
    // 查询文件上传情况
    { key: 'getImportInfo', url: '/initialize/query', method: 'GET' },
    // 自动导入到正式表，一次执行三个流程(同步)
    {
      key: 'importStockInitialize',
      url: '/{organizationId}/import/data/sync/auto-import?templateCode=STOCK.INITIALIZE_CODE',
      method: 'POST',
      type: 'form',
    },
    // 导出临时数据Excel
    {
      key: 'exportStockInitialize',
      url:
        '/{organizationId}/import/manager/export/excel?templateCode=STOCK.INITIALIZE_CODE&batch={batch}',
      method: 'GET',
      rType: 2,
    },
  ],
};
export default {
  ...generate(config),
};
