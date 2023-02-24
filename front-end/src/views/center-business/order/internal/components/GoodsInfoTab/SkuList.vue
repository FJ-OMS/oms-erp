<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :auto-fetch="false"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      serial-width="100"
      :selection="hasBtn && selection"
      :stripe="false"
      right-toolbar-hide
      :hide-search="hideSearch"
      row-key="id"
      :reset-data="resetDataFn"
      tree-select-auto-parent
      lazy
      :load="loadMethod"
      :row-style="$treeRowStyleFun"
      @handleSelectionChange="handleSelectionChange"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="收货状态" prop="receiveStatus">
            <BjSelectLov
              v-model="queryParams.receiveStatus"
              constant-key="ORDER_INTERNAL_SKU_RECEIVING_STATUS_LIST"
            />
          </el-form-item>
        </el-col>
        <template v-if="businessType == ORDER_BUSINESS_TYPES.COMMON">
          <el-col :span="colSpan">
            <el-form-item label="发货状态" prop="deliveryStatus">
              <BjSelectLov
                v-model="queryParams.deliveryStatus"
                constant-key="ORDER_INTERNAL_SKU_DELIVER_STATUS_LIST"
              />
            </el-form-item>
          </el-col>
          <el-col :span="colSpan">
            <el-form-item label="挂起状态" prop="suspendStatus">
              <BjSelectLov
                v-model="queryParams.suspendStatus"
                constant-key="ORDER_INTERNAL_SKU_HAND_UP_STATUS_LIST"
              />
            </el-form-item>
          </el-col>
          <el-col :span="colSpan">
            <el-form-item label="发货回写状态" prop="syncDeliveryStatus">
              <BjSelectLov
                v-model="queryParams.syncDeliveryStatus"
                constant-key="ORDER_INTERNAL_SKU_STOCK_BACK_STATUS_LIST"
              />
            </el-form-item>
          </el-col>
        </template>
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
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <template v-else-if="col.prop === 'tags'">
                <BjTagList :tag-code-string="scope.row.tags" />
              </template>
              <template v-else-if="col.prop === 'spuName'">
                <el-tag v-if="scope.row.setFlag == 1">套</el-tag>
                {{ scope.row[col.prop] }}
              </template>
              <template v-else-if="col.prop === 'giftFlag'">
                <el-tag :type="scope.row.giftFlag ? 'success' : 'info'">{{
                  STATUS_YES_NUM.bjGet(!!scope.row.giftFlag)
                }}</el-tag>
              </template>
              <template v-else-if="col.prop === 'refundFlag'">
                <el-tag :type="scope.row.refundFlag ? 'success' : 'info'">{{
                  STATUS_YES_NUM.bjGet(!!scope.row.refundFlag)
                }}</el-tag>
              </template>
              <template
                v-else-if="
                  col.prop.toLocaleLowerCase().includes('amount') ||
                    col.prop.toLocaleLowerCase().includes('price')
                "
              >
                {{ $number2money(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'deliveryStatus'">
                {{
                  [
                    ...ORDER_INTERNAL_SKU_DELIVER_STATUS_LIST,
                    ...ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
                  ].bjGet(scope.row[col.prop])
                }}
              </template>
              <template v-else-if="col.prop === 'receivingStatus'">
                {{
                  [
                    ...ORDER_INTERNAL_SKU_RECEIVING_STATUS_LIST,
                    ...ORDER_INTERNAL_LINE_RECEIVING_STATUS_LIST,
                  ].bjGet(scope.row[col.prop])
                }}
              </template>
              <template v-else-if="col.prop === 'suspendStatus'">
                {{
                  [
                    ...ORDER_INTERNAL_SKU_HAND_UP_STATUS_LIST,
                    ...ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST,
                  ].bjGet(scope.row[col.prop])
                }}
              </template>
              <template v-else-if="col.prop === 'syncDeliveryStatus'">
                {{
                  [
                    ...ORDER_INTERNAL_SKU_STOCK_BACK_STATUS_LIST,
                    ...ORDER_INTERNAL_LINE_STOCK_BACK_STATUS_LIST,
                  ].bjGet(scope.row[col.prop])
                }}
              </template>
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
            <template slot-scope="scope">
              <el-button type="text" @click="onDetail(scope.row)">查看</el-button>
            </template>
          </el-table-column>
        </template>
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
  ORDER_INTERNAL_SKU_PREEMPT_BACK_STATUS_LIST,
  ORDER_INTERNAL_SKU_DELIVER_STATUS_LIST,
  ORDER_INTERNAL_SKU_RECEIVING_STATUS_LIST,
  ORDER_INTERNAL_SKU_STOCK_BACK_STATUS_LIST,
  ORDER_INTERNAL_SKU_HAND_UP_STATUS_LIST,
  ORDER_INTERNAL_REFUND_STATUS_LIST,
  ORDER_INTERNAL_LINE_PREEMPT_STATUS_LIST,
  ORDER_INTERNAL_LINE_PREEMPT_BACK_STATUS_LIST,
  ORDER_INTERNAL_LINE_SOURCE_STATUS_LIST,
  ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
  ORDER_INTERNAL_LINE_RECEIVING_STATUS_LIST,
  ORDER_INTERNAL_LINE_STOCK_BACK_STATUS_LIST,
  ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST,
  ORDER_BUSINESS_TYPES,
} from '@/utils/constants';
export default {
  name: 'BusinessOrderInternalViewSkuList',
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
    hideSearch: {
      type: Boolean,
      default: false,
    },
    businessType: {
      type: String,
      default: ORDER_BUSINESS_TYPES.COMMON,
    },
  },
  data() {
    return {
      serial: true,
      sortTableByOptionName: true,
      STATUS_YES_NUM,
      ORDER_INTERNAL_ORDER_STATUS_LIST,
      ORDER_INTERNAL_SKU_PREEMPT_BACK_STATUS_LIST,
      ORDER_INTERNAL_SKU_DELIVER_STATUS_LIST,
      ORDER_INTERNAL_SKU_RECEIVING_STATUS_LIST,
      ORDER_INTERNAL_SKU_STOCK_BACK_STATUS_LIST,
      ORDER_INTERNAL_SKU_HAND_UP_STATUS_LIST,
      ORDER_INTERNAL_REFUND_STATUS_LIST,
      ORDER_INTERNAL_LINE_PREEMPT_STATUS_LIST,
      ORDER_INTERNAL_LINE_PREEMPT_BACK_STATUS_LIST,
      ORDER_INTERNAL_LINE_SOURCE_STATUS_LIST,
      ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
      ORDER_INTERNAL_LINE_RECEIVING_STATUS_LIST,
      ORDER_INTERNAL_LINE_STOCK_BACK_STATUS_LIST,
      ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST,
      ORDER_BUSINESS_TYPES,
      queryApi: orderApi.getInternalSkuListByOrder,
      // 查询参数
      queryParams: {
        deliveryStatus: null,
        isOutStock: null,
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
    labelList() {
      const ARR = [
        { label: '标签', prop: 'tags', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: '是否赠品', prop: 'giftFlag', isCheck: true, disabled: false, width: '50' },
        // { label: '是否退款', prop: 'refundFlag', isCheck: true, disabled: false, width: '50' },
        {
          label: '数量',
          prop: 'quantity',
          isCheck: true,
          disabled: false,
          width: '60',
        },
        {
          label: '三方平台销售单价',
          prop: 'thirdSaleAmount',
          isCheck: true,
          disabled: false,
          width: '74',
        },
        {
          label: '三方平台均摊总价',
          prop: 'outBalancePrice',
          isCheck: true,
          disabled: false,
          width: '74',
        },
        {
          label: '内部销售价',
          prop: 'innerSalePrice',
          isCheck: true,
          disabled: false,
          width: '74',
        },
        {
          label: '内部均摊总价',
          prop: 'innerBalancePrice',
          isCheck: true,
          disabled: false,
          width: '74',
        },
        {
          label: '成本单价',
          prop: 'costAmount',
          isCheck: true,
          disabled: false,
          width: '74',
        },
        {
          label: '成本总价',
          prop: 'totalCostAmount',
          isCheck: true,
          disabled: false,
          width: '74',
        },
        {
          label: '运费',
          prop: 'transPrice',
          isCheck: true,
          disabled: false,
          width: '74',
        },
        { label: '发货状态', prop: 'deliveryStatus', isCheck: true, disabled: false, width: '96' },
        {
          label: '收货状态',
          prop: 'receivingStatus',
          isCheck: true,
          disabled: false,
          width: '96',
        },
        { label: '挂起', prop: 'suspendStatus', isCheck: true, disabled: false, width: '96' },
        {
          label: '发货回写状态',
          prop: 'syncDeliveryStatus',
          isCheck: true,
          disabled: false,
          width: '96',
        },
      ];
      if (this.businessType == ORDER_BUSINESS_TYPES.O2O) {
        ARR.forEach(col => {
          if (col.prop == 'receivingStatus') {
            col.width = 'auto';
          }
          if (col.width == '74') {
            col.width = '128';
          }
          if (col.width < 100) {
            col.width = '100';
          }
        });
        const NOT_IN_O2O_STR =
          'deliveryStatus,preemptionStatus,sourceStatus,suspendStatus,syncPreempStatus,syncDeliveryStatus';
        NOT_IN_O2O_STR.split(',').forEach(key => {
          const INDEX = ARR.findIndex(col => col.prop == key);
          if (INDEX > -1) {
            ARR.splice(INDEX, 1);
          }
        });
      }
      return ARR;
    },
  },
  watch: {
    orderId: {
      handler(v) {
        if (v) {
          console.warn('SkuList', v);
          this.queryParams.orderId = v;
          this.$nextTick(() => {
            this.reFresh();
          });
        }
      },
      immediate: true,
    },
  },
  created() {},
  methods: {
    resetDataFn(data) {
      return data.map(e => {
        return { BJ_HAS_CHILD: true, BJ_PARENT_ID: null, ...e };
      });
    },
    loadMethod({ row }) {
      // 异步加载子节点
      return new Promise((resolve, reject) => {
        orderApi
          .getInternalSkuLineListBySku({
            itemId: row.id,
          })
          .then(res => {
            console.log('res', res);
            const TEMP = (Array.isArray(res) ? res : []).map(e => {
              return { ...e, BJ_HAS_CHILD: false, BJ_PARENT_ID: row.id };
            });
            if (TEMP.length) {
              resolve(TEMP);
            } else {
              this.$message.warning('返回数据为空，请联系管理员');
              reject();
            }
          });
      });
    },
    handleSelectionChange(data) {
      console.log('SKU_LIST handleSelectionChange', data);
      this.selectedRows = data;
    },
  },
};
</script>
