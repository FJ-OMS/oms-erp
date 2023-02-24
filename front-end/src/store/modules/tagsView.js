import router from '@/router';
const state = {
  visitedViews: [],
  cachedViews: [],
  visitedViewsMax: 12,
};
const checkMulTag = view => {
  return view.meta.mulTag === true ? 'fullPath' : 'path';
};
const mutations = {
  REPLACE_VISITED_VIEW: (state, view) => {
    const PATH_KEY = checkMulTag(view);
    const findViewIndex = state.visitedViews.findIndex(v => v[PATH_KEY] === view[PATH_KEY]);
    if (findViewIndex > -1) {
      state.visitedViews.splice(
        findViewIndex,
        1,
        Object.assign({}, view, {
          title: view.otherTitle ? view.otherTitle : view.meta.title || 'no-name',
        }),
      );
    }
  },
  INIT_VISITED_VIEW: (state, view) => {
    state.visiteViews = view;
  },
  ADD_VISITED_VIEW: (state, view) => {
    const PATH_KEY = checkMulTag(view);
    if (state.visitedViews.some(v => v[PATH_KEY] === view[PATH_KEY])) return;
    state.visitedViews.push(
      Object.assign({}, view, {
        title: view.meta.title || 'no-name',
      }),
    );
    if (state.visitedViews.length > state.visitedViewsMax) {
      state.visitedViews.splice(0, 1);
    }
  },
  ADD_CACHED_VIEW: (state, view) => {
    const PATH_KEY = checkMulTag(view);
    if (state.cachedViews.some(v => v[PATH_KEY] === view[PATH_KEY])) return;
    if (view.meta.isCache === true) {
      state.cachedViews.push(
        Object.assign({}, view, {
          title: view.meta.title || 'no-name',
        }),
      );
      // console.log('添加缓存页', state.cachedViews, PATH_KEY + ':' + view[PATH_KEY]);
    }
  },

  DEL_VISITED_VIEW: (state, view) => {
    const PATH_KEY = checkMulTag(view);
    const OLD_INDEX = state.visitedViews.findIndex(v => v[PATH_KEY] === view[PATH_KEY]);
    state.visitedViews.splice(OLD_INDEX, 1);
  },
  DEL_CACHED_VIEW: (state, view) => {
    const PATH_KEY = checkMulTag(view);
    const index = state.cachedViews.findIndex(v => v[PATH_KEY] === view[PATH_KEY]);
    if (index > -1) {
      state.cachedViews.splice(index, 1);
    } else {
      console.warn('未缓存', state.cachedViews, PATH_KEY + ':' + view[PATH_KEY]);
    }
  },

  DEL_OTHERS_VISITED_VIEWS: (state, view) => {
    const PATH_KEY = checkMulTag(view);
    state.visitedViews = state.visitedViews.filter(v => {
      return v.meta.affix || v[PATH_KEY] === view[PATH_KEY];
    });
  },
  DEL_OTHERS_CACHED_VIEWS: (state, view) => {
    const PATH_KEY = checkMulTag(view);
    const index = state.cachedViews.findIndex(v => v[PATH_KEY] === view[PATH_KEY]);
    if (index > -1) {
      state.cachedViews = state.cachedViews.slice(index, index + 1);
    } else {
      // if index = -1, there is no cached tags
      state.cachedViews = [];
    }
  },

  DEL_ALL_VISITED_VIEWS: state => {
    // keep affix tags
    const affixTags = state.visitedViews.filter(tag => tag.meta.affix);
    state.visitedViews = affixTags;
  },
  DEL_ALL_CACHED_VIEWS: state => {
    state.cachedViews = [];
  },

  UPDATE_VISITED_VIEW: (state, view) => {
    for (let v of state.visitedViews) {
      const PATH_KEY = checkMulTag(v);
      if (v[PATH_KEY] === view[PATH_KEY]) {
        v = Object.assign(v, view);
        break;
      }
    }
  },
  SWAP_VISITED_VIEW: (state, index) => {
    const temp = state.visitedViews.splice(-1)[0];
    state.visitedViews.splice(index, 0, temp);
  },
};

const actions = {
  replaceVisitedView({ commit }, view) {
    commit('REPLACE_VISITED_VIEW', view);
  },
  // 关闭当前页签：--------start----------
  closeCurrentView({ dispatch }, view) {
    const PATH_KEY = checkMulTag(view);
    const curView = state.visitedViews.find(v => {
      return v[PATH_KEY] === view[PATH_KEY];
    });
    dispatch('delView', curView).then(() => {
      if (curView[PATH_KEY] === view[PATH_KEY]) {
        dispatch('toLastView', curView);
      }
    });
  },
  // 关闭页签后，返回上一个页面
  toLastView(view) {
    const latestView = state.visitedViews.slice(-1)[0];
    if (latestView) {
      router.push(latestView.fullPath);
    } else {
      // now the default is to redirect to the home page if there is no tags-view,
      // you can adjust it according to your needs.
      if (view.name === 'Dashboard') {
        // to reload home page
        router.replace({ path: '/redirect' + view.fullPath });
      } else {
        router.push('/');
      }
    }
  },
  // 关闭当前页签：--------end----------

  initView({ commit }, view) {
    commit('INIT_VISITED_VIEW', view);
  },
  addView({ dispatch, state }, view) {
    dispatch('addVisitedView', view);
    dispatch('addCachedView', view);
  },
  addVisitedView({ commit }, view) {
    if (view.name === 'login') {
      return;
    }
    commit('ADD_VISITED_VIEW', view);
  },
  addCachedView({ commit }, view) {
    if (view.name === 'login') {
      return;
    }
    commit('ADD_CACHED_VIEW', view);
  },

  delView({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delVisitedView', view);

      const VIEWS = state.visitedViews.filter(tag => {
        return tag.name == view.name;
      });
      if (VIEWS.length == 0) {
        dispatch('delCachedView', view);
      }

      resolve({
        visitedViews: [...state.visitedViews],
        cachedViews: [...state.cachedViews],
      });
    });
  },
  delVisitedView({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_VISITED_VIEW', view);
      resolve([...state.visitedViews]);
    });
  },
  delCachedView({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_CACHED_VIEW', view);
      resolve([...state.cachedViews]);
    });
  },

  delOthersViews({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delOthersVisitedViews', view);
      dispatch('delOthersCachedViews', view);
      resolve({
        visitedViews: [...state.visitedViews],
        cachedViews: [...state.cachedViews],
      });
    });
  },
  delOthersVisitedViews({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_OTHERS_VISITED_VIEWS', view);
      resolve([...state.visitedViews]);
    });
  },
  delOthersCachedViews({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_OTHERS_CACHED_VIEWS', view);
      resolve([...state.cachedViews]);
    });
  },

  delAllViews({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delAllVisitedViews', view);
      dispatch('delAllCachedViews', view);
      resolve({
        visitedViews: [...state.visitedViews],
        cachedViews: [...state.cachedViews],
      });
    });
  },
  delAllVisitedViews({ commit, state }) {
    return new Promise(resolve => {
      commit('DEL_ALL_VISITED_VIEWS');
      resolve([...state.visitedViews]);
    });
  },
  delAllCachedViews({ commit, state }) {
    return new Promise(resolve => {
      commit('DEL_ALL_CACHED_VIEWS');
      resolve([...state.cachedViews]);
    });
  },

  updateVisitedView({ commit }, view) {
    commit('UPDATE_VISITED_VIEW', view);
  },
  swapVisitedView({ commit, state }, index) {
    return new Promise(resolve => {
      commit('SWAP_VISITED_VIEW', index);
      resolve([...state.visitedViews]);
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
