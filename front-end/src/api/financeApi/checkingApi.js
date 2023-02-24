/**
 * Account Checking Manage : 对账管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sfin/v1',
  items: [
    // 对账列表
    { key: 'getAccountCheckingList', url: '/account/checking/list', method: 'POST' },
    // 确认对账
    { key: 'notarizeAccountChecking', url: '/account/checking/notarize', method: 'POST' },

    // 已对账列表
    { key: 'getAccountCheckingFinish', url: '/account/checking/finish', method: 'POST' },
    // 已对账详情
    { key: 'detailAccountCheckingFinish', url: '/account/checking/detail/{id}', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
