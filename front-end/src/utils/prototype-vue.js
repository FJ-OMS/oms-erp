import Vue from 'vue';
import '@/utils/jumper2router.js';
//  全局组件之间通信
Vue.prototype.$eventHub = Vue.prototype.$eventHub || new Vue(); // 巴士

Vue.prototype.$rowStyle = {
  DEFAULT: { fontSize: '13px' }, // background: '#eef1f6', color: '#606266',
  LIGHT_GREY: { background: '#f7f7f7', color: '#606266' },
  LIGHT_BLUE: { background: '#c4d9f5', color: '#606266' },
  WHITE: { background: '#FFFFFF', color: '#6c6c6c' },
};

// 数字转2位小数金额
import {
  number2money,
  getFileTypeByUrl,
  disabledPickerDate,
  pickerOptions,
  pickerOption,
  infinityNumber,
  scrollToFormError,
  treeRowStyleFun,
  repeatArrayFun,
} from '@/utils/index.js';
Vue.prototype.$number2money = number2money;
Vue.prototype.$getFileTypeByUrl = getFileTypeByUrl;
Vue.prototype.$disabledPickerDate = disabledPickerDate;
Vue.prototype.$pickerOptions = pickerOptions;
Vue.prototype.$pickerOption = pickerOption;
Vue.prototype.$infinityNumber = infinityNumber;
Vue.prototype.$scrollToFormError = scrollToFormError;
Vue.prototype.$treeRowStyleFun = treeRowStyleFun;
Vue.prototype.$repeatArrayFun = repeatArrayFun;

import * as BjDialogListConfig from '@/utils/BjDialogListConfig.js';
Vue.prototype.$BjDialogListConfig = BjDialogListConfig;

Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: 'success' });
};
Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: 'error' });
};
Vue.prototype.msgInfo = function (msg) {
  this.message.info(msg);
};
Vue.prototype.$onCopy = () => {
  Vue.prototype.$message.success('复制成功');
};
Vue.prototype.$onError = () => {
  Vue.prototype.$message.error('复制失败');
};

import { MAX_LENGTH } from '@/utils/constants';
Vue.prototype.MAX_LENGTH = MAX_LENGTH;

import lodash from 'lodash';
Vue.prototype.$lodash = lodash;

// https://github.com/wangduanduan/jsplumb-chinese-tutorial/tree/master/docs
// https://www.cnblogs.com/leomYili/p/6346526.html

// import jsPlumb from 'jsplumb';
// Vue.prototype.$jsPlumb = jsPlumb.jsPlumb;

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
// if (process.env.NODE_ENV === 'production') {
//   const { mockXHR } = require('../mock');
//   mockXHR();
// }
const Mock = require('mockjs');
Vue.prototype.$getMockData = list => {
  const res = Mock.mock({
    'list|6-10': list,
  }).list;
  console.log(JSON.stringify(res));
  return res;
};
