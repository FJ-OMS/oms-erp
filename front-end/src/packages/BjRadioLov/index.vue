<template>
  <el-radio-group v-model="id" :disabled="disabled" :size="size" @change="onChange">
    <el-radio
      v-for="(item, index) in options"
      :key="index"
      :label="item.value"
      :disabled="item.disabled"
    >
      <slot name="default" :item="item" />
      <span v-if="!$scopedSlots.default">{{ item.label }}</span>
    </el-radio>
  </el-radio-group>
</template>
<script>
/**
 * 值集选择器
 * @props lovCode 值集编码 必须
 * 示例：
 *   <BjSelectLov v-model="typeCode" lov-code="HFLE.CAPACITY.ACCESS_CONTROL" />
 */
import * as constantsData from '@/utils/constants';
export default {
  name: 'BjRadioLov',
  props: {
    value: {
      type: [String, Number, Array, Boolean],
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
    size: {
      type: String,
      default: 'mini',
    },
    placeholder: {
      type: String,
      default: '请选择',
    },
    clearable: {
      type: Boolean,
      default: true,
    },
    lovCode: {
      type: String,
      default: '',
    },
    optionList: {
      type: Array,
      default: () => {
        return [];
      },
    },
    constantKey: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      id: null,
      list: [],
    };
  },
  computed: {
    __lovCode() {
      return this.lovCode.replace(/\./g, '_');
    },
    options() {
      if (this.constantKey) {
        return constantsData[this.constantKey];
      }
      if (this.optionList && this.optionList.length) {
        return this.optionList;
      }
      return this.$store.state.values[this.__lovCode] || [];
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
  activated() {
    this.id = this.value;
  },
  mounted() {
    if (this.lovCode == 'BJ_SALES_ORG') {
      this.$store.dispatch('values/getSalesOrganizationList');
    } else if (this.lovCode == 'BJ_EXECUTOR_ID') {
      this.$store.dispatch('values/getExecutorList');
    } else if (this.lovCode == 'BJ_EXECUTABLE_ID') {
      this.$store.dispatch('values/getExecutableList');
    } else if (this.lovCode == 'BJ_TENANTS') {
      this.$store.dispatch('values/getTenants');
    } else if (this.lovCode == 'BJ_LANGUAGE') {
      this.$store.dispatch('values/getLanguagesList');
    } else if (this.lovCode == 'BJ_INSTALL_SERVICES') {
      this.$store.dispatch('values/getInstallServices');
    } else if (this.lovCode == 'BJ_TAGS') {
      this.$store.dispatch('values/getTags');
    } else if (this.lovCode == 'BJ_AFTER_REFUND_ONLY_REASONS') {
      this.$store.dispatch('values/getAfterRefundOnlyReasons');
    } else if (this.lovCode == 'BJ_AFTER_REFUND_GOODS_REASONS') {
      this.$store.dispatch('values/getAfterRefundGoodsReasons');
    } else {
      if (this.lovCode) {
        this.$store.dispatch('values/getValueList', this.lovCode);
      }
    }
  },
  methods: {
    onChange(v) {
      const ITEM = this.options.find(e => e.value === v);
      this.$emit('change', v, ITEM || {});
    },
  },
};
</script>
