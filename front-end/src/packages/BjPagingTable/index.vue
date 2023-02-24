<template>
  <div class="bj-paging-table">
    <el-table
      ref="multipleTable"
      :loading="loading"
      :data="tableData"
      stripe
      border
      size="mini"
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
    >
      <slot name="tableColumn" />
    </el-table>

    <div v-if="pagination" class="pagination">
      <el-pagination
        :background="background"
        :current-page.sync="pagingInfo.page"
        :page-size.sync="pagingInfo.size"
        :layout="layout"
        :page-sizes="pageSizes"
        :total="pagingInfo.total"
      />
    </div>
  </div>
</template>
<script>
import { PAGE_SIZES } from '@/utils/constants';
export default {
  name: 'BjPagingTable',
  props: {
    api: Function,
    addTableData: {
      type: Array,
      default: () => {
        return [];
      },
    },
    autoFetch: {
      type: Boolean,
      default: true,
    },

    // 默认传参
    defaultParams: {
      type: Object,
      default() {
        return {};
      },
    },

    /** ***** 值处理  *********** */
    // 返回值res所在key
    res: {
      type: String,
      default: '',
    },
    isList: {
      type: Boolean,
      default: false,
    },
    resetData: Function,
    // 参与排序的key
    sort: {
      type: String,
      default: '',
    },
    // 排序-反序
    reverse: {
      type: Boolean,
      default: false,
    },
    /** ***** 分页  *********** */
    background: {
      type: Boolean,
      default: true,
    },
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper',
    },
    pageSizes: {
      type: Array,
      default() {
        return PAGE_SIZES;
      },
    },
    beforeModifyPagingInfo: Function,
    autoScroll: {
      type: Boolean,
      default: true,
    },
    pagination: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      tableData: [],
      loading: true,
      autoTableHeight: 480,
      pagingInfo: {
        // 分页信息
        size: 10,
        page: 1,
        total: 0,
      },
      oldSize: 10,
      oldPage: 1,
    };
  },
  watch: {
    'pagingInfo.size': function (v, o) {
      if (this.oldSize != v) {
        this.handleSizeChange(v, o);
      }
    },
    'pagingInfo.page': function (v, o) {
      if (this.oldPage != v) {
        this.handleCurrentChange(v, o);
      }
    },
  },
  mounted() {
    if (this.autoFetch) {
      this.fetchData();
    }
  },
  methods: {
    setTableData(value) {
      this.tableData = [].concat(value);
    },
    pushTableData(value) {
      if (Array.isArray(value)) {
        this.tableData.unshift(...value);
      } else {
        this.tableData.unshift(value);
      }
    },
    deleteTableData(row) {
      const index = this.tableData.findIndex(e => row.skuCode == e.skuCode);

      if (index != -1) this.tableData.splice(index, 1);
    },
    async handleSizeChange(val, o) {
      if (typeof this.beforeModifyPagingInfo == 'function') {
        const flag = await this.beforeModifyPagingInfo();
        if (!flag) {
          this.oldSize = o;
          this.pagingInfo.size = o;
          return false;
        }
      }
      this.pagingInfo.size = val;
      this.oldSize = null;
      this.fetchData();
      if (this.autoScroll) {
        scrollTo(0, 800);
      }
    },
    async handleCurrentChange(val, o) {
      if (typeof this.beforeModifyPagingInfo == 'function') {
        const flag = await this.beforeModifyPagingInfo();
        if (!flag) {
          this.oldPage = o;
          this.pagingInfo.page = o;
          return;
        }
      }
      this.pagingInfo.page = val;
      this.oldPage = null;
      this.fetchData();
      if (this.autoScroll) {
        scrollTo(0, 800);
      }
    },

    getQueryParams() {
      const conditionObj = {};
      // 基础搜索
      for (const key in this.defaultParams) {
        if (Object.prototype.hasOwnProperty.call(this.defaultParams, key)) {
          const value = this.defaultParams[key];
          if (
            key == 'tenantId' ||
            (value !== undefined && value != null && value !== '' && value.length !== 0)
          ) {
            conditionObj[key] = this.defaultParams[key];
          }
        }
      }
      console.log(this.defaultParams);
      return conditionObj;
    },
    /**
     * pageIndex
     */
    fetchData(pageIndex) {
      // 清空数据
      this.tableData = [];
      this.pagingInfo.page = pageIndex || this.pagingInfo.page || 1;

      if (!this.api) {
        this.loading = false;
        this.$message.error('获取数据失败，因为对应的api没有设置');
        return;
      }
      this.loading = true;

      /** TODO:临时处理 loadingHide=true; 不使用api的全局loading，使用组件内部的表格loading */
      let conditionObj = Object.assign({ BjAjaxLoadingHide: true }, this.getQueryParams());
      if (!this.isList) {
        /** TODO:临时处理 pageSize currentPage入参 */
        const PAGE_TEMP = {
          pageSize: this.pagingInfo.size,
          currentPage: this.pagingInfo.page - 1,
        };
        /** 合并条件 */
        conditionObj = Object.assign(conditionObj, this.pagingInfo, PAGE_TEMP);
      }
      this.doAction(conditionObj);
    },
    // 请求数据
    doAction(conditionObj) {
      this.api(conditionObj)
        .then(res => {
          this.$emit('afterFetchData', res);
          this.loading = false;
          // 不是规范格式的返回值处理
          if (this.res) {
            const RES_LIST = this.res.split('/');
            RES_LIST.forEach(key => {
              res = res[key] || res;
            });
          }

          // 分页模式
          if (!this.isList && res && res.content && Array.isArray(res.content)) {
            this.tableData = res.content;
            this.pagingInfo.total = res.totalElements || res.content.length;
          }
          // List值集模式
          if (this.isList && res && Array.isArray(res)) {
            this.tableData = res;
            this.pagingInfo.total = 0;
          }

          if (typeof this.resetData == 'function') {
            this.tableData = this.resetData(this.tableData);
          }

          if (this.sort) {
            const n = this.reverse ? -1 : 1;
            this.tableData = this.tableData.sort((a, b) => {
              return (a[this.sort] - b[this.sort]) * n;
            });
          }
        })
        .catch(error => {
          if (error.message) this.$message.error(`获取数据列表失败 ${error.message || ''}`);
          this.loading = false;
        });
    },
  },
};
</script>
<style scoped>
.pagination {
  display: flex;
  justify-content: flex-end;
  padding-top: 16px;
}
</style>
