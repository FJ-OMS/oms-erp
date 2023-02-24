import permission from './permission';
import hasRole from './hasRole';
import draggable from './draggable';
import hasDropdownChildren from './hasDropdownChildren';

const install = function (Vue) {
  Vue.directive('permission', permission);
  Vue.directive('hasRole', hasRole);
  Vue.directive('draggable', draggable);
  Vue.directive('hasDropdownChildren', hasDropdownChildren);
};

if (window.Vue) {
  window['permission'] = permission;
  Vue.use(install); // eslint-disable-line
}

permission.install = install;
export default permission;
