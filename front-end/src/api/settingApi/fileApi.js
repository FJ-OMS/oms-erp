/**
 * File : 文件
 *

 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/sfle',
  items: [
    /**
     * 获取文件列表汇总
     */

    { key: 'getFileList', url: '/v1/files/summary', method: 'GET' },
    /**
     * 根据key下载文件
     */
    {
      key: 'downloadFileByKey',
      url: '/v1/files/download-by-key',
      method: 'GET',
      rType: 2,
    },
    /**
     * 根据key获取文件授权url
     */
    {
      key: 'getFileUrlByKey',
      url: '/v1/files/file-url',
      method: 'GET',
    },
    /**
     * 获取文件授权ur
     */
    {
      key: 'getFileUrlByUrl',
      url: '/v1/files/signedUrl',
      method: 'GET',
      errorHide: true,
      loadingHide: true,
    },

    /**
     * 根据文件key预览文件
     */
    {
      key: 'viewFileByKey',
      url: '/v1/{organizationId}/file-preview/by-key',
      method: 'GET',
      rType: 2,
    },
    /**
     * 基于Multipart上传文件
     */
    {
      key: 'uploadFiles',
      url: '/v1/{organizationId}/files/multipart',
      method: 'POST',
      cType: 2,
    },

    /**
     * 文件存储配置信息
     */
    { key: 'getFileStorageConfigs', url: '/v2/storage-configs/{storageType}', method: 'GET' },
    /**
     * 新增
     */
    { key: 'createFileStorageConfigs', url: '/v2/storage-configs', method: 'put' },
    /**
     * 删除
     */
    { key: 'deleteFileStorageConfigs', url: '/v2/storage-configs', method: 'DELETE' },

    /** **  File Capacity Config : 文件容量配置**** */

    /**
     * 文件容量配置列表
     */
    { key: 'getFileCapacityConfigs', url: '/v1/{organizationId}/capacity-configs', method: 'GET' },

    /**
     * 保存文件容量配置
     */
    {
      key: 'updateFileCapacityConfigs',
      url: '/v1/{organizationId}/capacity-configs',
      method: 'PUT',
    },

    /**
     * 删除文件上传配置
     */
    { key: 'deleteUploadConfigs', url: '/v1/upload-configs', method: 'DELETE' },

    /**
     * 创建文件上传配置
     */
    { key: 'addUploadConfigs', url: '/v1/upload-configs', method: 'POST' },
    /**
     * 修改文件上传配置
     */
    { key: 'updateUploadConfigs', url: '/v1/upload-configs', method: 'PUT' },
    /**
     * 文件上传配置查询明细
     */
    { key: 'getUploadConfigsDetail', url: '/v1/upload-configs/detail', method: 'GET' },
    /**
     * 文件上传配置明细
     */
    {
      key: 'getUploadConfigsDetailById',
      url: '/v1/upload-configs/{uploadConfigId}',
      method: 'GET',
    },
  ],
};
export default {
  ...generate(config),
};
