<!--
 * @Author: your name
 * @Date: 2021-10-06 10:24:28
 * @LastEditTime: 2021-10-06 17:19:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\stock\abnormal\order\index.vue
-->
<template>
  <div class="app-container">
    <BjRouteTabs :route-list="STOCK_ABNORMAL_ROUTE_LIST"> </BjRouteTabs>
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'100px'"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :before-reset-search="beforeResetSearch"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="商品编码" prop="code">
            <el-input
              v-model.trim="queryParams.code"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品名称" prop="spuName">
            <el-input
              v-model.trim="queryParams.spuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="SKU编码" prop="speId">
            <el-input
              v-model.trim="queryParams.speId"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="三方订单号" prop="orderSn">
            <BjInputCode
              v-model.trim="queryParams.orderSn"
              placeholder="三方订单号/内部订单号"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="订单类型" prop="orderType">
            <BjSelectLov v-model="queryParams.orderType" :option-list="ORDER_BUSINESS_TYPE_LIST" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="店铺名称" prop="shopName">
            <el-input
              v-model.trim="queryParams.shopName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="渠道类型" prop="channelTypeCode">
            <BjSelectLov v-model="queryParams.channelTypeCode" lov-code="SCHL.CHANNEL_TYPE" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="status">
            <BjSelectLov
              v-model="queryParams.status"
              :option-list="STOCK_ABNORMAL_ORDER_STATUS_LIST"
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary">导 出</el-button>
        </el-col>
      </template>
      <template v-slot:tableColumn>
        <template v-for="(col, index) in dropCols">
          <el-table-column
            v-if="col.prop !== 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || false"
            align="center"
            class-name="item"
            :show-overflow-tooltip="col.prop !== 'bjTableAction'"
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <el-table-column
            v-if="col.prop === 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || 'right'"
            align="center"
          >
            <template slot-scope="props">
              <el-button v-if="!props.row.releaseStatus" type="text" @click="onPurchase(props.row)"
                >采 购</el-button
              >
              <el-button v-if="!props.row.releaseStatus" type="text" @click="onAllot(props.row)"
                >调 拨</el-button
              >
              <el-button v-if="!props.row.releaseStatus" type="text" @click="onMove(props.row)"
                >移 仓</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <Purchase ref="purchase" :status="0" />
    <Allot ref="allot" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
// import { goodsApi } from '@/api';
/**  */
import Purchase from '../components/purchase';
import Allot from '../components/allot';
import {
  STOCK_ABNORMAL_ROUTE_LIST,
  ORDER_BUSINESS_TYPE_LIST,
  STOCK_ABNORMAL_ORDER_STATUS_LIST,
} from '@/utils/constants';
// const getDefaultForm = () => {
//   return {};
// };
export default {
  name: 'BusinessAfterAudit',
  components: { Purchase, Allot },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 6,
      selection: false,
      /** */
      STOCK_ABNORMAL_ROUTE_LIST,
      ORDER_BUSINESS_TYPE_LIST,
      STOCK_ABNORMAL_ORDER_STATUS_LIST,
      /** */
      queryApi: null, // settingApi.getStockProcess,
      // 查询参数
      queryParams: {
        code: null,
        spuName: null,
        speId: null,
        orderSn: null,
        orderType: null,
        shopName: null,
        channelTypeCode: null,
        status: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '商品编码', prop: 'code', isCheck: true, disabled: false },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: '商品分类', prop: '', isCheck: true, disabled: false },
        { label: 'SKU编码（商品SKU编码）', prop: '', isCheck: true, disabled: false },
        { label: '三方订单单号', prop: '', isCheck: true, disabled: false },
        { label: '订单类型', prop: '', isCheck: true, disabled: false },
        { label: '订单类型', prop: '', isCheck: true, disabled: false },
        { label: '内部订单号', prop: '', isCheck: true, disabled: false },
        { label: '店铺名称', prop: '', isCheck: true, disabled: false },
        { label: '渠道类型', prop: '', isCheck: true, disabled: false },
        { label: '异常数量', prop: '', isCheck: true, disabled: false },
        { label: '状态', prop: '', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.$refs.BjPage.tableData = [
      {
        code: '6935898044253',
        spuName: '智能坐便器',
      },
    ];
  },
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 采购 */
    onPurchase() {
      this.$refs.purchase.show();
    },
    /** 调拨 */
    onAllot() {
      this.$refs.allot.show(0, '订单缺货-调拨');
    },
    /** 调拨 */
    onMove() {
      this.$refs.allot.show(1, '订单缺货-移仓');
    },
  },
};
</script>
<style lang="scss" scoped></style>
