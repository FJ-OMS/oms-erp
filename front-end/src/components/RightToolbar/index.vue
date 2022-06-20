<template>
  <div class="flex">
    <div class="top-left-btn">
      <el-row :gutter="10" class="mb8">
        <!--表格左侧操作区-->
        <slot name="tableOperation" />
      </el-row>
    </div>
    <!--表格右侧操作区-->
    <div class="top-right-btn">
      <el-row>
        <el-tooltip class="item" effect="dark" content="设 置" placement="top">
          <el-button circle icon="el-icon-setting" @click="showSetting()" />
        </el-tooltip>
        <el-tooltip
          v-if="hasQueryParams"
          class="item"
          effect="dark"
          :content="showSearch ? '隐藏搜索' : '显示搜索'"
          placement="top"
        >
          <el-button circle @click="toggleSearch()"
            ><svg-icon :icon-class="showSearch ? 'eye' : 'eye-open'"
          /></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="刷新" placement="top">
          <el-button circle icon="el-icon-refresh" @click="refresh()" />
        </el-tooltip>
      </el-row>

      <!-- <el-drawer
        title="我是标题"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="drawerClose"
        :size="'25%'"
      >
        <slot name="drawer" />
      </el-drawer> -->
    </div>
  </div>
</template>
<script>
export default {
  name: 'RightToolbar',
  props: {
    showSearch: {
      type: Boolean,
      default: true,
    },
    hasQueryParams: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {};
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    // 搜索
    toggleSearch() {
      this.$emit('update:showSearch', !this.showSearch);
    },
    // 刷新
    refresh() {
      this.$emit('queryTable');
    },
    /** START 以下配合 ElementTableSortable 插件 */
    // 设置
    showSetting() {
      this.$emit('showSetting');
    },
    /** END */
  },
};
</script>
<style lang="scss" scoped>
.flex {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
.top-right-btn {
  margin-bottom: 10px;
}
// ::v-deep .el-checkbox {
//   margin-bottom: 10px;
// }
// ::v-deep .el-checkbox__label {
//   min-width: 150px;
// }
@media (max-width: 550px) {
  .flex {
    display: block;
  }
}
</style>
