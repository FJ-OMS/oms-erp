<template>
  <ScrollSelect
    v-model="id"
    :api="api"
    :placeholder="placeholder"
    :multiple="multiple"
    :disabled="disabled"
    :size="size"
    :collapse-tags="collapseTags"
    :remote="remote"
    :default-params="defaultParams"
    :default-options="defaultOptions"
    search-key="name"
    option-label="name"
    :option-value="optionValue"
    res-key="content"
    @change="onChange"
  />
</template>
<script>
import { channelApi } from '@/api';
export default {
  name: 'BjSelectStore',
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
    placeholder: {
      type: String,
      default: '请选择',
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
    /** 多选时是否将选中值按文字的形式展示 */
    collapseTags: {
      type: Boolean,
      default: false,
    },
    remote: {
      type: Boolean,
      default: true,
    },
    optionValue: {
      type: String,
      default: 'id',
    },
  },
  data() {
    return {
      api: channelApi.getAllShopPage,
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
  },
};
</script>
