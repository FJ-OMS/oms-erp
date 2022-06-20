import { generate } from '@/utils/request';

const config = {
  prefix: '/sord/v1',
  items: [{ key: 'getPriceType', url: '/item-prices/price-type', method: 'GET' }],
};

export default {
  ...generate(config),
};
