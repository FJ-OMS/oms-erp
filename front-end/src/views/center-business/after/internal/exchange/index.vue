<template>
  <div class="app-container">
    <BjRouteTabs :route-list="SALES_AFTER_INTERNAL_ROUTE_LIST" />
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
          <el-form-item label="退货物流单号" prop="returnNo">
            <BjInputCode v-model.trim="queryParams.returnNo" @keyup.enter.native="handleQuery" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="退货物流公司" prop="companyId">
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
          <el-form-item label="退货人" prop="returnName">
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
          <el-form-item label="退货时间">
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
          <el-form-item label="结算方式" prop="payWay">
            <BjSelectLov
              v-model="queryParams.payWay"
              :option-list="AFTER_INTERNAL_RETURN_PAY_LIST"
              clearable
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary" class="main-btn" @click="onAdd">新增换货单</el-button>
          <el-button type="primary" @click="command('导入')">导入</el-button>
          <el-button type="primary" @click="command('导出')">导出</el-button>
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
                <span
                  class="link"
                  @click="$toBusinessAfterThirdExchangeView(scope.row.thirdRefundNo)"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'internalRefundNo'">
                <span class="link" @click="onView(scope.row)">{{ scope.row[col.prop] }}</span>
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
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
// import { settingApi } from '@/api';
import {
  INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
  AFTER_REFUND_TRADE_STATUS,
  SALES_AFTER_INTERNAL_ROUTE_LIST,
  AFTER_INTERNAL_RETURN_PAY_LIST,
} from '@/utils/constants';
const getDefaultForm = () => {
  return {};
};
import tableData from './data.json';

export default {
  name: 'BusinessAfterInternalExchangeIndex',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 6,
      selection: false,
      INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
      AFTER_REFUND_TRADE_STATUS,
      SALES_AFTER_INTERNAL_ROUTE_LIST,
      AFTER_INTERNAL_RETURN_PAY_LIST,
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
        payWay: null,
      },
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
          label: '内部换货单号',
          prop: 'internalRefundNo',
          isCheck: true,
          disabled: false,
          width: '170',
        },
        {
          label: '三方换货单号',
          prop: 'thirdRefundNo',
          isCheck: true,
          disabled: false,
          width: '180',
        },
        {
          label: '三方订单号',
          prop: 'thirdSellRefundNo',
          isCheck: true,
          disabled: false,
          width: '180',
        },
        {
          label: '内部订单号（原）',
          prop: 'internalSellRefundNo',
          isCheck: true,
          disabled: false,
          width: '170',
        },
        { label: '换货单来源', prop: 'returnSource', isCheck: true, disabled: false },
        { label: '换货状态', prop: 'status', isCheck: true, disabled: false },
        { label: '换货原因', prop: 'reason', isCheck: true, disabled: false },
        { label: '换货备注', prop: 'buyRemark', isCheck: true, disabled: false },
        { label: '退入仓库', prop: 'returnStock', isCheck: true, disabled: false },
        { label: '退货物流单号', prop: 'returnNo', isCheck: true, disabled: false },
        { label: '退货物流公司', prop: 'returnCompany', isCheck: true, disabled: false },
        { label: '买家昵称', prop: 'nickName', isCheck: true, disabled: false },
        { label: '换货人', prop: 'returnName', isCheck: true, disabled: false, width: 135 },
        { label: '换货人手机号', prop: 'returnPhone', isCheck: true, disabled: false },
        { label: '换货人地址', prop: 'returnAddress', isCheck: true, disabled: false },
        {
          label: '内部订单号（换）',
          prop: 'exchangeInternalSellRefundNo',
          isCheck: true,
          disabled: false,
          width: '170',
        },
        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelType', isCheck: true, disabled: false },
        { label: '三方换货时间', prop: 'time1', isCheck: true, disabled: false, width: 135 },
        { label: '内部创建时间', prop: 'time2', isCheck: true, disabled: false, width: 135 },
        { label: '入库时间', prop: 'time3', isCheck: true, disabled: false, width: 135 },
        { label: '换货完成时间', prop: 'time4', isCheck: true, disabled: false, width: 135 },
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
      this.$toPage('BusinessAfterInternalExchangeAdd');
      // this.$refs.DataForm.show();
    },
    onView(row) {
      console.log(row);
      this.$toPage('BusinessAfterInternalExchangeView', { id: row.thirdRefundNo });
    },
  },
};
</script>
