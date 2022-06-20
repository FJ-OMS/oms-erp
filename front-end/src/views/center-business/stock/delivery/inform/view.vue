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
          <el-form-item label="出库通知单号">
            {{ data.code || noDataText }}
            <BjCopyIcon class="ml10" :copy="data.code" />
          </el-form-item>
          <el-form-item label="出库逻辑仓编码">
            {{ data.outLogicWarehouseCode || noDataText }}
          </el-form-item>
          <el-form-item label="出库逻辑仓名称">
            {{ data.outLogicWarehouseName || noDataText }}
          </el-form-item>
          <el-form-item label="出库类型">
            {{ data.outTypeMeaning || noDataText }}
          </el-form-item>
          <el-form-item label="出库状态">
            {{ ORDER_STOCK_OUT_TYPE_LIST.bjGet(data.status) || noDataText }}
          </el-form-item>
          <el-form-item label="买家备注">
            {{ data.buyMsg || noDataText }}
          </el-form-item>
          <el-form-item label="商家备注">
            {{ data.orderMsg || noDataText }}
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="12">
        <el-form label-width="130px">
          <el-form-item label="物流">
            {{ data.logisticCompany || noDataText }}
          </el-form-item>
          <el-form-item label="物流业务">
            {{ data.logisticBusiness || noDataText }}
          </el-form-item>
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
        <el-form-item label="前置单号">
          <span @click="onView(0)">{{ data.frontRecordCode }}</span>
          <BjCopyIcon class="ml10" :copy="data.frontRecordCode" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="内部订单号">
          <span class="link" @click="$toBusinessOrderInternalView(data.orderCode)">{{
            data.orderCode
          }}</span>
          <BjCopyIcon class="ml10" :copy="data.orderCode" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="三方订单号">
          <span class="link" @click="$toBusinessOrderThirdView(data.platformOrderCode)">{{
            data.platformOrderCode
          }}</span>
          <BjCopyIcon class="ml10" :copy="data.platformOrderCode" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="出库结果单号">
          <span class="link">
            <span v-for="(item, index) in data.resultCodeAndIdList" :key="index">
              <span @click="$toBusinessStockDeliveryResultView(item.id)">{{
                index > 1 ? '' : item.code
              }}</span>
              <span v-if="data && data.resultCodeAndIdList.length > 1">{{
                index === 0 ? '，' : ''
              }}</span>
            </span>
          </span>
          <!--
            ① 超过两个结果单用...显示，并且点击是弹窗到 【查看出库结果单号】
          -->
          <el-button
            v-if="data && data.resultCodeAndIdList.length > 2"
            type="text"
            class="ml10"
            size="small"
            @click="onMore"
            >查看更多</el-button
          >
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
        <el-table-column label="发货数量" prop="plannedQty" align="center" />
        <el-table-column label="已发货数量" prop="alreadyOutQty" align="center" />
        <el-table-column label="商品名称" prop="spuName" align="center" />
      </template>
      <template #paginationLeft>
        <span>发货总数量：{{ qtyData.plannedQty }} </span
        ><span class="ml20">已发货数量：{{ qtyData.alreadyOutQty }}</span>
      </template>
    </BjPage>
    <tableDialog ref="codeTable" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';
import { ORDER_STOCK_OUT_TYPE_LIST } from '@/utils/constants';
import tableDialog from '../components/tableDialog.vue';
export default {
  name: 'BusinessStockDeliveryInformView',
  components: { tableDialog },
  mixins: [pageMiXin, viewMiXin],
  props: {},
  data() {
    return {
      ORDER_STOCK_OUT_TYPE_LIST,
      queryApi: stockApi.getOutboundDeliveryDetailPage,
      queryParams: {
        odoId: this.$route.query.id,
      },
      colSpan: 12,
      id: null,
      data: {
        resultCodeAndIdList: [],
      },
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
        .getOutboundDeliveryDetail({ id })
        .then(res => {
          // if (res.resultCode) {
          //   res.resultCode = res.resultCode.split(',');
          // } else {
          //   res.resultCode = [];
          // }
          this.data = res;
        })
        .catch(err => {
          console.log(err);
        });
      stockApi
        .getOutboundDeliveryDetailStats(id)
        .then(res => {
          this.qtyData = res;
        })
        .catch(err => {
          console.log(err);
        });
    },
    onView(type) {
      switch (type) {
        case 0:
          // TODO: 这里需要以后过来接，现在没数据
          // this.msgSuccess('跳转前置页面');
          break;
        case 1:
          this.$toBusinessOrderInternalView(this.data.orderCode);
          break;
        case 2:
          // TODO: 这里需要以后过来接，现在没数据
          this.$toBusinessOrderThirdView(this.data.platformOrderCode);
          break;
      }
    },
    handleClose() {
      this.cancel();
    },
    /** 查看更多 */
    onMore() {
      this.$refs.codeTable.show(this.data.resultCodeAndIdList);
    },
  },
};
</script>
<style lang="scss" scoped>
.el-form-item--mini.el-form-item {
  margin-bottom: 5px;
}
</style>
