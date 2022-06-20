<template>
  <div class="app-container">
    <BjRouteTabs :route-list="STOCK_TRANSFER_ROUTE_LIST" />
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="false"
      :before-reset-search="beforeResetSearch"
      :before-query-search="beforeQuerySearch"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="移仓单号" prop="code">
            <BjInputCode
              v-model.trim="queryParams.code"
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="status">
            <BjSelectLov
              v-model="queryParams.status"
              :option-list="isVerify ? STOCK_ALLOT_STATUS.slice(0, 2) : STOCK_ALLOT_STATUS"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="移入虚仓" prop="receiveWarehouseIdList">
            <BjSelectWarehouse
              v-model="queryParams.receiveWarehouseIdList"
              :type-code="['default-virtual', 'qty-virtual', 'interline-virtual']"
              multiple
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="移出虚仓" prop="supportWarehouseIdList">
            <BjSelectWarehouse
              v-model="queryParams.supportWarehouseIdList"
              :type-code="['default-virtual', 'qty-virtual', 'interline-virtual']"
              multiple
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="创建时间">
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
            /> </el-form-item
        ></el-col>
      </template>

      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-if="!isVerify"
            v-permission:BusinessStockTransferVirtualAdd
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
                {{ STOCK_ALLOT_STATUS.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop == 'bjTableAction'">
                <el-button
                  v-permission="
                    !isVerify
                      ? 'BusinessStockTransferVirtualView'
                      : 'BusinessStockVerifyVirtualView'
                  "
                  type="text"
                  @click="toView(scope.row)"
                  >查看</el-button
                >
                <template v-if="!isVerify" text="非审核页面">
                  <el-button
                    v-if="scope.row.status == 'wait'"
                    v-permission:BusinessStockTransferVirtualEdit
                    type="text"
                    text="待审核状态下可修改，点击跳转编辑调拨表单，自动带入当前调拨信息"
                    @click="onEdit(scope.row)"
                    >修改</el-button
                  >
                  <el-button
                    v-if="['fail', 'cancel'].includes(scope.row.status)"
                    v-permission:BusinessStockTransferVirtualIndex_Reset
                    type="text"
                    text="待审核状态下可取消，点击取消弹窗确认是否取消，确认取消后则取消所选调拨申请"
                    @click="onStatus(scope.row, 'resetDistributeVirtuals', '重置')"
                    >重置</el-button
                  >
                  <el-button
                    v-if="scope.row.status == 'wait-execute'"
                    v-permission:BusinessStockTransferVirtualIndex_Execute1
                    type="text"
                    text="待执行状态下可执行"
                    @click="onStatus(scope.row, 'executeDistributeVirtuals', '执行')"
                    >执行</el-button
                  >
                  <el-button
                    v-if="scope.row.status == 'execute-fail'"
                    v-permission:BusinessStockTransferVirtualIndex_Execute2
                    type="text"
                    text="执行失败状态下可再次执行"
                    @click="onStatus(scope.row, 'executeDistributeVirtuals', '再次执行')"
                    >再次执行</el-button
                  >
                  <el-button
                    v-if="scope.row.status == 'wait'"
                    v-permission:BusinessStockTransferVirtualIndex_Cancel
                    type="text"
                    text="待审核状态下可取消，点击取消弹窗确认是否取消，确认取消后则取消所选调拨申请"
                    @click="onStatus(scope.row, 'cancelDistributeVirtuals', '取消')"
                    >取消</el-button
                  >
                  <el-button
                    v-if="['wait-execute', 'execute-fail'].includes(scope.row.status)"
                    v-permission:BusinessStockTransferVirtualIndex_Invalid
                    type="text"
                    text="待执行和执行失败下可作废，作废后该移仓无需执行"
                    @click="onStatus(scope.row, 'invalidDistributeVirtuals', '作废')"
                    >作废</el-button
                  >
                </template>
                <template v-else>
                  <!-- 有配置逻辑仓调拨需要审核时才有审核 -->
                  <el-button
                    v-if="scope.row.status == 'wait'"
                    v-permission:BusinessStockVerifyVirtualIndex_Pass
                    type="text"
                    class="main-btn"
                    @click="onAudit('pass', scope.row.id)"
                    >审核通过</el-button
                  >
                  <el-button
                    v-if="scope.row.status == 'wait'"
                    v-permission:BusinessStockVerifyVirtualIndex_Reject
                    type="text"
                    @click="onAudit('reject', scope.row.id)"
                    >审核拒绝</el-button
                  >
                </template>
              </template>
              <span v-else>{{ scope.row[col.prop] }}</span>
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
import {
  STOCK_TRANSFER_VERIFY_TYPE_LIST,
  STOCK_TRANSFER_ROUTE_LIST,
  STOCK_VERIFY_ROUTE_LIST,
} from '@/utils/constants';
export default {
  name: 'BusinessStockTransferVirtualIndex',
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
      STOCK_TRANSFER_VERIFY_TYPE_LIST,
      STOCK_TRANSFER_ROUTE_LIST,
      STOCK_VERIFY_ROUTE_LIST,
      queryApi: stockApi.getDistributeVirtualsPage,
      // 查询参数
      queryParams: {
        code: null,
        receiveWarehouseIdList: [],
        status: null,
        supportWarehouseIdList: [],
        beginCreationDate: null,
        endCreationDate: null,
        auditFlag: this.isVerify,
      },
      labelsVersion: 11,
      labelList: [
        { label: '移仓单号', prop: 'code', isCheck: true, disabled: false },
        { label: '移出虚仓', prop: 'supportWarehouseName', isCheck: true, disabled: false },
        { label: '移入虚仓', prop: 'receiveWarehouseName', isCheck: true, disabled: false },
        { label: '移仓数量', prop: 'transferNum', isCheck: true, disabled: false },
        { label: '备注', prop: 'remark', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 180 },
      ],
      queryParamsTime: null,
    };
  },
  computed: {
    STOCK_ALLOT_STATUS() {
      return this.$store.state.values['STOCK_ALLOT_STATUS'] || [];
    },
  },
  watch: {
    queryParamsTime(v) {
      this.queryParams.beginCreationDate = (v || [])[0];
      this.queryParams.endCreationDate = (v || [])[1];
    },
  },
  mounted() {
    this.$store.dispatch('values/getValueList', 'STOCK.ALLOT_STATUS');
  },
  methods: {
    beforeQuerySearch(conditionObj) {
      // if (
      //   this.isVerify &&
      //   ((Array.isArray(conditionObj.status) && conditionObj.status.length == 0) ||
      //     !conditionObj.status)
      // ) {
      //   conditionObj.status = this.STOCK_TRANSFER_VERIFY_TYPE_LIST.map(e => e.value);
      // }
      return new Promise(resolve => {
        resolve(conditionObj);
      });
    },
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime = null;
      this.queryParams.beginCreationDate = null;
      this.queryParams.endCreationDate = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 新建 */
    onAdd() {
      this.$toPage('BusinessStockTransferVirtualAdd');
    },
    /** 查看详情 */
    toView(row) {
      this.$toPage(
        this.isVerify ? 'BusinessStockVerifyVirtualView' : 'BusinessStockTransferVirtualView',
        { id: row.id, isVerify: this.isVerify },
      );
    },
    /** 修改 */
    onEdit(row) {
      this.$toPage('BusinessStockTransferVirtualEdit', { id: row.id });
    },
    /** 修改状态 */
    onStatus(row, apiName, text) {
      this.$confirm(`您正在 ${text} 该单号，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi(row.id, apiName);
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
          .auditDistributeVirtuals({
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
    operationApi(id, name) {
      stockApi[name]({ id }).then(res => {
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
