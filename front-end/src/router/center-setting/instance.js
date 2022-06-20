/*
 * 事务管理  instance
 */
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
export default {
  path: 'instance',
  name: 'SettingInstance',
  component: EmptyRouterAlive,
  meta: {
    title: '事务管理',
  },
  children: [
    {
      path: 'manage',
      name: 'SettingInstanceManage',
      component: EmptyRouter,
      redirect: 'manage/index',
      meta: {
        title: '事务列表',
      },
      children: [
        {
          path: 'index',
          name: 'SettingInstanceManageIndex',
          component: () => import('@/views/center-setting/instance/manage'),
          meta: {
            title: '事务列表',
          },
        },
      ],
    },
  ],
};
