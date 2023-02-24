import Cookies from 'js-cookie';
import { commonApi } from '@/api';
const LIKE_CODE = 'myLikeMenuList';
const LIKE_CODE_VERSION = 3;
const state = {
  sidebar: {
    opened: true, // Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true,
    liked: Cookies.get('sidebarLiked') ? !!+Cookies.get('sidebarLiked') : false,
    withoutAnimation: false,
    likedData: [],
    likedMax: 8, // 最多添加 8 个收藏菜单
  },
  device: 'desktop',
  size: Cookies.get('size') || 'medium',
  loadingCount: 0,
  loadingDelayed: 501, // ms 延时关闭，防止递归请求的loading频繁开关。
};

const mutations = {
  TOGGLE_SIDEBAR: state => {
    state.sidebar.opened = !state.sidebar.opened;
    state.sidebar.withoutAnimation = false;
    if (state.sidebar.opened) {
      Cookies.set('sidebarStatus', 1);
    } else {
      Cookies.set('sidebarStatus', 0);
    }
  },
  TOGGLE_SIDEBAR_LIKE: state => {
    state.sidebar.liked = !state.sidebar.liked;
    state.sidebar.withoutAnimation = false;
    if (state.sidebar.liked) {
      Cookies.set('sidebarLiked', 1);
    } else {
      Cookies.set('sidebarLiked', 0);
    }
  },
  OPEN_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('sidebarStatus', 1);
    state.sidebar.opened = true;
    state.sidebar.withoutAnimation = withoutAnimation;
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('sidebarStatus', 0);
    state.sidebar.opened = false;
    state.sidebar.withoutAnimation = withoutAnimation;
  },
  CLOSE_SIDEBAR_LIKE: (state, withoutAnimation) => {
    Cookies.set('sidebarLiked', 0);
    state.sidebar.liked = false;
    state.sidebar.withoutAnimation = withoutAnimation;
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device;
  },
  SET_SIZE: (state, size) => {
    state.size = size;
    Cookies.set('size', size);
  },
  SET_LOADING_COUNT: (state, loadingCount) => {
    state.loadingCount = loadingCount;
    Cookies.set('loadingCount', loadingCount);
  },
  SET_SIDEBAR_LIKED_DATA: (state, list) => {
    state.sidebar.likedData = list;
  },
  ADD_SIDEBAR_LIKED_DATA: (state, route) => {
    route.meta.icon = ''; // 'el-icon-star-on';

    state.sidebar.likedData.push({
      ...route,
      children: [],
    });
    const LEN = state.sidebar.likedData.length;
    const MAX = state.sidebar.likedMax;
    if (LEN > MAX) {
      state.sidebar.likedData = state.sidebar.likedData.slice(LEN - MAX, LEN);
    }
  },
  DELETE_SIDEBAR_LIKED_DATA: (state, route) => {
    const TEMP = [].concat(state.sidebar.likedData);
    TEMP.remove(route.name, 'name');
    state.sidebar.likedData = [].concat(TEMP);
  },
};

const actions = {
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR');
  },
  toggleSideBarLike({ commit }) {
    commit('TOGGLE_SIDEBAR_LIKE');
  },
  openSideBar({ commit }, { withoutAnimation }) {
    commit('OPEN_SIDEBAR', withoutAnimation);
  },
  closeSideBar({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation);
  },
  closeSideBarLike({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR_LIKE', withoutAnimation);
  },

  toggleDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device);
  },
  setSize({ commit }, size) {
    commit('SET_SIZE', size);
  },
  addLoadingCount({ commit, state }, isAdd = true) {
    const ov = state.loadingCount;
    let nv;
    if (isAdd) {
      nv = ov + 1;
    } else {
      nv = ov ? ov - 1 : 0;
    }
    commit('SET_LOADING_COUNT', nv);
  },
  // 当isCache==true时，强制从接口获取数据
  getLikeData({ commit, state }, isCache) {
    if (state.sidebar.likedData.length && !isCache) {
      return state.sidebar.likedData;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getTabulation({
          code: LIKE_CODE,
        })
        .then(res => {
          console.log('saveConfig', res);
          const remoteVersion = res.flag || 1;
          let tempList = [];

          if (Array.isArray(res)) {
            tempList = res;
          }
          if (Array.isArray(res.titleList)) {
            tempList = res.titleList;
          }

          if (tempList && tempList.length && remoteVersion == LIKE_CODE_VERSION) {
            console.log('get really');
            tempList = tempList.map((e, i) => {
              return {
                children: [],
                component: null,
                hidden: false,
                meta: {
                  title: e.label,
                  icon: e.fixed,
                },
                name: e.prop,
                path: e.prop,
                redirect: e.width,
                sort: e.sort,
              };
            });
          } else {
            tempList = [];
          }
          // 只取后几个
          if (tempList.length > 8) {
            tempList = tempList.slice(tempList.length - state.sidebar.likedMax, tempList.length);
          }

          commit('SET_SIDEBAR_LIKED_DATA', tempList);
          resolve(tempList);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  saveLikeData({ commit, state }, list) {
    commonApi
      .saveTabulation({
        code: LIKE_CODE,
        flag: LIKE_CODE_VERSION,
        tabulationTitleDTOS: state.sidebar.likedData.map((e, i) => {
          return {
            disabled: false,
            fixed: e.meta && e.meta.icon,
            isCheck: false,
            label: e.meta && e.meta.title,
            prop: e.name,
            sort: i,
            width: e.redirect,
          };
        }),
      })
      .then(res => {
        console.log('saveConfig', res);
      });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
