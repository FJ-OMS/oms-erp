/*
 * 商品
 */

import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
export default {
  path: 'goods',
  name: 'SettingGoods',
  component: EmptyRouterAlive,
  meta: {
    title: '商品',
  },
  children: [
    {
      path: 'audit',
      name: 'SettingGoodsAudit',
      component: EmptyRouter,
      redirect: 'audit/strategy',
      meta: {
        title: '商品审核策略',
      },
      children: [
        {
          path: 'strategy',
          name: 'SettingGoodsAuditStrategyIndex',
          component: () => import('@/views/center-setting/goods/audit/strategy'),
          meta: {
            title: '商品审核策略',
          },
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'SettingGoodsAuditStrategyIndex_Save',
          meta: {
            title: '保存-商品审核策略',
          },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },
  ],
};
