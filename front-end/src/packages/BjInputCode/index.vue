<!--
 * @Author: 编码组件
 * @Date: 2022-01-06 10:54:16
 * @LastEditTime: 2022-01-06 13:15:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath:
-->
<template>
  <el-input
    v-model="id"
    :disabled="disabled"
    :readonly="readonly"
    :size="size"
    :placeholder="placeholder"
    :maxlength="MAX_LENGTH.DEFAULT"
    clearable
    @focus="onFocus"
    @keyup.native="onKeyUp"
    @blur="onBlur"
    @change="onChange"
  >
    <template slot="append">
      <slot name="append" />
    </template>
  </el-input>
</template>

<script>
import { checkCode, checkCodeWithComma } from '@/utils/validate';
export default {
  name: 'BjInputCode',
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    size: {
      type: String,
      default: 'mini',
    },
    placeholder: {
      type: String,
      default: '请输入',
    },
    // 千分号
    perMilage: {
      type: Boolean,
      default: false,
    },
    // 支持英文逗号
    comma: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      id: null,
    };
  },
  computed: {
    cptCheck() {
      return this.comma ? checkCodeWithComma : checkCode;
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
    },
  },
  activated() {
    this.id = this.value;
  },
  mounted() {},
  methods: {
    onFocus() {},
    onKeyUp() {
      this.id = String(this.id || '').replace(this.cptCheck, '');
    },
    onBlur() {
      this.$emit('blur');
    },
    onChange(v) {
      this.$emit('change', v);
    },
  },
};
</script>

<style></style>
