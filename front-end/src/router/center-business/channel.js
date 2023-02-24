/*
 * 渠道管理
 */

import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
export default {
  path: 'channel',
  name: 'BusinessChannel',
  component: EmptyRouterAlive,
  meta: {
    title: '渠道管理',
  },
  children: [
    {
      path: 'online',
      name: 'BusinessChannelOnline',
      component: EmptyRouter,
      redirect: 'online/index',
      meta: {
        title: '网店管理',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessChannelOnlineIndex',
          component: () => import('@/views/center-business/channel/online'),
          meta: {
            title: '网店管理',
          },
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessChannelOnlineList_Add',
          meta: { title: '创建网店', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessChannelOnlineList_Edit',
          meta: { title: '编辑网店' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessChannelOnlineList_Delete',
          meta: { title: '删除网店' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessChannelOnlineList_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        // {
        //   path: '',
        //   name: 'BusinessChannelOnlineList_Set',
        //   meta: { title: '设置网店' },
        //   hidden: true,
        // },
        /** 按钮权限end */
      ],
    },
    {
      path: 'physical',
      name: 'BusinessChannelPhysical',
      component: EmptyRouter,
      redirect: 'physical/index',
      meta: {
        title: '门店管理',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessChannelPhysicalIndex',
          component: () => import('@/views/center-business/channel/physical'),
          meta: {
            title: '门店管理',
          },
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessChannelPhysicalList_Add',
          meta: { title: '创建门店', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessChannelPhysicalList_Edit',
          meta: { title: '编辑门店' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessChannelPhysicalList_Delete',
          meta: { title: '删除门店' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessChannelPhysicalList_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },
    /* ******************************  暂时不显示  *******************************/
    // {
    //   path: 'dealer',
    //   name: 'BusinessChannelDealer',
    //   component: EmptyRouter,
    //   redirect: 'dealer/index',
    //   meta: {
    //     title: '经销商管理',
    //   },
    //   children: [
    //     {
    //       path: 'index',
    //       name: 'BusinessChannelDealerIndex',
    //       component: () => import('@/views/center-business/channel/dealer'),
    //       meta: {
    //         title: '经销商管理',
    //       },
    //     },
    //     /** 按钮权限start */
    //     {
    //       path: '',
    //       name: 'BusinessChannelDealerIndex_Add',
    //       meta: { title: '创建经销商' },
    //       hidden: true,
    //     },
    //     {
    //       path: '',
    //       name: 'BusinessChannelDealerIndex_Edit',
    //       meta: { title: '编辑经销商' },
    //       hidden: true,
    //     },
    //     {
    //       path: '',
    //       name: 'BusinessChannelDealerIndex_Delete',
    //       meta: { title: '删除经销商' },
    //       hidden: true,
    //     },
    //     {
    //       path: '',
    //       name: 'BusinessChannelDealerIndex_Status',
    //       meta: { title: '启用/禁用' },
    //       hidden: true,
    //     },
    //     /** 按钮权限end */
    //   ],
    // },
    {
      path: 'storage',
      name: 'BusinessChannelStorage',
      component: EmptyRouter,
      redirect: 'storage/index',
      meta: {
        title: '仓储管理',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessChannelStorageIndex',
          component: () => import('@/views/center-business/channel/storage'),
          meta: {
            title: '仓储管理',
          },
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessChannelStorageIndex_Add',
          meta: { title: '创建仓储', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessChannelStorageIndex_Edit',
          meta: { title: '编辑仓储' },
          hidden: true,
        },
        // {
        //   path: '',
        //   name: 'BusinessChannelStorageIndex_Delete',
        //   meta: { title: '删除仓储' },
        //   hidden: true,
        // },
        {
          path: '',
          name: 'BusinessChannelStorageIndex_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },
  ],
};
