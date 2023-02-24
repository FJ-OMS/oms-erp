<template>
  <el-steps :active="active" finish-status="success" :simple="times.length == 0" align-center>
    <el-step
      v-for="(item, index) in options"
      :key="index"
      :title="item.label"
      :description="times[index]"
    />
  </el-steps>
</template>
<script>
import * as constantsData from '@/utils/constants';
export default {
  props: {
    times: {
      type: Array,
      default: () => {
        return [];
      },
    },
    status: {
      type: [String, Number],
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
    return {};
  },
  computed: {
    options() {
      if (this.constantKey) {
        return constantsData[this.constantKey];
      }
      if (this.optionList && this.optionList.length) {
        return this.optionList;
      }
      return [];
    },
    active() {
      let index = this.options.findIndex(e => e.value == this.status);
      if (index == -1) {
        index = this.options.length;
      }
      return index;
    },
  },
};
</script>
