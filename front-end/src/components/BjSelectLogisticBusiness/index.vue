<!--
 * @Author: your name
 * @Date: 2021-09-01 11:58:39
 * @LastEditTime: 2022-01-22 09:59:45
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjSelectLogisticBusiness\index.vue
-->
<template>
  <ScrollSelect
    ref="ScrollSelect"
    v-model="id"
    :api="api"
    :placeholder="placeholder"
    :multiple="multiple"
    :disabled="disabled"
    :size="size"
    :default-params="defaultParams"
    :default-options="defaultOptions"
    :remote="remote"
    :no-data-text="noDataText"
    search-key="name"
    option-label="name"
    option-enabled="enableFlag"
    option-value="id"
    res-key="content"
    @change="onChange"
  />
</template>
<script>
import { logisticApi } from '@/api';
export default {
  name: 'BjSelectLogisticBusiness',
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
    placeholder: {
      type: String,
      default: '请选择',
    },
    noDataText: {
      type: String,
      default: '无数据',
    },
  },
  data() {
    return {
      api: logisticApi.getLogisticsBusiness,
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
      console.log(v);
      // console.log(this.$refs.ScrollSelect.list);
      const ITEM = this.$refs.ScrollSelect.list.find(e => e['id'] === v);
      console.log(ITEM);
      this.$emit('change', v, ITEM);
    },
  },
  mounted() {},
  methods: {
    onChange(v, item) {},
  },
};
</script>
