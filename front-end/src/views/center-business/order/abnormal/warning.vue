<template>
  <div class="app-container">
    <BjRouteTabs :route-list="ORDER_ABNORMAL_ROUTE_LIST" />
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
          <el-form-item label="单号" prop="orderCode">
            <BjInputCode
              v-model.trim="queryParams.orderCode"
              placeholder="三方订单号/内部订单号/寻源单号/出库通知单号"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="预警类型" prop="abnormalType">
            <BjSelectLov
              v-model="queryParams.abnormalType"
              :option-list="ORDER_ABNORMAL_TYPE_LIST"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="处理状态" prop="status">
            <BjSelectLov v-model="queryParams.status" :option-list="ABNORMAL_STATUS_LIST" />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary" class="main-btn" @click="reFresh">刷新预警</el-button>
          <el-button type="primary" @click="setWarn">业务预警设置</el-button>
          <el-button type="primary" @click="onAuto">自动刷新设置</el-button>
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
              <template v-else-if="col.prop === 'orderSn'">
                <span @click="onView(scope.row)">{{ scope.row[col.prop] }}</span>
                <BjCopyIcon type="el-button" :copy="scope.row.orderSn" />
              </template>
              <template v-else-if="col.prop === 'sourceCode'">
                <span @click="onView(scope.row)">{{ scope.row[col.prop] }}</span>
                <BjCopyIcon type="el-button" :copy="scope.row.sourceCode" />
              </template>
              <template v-else-if="col.prop === 'innerOrderSn'">
                <span>{{ scope.row[col.prop] }}</span>
                <BjCopyIcon class="ml10" type="el-button" :copy="scope.row.innerOrderSn" />
              </template>
              <template v-else-if="col.prop === 'abnormalType'">
                {{ ORDER_ABNORMAL_TYPE_LIST.bjGet(scope.row[col.prop]) }}
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
              <el-button v-show="!ids.includes(props.row.orderSn)" type="text">操 作</el-button>
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <BjDialogRefresh ref="autoRefresh" @confirm="setAutoRefresh" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
// import { stockApi } from '@/api';
import {
  ORDER_ABNORMAL_ROUTE_LIST,
  ORDER_ABNORMAL_TYPE_LIST,
  ABNORMAL_STATUS_LIST,
} from '@/utils/constants';
import BjDialogRefresh from '@/components/BjDialogRefresh';
const getDefaultForm = () => {
  return {};
};
export default {
  name: 'BusinessOrderSource',
  components: { BjDialogRefresh },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 6,
      ORDER_ABNORMAL_ROUTE_LIST,
      ORDER_ABNORMAL_TYPE_LIST,
      ABNORMAL_STATUS_LIST,
      queryApi: null, //
      // 查询参数
      queryParams: {
        orderCode: null,
        abnormalType: null,
        status: null,
      },
      title: '',
      dialogVisible: false,
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      labelsVersion: 11,
      labelList: [
        { label: '三方订单号', prop: 'orderSn', isCheck: true, disabled: false, width: '180' },
        { label: '内部订单号', prop: 'innerOrderSn', isCheck: true, disabled: false, width: '180' },
        { label: '出库通知单号', prop: '', isCheck: true, disabled: false },
        { label: '出库通知单状态', prop: '', isCheck: true, disabled: false },
        { label: '预警类型', prop: 'abnormalType', isCheck: true, disabled: false },
        { label: '预警原因', prop: 'earlyCause', isCheck: true, disabled: false },
        { label: '处理状态', prop: 'status', isCheck: true, disabled: false },
        { label: '预警时间', prop: 'earlyTime', isCheck: true, disabled: false, width: 135 },
        { label: '订单状态', prop: 'orderStatus', isCheck: true, disabled: false },
        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelType', isCheck: true, disabled: false },
        {
          label: '三方下单时间',
          prop: 'createTime',
          isCheck: true,
          disabled: false,
          width: '135px',
        },
        { label: '支付时间', prop: 'payTime', isCheck: true, disabled: false, width: 135 },
        { label: '拉单时间', prop: 'pullTime', isCheck: true, disabled: false, width: '135px' },
        // { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129},
      ],
      ids: [],
      intervalTime: null,
      enabledFlag: false,
      timer: null,
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.$refs.BjPage.tableData = this.$getMockData([
      {
        orderSn: /^\d{18}/,
        innerOrderSn: /^XS2021[01][1-9]\d{8}/,
        sourceCode: /^XY2021[01][1-9]\d{8}/,
        'abnormalType|0-3': 1,
        earlyCause: '订单超过12小时未审核',
        status: '未处理',
        earlyTime: '@dateTime',
        orderStatus: '待审核',
        storeName: '天猫_居家旗舰店',
        channelType: '天猫',
        createTime: '@dateTime',
        payTime: '@dateTime',
        pullTime: '@dateTime',
      },
    ]);
    // 输出结果
    // console.log(JSON.stringify(this.$refs.BjPage.tableData));
  },
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime_create = null;
      this.queryParamsTime_pay = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 业务预警设置 */
    setWarn() {},
    /** 自动刷新设置 */
    onAuto() {
      this.$refs.autoRefresh.show({
        intervalTime: this.intervalTime,
        enabledFlag: this.enabledFlag,
      });
    },
    /** 设置自动刷新 */
    setAutoRefresh(data) {
      this.intervalTime = data.intervalTime;
      this.enabledFlag = data.enabledFlag;
    },
    /** 详情 */
    /**
     * @description: 跳转
     * @param {*} row 数据
     * @param {*} type 0：总缺货数量、1：缺货网店数量
     * @return {*}
     */
    onView(row) {
      // this.$toPage(pageName, { id: row.innerOrderSn });
    },
  },
};
</script>
