<!--
 * @Author: your name
 * @Date: 2021-09-28 10:03:43
 * @LastEditTime: 2022-01-24 13:30:33
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\packages\BjSelectChinaArea\index.vue
-->
<template>
  <el-select
    v-model.trim="id"
    :placeholder="placeholder"
    :multiple="multiple"
    :disabled="disabled"
    :value-key="valueKey"
    :size="size"
    :clearable="clearable"
    @change="onChange"
  >
    <el-option
      v-for="(item, index) in list"
      :key="index"
      :label="item.name"
      :value="item.areaCode"
      :disabled="item.disabled"
    />
  </el-select>
</template>
<script>
import { commonApi } from '@/api';
export default {
  name: 'BjSelectChinaArea',
  props: {
    value: {
      type: [String, Number, Array],
      default: null,
    },
    type: {
      type: String,
      default: 'province',
      validator: value => {
        return ['province', 'city', 'area'].indexOf(value) !== -1;
      },
    },
    parentCode: {
      type: [String, Number],
      default: '',
    },
    placeholder: {
      type: String,
      default: null,
    },
    /** 多选 */
    multiple: {
      type: Boolean,
      default: false,
    },
    /** 是否禁用 */
    disabled: {
      type: Boolean,
      default: false,
    },
    /** 作为 value 唯一标识的键名，绑定值为对象类型时必填 */
    valueKey: {
      type: String,
      default: 'value',
    },
    size: {
      type: String,
      default: 'mini',
    },
    /** 清空选项 */
    clearable: {
      type: Boolean,
      default: false,
    },
    all: {
      type: Boolean,
      default: false,
    },
    disabledList: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  data() {
    return {
      id: '',
      list: [],
      allData: {
        areaCode: null,
        id: 0,
        name: '全部',
        shortName: '全部',
      },
    };
  },
  watch: {
    value: {
      handler(val) {
        this.id = val;
      },
      immediate: true,
    },
    id(val) {
      this.$emit('input', val);
    },
    parentCode(val) {
      if (val) this.getCity();
    },
  },
  created() {
    if (this.all) {
      this.list.unshift(this.allData);
    }
    if (this.type === 'province') this.getProvince();
  },
  methods: {
    /** 获取省 */
    getProvince() {
      commonApi.getProvince({ BjAjaxLoadingHide: true }).then(res => {
        if (this.all) {
          this.list = [this.allData];
        }
        res.forEach(e => {
          e.disabled = false;
          if (this.disabledList.includes(e.areaCode)) e.disabled = true;
        });
        this.list = [...this.list, ...res];
      });
    },
    /** 获取市和区 */
    getCity() {
      commonApi.getCity({ areaCode: this.parentCode, BjAjaxLoadingHide: true }).then(res => {
        if (this.all) {
          this.list = [this.allData];
        }
        res.forEach(e => {
          e.disabled = false;
          if (this.disabledList.includes(e.areaCode)) e.disabled = true;
        });
        this.list = [...this.list, ...res];
      });
    },
    onChange(val) {
      const data = this.list.filter(e => e.areaCode === val);
      this.$emit('change', val, data[0]);
    },
  },
};
</script>
