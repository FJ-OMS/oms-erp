/**
 *Country : 国家信息管理

PATCH /v1/{organizationId}/countries
批量禁用国家定义

GET /v1/{organizationId}/countries/all
获取所有国家

POST /v1/{organizationId}/countries/regions
listCountryRegion

POST /v1/{organizationId}/countries/regions/address
查询国家、地区

GET /v1/{organizationId}/country/region
queryCountryRegion

 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/spfm/v1',
  items: [
    { key: 'getCountry', url: '/{organizationId}/countries', method: 'GET' },
    { key: 'listCountry', url: '/{organizationId}/countries/all', method: 'GET' },
    { key: 'addCountry', url: '/{organizationId}/country', method: 'POST' },
    { key: 'updateCountry', url: '/{organizationId}/countries/{countryId}', method: 'PUT' },
    // { key: 'deleteCountry', url: '/{organizationId}/countries', method: 'DELETE' },
    { key: 'detailCountry', url: '/v1/{organizationId}/countries/{countryId}', method: 'GET' },

    /**
     * Country Region : 地区信息管理Show/HideList OperationsExpand Operations
GET /v1/{organizationId}/countries/regions
查询国家/地区下的地区列表

GET /v1/{organizationId}/countries/regions/{regionId}
查询指定地区

PUT /v1/{organizationId}/countries/regions/{regionId}
禁用/启用地区定义，影响下级

POST /v1/{organizationId}/countries/{countryId}/region
新增地区定义

GET /v1/{organizationId}/countries/{countryId}/regions
查询国家下地区定义，使用树状结构返回

POST /v1/{organizationId}/countries/{countryId}/regions
批量新增地区定义

GET /v1/{organizationId}/countries/{countryId}/regions/lazy-tree
查询国家下地区定义，使用树状懒加载结构返回

GET /v1/{organizationId}/countries/{countryId}/regions/list
分页查询国家下地区定义

PUT /v1/{organizationId}/countries/{countryId}/regions/{regionId}
更新地区定义
     */
    { key: 'getCountryRegions', url: '/{organizationId}/countries', method: 'GET' },
    { key: 'addCountryRegions', url: '/{organizationId}/country', method: 'POST' },
    { key: 'updateCountryRegions', url: '/{organizationId}/countries/{countryId}', method: 'PUT' },
    // { key: 'deleteCountry', url: '/{organizationId}/countries', method: 'DELETE' },
    {
      key: 'detailCountryRegions',
      url: '/v1/{organizationId}/countries/{countryId}',
      method: 'GET',
    },
  ],
};

export default {
  ...generate(config),
};
