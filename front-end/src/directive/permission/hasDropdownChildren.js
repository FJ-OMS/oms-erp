export default {
  inserted(el, binding) {
    if (el.children[1].children.length === 0) {
      el.remove();
    }
  },
};
