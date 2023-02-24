<template>
  <el-input
    v-model="id"
    type="textarea"
    :placeholder="placeholder"
    :maxlength="MAX_LENGTH.REMARK"
    :show-word-limit="showWordLimit"
    :rows="rows"
    :resize="resize"
    class="full-width"
  />
</template>

<script>
export default {
  name: 'BjTextarea',
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
    placeholder: {
      type: String,
      default: '请输入',
    },
    rows: {
      type: Number,
      default: 8,
    },
    showWordLimit: {
      type: Boolean,
      default: true,
    },
    /** 控制是否能被用户缩放 */
    resize: {
      type: String,
      default: 'none',
      validator: value => {
        return ['none', 'both', 'horizontal', 'vertical'].indexOf(value) !== -1;
      },
    },
  },
  data() {
    return {
      id: null,
    };
  },
  watch: {
    value: {
      handler(v) {
        this.id = v;
      },
      immediate: true,
    },
    id(v) {
      this.$emit('input', v);
    },
  },
};
</script>

<style></style>
