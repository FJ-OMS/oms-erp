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
          <el-form-item label="经销商名称" prop="dealerName">
            <el-input
              v-model.trim="queryParams.dealerName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态" prop="status">
            <el-select v-model.trim="queryParams.status" placeholder="请选择" clearable>
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
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            v-permission:BusinessChannelDealerIndex_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >
          <el-button
            v-permission:BusinessChannelDealerIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增经销商</el-button
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
                v-else-if="col.prop === 'status'"
                :type="scope.row.status ? 'success' : 'info'"
                >{{ STATUS_ENABLED_TRUE.bjGet(scope.row.status) }}</el-tag
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
                v-permission:BusinessChannelDealerIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-if="props.row.status"
                v-permission:BusinessChannelDealerIndex_Status
                type="text"
                @click="onStatus(false, props.row)"
                >禁用</el-button
              >
              <el-button
                v-if="!props.row.status"
                v-permission:BusinessChannelDealerIndex_Status
                type="text"
                @click="onStatus(true, props.row)"
                >启用</el-button
              >
              <el-button
                v-permission:BusinessChannelDealerIndex_Delete
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
    <DataForm ref="DataForm" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { channelApi } from '@/api';
import { STATUS_ENABLED_TRUE } from '@/utils/constants';
import DataForm from './components/DataForm.vue';
export default {
  name: 'BusinessChannelDealerIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_TRUE,
      queryApi: channelApi.getDealerPage,
      // 查询参数
      queryParams: { dealerName: null, status: null },

      labelList: [
        { label: '经销商编码', prop: 'dealerCode', isCheck: true, disabled: false },
        { label: '经销商名称', prop: 'dealerName', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
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
      channelApi.getDealerDetails({ id: row.id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$refs.DataForm.show(this.$lodash.clone(res));
        }
      });
    },

    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_TRUE.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} ${row.dealerName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationDealer({
            id: row.id,
            objectVersionNumber: row.objectVersionNumber,
            flag: flag,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在 删除 ${row.dealerName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.deleteDealer({
            id: row.id,
            objectVersionNumber: row.objectVersionNumber,
          });
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      if (this.selectedRows.some(e => e.status == true)) {
        this.$message.warning('您选中了正在启用的数据，不可删除');
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
    /* **********************新建经销商************************** */

    // 禁用API
    operationDealer(API_DATA) {
      channelApi.operationDealer(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 删除API
    deleteDealer(API_DATA) {
      channelApi.deleteDealer(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      channelApi.batchDeleteDealer(API_DATA).then(res => {
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
