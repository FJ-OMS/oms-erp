// ---  日志管理  log

import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'log',
  name: 'BusinessLog',
  component: EmptyRouterAlive,
  meta: {
    title: '日志管理',
  },
  children: [
    {
      path: 'operation/sys',
      name: 'BusinessLogOperationSys',
      component: EmptyRouter,
      redirect: 'operation/sys/index',
      meta: {
        title: '系统操作日志',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessLogOperationSysIndex',
          component: Test0Empty,
          meta: {
            title: '系统操作日志',
          },
        },
      ],
    },
    {
      path: 'data',
      name: 'BusinessLogData',
      component: EmptyRouter,
      redirect: 'data/index',
      meta: {
        title: '数据对接日志',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessLogDataIndex',
          component: Test0Empty,
          meta: {
            title: '数据对接日志',
          },
        },
      ],
    },
  ],
};
