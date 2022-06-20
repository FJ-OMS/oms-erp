import { generate } from '@/utils/request';
const config = {
  prefix: '/schl/v1',
  items: [
    // 店铺销售表
    { key: 'getChannelorderReport', url: '/shop/channel-order-report', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
