import Vue from 'vue';

import Cookies from 'js-cookie';

import 'normalize.css/normalize.css'; // a modern alternative to CSS resets

import Element from 'element-ui';
import './styles/element-variables.scss';
import 'element-ui/lib/theme-chalk/base.css';
// import enLang from 'element-ui/lib/locale/lang/en'// 如果使用中文语言包请默认支持，无需额外引入，请删除该依赖
// import UmyUi from 'umy-ui'
// import 'umy-ui/lib/theme-chalk/index.css' // 引入样式

// 引入vxe-table 组件 https://vxetable.cn/#/table/start/install
import XEUtils from 'xe-utils';
import {
  VXETable,
  Header,
  Column,
  Table,
  Icon,
  Keyboard,
  Tooltip,
  Colgroup,
  Menu,
} from 'vxe-table';
import zhCN from 'vxe-table/lib/locale/lang/zh-CN';
// 按需加载的方式默认是不带国际化的，自定义国际化需要自行解析占位符 '{0}'，例如：
VXETable.setup({
  i18n: (key, args) => XEUtils.toFormatString(XEUtils.get(zhCN, key), args),
  zIndex: 3001,
  icon: {
    TABLE_SORT_ASC: 'vxe-icon--caret-top',
    TABLE_SORT_DESC: 'vxe-icon--caret-bottom',
    TABLE_FILTER_NONE: 'vxe-icon--funnel',
    TABLE_FILTER_MATCH: 'vxe-icon--funnel',
    TABLE_EDIT: 'vxe-icon--edit-outline',
    TABLE_TREE_LOADED: 'vxe-icon--refresh roll',
    TABLE_TREE_OPEN: 'vxe-icon--caret-right rotate90',
    TABLE_TREE_CLOSE: 'vxe-icon--caret-right',
    TABLE_EXPAND_LOADED: 'vxe-icon--refresh roll',
    TABLE_EXPAND_OPEN: 'vxe-icon--arrow-right rotate90',
    TABLE_EXPAND_CLOSE: 'vxe-icon--arrow-right',
  },
});
// 表格功能
Vue.use(Keyboard).use(Header).use(Menu).use(Column).use(Table).use(Icon).use(Tooltip).use(Colgroup);

import '@/styles/index.scss'; // global css
import '@/styles/ruoyi.scss'; // global css
import 'vue-draggable-resizable/dist/VueDraggableResizable.css';

import App from './App';
import store from './store';
import router from './router';

import './icons'; // icon
import './permission'; // permission control
import './utils/error-log'; // error log
import '@/utils/prototype-native.js'; // window原生方法绑定
import '@/utils/prototype-vue.js'; // Vue.prototype.$全局方法绑定

import permission from './directive/permission';
Vue.use(permission);

// 注册全局实用过滤器
import * as filters from './filters';
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key]);
});

// 自定义的npm包 UI组件
import FeiJiUI from './packages';
Vue.use(FeiJiUI);
Vue.prototype.$ScrollLoad = FeiJiUI.ScrollLoad;

// 自定义的npm包 用于个人列表配置
import ElementTableSortable from './components/ElementTableSortable';
Vue.use(ElementTableSortable);

// 自定义组件 全局挂载
import componentsUI from '@/components';
Vue.use(componentsUI);

import vuescroll from 'vuescroll';
Vue.use(vuescroll);
// Vue.use(vuescroll, {
//   ops: {}, // 在这里设置全局默认配置
//   name: 'vueScroll', // 在这里自定义组件名字，默认是vueScroll
// });

import SuperFlow from 'vue-super-flow';
import 'vue-super-flow/lib/index.css';
Vue.use(SuperFlow);

import VueClipboard from 'vue-clipboard2';
Vue.use(VueClipboard);

// https://vue-scrollto.netlify.app/docs/#as-a-vue-directive
import VueScrollTo from 'vue-scrollto';
Vue.use(VueScrollTo, {
  container: 'body',
  duration: 500,
  easing: 'ease',
  offset: 0,
  force: true,
  cancelable: true,
  onStart: false,
  onDone: false,
  onCancel: false,
  x: false,
  y: true,
});

Vue.use(Element, {
  size: Cookies.get('size') || 'mini', // set element-ui default size
  // locale: enLang // 如果使用中文，无需设置，请删除
});
// Vue.use(UmyUi)

Vue.config.productionTip = false;

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
});
