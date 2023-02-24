import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1/process',
  items: [
    {
      key: 'getStockProcess',
      url: '/page',
      method: 'POST',
    },
    {
      key: 'deleteStockProcess',
      url: '/delete',
      method: 'POST',
      type: 'form',
    },
    {
      key: 'addStockProcess',
      url: '/create',
      method: 'POST',
    },
    {
      key: 'detailStockProcess',
      url: '/detail',
      method: 'POST',
      type: 'form',
    },
    {
      key: 'operationStockProcess',
      url: '/operation',
      method: 'POST',
    },
    {
      key: 'updateStockProcess',
      url: '/update',
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
