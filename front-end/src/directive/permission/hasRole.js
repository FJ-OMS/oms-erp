/**
 * 角色权限处理
 */
import store from '@/store';
export default {
   inserted(el, binding, vnode) {
      const { value } = binding;
      // console.log(value)
      const super_admin = 'admin';
      const roles = store.getters && store.getters.roles;
      // console.log(roles)
      if (value && value.length > 0 && value instanceof Array) {
        const hasRole = roles.some(role => {
          return super_admin === role || value.includes(role);
        });
        // console.log(hasRole)
        if (!hasRole) {
          el.parentNode && el.parentNode.removeChild(el);
        }
      } else {
        throw new Error(`请设置角色权限标签值`);
      }
    }
};
