/*
 * @Author: your name
 * @Date: 2021-08-12 09:28:35
 * @LastEditTime: 2022-04-27 15:08:49
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\packages\index.js
 */
import BjImportFile from './BjImportFile';
import BjDialogList from './BjDialogList';
import BjDivider from './BjDivider';
import BjDrawerForm from './BjDrawerForm';
import BjSelectLov from './BjSelectLov';
import BjCheckboxLov from './BjCheckboxLov';
import BjRadioLov from './BjRadioLov';
import BjRouteTabs from './BjRouteTabs';
import BjInputMoney from './BjInputMoney';
import ScrollLoad from './ScrollLoad';
import ScrollSelect from './ScrollSelect';
import BjPagingTable from './BjPagingTable';
import BjSelectProvinces from './BjSelectProvinces';
import BjInputNumber from './BjInputNumber';
import BjSelectChinaArea from './BjSelectChinaArea';
import BjUpload from './BjUpload';
import BjDialogTable from './BjDialogTable';
import BjDialog from './BjDialog';
import BjDateTimerPicker from './BjDateTimerPicker';
import BjCopyIcon from './BjCopyIcon';
import BjTextarea from './BjTextarea';

// 编码组件
import BjInputCode from './BjInputCode';
import BjTag from './BjTag';
import BjTagList from './BjTagList';

const components = [
  BjImportFile,
  BjDialogList,
  BjDivider,
  BjDrawerForm,
  BjSelectLov,
  BjCheckboxLov,
  BjRadioLov,
  BjRouteTabs,
  BjInputMoney,
  ScrollSelect,
  BjPagingTable,
  BjSelectProvinces,
  BjInputNumber,
  BjSelectChinaArea,
  BjUpload,
  BjDialogTable,
  BjDialog,
  BjDateTimerPicker,
  BjInputCode,
  BjTag,
  BjTagList,
  BjCopyIcon,
  BjTextarea,
];
const version = '1.4.0';
const install = function(Vue, opts = {}) {
  components.forEach(component => {
    if (component.name) {
      Vue.component(component.name, component);
    } else {
      try {
        console.log(`组件 ${component.__file.split('/')[1]} 未命名，注册失败`, component);
      } catch (error) {
        console.log('组件未命名，注册失败', component, error);
      }
    }
  });
  console.log('%c npm i bj-ui@' + version, 'background-color:#f88;color:#fff');
  // Vue.prototype.$loading = Loading.service;
};

/* istanbul ignore if */
if (typeof window !== 'undefined' && window.Vue) {
  install(window.Vue);
}

export default {
  version: version,
  install,
  BjImportFile,
  BjDialogList,
  BjDivider,
  BjDrawerForm,
  BjSelectLov,
  BjRouteTabs,
  BjInputMoney,
  ScrollLoad,
  ScrollSelect,
  BjPagingTable,
  BjSelectProvinces,
  BjInputNumber,
  BjSelectChinaArea,
  BjUpload,
  BjDialogTable,
  BjDialog,
  BjInputCode,
  BjCopyIcon,
  BjTextarea,
};
