/*
 * @Author: your name
 * @Date: 2021-08-12 09:24:24
 * @LastEditTime: 2021-11-05 14:34:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\index.js
 */
import BjForm from './BjForm';
import BjTable from './BjTable/BjTable';
import Pagination from './Pagination';
import BjTooltip from './BjTooltip';
import RightToolbar from './RightToolbar';
import BjPage from './BjPage/index';
import BjGoodsTypeTreeSelect from './BjGoodsTypeTreeSelect/index';
import BjCompanyTreeSelect from './BjCompanyTreeSelect/index';
import BjSelectBrand from './BjSelectBrand/index';
import BjSelectWarehouse from './BjSelectWarehouse/index';
import BjSelectMaterial from './BjSelectMaterial/index';
import BjSelectAttribute from './BjSelectAttribute/index';
import BjSelectGoods from './BjSelectGoods/index';
import BjSelectTenant from './BjSelectTenant/index';
import BjKeyImage from './BjKeyImage/index';
import BjKeyVideo from './BjKeyVideo/index';
import BjViewUrl from './BjViewUrl/index';
import BjLogTable from './BjLogTable';
import BjSelectStore from './BjSelectStore';
import BjSelectLogisticCompany from './BjSelectLogisticCompany';
import BjSelectLogisticBusiness from './BjSelectLogisticBusiness';
import BjFormItemAutoCode from './BjFormItemAutoCode';
import BjSelectStoreOnline from './BjSelectStoreOnline';
import BjFormItemMobile from './BjFormItemMobile';
import BjFormItemAge from './BjFormItemAge';
import BjFormItemCode from './BjFormItemCode';
import BjFormItemEmail from './BjFormItemEmail';
import BjFormItemPhone from './BjFormItemPhone';
import BjFormItemPostCode from './BjFormItemPostCode';
import BjSelectStorage from './BjSelectStorage';
import BjSelectChannelStore from './BjSelectChannelStore';
import BjPreviewList from './BjPreviewList';
import BjStatusTabs from './BjStatusTabs';
import BjSelectStorePhysical from './BjSelectStorePhysical';
import BjSelectUser from './BjSelectUser';

const components = [
  BjForm,
  BjTable,
  Pagination,
  BjTooltip,
  RightToolbar,
  BjPage,
  BjGoodsTypeTreeSelect,
  BjCompanyTreeSelect,
  BjSelectBrand,
  BjSelectWarehouse,
  BjSelectMaterial,
  BjSelectMaterial,
  BjSelectGoods,
  BjSelectTenant,
  BjKeyImage,
  BjKeyVideo,
  BjViewUrl,
  BjSelectAttribute,
  BjLogTable,
  BjSelectStore,
  BjSelectLogisticCompany,
  BjSelectLogisticBusiness,
  BjFormItemAutoCode,
  BjSelectStoreOnline,
  BjFormItemMobile,
  BjFormItemAge,
  BjFormItemCode,
  BjFormItemEmail,
  BjFormItemPhone,
  BjFormItemPostCode,
  BjSelectStorage,
  BjSelectChannelStore,
  BjPreviewList,
  BjStatusTabs,
  BjSelectStorePhysical,
  BjSelectUser,
];

const install = function (Vue, opts = {}) {
  components.forEach(component => {
    if (component.name) {
      Vue.component(component.name, component);
    } else {
      try {
        console.warn(`组件 ${component.__file.split('/')[2]} 未命名，注册失败`, component);
      } catch (error) {
        console.warn('组件未命名，注册失败', component, error);
      }
    }
  });
};
if (typeof window !== 'undefined' && window.Vue) {
  install(window.Vue);
}
export default {
  install,
};
