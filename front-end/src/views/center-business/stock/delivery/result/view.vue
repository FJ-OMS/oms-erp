<template>
  <div class="app-container bj-label-colon">
    <BjDivider right-fixed
      >出库基本信息
      <template slot="right">
        <el-button @click="handleClose">返 回</el-button>
      </template>
    </BjDivider>
    <el-row>
      <el-col :span="12">
        <el-form label-width="130px">
          <el-form-item label="出库结果单号">
            {{ data.resultCode || noDataText }}
            <BjCopyIcon class="ml10" :copy="data.resultCode" />
          </el-form-item>
          <el-form-item label="出库逻辑仓编码">
            {{ data.outLogicWarehouseCode || noDataText }}
          </el-form-item>
          <el-form-item label="出库逻辑仓名称">
            {{ data.outLogicWarehouseName || noDataText }}
          </el-form-item>
          <el-form-item label="物流">
            {{ data.logisticCompany || noDataText }}
          </el-form-item>
          <el-form-item label="物流业务">
            {{ data.logisticBusiness || noDataText }}
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="12">
        <el-form label-width="130px">
          <el-form-item label="收货人">{{ data.receiverName || noDataText }} </el-form-item>
          <el-form-item label="联系电话">{{ data.receiverMobile | phoneFilter }} </el-form-item>
          <el-form-item label="收货地址">
            {{ data.receiverProvince + data.receiverCity + data.receiverDistrict + '******' }}
          </el-form-item>
          <el-form-item label="创建时间">
            {{ data.creationDate || noDataText }}
          </el-form-item>
          <el-form-item label="创建人">
            {{ data.createdName || noDataText }}
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <BjDivider>关联单据</BjDivider>
    <el-form label-width="130px" inline>
      <el-col :span="8">
        <el-form-item label="物流单号">
          {{ data.shippingNo || noDataText }}
          <BjCopyIcon v-if="data.shippingNo" class="ml10" :copy="data.shippingNo" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="出库通知单号">
          <span
            class="link"
            @click="$toPage('BusinessStockDeliveryInformView', { id: data.odoId })"
            >{{ data.odoCode || noDataText }}</span
          >
          <BjCopyIcon v-if="data.odoCode" class="ml10" :copy="data.odoCode" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="WMS单号">
          {{ data.wmsCode || noDataText }}
          <BjCopyIcon v-if="data.wmsCode" class="ml10" :copy="data.odowmsCodeCode" />
        </el-form-item>
      </el-col>
    </el-form>
    <BjDivider>商品明细</BjDivider>
    <!-- TODO:商品明细应该只有一条 by skyer-ebc-325 -->
    <BjPage
      ref="BjPage"
      :col-span="colSpan"
      :get-action="queryApi"
      :query-params="queryParams"
      :serial="serial"
      :selection="false"
      :stripe="false"
      search-btn-hide
      right-toolbar-hide
      row-key="id"
      no-auto-height
      @handleSelectionChange="handleSelectionChange"
    >
      <template v-slot:baseForm> </template>
      <template #startBtnGroup>
        <!-- <el-button type="primary">解挂</el-button> -->
      </template>
      <template v-slot:tableColumn>
        <el-table-column label="商品编码" prop="spuCode" align="center" />
        <el-table-column label="SKU名称" prop="skuName" align="center" />
        <el-table-column label="SKU编码" prop="skuCode" align="center" />
        <el-table-column label="已发货数量" prop="realNum" align="center" />
        <el-table-column label="商品名称" prop="spuName" align="center" />
      </template>
      <template #paginationLeft>
        <span class="ml20">已发货数量：{{ qtyData.alreadyOutQty }}</span>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';
import { ORDER_STOCK_OUT_TYPE_LIST } from '@/utils/constants';
export default {
  name: 'BusinessStockDeliveryResultView',
  mixins: [pageMiXin, viewMiXin],
  props: {},
  data() {
    return {
      ORDER_STOCK_OUT_TYPE_LIST,
      queryApi: stockApi.getResultDetailPage,
      queryParams: {
        resultId: this.$route.query.id,
      },
      colSpan: 12,
      id: null,
      data: {},
      noDataText: '暂无',
      qtyData: {},
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.getData(this.id);
  },
  mounted() {},
  methods: {
    getData(id) {
      stockApi
        .getResultRecordsDetail({ id })
        .then(res => {
          this.data = res;
        })
        .catch(err => {
          console.log(err);
        });
      stockApi
        .getResultDetailStatus({ resultId: id })
        .then(res => {
          this.qtyData = res;
        })
        .catch(err => {
          console.log(err);
        });
    },
    handleClose() {
      this.cancel();
    },
  },
};
</script>
<style lang="scss" scoped>
.el-form-item--mini.el-form-item {
  margin-bottom: 5px;
}
</style>
