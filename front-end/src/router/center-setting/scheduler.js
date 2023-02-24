/*
 * 任务管理  SchedulerManage
 */
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
export default {
  path: 'scheduler',
  name: 'SettingScheduler',
  component: EmptyRouterAlive,
  meta: {
    title: '调度服务',
  },
  children: [
    {
      path: 'executor',
      name: 'SettingSchedulerExecutor',
      component: EmptyRouter,
      redirect: 'executor/index',
      meta: {
        title: '执行器管理',
      },
      children: [
        {
          path: 'index',
          name: 'SettingSchedulerExecutorIndex',
          component: () => import('@/views/center-setting/scheduler/executor'),
          meta: {
            title: '执行器管理',
          },
        },
      ],
    },

    {
      path: 'task',
      name: 'SettingSchedulerTask',
      component: EmptyRouter,
      redirect: 'task/index',
      meta: {
        title: '调度任务',
      },
      children: [
        {
          path: 'index',
          name: 'SettingSchedulerTaskIndex',
          component: () => import('@/views/center-setting/scheduler/task'),
          meta: {
            title: '调度任务',
          },
        },
      ],
    },
    {
      path: 'log',
      name: 'SettingSchedulerLog',
      component: EmptyRouter,
      redirect: 'log/index',
      meta: {
        title: '调度日志',
      },
      children: [
        {
          path: 'index',
          name: 'SettingSchedulerLogIndex',
          component: () => import('@/views/center-setting/scheduler/log'),
          meta: {
            title: '调度日志',
          },
        },
      ],
    },
    {
      path: 'executable',
      name: 'SettingSchedulerExecutable',
      component: EmptyRouter,
      redirect: 'executable/index',
      meta: {
        title: '可执行定义',
      },
      children: [
        {
          path: 'index',
          name: 'SettingSchedulerExecutableIndex',
          component: () => import('@/views/center-setting/scheduler/executable'),
          meta: {
            title: '可执行定义',
          },
        },
      ],
    },
    {
      path: 'request',
      name: 'SettingSchedulerRequest',
      component: EmptyRouter,
      redirect: 'request/list',
      meta: {
        title: '请求定义',
      },
      children: [
        {
          path: 'list',
          name: 'SettingSchedulerRequestIndex',
          component: () => import('@/views/center-setting/scheduler/request'),
          meta: {
            title: '请求定义',
          },
        },
        {
          path: 'add',
          name: 'SettingSchedulerRequestAdd',
          component: () => import('@/views/center-setting/scheduler/request/add'),
          meta: {
            title: '新增请求定义',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'edit',
          name: 'SettingSchedulerRequestEdit',
          component: () => import('@/views/center-setting/scheduler/request/edit'),
          meta: {
            title: '编辑请求定义',
          },
          hidden: true,
        },
        {
          path: 'permissions',
          name: 'SettingSchedulerRequestPermissions',
          component: () => import('@/views/center-setting/scheduler/request/permissions'),
          meta: {
            title: '权限分配',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'concurrent',
      name: 'SettingSchedulerConcurrent',
      component: EmptyRouter,
      redirect: 'concurrent/index',
      meta: {
        title: '并发请求',
      },
      children: [
        {
          path: 'index',
          name: 'SettingSchedulerConcurrentIndex',
          component: () => import('@/views/center-setting/scheduler/concurrent'),
          meta: {
            title: '并发请求',
          },
        },
      ],
    },
  ],
};
