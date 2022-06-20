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
          <el-form-item label="执行器编码" prop="executorCode">
            <el-input
              v-model.trim="queryParams.executorCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
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
          <el-form-item label="注册方式" prop="executorType">
            <el-select
              v-model.trim="queryParams.executorType"
              class="full-width"
              placeholder="请选择"
              clearable
            >
              <el-option
                v-for="(item, index) in EXECUTOR_TYPE_LIST"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select> </el-form-item
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

          <el-button type="primary" class="main-btn" @click="onAdd">新增执行器</el-button>
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
              <span v-else-if="col.prop === 'statusMeaning'">
                <el-tag :type="scope.row.statusMeaning == '在线' ? 'success' : 'info'">{{
                  scope.row.statusMeaning
                }}</el-tag>
              </span>

              <span v-else-if="col.prop === 'executorType'">
                {{ EXECUTOR_TYPE_LIST.bjGet(scope.row.executorType) }}
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
              <el-button type="text" @click="onAction('配置', props.row)">配置</el-button>
              <el-button type="text" class="edit-btn" @click="onAction('编辑', props.row)"
                >编辑</el-button
              >
              <el-button
                v-show="props.row.status == 'ONLINE'"
                type="text"
                @click="onAction('下线', props.row)"
                >下线</el-button
              >
              <el-button
                v-show="props.row.status != 'ONLINE'"
                type="text"
                @click="onAction('上线', props.row)"
                >上线</el-button
              >

              <el-button type="text" class="delete-btn" @click="onAction('删除', props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
    <!-- executorsConfigList-->
    <ExecutorsConfig ref="ExecutorsConfig" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { settingApi } from '@/api';
import { EXECUTOR_TYPE_LIST } from '@/utils/constants';

import DataForm from './components/DataForm.vue';
import ExecutorsConfig from './components/ExecutorsConfig.vue';

export default {
  name: 'SettingSchedulerExecutorIndex',
  components: {
    DataForm,
    ExecutorsConfig,
  },
  mixins: [pageMiXin],
  data() {
    return {
      EXECUTOR_TYPE_LIST,
      queryApi: settingApi.getExecutors,
      // 查询参数
      queryParams: {
        executorCode: null,
        executorName: null,
        executorType: null,

        // 执行器编码：执行器的唯一标识，支持模糊查询。
        // 执行器名称：执行器的名称，支持模糊查询。
        // 注册方式：下拉框选择注册方式。 执行器地址类型：0=自动注册、1=手动录入 ,
      },

      labelList: [
        { label: '排序', prop: 'orderSeq', isCheck: true, disabled: false },
        { label: '所属租户', prop: 'tenantName', isCheck: true, disabled: false },
        { label: '执行器编码', prop: 'executorCode', isCheck: true, disabled: false },
        { label: '执行器名称', prop: 'executorName', isCheck: true, disabled: false },
        { label: '注册方式', prop: 'executorType', isCheck: true, disabled: false },
        { label: '服务', prop: 'serverName', isCheck: true, disabled: false },
        { label: 'OnLine机器地址', prop: 'addressList', isCheck: true, disabled: false },
        { label: '状态', prop: 'statusMeaning', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},

  mounted() {},
  methods: {
    onAction(action, row) {
      switch (action) {
        case '配置':
          this.$refs.ExecutorsConfig.show(row);
          break;
        case '下线':
          this.updateStatusApi(row, 'OFFLINE');
          break;
        case '上线':
          this.updateStatusApi(row, 'ONLINE');
          break;
        case '执行':
          this.$message.warning('TODO: no api');
          break;
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
    updateStatusApi(row, status) {
      const API_DATA = this.$lodash.clone(row);
      API_DATA['_innerMap'] = {};
      API_DATA['status'] = status;
      this.updateApi(API_DATA);
    },
    /** ******* API ************ */
    // 修改API
    updateApi(API_DATA) {
      settingApi.updateExecutors(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.dialogVisible = false;
            this.reFresh();
          }, 500);
        }
      });
    },
    deleteApi(API_DATA) {
      settingApi.deleteExecutors(API_DATA).then(res => {
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
