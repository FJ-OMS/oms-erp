<template>
  <el-radio-group v-model="id" class="bj-el-radio-group" :style="`width:${width}`">
    <el-radio label="1"><i class="el-icon-s-flag flag-one" /></el-radio>
    <el-radio label="2"><i class="el-icon-s-flag flag-two" /></el-radio>
    <el-radio label="3"><i class="el-icon-s-flag flag-three" /></el-radio>
    <el-radio label="4"><i class="el-icon-s-flag flag-four" /></el-radio>
    <el-radio label="5"><i class="el-icon-s-flag flag-five" /></el-radio>
  </el-radio-group>
</template>
<script>
export default {
  name: 'FiveFlag',
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    size: {
      type: String,
      default: 'small',
    },
    width: {
      type: String,
      default: '400px',
    },
  },
  data() {
    return { id: null };
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
    console.log(this.value);
  },
  mounted() {},
  methods: {
    init() {},
  },
};
</script>
<style>
.bj-el-radio-group .el-radio__label {
  padding-left: 4px;
}
</style>
<style scoped></style>
