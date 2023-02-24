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
        <!-- <el-form-item label="租户" prop="tenantId">
          <BjSelectTenant
            v-model.trim="queryParams.tenantId"
            placeholder="请输入"
            disabled
            clearable
          />
        </el-form-item> -->
        <el-form-item label="任务ID" prop="jobId">
          <el-input
            v-model.trim="queryParams.jobId"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="请求编码" prop="concCode">
          <el-input
            v-model.trim="queryParams.concCode"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="请求名称" prop="concName">
          <el-input
            v-model.trim="queryParams.concName"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="周期性" prop="cycleFlag">
          <el-select
            v-model.trim="queryParams.cycleFlag"
            class="full-width"
            placeholder="请选择"
            clearable
          >
            <el-option
              v-for="(item, index) in STATUS_YES_NUM"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="cycleFlag">
          <BjSelectLov v-model="queryParams.cycleFlag" lov-code="HSDR.JOB_STATUS" />
        </el-form-item>
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
              <span v-else-if="col.prop === 'cycleFlag'">
                <el-tag :type="scope.row.cycleFlag == 1 ? 'success' : 'info'">{{
                  STATUS_YES_NUM.bjGet(scope.row.cycleFlag)
                }}</el-tag>
              </span>
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
              <!--
                任务状态 	功能按钮
                正常 	执行``暂停``终止``日志``编辑
                结束 	日志
                暂停 	恢复``终止``日志
                完成 	终止``日志
                错误 	终止``日志
                阻塞 	终止``日志 -->
              <el-button
                v-if="['正常'].includes(props.row.jobStatusMeaning)"
                type="text"
                @click="onAction(props.row, '暂停')"
                >暂停</el-button
              >
              <el-button
                v-if="['暂停'].includes(props.row.jobStatusMeaning)"
                type="text"
                @click="onAction(props.row, '恢复')"
                >恢复</el-button
              >
              <el-button
                v-if="['正常', '暂停', '完成', '错误', '阻塞'].includes(props.row.jobStatusMeaning)"
                type="text"
                @click="onAction(props.row, '终止')"
                >终止</el-button
              >
              <el-button
                v-if="['正常'].includes(props.row.jobStatusMeaning)"
                type="text"
                @click="onAction(props.row, '执行')"
                >执行</el-button
              >

              <el-button type="text" @click="onAction(props.row, '日志')">日志</el-button>
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
import { STATUS_YES_NUM } from '@/utils/constants';

import DataForm from './components/DataForm.vue';

export default {
  name: 'SettingSchedulerConcurrentIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_YES_NUM,
      queryApi: settingApi.getConcurrent,
      // 查询参数
      queryParams: {
        jobId: null,
        concCode: null,
        concName: null,
        tenantId: null,
        jobStatus: null,
        cycleFlag: null,
        // 任务ID：调度任务的ID。
        // 请求编码：关联的请求定义的编码。支持模糊查询。
        // 请求名称：关联的请求定义的名称。支持模糊查询。
        // 租户：LOV选择相应的租户进行查询，租户无该字段
        // 状态：支持结束、正常、暂停、完成、错误、阻塞。
        // 是否周期性：下拉框选择周期性：是/否。
      },

      labelList: [
        { label: '任务ID', prop: 'jobId', isCheck: true, disabled: false },
        // taskId
        { label: '租户', prop: 'tenantName', isCheck: true, disabled: false },
        { label: '请求编码', prop: 'concCode', isCheck: true, disabled: false },
        { label: '请求名称', prop: 'concName', isCheck: true, disabled: false },
        { label: '提交人', prop: 'username', isCheck: true, disabled: false, width: 135 },

        { label: '周期性', prop: 'cycleFlag', isCheck: true, disabled: false },
        { label: '请求参数', prop: 'requestParam', isCheck: true, disabled: false },
        { label: '起始时间', prop: 'startDate', isCheck: true, disabled: false, width: 135 },
        { label: '结束时间', prop: 'endDate', isCheck: true, disabled: false, width: 135 },
        { label: '父任务ID', prop: 'parentId', isCheck: true, disabled: false },
        { label: '状态', prop: 'jobStatusMeaning', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},

  mounted() {},
  methods: {
    onAction(row, action) {
      switch (action) {
        case '暂停':
          this.pauseJobInfo(row);
          break;
        case '恢复':
          this.resumeJobInfo(row);
          break;
        case '终止':
          this.stopJobInfo(row);
          break;
        case '执行':
          this.triggerJobInfo(row);
          break;
        case '日志':
          this.logJobInfo(row);
          break;
      }
    },

    /** 删除操作 */
    // onDelete(row) {
    //   this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.deleteApi(row);
    //     })
    //     .catch(() => {});
    // },
    /** 批量删除操作 */
    onDeleteSelected() {
      //   if (!this.selectedRows.length) {
      //     this.$message.warning('请选择');
      //     return;
      //   }
      //   this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     type: 'warning',
      //   })
      //     .then(() => {
      //       this.deleteApi(this.selectedRows);
      //     })
      //     .catch(() => {});
    },
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    // onEdit(row) {
    //   this.$refs.DataForm.show(this.$lodash.clone(row));
    // },
    // /** ******* API ************ */
    // deleteApi(API_DATA) {
    //   settingApi.deleteExecutables(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },

    // 任务暂停
    pauseJobInfo(row) {
      settingApi.pauseJobInfo({ id: row.jobId }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 恢复任务
    resumeJobInfo(row) {
      settingApi.resumeJobInfo({ id: row.jobId }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 终止任务
    stopJobInfo(row) {
      settingApi.getJobInfoById({ jobId: row.jobId }).then(res => {
        settingApi.stopJobInfo({ id: row.jobId, executorId: res.executorId }).then(res => {
          if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
            this.$message.success('操作成功');
            setTimeout(() => {
              this.reFresh();
            }, 500);
          }
        });
      });
    },
    // 立即执行
    triggerJobInfo(row) {
      settingApi.triggerJobInfo({ id: row.jobId }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    logJobInfo(row) {
      this.$router.push({
        name: 'SettingSchedulerLogIndex',
        query: {
          jobId: row.jobId,
        },
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
