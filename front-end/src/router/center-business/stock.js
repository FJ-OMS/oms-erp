/*
 * @Author: your name
 * @Date: 2021-07-13 14:49:11
 * @LastEditTime: 2022-06-10 09:51:03
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\router\center-business\stock.js
 */
/*
 * 库存管理
 */
import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
// import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'stock',
  name: 'BusinessStock',
  component: EmptyRouterAlive,
  meta: {
    title: '库存管理',
  },
  children: [
    /**  **** 库存查询 Search  ****  */
    {
      path: 'search',
      name: 'BusinessStockSearch',
      component: EmptyRouter,
      redirect: 'search/sku',
      meta: {
        title: '库存查询',
      },
      children: [
        {
          path: 'sku',
          name: 'BusinessStockSearchIndex',
          component: () => import('@/views/center-business/stock/search/index.vue'),
          meta: {
            title: '库存查询',
          },
          hidden: true,
        },
      ],
    },
    /**  **** 库存变动查询 Change  ****  */
    {
      path: 'change',
      name: 'BusinessStockChange',
      component: EmptyRouter,
      redirect: 'change/index',
      meta: {
        title: '库存变动查询',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessStockChangeIndex',
          component: () => import('@/views/center-business/stock/change/index.vue'),
          meta: {
            title: '库存变动查询',
          },
        },
      ],
    },
    /** 入库管理 **/
    {
      path: 'entry',
      name: 'BusinessStockEntry',
      component: EmptyRouter,
      redirect: 'entry/inform',
      meta: {
        title: '入库管理',
      },
      children: [
        {
          path: 'inform',
          name: 'BusinessStockEntryInformIndex',
          component: () => import('@/views/center-business/stock/entry/inform/index.vue'),
          meta: {
            title: '入库通知单',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'inform/view',
          name: 'BusinessStockEntryInformView',
          component: () => import('@/views/center-business/stock/entry/inform/view.vue'),
          meta: {
            title: '入库通知单详情',
          },
          hidden: true,
        },
        {
          path: 'result',
          name: 'BusinessStockEntryResultIndex',
          component: () => import('@/views/center-business/stock/entry/result/index.vue'),
          meta: {
            title: '入库结果单',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'result/view',
          name: 'BusinessStockEntryResultView',
          component: () => import('@/views/center-business/stock/entry/result/view.vue'),
          meta: {
            title: '入库结果单详情',
          },
          hidden: true,
        },
      ],
    },
    /** 入库管理end */
    /** 出库管理 **/
    {
      path: 'delivery',
      name: 'BusinessStockDelivery',
      component: EmptyRouter,
      redirect: 'delivery/inform',
      meta: {
        title: '出库管理',
      },
      children: [
        {
          path: 'inform',
          name: 'BusinessStockDeliveryInformIndex',
          component: () => import('@/views/center-business/stock/delivery/inform/index.vue'),
          meta: {
            title: '出库通知单',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'inform/view',
          name: 'BusinessStockDeliveryInformView',
          component: () => import('@/views/center-business/stock/delivery/inform/view.vue'),
          meta: {
            title: '出库通知单详情',
          },
          hidden: true,
        },
        {
          path: 'result',
          name: 'BusinessStockDeliveryResultIndex',
          component: () => import('@/views/center-business/stock/delivery/result/index.vue'),
          meta: {
            title: '出库结果单',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'result/view',
          name: 'BusinessStockDeliveryResultView',
          component: () => import('@/views/center-business/stock/delivery/result/view.vue'),
          meta: {
            title: '出库结果单详情',
          },
          hidden: true,
        },
      ],
    },
    /** 出库管理end */
    /**  **** 数据同步管理 Synchronization  ****  */
    {
      path: 'synchronization',
      name: 'BusinessStockSynchronization',
      component: EmptyRouter,
      redirect: 'synchronization/List',
      meta: {
        title: '数据同步管理',
      },
      children: [
        {
          path: 'list',
          name: 'BusinessStockSynchronizationIndex',
          component: () => import('@/views/center-business/stock/synchronization'),
          meta: {
            title: '数据同步管理',
          },
        },
        {
          path: 'add',
          name: 'BusinessStockSynchronizationAdd',
          component: () => import('@/views/center-business/stock/synchronization/add'),
          meta: {
            title: '新建数据同步',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessStockSynchronizationIndex_Pass',
          meta: {
            title: '审核通过',
          },
          hidden: true,
        },
      ],
    },
    /** 数据同步管理end */
  ],
};
