/*
 * @Author: your name
 * @Date: 2021-08-06 15:12:58
 * @LastEditTime: 2021-08-17 09:46:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\settingApi\report.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/srpt',
  items: [
    /** 获取数据集列表 */
    {
      key: 'getReportList',
      url: '/v1/{organizationId}/datasets',
      method: 'get',
    },
    /** 创建数据集 */
    {
      key: 'addReport',
      url: '/v1/{organizationId}/datasets',
      method: 'post',
    },
    /** 修改数据集 */
    {
      key: 'editReport',
      url: '/v1/{organizationId}/datasets',
      method: 'put',
    },
    /** 删除数据集 */
    {
      key: 'deleteReport',
      url: '/v1/{organizationId}/datasets',
      method: 'delete',
    },
    /** 数据集详情 */
    {
      key: 'getReportDetail',
      url: '/v1/{organizationId}/datasets/detail',
      method: 'get',
    },
    /** 查看报表 */
    {
      key: 'lookReportDetail',
      url: '/v1/{organizationId}/datasets/{dataSetId}',
      method: 'get',
    },
    /** 预览SQL语句 */
    {
      key: 'previewReportSql',
      url: '/v1/{organizationId}/datasets/preview-sql',
      method: 'post',
    },
    /** 获取XML示例 */
    // 暂废
    // {
    //   key: 'getReporXmlExample',
    //   url: '/v1/{organizationId}/datasets/xml-sample',
    //   method: 'post'
    // }
    /** **************************************** 报表模板管理接口部分 ************************************************/
    /** 报表模板管理列表 */
    {
      key: 'getTemplateList',
      url: '/v1/{organizationId}/templates',
      method: 'get',
    },
    {
      key: 'addTemplate',
      url: '/v1/{organizationId}/templates',
      method: 'post',
    },
    {
      key: 'updateTemplate',
      url: '/v1/{organizationId}/templates',
      method: 'put',
    },
    {
      key: 'deleteTemplate',
      url: '/v1/{organizationId}/templates',
      method: 'delete',
    },
    /** **************************************** 报表定义 ************************************************/
    /** 报表定义列表 */
    {
      key: 'getDesigner',
      url: '/v1/{organizationId}/reports/designer',
      method: 'get',
    },
    /** 新建报表定义 */
    {
      key: 'addDesigner',
      url: '/v1/{organizationId}/reports/designer',
      method: 'post',
    },
    /** 编辑报表定义 */
    {
      key: 'editDesigner',
      url: '/v1/{organizationId}/reports/designer',
      method: 'put',
    },
    /** 删除报表定义 */
    {
      key: 'deleteDesigner',
      url: '/v1/{organizationId}/reports/designer',
      method: 'delete',
    },
    /** 报表定义数据详情 */
    {
      key: 'getDesignerDetail',
      url: '/v1/{organizationId}/reports/designer/{reportId}',
      method: 'get',
    },
    /* ********************************* 报表查询 **************************************** */
    /** 报表查询列表 */
    {
      key: 'getInquire',
      url: '/v1/{organizationId}/reports',
      method: 'get',
    },
    /** 报表查看 元数据 */
    {
      key: 'getInquireDetail',
      url: '/v1/{organizationId}/reports/{reportUuid}',
      method: 'get',
    },
    /** 生成报表 */
    {
      key: 'createInquireDetail',
      url: '/v1/{organizationId}/reports/{reportUuid}/data',
      method: 'post',
    },
    /* ********************************* 报表请求 **************************************** */
    /** 报表请求列表 */
    {
      key: 'getInquireRequest',
      url: '/v1/{organizationId}/report-requests',
      method: 'get',
    },
  ],
};

export default {
  ...generate(config),
};
