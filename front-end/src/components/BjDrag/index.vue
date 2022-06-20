<template>
  <!-- <div> -->
  <div
    class="Bj-drag"
    :style="{ left: x + 'px', top: y + 'px' }"
    @mousedown.stop="onMousedown"
  >
    <el-button size="medium" :type="type">
      {{ name ? name : '未命名虚仓' }}
    </el-button>
    <!-- <span
        class="endpoint endpoint-left"
        @mousedown.stop="createOrigin"
        @mouseover="onOver"
        @mouseup="onUp"
      />
      <span class="endpoint endpoint-top" />
      <span class="endpoint endpoint-right" />
      <span class="endpoint endpoint-bottom" />
      <svg class="svg" :width="drag.width" :height="drag.height">
      <line class="line" :x1="drag.fromX" :y1="drag.fromY" :x2="drag.toX" :y2="drag.toY" style="stroke:#fa9668; stroke-width:2px; " />
    </svg> -->
  </div>
  <!-- </div> -->
</template>
<script>
export default {
  directives: {
    // inserted: function(e, binding) {
    //   document.onselectstart = function() {
    //     return false; // 禁止选择网页文字
    //   };
    //   // 鼠标按下事件
    //   e.onmousedown = el => {
    //     el.preventDefault();
    //   };
    // },
  },
  props: {
    // 是否允许拖动
    draggable: {
      type: Boolean,
      default: true,
    },
    x: {
      type: Number,
      default: 0,
    },
    y: {
      type: Number,
      default: 0,
    },
    containment: {
      type: Boolean,
      default: true,
    },
    name: {
      type: String,
      default: '',
    },
    type: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      positionX: 0,
      positionY: 0,
      drag: {
        show: false,
        fromX: null,
        fromY: null,
        toX: null,
        toY: null,
        width: null,
        height: null,
      },
    };
  },
  // mounted() {
  //   window.addEventListener('mousemove', this.handleKeymove);
  //   window.addEventListener('mouseup', this.handleKeyup);
  // },
  // destroyed() {
  //   window.removeEventListener('mousemove', this.handleKeymove);
  //   window.removeEventListener('mouseup', this.handleKeyup);
  // },
  methods: {
    onMousedown(e) {
      document.onselectstart = function() { return false; };// 解决拖动会选中文字的问题
      if (e.target.className === 'Bj-drag') {
        // 获取目标元素
        const odiv = e.target;
        // 计算鼠标相对元素位置
        // 元素距离页面左侧的长度 -
        const disX = e.clientX - odiv.offsetLeft;
        const disY = e.clientY - odiv.offsetTop;
        document.onmousemove = el => {
          // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
          let left = el.clientX - disX;
          let top = el.clientY - disY;
          const className = e.path[1].className;
          // console.log(e.path);
          const parent = document.getElementsByClassName(className)[0];
          // 判断是否启用限制节点拖动区域
          if (this.containment) {
            const MAX_W = parent.clientWidth - e.target.offsetWidth;
            const AMX_H = parent.clientHeight - e.target.offsetHeight;
            if (left < 0) left = 0;
            if (top < 0) top = 0;
            if (left > MAX_W) left = MAX_W;
            if (top > AMX_H) top = AMX_H;
          }
          // 绑定元素位置到positionX和positionY上面
          this.positionX = top;
          this.positionY = left;

          // 移动当前元素
          odiv.style.left = left + 'px';
          odiv.style.top = top + 'px';
          this.$emit('onDown', e);
        };
        document.onmouseup = el => {
          document.onmousemove = document.onmouseup = null;
        };
      }
    },
    /** 获取元素端点坐标 */
    getEndpointPlace(e) {},
    createOrigin(e) {
      console.log(e);
      this.drag.show = true;
      this.drag.fromX = e.clientX - e.offsetX;
      this.drag.fromY = e.clientY - e.offsetY;
    },
    onOver(e) {
      // console.log('移入');
      // console.log(e);
    },
    // onLeave(e) {
    //   console.log('移出');
    //   console.log(e);
    // },
    onUp(e) {
      console.log('抬起');
    },
    // handleKeymove(e) {
    //   if (this.drag.show) {
    //     // console.log(e);
    //     this.drag.toX = e.x;
    //     this.drag.toY = e.y;
    //     this.drag.width = Math.abs(e.x - this.drag.fromX);
    //     this.drag.height = Math.abs(e.y - this.drag.fromY);
    //   }
    // },
    // handleKeyup() {
    //   console.log('鼠标抬起');
    //   this.drag.show = false;
    //   console.log(this.drag);
    // },
  },
};
</script>

<style lang="scss" scoped>
.Bj-drag {
  position: absolute;
  display: inline-block;
  .el-button {
    position: relative;
    z-index: -1;
  }
}
</style>
