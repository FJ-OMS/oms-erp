import Vue from 'vue';
import OrderLog from './log.vue'; // 引入刚才写的弹框组件
const OrderLogConstructor = Vue.extend(OrderLog); // 返回一个“扩展实例构造器”

const OrderLogModal = orderNo => {
  const orderLogDom = new OrderLogConstructor({
    el: document.createElement('div') // 将Alert组件挂载到新创建的div上
  });
  document.body.appendChild(orderLogDom.$el); // 把Alert组件的dom添加到body里

  // 标题
  orderLogDom.orderNo = orderNo;
  orderLogDom.dialogTableVisible = true;
  orderLogDom.init();
};
// // 订单统一调用 $orderlog(内部订单号)
export default OrderLogModal;
