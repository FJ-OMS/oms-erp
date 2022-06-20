<!--
 * @Author: your name
 * @Date: 2021-11-05 13:47:38
 * @LastEditTime: 2022-04-22 10:05:04
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjInputMobile\index.vue
-->
<template>
  <el-form-item :label="label" :prop="prop" :rules="rules" :size="size">
    <el-input
      v-model="id"
      :placeholder="placeholder"
      :clearable="clearable"
      :disabled="disabled"
      maxlength="11"
      @keyup.native="proving($event)"
    />
  </el-form-item>
</template>
<script>
import { validatorMobile } from '@/utils/validate';
export default {
  name: 'BjFormItemMobile',
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
      default: '手机号',
    },
    prop: {
      type: [String],
      default: null,
    },
    required: {
      type: Boolean,
      default: false,
    },
    disabled: {
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
          validator: validatorMobile,
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
    // 限制正整数输入
    proving(e) {
      // 禁止0开头负数和小数点
      e.target.value = e.target.value.replace(/\D/g, '').replace(/^0{1,}/g, '');
      // e.target.value = parseInt(e.target.value.replace(/\D/g, ''));
      // if (isNaN(e.target.value)) {
      //   e.target.value = '';
      // }
    },
  },
};
</script>
