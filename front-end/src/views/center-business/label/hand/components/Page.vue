<template>
  <div class="app-container">
    <BjRouteTabs :route-list="BUSINESS_LABEL_ROUTE_LIST" />
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
      @afterFetchData="handleAfterFetchData"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="标签名称" prop="labelName">
            <el-input
              v-model.trim="queryParams.labelName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="标签类型" prop="labelType">
            <BjSelectLov v-model="queryParams.labelType" :option-list="LABEL_SYS_TYPES" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="标签状态" prop="status">
            <BjSelectLov v-model="queryParams.status" :option-list="STATUS_ENABLED_TRUE" />
          </el-form-item>
        </el-col>

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
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-if="!isSys" v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            v-permission:BusinessLabelHandIndex_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >
          <el-button
            v-permission:BusinessLabelHandIndex_Status
            type="primary"
            :disabled="tableOperationProp.notSelected"
            @click="onStatusSelected(false)"
            >禁用</el-button
          >
          <el-button
            v-permission:BusinessLabelHandIndex_Status
            type="primary"
            :disabled="tableOperationProp.notSelected"
            @click="onStatusSelected(true)"
            >启用</el-button
          >
          <el-button
            v-permission:BusinessLabelHandIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增</el-button
          >
        </el-col>
      </template>
      <template v-slot:tableColumn>
        <template v-for="(col, index) in filterDropCols">
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

              <template v-else-if="col.prop === 'labelType'">
                {{ LABEL_SYS_TYPES.bjGet(scope.row[col.prop]) }}
              </template>

              <el-tag
                v-else-if="col.prop === 'changeFlag'"
                :type="scope.row.changeFlag ? 'success' : 'info'"
                >{{ STATUS_YES_TRUE.bjGet(scope.row.status) }}</el-tag
              >

              <el-tag
                v-else-if="col.prop === 'status'"
                :type="scope.row.status ? 'success' : 'info'"
                >{{ STATUS_ENABLED_TRUE.bjGet(scope.row.status) }}</el-tag
              >

              <template v-else-if="col.prop === 'preview'">
                <BjTag :color="scope.row.textColor" :text="scope.row.textContent" />
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
                v-if="!isSys"
                v-permission:BusinessLabelHandIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(scope.row)"
                >编辑</el-button
              >
              <el-button
                v-if="scope.row.status == 1"
                v-permission="
                  isSys ? 'BusinessLabelSysIndex_Status' : 'BusinessLabelHandIndex_Status'
                "
                type="text"
                @click="onStatus(false, scope.row)"
                >禁用</el-button
              >
              <el-button
                v-if="scope.row.status == 0"
                v-permission="
                  isSys ? 'BusinessLabelSysIndex_Status' : 'BusinessLabelHandIndex_Status'
                "
                type="text"
                @click="onStatus(true, scope.row)"
                >启用</el-button
              >
              <el-button
                v-if="!isSys"
                v-permission:BusinessLabelHandIndex_Delete
                :disabled="scope.row.status"
                type="text"
                class="delete-btn"
                @click="onDelete(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { tagsApi } from '@/api';
import {
  STATUS_ENABLED_TRUE,
  STATUS_YES_TRUE,
  LABEL_SYS_TYPES,
  BUSINESS_LABEL_ROUTE_LIST,
} from '@/utils/constants';
import DataForm from './DataForm.vue';
export default {
  name: 'BusinessLabelPage',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  props: {
    isSys: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      selection: !this.isSys,
      colSpan: 8,
      STATUS_YES_TRUE,
      BUSINESS_LABEL_ROUTE_LIST,
      STATUS_ENABLED_TRUE,
      LABEL_SYS_TYPES,
      queryApi: tagsApi.getLabels,
      // 查询参数
      queryParams: {
        labelName: null,
        labelType: null,
        status: null,
        beginCreationDate: null,
        endCreationDate: null,
        systemLabelFlag: this.isSys,
      },
      labelsVersion: 11,
      labelList: [
        { label: '标签名称', prop: 'labelName', isCheck: true, disabled: false },
        { label: '标签类型', prop: 'labelType', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
        // { label: '模块', prop: 'moduleCode ', isCheck: true, disabled: false },
        { label: '可挪移', prop: 'changeFlag', isCheck: true, disabled: false },
        { label: '标签预览', prop: 'preview', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],

      queryParamsTime: null,
      /** */
    };
  },
  computed: {
    filterDropCols() {
      if (this.isSys) {
        return this.dropCols;
      }
      // 人工标签不展示 可挪移
      return this.dropCols.filter(e => e.prop != 'changeFlag');
    },
  },
  watch: {
    queryParamsTime(v) {
      this.queryParams.beginCreationDate = (v || [])[0];
      this.queryParams.endCreationDate = (v || [])[1];
    },
  },
  mounted() {},
  methods: {
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(row);
    },
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = this.STATUS_ENABLED_TRUE.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} ${row.labelName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            ids: [row.id],
            status: flag,
          });
        })
        .catch(() => {});
    },
    /** 批量禁用操作 */
    onStatusSelected(flag) {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      const ids = this.selectedRows.map(e => e.id);

      const TEXT = this.STATUS_ENABLED_TRUE.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} 所选数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            ids: ids,
            status: flag,
          });
        })
        .catch(() => {});
    },
    /** 删除 */
    onDelete(row) {
      const ids = [row.id];
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ ids: ids });
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      if (this.selectedRows.some(e => e.status)) {
        this.$message.warning('您选中了正在启用的标签，不可删除');
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
    // 禁用API
    operationApi(API_DATA) {
      tagsApi.operationLabels(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      tagsApi.batchDeleteLabels(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    handleAfterFetchData(res) {
      this.$store.dispatch('values/getTags', true);
    },
  },
};
</script>
<style lang="scss">
.el-input-group__append-width-100 .el-input-group__append {
  width: 80px;
  left: -80px;
}
</style>
