/*
 * @Author: your name
 * @Date: 2021-06-29 09:18:37
 * @LastEditTime: 2021-09-10 17:52:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\commonApi\areaApi.js
 */
/**
 * CnareaAdministrativeDivision Manage : 中国行政区划分管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/spfm/v1',
  items: [
    /**
     * 获取省市
     */
    { key: 'getDistrictTree', url: '/cnareaAdministrativeDivision/getDistrictTree', method: 'GET' },
    /**
     * 获得省级行政区信息
     */
    { key: 'getProvince', url: '/cnareaAdministrativeDivision/getProvince', method: 'GET' },
    /**
     * 获得市级及以下行政区信息
     */
    { key: 'getCity', url: '/cnareaAdministrativeDivision/getCity', method: 'GET' },
    /**
     * 根据Id获取中国行政地区表信息
     */
    {
      key: 'getAreaDetails',
      url: '/cnareaAdministrativeDivision/details',
      method: 'GET',
    },
    /**
     * 修改中国行政地区表
     */
    { key: 'updateArea', url: '/cnareaAdministrativeDivision/update', method: 'POST' },
    /**
     * 删除中国行政地区表
     */
    {
      key: 'deleteArea',
      url: '/cnareaAdministrativeDivision/delete',
      method: 'POST',
      type: 'query',
    },
    /**
     * 新增中国行政地区表
     */
    { key: 'addArea', url: '/cnareaAdministrativeDivision/add', method: 'POST' },
    // 语言列表
    { key: 'getLanguages', url: '/languages/list', method: 'GET' },
    /** 数据源 */
    { key: 'getDatasource', url: '/{organizationId}/datasources', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
