/**
 * 示例
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/iam',
  items: [
    /** *********** 字段权限 ********************* */
    /**
     * 字段权限 - 分页查询接口列表
     *  @organizationId : 0
     */
    {
      key: 'xxx',
      url: '/v1/{organizationId}/apis',
      method: 'GET',
    },
    /**
     * 字段权限 - 接口字段维护列表
     *  @organizationId : 0
     */
    {
      key: 'xxx',
      url: '/v1/{organizationId}/apis/{permissionId}/fields',
      method: 'GET',
    },

    /**
     * 权限维护 - 权限数据（角色），新建 or 更新
     *  @organizationId : 0
     */
    {
      key: 'xxx',
      url: '/v1/{organizationId}/data-auth-manager/role/data/assign-role',
      method: 'POST',
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
  ],
};

export default {
  ...generate(config),
};
