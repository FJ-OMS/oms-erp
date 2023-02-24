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
      v-show="filterRouteList.length > 1"
      :value="tabValue"
      :before-leave="beforeLeave"
      type="card"
      @tab-click="handleClick"
    >
      <el-tab-pane
        v-for="(item, index) in filterRouteList"
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
  name: 'BjRouteTabs',
  props: {
    leave: {
      type: Boolean,
      default: false,
    },
    value: {
      type: String,
      default: '',
    },
    constantKey: {
      type: String,
      default: '',
    },
    routeList: {
      type: Array,
      default: function() {
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
  },
  computed: {
    tabValue() {
      if (this.value) return this.value;
      return this.$route.name;
    },
    // 根据菜单权限，过滤路由
    filterRouteList() {
      const PARENT_ROUTE = this.$route.matched.slice(-2, -1)[0];
      if (
        PARENT_ROUTE.meta &&
        Array.isArray(PARENT_ROUTE.meta.tabs) &&
        PARENT_ROUTE.meta.tabs.length
      ) {
        return PARENT_ROUTE.meta.tabs;
      }

      if (this.constantKey) {
        return constantsData[this.constantKey];
      }

      return this.routeList;
    },
  },
  methods: {
    handleClick(tab, event) {
      this.$router.push({ name: tab.name });
    },
    beforeLeave() {
      return this.leave;
    },
  },
};
</script>
<style scoped lang="scss">
.bj-divider {
  padding: 16px 0;
  border-bottom: 1px solid #eee;
  margin-bottom: 16px;
  display: flex;
}
.bj-divider span:first-child {
  border-left: 4px #42b983 solid;
  padding-left: 16px;
  font-size: 18px;
}
.bj-divider .el-button {
  font-size: 12px;
  padding: 3px 10px;
  margin-left: 16px;
}
.bj-divider .tips {
  line-height: 20px;
  margin-left: 16px;
}
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
