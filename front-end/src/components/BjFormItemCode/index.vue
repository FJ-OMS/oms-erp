<!--
 * @Author: your name
 * @Date: 2021-11-05 13:47:38
 * @LastEditTime: 2021-12-22 18:05:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjInputMobile\index.vue
-->
<template>
  <el-form-item :label="label" :prop="prop" :rules="rules" :size="size">
    <el-input
      v-if="!readonly"
      v-model="id"
      :disabled="disabled"
      :placeholder="placeholder"
      :clearable="clearable"
      maxlength="50"
      @keyup.native="proving($event)"
    />
    <span v-if="readonly">{{ id }}</span>
  </el-form-item>
</template>
<script>
import { validatorCodeSimpleLabel } from '@/utils/validate';
export default {
  name: 'BjFormItemCode',
  props: {
    readonly: {
      type: Boolean,
      default: false,
    },
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
      default: '编码',
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
    validator: Function,
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
          validator: (rule, value, callback) => {
            if (typeof this.validator == 'function') {
              this.validator(rule, value, callback, '商品编码');
            } else {
              validatorCodeSimpleLabel(rule, value, callback, '商品编码');
            }
          },
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
      // e.target.value = e.target.value.replace(/\D/g, '').replace(/^0{1,}/g, '');
      // e.target.value = parseInt(e.target.value.replace(/\D/g, ''));
      // if (isNaN(e.target.value)) {
      //   e.target.value = '';
      // }
    },
  },
};
</script>
