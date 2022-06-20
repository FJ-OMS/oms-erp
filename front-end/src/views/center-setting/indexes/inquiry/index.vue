<!--
 * @Author: your name
 * @Date: 2021-07-21 16:48:35
 * @LastEditTime: 2022-06-07 16:48:39
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\query\list\index.vue
-->
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
          <el-form-item label="编码" prop="indexCode">
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
          <el-form-item label="状态" prop="enabledFlag">
            <el-select
              v-model.trim="queryParams.enabledFlag"
              class="full-width"
              placeholder="请选择"
              clearable
            >
              <el-option
                v-for="(item, index) in INDEXES_TYPE_LIST"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="生效开始时间">
            <el-date-picker
              v-model="queryParams.activeStartTime"
              type="datetime"
              placeholder="请选择"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="生效结束时间">
            <el-date-picker
              v-model="queryParams.activeEndTime"
              type="datetime"
              placeholder="请选择"
            />
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
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button v-permission:SettingInquiryAdd type="primary" class="main-btn" @click="onAdd"
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
                v-permission:SettingInquiryEdit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-permission:SettingInquiry_Delete
                type="text"
                class="delete-btn"
                @click="onDelete(props.row)"
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
import { INDEXES_TYPE_LIST } from '@/utils/constants';
const getDefaultForm = () => {
  return {};
};

export default {
  name: 'SettingInquiryIndex',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      INDEXES_TYPE_LIST,
      queryApi: settingApi.getInquiryList,
      // 查询参数
      queryParams: {
        indexCode: null,
        enabledFlag: null,
        activeStartTime: null,
        activeEndTime: null,
        remark: null,
      },
      drawer: false,
      details: {},
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      labelList: [
        { label: '编码', prop: 'indexCode', isCheck: true, disabled: false },
        { label: '索引', prop: 'indexCode', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '描述', prop: 'remark', isCheck: true, disabled: false },
        {
          label: '生效结束时间',
          prop: 'activeEndTime',
          isCheck: true,
          disabled: false,
          width: 135,
        },
        {
          label: '生效开始时间',
          prop: 'activeStartTime',
          isCheck: true,
          disabled: false,
          width: 135,
        },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  created() {
    // settingApi.getInquiryQuery().then(res => {
    //   console.log(res);
    // });
  },
  mounted() {},
  methods: {
    getList() {},
    onAdd() {
      this.$router.push({
        name: 'SettingInquiryAdd',
        query: {},
      });
    },
    onEdit(row) {
      row = JSON.stringify(row);
      this.$router.push({
        name: 'SettingInquiryEdit',
        query: {
          form: row,
        },
      });
    },
    onDelete(row) {
      // console.log(row);
      this.$alert('确认删除选中行？', '提示', {
        confirmButtonText: '确定',
        callback: action => {
          // console.log(action);
          if (action === 'confirm') {
            settingApi.deleteInquiry({ configId: row.configId, _token: row._token }).then(res => {
              this.msgSuccess('删除成功');
              this.reFresh();
            });
          }
        },
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
