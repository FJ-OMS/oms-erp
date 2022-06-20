import { generate } from '@/utils/request';
const config = {
  prefix: '/schl',
  items: [
    /** 获取默认集团 */
    { key: 'getDefaultCompany', url: '/v1/groups/default', method: 'GET' },
    /** 修改默认集团信息 */
    { key: 'updateCompanyInfo', url: '/v1/groups/update', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
