// --  标签管理  label
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
// import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'label',
  name: 'BusinessLabel',
  component: EmptyRouterAlive,
  meta: {
    title: '标签管理',
  },
  children: [
    {
      path: 'sys',
      name: 'BusinessLabelSys',
      component: EmptyRouter,
      redirect: 'sys/index',
      meta: {
        title: '系统标签',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessLabelSysIndex',
          component: () => import('@/views/center-business/label/sys/index'),
          meta: {
            title: '系统标签',
          },
        },
        {
          path: '',
          name: 'BusinessLabelSysIndex_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
      ],
    },
    {
      path: 'hand',
      name: 'BusinessLabelHand',
      component: EmptyRouter,
      redirect: 'hand/index',
      meta: {
        title: '人工标签',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessLabelHandIndex',
          component: () => import('@/views/center-business/label/hand/index'),
          meta: {
            title: '人工标签',
          },
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessLabelHandIndex_Add',
          meta: { title: '新增', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessLabelHandIndex_Edit',
          meta: { title: '编辑' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessLabelHandIndex_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessLabelHandIndex_Delete',
          meta: { title: '删除' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },
  ],
};
