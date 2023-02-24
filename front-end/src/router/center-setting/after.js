/*
 *  售后
 */

import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'after',
  name: 'SettingAfter',
  component: EmptyRouterAlive,
  meta: {
    title: '售后',
  },
  children: [
    {
      path: 'process',
      name: 'SettingAfterProcess',
      redirect: 'process/config',
      component: EmptyRouter,
      meta: {
        title: '售后流程配置',
      },
      children: [
        {
          path: 'config',
          name: 'SettingAfterProcessConfig',
          component: Test0Empty,
          meta: {
            title: '售后流程配置',
          },
        },
      ],
    },
    {
      path: 'audit',
      name: 'SettingAfterAudit',
      redirect: 'audit/strategy',
      component: EmptyRouter,
      meta: {
        title: '售后审核策略',
      },
      children: [
        {
          path: 'strategy',
          name: 'SettingAfterAuditStrategy',
          component: Test0Empty,
          meta: {
            title: '售后审核策略',
          },
        },
      ],
    },
  ],
};
