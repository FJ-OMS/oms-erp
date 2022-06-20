<!--
 * @Author: your name
 * @Date: 2021-11-02 13:32:19
 * @LastEditTime: 2021-11-12 14:15:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjSelectAudit\index.vue
-->
<template>
  <ScrollSelect
    v-model="id"
    :api="api"
    placeholder="请选择"
    :multiple="multiple"
    :disabled="disabled"
    :size="size"
    :remote="remote"
    :default-params="defaultParams"
    :default-options="defaultOptions"
    search-key="name"
    :option-label="optionLabel"
    :option-value="optionValue"
    res-key="content"
    @change="onChange"
  />
</template>
<script>
import { strategyApi } from '@/api';
export default {
  name: 'BjSelectOrderAudit',
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
    // /** 是否是订单策略，否为商品行策略 */
    // orderAudit: {
    //   type: Boolean,
    //   default: true,
    // },
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
      default: 'strategyName',
    },
    optionValue: {
      type: String,
      default: 'id',
    },
  },
  data() {
    return {
      api: strategyApi.getOrderAuditSetting,
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
    // api() {
    //     return this.orderAudit ? strategyApi.getOrderAuditSetting : strategyApi.getGoodLineAuditSetting;
    // },
  },
  mounted() {},
  methods: {
    onChange(v, item) {
      this.$emit('change', v, item);
    },
  },
};
</script>
