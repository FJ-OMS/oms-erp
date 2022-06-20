<template>
  <div class="app-container">
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
          <el-form-item label="租户名称" prop="tenantName">
            <el-input
              v-model.trim="queryParams.tenantName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="enabledFlag">
            <el-select v-model.trim="queryParams.enabledFlag" placeholder="请选择" clearable>
              <el-option
                v-for="item in STATUS_ENABLED_NUM"
                :key="item.label"
                :label="item.label"
                :value="item.value"
              />
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="是否可授权" prop="customMenuFlag ">
            <el-select v-model.trim="queryParams.customMenuFlag" placeholder="请选择" clearable>
              <el-option
                v-for="item in STATUS_YES_NUM"
                :key="item.label"
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
            @click="onDelete"
            >删除</el-button
          >
          <el-button type="primary" class="main-btn" @click="onAdd">创建租户</el-button>
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
              <span v-else-if="col.prop === 'enabledFlag'">{{
                STATUS_ENABLED_NUM.bjGet(scope.row.enabledFlag)
              }}</span>
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
              <el-button type="text" class="edit-btn" @click="onEdit(props.row)">编辑</el-button>
              <el-button type="text" @click="onStatus(1, props.row)">禁用</el-button>
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
import { tenantApi } from '@/api/index';
import { STATUS_ENABLED_NUM, STATUS_YES_NUM } from '@/utils/constants';
export default {
  name: 'SettingTenantPage',
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: tenantApi.getTenants,
      labelList: [
        { label: '租户编码', prop: 'tenantNum', isCheck: true, disabled: false },
        { label: '租户名称', prop: 'tenantName', isCheck: true, disabled: false },
        { label: '管理账户', prop: 'tenantId ', isCheck: true, disabled: false },
        { label: '域名', prop: 'todo', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '是否可授权', prop: 'customMenuFlag', isCheck: true, disabled: false },
        { label: '有效期从', prop: 'todo2', isCheck: true, disabled: false },
        { label: '有效期至', prop: 'todo3', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      STATUS_ENABLED_NUM,
      STATUS_YES_NUM,
      // 查询参数
      queryParams: {
        tenantName: null,
        tenantNum: null,
        enabledFlag: null,
        customMenuFlag: null,
        createDate: [],
      },
    };
  },

  created() {},
  mounted() {},
  methods: {
    /** 创建租户 */
    onAdd() {
      this.$toPage('SettingTenantAdd');
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$toPage('SettingTenantEdit', { id: row.tenantId });
    },
    /** 禁用 */
    onStatus() {
      this.$message.warning('TODO:no api');
      // this.$confirm('即将禁用该数据，是否继续？', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning',
      // })
      //   .then(() => {})
      //   .catch(() => {});
    },
    /** 删除操作 */
    onDelete() {
      this.$message.warning('TODO:no api');
      // this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning',
      // })
      //   .then(() => {})
      //   .catch(() => {});
    },
  },
};
</script>
<style scoped>
.steps {
  margin-bottom: 50px;
}
.treeFilter {
  width: 200px;
  margin: 20px 0;
}
</style>
