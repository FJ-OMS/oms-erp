<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    :width="width"
    :fullscreen="fullscreen"
    :top="top"
    :modal="modal"
    :modal-append-to-body="modalAppendToBody"
    :append-to-body="appendToBody"
    :lock-scroll="lockScroll"
    :custom-class="customClass"
    :close-on-click-modal="closeOnClickModal"
    :show-close="showClose"
    :before-close="handleBeforeClose"
    :center="center"
    :destroy-on-close="destroyOnClose"
    @close="handleCloes"
  >
    <slot name="default"></slot>
    <span slot="footer" class="dialog-footer">
      <slot name="footer"></slot>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: 'BjDialog',
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: null,
    },
    width: {
      type: String,
      default: '50%',
    },
    /** 是否为全屏 Dialog */
    fullscreen: {
      type: Boolean,
      default: false,
    },
    /** Dialog CSS 中的 margin-top 值 */
    top: {
      type: String,
      default: '15vh',
    },
    // 是否需要遮罩层
    modal: {
      type: Boolean,
      default: true,
    },
    // 遮罩层是否插入至 body 元素上，若为 false，则遮罩层会插入至 Dialog 的父元素上
    modalAppendToBody: {
      type: Boolean,
      default: true,
    },
    // Dialog 自身是否插入至 body 元素上。嵌套的 Dialog 必须指定该属性并赋值为 true
    appendToBody: {
      type: Boolean,
      default: false,
    },
    // 是否在 Dialog 出现时将 body 滚动锁定
    lockScroll: {
      type: Boolean,
      default: false,
    },
    // Dialog 的自定义类名
    customClass: {
      type: String,
      default: null,
    },
    // 是否可以通过点击 modal 关闭 Dialog
    closeOnClickModal: {
      type: Boolean,
      default: true,
    },
    // 是否显示关闭按钮
    showClose: {
      type: Boolean,
      default: true,
    },
    // 关闭前的回调，会暂停 Dialog 的关闭
    beforeClose: Function,
    // 是否对头部和底部采用居中布局
    center: {
      type: Boolean,
      default: false,
    },
    // 关闭时销毁 Dialog 中的元素
    destroyOnClose: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      dialogVisible: false,
    };
  },
  watch: {
    visible(v) {
      this.dialogVisible = v;
    },
  },
  methods: {
    handleBeforeClose(done) {
      done();
    },
    handleCloes() {
      this.$emit('close');
    },
  },
};
</script>

<style></style>
