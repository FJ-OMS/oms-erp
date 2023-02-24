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

    { key: 'getNotices', url: '/{organizationId}/notices', method: 'GET' },
    { key: 'deleteNotices', url: '/{organizationId}/notices', method: 'DELETE' },
    { key: 'addNotices', url: '/{organizationId}/notices', method: 'POST' },
    { key: 'updateNotices', url: '/{organizationId}/notices', method: 'PUT' },
    { key: 'detailNotices', url: '/{organizationId}/notices/{noticeId}', method: 'GET' },
    // 发布
    { key: 'publishNotices', url: '/{organizationId}/notices/{noticeId}/publish', method: 'POST' },
    // 撤消
    { key: 'revokeNotices', url: '/{organizationId}/notices/{noticeId}/revoke', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
