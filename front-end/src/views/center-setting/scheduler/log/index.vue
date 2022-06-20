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
      :before-reset-search="beforeResetSearch"
      :auto-fetch="false"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="任务ID" prop="jobId">
            <el-input
              v-model.trim="queryParams.jobId"
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
          <el-form-item label="任务描述" prop="description">
            <el-input
              v-model.trim="queryParams.description"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="调度结果" prop="jobResult">
            <BjSelectLov
              v-model="queryParams.jobResult"
              lov-code="HSDR.LOG.JOB_RESULT"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="客户端执行结果" prop="clientResult">
            <BjSelectLov
              v-model="queryParams.clientResult"
              lov-code="HSDR.LOG.CLIENT_RESULT"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="执行器名称" prop="executorName">
            <el-input
              v-model.trim="queryParams.executorName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="调度时间">
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
              <el-button type="text" class="delete-btn" @click="onDelete(props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { settingApi } from '@/api';

export default {
  name: 'SettingSchedulerLogIndex',
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: settingApi.getJobLogs,
      // 查询参数
      queryParams: {
        jobId: null,
        jobCode: null,
        description: null,
        jobResult: null,
        clientResult: null,
        executorName: null,
        timeStart: null,
        timeEnd: null,
      },
      title: '',
      labelList: [
        { label: '日志ID', prop: 'logId', isCheck: true, disabled: false },
        { label: '任务ID', prop: 'jobId', isCheck: true, disabled: false },
        // { label: 'taskId', prop: 'taskId', isCheck: true, disabled: false },
        { label: '任务编码', prop: 'jobCode', isCheck: true, disabled: false },
        { label: '任务描述', prop: 'description', isCheck: true, disabled: false },
        { label: '所属租户', prop: 'tenantName', isCheck: true, disabled: false },

        { label: '调度结果', prop: 'jobResultMeaning', isCheck: true, disabled: false },
        { label: '客户端执行结果', prop: 'clientResultMeaning', isCheck: true, disabled: false },
        { label: '执行器名称', prop: 'executorName', isCheck: true, disabled: false },
        { label: '执行地址', prop: 'address', isCheck: true, disabled: false },
        { label: '开始时间', prop: 'startTime', isCheck: true, disabled: false, width: 135 },
        { label: '结束时间', prop: 'endTime', isCheck: true, disabled: false, width: 135 },

        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      queryParamsTime: null,
    };
  },
  computed: {
    jobId() {
      return this.$route.query.jobId || '';
    },
  },
  watch: {
    queryParamsTime(v) {
      console.log(v);
      this.queryParams.timeStart = (v || [])[0];
      this.queryParams.timeEnd = (v || [])[1];

      console.log(this.queryParams.timeStart, this.queryParams.timeEnd);
    },
  },
  mounted() {
    if (this.jobId) {
      this.queryParams.jobId = this.jobId;
    }
    this.$refs.BjPage.fetchData();
  },
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi(row);
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
    batchDeleteApi(API_DATA) {
      settingApi.deleteJobLogs(API_DATA).then(res => {
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
