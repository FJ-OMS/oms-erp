<template>
  <div>
    <BjPage
      ref="BjPage"
      :auto-fetch="false"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :stripe="false"
      :selection="selection"
      right-toolbar-hide
      search-btn-hide
      @handleSelectionChange="handleSelectionChange"
    >
      <template v-slot:baseForm> </template>
      <template v-slot:tableColumn>
        <el-table-column prop="outLogicWarehouseName" label="发货仓库" align="center" />
        <el-table-column prop="code" label="出库通知单单号" align="center">
          <template slot-scope="scope">
            <span class="link" @click="$toBusinessStockDeliveryInformView(scope.row.id)">{{
              scope.row.code
            }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="resultCode" label="出库结果单单号" align="center">
          <template slot-scope="scope">
            <span
              v-for="item in scope.row.resultCodeAndIdList"
              :key="item.id"
              class="ml5 link"
              @click="$toBusinessStockDeliveryResultView(item.id)"
              >{{ item.code }}</span
            >
          </template>
        </el-table-column>
        <el-table-column prop="logisticCompany" label="指定发货物流" align="center" />
        <el-table-column prop="status" label="出库状态" align="center">
          <template slot-scope="scope">
            {{ ORDER_STOCK_OUT_TYPE_LIST.bjGet(scope.row.status) }}
          </template>
        </el-table-column>
        <el-table-column prop="qty" label="商品数" align="center" />
        <el-table-column prop="alreadyQty" label="已出库数量" align="center" />
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { stockApi } from '@/api';
import { ORDER_STOCK_OUT_TYPE_LIST } from '@/utils/constants';

export default {
  name: '',
  components: {},
  mixins: [pageMiXin],
  inject: ['getInfo'],
  data() {
    return {
      ORDER_STOCK_OUT_TYPE_LIST,
      selection: false,
      queryApi: stockApi.getOutboundDelivery,
      // 查询参数
      queryParams: {
        orderId: null,
      },
      selectedRow: null,
    };
  },
  computed: {
    orderId() {
      return this.info.orderInfoVO.id;
    },
    info() {
      return this.getInfo();
    },
  },
  watch: {
    orderId(v) {
      if (v) {
        this.queryParams.orderId = v;
        this.reFresh();
      }
    },
  },
  created() {},
  methods: {},
};
</script>
