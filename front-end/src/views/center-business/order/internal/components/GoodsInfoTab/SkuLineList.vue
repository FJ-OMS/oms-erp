<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :stripe="false"
      :selection="hasBtn && selection"
      right-toolbar-hide
      @handleSelectionChange="handleSelectionChange"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="收货状态" prop="receiveStatus">
            <BjSelectLov
              v-model="queryParams.receiveStatus"
              constant-key="ORDER_INTERNAL_LINE_RECEIVING_STATUS_LIST"
            />
          </el-form-item>
        </el-col>
        <template v-if="businessType == ORDER_BUSINESS_TYPES.COMMON">
          <el-col :span="colSpan">
            <el-form-item label="发货状态" prop="deliveryStatus">
              <BjSelectLov
                v-model="queryParams.deliveryStatus"
                constant-key="ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST"
              />
            </el-form-item>
          </el-col>
          <el-col :span="colSpan">
            <el-form-item label="挂起状态" prop="suspendStatus">
              <BjSelectLov
                v-model="queryParams.suspendStatus"
                constant-key="ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST"
              />
            </el-form-item>
          </el-col>
          <el-col :span="colSpan">
            <el-form-item label="发货回写状态" prop="syncDeliveryStatus">
              <BjSelectLov
                v-model="queryParams.syncDeliveryStatus"
                constant-key="ORDER_INTERNAL_LINE_STOCK_BACK_STATUS_LIST"
              />
            </el-form-item> </el-col
        ></template>
      </template>
      <template v-slot:tableColumn>
        <el-table-column prop="tags" label="标签" show-overflow-tooltip width="160">
          <template slot-scope="scope">
            <BjTagList :tag-code-string="scope.row.tags" />
          </template>
        </el-table-column>
        <el-table-column prop="spuName" label="商品名称" show-overflow-tooltip width="160" />
        <el-table-column prop="spuCode" label="商品编码" width="160"> </el-table-column>
        <el-table-column prop="skuCode" label="SKU编码" width="160"> </el-table-column>
        <el-table-column prop="giftFlag" label="是否赠品" :width="colWidth.giftFlag">
          <template slot-scope="scope">
            <el-tag :type="scope.row.giftFlag ? 'success' : 'info'">{{
              STATUS_YES_NUM.bjGet(!!scope.row.giftFlag)
            }}</el-tag>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="refundFlag" label="是否退款" width="50">
          <template slot-scope="scope">
            <el-tag :type="scope.row.refundFlag ? 'success' : 'info'">{{
              STATUS_YES_NUM.bjGet(!!scope.row.refundFlag)
            }}</el-tag>
          </template>
        </el-table-column> -->
        <el-table-column prop="quantity" label="数量" width="60"> </el-table-column>
        <el-table-column prop="thirdSaleAmount" label="三方平台销售单价" :width="colWidth.price"
          ><template slot-scope="scope">
            {{ $number2money(scope.row.thirdSaleAmount) }}
          </template></el-table-column
        >
        <el-table-column prop="outBalancePrice" label="三方平台均摊总价" :width="colWidth.price"
          ><template slot-scope="scope">
            {{ $number2money(scope.row.outBalancePrice) }}
          </template></el-table-column
        >
        <el-table-column prop="innerSalePrice" label="内部销售价" :width="colWidth.price"
          ><template slot-scope="scope">
            {{ $number2money(scope.row.innerSalePrice) }}
          </template></el-table-column
        >
        <el-table-column prop="innerBalancePrice" label="内部均摊总价" :width="colWidth.price"
          ><template slot-scope="scope">
            {{ $number2money(scope.row.innerBalancePrice) }}
          </template></el-table-column
        >
        <el-table-column prop="costAmount" label="成本单价" :width="colWidth.price"
          ><template slot-scope="scope">
            {{ $number2money(scope.row.costAmount) }}
          </template></el-table-column
        >
        <el-table-column prop="totalCostAmount" label="成本总价" :width="colWidth.price"
          ><template slot-scope="scope">
            {{ $number2money(scope.row.totalCostAmount) }}
          </template></el-table-column
        ><el-table-column prop="transPrice" label="运费" :width="colWidth.price"
          ><template slot-scope="scope">
            {{ $number2money(scope.row.transPrice) }}
          </template></el-table-column
        >
        <template v-if="businessType == ORDER_BUSINESS_TYPES.COMMON">
          <el-table-column prop="deliveryStatus" label="发货状态" width="70"
            ><template slot-scope="scope">
              {{ ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST.bjGet(scope.row.deliveryStatus) }}
            </template></el-table-column
          ></template
        >
        <el-table-column prop="receivingStatus" label="收货状态" :width="colWidth.receivingStatus"
          ><template slot-scope="scope">
            {{ ORDER_INTERNAL_LINE_RECEIVING_STATUS_LIST.bjGet(scope.row.receivingStatus) }}
          </template></el-table-column
        >
        <template v-if="businessType == ORDER_BUSINESS_TYPES.COMMON">
          <el-table-column prop="suspendStatus" label="挂起" width="70"
            ><template slot-scope="scope">
              {{ ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST.bjGet(scope.row.suspendStatus) }}
            </template></el-table-column
          >
          <el-table-column prop="syncDeliveryStatus" label="发货回写状态" width="70"
            ><template slot-scope="scope">
              {{ ORDER_INTERNAL_LINE_STOCK_BACK_STATUS_LIST.bjGet(scope.row.syncDeliveryStatus) }}
            </template></el-table-column
          ></template
        >
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { orderApi } from '@/api';
import {
  STATUS_YES_NUM,
  ORDER_INTERNAL_ORDER_STATUS_LIST,
  ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
  ORDER_INTERNAL_LINE_RECEIVING_STATUS_LIST,
  ORDER_INTERNAL_LINE_STOCK_BACK_STATUS_LIST,
  ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST,
  ORDER_INTERNAL_REFUND_STATUS_LIST,
  ORDER_BUSINESS_TYPES,
} from '@/utils/constants';
export default {
  name: 'BusinessOrderInternalViewSkuLineList',
  components: {},
  mixins: [pageMiXin],
  props: {
    orderId: {
      type: String,
      default: null,
    },
    hasBtn: {
      type: Boolean,
      default: true,
    },
    businessType: {
      type: String,
      default: ORDER_BUSINESS_TYPES.COMMON,
    },
  },
  data() {
    return {
      sortTableByOptionName: true,
      STATUS_YES_NUM,
      ORDER_INTERNAL_ORDER_STATUS_LIST,
      ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
      ORDER_INTERNAL_LINE_RECEIVING_STATUS_LIST,
      ORDER_INTERNAL_LINE_STOCK_BACK_STATUS_LIST,
      ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST,
      ORDER_INTERNAL_REFUND_STATUS_LIST,
      ORDER_BUSINESS_TYPES,
      queryApi: orderApi.getInternalSkuLineListByOrder,
      // 查询参数
      queryParams: {
        deliveryStatus: null,
        // isOutStock: null,
        refundFlag: null,
        orderId: null,
        preemptionStatus: null,
        receiveStatus: null,
        sourceStatus: null,
        suspendStatus: null,
        syncDeliveryStatus: null,
        syncPreempStatus: null,
      },
      labelsVersion: 11,
      tableSortableDisabled: true,
      selectedRows: null,
    };
  },
  computed: {
    colWidth() {
      const O2O = this.businessType == ORDER_BUSINESS_TYPES.O2O;
      return {
        price: O2O ? '128' : '74',
        receivingStatus: O2O ? 'auto' : '70',
        giftFlag: O2O ? '100' : '50',
      };
    },
  },
  watch: {},
  created() {
    this.queryParams.orderId = this.orderId;
  },
  methods: {},
};
</script>
