/*
 * 用户管理
 */

import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
export default {
  path: 'user',
  name: 'BusinessUser',
  component: EmptyRouterAlive,
  meta: {
    title: '用户管理',
  },
  children: [
    /**     ****** 用户管理    ******** */
    {
      path: 'account',
      name: 'BusinessUserAccount',
      component: EmptyRouter,
      redirect: 'account/list',
      meta: { title: '账户管理', isCache: true },
      children: [
        {
          path: 'list',
          name: 'BusinessUserAccountIndex',
          component: () => import('@/views/center-business/user/account/index'),
          meta: { title: '账户管理', isCache: true },
        },
        {
          path: 'add',
          name: 'BusinessUserAccountAdd',
          component: () => import('@/views/center-business/user/account/add'),
          meta: { title: '新建账户', isCache: true },
          hidden: true,
        },
        {
          path: 'edit',
          name: 'BusinessUserAccountEdit',
          component: () => import('@/views/center-business/user/account/edit'),
          meta: { title: '编辑账户' },
          hidden: true,
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessUserAccountIndex_RelatedCompany',
          meta: { title: '关联组织架构' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessUserAccountIndex_ChangePassword',
          meta: { title: '修改密码' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessUserAccountIndex_UseStatus',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessUserAccountIndex_Locked',
          meta: { title: '锁定/解锁' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessUserAccountIndex_Delete',
          meta: { title: '删除账户' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },

    // {
    //   path: 'account/allot',
    //   name: 'BusinessUserAccountAllot',
    //   component: () => import('@/views/center-business/user/account/allot'),
    //   meta: { title: '分配用户组' },
    //   hidden: true,
    // },
    /**     ****** 用户组管理    ******** */
    // {
    //   path: 'group/list',
    //   name: 'BusinessUserGroupList',
    //   component: () => import('@/views/center-business/user/group/index'),
    //   meta: { title: '用户组管理' },
    // },
    // {
    //   path: 'group/edit',
    //   name: 'BusinessUserGroupEdit',
    //   component: () => import('@/views/center-business/user/group/edit'),
    //   meta: { title: '编辑用户组' },
    //   hidden: true,
    // },

    /**     ****** 角色管理    ******** */
    {
      path: 'role',
      name: 'BusinessUserRole',
      component: EmptyRouter,
      redirect: 'role/list',
      meta: { title: '角色管理' },
      children: [
        {
          path: 'list',
          name: 'BusinessUserRoleIndex',
          component: () => import('@/views/center-business/user/role/index'),
          meta: { title: '角色管理' },
        },
        {
          path: 'allot-account',
          name: 'BusinessUserRoleAllotUser',
          component: () => import('@/views/center-business/user/role/allotUser'),
          meta: { title: '分配用户' },
          hidden: true,
        },
        {
          path: 'allot-data',
          name: 'BusinessUserRoleAllotData',
          component: () => import('@/views/center-business/user/role/allotData'),
          meta: { title: '分配数据权限' },
          hidden: true,
        },
        {
          path: 'allot-menu',
          name: 'BusinessUserRoleAllotMenu',
          component: () => import('@/views/center-business/user/role/allotMenu'),
          meta: { title: '分配菜单权限' },
          hidden: true,
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessUserRoleIndex_AddRole',
          meta: { title: '创建角色', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessUserRoleIndex_Edit',
          meta: { title: '编辑角色' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessUserRoleIndex_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },

    /**     ****** 权限管理    ******** */
    /* ******************************  暂时不显示  *******************************/
    // {
    //   path: 'data',
    //   name: 'BusinessUserData',
    //   component: EmptyRouter,
    //   redirect: 'data/list',
    //   meta: { title: '权限管理' },
    //   children: [
    //     {
    //       path: 'list',
    //       name: 'BusinessUserDataList',
    //       component: () => import('@/views/center-business/user/data/index'),
    //       meta: { title: '权限管理' },
    //     },
    //     {
    //       path: 'add',
    //       name: 'BusinessUserDataAdd',
    //       component: () => import('@/views/center-business/user/data/add'),
    //       meta: { title: '新建权限' },
    //       hidden: true,
    //     },
    //     /** 按钮权限start */
    //     {
    //       path: '',
    //       name: 'BusinessUserDataList_Delete',
    //       meta: { title: '删除权限' },
    //       hidden: true,
    //     },
    //     {
    //       path: '',
    //       name: 'BusinessUserDataList_AllotData',
    //       meta: { title: '分配数据权限' },
    //       hidden: true,
    //     },
    //     /** 按钮权限end */
    //   ],
    // },
  ],
};
