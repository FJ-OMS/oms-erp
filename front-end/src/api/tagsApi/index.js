/**
 * 示例
 */

import { generate } from '@/utils/request';
const config = {
  prefix: 'tags/v1/labels',
  items: [
    /**
     * 分页
     */
    { key: 'getLabels', url: '', method: 'GET' },
    /**
     * 新增
     */
    { key: 'addLabels', url: '', method: 'POST' },
    /**
     * 修改
     */
    { key: 'updateLabels', url: '', method: 'PUT' },
    /**
     * 状态
     */
    { key: 'operationLabels', url: '/batch/update/status', method: 'PUT' },
    /**
     * 批量删除
     */
    { key: 'batchDeleteLabels', url: '/batch/remove', method: 'delete' },
  ],
};

export default {
  ...generate(config),
};
