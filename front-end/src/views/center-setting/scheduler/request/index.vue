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
          <el-form-item label="请求编码" prop="concCode">
            <el-input
              v-model.trim="queryParams.concCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="请求名称" prop="concName">
            <el-input
              v-model.trim="queryParams.concName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="请求描述" prop="concDescription">
            <el-input
              v-model.trim="queryParams.concDescription"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="enabledFlag">
            <el-select
              v-model.trim="queryParams.enabledFlag"
              class="full-width"
              placeholder="请选择"
              clearable
            >
              <el-option
                v-for="(item, index) in STATUS_ENABLED_NUM"
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
              <el-button type="text" @click="onAction('分配权限', props.row)">分配权限</el-button>
              <el-button type="text" class="edit-btn" @click="onAction('编辑', props.row)"
                >编辑</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <!-- 新增/编辑弹窗 -->
    <!-- <DataForm ref="DataForm" @reFresh="reFresh" /> -->
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { settingApi } from '@/api';
import { STATUS_ENABLED_NUM } from '@/utils/constants';

// import DataForm from './components/DataForm.vue';

export default {
  name: 'SettingSchedulerRequestIndex',
  // components: {
  //   DataForm,
  // },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_NUM,
      queryApi: settingApi.getRequest,
      // 查询参数
      queryParams: {
        concCode: null,
        concName: null,
        concDescription: null,
        enabledFlag: null,
        // 请求编码：请求定义的唯一标识。支持模糊查询。
        // 请求名称：请求定义的名称。支持模糊查询。
        // 请求描述：请求的描述信息。支持模糊查询。
        // 状态：下拉框选择状态，启用/禁用。
      },

      labelList: [
        // concCode: "HRPT_GENERATE_REPORT"
        // concDescription: "定时生成报表(勿动!)"
        // concName: "定时生成报表"
        // concurrentId: "=ZxDdFxkjXsEXj1t5u5bLQA==="
        // enabledFlag: 1
        // executableCode: "HRPT_GENERATE_REPORT"
        // executableId: "=ZxDdFxkjXsEXj1t5u5bLQA==="
        // executableName: "定时生成报表"
        // objectVersionNumber: 1
        // tenantId: 0
        // tenantName: "HZERO平台"
        { label: '请求编码', prop: 'concCode', isCheck: true, disabled: false },
        { label: '请求名称', prop: 'concName', isCheck: true, disabled: false },
        { label: '请求描述', prop: 'concDescription', isCheck: true, disabled: false },
        { label: '可执行编码', prop: 'executableCode', isCheck: true, disabled: false },
        { label: '可执行名称', prop: 'executableName', isCheck: true, disabled: false },
        // TODO: alarmEmail 没有返回字段
        { label: '报警邮箱', prop: 'alarmEmail', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},

  mounted() {},
  methods: {
    onAction(action, row) {
      switch (action) {
        case '删除':
          this.onDelete(row);
          break;
        case '编辑':
          this.onEdit(row);
          break;
        case '分配权限':
          this.onPermissions(row);
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
      this.$toPage('SettingSchedulerRequestAdd');
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$toPage('SettingSchedulerRequestEdit', { concurrentId: row.concurrentId });
    },
    /** 编辑操作 */
    onPermissions(row) {
      this.$toPage('SettingSchedulerRequestPermissions', {
        concurrentCode: row.concCode,
        concurrentId: row.concurrentId,
        concurrentName: row.concName,
      });
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
