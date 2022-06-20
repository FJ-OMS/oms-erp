<!--
 * @Author: your name
 * @Date: 2021-10-27 18:45:36
 * @LastEditTime: 2021-11-12 14:15:47
 * @LastEditors: your name
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \newBj-admin\src\components\BjSelectStoreOnline\index.vue
-->
<template>
  <ScrollSelect
    v-model="id"
    :api="api"
    :placeholder="placeholder"
    :multiple="multiple"
    :disabled="disabled"
    :size="size"
    :remote="remote"
    :default-params="defaultParams"
    :default-options="defaultOptions"
    search-key="name"
    :option-label="optionLabel"
    :option-value="optionValue"
    option-enabled="status"
    res-key="content"
    @change="onChange"
  />
</template>
<script>
import { channelApi } from '@/api';
export default {
  name: 'BjSelectStorePhysical',
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
    remote: {
      type: Boolean,
      default: true,
    },
    optionLabel: {
      type: String,
      default: 'name',
    },
    optionValue: {
      type: String,
      default: 'id',
    },
  },
  data() {
    return {
      api: channelApi.getPhysicalShopPage,
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
