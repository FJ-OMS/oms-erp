<!--
 * @Author: your name
 * @Date: 2021-11-04 11:18:28
 * @LastEditTime: 2021-11-05 16:50:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjGoodsClassfiyTreeSelect\index.vue
-->
<template>
  <el-cascader
    ref="cascader"
    v-model="id"
    :options="options"
    :placeholder="placeholder"
    :disabled="disabled"
    :clearable="clearable"
    :show-all-levels="showAllLevels"
    :collapse-tags="collapseTags"
    :props="props"
    :size="size"
    @change="handleChange"
  >
  </el-cascader>
</template>
<script>
import { commonApi } from '@/api';
export default {
  name: 'BjGoodsClassfiyTreeSelect',
  props: {
    value: {
      type: [Array],
      default: () => {
        return [];
      },
    },
    props: {
      type: Object,
      default: () => {
        return {
          value: 'id',
          label: 'name',
          multiple: true,
          children: 'sonLevelCategory',
          // 在选中节点改变时，是否返回由该节点所在的各级菜单的值所组成的数组，若设置 false，则只返回该节点的值
          emitPath: false,
          // 是否严格的遵守父子节点不互相关联
          checkStrictly: false,
        };
      },
    },
    size: {
      type: String,
      default: 'mini',
    },
    placeholder: {
      type: String,
      default: null,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    clearable: {
      type: Boolean,
      default: false,
    },
    showAllLevels: {
      type: Boolean,
      default: true,
    },
    collapseTags: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      id: null,
      options: [],
      changeList: [],
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
  created() {
    commonApi.getAllCategory().then(res => {
      this.options = res[0].sonLevelCategory || [];
    });
  },
  methods: {
    handleChange(v) {
      // console.log(this.$refs.cascader.getCheckedNodes());
      this.changeList = this.$refs.cascader.getCheckedNodes().map(item => item.data);
      console.log(this.changeList);
      this.$emit('change', v);
    },
  },
};
</script>
