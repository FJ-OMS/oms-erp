<template>
  <div class="app-container">
    <BjRouteTabs :route-list="STOCK_DISTRIBUTION_ROUTE_LIST" />
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
          <el-form-item label="分仓计划单号" prop="code">
            <BjInputCode
              v-model.trim="queryParams.code"
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="status">
            <BjSelectLov
              v-model="queryParams.status"
              :option-list="
                isVerify ? STOCK_DISTRIBUTE_PLAN_STATUS.slice(0, 2) : STOCK_DISTRIBUTE_PLAN_STATUS
              "
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="接收仓" prop="receiveWarehouseIdList">
            <BjSelectWarehouse
              v-model="queryParams.receiveWarehouseIdList"
              multiple
              :type-code="['default-virtual', 'qty-virtual', 'interline-virtual']"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="开始时间" prop="beginStartDate">
            <el-date-picker
              v-model="queryParamsStartTime"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              :picker-options="$pickerOptions()"
              align="right"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="结束时间" prop="endStartDate">
            <el-date-picker
              v-model="queryParamsEndTime"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              :picker-options="$pickerOptions()"
              align="right"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']"
            /> </el-form-item
        ></el-col>
      </template>

      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-if="!isVerify"
            v-permission:BusinessStockDistributionPlanAdd
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增</el-button
          >
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
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <template v-else-if="col.prop === 'status'">
                {{ STOCK_DISTRIBUTE_PLAN_STATUS.bjGet(scope.row[col.prop]) }}
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
              <el-button
                v-permission="
                  !isVerify ? 'BusinessStockDistributionPlanView' : 'BusinessStockVerifyPlanView'
                "
                type="text"
                @click="onView(scope.row)"
                >查看</el-button
              >
              <template v-if="!isVerify" text="非审核页面">
                <el-button
                  v-if="scope.row.status == 'wait'"
                  v-permission:BusinessStockDistributionPlanEdit
                  type="text"
                  text="待审核状态下可修改，点击跳转修改分仓计划表单，自动带入当前分仓计划信息"
                  @click="onEdit(scope.row)"
                  >编辑</el-button
                >
                <el-button
                  v-if="['fail', 'cancel'].includes(scope.row.status)"
                  v-permission:BusinessStockDistributionPlanIndex_Reset
                  type="text"
                  text="审核拒绝以及已取消状态下可重置，重置后该分仓计划自动变为待审核状态"
                  @click="onStatus('wait', scope.row)"
                  >重置</el-button
                >
                <el-button
                  v-if="scope.row.status == 'underway'"
                  v-permission:BusinessStockDistributionPlanIndex_Discard
                  type="text"
                  text="进行中的分仓计划可作废，作废后该计划不再执行，状态变更为已作废"
                  @click="onStatus('abandon', scope.row)"
                  >作废</el-button
                >
                <el-button
                  v-if="scope.row.status == 'wait'"
                  v-permission:BusinessStockDistributionPlanIndex_Cancel
                  type="text"
                  text="待审核状态下可取消，点击取消弹窗确认是否取消，确认取消后则取消所选分仓计划"
                  @click="onStatus('cancel', scope.row)"
                  >取消</el-button
                >
              </template>
              <template v-else>
                <!-- 有配置逻辑仓调拨需要审核时才有审核 -->
                <el-button
                  v-if="scope.row.status == 'wait'"
                  v-permission:BusinessStockVerifyPlanIndex_Pass
                  type="text"
                  class="main-btn"
                  @click="onAudit('pass', scope.row.id)"
                  >审核通过</el-button
                >
                <el-button
                  v-if="scope.row.status == 'wait'"
                  v-permission:BusinessStockVerifyPlanIndex_Reject
                  type="text"
                  @click="onAudit('reject', scope.row.id)"
                  >审核拒绝</el-button
                >
              </template>
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { stockApi } from '@/api';
import { STOCK_DISTRIBUTION_ROUTE_LIST } from '@/utils/constants';
export default {
  name: 'BusinessStockDistributionPlanIndex',
  components: {},
  mixins: [pageMiXin],
  props: {
    /** 是否是审核页面 */
    isVerify: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      colSpan: 8,
      STOCK_DISTRIBUTION_ROUTE_LIST,
      queryApi: stockApi.getDistributePlansPage,
      selection: false,
      // 查询参数
      queryParams: {
        code: null,
        status: null,
        receiveWarehouseIdList: [],
        beginStartDate: null,
        endStartDate: null,
        beginEndDate: null,
        endEndDate: null,
        auditFlag: this.isVerify,
      },
      queryParamsStartTime: null,
      queryParamsEndTime: null,
      labelsVersion: 11,
      labelList: [
        { label: '计划单号', prop: 'code', isCheck: true, disabled: false },
        { label: '接收仓', prop: 'receiveWarehouseName', isCheck: true, disabled: false },
        { label: '计划供应数量', prop: 'planNum', isCheck: true, disabled: false },
        { label: '已供应数量', prop: 'alreadyNum', isCheck: true, disabled: false },
        { label: '优先级', prop: 'priorityGrade', isCheck: true, disabled: false },
        { label: '开始时间', prop: 'startDate', isCheck: true, disabled: false, width: 135 },
        { label: '结束时间', prop: 'endDate', isCheck: true, disabled: false, width: 135 },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
        { label: '备注', prop: 'remark', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 180 },
      ],
    };
  },
  computed: {
    STOCK_DISTRIBUTE_PLAN_STATUS() {
      return this.$store.state.values['STOCK_DISTRIBUTE_PLAN_STATUS'] || [];
    },
  },
  watch: {
    queryParamsStartTime(v) {
      this.queryParams.beginStartDate = (v || [])[0];
      this.queryParams.endStartDate = (v || [])[1];
    },
    queryParamsEndTime(v) {
      this.queryParams.beginEndDate = (v || [])[0];
      this.queryParams.endEndDate = (v || [])[1];
    },
  },
  mounted() {
    this.$store.dispatch('values/getValueList', 'STOCK.DISTRIBUTE_PLAN_STATUS');
  },
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsStartTime = null;
      this.queryParamsEndTime = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    onStatus(flag, row) {
      let TEXT = this.STOCK_DISTRIBUTE_PLAN_STATUS.bjGet(flag);
      if (flag === 'wait') TEXT = '重置';
      this.$confirm(`您正在 ${TEXT} 分仓计划，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi(row.id, flag);
        })
        .catch(() => {});
    },
    /** 审核 */
    onAudit(status, id) {
      this.$confirm(`是否审核${status === 'pass' ? '通过' : '不通过'}该数据？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        stockApi
          .auditDistributePlan({
            id,
            status,
          })
          .then(res => {
            if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
              this.$message.success('操作成功');
              setTimeout(() => {
                this.reFresh();
              }, 500);
            }
          });
      });
    },
    /** 新建 */
    onAdd() {
      this.$toPage('BusinessStockDistributionPlanAdd');
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$toPage('BusinessStockDistributionPlanEdit', { id: row.id });
    },
    /** 查看 */
    onView(row) {
      this.$toPage(
        this.isVerify ? 'BusinessStockVerifyPlanView' : 'BusinessStockDistributionPlanView',
        {
          id: row.id,
          isVerify: this.isVerify,
        },
      );
    },
    operationApi(id, type) {
      let apiName = null;
      switch (type) {
        case 'wait':
          apiName = 'resetDistributePlan'; // 重置
          break;
        case 'abandon':
          apiName = 'invalidDistributePlan'; // 作废
          break;
        case 'cancel':
          apiName = 'cancelDistributePlan'; // 取消
          break;
      }
      stockApi[apiName]({ id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      stockApi.detailDistributePlans(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
