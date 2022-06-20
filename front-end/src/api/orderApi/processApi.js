import { generate } from '@/utils/request';

const config = {
  prefix: '/sord/v1/process-defines',
  items: [
    { key: 'addOrderProcessDefines', url: '', method: 'POST' },
    { key: 'deleteOrderProcessDefines', url: '', method: 'DELETE' },
    { key: 'updateOrderProcessDefines', url: '', method: 'PUT' },
    { key: 'getOrderProcessDefines', url: '', method: 'GET' },
    { key: 'detailOrderProcessDefines', url: '/', method: 'GET' },
    // 流程部署
    { key: 'deploymentOrderProcessDefines', url: '/deploy-process/', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
