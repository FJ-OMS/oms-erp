/**
 * goods-install-service : 安装服务管理
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/install-services',
  items: [
    { key: 'addInstallServices', url: '', method: 'POST' },

    { key: 'deleteInstallServices', url: '', method: 'DELETE' },

    { key: 'DetailInstallServices', url: '/', method: 'GET' },

    { key: 'getInstallServices', url: '', method: 'GET' },

    { key: 'updateInstallServices', url: '', method: 'PUT' },
  ],
};

export default {
  ...generate(config),
};
