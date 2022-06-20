<template>
  <div :class="{ hidden: hidden }" class="pagination">
    <div class="pagination-left"><slot name="paginationLeft" /></div>
    <div class="pagination-right">
      <el-pagination
        :background="background"
        :current-page.sync="currentPage"
        :page-size.sync="pageSize"
        :layout="layout"
        :page-sizes="pageSizes"
        :total="total"
        v-bind="$attrs"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
      <div><slot name="paginationRight" /></div>
    </div>
  </div>
</template>

<script>
import { scrollTo } from '@/utils/scroll-to';
import { PAGE_SIZES } from '@/utils/constants';
export default {
  name: 'Pagination',
  props: {
    total: {
      required: true,
      type: Number,
    },
    page: {
      type: Number,
      default: 1,
    },
    limit: {
      type: Number,
      default: 20,
    },
    pageSizes: {
      type: Array,
      default() {
        return PAGE_SIZES;
      },
    },
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper',
    },
    background: {
      type: Boolean,
      default: true,
    },
    autoScroll: {
      type: Boolean,
      default: true,
    },
    hidden: {
      type: Boolean,
      default: false,
    },
    noAutoHeight: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    currentPage: {
      get() {
        return this.page;
      },
      set(val) {
        this.$emit('update:page', val);
      },
    },
    pageSize: {
      get() {
        return this.limit;
      },
      set(val) {
        this.$emit('update:limit', val);
      },
    },
  },
  methods: {
    handleSizeChange(val) {
      this.$emit('pagination', { page: this.currentPage, size: val });
      if (this.autoScroll && this.noAutoHeight) {
        scrollTo(0, 800);
      }
    },
    handleCurrentChange(val) {
      this.$emit('pagination', { page: val, size: this.pageSize });
      if (this.autoScroll && this.noAutoHeight) {
        scrollTo(0, 800);
      }
    },
  },
};
</script>

<style scoped>
.pagination-container {
  background: #fff;
  padding: 32px 16px;
}
.hidden {
  display: none;
}
.pagination {
  display: flex;
  justify-content: space-between;
  padding-top: 16px;
}
.pagination-left {
  color: #606266;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 13px;
}
.pagination-right {
  color: #606266;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
}
</style>
