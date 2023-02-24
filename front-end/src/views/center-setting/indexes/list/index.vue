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
            </el-select>
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:SettingIndexesConfigListAdd
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新 增</el-button
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
                >{{ STATUS_ENABLED_NUM.bjGet(scope.row.enabledFlag) }}</el-tag
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
                v-permission:SettingIndexesConfigListEdit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-if="props.row.enabledFlag === 0"
                v-permission:SettingIndexesConfigList_Status
                type="text"
                @click="onUpdata(props.row, 1)"
                >启用</el-button
              >
              <el-button
                v-if="props.row.enabledFlag === 1"
                v-permission:SettingIndexesConfigList_Status
                type="text"
                @click="onUpdata(props.row, 0)"
                >禁用</el-button
              >
              <el-button type="text" @click="copy(props.row)">复制索引</el-button>
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
import { STATUS_ENABLED_NUM } from '@/utils/constants';
const getDefaultForm = () => {
  return {};
};

export default {
  name: 'SettingIndexesConfigListIndex',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      STATUS_ENABLED_NUM,
      queryApi: settingApi.getIndexesList,
      // 查询参数
      queryParams: {
        indexCode: null,
        remark: null,
        enabledFlag: null,
      },
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      labelsVersion: 11,
      labelList: [
        { label: '索引', prop: 'indexCode', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '描述', prop: 'remark', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createUser', isCheck: true, disabled: false, width: 135 },

        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '最近更新人', prop: 'updateUser', isCheck: true, disabled: false, width: 135 },
        {
          label: '最近更新时间',
          prop: 'lastUpdateDate',
          isCheck: true,
          disabled: false,
          width: 135,
        },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 149 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    onAdd() {
      this.$router.push({
        name: 'SettingIndexesConfigListAdd',
        query: {},
      });
    },
    onEdit(row) {
      row = JSON.stringify(row);
      this.$router.push({
        name: 'SettingIndexesConfigListEdit',
        query: {
          form: row,
        },
      });
    },
    copy(row) {
      this.$copyText(row.indexCode).then(
        e => {
          this.msgSuccess('复制成功');
        },
        function(e) {
          this.msgError('复制失败');
        },
      );
    },
    onUpdata(row, enabledFlag) {
      settingApi
        .updataIndexes({
          indexId: row.indexId,
          indexCode: row.indexCode,
          enabledFlag: enabledFlag,
          _token: row._token,
        })
        .then(res => {
          if (!res.failed) {
            row.enabledFlag = enabledFlag;
            this.msgSuccess('修改成功');
          }
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
