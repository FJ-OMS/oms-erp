<template>
  <div class="app-container">
    <!-- sort="orderSeq" -->
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
          <el-form-item label="可执行名称" prop="executableName">
            <el-input
              v-model.trim="queryParams.executableName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="可执行描述" prop="exeTypeMeaning">
            <el-input
              v-model.trim="queryParams.exeTypeMeaning"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <!-- <el-col :span="colSpan">
              <el-form-item label="执行器名称" prop="executorName">
          <el-input :maxlength="MAX_LENGTH.DEFAULT"
            v-model.trim="queryParams.executorName"
            placeholder="请输入"
            clearable

            @keyup.enter.native="handleQuery"
          />
        </el-form-item></el-col> -->
        <el-col :span="colSpan">
          <el-form-item label="执行器" prop="executorId">
            <BjSelectLov
              v-model="queryParams.executorId"
              lov-code="BJ_EXECUTOR_ID"
            /> </el-form-item
        ></el-col>
      </template>

      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >

          <el-button type="primary" class="main-btn" @click="onAdd">新增</el-button>
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
              <span v-else-if="col.prop === 'enabledFlag'">
                <el-tag :type="scope.row.enabledFlag == 1 ? 'success' : 'info'">{{
                  STATUS_ENABLED_NUM.bjGet(scope.row.enabledFlag)
                }}</el-tag>
              </span>

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
              <el-button type="text" class="delete-btn" @click="onAction('删除', props.row)"
                >删除</el-button
              >
              <el-button type="text" class="edit-btn" @click="onAction('编辑', props.row)"
                >编辑</el-button
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
import { settingApi } from '@/api';
import { STATUS_ENABLED_NUM } from '@/utils/constants';

import DataForm from './components/DataForm.vue';

export default {
  name: 'SettingSchedulerExecutableIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_NUM,
      queryApi: settingApi.getExecutables,
      // 查询参数
      queryParams: {
        executableName: null,
        executorId: null,
        exeTypeMeaning: null,
        // 可执行名称：可执行的名称。支持模糊查询。
        // 执行器：LOV框选择执行器。
        // 可执行描述：可执行的描述信息。支持模糊查询。
      },

      labelList: [
        { label: '可执行编码', prop: 'executableCode', isCheck: true, disabled: false },
        { label: '可执行名称', prop: 'executableName', isCheck: true, disabled: false },
        { label: '可执行类型', prop: 'exeTypeMeaning', isCheck: true, disabled: false },
        { label: 'JobHandler', prop: 'jobHandler', isCheck: true, disabled: false },
        { label: '失败处理策略', prop: 'failStrategyMeaning', isCheck: true, disabled: false },
        { label: '执行器', prop: 'executorName', isCheck: true, disabled: false },
        { label: '执行器策略', prop: 'executorStrategyMeaning', isCheck: true, disabled: false },
        { label: '可执行描述', prop: 'executableDesc', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  methods: {
    onAction(action, row) {
      switch (action) {
        case '删除':
          this.onDelete(row);
          break;
        case '编辑':
          this.onEdit(row);
          break;
      }
    },

    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteApi(row);
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteApi(this.selectedRows);
        })
        .catch(() => {});
    },
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },
    /** ******* API ************ */
    deleteApi(API_DATA) {
      settingApi.deleteExecutables(API_DATA).then(res => {
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
