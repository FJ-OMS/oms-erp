import router from './router';
import store from './store';
import { Message } from 'element-ui';
import NProgress from 'nprogress'; // progress bar
import 'nprogress/nprogress.css'; // progress bar style
import { getToken } from '@/utils/auth'; // get token from cookie
import getPageTitle from '@/utils/get-page-title';

NProgress.configure({ showSpinner: false }); // NProgress Configuration

const whiteList = ['/login', '/auth-redirect']; // no redirect whitelist 没有重定向白名单

router.beforeEach(async (to, from, next) => {
  // console.log('%c *************  router.beforeEach *****************', 'color:red;font-weight:700');
  // console.log(`%c fullPath: ${from.fullPath} => ${to.fullPath}`, 'font-weight:700');

  // start progress bar
  NProgress.start();

  // set page title
  document.title = getPageTitle(to.meta.title);

  if (process.env.VUE_APP_ACCESS_TOKEN) {
    await store.dispatch('user/getInfo');
    const USER_PERMISSIONS = store.getters.userPermissions;
    const accessRoutes = await store.dispatch('permission/generateRoutes', USER_PERMISSIONS);
    router.addRoutes(accessRoutes);
    next();
    return;
  }

  // determine whether the user has logged in
  const hasToken = getToken();
  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' });
      NProgress.done(); // hack: https://github.com/PanJiaChen/vue-element-admin/pull/2939
    } else {
      // determine whether the user has obtained his permission roles through get Info
      const HAS_PERMISSIONS =
        store.getters.userPermissions && store.getters.userPermissions.length > 0;
      if (HAS_PERMISSIONS) {
        next();
      } else {
        try {
          // get user info
          // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
          await store.dispatch('user/getInfo');
          // console.log('router.beforeEach: user/getInfo   => roles       =', roles);

          const USER_PERMISSIONS = store.getters.userPermissions;
          if (USER_PERMISSIONS.length == 0) {
            throw new Error('账号未配置角色，或角色的相关信息错误，请联系管理员');
          }
          // console.log('store.getters.userPermissions', JSON.stringify(USER_PERMISSIONS));
          // generate accessible routes map based on roles
          // TODO: 把菜单的权限 传入异步路由生成
          const accessRoutes = await store.dispatch('permission/generateRoutes', USER_PERMISSIONS);
          console.log('---------accessRoutes---------------------');
          console.log('router.beforeEach: generateRoutes => accessRoutes=', accessRoutes);
          // dynamically add accessible routes
          // console.log(router);
          router.addRoutes(accessRoutes);
          // hack method to ensure that addRoutes is complete
          // set the replace: true, so the navigation will not leave a history record
          next({ ...to, replace: true });
        } catch (error) {
          // remove token and go to login page to re-login
          // 删除令牌并转到登录页面重新登录
          // await store.dispatch('user/resetToken')
          // console.log(error);
          Message.error(error || 'Has Error');
          setTimeout(async () => {
            await store.dispatch('user/logout');
            next(`/login?redirect=${to.path}`);
            NProgress.done();
          }, 1000);
        }
      }
    }
  } else {
    /* has no token*/
    // console.log('===== >>>>> has no token 。     to:', to);
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      // 在免费登录白名单，直接去
      // console.log('===== >>>>> 在免费登录白名单，直接去。to.fullPath=', to.fullPath);
      next();
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      // 其他没有访问权限的页面被重定向到登录页面。
      next(`/login?redirect=${to.path}`);
      console.log(
        `===== >>>>> 其他没有访问权限的页面被重定向到登录页面。/login?redirect=${to.path}`,
      );
      NProgress.done();
    }
  }
});

router.afterEach(() => {
  // finish progress bar
  NProgress.done();
});
