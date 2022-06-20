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
          <el-form-item label="仓库名称" prop="name">
            <el-input
              v-model.trim="queryParams.name"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="仓库编码" prop="code">
            <el-input
              v-model.trim="queryParams.code"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="逻辑仓状态" prop="enableFlag">
            <el-select v-model.trim="queryParams.enableFlag" placeholder="请选择" clearable>
              <el-option
                v-for="(item, index) in STATUS_ENABLED_TRUE"
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
            v-permission:BusinessChannelStorageIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增仓库</el-button
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
              <el-tag
                v-else-if="col.prop === 'logicalFlag'"
                :type="scope.row.logicalFlag ? 'success' : 'info'"
                >{{ STATUS_ENABLED_TRUE.bjGet(scope.row.logicalFlag) }}</el-tag
              >
              <span v-else-if="col.prop === 'detailAddress'">{{
                scope.row.provinceName +
                scope.row.cityName +
                scope.row.districtName +
                scope.row.detailAddress
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
                v-permission:BusinessChannelStorageIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-if="props.row.logicalFlag"
                v-permission:BusinessChannelStorageIndex_Status
                type="text"
                @click="onStatus(false, props.row)"
                >禁用</el-button
              >
              <el-button
                v-if="!props.row.logicalFlag"
                v-permission:BusinessChannelStorageIndex_Status
                type="text"
                @click="onStatus(true, props.row)"
                >启用</el-button
              >
              <!-- <el-button
                v-permission:BusinessChannelStorageIndex_Delete
                type="text"
                @click="onDelete(props.row)"
                 class='delete-btn'>删除</el-button
              > -->
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <DataForm ref="DataForm" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { channelApi } from '@/api';
import { STATUS_ENABLED_TRUE } from '@/utils/constants';
import DataForm from './components/DataForm.vue';

export default {
  name: 'BusinessChannelStorageIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_TRUE,
      queryApi: channelApi.getWarehousePage,
      selection: false,
      // 查询参数
      queryParams: {
        name: null,
        code: null,
        enableFlag: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '仓库编码', prop: 'code', isCheck: true, disabled: false },
        { label: '仓库名称', prop: 'name', isCheck: true, disabled: false },
        { label: '联系人名称', prop: 'contactName', isCheck: true, disabled: false },
        { label: '联系人手机', prop: 'contactPhone', isCheck: true, disabled: false },
        { label: '仓库地址', prop: 'detailAddress', isCheck: true, disabled: false },
        { label: '逻辑仓名称', prop: 'logicalWarehouseName', isCheck: true, disabled: false },
        { label: '逻辑仓状态', prop: 'logicalFlag', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    /** 新增门店 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      channelApi.getWarehouseDetails({ id: row.id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          console.log(res);
          this.$refs.DataForm.show(this.$lodash.clone({ ...row, ...res }));
        }
      });
    },

    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_TRUE.bjGet(flag);
      this.$confirm(
        `您正在 ${TEXT} 仓库（${row.name}）的逻辑仓（${row.logicalWarehouseName}），是否继续?`,
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        },
      )
        .then(() => {
          this.operationWarehouse({
            id: row.id,
            flag,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      const ids = [row.id];
      this.$confirm(`您正在删除  ${row.name}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ ids: ids });
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }
      const ids = this.selectedRows.map(e => e.id);
      this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ ids: ids });
        })
        .catch(() => {});
    },
    /* **********************新建仓库************************** */
    /**  */

    // 禁用API
    operationWarehouse(API_DATA) {
      channelApi.operationWarehouse(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      channelApi.batchDeleteWarehouse(API_DATA).then(res => {
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
<style scoped lang="scss">
.dialogForm {
  .el-input {
    width: 240px;
  }
}
</style>
