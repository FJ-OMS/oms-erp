import { asyncRoutes, constantRoutes } from '@/router';
/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(permissions, route) {
  const code = route.name;

  const f = p => {
    let find = null;
    for (let index = 0; index < p.length; index++) {
      const e = p[index];
      if (e.code == code) {
        find = e;
        break;
      }
      find = f(e.children || []);
      if (find) break;
    }
    return find;
  };
  const obj = f(permissions);

  // 覆盖部分路由的配置
  let newRoute = route;
  if (obj) {
    newRoute = {
      ...route,
      // redirect
      meta: {
        ...route.meta,
        title: obj.title,
        icon: obj.icon,
      },
      hidden: obj.hidden,
      sort: obj.sort,
    };
  }
  return {
    flag: !!obj,
    route: newRoute,
    permission: obj,
  };
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, USER_PERMISSIONS) {
  const res = [];
  routes.forEach(route => {
    const re = hasPermission(USER_PERMISSIONS, route);
    if (re.flag) {
      const tmp = re.route;
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, USER_PERMISSIONS);
      }
      res.push(tmp);
    }
  });

  return res;
}

const state = {
  routes: [],
  addRoutes: [],
};
// 按sort从小到大排序
function sortRoutes(routes) {
  routes.sort((a, b) => {
    return a.sort - b.sort;
  });
  routes.forEach(e => {
    if (e.children && e.children.length) {
      e.children = sortRoutes(e.children);
    }
  });
  return routes;
}
//
function redirectFix(routes) {
  routes.forEach(e => {
    if (e.children && e.children.length) {
      e.children = redirectFix(e.children);
      const tabs = e.children.filter(r => r.permissionType == 'tab');
      if (tabs.length) {
        // 在父级的meta.tabs 自动构建一个数组，用于 BjRouteTabs 组件的支持
        e.meta['tabs'] = tabs.map(tab => {
          return {
            label: tab.meta && tab.meta.title,
            name: tab.name,
          };
        });
        const NEW_END_PATH = tabs[0].path; // 'sku/ordinary'

        // 当第1个tab无权访问时，自动redirect到下一个有权限的tab
        if (e.redirect) {
          const OLD_END_PATH = e.redirect.replace(e.path + '/', ''); // (e.redirect || '').split('/').slice(-1)[0]; //'ordinary'
          // const OLD_START_PATH_ARR = (e.redirect || '').split('/').slice(0, -1); //['audit', 'sku']
          if (NEW_END_PATH != OLD_END_PATH) {
            e.redirect = e.path + '/' + NEW_END_PATH; // [...OLD_START_PATH_ARR, NEW_END_PATH].join('/');
            console.log(' e.redirect', e.redirect);
          }
        }
      }
    }
  });

  return routes;
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes;
    state.routes = constantRoutes.concat(routes);
  },
};

const actions = {
  generateRoutes({ commit }, USER_PERMISSIONS) {
    return new Promise(resolve => {
      // 按权限code过滤
      let accessedRoutes = filterAsyncRoutes(asyncRoutes, USER_PERMISSIONS);
      // permissionType: 'tab',处理父级redirect
      accessedRoutes = redirectFix(accessedRoutes);
      // 按sort从小到大排序
      accessedRoutes = sortRoutes(accessedRoutes);
      // 在路由最后，加入404
      accessedRoutes.push({ path: '*', redirect: '/404', hidden: true });
      commit('SET_ROUTES', accessedRoutes);
      resolve(accessedRoutes);
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
