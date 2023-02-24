/**
 * 示例
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/iam',
  items: [
    // 用户信息
    { key: 'getUserInfo', url: '/skyer/v1/users/self', method: 'get' },
    // 用户菜单权限  获取当前用户树形菜单
    {
      key: 'getUserMenuTree',
      url: '/v1/sys/menu/manage/user/tree',
      method: 'get',
      loadingHide: true,
    },
    // 初始化菜单（仅第一次调用）
    {
      key: 'initMenuTree',
      url: '/v1/sys/menu/manage/menu/init',
      method: 'POST',
    },
    // 创建菜单
    {
      key: 'addMenuTree',
      url: '/v1/sys/menu/manage/menu/create',
      method: 'POST',
    },
    // 删除菜单
    {
      key: 'deleteMenuTree',
      url: '/v1/sys/menu/manage/menu/delete',
      method: 'POST',
    },
    // 批量删除菜单
    {
      key: 'batchDeleteMenuTree',
      url: '/v1/sys/menu/manage/menu/batch/delete',
      method: 'POST',
    },
    // 修改菜单
    {
      key: 'updateMenuTree',
      url: '/v1/sys/menu/manage/menu/update',
      method: 'POST',
    },

    /**
     * 用户管理-分页查询
     *  @organizationId : 0
     */
    { key: 'getUserList', url: '/skyer/v1/{organizationId}/users/paging', method: 'get' },
    /**
     * 用户管理-修改密码
     *  @organizationId : 0
     *  @userId : 194783206599467008
     */
    {
      key: 'adminUpdatePassword',
      url: '/skyer/v1/{organizationId}/users/{userId}/admin-password',
      method: 'PUT',
      type: 'BODY',
    },
    /**
     * 用户管理-创建用户
     *  @organizationId : 0
     */
    {
      key: 'createUser',
      url: '/skyer/v1/{organizationId}/users',
      method: 'POST',
    },

    /**
     * 用户管理-根据id查询用户信息
     *  @organizationId : 0
     *  @userId
     */
    {
      key: 'getUserInfoById',
      url: '/skyer/v1/{organizationId}/users/{userId}/info',
      method: 'GET',
    },

    /**
     * 用户管理- 子账户 - 根据id查询所有分配给用户的角色
     *  @organizationId : 0
     *  @userId
     */
    {
      key: 'getUserAllRolesById',
      url: '/skyer/v1/{organizationId}/member-roles/user-all-roles/{userId}',
      method: 'GET',
    },
    /**
     * 用户管理- 角色管理 - 批量删除用户的角色
     *  @organizationId : 0
     *  @userId
     */
    {
      key: 'deleteUserRoles',
      url: '/skyer/v1/{organizationId}/member-roles/batch-delete',
      method: 'DELETE',
    },
    /**
     * 用户管理- 角色管理 - 批量分配成员角色
     *  @organizationId : 0
     *  @userId
     */
    {
      key: 'addUserRoles',
      url: '/skyer/v1/{organizationId}/member-roles/batch-assign',
      method: 'POST',
    },

    /**
     * 用户管理 - 修改用户
     *  @organizationId : 0
     */
    {
      key: 'updateUserInfo',
      url: '/skyer/v1/{organizationId}/users',
      method: 'PUT',
      type: 'BODY',
    },

    /** *************** 个人中心 ******************* */

    /**
     * 登录用户 - 修改自己的密
        {
          "organizationId": 0,
          "originalPassword": "string",
          "password": "string"
        }
     */
    {
      key: 'updateMyPassword',
      url: '/skyer/v1/users/password',
      method: 'PUT',
      type: 'BODY',
    },
    /**
     * 登录用户 - 更改名称
     * @realName
     */
    {
      key: 'updateMyRealName',
      url: '/skyer/v1/users/real-name',
      method: 'PUT',
      type: 'Query',
    },
    /**
     * 登录用户 - 更改头像
     * @avatar
     */
    {
      key: 'updateMyAvatar',
      url: '/skyer/v1/users/avatar',
      method: 'PUT',
      type: 'Query',
    },
    /**
     * 登录用户 - 前置密码校验
     * @password
     * @ResponseBody  {lastCheckKey}
     */
    {
      key: 'checkMyPassword',
      url: '/skyer/v1/users/password/pre-validate',
      method: 'GET',
    },
    /**
     * 登录用户 - 给新手机号发送验证码
     * @lastCheckKey
     * @phone
     * @ResponseBody    "captchaKey", "message" , "interval": 60
     */
    {
      key: 'sendCaptchaByPhone',
      url: '/skyer/v1/users/phone-new/send-captcha',
      method: 'GET',
    },
    /**
     * 登录用户 - 新手机号验证码验证并修改手机
     * @lastCheckKey  前置密码校验返回的
     * @captchaKey    给新手机号发送验证码返回的
     * @captcha       手机接收的验证码
     * @phone         手机号
     */
    {
      key: 'checkCaptchaByPhone',
      url: '/skyer/v1/users/phone-new/validate-captcha',
      method: 'GET',
    },

    /**
     * 登录用户 - 给新邮箱发送验证码
     * @lastCheckKey
     * @email
     * @ResponseBody    "captchaKey", "message" , "interval": 60
     */
    {
      key: 'sendCaptchaByEmail',
      url: '/skyer/v1/users/email-new/send-captcha',
      method: 'GET',
    },
    /**
     * 登录用户 - 新邮箱验证码验证并修改邮箱
     * @lastCheckKey  前置密码校验返回的
     * @captchaKey    给新邮箱发送验证码返回的
     * @captcha       邮箱接收的验证码
     * @email         邮箱号
     */
    {
      key: 'checkCaptchaByEmail',
      url: '/skyer/v1/users/email-new/validate-captcha',
      method: 'GET',
    },
    /**
     * 查询用户公司的 ids
     */
    {
      key: 'getUserCompany',
      url: '/skyer/v1/users/{userId}/company',
      method: 'GET',
    },
    // 启用用户
    {
      key: 'userNnfrozen',
      url: '/skyer/v1/{organizationId}/users/{userId}/unfrozen',
      method: 'POST',
    },
    // 禁用用户
    {
      key: 'userFrozen',
      url: '/skyer/v1/{organizationId}/users/{userId}/frozen',
      method: 'POST',
    },
    // 锁定用户
    {
      key: 'userLocked',
      url: '/skyer/v1/{organizationId}/users/{userId}/locked',
      method: 'POST',
    },
    // 解锁用户
    {
      key: 'userUnlocked',
      url: '/skyer/v1/{organizationId}/users/{userId}/unlocked',
      method: 'POST',
    },
    // 删除用户
    {
      key: 'deleteUser',
      url: '/skyer/v1/{organizationId}/users/{userId}/delete',
      method: 'DELETE',
    },
  ],
};

export default {
  ...generate(config),
};
