/*
 * 文件管理  File
 */
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
export default {
  path: 'file',
  name: 'SettingFile',
  component: EmptyRouterAlive,
  redirect: 'file/list',
  meta: {
    title: '文件管理',
  },
  children: [
    {
      path: 'search',
      name: 'SettingFileSearch',
      component: EmptyRouter,
      redirect: 'search/list',
      meta: {
        title: '汇总查询',
      },
      children: [
        {
          path: 'list',
          name: 'SettingFileSearchIndex',
          component: () => import('@/views/center-setting/file/search'),
          meta: {
            title: '汇总查询',
          },
        },
        {
          path: '',
          name: 'SettingFileSearchIndex_UpLoad',
          meta: {
            title: '文件上传-汇总查询',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingFileSearchIndex_View',
          meta: {
            title: '预览-汇总查询',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingFileSearchIndex_DownLoad',
          meta: {
            title: '下载-汇总查询',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'storage',
      name: 'SettingFileStorage',
      component: EmptyRouter,
      redirect: 'storage/list',
      meta: {
        title: '存储配置',
      },
      children: [
        {
          path: 'list',
          name: 'SettingFileStorageIndex',
          component: () => import('@/views/center-setting/file/storage'),
          meta: {
            title: '存储配置',
          },
        },
        {
          path: '',
          name: 'SettingFileStorageIndex_Add',
          meta: {
            title: '新增-存储配置',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingFileStorageIndex_Edit',
          meta: {
            title: '编辑-存储配置',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingFileStorageIndex_Delete',
          meta: {
            title: '删除-存储配置',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'upload/ram',
      name: 'SettingFileUploadRam',
      component: EmptyRouter,
      redirect: 'upload/ram/index',
      meta: {
        title: '上传容量配置',
      },
      children: [
        {
          path: 'index',
          name: 'SettingFileUploadRamIndex',
          component: () => import('@/views/center-setting/file/upload-ram/index'),
          meta: {
            title: '上传容量配置',
          },
        },
        {
          path: '',
          name: 'SettingFileUploadRamIndex_Save',
          meta: {
            title: '保存-上传容量配置',
          },
          hidden: true,
        },
      ],
    },
    {
      path: 'upload/config',
      name: 'SettingFileUploadConfig',
      component: EmptyRouter,
      redirect: 'upload/config/index',
      meta: {
        title: '上传详细配置',
      },
      children: [
        {
          path: 'index',
          name: 'SettingFileUploadConfigIndex',
          component: () => import('@/views/center-setting/file/upload-config/index'),
          meta: {
            title: '上传详细配置',
          },
        },
        {
          path: '',
          name: 'SettingFileUploadConfigIndex_Add',
          meta: {
            title: '新增-上传详细配置',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingFileUploadConfigIndex_Edit',
          meta: {
            title: '编辑-上传详细配置',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'SettingFileUploadConfigIndex_Delete',
          meta: {
            title: '删除-上传详细配置',
          },
          hidden: true,
        },
      ],
    },
  ],
};
