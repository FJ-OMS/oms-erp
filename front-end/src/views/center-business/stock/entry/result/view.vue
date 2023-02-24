<template>
  <div class="app-container bj-label-colon">
    <BjDivider right-fixed
      >基本信息
      <template slot="right">
        <el-button @click="handleClose">返 回</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="reFresh">刷新</el-button>
      </template>
    </BjDivider>
    <el-row>
      <el-col :span="12">
        <el-form label-width="130px">
          <el-form-item label="入库结果单号">
            {{ data.code || noDataText }}
            <BjCopyIcon class="ml10" :copy="data.code" />
          </el-form-item>
          <el-form-item label="入库逻辑仓编码">
            {{ data.receivingLogicWarehouseCode || noDataText }}
          </el-form-item>
          <el-form-item label="入库逻辑仓名称">
            {{ data.receivingLogicWarehouseName || noDataText }}
          </el-form-item>
          <el-form-item label="入库类型">
            {{ data.inputTypeMeaning || noDataText }}
          </el-form-item>
          <el-form-item label="入库状态">
            {{ data.statusMeaning || noDataText }}
          </el-form-item>
          <!-- <el-form-item label="买家备注">
            {{ data.buyMsg || noDataText }}
          </el-form-item>
          <el-form-item label="商家备注">
            {{ data.orderMsg || noDataText }}
          </el-form-item> -->
        </el-form>
      </el-col>
      <el-col :span="12">
        <el-form label-width="130px">
          <el-form-item label="退货人">{{ data.returnsName || noDataText }} </el-form-item>
          <el-form-item label="退货人手机号">{{ data.returnsMobile | phoneFilter }} </el-form-item>
          <el-form-item label="退货人地址">{{ cptAddress }} </el-form-item>
          <el-form-item label="创建时间">
            {{ data.creationDate || noDataText }}
          </el-form-item>
          <!-- <el-form-item label="创建人">
            {{ data.createdName || noDataText }}
          </el-form-item> -->
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <BjDivider>关联单据</BjDivider>
      <el-form label-width="130px" inline>
        <el-col :span="8">
          <el-form-item label="入库通知单号">
            <span
              class="link"
              @click="$toBusinessStockEntryInformView(data.warehouseWarrantCode)"
              >{{ data.warehouseWarrantCode }}</span
            >
            <BjCopyIcon class="ml10" :copy="data.warehouseWarrantCode" />
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
      </el-form>
    </el-row>
    <BjDivider>商品信息</BjDivider>
    <!-- TODO:商品明细应该只有一条 by skyer-ebc-325 -->
    <BjPage
      ref="BjPage"
      :col-span="colSpan"
      :get-action="queryApi"
      :query-params="queryParams"
      :auto-fetch="false"
      :serial="serial"
      :selection="false"
      :stripe="false"
      search-btn-hide
      right-toolbar-hide
      row-key="id"
      no-auto-height
      @handleSelectionChange="handleSelectionChange"
    >
      <template #tableColumn>
        <el-table-column label="商品编码" prop="spuCode" align="center" />
        <el-table-column label="SKU名称" prop="skuName" align="center" />
        <el-table-column label="SKU编码" prop="skuCode" align="center" />
        <el-table-column label="已入库数量" prop="alreadyQty" align="center" />
        <el-table-column label="商品名称" prop="spuName" align="center" />
      </template>
      <template #paginationLeft>
        <span class="ml20">已入库数量：{{ data.alreadyQty }}</span>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';
export default {
  name: 'BusinessStockEntryResultView',
  components: {},
  mixins: [pageMiXin, viewMiXin],
  props: {},
  data() {
    return {
      queryApi: stockApi.getEntryResultDetailGoods,
      queryParams: {
        warehouseResultId: null,
      },
      colSpan: 12,
      id: null,
      data: {
        alreadyTotalQty: null,
        channelTypeCode: null,
        channelTypeCodeMeaning: null,
        city: null,
        code: null,
        country: null,
        creationDate: null,
        district: null,
        frontRecordCode: null,
        frontRecordId: null,
        frontRecordType: null,
        id: null,
        inputType: null,
        inputTypeMeaning: null,
        logisticsList: [
          // {
          //   logisticsCompanyName: null,
          //   logisticsNo: null,
          // },
        ],
        orderCode: null,
        platformOrderCode: null,
        province: null,
        receivingLogicWarehouseCode: null,
        receivingLogicWarehouseName: null,
        returnsAddress: null,
        returnsMobile: null,
        returnsName: null,
        returnsTel: null,
        status: null,
        statusMeaning: null,
        storeName: null,
        totalQty: null,
      },
      noDataText: '暂无',
    };
  },
  computed: {
    cptAddress() {
      let text = '';
      text += this.data.province || '';
      text += this.data.city || '';
      text += this.data.district || '';
      text += this.data.returnsAddress || '';
      return text;
    },
  },
  created() {
    this.reFresh();
  },
  mounted() {},
  methods: {
    reFresh() {
      stockApi
        .detailEntryResult({ id: this.$route.query.id, code: this.$route.query.code })
        .then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.data = Object.assign({}, this.data, res || {});
            if (this.data.id) {
              this.queryParams.warehouseResultId = this.data.id;
              this.$refs.BjPage.fetchData();
            }
          }
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
      this.$refs.codeTable.show(this.data.logisticsList);
    },
  },
};
</script>
<style lang="scss" scoped>
.el-form-item--mini.el-form-item {
  margin-bottom: 5px;
}
</style>
