/*
 * @Author: your name
 * @Date: 2021-07-13 14:49:11
 * @LastEditTime: 2022-06-10 09:52:01
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\router\center-setting\index.js
 */
import user from './user'; // 用户
import goods from './goods'; // 商品
import baseData from './base-data'; // 基础数据
import indexes from './indexes'; // 索引配置
import file from './file'; // 文件管理
import instance from './instance'; // 事务管理

/* ******************************  暂时不显示  *******************************/
// import message from './message'; // 消息服务
// import after from './after'; // 售后
// import scheduler from './scheduler'; // 调度服务
// 修改以下数组，需同时修改 src\layout\components\Sidebar\HoverItem.vue 99:TODO
const SETTING_CENTER_ROUTER = [
  user,
  goods,

  // after

  baseData,
  // message,
  indexes,
  file,
  // scheduler,
  instance,
  // manage,
  // processOrder,
  // stockStructure,
  // processChecking,
  // search,
  // statement,
];
// 按可见子菜单数量 从小到大 排序一下……
// SETTING_CENTER_ROUTER.sort(function(a, b) {
//   return (
//     a.children.filter(e => e.hidden != true).length -
//     b.children.filter(e => e.hidden != true).length
//   );
// });
export default SETTING_CENTER_ROUTER;
