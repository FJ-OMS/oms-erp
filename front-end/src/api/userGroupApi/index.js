/**
 * 示例
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/iam',
  items: [
    /**
     * 用户组管理 - 用户组列表
     *  @organizationId : 0
     */
    {
      key: 'getUserGroupList',
      url: '/v1/{organizationId}/user-groups',
      method: 'GET',
    },
    /**
     * 用户组管理 - 创建
     *  @organizationId : 0
     */
    {
      key: 'createUserGroup',
      url: '/v1/{organizationId}/user-groups',
      method: 'POST',
    },
    /**
     * 用户组管理 - 修改
     *  @organizationId : 0
     */
    {
      key: 'updateUserGroup',
      url: '/v1/{organizationId}/user-groups',
      method: 'PUT',
    },
    /**
     * 用户组管理 - 删除
     *  @organizationId : 0
     */
    {
      key: 'deleteUserGroup',
      url: '/v1/{organizationId}/user-groups',
      method: 'DELETE',
    },
    /**
     * 用户组管理 - 用户组明细
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'getUserGroupInfo',
      url: '/v1/{organizationId}/user-groups/details/{userGroupId}',
      method: 'GET',
    },
    /** *****************一个用户组 对应的 用户列表******************************** */
    /**
     * 用户组管理 - 用户分配列表
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'getUserListByGroupId',
      url: '/v1/{organizationId}/user-groups/{userGroupId}/users',
      method: 'GET',
    },
    /**
     * 用户组管理 - 查询排除已经分配的用户列表
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'getUserListByGroupIdExclude',
      url: '/v1/{organizationId}/user-groups/{userGroupId}/users/exclude-users',
      method: 'GET',
    },
    /**
     * 用户组管理 - 创建用户分配
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'addUserListToGroupId',
      url: '/v1/{organizationId}/user-groups/{userGroupId}/users',
      method: 'POST',
    },
    /**
     * 用户组管理 - 删除用户分配
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'deleteUserListToGroupId',
      url: '/v1/{organizationId}/user-groups/{userGroupId}/users',
      method: 'DELETE',
    },
    /** ************** 一个用户 对应的 用户组列表***************************** */
    /**
     * 用户组管理 - 用户组分配列表
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'getUserGroupListByUserId',
      url: '/v1/{organizationId}/{userId}/user-group-assigns',
      method: 'GET',
    },
    /**
     * 用户组管理 - 查询排除已经分配的用户组列表
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'getUserGroupListByUserIdExclude',
      url: '/v1/{organizationId}/{userId}/user-group-assigns/exclude-groups',
      method: 'GET',
    },
    /**
     * 用户组管理 - 创建用户组分配
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'addUserGroupListToUserId',
      url: '/v1/{organizationId}/{userId}/user-group-assigns',
      method: 'POST',
    },
    /**
     * 用户组管理 - 删除用户组分配
     *  @organizationId : 0
     *  @userGroupId
     */
    {
      key: 'deleteUserGroupListToUserId',
      url: '/v1/{organizationId}/{userId}/user-group-assigns',
      method: 'DELETE',
    },
  ],
};

export default {
  ...generate(config),
};
