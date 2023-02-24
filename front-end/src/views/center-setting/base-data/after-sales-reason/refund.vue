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
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="原因编码" prop="reasonCode">
            <el-input
              v-model.trim="queryParams.reasonCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="原因描述" prop="reasonDescribe">
            <el-input
              v-model.trim="queryParams.reasonDescribe"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="状态" prop="status">
            <BjSelectLov v-model="queryParams.status" :option-list="STATUS_ENABLED_NUM" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="是否已使用" prop="useFlag">
            <BjSelectLov v-model="queryParams.useFlag" :option-list="USE_FLAG_NUM" />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <!-- <el-button
            v-permission:SettingBaseDataAfterSalesReasonRefundIndex_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            @click="onDeleteSelected"
             class='delete-btn'>删除</el-button
          > -->
          <el-button
            v-permission:SettingBaseDataAfterSalesReasonRefundIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增售后原因</el-button
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

              <el-tag
                v-else-if="col.prop === 'status'"
                :type="scope.row.status ? 'success' : 'info'"
                >{{ STATUS_ENABLED_NUM.bjGet(scope.row.status) }}</el-tag
              >

              <el-tag
                v-else-if="col.prop === 'useFlag'"
                :type="scope.row.useFlag ? 'success' : 'info'"
                >{{ USE_FLAG_NUM.bjGet(scope.row.useFlag) }}</el-tag
              >
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
                v-permission:SettingBaseDataAfterSalesReasonRefundIndex_Edit
                :disabled="!!props.row.useFlag"
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-if="props.row.status"
                v-permission:SettingBaseDataAfterSalesReasonRefundIndex_Status
                type="text"
                @click="onStatus(0, props.row)"
                >禁用</el-button
              >
              <el-button
                v-if="!props.row.status"
                v-permission:SettingBaseDataAfterSalesReasonRefundIndex_Status
                type="text"
                @click="onStatus(1, props.row)"
                >启用</el-button
              >
              <el-button
                v-permission:SettingBaseDataAfterSalesReasonRefundIndex_Delete
                :disabled="!!props.row.useFlag"
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

    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { baseDataApi } from '@/api';
import { STATUS_ENABLED_NUM, USE_FLAG_NUM } from '@/utils/constants';
import DataForm from './components/DataForm.vue';

const DEFAULT_TYPE = 0; // 原因类型 0仅退款 1退货退款
export default {
  name: 'SettingBaseDataAfterSalesReasonRefundIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      STATUS_ENABLED_NUM,
      USE_FLAG_NUM,
      queryApi: baseDataApi.getAfterSalesReasons,
      // 查询参数
      queryParams: {
        reasonCode: null,
        reasonDescribe: null,
        status: null,
        useFlag: null,
        type: DEFAULT_TYPE,
      },

      labelsVersion: 11,
      labelList: [
        { label: '原因编码', prop: 'reasonCode', isCheck: true, disabled: false },
        { label: '原因描述', prop: 'reasonDescribe', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
        { label: '是否已使用', prop: 'useFlag', isCheck: true, disabled: false }, // 使用状态 0未使用 1已使用 ,
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true },
      ],
      selectedRow: null,
    };
  },
  watch: {},
  methods: {
    /** 新增售后原因 */
    onAdd() {
      this.$refs.DataForm.show({ type: DEFAULT_TYPE });
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
      // baseDataApi.detailAfterSalesReasons({ id: row.id }).then(res => {
      //   if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
      //     this.$refs.DataForm.show(this.$lodash.clone(res));
      //   }
      // });
    },
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_NUM.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} 售后原因（${row.reasonDescribe}），是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            id: row.id,
            status: flag,
            objectVersionNumber: row.objectVersionNumber,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除 售后原因（${row.reasonDescribe}），是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteApi({
            id: row.id,
            objectVersionNumber: row.objectVersionNumber,
          });
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      if (this.selectedRows.some(e => e.useFlag == 1)) {
        this.$message.warning('您选中了已使用的数据，不可删除');
        return;
      }

      const ids = this.selectedRows.map(e => e.id);
      this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ ids: ids });
        })
        .catch(() => {});
    },
    /* 关闭弹窗 */
    handleClose() {
      this.setDialogVisible = false;
    },

    operationApi(API_DATA) {
      baseDataApi.updateAfterSalesReasons(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    deleteApi(API_DATA) {
      baseDataApi.deleteAfterSalesReasons(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      baseDataApi.batchDeleteAfterSalesReasons(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    reFresh() {
      this.$store.dispatch('values/getAfterRefundOnlyReasons', true);
      this.$refs.BjPage && this.$refs.BjPage.fetchData();
    },
  },
};
</script>
