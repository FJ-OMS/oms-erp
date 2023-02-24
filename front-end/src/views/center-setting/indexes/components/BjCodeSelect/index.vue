<!--
 * @Author: your name
 * @Date: 2021-07-26 10:43:27
 * @LastEditTime: 2021-07-27 16:28:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\indexes\components\BjSelect\index.vue
-->
<template
  ><div>
    <el-select
      v-model="currentValue"
      filterable
      reserve-keyword
      :size="size"
      placeholder="请选择"
      :loading="loading"
      @change="change"
    >
      <el-option
        v-for="item in indexCodeList"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
  </div>
</template>
<script>
import { settingApi } from '@/api';
export default {
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
    indexCode: {
      type: [String, Number],
      default: null,
    },
    size: {
      type: String,
      default: 'mini',
    },
  },
  data() {
    return {
      currentValue: null,
      loading: false,
      /** 模糊查询索引列表 */
      indexCodeList: [],
      /** 模糊查询索引匹配列表 */
      indexCodeListOptions: [],
    };
  },
  watch: {
    value: {
      handler(newVal) {
        this.currentValue = newVal;
      },
      immediate: true,
    },
    currentValue(v) {
      // const obj = this.indexCodeList.find(item => {
      //   return item.value === v;
      // });
      // this.$emit('input', {
      //   indexCode: obj.label,
      //   indexId: obj.value,
      // });
      // this.$emit('change', {
      //   indexCode: obj.label,
      //   indexId: obj.value,
      // });
    },
  },
  created() {},
  mounted() {
    settingApi.getIndexesList().then(res => {
      if (!res.failed) {
        res.content.forEach(item => {
          this.indexCodeList.push({
            value: item.indexId,
            label: item.indexCode,
          });
        });
      }
    });
  },
  methods: {
    change(val) {
      const obj = this.indexCodeList.find(item => {
        return item.value === val;
      });
      this.$emit('input', {
        indexCode: obj.label,
        indexId: obj.value,
      });
      this.$emit('change', {
        indexCode: obj.label,
        indexId: obj.value,
      });
    },
  },
};
</script>
