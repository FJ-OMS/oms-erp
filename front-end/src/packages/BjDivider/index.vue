<template>
  <div
    ref="BjDivider"
    class="bj-divider"
    :class="($slots.default ? ' ' : ' noPadding ') + size"
    :style="`width:${width}`"
  >
    <div class="left">
      <span v-if="$slots.default" class="default-text">
        <slot />
      </span>
      <div class="button">
        <slot name="button" />
      </div>
      <span class="tips"><slot name="tips" /></span>
    </div>
    <div id="rightBtnFixed" class="right" :class="{ fixed: isFixed }">
      <slot name="right" />
    </div>
  </div>
</template>
<script>
//   <span></span>
export default {
  name: 'BjDivider',
  props: {
    width: {
      type: String,
      default: '100%',
    },
    // 是否吸顶
    rightFixed: {
      type: Boolean,
      default: false,
    },
    size: {
      type: String,
      default: '', // mini  小字号
    },
  },
  data() {
    return {
      isFixed: false,
      distance: 0,
    };
  },
  mounted() {
    if (this.rightFixed) {
      // DOM异步更新 对未来更新后的视图进行操作 在更新后执行
      this.$nextTick(() => {
        const RIGHT_BTN = document.getElementById('rightBtnFixed');
        // 获取元素距离视口右边的距离，设置右边的定位距离
        const right =
          document.body.offsetWidth - this.$refs.BjDivider.getBoundingClientRect().right;
        RIGHT_BTN.style.right = right + 'px';
        // 固定值
        const btnTop = RIGHT_BTN.offsetTop;
          const btnHeight = RIGHT_BTN.clientHeight;
          const fixedHeaderHeight = document.querySelector('.fixed-header').clientHeight;
        this.distance = btnTop + btnHeight - fixedHeaderHeight - 1;
        // 对整个页面滚轮进行监听，每发生一次滚轮事件，执行一次方法
        window.addEventListener('scroll', this.initHeight, true);
      });
    }
  },
  destroyed() {
    // 移除监听
    if (this.rightFixed) window.removeEventListener('scroll', this.initHeight);
  },
  methods: {
    initHeight() {
      const scrollTop =
        window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
      this.isFixed = scrollTop > this.distance;
    },
  },
};
</script>
<style scoped lang="scss">
.bj-divider {
  padding: 16px 0;
  border-bottom: 1px solid #eee;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}
.bj-divider.mini {
  font-size: 14px;
  padding: 8px 0;
  margin-bottom: 8px;
}
.bj-divider.noPadding {
  padding: 0;
}

.bj-divider .left {
  display: flex;
  align-items: center;
}
.bj-divider .button {
  display: flex;
}
.bj-divider .default-text {
  border-left: 4px $main-dark solid;
  padding-left: 16px;
  font-size: 18px;
}
.bj-divider.mini .default-text {
  font-size: 14px;
}
.bj-divider .xs {
  font-size: 12px;
  padding: 0px 8px;
}
.bj-divider .tips {
  line-height: 20px;
  margin-left: 16px;
}
.bj-divider .right {
  display: flex;
  align-items: center;
}
.bj-divider .el-button {
  margin-left: 16px;
}
.fixed {
  position: fixed;
  top: 55px;
  z-index: 9;
  right: 20px;
}
.right .el-form-item--mini.el-form-item {
  margin-bottom: 0;
}
</style>
