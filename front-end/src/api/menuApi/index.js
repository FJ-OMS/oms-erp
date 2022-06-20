/**
 * Tenant(Site Level) : 租户管理(平台级)
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/iam',
  items: [
    // GET /skyer/v1/menus/tree菜单展示-获取导航栏树形菜单
    { key: 'treeMenu', url: '/skyer/v1/menus/tree', method: 'get' },

    // 租户层-菜单管理-获取树形菜单
    { key: 'getMenuTree', url: '/skyer/v1/{organizationId}/menus/manage-tree', method: 'get' },

    // 租户层-创建菜单

    { key: 'addMenu', url: '/skyer/v1/{organizationId}/menus/create', method: 'POST' },

    // 租户层-禁用菜单及其子菜单
    { key: 'disableMenu', url: '/skyer/v1/{organizationId}/menus/disable', method: 'PUT' },

    // 租户层-启用菜单及其子菜单
    { key: 'enableMenu', url: '/skyer/v1/{organizationId}/menus/enable', method: 'PUT' },

    { key: 'detailMenu', url: '/v1/tenants-manager/', method: 'GET' },

    // 租户层-更新菜单
    { key: 'updateMenu', url: '/skyer/v1/{organizationId}/menus/update', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
