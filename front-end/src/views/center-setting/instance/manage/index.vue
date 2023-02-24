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
          <el-form-item label="事务实例" prop="sagaCode">
            <el-input
              v-model.trim="queryParams.sagaCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="status">
            <el-select
              v-model.trim="queryParams.status"
              class="full-width"
              placeholder="请选择"
              clearable
            >
              <el-option
                v-for="(item, index) in INSTANCE_RESULT_TYPE_LIST"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="关联业务类型" prop="refType">
            <el-input
              v-model.trim="queryParams.refType"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="关联业务ID" prop="refId">
            <el-input
              v-model.trim="queryParams.refId"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
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
            :show-overflow-tooltip="col.prop === 'refId'"
          >
            <template slot-scope="scope">
              <el-link
                v-if="col.prop === 'sagaCode'"
                type="primary"
                :underline="false"
                @click="showDrawer(scope.row)"
              >
                {{ scope.row[col.prop] }}
              </el-link>
              <span v-else-if="col.prop === 'status'">
                <el-tag
                  :type="
                    scope.row.status === 'RUNNING'
                      ? 'success'
                      : scope.row.status === 'FAILED'
                      ? 'danger'
                      : scope.row.status === 'COMPLETED'
                      ? 'info'
                      : scope.row.status === 'NON_CONSUMER'
                      ? 'success'
                      : scope.row.status === 'WAIT_TO_BE_PULLED'
                      ? 'success'
                      : ''
                  "
                  >{{ INSTANCE_RESULT_TYPE_LIST.bjGet(scope.row.status) }}</el-tag
                >
              </span>
              <el-tooltip v-else-if="col.prop === 'completedCount'" placement="top">
                <div slot="content">
                  <div>完成：{{ scope.row.completedCount }}</div>
                  <div>失败：{{ scope.row.failedCount }}</div>
                  <div>进行中：{{ scope.row.runningCount }}</div>
                  <div>等待被拉取：{{ scope.row.waitToBePulledCount }}</div>
                </div>
                <el-button>查看</el-button>
                <el-progress
                  v-if="scope.row.status === 'COMPLETED'"
                  :percentage="0"
                  :show-text="false"
                />
                <el-progress
                  v-if="scope.row.status === 'FAILED'"
                  :percentage="100"
                  status="exception"
                  :show-text="false"
                />
                <el-progress
                  v-if="
                    scope.row.status === 'RUNNING' ||
                    scope.row.status === 'NON_CONSUMER' ||
                    scope.row.status === 'WAIT_TO_BE_PULLED'
                  "
                  :percentage="10"
                  :show-text="false"
                />
              </el-tooltip>
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <el-drawer
      size="80%"
      :visible.sync="drawer"
      direction="rtl"
      destroy-on-close
      :before-close="handleClose"
    >
      <instance-detail
        :data="details"
        :type-list="INSTANCE_RESULT_TYPE_LIST"
        @resetData="onResetData"
      />
    </el-drawer>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { settingApi } from '@/api';
import { INSTANCE_RESULT_TYPE_LIST } from '@/utils/constants';
import instanceDetail from '../components/instance-detail';
const getDefaultForm = () => {
  return {};
};

export default {
  name: 'SettingInstanceManageIndex',
  components: {
    instanceDetail,
  },
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      INSTANCE_RESULT_TYPE_LIST,
      queryApi: settingApi.getInstancesList,
      // 查询参数
      queryParams: {
        searchId: null,
        sagaCode: null,
        status: null,
        refType: null,
        refId: null,
      },
      drawer: false,
      details: {},
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      labelsVersion: 11,
      labelList: [
        { label: '事务实例', prop: 'sagaCode', isCheck: true, disabled: false, width: 180 },
        { label: '事务描述', prop: 'description', isCheck: true, disabled: false, width: 180 },
        { label: '状态', prop: 'status', isCheck: true, disabled: false, width: 80 },
        { label: '进度条', prop: 'completedCount', isCheck: true, disabled: false, width: 200 },
        { label: '开始时间', prop: 'startTime', isCheck: true, disabled: false, width: 135 },
        { label: '关联业务类型', prop: 'refType', isCheck: true, disabled: false, width: 110 },
        { label: '关联业务ID', prop: 'refId', isCheck: true, disabled: false },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    showDrawer(row) {
      settingApi.getInstancesDetail({ id: row.id }).then(res => {
        this.details = res;
        this.drawer = true;
      });
    },
    onResetData(id) {
      settingApi.getInstancesDetail({ id: id }).then(res => {
        console.log(res);
        this.details = res;
      });
    },
    handleClose(done) {
      done();
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-drawer__body {
  overflow: auto;
  padding: 30px;
}
// ::v-deep .el-drawer__container ::-webkit-scrollbar{
//     display: none;
// }
</style>
