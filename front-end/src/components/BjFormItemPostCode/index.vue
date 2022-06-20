<!--
 * @  邮编
-->
<template>
  <el-form-item :label="label" :prop="prop" :rules="rules" :size="size">
    <el-input
      v-model="id"
      :placeholder="placeholder"
      :clearable="clearable"
      maxlength="6"
      @keyup.native="proving($event)"
    />
  </el-form-item>
</template>
<script>
import { validatorPostCode } from '@/utils/validate';
export default {
  name: 'BjFormItemPostCode',
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
    placeholder: {
      type: String,
      default: '请输入',
    },
    size: {
      type: String,
      default: 'mini',
    },
    clearable: {
      type: Boolean,
      default: true,
    },
    label: {
      type: [String, Number],
      default: '邮编',
    },
    prop: {
      type: [String],
      default: null,
    },
    required: {
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
    rules() {
      return [
        {
          validator: validatorPostCode,
          required: this.required,
          trigger: 'blur',
        },
      ];
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
  methods: {
    // 限制整数输入
    proving(e) {
      // 禁止负数和小数点
      e.target.value = e.target.value.replace(/\D/g, '');
    },
  },
};
</script>
