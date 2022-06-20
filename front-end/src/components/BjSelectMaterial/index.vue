<template>
  <ScrollSelect
    v-model="id"
    :api="api"
    placeholder="请选择"
    :multiple="multiple"
    :disabled="disabled"
    :size="size"
    :default-params="defaultParams"
    :default-options="defaultOptions"
    :remote="remote"
    option-label="materialName"
    option-value="id"
    res-key="content"
    search-key="materialName"
    @change="onChange"
  />
</template>
<script>
import { commonApi } from '@/api';
export default {
  name: 'BjSelectMaterial',
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
        return {};
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
  },
  data() {
    return {
      api: commonApi.getPageMaterial,
      id: null,
    };
  },
  watch: {
    value: {
      handler(v, o) {
        this.id = v;
        console.log('BjSelectMaterial', o, v);
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
  },
};
</script>
