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
              placeholder="退款单号/订单号"
              @keyup.enter.native="handleQuery"
            />
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

        <el-col :span="colSpan">
          <el-form-item label="三方退款时间">
            <el-date-picker
              v-model="queryParamsTime"
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
        <BjSelectChannelStore
          :span="colSpan"
          :query-params="queryParams"
          :query-props="['channelTypeCode', 'storeIdList']"
          multiple
        />
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:BusinessAfterThirdRefundAdd
            type="primary"
            class="main-btn"
            @click="command('新建退款单')"
            >新增退款单</el-button
          >
          <!-- <el-button type="primary" @click="command('导入')">导入</el-button>
          <el-button type="primary" @click="command('导出')">导出</el-button> -->
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
              <template v-else-if="col.prop === 'outRefundNo'">
                <span
                  class="link"
                  @click="$toBusinessAfterThirdRefundView(scope.row.outRefundNo)"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'innerRefundNo'">
                <span
                  class="link"
                  @click="$toBusinessAfterInternalRefundView(scope.row[col.prop])"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>

              <template v-else-if="col.prop === 'exceptionStatus'">
                {{ AFTER_THIRD_REFUND_TRANSFER_STATUS.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'reason'">
                {{ BJ_AFTER_REFUND_ONLY_REASONS.bjGet(scope.row[col.prop]) }}
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
            <template slot-scope="props">
              <el-button
                v-if="!props.row.releaseStatus"
                type="text"
                class="delete-btn"
                @click="onDelete(props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 新增弹窗 -->
    <!-- <DataForm ref="DataForm" @reFresh="reFresh" /> -->
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { afterApi } from '@/api';
// import DataForm from './components/DataForm.vue';
import { AFTER_ORDER_TYPES, AFTER_THIRD_REFUND_TRANSFER_STATUS } from '@/utils/constants';

export default {
  name: 'BusinessAfterThirdRefundIndex',
  // components: { DataForm },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 8,
      selection: false,
      AFTER_THIRD_REFUND_TRANSFER_STATUS,
      queryApi: afterApi.getThirdRefundOrder,
      // 查询参数
      queryParamsTime: [],
      queryParams: {
        storeIdList: [],
        startTime: null, // 三方退货/退款时间 ,
        endTime: null, // 三方退货/退款时间 ,
        orderNo: null, // 第三方/内部订单号 ,
        channelTypeCode: null,
        buyerNick: null, // 买家昵称
        exceptionStatus: null, // 转单状态 0- 正常 1-异常 tab页切换 ,
        type: AFTER_ORDER_TYPES.REFUND_ONLY, // 类型 1-仅退款单 2- 退货退款
      },
      labelsVersion: 11,
      labelList: [
        {
          label: '三方退款单号',
          prop: 'outRefundNo',
          isCheck: true,
          disabled: false,
          width: 160,
        },
        {
          label: '内部退款单号',
          prop: 'innerRefundNo',
          isCheck: true,
          disabled: false,
          width: 160,
        },

        { label: '三方订单号', prop: 'outerOrderNo', isCheck: true, disabled: false, width: 160 },
        { label: '内部订单号', prop: 'innerOrderNo', isCheck: true, disabled: false, width: 160 },

        { label: '转单状态', prop: 'exceptionStatus', isCheck: true, disabled: false },
        { label: '退款数量', prop: 'goodsQuantity', isCheck: true, disabled: false },
        { label: '退款金额', prop: 'totalAmount', isCheck: true, disabled: false },
        { label: '退款原因', prop: 'reason', isCheck: true, disabled: false },
        { label: '退款备注', prop: 'remarks', isCheck: true, disabled: false },

        { label: '买家昵称', prop: 'buyerNick', isCheck: true, disabled: false },
        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelTypeMeaning', isCheck: true, disabled: false },
        {
          label: '三方退款时间',
          prop: 'refundTime',
          isCheck: true,
          disabled: false,
          width: 135,
        },
      ],
    };
  },
  computed: {
    BJ_AFTER_REFUND_ONLY_REASONS() {
      return this.$store.state.values.BJ_AFTER_REFUND_ONLY_REASONS || [];
    },
  },
  watch: {
    queryParamsTime(v) {
      this.queryParams.startTime = (v || [])[0];
      this.queryParams.endTime = (v || [])[1];
    },
  },
  mounted() {
    this.$store.dispatch('values/getAfterRefundOnlyReasons');
  },
  methods: {
    /** 操作按钮 */
    command(action, row) {
      switch (action) {
        case '新建退款单':
          this.$router.push({ name: 'BusinessAfterThirdRefundAdd' });
          break;
        case '导入':
          break;
        case '导出':
          break;
      }
    },
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 创建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 禁用 */
    onStatus(flag, row) {
      console.log(flag, row);
      this.operationApi({
        flag: flag,
        id: row.id,
        objectVersionNumber: row.objectVersionNumber,
      });
    },

    /** 删除 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ id: row.id });
        })
        .catch(() => {});
    },
    // operationApi(API_DATA) {
    //   settingApi.operationStockProcess(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    // batchDeleteApi(API_DATA) {
    //   settingApi.deleteStockProcess(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
  },
};
</script>
