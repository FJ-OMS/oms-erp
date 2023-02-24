<template>
  <div>
    <BjPage
      ref="BjPage"
      :auto-fetch="false"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      label-width="60px"
      :serial="serial"
      :stripe="false"
      :selection="selection"
      right-toolbar-hide
      @handleSelectionChange="handleSelectionChange"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="单号" prop="queryCode">
            <BjInputCode
              v-model.trim="queryParams.queryCode"
              placeholder="出库结果单号/出库通知单号"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableColumn>
        <el-table-column label="物流单号" prop="shippingNo" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.shippingNo }}</span>
          </template>
        </el-table-column>
        <el-table-column label="物流公司" prop="logisticCompany" align="center"></el-table-column>
        <el-table-column label="出库通知单单号" prop="odoCode" align="center">
          <template slot-scope="scope">
            <span class="link" @click="$toBusinessStockDeliveryInformView(scope.row.odoId)">{{
              scope.row.odoCode
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="包裹商品数" prop="alreadyOutQty" align="center"></el-table-column>
        <el-table-column label="出库时间" prop="creationDate" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <span class="link" @click="showPackageDetail(scope.row)">包裹明细</span>
            <!-- <span class="link ml10" @click="showLogistics">查看物流</span> -->
          </template>
        </el-table-column>
      </template>
    </BjPage>
    <ParcelDetails ref="parcelDetails" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { stockApi } from '@/api';
import {} from '@/utils/constants';
import ParcelDetails from './ParcelDetails.vue';

export default {
  name: '',
  components: { ParcelDetails },
  mixins: [pageMiXin],
  inject: ['getInfo'],
  data() {
    return {
      selection: false,
      queryApi: stockApi.getResultRecordsPage,
      // 查询参数
      queryParams: {
        queryCode: null,
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
  created() {
    console.log(this.getInfo());
    console.log(this.id);
  },
  methods: {
    showPackageDetail(row) {
      console.log(row);
      this.$refs.parcelDetails.show(row);
    },
  },
};
</script>
