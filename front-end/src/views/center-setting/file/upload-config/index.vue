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
      res="listConfig"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="存储类型" prop="storageType">
            <el-select
              v-model.trim="queryParams.storageType"
              class="full-width"
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in FILE_STORAGE_TYPE_LIST"
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
            v-permission:SettingFileUploadConfigIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增</el-button
          >

          <el-button
            v-permission:SettingFileUploadRamIndex
            @click="$toPage('SettingFileUploadRamIndex')"
            >查看容量配置</el-button
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
              <template v-if="false" />
              <span v-else-if="col.prop === 'defaultFlag'">{{
                STATUS_YES_NUM.bjGet(scope.row.defaultFlag)
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
              <el-button
                v-permission:SettingFileUploadConfigIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-permission:SettingFileUploadConfigIndex_Delete
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
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { settingApi } from '@/api';
import { FILE_STORAGE_TYPE_LIST, STATUS_YES_NUM } from '@/utils/constants';
import DataForm from './components/DataForm.vue';

export default {
  name: 'SettingFileUploadConfigIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      STATUS_YES_NUM,
      FILE_STORAGE_TYPE_LIST,
      queryApi: settingApi.getFileCapacityConfigs,
      // 查询参数
      queryParams: {
        storageType: 1,
        tenantId: null,
      },
      labelList: [
        { label: '分组', prop: 'bucketName', isCheck: true, disabled: false },
        { label: '上传目录', prop: 'directory', isCheck: true, disabled: false },
        { label: '文件分类', prop: 'contentTypeMeaning', isCheck: true, disabled: false },
        { label: '文件格式', prop: 'fileFormat', isCheck: true, disabled: false },
        { label: '文件大小限制', prop: 'storageSize', isCheck: true, disabled: false },
        { label: '文件大小单位', prop: 'storageUnitMeaning', isCheck: true, disabled: false },

        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },

  watch: {
    'queryParams.storageType'(v) {
      this.$refs.BjPage.fetchData();
    },
  },
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
          this.batchDeleteApi(row);
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
    /* 关闭弹窗 */
    handleClose() {
      this.dialogVisible = false;
    },

    batchDeleteApi(API_DATA) {
      settingApi.deleteUploadConfigs(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed !== false) {
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
