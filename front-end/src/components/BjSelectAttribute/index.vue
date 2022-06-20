<template>
  <el-select
    v-model.trim="id"
    class="full-width"
    placeholder="请选择"
    :multiple="multiple"
    :disabled="disabled"
    filterable
    clearable
  >
    <el-option v-for="(item, index) in list" :key="index" :label="item.label" :value="item.value" />
  </el-select>
</template>
<script>
import { commonApi } from '@/api';
export default {
  name: 'BjSelectAttribute',
  props: {
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
  },
  data() {
    return {
      id: null,
      list: [],
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
  mounted() {
    commonApi.getListAttribute({}).then(res => {
      this.list = (res || []).map(e => {
        return {
          label: e.attributeName,
          value: e.id,
        };
      });
    });
  },
  methods: {},
};
</script>
