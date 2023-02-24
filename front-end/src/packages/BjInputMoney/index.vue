<template>
  <BjInputNumber
    v-model="id"
    :disabled="disabled"
    :readonly="readonly"
    :size="size"
    :placeholder="placeholder"
    :maxlength="maxlength"
    clearable
    :per-milage="perMilage"
    :precision="2"
    @blur="onBlur"
  >
    <template slot="append">
      <slot name="append" />
    </template>
  </BjInputNumber>
</template>
<script>
export default {
  name: 'BjInputMoney',
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
    placeholder: {
      type: String,
      default: '请输入',
    },
    // 千分号
    perMilage: {
      type: Boolean,
      default: false,
    },
    maxlength: {
      type: Number,
      default: 9,
    },
  },
  data() {
    return {
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
      this.$emit('change', v);
    },
  },
  activated() {
    this.id = this.value;
  },
  mounted() {},
  methods: {
    onBlur(v) {
      this.$emit('blur');
    },
  },
};
</script>
<style scoped></style>
