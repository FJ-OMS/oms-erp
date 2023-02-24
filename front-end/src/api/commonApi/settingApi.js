/**
 * CnareaAdministrativeDivision Manage : 中国行政区划分管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/iam',
  items: [
    /**
     * 保存列表动态标题
     */
    { key: 'saveTabulation', url: '/v1/tabulation/save', method: 'POST', loadingHide: true },
    /**
     * 获取用户动态标题
     */
    { key: 'getTabulation', url: '/v1/tabulation/list', method: 'GET', loadingHide: true },
  ],
};

export default {
  ...generate(config),
};
