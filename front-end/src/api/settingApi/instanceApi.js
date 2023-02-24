import { generate } from '@/utils/request';
const config = {
  prefix: '/hagd',
  items: [
    {
      key: 'getInstancesList',
      url: '/v1/sagas/instances',
      method: 'get',
    },
    {
      key: 'getInstancesDetail',
      url: '/v1/sagas/instances/{id}',
      method: 'get',
    },
    {
      key: 'retryInstancesTask',
      url: '/v1/sagas/tasks/instances/{id}/retry',
      method: 'put',
    },
    {
      key: 'failedInstancesTask',
      url: '/v1/sagas/tasks/instances/{id}/failed',
      method: 'put',
    },
  ],
};

export default {
  ...generate(config),
};
