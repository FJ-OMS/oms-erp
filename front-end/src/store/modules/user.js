import { userApi } from '@/api';
import { getToken, setToken, removeToken } from '@/utils/auth';
import router, { resetRouter } from '@/router';
import { VUE_APP_BASE_API } from '@/utils/index';

import permissionJson from '@/permissionOS.json';

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    user: '',
    avatar: '',
    introduction: '',
    roles: [],
    permissions: [],
    permissionBtns: [],
    userInfo: null,
    isSuperAdmin: true,
  };
};
const state = getDefaultState();
const mutations = {
  RESET_STATE: state => {
    Object.assign(state, getDefaultState());
  },
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
  },
  SET_USER: (state, user) => {
    state.user = user;
  },
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo;
  },
  SET_SUPER_ADMIN: (state, isSuperAdmin) => {
    state.isSuperAdmin = isSuperAdmin;
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions;
    const permissionBtnList = [];
    permissions.forEach(a1 => {
      (a1.children || []).forEach(a2 => {
        (a2.children || []).forEach(a3 => {
          (a3.children || []).forEach(a4 => {
            // if (a4.permissionType == 'btn') {
            permissionBtnList.push(a4.code.toLocaleLowerCase());
            // }
          });
        });
      });
    });
    state.permissionBtns = permissionBtnList;
  },
};
const actions = {
  setSuperAdmin({ commit }, isSuperAdmin) {
    commit('SET_SUPER_ADMIN', isSuperAdmin);
  },
  setUserInfo({ commit }, userInfo) {
    commit('SET_USER_INFO', userInfo);
  },
  setToken({ commit }, access_token) {
    commit('SET_TOKEN', access_token);
  },
  // user login
  login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      commit('SET_TOKEN', 'admin-token');
      setToken('admin-token');
      resolve();
      // login({ username: username.trim(), password: password }).then(response => {
      //   const { data } = response
      //   commit('SET_TOKEN', data.token)
      //   setToken(data.token)
      //   resolve()
      // }).catch(error => {
      //   reject(error)
      // })
    });
  },
  resetInfo({ commit }) {
    return new Promise((resolve, reject) => {
      removeToken();
      commit('SET_TOKEN', '');
      commit('SET_PERMISSIONS', []);
      resetRouter();
      commit('RESET_STATE');
      resolve();
    });
  },
  // get user info
  getInfo({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      if (process.env.VUE_APP_ACCESS_TOKEN) {
        const response = {
          id: '=3pLNuuotTVQSDRtvdZ5QKw===',
          loginName: 'admin',
          email: 'admin@hzero.com',
          organizationId: 0,
          realName: '超级管理员',
          phone: '18666666666',
          imageUrl:
            'minio.baogicloud.com/test/0/1632481375656_16EK7K8I_src=http___img.zcool.cn_community_01dd6c56dc421232f875520ffb98e9.png@1280w_1l_2o_100sh.png&refer=http___img.zcool.jpg',
          language: 'zh_CN',
          languageName: '简体中文',
          timeZone: 'GMT+8',
          lastPasswordUpdatedAt: '2021-08-06 12:16:19',
          regionId: 0,
          phoneCheckFlag: 1,
          emailCheckFlag: 1,
          passwordResetFlag: 1,
          tenantName: 'HZERO平台',
          tenantNum: 'SKYER',
          dateFormat: 'YYYY-MM-DD',
          timeFormat: 'HH:mm:ss',
          dateTimeFormat: 'YYYY-MM-DD HH:mm:ss',
          changePasswordFlag: 0,
          title: 'HZERO技术平台',
          logo: '/hsop-image/doc_classify/0/8ff6ad27c153445d90606db2eca4c268/logo.png',
          menuLayout: 'side-all',
          menuLayoutTheme: 'color',
          roleMergeFlag: 0,
          tenantId: 0,
          currentRoleId: '=3pLNuuotTVQSDRtvdZ5QKw===',
          currentRoleCode: 'role/site/default/administrator',
          currentRoleName: '管理员1',
          currentRoleLevel: 'site',
          favicon:
            '/hsop-image/doc_classify/0/8ff6ad27c153445d90606db2eca4c268/logo.png',
          dataHierarchyFlag: 0,
          recentAccessTenantList: [],
        };
        if (response.failed === true) {
          console.error(response);
          reject(response);
        }
        const data = {
          // roles: response.recentAccessTenantList || [],
          name: response.realName,
          user: response.loginName,
          avatar: response.imageUrl,
          introduction: response.title,
        };
        commit('SET_USER_INFO', Object.assign(response, data));
        const { name, user, avatar, introduction } = data;
        commit('SET_USER', user);
        commit('SET_NAME', name);
        commit('SET_USER', user);
        commit('SET_AVATAR', avatar);
        commit('SET_INTRODUCTION', introduction);
        commit(
          'SET_SUPER_ADMIN',
          response.currentRoleLevel === 'site' && response.loginName === 'admin',
        );
        commit('SET_PERMISSIONS', permissionJson);
        resolve(data);
        return;
      }
      userApi
        .getUserInfo()
        .then(async response => {
          if (response.failed === true) {
            console.error(response);
            reject(response);
          }
          const data = {
            // roles: response.recentAccessTenantList || [],
            name: response.realName,
            user: response.loginName,
            avatar: response.imageUrl,
            introduction: response.title,
          };
          commit('SET_USER_INFO', Object.assign(response, data));
          const { name, user, avatar, introduction } = data;
          commit('SET_USER', user);
          commit('SET_NAME', name);
          commit('SET_USER', user);
          commit('SET_AVATAR', avatar);
          commit('SET_INTRODUCTION', introduction);
          commit(
            'SET_SUPER_ADMIN',
            response.currentRoleLevel === 'site' && response.loginName === 'admin',
          );
          await dispatch('getUserPermission');
          resolve(data);
        })
        .catch(error => {
          reject(error);
        });
    });
  },
  // 获取用户菜单权限
  getUserPermission({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      userApi
        .getUserMenuTree()
        .then(response => {
          commit('SET_PERMISSIONS', response);
          resolve(response);
        })
        .catch(error => {
          reject(error);
        });
      // permissionApi.get({ roleId: ROLE_ID }).then(res => {
      //   const PERMISSION_ROUTE_NAMES = [];
      //   const F = list => {
      //     list.forEach(e => {
      //       if (e.check === true && !PERMISSION_ROUTE_NAMES.includes(e.path)) {
      //         PERMISSION_ROUTE_NAMES.push(e.path);
      //       }
      //       if (Array.isArray(e.levelPermissionVOS)) {
      //         F(e.levelPermissionVOS);
      //       }
      //     });
      //   };
      //   F(Array.isArray(res) ? res : []);
      //   console.log('PERMISSION_ROUTE_NAME_LIST', PERMISSION_ROUTE_NAMES);
      //   commit('SET_PERMISSION_ROUTE_NAMES', PERMISSION_ROUTE_NAMES);
      //   resolve(res);
      // });
    });
  },
  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      const LOGOUT_URL = `${VUE_APP_BASE_API()}/oauth/logout?access_token=${state.token}`;
      window.location.replace(LOGOUT_URL);
      // commonApi
      //   .logout({ access_token: state.token })
      //   .then(() => {
      removeToken();
      commit('SET_TOKEN', '');
      commit('SET_PERMISSIONS', []);
      resetRouter();
      // reset visited views and cached views
      // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
      dispatch('tagsView/delAllViews', null, {
        root: true,
      });
      resolve();
      // })
      // .catch(error => {
      //   removeToken();
      //   commit('SET_TOKEN', '');
      //   commit('SET_PERMISSIONS', []);
      //   reject(error);
      // });
    });
  },
  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '');
      commit('SET_PERMISSIONS', []);
      removeToken();
      resolve();
    });
  },
  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token';
    commit('SET_TOKEN', token);
    setToken(token);
    const { roles } = await dispatch('user/getInfo');
    console.log('changeRoles  user/getInfo roles=', roles);
    resetRouter();
    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, {
      root: true,
    });
    // dynamically add accessible routes
    router.addRoutes(accessRoutes);
    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, {
      root: true,
    });
  },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
