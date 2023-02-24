/**
 * CnareaAdministrativeDivision Manage : 中国行政区划分管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/oauth',
  items: [
    /**
     *
     * @access_token   // http://api.skyer.org/oauth/logout?access_token=bf4411f3-95db-45f4-87c1-c179ab39e7ed
     */
    { key: 'logout', url: '/logout', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
