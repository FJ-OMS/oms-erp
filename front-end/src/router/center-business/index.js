/*
 * @Author: your name
 * @Date: 2021-07-13 14:49:11
 * @LastEditTime: 2022-06-10 09:49:54
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\router\center-business\index.js
 */
import user from './user'; // 用户管理
import channel from './channel'; // 渠道管理
import goods from './goods'; // 商品管理
import stock from './stock'; // 库存管理
import order from './order'; // 订单管理
import after from './after'; // 售后管理
import label from './label'; // 标签管理

/* ******************************  暂时不显示  *******************************/
// import log from './log'; // 日志管理
// import worksheet from './worksheet'; // 工单管理
// import strategy from './strategy';
// 修改以下数组，需同时修改 src\layout\components\Sidebar\HoverItem.vue 99:TODO
const BUSINESS_CENTER_ROUTER = [
  user,
  channel,
  goods,
  stock,
  order,
  after,
  label,
  // log,

  // worksheet,
  // strategy,
];
// 按可见子菜单数量 从小到大 排序一下……
// BUSINESS_CENTER_ROUTER.sort(function(a, b) {
//   return (
//     a.children.filter(e => e.hidden != true).length -
//     b.children.filter(e => e.hidden != true).length
//   );
// });
export default BUSINESS_CENTER_ROUTER;
