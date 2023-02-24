/*
 * 订单管理
 */
import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
// import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'after',
  name: 'BusinessAfter',
  component: EmptyRouterAlive,
  meta: {
    title: '售后管理',
  },
  children: [
    /** 三方售后单 */
    {
      path: 'third',
      name: 'BusinessAfterThird',
      component: EmptyRouter,
      redirect: 'third/refund',
      meta: {
        title: '三方售后单',
      },
      children: [
        // 仅退款
        {
          path: 'refund',
          name: 'BusinessAfterThirdRefundIndex',
          component: () => import('@/views/center-business/after/third/refund/index'),
          meta: {
            title: '三方仅退款',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'refund/view',
          name: 'BusinessAfterThirdRefundView',
          component: () => import('@/views/center-business/after/third/refund/view'),
          meta: {
            title: '三方仅退款详情',
          },
          hidden: true,
        },
        {
          path: 'refund/add',
          name: 'BusinessAfterThirdRefundAdd',
          component: () => import('@/views/center-business/after/third/refund/add'),
          meta: {
            title: '新增三方仅退款单',
          },
          hidden: true,
        },
        // 退货退款
        {
          path: 'return',
          name: 'BusinessAfterThirdReturnIndex',
          component: () => import('@/views/center-business/after/third/return/index'),
          meta: {
            title: '三方退货退款',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'return/view',
          name: 'BusinessAfterThirdReturnView',
          component: () => import('@/views/center-business/after/third/return/view'),
          meta: {
            title: '三方退货退款详情',
          },
          hidden: true,
        },
        {
          path: 'return/add',
          name: 'BusinessAfterThirdReturnAdd',
          component: () => import('@/views/center-business/after/third/return/add'),
          meta: {
            title: '新增三方退货退款单',
          },
          hidden: true,
        },
        // 换货
        // {
        //   path: 'exchange',
        //   name: 'BusinessAfterThirdExchange',
        //   component: () => import('@/views/center-business/after/third/exchange/index'),
        //   meta: {
        //     title: '三方换货',
        //   },
        //   permissionType: 'tab',
        //   hidden: true,
        // },
        // {
        //   path: 'exchange/view',
        //   name: 'BusinessAfterThirdExchangeView',
        //   component: () => import('@/views/center-business/after/third/exchange/view'),
        //   meta: {
        //     title: '三方换货详情',
        //   },
        //   hidden: true,
        // },
      ],
    },
    /** 内部售后单 */
    {
      path: 'internal',
      name: 'BusinessAfterInternal',
      component: EmptyRouter,
      redirect: 'internal/refund',
      meta: {
        title: '内部售后单',
      },
      children: [
        // 仅退款
        {
          path: 'refund',
          name: 'BusinessAfterInternalRefundIndex',
          component: () => import('@/views/center-business/after/internal/refund/index'),
          meta: {
            title: '内部仅退款',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'refund/add',
          name: 'BusinessAfterInternalRefundAdd',
          component: () => import('@/views/center-business/after/internal/refund/add'),
          meta: {
            title: '新增内部仅退款单',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'refund/view',
          name: 'BusinessAfterInternalRefundView',
          component: () => import('@/views/center-business/after/internal/refund/view'),
          meta: {
            title: '内部仅退款单详情',
          },
          hidden: true,
        },
        // 退货退款
        {
          path: 'return',
          name: 'BusinessAfterInternalReturnIndex',
          component: () => import('@/views/center-business/after/internal/return/index'),
          meta: {
            title: '内部退货退款',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'return/add',
          name: 'BusinessAfterInternalReturnAdd',
          component: () => import('@/views/center-business/after/internal/return/add'),
          meta: {
            title: '新增内部退货退款单',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'return/view',
          name: 'BusinessAfterInternalReturnView',
          component: () => import('@/views/center-business/after/internal/return/view'),
          meta: {
            title: '内部退货退款单详情',
          },
          hidden: true,
        },
        // 换货
        // {
        //   path: 'exchange',
        //   name: 'BusinessAfterInternalExchangeIndex',
        //   component: () => import('@/views/center-business/after/internal/exchange/index'),
        //   meta: {
        //     title: '内部换货',
        //   },
        //   permissionType: 'tab',
        //   hidden: true,
        // },
        {
          path: 'exchange/add',
          name: 'BusinessAfterInternalExchangeAdd',
          component: () => import('@/views/center-business/after/internal/exchange/add'),
          meta: {
            title: '新增内部换货单',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'exchange/view',
          name: 'BusinessAfterInternalExchangeView',
          component: () => import('@/views/center-business/after/internal/exchange/view'),
          meta: {
            title: '内部换货单详情',
          },
          hidden: true,
        },
      ],
    },
  ],
};
