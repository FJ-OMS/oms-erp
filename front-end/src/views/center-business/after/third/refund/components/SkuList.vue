<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :stripe="false"
      search-btn-hide
      right-toolbar-hide
      no-auto-height
      :auto-scroll="false"
      row-key="id"
      :reset-data="resetDataFn"
      @handleTableDataChange="handleTableDataChange"
      @handleSelectionChange="handleSelectionChange"
    >
      <template v-slot:tableColumn>
        <el-table-column label="商品名称" prop="spuName" align="center"></el-table-column>
        <el-table-column label="商品编码" prop="spuCode" align="center" width="160" />
        <el-table-column label="SKU名称" prop="skuName" align="center" />
        <el-table-column label="SKU编码" prop="skuCode" align="center" width="160" />

        <el-table-column label="是否赠品" prop="giftFlag" align="center" width="80">
          {
          <template slot-scope="scope" align="center">
            {{ SKU_GIFT_TYPE_LIST_FOR_CREATE.bjGet(scope.row.giftFlag) }} </template
          >}
        </el-table-column>

        <el-table-column label="三方销售单价" prop="outerSalePrice" align="center" width="160" />

        <el-table-column label="退款数量" prop="quantity" align="center" width="80" />

        <!--
TODO:
  ① 上限为关联销售单的实付金额

② 最多为（销售单的实付金额-已创建的三方退款金额累加（仅退款+退货退款）

 -->
        <el-table-column label="退款金额" prop="totalAmount" align="center" width="160">
          <template slot-scope="scope">
            <BjInputMoney v-model="scope.row.totalAmount" />
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" fixed="right" width="120">
          <template slot-scope="scope">
            <el-button type="text" @click="onDelete(scope.row)">删 除</el-button>
          </template>
        </el-table-column>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { SKU_GIFT_TYPE_LIST_FOR_CREATE } from '@/utils/constants';
export default {
  name: 'BusinessOrderInternalOrderItemTable',
  components: {},
  mixins: [pageMiXin],
  props: {
    tableData: {
      type: Array,
      default: function () {
        return [];
      },
    },
    defaultQueryParams: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      serial: false,
      selection: true,
      SKU_GIFT_TYPE_LIST_FOR_CREATE,

      // 查询参数
      queryParams: {
        id: null,
      },

      selectedRows: null,
    };
  },
  watch: {},
  created() {
    if (this.defaultQueryParams) {
      this.queryParams = { ...this.queryParams, ...this.defaultQueryParams };
    }
  },
  methods: {
    // 更新父组件数据
    handleTableDataChange(v) {
      const TEMP = this.tableData.map(e => {
        const FIND = (v || []).find(ee => ee.BJ_EBC_ID == e.BJ_EBC_ID);
        if (FIND) {
          return FIND;
        }
        return e;
      });
      this.$emit('update:tableData', TEMP);
    },
    // 主表查询API
    queryApi(queryParams) {
      const start = queryParams.currentPage * queryParams.pageSize;
      const end = queryParams.page * queryParams.pageSize;
      console.log('主表查询API', queryParams);
      return new Promise(resolve => {
        resolve({
          content: this.tableData.slice(start, end),
          totalElements: this.tableData.length, // 数据总数
        });
      });
    },
    onDelete(row) {
      this.$emit('delete', row);
    },
    handleSelectionChange(d) {
      this.$emit('selection-change', d);
    },

    /** * */
    resetDataFn(data) {
      return data.map(e => {
        return { children: [], ...e };
      });
    },
    onSkuLineLog() {},
  },
};
</script>
