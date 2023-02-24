/*
 * 基础数据设置
 */

import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
export default {
  path: 'base-data',
  name: 'SettingBaseData',
  component: EmptyRouterAlive,
  meta: {
    title: '基础数据',
  },
  children: [
    {
      path: 'code-rule',
      name: 'SettingBaseDataCodeRule',
      component: EmptyRouter,
      redirect: 'code-rule/list',
      meta: {
        title: '编码规则',
      },
      children: [
        {
          path: 'list',
          name: 'SettingBaseDataCodeRuleIndex',
          component: () => import('@/views/center-setting/base-data/code-rule/index'),
          meta: {
            title: '编码规则',
          },
        },
        {
          path: '',
          name: 'SettingBaseDataCodeRule_Add',
          meta: {
            title: '新增-编码规则',
          },
          hidden: true,
        },
        {
          path: 'code-rule/edit',
          name: 'SettingBaseDataCodeRuleEdit',
          component: () => import('@/views/center-setting/base-data/code-rule/edit'),
          meta: {
            title: '编辑-编码规则',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataCodeRule_Delete',
          meta: {
            title: '删除-编码规则',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'country',
      name: 'SettingBaseDataCountry',
      component: EmptyRouter,
      redirect: 'country/index',
      meta: {
        title: '国家定义',
      },
      children: [
        {
          path: 'index',
          name: 'SettingBaseDataCountryIndex',
          component: () => import('@/views/center-setting/base-data/country/index'),
          meta: {
            title: '国家定义',
          },
        },
        {
          path: '',
          name: 'SettingBaseDataCountryIndex_Add',
          meta: {
            title: '新增-国家',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataCountryIndex_Edit',
          meta: {
            title: '编辑-国家',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataCountryIndex_Status',
          meta: {
            title: '启用/禁用-国家',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'region',
      name: 'SettingBaseDataRegion',
      component: EmptyRouter,
      redirect: 'region/index',
      meta: {
        title: '中国行政区定义',
      },
      children: [
        {
          path: 'index',
          name: 'SettingBaseDataRegionIndex',
          component: () => import('@/views/center-setting/base-data/region/index'),
          meta: {
            title: '中国行政区定义',
          },
        },
        {
          path: '',
          name: 'SettingBaseDataRegionIndex_Add',
          meta: {
            title: '新增-中国行政区',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataRegionIndex_Edit',
          meta: {
            title: '编辑-中国行政区',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataRegionIndex_Delete',
          meta: {
            title: '删除-中国行政区',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataRegionIndex_AddChild',
          meta: {
            title: '新建下级区域-中国行政区',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'after-sales-reason',
      name: 'SettingBaseDataAfterSalesReason',
      component: EmptyRouter,
      redirect: 'after-sales-reason/refund',
      meta: {
        title: '售后原因',
      },
      children: [
        {
          path: 'refund',
          name: 'SettingBaseDataAfterSalesReasonRefundIndex',
          component: () => import('@/views/center-setting/base-data/after-sales-reason/refund'),
          meta: {
            title: '仅退款原因',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataAfterSalesReasonRefundIndex_Add',
          meta: { title: '新增仅退款原因', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataAfterSalesReasonRefundIndex_Edit',
          meta: { title: '编辑仅退款原因' },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataAfterSalesReasonRefundIndex_Status',
          meta: { title: '禁用/启用仅退款原因' },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataAfterSalesReasonRefundIndex_Delete',
          meta: { title: '删除仅退款原因' },
          hidden: true,
        },
        {
          path: 'return',
          name: 'SettingBaseDataAfterSalesReasonReturnIndex',
          component: () => import('@/views/center-setting/base-data/after-sales-reason/return'),
          meta: {
            title: '退货退款原因',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataAfterSalesReasonReturnIndex_Add',
          meta: { title: '新增退货退款原因', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataAfterSalesReasonReturnIndex_Edit',
          meta: { title: '编辑退货退款原因' },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataAfterSalesReasonReturnIndex_Status',
          meta: { title: '禁用/启用退货退款原因' },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataAfterSalesReasonReturnIndex_Delete',
          meta: { title: '删除退货退款原因' },
          hidden: true,
        },
      ],
    },
    {
      path: 'lov',
      name: 'SettingBaseDataLov',
      component: EmptyRouter,
      redirect: 'lov/index',
      meta: {
        title: '值集管理',
      },
      children: [
        {
          path: 'index',
          name: 'SettingBaseDataLovIndex',
          component: () => import('@/views/center-setting/base-data/lov'),
          meta: {
            title: '值集管理',
          },
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'SettingBaseDataLovIndex_Add',
          meta: { title: '创建值集值', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataLovIndex_Edit',
          meta: { title: '编辑值集值' },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataLovIndex_Delete',
          meta: { title: '删除值集值' },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingBaseDataLovIndex_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },
  ],
};
