<template>
  <div class="app-container">
    <BjRouteTabs />
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
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
          <el-form-item label="单号" prop="orderNo">
            <BjInputCode
              v-model.trim="queryParams.orderNo"
              placeholder="退款单号/销售单号"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="退货物流单号" prop="logisticsNo">
            <BjInputCode
              v-model.trim="queryParams.logisticsNo"
              placeholder="请输入"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="退货单来源" prop="actionType">
            <BjSelectLov v-model="queryParams.actionType" :option-list="ORDER_ACTION_TYPE_LIST" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="买家昵称" prop="buyerNick">
            <el-input
              v-model.trim="queryParams.buyerNick"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <BjSelectChannelStore
          :span="colSpan"
          :query-params="queryParams"
          :query-props="['channelTypeCode', 'storeIdList']"
          multiple
        />
        <el-col :span="colSpan">
          <el-form-item label="退货人" prop="receiver">
            <el-input
              v-model.trim="queryParams.receiver"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="status">
            <BjSelectLov
              v-model="queryParams.status"
              :option-list="AFTER_RETURN_TRADE_STATUS"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="三方退货时间">
            <el-date-picker
              v-model="queryParamsTime_pay"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              :picker-options="$pickerOptions()"
              align="right"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']"
          /></el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <div>
          <el-col :span="1.5">
            <el-button
              v-permission:BusinessAfterInternalReturnAdd
              type="primary"
              class="main-btn"
              @click="onAdd"
              >新增退货退款单</el-button
            >
          </el-col>
        </div>
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

              <template v-else-if="col.prop === 'logisticsInfo'">
                <!--         // 查看退货物流信息 按钮 -->
                <span class="link" @click="onViewLogistics(scope.row)">查看</span>
              </template>

              <template v-else-if="col.prop === 'outerRefundNo'">
                <span
                  class="link"
                  @click="$toBusinessAfterThirdReturnView(scope.row.outerRefundNo)"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'innerRefundNo'">
                <span
                  class="link"
                  @click="$toBusinessAfterInternalReturnView(scope.row[col.prop])"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'outerOrderNo'">
                <span class="link" @click="$toBusinessOrderThirdView(scope.row.outerOrderNo)">{{
                  scope.row[col.prop]
                }}</span>
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'innerOrderNo'">
                <span class="link" @click="$toBusinessOrderInternalView(scope.row.innerOrderNo)">{{
                  scope.row[col.prop]
                }}</span>
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'actionType'">
                {{ ORDER_ACTION_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'orderRefundType'">
                {{ INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'refundType'">
                {{ INTERNAL_AFTER_RETURN_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'payType'">
                {{ AFTER_INTERNAL_RETURN_PAY_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'reason'">
                {{ BJ_AFTER_REFUND_GOODS_REASONS.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'status'">
                {{ AFTER_RETURN_TRADE_STATUS.bjGet(scope.row[col.prop]) }}
              </template>
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column
            v-if="col.prop === 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || 'right'"
            align="center"
          >
            <template slot-scope="props">
              <el-button v-if="!props.row.releaseStatus" type="text"  class='delete-btn'>删除</el-button>
            </template>
          </el-table-column> -->
        </template>
      </template>
    </BjPage>
    <!-- 查看退货物流信息 -->
    <LogisticsListDialog ref="LogisticsListDialog" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { afterApi } from '@/api';
import LogisticsListDialog from '@/views/center-business/after/components/LogisticsListDialog.vue';
import {
  AFTER_RETURN_TRADE_STATUS,
  ORDER_ACTION_TYPE_LIST,
  INTERNAL_AFTER_RETURN_TYPE_LIST,
  AFTER_INTERNAL_RETURN_PAY_LIST,
  INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
} from '@/utils/constants';

export default {
  name: 'BusinessAfterInternalReturnIndex',
  components: { LogisticsListDialog },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 6,
      selection: false,
      AFTER_RETURN_TRADE_STATUS,
      ORDER_ACTION_TYPE_LIST,
      INTERNAL_AFTER_RETURN_TYPE_LIST,
      AFTER_INTERNAL_RETURN_PAY_LIST,
      INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
      queryApi: afterApi.pageRefundGoodsOrder,
      // 查询参数
      queryParamsTime_pay: [],
      queryParams: {
        actionType: null,
        buyerNick: null,
        channelTypeCode: null,
        storeIdList: [],
        logisticsNo: null,
        orderNo: null,
        receiver: null,
        status: null,

        startTime: null,
        endTime: null,
      },
      // 表单校验
      rules: {},
      labelsVersion: 11,
      labelList: [
        {
          label: '内部退货单号',
          prop: 'innerRefundNo',
          isCheck: true,
          disabled: false,
          width: '170',
        },
        {
          label: '三方退货单号',
          prop: 'outerRefundNo',
          isCheck: true,
          disabled: false,
          width: '180',
        },
        {
          label: '三方订单号',
          prop: 'outerOrderNo',
          isCheck: true,
          disabled: false,
          width: '180',
        },
        {
          label: '内部订单号',
          prop: 'innerOrderNo',
          isCheck: true,
          disabled: false,
          width: '170',
        },
        { label: '退货单类型', prop: 'orderRefundType', isCheck: true, disabled: false },
        { label: '退货类型', prop: 'refundType', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
        { label: '退货单来源', prop: 'actionType', isCheck: true, disabled: false },
        { label: '退货退款原因', prop: 'reason', isCheck: true, disabled: false },
        { label: '买家备注', prop: 'buyerRemarks', isCheck: true, disabled: false },
        { label: '客服备注', prop: 'serviceRemarks', isCheck: true, disabled: false },
        { label: '退货数量', prop: 'goodsQuantity', isCheck: true, disabled: false },
        { label: '应退买家合计', prop: 'refundAmount', isCheck: true, disabled: false },
        { label: '结算方式', prop: 'payType', isCheck: true, disabled: false },
        { label: '收货仓库', prop: 'receivingWarehouseName', isCheck: true, disabled: false },

        { label: '退货物流信息', prop: 'logisticsInfo', isCheck: true, disabled: false },
        { label: '买家昵称', prop: 'buyerNick', isCheck: true, disabled: false },
        { label: '退货人', prop: 'receiverName', isCheck: true, disabled: false, width: 135 },
        { label: '退货人手机号', prop: 'mobile', isCheck: true, disabled: false },
        { label: '退货人地址', prop: 'address', isCheck: true, disabled: false },
        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelTypeMeaning', isCheck: true, disabled: false },
        {
          label: '三方退货时间',
          prop: 'outReturnTime',
          isCheck: true,
          disabled: false,
          width: 135,
        },
        { label: '内部创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '退货完成时间', prop: 'completeDate', isCheck: true, disabled: false, width: 135 },
      ],
    };
  },
  computed: {
    BJ_AFTER_REFUND_GOODS_REASONS() {
      return this.$store.state.values.BJ_AFTER_REFUND_GOODS_REASONS || [];
    },
  },
  watch: {
    queryParamsTime_pay(v) {
      this.queryParams.startTime = (v || [])[0];
      this.queryParams.endTime = (v || [])[1];
    },
  },
  mounted() {
    this.$store.dispatch('values/getAfterRefundGoodsReasons');
  },
  methods: {
    onViewLogistics(row) {
      this.$refs.LogisticsListDialog.show(row.id);
    },

    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime_pay = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 新建退款单 */
    onAdd() {
      this.$toPage('BusinessAfterInternalReturnAdd');
      // this.$refs.DataForm.show();
    },
  },
};
</script>
