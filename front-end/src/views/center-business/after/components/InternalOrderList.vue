<!--
 * @Author: your name
 * @Date: 2021-09-30 13:51:36
 * @LastEditTime: 2022-03-08 17:05:24
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\after\internal\components\shopList.vue
-->
<template>
  <div>
    <el-table ref="orderTable" :data="data" border tooltip-effect="light">
      <!-- <el-table-column label="内部订单号" prop="innerOrderNo" width="170px" align="center" />
      <el-table-column label="三方订单号" prop="outerOrderNo" width="170px" align="center" />
      <el-table-column label="订单类型" prop="businessType" width="px" align="center" />
      <el-table-column label="订单状态" prop="orderStatus" width="px" align="center">
        <template slot-scope="scope">
          {{ ORDER_INTERNAL_ORDER_STATUS_LIST.bjGet(scope.row.orderStatus) }}
        </template>
      </el-table-column>
      <el-table-column label="店铺" prop="storeName" width="160px" align="center" />
      <el-table-column label="渠道" prop="channelTypeMeaning" width="px" align="center" />
      <el-table-column label="支付状态" prop="refundStatus" width="px" align="center" />
      <el-table-column label="商品行数量" prop="goodsQuatity" width="px" align="center" />
      <el-table-column label="商品总金额" prop="totalAmount" width="120px" align="center" />
      <el-table-column label="买家应付" prop="buyerPay" width="120px" align="center" />
      <el-table-column label="应收总成本" prop="costPrice" width="120px" align="center" />
      <el-table-column label="买家实付" prop="payment" width="px" align="center" />
      <el-table-column label="发货状态" prop="deliveryStatus" width="px" align="center" />
      <el-table-column label="收货状态" prop="receiveStatus" width="px" align="center" />
      <el-table-column label="买家昵称" prop="buyerNick" width="160px" align="center" />
      <el-table-column label="收货人姓名" prop="receiverName" width="120px" align="center" />
      <el-table-column label="手机号" prop="mobile" width="100px" align="center" />
      <el-table-column
        label="收货地址"
        prop="address"
        width="160px"
        align="center"
        show-overflow-tooltip
      />
      <el-table-column label="三方下单时间" prop="orderTime" width="160px" align="center" />
      <el-table-column label="支付时间" prop="payTime" width="160px" align="center" />
      <el-table-column label="创建时间" prop="creationDate" width="160px" align="center" /> -->

      <template v-for="(col, index) in labelList">
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

            <template
              v-else-if="
                col.prop === 'totalAmount' || col.prop === 'buyerPay' || col.prop === 'costPrice'
              "
            >
              {{ $number2money(scope.row[col.prop]) }}
            </template>
            <template v-else-if="col.prop === 'payStatus'">
              <el-tag :type="scope.row[col.prop] === '1' ? 'success' : 'info'">{{
                ORDER_INTERNAL_PAYMENT_STATUS_LIST.bjGet(scope.row[col.prop])
              }}</el-tag>
            </template>
            <template v-else-if="col.prop === 'deliveryStatus'">
              <el-tag :type="scope.row[col.prop] === '30512' ? 'success' : 'info'">{{
                ORDER_INTERNAL_DELIVER_STATUS_LIST.bjGet(scope.row[col.prop])
              }}</el-tag>
            </template>
            <template v-else-if="col.prop === 'receivingStatus'">
              <el-tag :type="scope.row[col.prop] === '30522' ? 'success' : 'info'">{{
                ORDER_INTERNAL_RECEIVING_STATUS_LIST.bjGet(scope.row[col.prop])
              }}</el-tag>
            </template>

            <template v-else-if="col.prop === 'businessType'">
              {{ ORDER_BUSINESS_TYPE_LIST.bjGet(scope.row[col.prop]) }}
            </template>

            <template v-else-if="col.prop === 'orderStatus'">
              <el-tag :type="scope.row[col.prop] === 2 ? 'success' : 'info'">{{
                ORDER_INTERNAL_ORDER_STATUS_LIST.bjGet(scope.row[col.prop])
              }}</el-tag>
            </template>

            <template v-else-if="col.prop === 'mobile'">
              {{ scope.row[col.prop] | phoneFilter }}
            </template>
            <span v-else>{{ scope.row[col.prop] }}</span>
          </template>
        </el-table-column>
      </template>
    </el-table>
  </div>
</template>
<script>
import {
  ORDER_INTERNAL_ORDER_STATUS_LIST,
  ORDER_INTERNAL_DELIVER_STATUS_LIST,
  ORDER_BUSINESS_TYPE_LIST,
  ORDER_INTERNAL_RECEIVING_STATUS_LIST,
  ORDER_INTERNAL_PAYMENT_STATUS_LIST,
} from '@/utils/constants';
export default {
  name: 'InternalOrderList',
  props: {
    data: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  data() {
    return {
      ORDER_INTERNAL_ORDER_STATUS_LIST,
      ORDER_INTERNAL_DELIVER_STATUS_LIST,
      ORDER_BUSINESS_TYPE_LIST,
      ORDER_INTERNAL_RECEIVING_STATUS_LIST,
      ORDER_INTERNAL_PAYMENT_STATUS_LIST,
      list: [],
      labelList: [
        { label: '三方订单号', prop: 'outerOrderNo', isCheck: true, disabled: false, width: '160' },
        { label: '内部订单号', prop: 'innerOrderNo', isCheck: true, disabled: false, width: '160' },

        { label: '订单类型', prop: 'businessType', isCheck: true, disabled: false },
        { label: '订单状态', prop: 'orderStatus', isCheck: true, disabled: false },

        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelTypeMeaning', isCheck: true, disabled: false },

        // 支付状态  refundStatus
        { label: '支付状态', prop: 'payStatus', isCheck: true, disabled: false },

        { label: '商品行数量', prop: 'goodsQuatity', isCheck: true, disabled: false, width: '90' },

        { label: '商品总金额', prop: 'totalAmount', isCheck: true, disabled: false, width: '90' },

        { label: '买家应付', prop: 'buyerPay', isCheck: true, disabled: false, width: '90' },
        { label: '商品总成本', prop: 'costPrice', isCheck: true, disabled: false, width: '90' },

        // 买家实付
        { label: '买家实付', prop: 'buyerRealPrice', isCheck: true, disabled: false, width: '90' },

        {
          label: '发货状态',
          prop: 'deliveryStatus',
          isCheck: true,
          disabled: false,
          width: '95',
        },
        // 收货状态
        { label: '收货状态', prop: 'receivingStatus', isCheck: true, disabled: false, width: '90' },
        { label: '买家昵称', prop: 'buyerNick', isCheck: true, disabled: false },
        { label: '收货人姓名', prop: 'receiverName', isCheck: true, disabled: false, width: '90' },
        { label: '收货人手机号', prop: 'mobile', isCheck: true, disabled: false, width: '100' },
        { label: '收货人地址', prop: 'address', isCheck: true, disabled: false, width: '90' },
        { label: '三方下单时间', prop: 'orderTime', isCheck: true, disabled: false, width: '135' },
        { label: '支付时间', prop: 'payTime', isCheck: true, disabled: false, width: 135 },
        {
          label: '内部创单时间',
          prop: 'creationDate',
          isCheck: true,
          disabled: false,
          width: '135',
        },
      ],
    };
  },
  created() {},
  mounted() {},
  methods: {},
};
</script>
<style lang="scss" scoped></style>
