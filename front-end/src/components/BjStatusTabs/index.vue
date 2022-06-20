<!--
 * @Author: your name
 * @Date: 2021-08-23 15:06:05
 * @LastEditTime: 2021-10-04 17:01:48
 * @LastEditors: Please set LastEditors
 * @Description: 以路由数组（在constant.js中定义）构建的tab，默认选中当前路由，点击自动跳转路由
 * @FilePath: \newBj-admin\packages\BjRouteTabs\index.vue
-->
<template>
  <div class="Bj-tabs">
    <el-tabs
      v-show="options.length > 1"
      v-model="id"
      :before-leave="beforeLeave"
      type="card"
      @tab-click="handleClick"
    >
      <el-tab-pane
        v-for="(item, index) in options"
        :key="index"
        :label="item.label"
        :name="item.name || item.value"
      ></el-tab-pane>
    </el-tabs>
    <div class="right"><slot /></div>
  </div>
</template>
<script>
// 以路由数组（在constant.js中定义）构建的tab，默认选中当前路由，点击自动跳转对应路由
import * as constantsData from '@/utils/constants';
export default {
  name: 'BjStatusTabs',
  props: {
    leave: {
      type: Boolean,
      default: true,
    },
    value: {
      type: String,
      default: 'all',
    },
    lovCode: {
      type: String,
      default: '',
    },
    optionList: {
      type: Array,
      default: () => {
        return [
          {
            label: 'a',
            name: 'routeName-a',
          },
          {
            label: 'b',
            name: 'routeName-b',
          },
          {
            label: 'c',
            name: 'routeName-c',
          },
        ];
      },
    },
    constantKey: {
      type: String,
      default: '',
    },
    itemLabel: {
      type: String,
      default: 'label',
    },
    itemValue: {
      type: String,
      default: 'value',
    },
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
    handleClick(tab, event) {
      this.id = tab.name;
      this.$emit('handleTabClick', tab, event);
    },
    beforeLeave() {
      return this.leave;
    },
  },
};
</script>
<style scoped lang="scss">
.Bj-tabs {
  position: relative;
  .right {
    position: absolute;
    right: 0;
    top: 0;
    height: 40px;
    line-height: 40px;
    z-index: 1;
  }
}
</style>
