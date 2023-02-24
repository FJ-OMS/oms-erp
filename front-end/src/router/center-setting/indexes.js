/*
 * @Author: your name
 * @Date: 2021-07-20 15:46:13
 * @LastEditTime: 2022-06-07 16:50:25
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\router\center-setting\indexes.js
 */
/*
 * 索引配置  indexes
 */
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
export default {
  path: 'indexes',
  name: 'SettingIndexes',
  component: EmptyRouterAlive,
  meta: {
    title: '索引配置',
  },
  children: [
    {
      path: 'config',
      name: 'SettingIndexesConfig',
      component: EmptyRouter,
      redirect: 'config/list',
      meta: {
        title: '索引配置列表',
      },
      children: [
        {
          path: 'list',
          name: 'SettingIndexesConfigListIndex',
          component: () => import('@/views/center-setting/indexes/list'),
          meta: {
            title: '索引配置列表',
          },
        },
        {
          path: 'add',
          name: 'SettingIndexesConfigListAdd',
          component: () => import('@/views/center-setting/indexes/list/add'),
          meta: {
            title: '新增索引',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'edit',
          name: 'SettingIndexesConfigListEdit',
          component: () => import('@/views/center-setting/indexes/list/edit'),
          meta: {
            title: '编辑索引',
          },
          hidden: true,
        },
        {
          path: 'edit',
          name: 'SettingIndexesConfigList_Status',
          meta: {
            title: '启用/禁用-索引',
          },
          hidden: true,
        },
      ],
    },
    /** 查询配置部分 */
    {
      path: 'inquiry',
      name: 'SettingInquiry',
      component: EmptyRouter,
      redirect: 'inquiry/list',
      meta: {
        title: '查询配置',
      },
      children: [
        {
          path: 'list',
          name: 'SettingInquiryIndex',
          component: () => import('@/views/center-setting/indexes/inquiry'),
          meta: {
            title: '查询配置',
          },
        },
        {
          path: 'inquiryAdd',
          name: 'SettingInquiryAdd',
          component: () => import('@/views/center-setting/indexes/inquiry/add'),
          meta: {
            title: '添加查询配置',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'inquiryEdit',
          name: 'SettingInquiryEdit',
          component: () => import('@/views/center-setting/indexes/inquiry/edit'),
          meta: {
            title: '编辑查询配置',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingInquiry_Delete',
          meta: {
            title: '删除-查询配置',
          },
          hidden: true,
        },
      ],
    },
    /** 索引同步配置部分 */
    {
      path: 'sync',
      name: 'SettingIndexesSync',
      component: EmptyRouter,
      redirect: 'sync/list',
      meta: {
        title: '索引同步',
      },
      children: [
        {
          path: 'list',
          name: 'SettingIndexesSyncIndex',
          component: () => import('@/views/center-setting/indexes/sync'),
          meta: {
            title: '索引同步',
          },
        },
        {
          path: 'add',
          name: 'SettingIndexesSyncAdd',
          component: () => import('@/views/center-setting/indexes/sync/add'),
          meta: {
            title: '添加同步索引',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'edit',
          name: 'SettingIndexesSyncEdit',
          component: () => import('@/views/center-setting/indexes/sync/edit'),
          meta: {
            title: '编辑同步索引',
          },
          hidden: true,
        },
      ],
    },
  ],
};
