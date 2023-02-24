/*
 * -消息服务 message
 */
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
// import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'message',
  name: 'SettingMessage',
  component: EmptyRouterAlive,
  meta: {
    title: '消息服务',
  },
  children: [
    {
      path: 'notice',
      name: 'SettingMessageNotice',
      component: EmptyRouter,
      redirect: 'notice/list',
      meta: {
        title: '公告管理',
      },
      children: [
        {
          path: 'list',
          name: 'SettingMessageNoticeIndex',
          component: () => import('@/views/center-setting/message/notice/index'),
          meta: {
            title: '公告管理',
          },
        },
        {
          path: 'add',
          name: 'SettingMessageNoticeAdd',
          component: () => import('@/views/center-setting/message/notice/add'),
          meta: {
            title: '新增公告',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'edit',
          name: 'SettingMessageNoticeEdit',
          component: () => import('@/views/center-setting/message/notice/edit'),
          meta: {
            title: '编辑公告',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'email',
      name: 'SettingMessageEmail',
      component: EmptyRouter,
      redirect: 'email/list',
      meta: {
        title: '邮箱账户配置',
      },
      children: [
        {
          path: 'list',
          name: 'SettingMessageEmailIndex',
          component: () => import('@/views/center-setting/message/email/index'),
          meta: {
            title: '邮箱账户配置',
          },
        },
        {
          path: 'add',
          name: 'SettingMessageEmailAdd',
          component: () => import('@/views/center-setting/message/email/add'),
          meta: {
            title: '新增邮箱配置',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'edit',
          name: 'SettingMessageEmailEdit',
          component: () => import('@/views/center-setting/message/email/edit'),
          meta: {
            title: '编辑邮箱配置',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'sms',
      name: 'SettingMessageSms',
      component: EmptyRouter,
      redirect: 'sms/index',
      meta: {
        title: '短信账户配置',
      },
      children: [
        {
          path: 'index',
          name: 'SettingMessageSmsIndex',
          component: () => import('@/views/center-setting/message/sms/index'),
          meta: {
            title: '短信账户配置',
          },
        },
      ],
    },
    // {
    //   path: 'template',
    //   name: 'SettingMessageTemplate',
    //   component: () => import('@/views/center-setting/message/template/index'),
    //   meta: {
    //     title: '消息模板管理',
    //   },
    // },
    // {
    //   path: 'template/add',
    //   name: 'SettingMessageTemplateAdd',
    //   component: () => import('@/views/center-setting/message/template/add'),
    //   meta: {
    //     title: '新增消息模板',
    //   },
    //   hidden: true,
    // },
    // {
    //   path: 'template/edit',
    //   name: 'SettingMessageTemplateEdit',
    //   component: () => import('@/views/center-setting/message/template/edit'),
    //   meta: {
    //     title: '编辑消息模板',
    //   },
    //   hidden: true,
    // },
    {
      path: 'send',
      name: 'SettingMessageSend',
      component: EmptyRouter,
      redirect: 'send/list',
      meta: {
        title: '消息发送配置',
      },
      children: [
        {
          path: 'list',
          name: 'SettingMessageSendIndex',
          component: () => import('@/views/center-setting/message/send/index'),
          meta: {
            title: '消息发送配置',
          },
        },
        {
          path: 'add',
          name: 'SettingMessageSendAdd',
          component: () => import('@/views/center-setting/message/send/add'),
          meta: {
            title: '新增消息发送配置',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'edit',
          name: 'SettingMessageSendEdit',
          component: () => import('@/views/center-setting/message/send/edit'),
          meta: {
            title: '编辑消息发送配置',
          },
          hidden: true,
        },
      ],
    },
  ],
};
