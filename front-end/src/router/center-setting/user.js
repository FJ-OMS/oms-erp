/*
 * 配置中心路由
 */

import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
export default {
  path: 'user',
  name: 'SettingUser',
  component: EmptyRouterAlive,
  meta: {
    title: '用户',
  },
  children: [
    /** 组织架构部分 */
    {
      path: 'organization',
      name: 'SettingUserOrganization',
      redirect: 'organization/group',
      meta: {
        title: '组织架构',
      },
      component: EmptyRouter,
      children: [
        {
          path: 'group',
          name: 'SettingUserOrganizationGroup',
          component: () => import('@/views/center-setting/user/organization/group'),
          meta: {
            title: '集团设置',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'company',
          name: 'SettingUserOrganizationCompany',
          component: () => import('@/views/center-setting/user/organization/company'),
          meta: {
            title: '下属公司',
          },
          permissionType: 'tab',
          hidden: true,
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'SettingUserOrganizationGroup_Edit',
          meta: {
            title: '编辑-集团设置',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingUserOrganizationCompany_Delete',
          meta: {
            title: '删除-下属公司',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingUserOrganizationCompany_Add',
          meta: {
            title: '新增公司-下属公司',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingUserOrganizationCompany_Edit',
          meta: {
            title: '编辑-下属公司',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingUserOrganizationCompany_Status',
          meta: {
            title: '禁用/启用-下属公司',
          },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },

    {
      path: 'account',
      name: 'SettingUserAccount',
      component: EmptyRouter,
      meta: {
        title: '账号安全策略',
      },

      redirect: 'account/strategy',
      children: [
        {
          path: 'strategy',
          name: 'SettingUserAccountStrategy',
          component: () => import('@/views/center-setting/user/account/strategy.vue'),
          meta: {
            title: '账号安全策略配置',
          },
        },
        {
          path: '',
          name: 'SettingUserAccountStrategy_Save',
          meta: {
            title: '保存-账号安全策略配置',
          },
          hidden: true,
        },
      ],
    },
  ],
};
