<template>
  <div class="app-container">
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
              placeholder="三方订单号/内部订单号"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="回写状态" prop="syncStatus">
            <BjSelectLov v-model="queryParams.syncStatus" :option-list="ORDER_BACK_RES_LIST" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="收货人姓名" prop="receiverName">
            <el-input
              v-model.trim="queryParams.receiverName"
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
          :query-props="['channel', 'storeIdList']"
          multiple
        />
        <el-col :span="colSpan">
          <BjFormItemMobile
            v-model.trim="queryParams.mobile"
            prop="mobile"
            label="手机号"
            clearable
          />
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="支付时间" prop="name">
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
          <el-form-item label="下单时间" prop="name">
            <el-date-picker
              v-model="queryParamsTime_create"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              :picker-options="$pickerOptions()"
              align="right"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']"
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission="addPermissionName"
            type="primary"
            class="main-btn"
            @click="onCreate"
            >创建订单</el-button
          >
          <!-- <el-button type="primary" @click="isBjImportFileVisible = true">Excel导入</el-button> -->
          <!-- <el-dropdown @command="onCommand">
            <span class="el-dropdown-link">
              下载订单<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="0">下载订单（按单号）</el-dropdown-item>
              <el-dropdown-item :command="1">下载订单（按时间）</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown> -->
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
                <span
                  class="link"
                  @click="$toBusinessOrderThirdView(scope.row.outerOrderNo, false, businessType)"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'innerOrderNo'">
                <span
                  class="link"
                  @click="$toBusinessOrderInternalView(scope.row.innerOrderNo, false, businessType)"
                  >{{ scope.row[col.prop] }}</span
                >
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'circulationStatus'">
                {{ AFTER_THIRD_REFUND_TRANSFER_STATUS.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'syncStatus'">
                {{ ORDER_BACK_RES_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'receivingStatus'">
                {{ ORDER_THIRD_RECEIVING_STATUS_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'mobile'">
                {{ scope.row[col.prop] | phoneFilter }}
                <BjCopyIcon :copy="scope.row[col.prop]" />
              </template>
              <template v-else-if="col.prop === 'businessType'">
                {{ ORDER_BUSINESS_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'resStatus'">
                {{ ORDER_BACK_RES_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'tradeStatus'">
                {{ ORDER_INTERNAL_ORDER_STATUS_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'thirdReceiveStatus'">
                {{ THIRD_ORDER_RECEIVE_STATUS.bjGet(scope.row[col.prop]) }}
              </template>
              <template
                v-else-if="
                  [
                    'goodsTotalAmount',
                    'totalDiscount',
                    'amountReceivable',
                    'buyerPayAmount',
                    'businessDiscountAmount',
                    'thirdDiscountAmount',
                  ].includes(col.prop)
                "
              >
                {{ $number2money(scope.row[col.prop]) }}
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
    <DialogForm ref="dialog" @confirm="onConfirm" />
    <!-- 导入弹窗 -->
    <el-dialog
      v-if="isBjImportFileVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="导入数据"
      :visible.sync="isBjImportFileVisible"
      destroy-on-close
      width="500px"
    >
      <BjImportFile ref="BjImportFile" accept=".xls,.xlsx" auto-upload @importFile="importFile">
        <template slot="default"> 只能上传 Excel 文件，且不超过 2Mb </template>
      </BjImportFile>
    </el-dialog>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { orderApi } from '@/api';
import DialogForm from './dialogForm.vue';
import {
  ORDER_BUSINESS_TYPE_LIST,
  ORDER_BACK_RES_LIST,
  ORDER_INTERNAL_ORDER_STATUS_LIST,
  THIRD_ORDER_RECEIVE_STATUS,
  AFTER_THIRD_REFUND_TRANSFER_STATUS,
  ORDER_THIRD_RECEIVING_STATUS_LIST,
  ORDER_BUSINESS_TYPES,
} from '@/utils/constants';
const getDefaultForm = () => {
  return {};
};
export default {
  name: 'BusinessOrderThirdListPageList',
  components: { DialogForm },
  mixins: [pageMiXin],
  props: {
    businessType: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      colSpan: 8,
      selection: false,
      /** */
      ORDER_BUSINESS_TYPE_LIST,
      ORDER_BACK_RES_LIST,
      ORDER_INTERNAL_ORDER_STATUS_LIST,
      THIRD_ORDER_RECEIVE_STATUS,
      AFTER_THIRD_REFUND_TRANSFER_STATUS,
      ORDER_THIRD_RECEIVING_STATUS_LIST,
      /** */
      queryApi: orderApi.getThirdOrderPage,
      // 查询参数
      queryParamsTime_pay: [],
      queryParamsTime_create: [],
      // 已核对字段
      queryParams: {
        orderNo: null,
        businessType: this.businessType,
        syncStatus: null,
        receiverName: null,
        channel: null,
        storeIdList: [],
        mobile: null,
        payStartTime: null,
        payEndTime: null,
        orderStartTime: null,
        orderEndTime: null,
      },
      title: '',
      dialogVisible: false,
      isBjImportFileVisible: false,
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {
        name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
        typeCode: [{ required: true, message: '请选择所属分类', trigger: 'change' }],
      },
      labelsVersion: 11,
      labelList: [
        { label: '三方订单号', prop: 'outerOrderNo', isCheck: true, disabled: false, width: '180' },
        { label: '内部订单号', prop: 'innerOrderNo', isCheck: true, disabled: false, width: '180' },
        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false, width: '160' },
        {
          label: '销售渠道',
          prop: 'channelTypeMeaning',
          isCheck: true,
          disabled: false,
          width: '100',
        },
        {
          label: '流转状态',
          prop: 'circulationStatus',
          isCheck: true,
          disabled: false,
          width: '100',
        },
        { label: '回写状态', prop: 'syncStatus', isCheck: true, disabled: false, width: '100' },
        {
          label: '平台收货状态',
          prop: 'receivingStatus',
          isCheck: true,
          disabled: false,
          width: '100',
        },
        { label: '买家留言', prop: 'buyerRemarks', isCheck: true, disabled: false, width: '100' },
        { label: '商家备注', prop: 'orderRemarks', isCheck: true, disabled: false, width: '100' },
        { label: '商品数', prop: 'goodsQuantity', isCheck: true, disabled: false, width: '80' },
        {
          label: '商品总金额',
          prop: 'goodsTotalAmount',
          isCheck: true,
          disabled: false,
          width: '100',
        },
        { label: '优惠合计', prop: 'totalDiscount', isCheck: true, disabled: false, width: '100' },
        {
          label: '应收总金额',
          prop: 'amountReceivable',
          isCheck: true,
          disabled: false,
          width: '100',
        },
        { label: '买家实付', prop: 'buyerPayAmount', isCheck: true, disabled: false, width: '100' },
        // 商家提供的优惠券+折扣金额
        {
          label: '商家承担优惠金额',
          prop: 'businessDiscountAmount',
          isCheck: true,
          disabled: false,
          width: '140',
        },
        {
          label: '三方承担优惠金额',
          prop: 'thirdDiscountAmount',
          isCheck: true,
          disabled: false,
          width: '140',
        },
        {
          label: '买家昵称',
          prop: 'buyerNick',
          isCheck: true,
          disabled: false,
          width: '120',
        },
        { label: '收货人姓名', prop: 'receiverName', isCheck: true, disabled: false, width: '100' },
        { label: '手机号', prop: 'mobile', isCheck: true, disabled: false, width: '160' },
        {
          label: '收货地址',
          prop: 'address',
          isCheck: true,
          disabled: false,
          width: '160',
        },
        {
          label: '三方下单时间',
          prop: 'orderTime',
          isCheck: true,
          disabled: false,
          width: '135px',
        },
        { label: '支付时间', prop: 'payTime', isCheck: true, disabled: false, width: '135' },
        { label: '拉单时间', prop: 'creationDate', isCheck: true, disabled: false, width: '135px' },
        // { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129},
      ],
    };
  },
  computed: {
    addPermissionName() {
      return this.businessType == ORDER_BUSINESS_TYPES.COMMON
        ? 'BusinessOrderThirdAdd'
        : 'BusinessO2oThirdAdd';
    },
  },
  watch: {
    queryParamsTime_create(v) {
      this.queryParams.orderStartTime = (v || [])[0];
      this.queryParams.orderEndTime = (v || [])[1];
    },
    queryParamsTime_pay(v) {
      this.queryParams.payStartTime = (v || [])[0];
      this.queryParams.payEndTime = (v || [])[1];
    },
  },
  mounted() {},
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime_create = null;
      this.queryParamsTime_pay = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    importFile(file) {
      const API_DATA = { excel: file };
      console.log(API_DATA);
      // financeApi
      //   .importOriginalBillTmalls(API_DATA)
      //   .then(res => {
      //     if (!res.failed) {
      //       this.msgSuccess('导入成功');
      //       this.reFresh();
      //     }
      //   })
      //   .catch(err => {
      //     console.error(err);
      //   });
    },
    onCommand(command) {
      this.$refs.dialog.show(command);
    },
    onConfirm(form) {
      console.log(form);
    },
    /** 创建 */
    onCreate() {
      if (this.businessType == ORDER_BUSINESS_TYPES.COMMON) {
        this.$toPage('BusinessOrderThirdAdd');
      }

      if (this.businessType == ORDER_BUSINESS_TYPES.O2O) {
        this.$toPage('BusinessO2oThirdAdd');
      }
    },
  },
};
</script>
