/**
 * goods-install-service : 安装服务管理
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/spu-install-services',
  items: [
    { key: 'addSpuInstallServices', url: '', method: 'POST' },

    { key: 'deleteSpuInstallServices', url: '', method: 'DELETE' },

    { key: 'DetailSpuInstallServices', url: '/', method: 'GET' },

    { key: 'getSpuInstallServices', url: '', method: 'GET' },

    { key: 'updateSpuInstallServices', url: '', method: 'PUT' },
  ],
};

export default {
  ...generate(config),
};
