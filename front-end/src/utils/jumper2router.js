import Vue from 'vue';
import router from '@/router';
import { ORDER_BUSINESS_TYPES } from '@/utils/constants.js';

Vue.prototype.$toPage = (name, query) => {
  router.push({
    name: name,
    query: query,
  });
};
Vue.prototype.$toView = (name, id) => {
  router.push({
    name: name,
    query: { id: id },
  });
};
// 三方销售单
Vue.prototype.$toBusinessOrderThirdView = (code, useIdFlag = false, businessType = 'common') => {
  let id = null;
  let name = 'BusinessOrderThirdView';
  if (useIdFlag) {
    id = code;
    code = null;
  }

  if (ORDER_BUSINESS_TYPES.O2O == businessType) {
    name = 'BusinessO2oThirdView';
  }
  router.push({
    name,
    query: { id, code },
  });
};
// 内部销售单
Vue.prototype.$toBusinessOrderInternalView = (code, useIdFlag = false, businessType = 'common') => {
  let id = null;
  let name = 'BusinessOrderInternalView';
  if (useIdFlag) {
    id = code;
    code = null;
  }
  if (ORDER_BUSINESS_TYPES.O2O == businessType) {
    name = 'BusinessO2oInternalView';
  }
  router.push({
    name,
    query: { id, code },
  });
};
// 三方仅退款
Vue.prototype.$toBusinessAfterThirdRefundView = (code, useIdFlag = false) => {
  let id = null;
  if (useIdFlag) {
    id = code;
    code = null;
  }
  router.push({
    name: 'BusinessAfterThirdRefundView',
    query: { id, code },
  });
};
// 三方退货退款
Vue.prototype.$toBusinessAfterThirdReturnView = (code, useIdFlag = false) => {
  let id = null;
  if (useIdFlag) {
    id = code;
    code = null;
  }
  router.push({
    name: 'BusinessAfterThirdReturnView',
    query: { id, code },
  });
};
// 内部仅退款
Vue.prototype.$toBusinessAfterInternalRefundView = (code, useIdFlag = false) => {
  let id = null;
  if (useIdFlag) {
    id = code;
    code = null;
  }
  router.push({
    name: 'BusinessAfterInternalRefundView',
    query: { id, code },
  });
};
// 内部退货退款
Vue.prototype.$toBusinessAfterInternalReturnView = (code, useIdFlag = false) => {
  let id = null;
  if (useIdFlag) {
    id = code;
    code = null;
  }
  router.push({
    name: 'BusinessAfterInternalReturnView',
    query: { id, code },
  });
};
// 三方换货单
Vue.prototype.$toBusinessAfterThirdExchangeView = id => {
  router.push({
    name: 'BusinessAfterThirdExchangeView',
    query: { id: id },
  });
};
// 内部换货单
Vue.prototype.$toBusinessAfterInternalExchangeView = id => {
  router.push({
    name: 'BusinessAfterInternalExchangeIndex',
    query: { id: id },
  });
};
/** 出库通知单详情 */
Vue.prototype.$toBusinessStockDeliveryInformView = id => {
  router.push({
    name: 'BusinessStockDeliveryInformView',
    query: { id: id },
  });
};
/** 出库结果单详情 */
Vue.prototype.$toBusinessStockDeliveryResultView = id => {
  router.push({
    name: 'BusinessStockDeliveryResultView',
    query: { id: id },
  });
};

/** 入库通知单详情 */
Vue.prototype.$toBusinessStockEntryInformView = (code, useIdFlag = false) => {
  let id = null;
  if (useIdFlag) {
    id = code;
    code = null;
  }
  router.push({
    name: 'BusinessStockEntryInformView',
    query: { id, code },
  });
};
/** 入库结果单详情 */
Vue.prototype.$toBusinessStockEntryResultView = (code, useIdFlag = false) => {
  let id = null;
  if (useIdFlag) {
    id = code;
    code = null;
  }
  router.push({
    name: 'BusinessStockEntryResultView',
    query: { id, code },
  });
};
