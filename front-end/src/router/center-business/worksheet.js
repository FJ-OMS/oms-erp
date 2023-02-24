// ---工单管理 worksheet

import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'worksheet',
  name: 'BusinessWorksheet',
  component: EmptyRouterAlive,
  meta: {
    title: '工单管理',
  },
  children: [
    {
      path: 'install',
      name: 'BusinessWorksheetInstall',
      component: EmptyRouter,
      redirect: 'install/index',
      meta: {
        title: '安装工单管理',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessWorksheetInstallIndex',
          component: Test0Empty,
          meta: {
            title: '安装工单管理',
          },
        },
      ],
    },
    {
      path: 'service',
      name: 'BusinessWorksheetService',
      component: EmptyRouter,
      redirect: 'service/list',
      meta: {
        title: '服务管理',
      },
      children: [
        {
          path: 'list',
          name: 'BusinessWorksheetServiceIndex',
          component: () => import('@/views/center-business/worksheet/service'),
          meta: {
            title: '服务管理',
          },
        },
        {
          path: 'view',
          name: 'BusinessWorksheetServiceView',
          component: () => import('@/views/center-business/worksheet/service/view.vue'),
          meta: {
            title: '服务管理详情',
          },
          hidden: true,
        },
      ],
    },
  ],
};
