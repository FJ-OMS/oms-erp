import ElementTableSortable from './src/index';
import Vue from 'vue';

const createComponent = (component, props) => {
  const ConCtor = Vue.extend(component);

  const EL = new ConCtor({ propsData: props }).$mount();

  document.body.appendChild(EL.$el);

  ConCtor.remove = () => {
    document.body.removeChild(EL.$el);
    ConCtor.$destroy();
  };

  return EL;
};
const version = '1.1.1';
export default {
  version: version,
  install(Vue) {
    Vue.prototype.$initElementTableSortable = props => createComponent(ElementTableSortable, props);
    console.log('%c npm i element-table-sortable@' + version, 'background-color:#f88;color:#fff');
  },

  /**
   * import Notice from '../packages/notice'
   * Notice.fail({content: '服务器错误'})
   */

  // fail(props) {
  //   props = Object.assign({ defaultList: [] }, props);

  //   return createComponent(ElementTableSortable, props);
  // }
};
