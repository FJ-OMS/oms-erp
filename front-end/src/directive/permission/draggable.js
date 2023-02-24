const draggable = {
  inserted: function(e, binding) {
    // console.log(binding)
    // document.onclick = function(e) {
    //   e.path[0].focus()
    //   return
    // }
    document.ondblclick = function(e) {
      e.path[0].focus();
      return;
    };
    document.onselectstart = function() {
      return false; // 禁止选择网页文字
    };
    e.onmousedown = el => {
      el.preventDefault();
      // 计算当前可视区域距离元素的距离
      // e.clientX:鼠标指针的X坐标
      // el.offsetLeft:距离浏览器左边距离
      const disX = el.clientX - e.offsetLeft;
      const disY = el.clientY - e.offsetTop;
      document.onmousemove = function(el) {
        el.preventDefault();
        const className = e.parentNode.className;
        const listEl = document.getElementsByClassName(className)[0];
        // const listEl = document.getElementById('virtual')
        let left = el.clientX - disX;
        let top = el.clientY - disY;
        const MAX_W = listEl.clientWidth - e.clientWidth;
        const AMX_H = listEl.clientHeight - e.clientHeight;
        if (left < 0) left = 0;
        if (top < 0) top = 0;
        if (left > MAX_W) left = MAX_W;
        if (top > AMX_H) top = AMX_H;
        // console.log(MAX_W,AMX_H)
        e.style.left = left + 'px';
        e.style.top = top + 'px';
        // binding.value.set(binding.value.index, left, top)
      };
      document.onmouseup = function() {
        document.onmousemove = document.onmouseup = null;
      };
      return false;
    };
  },
};
export default draggable;
