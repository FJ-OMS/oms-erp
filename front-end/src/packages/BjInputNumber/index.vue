<template>
  <el-input
    ref="Input20220317"
    v-model="id"
    :disabled="disabled"
    :readonly="readonly"
    :size="size"
    :placeholder="placeholder"
    :maxlength="maxlength"
    :clearable="clearable"
    :style="{ width: width }"
    @focus="onFocus"
    @keyup.native="onKeyUp"
    @blur="onBlur"
    @clear="onClear"
  >
    <template slot="append">
      <slot name="append" />
    </template>
  </el-input>
</template>
<script>
/**
 *  注 rules 用 change 事件
 *  例 ：
 *  rules: {  giftNum: [{ required: true, message: '请输入', trigger: 'change' }], },
 */
export default {
  name: 'BjInputNumber',
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
    readonly: {
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
    width: {
      type: [String, Number],
      default: '100%',
    },
    // 千分号
    perMilage: {
      type: Boolean,
      default: false,
    },
    placeholder: {
      type: String,
      default: '请输入',
    },
    min: {
      type: Number,
      default: null,
    },
    max: {
      type: Number,
      default: null,
    },
    maxlength: {
      type: Number,
      default: 9,
    },
    clearable: {
      type: Boolean,
      default: false,
    },
    // 数值精度（精确到小数点后几位）
    precision: {
      type: Number,
      default: 0,
    },
    // 空值时，不自动变为0；
    notAutoZero: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      id: null,
      preValue: null,
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
      this.$emit('change', v);
    },
  },
  activated() {
    this.id = this.value;
  },
  mounted() {},
  methods: {
    onFocus() {
      if (this.id == '0.00' || this.id == '0.0' || this.id == '0' || !this.id) {
        this.id = '';
      } else {
        this.id = String(this.id)
          .replace(/\,/g, '') // 去千分号
          .replace(/\.00/, '') // 去  xx.00
          .replace(/(\.\d+)0/, '$1'); // .x0
      }
      this.preValue = this.id;
    },
    onKeyUp() {
      if (this.id === '') {
        if (this.notAutoZero) {
          return;
        }
      }
      this.id = String(this.id)
        .replace(/^[\.]/, '')
        .replace(/[^\d.]/g, '');
      if (String(this.id).split('.').length - 1 > 1) {
        this.id = this.preValue;
      }
      this.tryMinMax();
      this.preValue = this.id;
    },
    onBlur() {
      if (this.id === '') {
        if (this.notAutoZero) {
          return;
        }
      }

      this.tryMinMax();
      this.id = String(this.id).replace(/[\.]$/, ''); // 去结尾的 小数点
      const re = new RegExp('(.*)\\.([\\d]{' + this.precision + '})(\\d*)', 'g');
      this.id = String(this.id).replace(re, '$1.$2'); // 保留小数
      this.id = Number(this.id).toFixed(this.precision); // 保留小数

      // 千分号
      if (this.perMilage) {
        var logNum = this.id.toString();
        if (logNum.match(/\./g) != null) {
          let integerNum = parseInt(logNum).toString(); // 整数部分
          integerNum = integerNum.replace(/\d(?=(\d{3})+$)/g, '$&,');
          const decimalNum = '.' + logNum.replace(/(.*)\.(.*)/g, '$2'); // 小数部分
          this.id = integerNum + decimalNum;
        } else {
          this.id = logNum.replace(/\d(?=(\d{3})+$)/g, '$&,');
        }
      }
      // 字符串转数字类型
      if (this.precision === 0) {
        this.id = Number(this.id);
      }
      this.$emit('blur');
    },
    onClear() {
      this.id = '';
    },
    tryMinMax() {
      if (this.min && this.id < this.min) {
        this.id = this.min;
      }
      if (this.max && this.id > this.max) {
        this.id = this.max;
      }
    },
  },
};
</script>
<style scoped></style>
