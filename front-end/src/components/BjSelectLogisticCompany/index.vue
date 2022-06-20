<!--
 * @Author: your name
 * @Date: 2021-08-31 17:18:40
 * @LastEditTime: 2022-01-25 11:42:12
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjSelectLogisticCompany\index.vue
-->
<template>
  <ScrollSelect
    ref="scrollSelect"
    v-model="id"
    :api="api"
    :placeholder="placeholder"
    :multiple="multiple"
    :disabled="disabled"
    :size="size"
    :default-params="defaultParams"
    :default-options="defaultOptions"
    :remote="remote"
    search-key="name"
    option-label="name"
    :option-value="optionValue"
    option-enabled="enableFlag"
    res-key="content"
    @change="onChange"
    @blur="onBlur"
  />
</template>
<script>
import { logisticApi } from '@/api';
export default {
  name: 'BjSelectLogisticCompany',
  props: {
    // 默认添加的选项，可用于回写数据
    defaultOptions: {
      type: [Array, Object],
      default() {
        return [];
      },
    },
    // 默认传参
    defaultParams: {
      type: Object,
      default() {
        return { enableFlag: true };
      },
    },
    value: {
      type: [String, Number, Array],
      default: null,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    pageSize: {
      type: Number,
      default: 10,
    },
    size: {
      type: String,
      default: 'mini',
    },
    remote: {
      type: Boolean,
      default: true,
    },
    optionValue: {
      type: String,
      default: 'id',
    },
    placeholder: {
      type: String,
      default: '请选择',
    },
  },
  data() {
    return {
      api: logisticApi.getLogisticsCompany,
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
  mounted() {},
  methods: {
    onChange(v, item) {
      this.$emit('change', v, item);
    },
    onBlur() {
      this.$emit('blur');
    },
  },
};
</script>
