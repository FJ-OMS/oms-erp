<template>
  <div class="app-container">
    <BjRouteTabs :route-list="SALES_AFTER_THIRD_ROUTE_LIST" />
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
          <el-form-item label="换货物流单号" prop="returnNo">
            <BjInputCode
              v-model.trim="queryParams.returnNo"
              placeholder="请输入"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="换货物流公司" prop="companyId">
            <BjSelectLogisticCompany v-model.trim="form.companyId" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="买家昵称" prop="nickName">
            <el-input
              v-model.trim="queryParams.nickName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="渠道" prop="channelTypeCode">
            <BjSelectLov v-model="queryParams.channelTypeCode" lov-code="SCHL.CHANNEL_TYPE" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="店铺" prop="storeIdList">
            <BjSelectStore v-model="queryParams.storeIdList" multiple />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="换货人" prop="returnName">
            <el-input
              v-model.trim="queryParams.returnName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="三方换货时间">
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
        <el-col :span="colSpan">
          <el-form-item label="换货状态" prop="status">
            <BjSelectLov
              v-model="queryParams.status"
              :option-list="AFTER_THIRD_EXCHANGE_STATUS_LIST"
              clearable
            />
          </el-form-item>
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
              <template v-else-if="col.prop === 'thirdRefundNo'">
                <span class="link" @click="onView(scope.row)">{{ scope.row[col.prop] }}</span>
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'internalRefundNo'">
                <span>{{ scope.row[col.prop] }}</span>
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'thirdSellRefundNo'">
                <span
                  class="link"
                  @click="$toBusinessOrderThirdView(scope.row.thirdSellRefundNo)"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'internalSellRefundNo'">
                <span>{{ scope.row[col.prop] }}</span>
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'returnPhone'">
                <span>{{ scope.row[col.prop] }}</span>
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'type'">
                {{ INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'resStatus'">
                {{ AFTER_REFUND_TRADE_STATUS.bjGet(scope.row[col.prop]) }}
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
// import { settingApi } from '@/api';
// import DataForm from './components/DataForm.vue';
import {
  INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
  AFTER_REFUND_TRADE_STATUS,
  SALES_AFTER_THIRD_ROUTE_LIST,
  AFTER_THIRD_EXCHANGE_STATUS_LIST,
} from '@/utils/constants';
const getDefaultForm = () => {
  return {};
};
import tableData from './data.json';

export default {
  name: 'BusinessAfterThirdExchange',
  // components: { DataForm },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 6,
      selection: false,
      INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
      AFTER_REFUND_TRADE_STATUS,
      SALES_AFTER_THIRD_ROUTE_LIST,
      AFTER_THIRD_EXCHANGE_STATUS_LIST,
      queryApi: null, // settingApi.getStockProcess,
      // 查询参数
      queryParamsTime_pay: [],
      queryParams: {
        orderNo: null,
        returnNo: null,
        companyId: null,
        nickName: null,
        storeIdList: [],
        returnName: null,
        status: null,
      },
      title: '',
      dialogVisible: false,
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {
        name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
        typeCode: [{ required: true, message: '请选择所属分类', trigger: 'change' }],
      },
      labelsVersion: 11,
      labelList: [
        {
          label: '三方换货单号',
          prop: 'thirdRefundNo',
          isCheck: true,
          disabled: false,
          width: '180',
        },
        {
          label: '内部换货单号',
          prop: 'internalRefundNo',
          isCheck: true,
          disabled: false,
          width: '170',
        },
        {
          label: '三方订单号',
          prop: 'thirdSellRefundNo',
          isCheck: true,
          disabled: false,
          width: '180',
        },
        {
          label: '内部订单号',
          prop: 'internalSellRefundNo',
          isCheck: true,
          disabled: false,
          width: '170',
        },
        { label: '换货状态', prop: 'status', isCheck: true, disabled: false },
        { label: '换货原因', prop: 'reason', isCheck: true, disabled: false },
        { label: '换货备注', prop: 'remark', isCheck: true, disabled: false },
        { label: '退货数量', prop: 'returnGoodsNum', isCheck: true, disabled: false },
        { label: '退货商品金额', prop: 'money', isCheck: true, disabled: false },
        { label: '退入仓库', prop: 'returnStock', isCheck: true, disabled: false },
        { label: '退货物流单号', prop: 'returnNo', isCheck: true, disabled: false },
        { label: '退货物流公司', prop: 'returnCompany', isCheck: true, disabled: false },
        { label: '买家昵称', prop: 'nickName', isCheck: true, disabled: false },
        { label: '换货人', prop: 'returnName', isCheck: true, disabled: false, width: 135 },
        { label: '换货人手机号', prop: 'returnPhone', isCheck: true, disabled: false, width: 120 },
        { label: '换货人地址', prop: 'returnAddress', isCheck: true, disabled: false },
        { label: '三方销售单状态', prop: 'thirdStatus', isCheck: true, disabled: false },
        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelType', isCheck: true, disabled: false },
        { label: '三方换货时间', prop: 'time1', isCheck: true, disabled: false, width: 135 },
      ],
    };
  },
  computed: {},
  watch: {
    queryParamsTime_pay(v) {
      this.queryParams.beginSendTime = (v || [])[0];
      this.queryParams.endSendTime = (v || [])[1];
    },
  },
  mounted() {
    this.$refs.BjPage.tableData = tableData;
    // 输出结果
    // console.log(JSON.stringify(this.$refs.BjPage.tableData));
  },
  methods: {
    /** 操作按钮 */
    command(action, row) {
      this.$message(`${action}成功！`);
      switch (action) {
        case '新建退款单':
          break;
        case '导入':
          break;
        case '导出':
          break;
      }
    },
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime_pay = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 创建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 跳转 */
    onView(row) {
      // 换货详情-三方售后单
      this.$toPage('BusinessAfterThirdExchangeView', { id: row.thirdRefundNo });
    },
    // /** 禁用 */
    // onStatus(flag, row) {
    //   console.log(flag, row);
    //   this.operationApi({
    //     flag: flag,
    //     id: row.id,
    //     objectVersionNumber: row.objectVersionNumber,
    //   });
    // },

    // /** 删除 */
    // onDelete(row) {
    //   this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.batchDeleteApi({ id: row.id });
    //     })
    //     .catch(() => {});
    // },
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
