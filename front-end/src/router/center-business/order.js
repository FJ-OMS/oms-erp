/*
 * 订单管理
 */
import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
// import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'order',
  name: 'BusinessOrder',
  component: EmptyRouterAlive,
  meta: {
    title: '普通订单',
  },
  children: [
    {
      path: 'third',
      name: 'BusinessOrderThird',
      component: EmptyRouter,
      redirect: 'third/list',
      meta: {
        title: '三方销售单',
      },
      children: [
        {
          path: 'list',
          name: 'BusinessOrderThirdIndex',
          component: () => import('@/views/center-business/order/third/index'),
          meta: {
            title: '三方销售单', // 拉取订单管理
          },
        },
        {
          path: 'add',
          name: 'BusinessOrderThirdAdd',
          component: () => import('@/views/center-business/order/third/add'),
          meta: {
            title: '三方单录入',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'view',
          name: 'BusinessOrderThirdView',
          component: () => import('@/views/center-business/order/third/view'),
          meta: {
            title: '三方销售单详情',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'internal',
      name: 'BusinessOrderInternal',
      component: EmptyRouter,
      redirect: 'internal/list',
      meta: {
        title: '内部销售单',
      },
      children: [
        {
          path: 'list',
          name: 'BusinessOrderInternalIndex',
          component: () => import('@/views/center-business/order/internal/index'),
          meta: {
            title: '内部销售单',
          },
        },
        {
          path: 'view',
          name: 'BusinessOrderInternalView',
          component: () => import('@/views/center-business/order/internal/view'),
          meta: {
            title: '内部销售单详情',
          },
          hidden: true,
        },
        {
          path: 'add',
          name: 'BusinessOrderInternalAdd',
          component: () => import('@/views/center-business/order/internal/add'),
          meta: {
            title: '销售单录入',
            isCache: true,
          },
          hidden: true,
        },
      ],
    },
  ],
};
