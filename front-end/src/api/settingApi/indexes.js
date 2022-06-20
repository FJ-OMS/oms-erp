/*
 * @Author: your name
 * @Date: 2021-07-20 16:07:16
 * @LastEditTime: 2021-07-28 14:45:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\settingApi\indexes.js
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/ssrh',
  items: [
    /** 索引配置管理列表 */
    {
      key: 'getIndexesList',
      url: '/v1/{organizationId}/indices',
      method: 'get',
    },
    /** 索引配置管理列表-添加数据 */
    {
      key: 'getIndexesAdd',
      url: '/v1/{organizationId}/indices/with-fields',
      method: 'post',
    },
    /** 索引配置管理列表-详情 */
    {
      key: 'getIndexesDetail',
      url: '/v1/{organizationId}/index-fields/without-pages',
      method: 'get',
      loadingHide: true,
    },
    /** 索引配置管理列表-更新状态 */
    {
      key: 'updataIndexes',
      url: '/v1/{organizationId}/indices/with-fields',
      method: 'post',
    },
    /** ************************* 查询配置 **********************************/
    /** 查询配置列表  */
    {
      key: 'getInquiryList',
      url: '/v1/{organizationId}/query-configs',
      method: 'get',
    },
    /** 配置查询 */
    {
      key: 'getInquiryQuery',
      url: '/v1/{organizationId}/query-configs/{configCode}/query',
      method: 'post',
    },
    /** 创建查询配置 */
    {
      key: 'addInquiry',
      url: '/v1/{organizationId}/query-configs',
      method: 'post',
    },
    /** 修改查询配置 */
    {
      key: 'editInquiry',
      url: '/v1/{organizationId}/query-configs',
      method: 'put',
    },
    /** 删除查询配置 */
    {
      key: 'deleteInquiry',
      url: '/v1/{organizationId}/query-configs',
      method: 'delete',
    },
    /** 获取显示字段列表 */
    {
      key: 'getInquiryFieldList',
      url: '/v1/{organizationId}/indices/with-fields',
      method: 'get',
    },
    {
      key: 'addInquiryField',
      url: '/v1/{organizationId}/indices/with-fields',
      method: 'post',
    },
    {
      key: 'InquiryFieldQuery',
      url: '/v1/{organizationId}/query-configs/{configCode}/query',
      method: 'post',
    },
    /** ************************* 索引同步 **********************************/
    /** 索引同步列表 */
    {
      key: 'getIndexesSyncList',
      url: '/v1/{organizationId}/index-sync-configs',
      method: 'get',
    },
    /** 创建索引同步配置 */
    {
      key: 'AddIndexesSync',
      url: '/v1/{organizationId}/index-sync-configs',
      method: 'post',
    },
    /** 查询索引同步配置明细 */
    {
      key: 'getIndexesSyncConfigs',
      url: '/v1/{organizationId}/index-sync-configs/{syncConfId}',
      method: 'get',
    },
  ],
};

export default {
  ...generate(config),
};
