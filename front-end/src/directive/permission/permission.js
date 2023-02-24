import store from '@/store';

function checkPermission(el, binding) {
  const permissionCode = binding.arg
    ? binding.arg.toLocaleLowerCase()
    : binding.value.toLocaleLowerCase();

  if (!permissionCode) {
    throw new Error(`need permission code ! Like v-permission="code"`);
  }
  const userPermissionBtns = store.getters && store.getters.userPermissionBtns;
  const hasPermission = userPermissionBtns.includes(permissionCode);
  if (!hasPermission && el) {
    (el.parentNode && el.parentNode.removeChild(el)) || (el.style.display = 'none');
  }
}

export default {
  inserted(el, binding) {
    checkPermission(el, binding);
  },
  update(el, binding) {
    checkPermission(el, binding);
  },
};
