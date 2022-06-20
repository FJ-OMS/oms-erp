<!--
 * @Author: your name
 * @Date: 2021-07-22 09:42:30
 * @LastEditTime: 2022-06-07 16:53:22
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\indexes\indexes-sync.vue
-->
<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'100px'"
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
          <el-form-item label="索引" prop="indexCode">
            <el-input
              v-model.trim="queryParams.indexCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="编码" prop="syncConfCode ">
            <el-input
              v-model.trim="queryParams.syncConfCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="来源类型" prop="sourceFromType">
            <el-select
              v-model.trim="queryParams.sourceFromType"
              class="full-width"
              placeholder="请选择"
              clearable
            >
              <!-- 暂时写死 -->
              <el-option label="INTERFACE" value="INTERFACE" />
              <el-option label="DB" value="DB" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="描述" prop="remark">
            <el-input
              v-model.trim="queryParams.remark"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="数据来源" prop="sourceFromCode">
            <el-input
              v-model.trim="queryParams.sourceFromCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:SettingIndexesSyncAdd
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新 建</el-button
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
              <el-tag
                v-if="col.prop === 'enabledFlag'"
                :type="scope.row.enabledFlag === 1 ? 'success' : 'info'"
                >{{ INDEXES_TYPE_LIST.bjGet(scope.row.enabledFlag) }}</el-tag
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
                v-permission:SettingIndexesSyncEdit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
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
import { INDEXES_TYPE_LIST } from '@/utils/constants';
const getDefaultForm = () => {
  return {};
};

export default {
  name: 'SettingIndexesSyncIndex',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      INDEXES_TYPE_LIST,
      queryApi: settingApi.getIndexesSyncList,
      // 查询参数
      queryParams: {
        indexCode: null,
        syncConfCode: null,
        sourceFromType: null,
        remark: null,
        sourceFromCode: null,
      },
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      labelList: [
        { label: '索引', prop: 'indexCode', isCheck: true, disabled: false },
        { label: '编码', prop: 'syncConfCode', isCheck: true, disabled: false },
        { label: '来源类型', prop: 'sourceFromType', isCheck: true, disabled: false },
        { label: '数据来源', prop: 'sourceFromCode', isCheck: true, disabled: false },
        { label: '描述', prop: 'remark', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    onAdd() {
      this.$router.push({
        name: 'SettingIndexesSyncAdd',
        query: {},
      });
    },
    onEdit(row) {
      this.$router.push({
        name: 'SettingIndexesSyncEdit',
        query: {
          syncConfId: row.syncConfId,
        },
      });
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
