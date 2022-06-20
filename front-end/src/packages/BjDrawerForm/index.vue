<template>
  <el-drawer
    v-if="visible"
    class="drawer-data-form-20210720"
    :title="title"
    :visible.sync="visible"
    direction="rtl"
    :before-close="hide"
    :show-close="showClose"
    :with-header="withHeader"
    :size="width"
    :wrapper-closable="wrapperClosable"
    :modal-append-to-body="appendToBody"
  >
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      :label-width="labelWidth"
      :size="size"
      :inline="inline"
      class="bj-label-colon"
    >
      <el-row> <slot /></el-row>
    </el-form>
    <div class="footer" :class="withHeader ? 'has-title' : ''">
      <el-button v-if="cancelButtonText" @click="hide">{{ cancelButtonText }}</el-button>
      <el-button v-if="confirmButtonText" type="primary" @click="handleConfirm">{{
        confirmButtonText
      }}</el-button>
    </div>
  </el-drawer>
</template>
<script>
export default {
  name: 'BjDrawerForm',
  props: {
    /* 表单数据对象 */
    form: {
      type: Object,
      default: () => {
        return {};
      },
    },
    /** 表单校验对象 */
    rules: {
      type: Object,
      default: () => {
        return {};
      },
    },
    labelWidth: {
      type: String,
      default: '100px',
    },
    width: {
      type: String,
      default: '600px',
    },
    size: {
      type: String,
      default: 'mini',
    },
    /** 行内表单模式 */
    inline: {
      type: Boolean,
      default: false,
    },
    /** 是否显示关闭按钮 */
    showClose: {
      type: Boolean,
      default: true,
    },
    /** 是否点击遮罩关闭 */
    wrapperClosable: {
      type: Boolean,
      default: false,
    },
    /** 确定按钮的文本内容 */
    confirmButtonText: {
      type: [String, Number],
      default: '确 定',
    },
    /** 取消按钮的文本内容 */
    cancelButtonText: {
      type: [String, Number],
      default: '取 消',
    },
    appendToBody: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      title: '',
      visible: false,
    };
  },
  computed: {
    withHeader() {
      return !!this.title;
    },
  },
  methods: {
    show() {
      this.visible = true;
    },
    setTitle(v) {
      this.title = v;
    },
    hide() {
      this.visible = false;
      this.$emit('close', this.form);
    },
    handleConfirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('handleConfirm');
        }
      });
    },
  },
};
</script>

<style lang="scss">
/***/
.drawer-data-form-20210720 {
  .el-drawer__container {
    padding: 8px;
  }
  .el-drawer__header {
    margin-bottom: 4px;
  }
  .el-drawer__body {
    padding: 16px;
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  .el-drawer__body form {
    flex: 1;
    max-height: 100%;
    overflow-y: auto;
    padding: 16px;
  }
  .el-drawer__body .footer button {
    width: 200px;
  }
  .footer.has-title {
    margin-bottom: 45px;
  }
}
</style>
