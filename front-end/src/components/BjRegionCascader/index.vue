<template>
  <el-cascader
    v-model="regionArray"
    :options="options"
    :clearable="clearable"
    :disabled="disabled"
    @change="addressChange"
    @active-item-change="handleItemChange"
  />
</template>
<script>
/**
 * @description 中国行政区三级联动
 * @author ZhengXinquan
 * el-cascader 文档： https://cloud.tencent.com/developer/section/1489874
 */
// import { regionDataPlus, CodeToText } from 'element-china-area-data';
import { commonApi } from '@/api';
export default {
  props: {
    value: {
      type: [String, Number, Array],
      default: null,
    },
    // 值类型可能只是一个 区级的id；或者 完整的id Array=[省，市，区]
    isArray: {
      type: Boolean,
      default: false,
    },
    clearable: {
      type: Boolean,
      default: false,
    },
    length: {
      type: Number,
      default: 3,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      regionArray: [],
      options: [],
      resultArray: [],
    };
  },
  watch: {
    regionArray(arr, oarr) {
      // console.log('==>watch regionArray', JSON.stringify(arr), JSON.stringify(oarr));
      if (this.isArray) {
        this.$emit('input', arr);
      } else {
        const v = arr[arr.length - 1];
        const ov = oarr[oarr.length - 1];
        if (v == ov) {
          // 避免 String 和 Number 类型 引起的重复请求
          return;
        }
        this.$emit('input', Number(v));
      }
    },
    value: {
      async handler(v, ov) {
        console.log('==>watch value', JSON.stringify(v), JSON.stringify(ov));
        this.options = await this.getProvince();
        if (v) {
          if (v == ov) {
            // 避免 String 和 Number 类型 引起的重复请求
            return;
          }
          if (this.isArray) {
            // 避免引起的重复请求
            if (JSON.stringify(v) != JSON.stringify(ov)) {
              this.regionArray = v;
            } else {
              console.warn('重复请求 JSON.stringify(ov)', JSON.stringify(ov));
              return;
            }
          } else {
            // 用区id 从接口 获取完整的id Array=[省，市，区]
            this.regionArray = await this.getAreaDetails(v);
          }

          // console.log('==> regionArray', JSON.stringify(this.regionArray));
          if (this.regionArray.length >= 2) {
            await this.getCity(this.regionArray[0]);

            if (this.regionArray.length == 3) {
              this.$nextTick(async () => {
                await this.getArea(this.regionArray[0], this.regionArray[1]);
              });
            }
          }
        }
      },
      immediate: true,
    },
  },
  mounted() {},
  methods: {
    getAreaDetails(areaCode) {
      return new Promise((resolve, reject) => {
        commonApi
          .getAreaDetails({
            areaCode: areaCode,
          })
          .then(res => {
            if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
              // TODO: 这里需要按新接口作调整
              const provinceCode = res.levelOneAreaCode;
              const cityCode = res.levelTwoAreaCode;
              const areaCode = res.levelThreeAreaCode;
              resolve([provinceCode, cityCode, areaCode]);
            } else {
              reject(res);
            }
          })
          .catch(err => {
            reject(err);
          });
      });
    },
    async handleItemChange(value, flag) {
      console.log('active item ', value, flag);
      const [provinceCode, cityCode, areaCode] = value;
      console.log(provinceCode, cityCode, areaCode);
      if (!flag && areaCode) {
        return;
      }
      if (cityCode) {
        await this.getArea(provinceCode, cityCode);
      } else {
        if (provinceCode) {
          await this.getCity(provinceCode);
        }
      }
    },
    getArea(provinceCode, cityCode) {
      const N = this.options.findIndex(e => e.value == provinceCode);
      // console.log(provinceCode, N);
      // console.log(cityCode);
      const M = this.options[N].children.findIndex(e => e.value == cityCode);
      if (
        this.options[N] &&
        this.options[N].children[M] &&
        this.options[N].children[M].children &&
        this.options[N].children[M].children.length
      ) {
        // console.log(`-->getArea2.[${cityCode}]`, this.options[N].children[M].children);
        return this.options[N].children[M].children;
      }

      return new Promise((resolve, reject) => {
        commonApi
          .getCity({
            areaCode: cityCode,
            BjAjaxLoadingHide: true,
          })
          .then(res => {
            if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
              this.options[N].children[M].children = (res || []).map(e => {
                return {
                  value: e.areaCode,
                  label: e.name,
                };
              });
            }
            console.log(`-->getArea2.[${cityCode}]`, this.options[N].children[M].children);
            resolve();
          })
          .catch(err => {
            console.error(err);
            reject(err);
          });
      });
    },
    getCity(provinceCode) {
      const N = this.options.findIndex(e => e.value == provinceCode);
      if (this.options[N] && this.options[N].children && this.options[N].children.length) {
        // console.log(`-->getCity1.[${provinceCode}]`, this.options[N].children);
        return this.options[N].children;
      }

      return new Promise((resolve, reject) => {
        commonApi
          .getCity({
            areaCode: provinceCode,
            BjAjaxLoadingHide: true,
          })
          .then(res => {
            if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
              const N = this.options.findIndex(e => e.value == provinceCode);
              this.options[N].children = (res || []).map(e => {
                const TEMP = {
                  value: e.areaCode,
                  label: e.name,
                  children: [],
                };
                if (this.length <= 2) {
                  delete TEMP.children;
                }
                return TEMP;
              });
            }
            // console.log(`-->getCity2.[${provinceCode}]`, this.options[N].children);
            resolve();
          })
          .catch(err => {
            console.error(err);
            reject(err);
          });
      });
    },
    getProvince() {
      if (this.options && this.options.length) {
        return this.options;
      }
      this.options = [{}];
      return new Promise((resolve, reject) => {
        commonApi
          .getProvince({ BjAjaxLoadingHide: true })
          .then(res => {
            if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
              const options = (res || []).map(e => {
                return {
                  value: e.areaCode,
                  label: e.name,
                  children: [],
                };
              });
              resolve(options);
            }
          })
          .catch(err => {
            console.error(err);
            reject(err);
          });
      });
    },
    addressChange(v) {
      if (v.length) {
        this.resultArray[0] = this.options.find(e => e.value === v[0]);
      }
      if (v.length > 1) {
        this.resultArray[1] = this.resultArray[0].children.find(e => e.value === v[1]);
      }
      if (v.length > 2) {
        this.resultArray[2] = this.resultArray[1].children.find(e => e.value === v[2]);
      }
      this.$emit('change', this.resultArray);
    },
  },
};
</script>
