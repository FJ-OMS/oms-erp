<template>
  <div class="app-container">
    <!--  sort="orderSeq" -->
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
          <el-form-item label="任务ID" prop="taskId">
            <el-input
              v-model.trim="queryParams.taskId"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="任务编码" prop="jobCode">
            <el-input
              v-model.trim="queryParams.jobCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="任务类型" prop="glueType">
            <BjSelectLov v-model="queryParams.typeCode" lov-code="HSDR.GLUE_TYPE" /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="任务状态" prop="jobStatus">
            <BjSelectLov
              v-model="queryParams.jobStatus"
              lov-code="HSDR.JOB_STATUS"
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

          <el-button type="primary" class="main-btn" @click="onAdd">新增任务</el-button>
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
              <el-switch v-else-if="col.prop === 'status'" v-model="scope.row[col.prop]" />
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
                v-if="['正常'].includes(props.row.jobStatusMeaning)"
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >

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
                @click="pauseJobInfo(props.row)"
                >暂停</el-button
              >
              <el-button
                v-if="['暂停'].includes(props.row.jobStatusMeaning)"
                type="text"
                @click="resumeJobInfo(props.row)"
                >恢复</el-button
              >
              <el-button
                v-if="['正常', '暂停', '完成', '错误', '阻塞'].includes(props.row.jobStatusMeaning)"
                type="text"
                @click="stopJobInfo(props.row)"
                >终止</el-button
              >
              <el-button
                v-if="['正常'].includes(props.row.jobStatusMeaning)"
                type="text"
                @click="triggerJobInfo(props.row)"
                >执行</el-button
              >

              <el-button
                v-if="['结束'].includes(props.row.jobStatusMeaning)"
                type="text"
                class="delete-btn"
                @click="onDelete(props.row)"
                >删除</el-button
              >

              <el-button type="text" @click="logJobInfo(props.row)">日志</el-button>
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
import DataForm from './components/DataForm.vue';

export default {
  name: 'SettingSchedulerTaskIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: settingApi.getJobInfo,
      // 查询参数
      queryParams: {
        taskId: null,
        jobCode: null,
        glueType: null,
        jobStatus: null,
      },

      labelList: [
        { label: '任务ID', prop: 'taskId', isCheck: true, disabled: false },
        { label: '所属租户', prop: 'tenantName', isCheck: true, disabled: false },
        { label: '任务编码', prop: 'jobCode', isCheck: true, disabled: false },
        { label: '任务类型', prop: 'glueTypeMeaning', isCheck: true, disabled: false },

        { label: 'Jobhandler', prop: 'jobHandler', isCheck: true, disabled: false },
        { label: '任务描述', prop: 'description', isCheck: true, disabled: false },
        { label: '状态', prop: 'jobStatusMeaning', isCheck: true, disabled: false },
        // 状态：定时任务的状态，包括结束``正常``暂停``完成``错误``阻塞六种状态。

        // { label: '操作人员', prop: 'status',isCheck: true, disabled: false, width: 135 },
        // { label: '创建时间', prop: 'channelStorehouseStatus',isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  mounted() {},
  methods: {
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi([row]);
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
          this.batchDeleteApi(this.selectedRows);
        })
        .catch(() => {});
    },
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      settingApi.getJobInfoById({ jobId: row.jobId }).then(res => {
        this.$refs.DataForm.show(res);
      });
    },
    batchDeleteApi(API_DATA) {
      settingApi.deleteJobInfo(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
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
