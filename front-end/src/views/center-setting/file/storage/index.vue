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
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="租户" prop="tenantId">
            <BjSelectLov v-model="queryParams.tenantId" lov-code="BJ_TENANTS" /> </el-form-item
        ></el-col>
      </template>

      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:SettingFileStorageIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增</el-button
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
                v-permission:SettingFileStorageIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-permission:SettingFileStorageIndex_Delete
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
  name: 'SettingFileStorageIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,

      STATUS_YES_NUM,
      FILE_STORAGE_TYPE_LIST,
      queryApi: settingApi.getFileStorageConfigs,
      // 查询参数
      queryParams: {
        storageType: 1,
        tenantId: null,
      },
      labelsVerson: 2,
      labelList: [
        { label: '存储编码', prop: 'storageCode', isCheck: true, disabled: false, width: '100px' },
        {
          label: '文件名前缀策略',
          prop: 'prefixStrategyMeaning',
          isCheck: true,
          disabled: false,
        },
        { label: '域名(Domain)', prop: 'domain', isCheck: true, disabled: false, width: '150px' },
        { label: 'EndPoint', prop: 'endPoint', isCheck: true, disabled: false, width: '150px' },
        {
          label: 'AccessKeyId',
          prop: 'accessKeyId',
          isCheck: true,
          disabled: false,
          width: '150px',
        },
        {
          label: 'bucket权限控制',
          prop: 'accessControl',
          isCheck: true,
          disabled: false,
        },
        {
          label: 'bucket前缀',
          prop: 'bucketPrefix',
          isCheck: true,
          disabled: false,
          width: '100px',
        },
        { label: '默认', prop: 'defaultFlag', isCheck: true, disabled: false, width: '100px' }, // 0:不启用，1:启用
        { label: '创建人', prop: 'createdBy', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
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
    /** 批量删除操作 */
    // onDeleteSelected() {
    //   if (!this.selectedRows.length) {
    //     this.$message.warning('请选择');
    //     return;
    //   }

    //   this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.batchDeleteApi(this.selectedRows);
    //     })
    //     .catch(() => {});
    // },
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },
    batchDeleteApi(API_DATA) {
      settingApi.deleteFileStorageConfigs(API_DATA).then(res => {
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
        name: 'SettingTaskLog',
        query: {
          jobId: row.jobId,
        },
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
