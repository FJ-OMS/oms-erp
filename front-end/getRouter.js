// import { asyncRoutes } from './src/router/index';
const asyncRoutes = require('./src/router/index');
console.log(asyncRoutes);

// console.log(
//   '权限表 permission.json 用于初始化菜单权限数据',
//   [asyncRoutes[0], asyncRoutes[1]].map((e1, n1) => {
//     return {
//       permissionType: e1.permissionType || 'api',
//       title: e1.meta.title,
//       icon: e1.meta.icon || null,
//       code: e1.name,
//       hiddenFlag: false,
//       sort: n1,
//       children: !e1.children
//         ? []
//         : e1.children.map((e2, n2) => {
//             if (!e2.children || e2.children.length == 0) {
//               console.log('只有二级：' + e1.meta.title + '-' + e2.meta.title);
//             }
//             return {
//               permissionType: e2.permissionType || 'api',
//               title: e2.meta.title,
//               icon: e2.meta.icon || null,
//               code: e2.name,
//               hiddenFlag: false,
//               sort: n2,
//               children: !e2.children
//                 ? []
//                 : e2.children.map((e3, n3) => {
//                     if (!e3.children || e3.children.length == 0) {
//                       console.log(
//                         '只有三级：' + e1.meta.title + '-' + e2.meta.title + '-' + e3.meta.title,
//                       );
//                     }
//                     return {
//                       permissionType: e3.permissionType || 'api',
//                       title: e3.meta.title,
//                       icon: e3.meta.icon || null,
//                       code: e3.name,
//                       hiddenFlag: false,
//                       sort: n3,
//                       children: !e3.children
//                         ? []
//                         : e3.children.map((e4, n4) => {
//                             // 有下划线的 默认为按钮，没有下划线的为页面，如果是tab页，则对应路由表permissionType=tab
//                             const DEFAULT_TYPE = e4.name.includes('_') ? 'btn' : 'page';
//                             return {
//                               permissionType: e4.permissionType || DEFAULT_TYPE,
//                               title: e4.meta.title,
//                               icon: e4.meta.icon || null,
//                               code: e4.name,
//                               hiddenFlag: true,
//                               sort: n4,
//                             };
//                           }),
//                     };
//                   }),
//             };
//           }),
//     };
//   }),
// );
const routes = [asyncRoutes[0], asyncRoutes[1]].map((e1, n1) => {
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
});

const fs = require('fs');
const content = JSON.stringify(routes, null, '\t');
// 写入文件
fs.writeFile('111.json', content, function(err) {
  if (err) {
    return console.log(err);
  }
  console.log('路由文件已经生成');
});

// export default routes;
