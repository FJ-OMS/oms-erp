<template>
  <el-form-item
    ref="BjFormItemAutoCode20210908"
    :label="label"
    :prop="prop"
    :rules="rules"
    :size="size"
  >
    <el-input
      v-model.trim="id"
      :disabled="bj_disabled"
      :placeholder="bjAutoCode ? '自动生成' : `请输入${label}`"
      clearable
      @focus="onFocus"
      @keyup.native="onKeyUp"
      @blur="onBlur"
    >
      <el-switch v-if="!disabled" slot="append" v-model="bjAutoCode" active-text="自动生成" />
    </el-input>
  </el-form-item>
</template>
<script>
export default {
  name: 'BjFormItemAutoCode',
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    size: {
      type: String,
      default: 'small',
    },
    /** */
    label: {
      type: String,
      default: '',
    },
    prop: {
      type: String,
      default: 'code',
    },
  },
  data() {
    return {
      id: null,
      bjAutoCode: false,
    };
  },
  computed: {
    rules() {
      return {
        required: !this.bjAutoCode,
        message: `请输入${this.label}`,
        trigger: 'blur',
      };
    },
    bj_disabled() {
      if (this.disabled) {
        return true;
      }
      return this.bjAutoCode;
    },
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
      this.$emit('change', v);
    },
    bjAutoCode(value) {
      if (value && !this.disabled) {
        this.id = null;
        this.$refs.BjFormItemAutoCode20210908.clearValidate();
      }
    },
  },
  activated() {
    this.id = this.value;
  },
  mounted() {},
  methods: {
    onFocus(e) {
      this.$emit('focus', e);
    },
    onKeyUp(e) {
      this.$emit('keyup', e);
    },
    onBlur(e) {
      this.$emit('blur', e);
    },
  },
};
</script>
<style scoped></style>
