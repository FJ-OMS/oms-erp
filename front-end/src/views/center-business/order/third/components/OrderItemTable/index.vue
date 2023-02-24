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
        <el-table-column label="商品编码" prop="spuCode" align="center" />
        <!-- <el-table-column label="SKU名称" prop="skuName" align="center" /> -->
        <el-table-column label="SKU编码" prop="skuCode" align="center" />
        <el-table-column label="数量" prop="quantity" align="center" width="120">
          <template slot-scope="scope">
            <el-input-number
              v-model="scope.row.quantity"
              :maxlength="MAX_LENGTH.DEFAULT"
              class="full-width"
              :min="0"
              :precision="0"
              :controls="false"
              @blur="onBlurQuantity(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="是否赠品" prop="giftFlag" align="center" width="120">
          <template slot-scope="scope" align="center">
            <BjSelectLov
              v-model="scope.row.giftFlag"
              :option-list="SKU_GIFT_TYPE_LIST_FOR_CREATE"
              :clearable="false"
              @change="onChangeGiftFlag(scope.row)"
            />
          </template>
        </el-table-column>
        <!--  salesPrice->outerSingleSaleAmount  -->
        <el-table-column label="三方平台销售单价" prop="outerSingleSaleAmount" align="center">
          <template slot-scope="scope">
            <BjInputMoney
              v-model="scope.row.outerSingleSaleAmount"
              :disabled="scope.row.giftFlag == 1"
            />
          </template>
        </el-table-column>
        <el-table-column label="三方平台销售总价" prop="outerSaleAmount" align="center" width="160">
          <template slot-scope="scope">
            <BjInputMoney v-model="scope.row.outerSaleAmount" :disabled="scope.row.giftFlag == 1" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="left" fixed="right" width="120">
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
  name: 'BusinessOrderThirdOrderItemTable',
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
      expandTableSelectedObj: null,
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
    onChangeGiftFlag(row) {
      this.$nextTick(() => {
        if (row.giftFlag == 1) {
          row.outerSingleSaleAmount = '0.00';
          row.outerSaleAmount = '0.00';
          row.transPrice = '0.00';
        } else {
          this.onBlurQuantity(row);
        }
      });
    },
    onBlurQuantity(row) {
      console.warn('onBlurQuantity', row);
      row.outerSaleAmount = this.$number2money(
        (row.outerSingleSaleAmount || 0) * (row.quantity || 0),
      );
      row.innerSaleAmount = this.$number2money(
        (row.innerSingleSaleAmount || 0) * (row.quantity || 0),
      );
      row.costAmount = this.$number2money((row.costPrice || 0) * (row.quantity || 0));
    },
    /** * */
    resetDataFn(data) {
      return data.map(e => {
        return { BJ_HAS_CHILD: true, BJ_PARENT_ID: null, ...e };
      });
    },
    onSkuLineLog() {},
  },
};
</script>
