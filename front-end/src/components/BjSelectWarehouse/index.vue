<template>
  <el-select
    v-model.trim="id"
    class="full-width"
    :placeholder="placeholder"
    :multiple="multiple"
    :disabled="disabled"
    :collapse-tags="collapseTags"
    :size="size"
    filterable
    clearable
    :loading="loading"
    @change="onChange"
    @clear="onClear"
  >
    <el-option
      v-for="(item, index) in options"
      :key="index"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>
<script>
import { commonApi } from '@/api';
export default {
  name: 'BjSelectWarehouse',
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
    placeholder: {
      type: String,
      default: '请选择',
    },
    // [{"value":"channel","meaning":"渠道仓","orderSeq":1},{"value":"ratio-virtual","meaning":"比例虚仓","orderSeq":2},{"value":"qty-virtual","meaning":"数量虚仓","orderSeq":3},{"value":"interline-virtual","meaning":"中转虚仓","orderSeq":4},{"value":"default-virtual","meaning":"默认虚仓","orderSeq":5},{"value":"real","meaning":"实仓","orderSeq":6},{"value":"logic","meaning":"逻辑仓","orderSeq":7}]
    typeCode: {
      type: Array,
      default: function() {
        return null;
      },
    },
    defaultCode: {
      type: String,
      default: null,
    },
    defaultName: {
      type: String,
      default: null,
    },
    size: {
      type: String,
      default: '',
    },
    collapseTags: {
      type: Boolean,
      default: true,
    },
    labelCode: {
      type: String,
      default: 'name',
    },
    valueCode: {
      type: String,
      default: 'id',
    },
  },
  data() {
    return {
      id: null,
      list: [],
      loading: false,
    };
  },
  computed: {
    options() {
      return this.list.map(e => {
        return {
          label: e[this.labelCode],
          value: e[this.valueCode],
        };
      });
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
    typeCode(o, n) {
      if (JSON.stringify(o) !== JSON.stringify(n)) {
        this.get();
      }
    },
    defaultCode(o, n) {
      this.get();
    },
    defaultName(o, n) {
      this.get();
    },
  },
  mounted() {
    this.get();
  },
  methods: {
    get() {
      this.loading = true;
      commonApi
        .getListWarehouses({
          code: this.defaultCode,
          name: this.defaultName,
          typeCode: this.typeCode,
          status: 1,
        })
        .then(res => {
          this.loading = false;
          this.list = Array.isArray(res) ? res : [];
        });
    },
    onChange(v) {
      const ITEM = this.list.find(e => e[this.valueCode] === v);
      this.$emit('change', v, ITEM || {});
    },
    onClear() {
      this.$emit('clear');
    },
  },
};
</script>
