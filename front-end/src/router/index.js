import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

/* Layout */
import Layout from '@/layout';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
import EmptyRouter from '@/layout/EmptyRouter.vue';
// 新的
import settingCenter from './center-setting/index';
import businessCenter from './center-business/index';

/**
 * 注意:子菜单只在路由子时出现。 长度> = 1
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   如果设置为true，项目将不会显示在侧边栏中(默认为false)
 * alwaysShow: true               如果设置为true，将始终显示根菜单
 *                                如果没有设置alwaysShow，当item有多个子路由时，
 *                                它将变成嵌套模式，否则不显示根菜单
 * redirect: noRedirect           如果设置noRedirect，则不会在面包屑中重定向
 * name:'router-name'             <keep-alive>(必须设置!!)
 * meta : {
    roles: ['admin','editor']    控制页面角色(可以设置多个角色)
    title: 'title'               名称显示在侧边栏和面包屑中(推荐集)
    icon: 'svg-name'/'el-icon-x' 图标显示在侧栏中
    isCache: false               如果设置为true，页面将会被缓存(默认为false)  有效
    affix: true                  如果设置为true，标记将附着在标记视图中
    breadcrumb: false            如果设置为false，该项将隐藏在breadcrumb中(默认为true)
    activeMenu: '/example/list'  如果设置路径，侧边栏将突出显示您设置的路径
    isShowChildrenByHover: false 如果设置为true, 鼠标在一级菜单上时，展示所有的二、三级菜单(默认false: 正常下拉展示 二、三级菜单）
    mulTag:false                 如果设置为true，不同query的链接将打开一个新的tag页面
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    name: 'Redirect',
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/common/redirect/index'),
      },
    ],
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/common/login/auth-redirect'),
    hidden: true,
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/common/login/auth-redirect'),
    hidden: true,
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/common/error-page/404'),
    hidden: true,
  },
  {
    path: '/401',
    name: '401',
    component: () => import('@/views/common/error-page/401'),
    hidden: true,
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard1',
    meta: {
      // 展示在侧边栏
      title: '',
      icon: 'dashboard',
      breadcrumb: false,
    },
    hidden: true,
    children: [
      {
        path: '占位，第二级路由',
        name: 'Dashboard2',
        component: EmptyRouterAlive,
        redirect: '/dashboard',
        meta: {
          // 占位，凑四级路由，不展示
          title: '首页2',
          breadcrumb: false,
        },
        hidden: true,
        children: [
          {
            path: '占位，第三级路由',
            name: 'Dashboard3',
            component: EmptyRouter,
            redirect: '/dashboard',
            meta: {
              // 占位，凑四级路由，不展示
              title: '首页2',
              breadcrumb: false,
            },
            hidden: true,
            children: [
              {
                path: '/dashboard',
                component: () => import('@/views/common/dashboard/index'),
                name: 'Dashboard',
                meta: {
                  // 展示在 面包屑、页签title
                  title: '',
                  icon: 'dashboard',
                  affix: false,
                },
              },
            ],
          },
        ],
      },
    ],
  },

  {
    path: '/common',
    component: Layout,
    redirect: 'index',
    name: 'Common',
    hidden: true,
    children: [
      {
        path: 'xx',
        name: 'xxxxxx',
        component: EmptyRouterAlive,
        meta: {
          // 占位，凑四级路由，不展示
          title: '通用模块',
          breadcrumb: false,
        },
        hidden: true,
        children: [
          {
            path: 'xx',
            name: 'xxxxxx',
            component: EmptyRouter,
            meta: {
              // 占位，凑四级路由，不展示
              title: '通用模块',
              breadcrumb: false,
            },
            hidden: true,
            children: [
              {
                path: '/common/personal',
                component: () => import('@/views/common/personal'),
                name: 'CommonPersonal',
                meta: {
                  // 展示在 面包屑、页签title
                  title: '个人中心',
                },
              },
              {
                path: '/common/menu',
                component: () => import('@/views/center-setting/menu'),
                name: 'CommonMenu',
                meta: {
                  title: '菜单配置',
                },
              },
            ],
          },
        ],
      },
    ],
  },
];

export const asyncRoutes = [
  {
    path: '/setting',
    component: Layout,
    // redirect: '/personal/index',
    name: 'Setting',
    meta: {
      title: '配置中心',
      icon: 'el-icon-s-operation',
      isShowChildrenByHover: true,
    },
    children: [...settingCenter],
  },
  {
    path: '/business',
    component: Layout,
    // redirect: '/personal/index',
    name: 'Business',
    meta: {
      title: '业务中心',
      icon: 'el-icon-notebook-1',
      isShowChildrenByHover: true,
    },
    children: [...businessCenter],
  },
  // {
  //   path: '/test',
  //   component: Layout,
  //   redirect: '/test/index',
  //   name: 'Test',
  //   meta: {
  //     title: 'DEMO',
  //     breadcrumb: false,
  //     icon: 'bug',
  //   },
  //   children: [
  //     {
  //       path: 'xx20210825',
  //       name: 'XX20210825',
  //       component: EmptyRouter,
  //       meta: {
  //         // 占位，凑三级路由，不展示
  //         breadcrumb: false,
  //       },
  //       hidden: true,
  //       children: [
  //         {
  //           path: '/test/index',
  //           component: () => import('@/views/test/index'),
  //           name: 'TestIndex',
  //           meta: { title: '测试', isCache: true },
  //           hidden: true,
  //         },
  //       ],
  //     },
  //   ],
  // },
  { path: '*', redirect: '/404', hidden: true },
];

console.log('asyncRoutes', asyncRoutes);
console.log(
  '权限表 permission.json 用于初始化菜单权限数据',
  [asyncRoutes[0], asyncRoutes[1]].map((e1, n1) => {
    return {
      permissionType: e1.permissionType || 'api',
      title: e1.meta.title,
      icon: e1.meta.icon || null,
      code: e1.name,
      hiddenFlag: false,
      sort: n1,
      children: !e1.children
        ? []
        : e1.children.map((e2, n2) => {
            if (!e2.children || e2.children.length == 0) {
              console.log('只有二级：' + e1.meta.title + '-' + e2.meta.title);
            }
            return {
              permissionType: e2.permissionType || 'api',
              title: e2.meta.title,
              icon: e2.meta.icon || null,
              code: e2.name,
              hiddenFlag: false,
              sort: n2,
              children: !e2.children
                ? []
                : e2.children.map((e3, n3) => {
                    if (!e3.children || e3.children.length == 0) {
                      console.log(
                        '只有三级：' + e1.meta.title + '-' + e2.meta.title + '-' + e3.meta.title,
                      );
                    }
                    return {
                      permissionType: e3.permissionType || 'api',
                      title: e3.meta.title,
                      icon: e3.meta.icon || null,
                      code: e3.name,
                      hiddenFlag: false,
                      sort: n3,
                      children: !e3.children
                        ? []
                        : e3.children.map((e4, n4) => {
                            // 有下划线的 默认为按钮，没有下划线的为页面，如果是tab页，则对应路由表permissionType=tab
                            const DEFAULT_TYPE = e4.name.includes('_') ? 'btn' : 'page';
                            return {
                              permissionType: e4.permissionType || DEFAULT_TYPE,
                              title: e4.meta.title,
                              icon: e4.meta.icon || null,
                              code: e4.name,
                              hiddenFlag: true,
                              sort: n4,
                            };
                          }),
                    };
                  }),
            };
          }),
    };
  }),
);

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({
      y: 0,
    }),
    routes: constantRoutes,
  });

const router = createRouter();
// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

export default router;
