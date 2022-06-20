<template>
  <el-select v-model="country" placeholder="请选择" filterable clearable @change="change">
    <el-option-group v-for="group in countryList" :key="group.label" :label="group.label">
      <el-option
        v-for="item in group.options"
        :key="item.value"
        :label="item.abbreviation"
        :value="item.countryId"
      >
        <span style="float: left">{{ item.abbreviation }}</span>
        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.countryCode }}</span>
      </el-option>
    </el-option-group>
  </el-select>
</template>
<script>
import { baseDataApi } from '@/api';
export default {
  name: 'BjCountry',
  props: {
    value: {
      type: [String, Number],
      default: '',
    },
  },
  data() {
    return {
      country: '',
      countryList: [
        {
          label: '热门国家',
          options: [{ value: 'China', label: '中国', id: 0 }],
        },
        {
          label: '所有国家',
          options: [],
        },
      ],
    };
  },
  watch: {
    value: {
      handler(v, ov) {
        // console.log('   get old value=', ov);
        // console.log('BjCountry get new value=', v);

        if (!v) {
          if (v === '0' || v === 0) {
            this.country = 0;
          } else {
            this.country = '';
          }
        } else {
          this.country = v;
        }

        // console.log('BjCountry  set country', this.country);
      },
      immediate: true,
    },
    country(v, ov) {
      // console.log('BjCountry old country=', ov);
      // console.log('BjCountry new country=', v);
      // console.log('BjCountry  set input', v);
      this.$emit('input', v); // 触发父组件的input事件
      const data = this.countryList[1].options.find(item => item.countryId === v);
      this.$emit('change', v, data);
    },
  },
  mounted() {
    baseDataApi.listCountry({ enabledFlag: 1 }).then(res => {
      if (Array.isArray(res)) {
        const LIST = [];
        const CHINA = res.find(e => e.abbreviation == '中国');
        if (CHINA) {
          this.country = CHINA.countryId;
          LIST.push({
            label: '热门国家',
            options: [CHINA],
          });
        }
        LIST.push({
          label: '所有国家',
          options: res,
        });
        this.countryList = LIST;
      }
    });
  },
  methods: {
    change(id) {
      // 获取选中国家对应的data
      const data = this.countryList[1].options.find(item => item.countryId === id);
      this.$emit('change', id, data);
    },
  },
};
</script>
