<!--
 * @  固定电话 固话
-->
<template>
  <el-form-item :label="label" :prop="prop" :rules="rules" :size="size">
    <el-input v-model="id" :placeholder="placeholder" :clearable="clearable" maxlength="13" />
  </el-form-item>
</template>
<script>
import { validatorPhone } from '@/utils/validate';
export default {
  name: 'BjFormItemPhone',
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
      default: '固定电话',
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
          validator: validatorPhone,
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
    // 限制输入
    proving(e) {
      e.target.value = e.target.value.replace(/[^\d-]/g, '');
    },
  },
};
</script>
