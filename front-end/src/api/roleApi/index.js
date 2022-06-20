/**
 * 示例
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/iam',
  items: [
    /**
     * 角色管理-分页查询
     *  @organizationId : 0
     */
    { key: 'getRoleList', url: '/skyer/v1/roles/self/manageable-roles', method: 'get' },
    /**
     * 角色管理-创建角色
     *  @organizationId : 0
     */
    { key: 'createRole', url: '/skyer/v1/{organizationId}/roles', method: 'POST' },
    /**
     * 角色管理-编辑角色
     *  @organizationId : 0
     */
    { key: 'updateRole', url: '/skyer/v1/{organizationId}/roles', method: 'PUT' },
    /**
     * 角色管理-禁用角色
     *  @organizationId : 0
     */
    { key: 'disableRole', url: '/skyer/v1/{organizationId}/roles/disable', method: 'PUT' },
    /**
     * 角色管理-启用角色
     *  @organizationId : 0
     */
    { key: 'enableRole', url: '/skyer/v1/{organizationId}/roles/enable', method: 'PUT' },

    /** *********** 一个角色对应的用户列表 ********************* */
    /**
     * 角色管理-查询角色已分配的用户
     *  @organizationId : 0
     */
    {
      key: 'getUserListByRoleId',
      url: '/skyer/v1/{organizationId}/member-roles/role-users/{roleId}',
      method: 'GET',
    },
    /** *********** 一个角色对应的权限分配 ********************* */
    /**
     * 权限分配 - 查询角色可分配的权限集树
     *  @organizationId : 0
     */
    {
      key: 'getPermissionTreeByRoleId',
      url: '/skyer/v1/{organizationId}/roles/{roleId}/permission-set-tree',
      method: 'GET',
    },
    /**
     * 权限分配 - 分配角色权限集
     *  @organizationId : 0
     */
    {
      key: 'assignPermissionTreeByRoleId',
      url: '/skyer/v1/{organizationId}/roles/{roleId}/permission-sets/assign',
      method: 'PUT',
    },
    /**
     * 权限分配 - 回收角色权限集
     *  @organizationId : 0
     */
    {
      key: 'recyclePermissionTreeByRoleId',
      url: '/skyer/v1/{organizationId}/roles/{roleId}/permission-sets/recycle',
      method: 'PUT',
    },
    /** *********** 一个角色对应的 数据权限 ********************* */
    /**
     * 角色数据权限定义 - 数据权限定义列表
     *  @organizationId : 0
     */
    {
      key: 'getAuthorizationsByRoleId',
      url: '/v1/{organizationId}/roles/{roleId}/role-auths/{organizationId}',
      method: 'GET',
    },
    /**
     * 角色数据权限定义 - 批量新增或保存角色数据权限定义明细
     *  @organizationId : 0
     */
    {
      key: 'addAuthorizationsByRoleId',
      url: '/v1/{organizationId}/roles/{roleId}/role-auths',
      method: 'POST',
    },
    /**
     * 角色数据权限定义 - 删除角色数据权限定义
     *  @organizationId : 0
     */
    {
      key: 'delAuthorizationsByRoleId',
      url: '/v1/{organizationId}/roles/{roleId}/role-auths',
      method: 'DELETE',
    },
    // 获取角色菜单权限树
    {
      key: 'getAllmenu',
      url: '/v1/sys/menu/manage/role/menu',
      method: 'GET',
    },
    // 批量更新角色菜单权限数据
    {
      key: 'saveRoleMenu',
      url: '/v1/sys/menu/manage/role/menu/batch/save',
      method: 'POST',
    },
  ],
};

export default {
  ...generate(config),
};
