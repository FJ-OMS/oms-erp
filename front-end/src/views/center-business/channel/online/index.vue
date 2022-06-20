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
          <el-form-item label="网店名称" prop="name">
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
          <el-form-item label="渠道" prop="channelTypeCode">
            <BjSelectLov v-model="queryParams.channelTypeCode" lov-code="SCHL.CHANNEL_TYPE" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="渠道仓状态" prop="status">
            <el-select
              v-model.trim="queryParams.status"
              class="full-width"
              placeholder="请选择"
              clearable
            >
              <el-option
                v-for="(item, index) in STATUS_ENABLED_TRUE"
                :key="index"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="网店类型" prop="onlineStoreTypeCode">
            <BjSelectLov v-model="queryParams.onlineStoreTypeCode" lov-code="SCHL.SHOP_TYPE" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="所属公司" prop="orgName">
            <el-input
              v-model.trim="queryParams.orgName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            v-permission:BusinessChannelOnlineList_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >
          <el-button
            v-permission:BusinessChannelOnlineList_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增网店</el-button
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

              <el-tag
                v-else-if="col.prop === 'channelWarehouseStatus'"
                :type="scope.row.channelWarehouseStatus ? 'success' : 'info'"
                >{{ STATUS_ENABLED_TRUE.bjGet(scope.row.channelWarehouseStatus) }}</el-tag
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
                v-permission:BusinessChannelOnlineList_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-if="props.row.channelWarehouseStatus"
                v-permission:BusinessChannelOnlineList_Status
                type="text"
                @click="onStatus(false, props.row)"
                >禁用</el-button
              >
              <el-button
                v-if="!props.row.channelWarehouseStatus"
                v-permission:BusinessChannelOnlineList_Status
                type="text"
                @click="onStatus(true, props.row)"
                >启用</el-button
              >

              <!-- <el-button
                v-permission:BusinessChannelOnlineList_Set
                type="text"
                @click="onSet(props.row)"
                >设置</el-button
              > -->
              <el-button
                v-permission:BusinessChannelOnlineList_Delete
                :disabled="props.row.channelWarehouseStatus"
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
    <DataForm ref="DataForm" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" />

    <!-- 网店设置 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="setDialogVisible"
      :before-close="handleClose"
      width="500px"
      append-to-body
    >
      <el-form
        ref="setForm"
        :inline="false"
        :model="setForm"
        :rules="setRules"
        label-width="140px"
        class="bj-label-colon"
      >
        <el-form-item label="网店名称" prop="name">
          <el-input
            v-model.trim="setForm.name"
            :maxlength="MAX_LENGTH.DEFAULT"
            disabled
            placeholder="请输入"
            clearable
          />
        </el-form-item>
        <el-form-item label="自动确认发货" prop="autoConfirmDay">
          <BjInputNumber v-model.trim="setForm.autoConfirmDay" not-auto-zero>
            <template #append> 天 </template>
          </BjInputNumber>
        </el-form-item>
        <el-form-item label="自动下载商品">
          <el-switch v-model="setForm.autoDownloadGoods" />
        </el-form-item>
        <el-form-item label="自动同步库存">
          <el-switch v-model="setForm.autoSyncInventory" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleSetConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { channelApi } from '@/api';
import { STATUS_ENABLED_TRUE } from '@/utils/constants';
import DataForm from './components/DataForm.vue';

export default {
  name: 'BusinessChannelOnlineIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_TRUE,
      queryApi: channelApi.getOnlineShopPage,
      // 查询参数
      queryParams: {
        name: null,
        channelTypeCode: null,
        onlineStoreTypeCode: null,
        status: null,
        orgName: null,
      },
      title: '网店设置',
      setDialogVisible: false,

      /** 表单-设置 */
      setForm: {
        name: null,
        autoConfirmDay: 0,
        autoDownloadGoods: false,
        autoSyncInventory: false,
        id: null,
        objectVersionNumber: null,
      },
      setRules: {
        autoConfirmDay: [{ required: true, message: '请输入自动确认发货天数', trigger: 'blur' }],
        autoDownloadGoods: [{ required: true, message: '请选择自动下载商品', trigger: 'change' }],
        autoSyncInventory: [{ required: true, message: '请选择自动下载商品', trigger: 'change' }],
        name: [{ required: true, message: '请输入网店名称', trigger: 'blur' }],
      },
      labelsVersion: 11,
      labelList: [
        { label: '网店编码', prop: 'code', isCheck: true, disabled: false },
        { label: '网店名称', prop: 'name', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelTypeMeaning', isCheck: true, disabled: false },
        // { label: '网店状态', prop: 'status', isCheck: true, disabled: false },
        { label: '所属公司', prop: 'orgName', isCheck: true, disabled: false },
        { label: '网店类型', prop: 'onlineStoreTypeMeaning', isCheck: true, disabled: false },
        // { label: '店铺地址', prop: 'url', isCheck: true, disabled: false },

        { label: '渠道仓名称', prop: 'channelWarehouseName', isCheck: true, disabled: false },
        { label: '渠道仓状态', prop: 'status', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },

        { label: '关联门店', prop: 'relationPhysicalShopName', isCheck: true, disabled: false },

        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 130 },
      ],
      selectedRow: null,
    };
  },
  watch: {},
  methods: {
    handleSetConfirm() {
      const API_DATA = this.$lodash.clone(this.setForm);
      delete API_DATA.name;
      console.log('确认', API_DATA);

      this.$refs['setForm'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.setApi(API_DATA);
      });
    },

    /** 新增网店 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      channelApi.getOnlineShopDetails({ id: row.id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$refs.DataForm.show(this.$lodash.clone(res));
        }
      });
    },
    /** 设置 */
    onSet(row) {
      this.selectedRow = this.$lodash.clone(row);
      this.setForm.id = this.selectedRow.id;
      this.setForm.objectVersionNumber = this.selectedRow.objectVersionNumber;
      this.setForm.name = this.selectedRow.name;
      this.setDialogVisible = true;
      channelApi.getOnlineShopSetting({ id: row.id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.setForm = {
            ...res,
          };
        }
      });
    },
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_TRUE.bjGet(flag);
      this.$confirm(
        `您正在 ${TEXT} 网店（${row.name}）的渠道仓（${row.channelWarehouseName}），是否继续?`,
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        },
      )
        .then(() => {
          this.operationApi({
            id: row.id,
            objectVersionNumber: row.objectVersionNumber,
            flag: flag,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(
        `您正在删除 网店（${row.name}）和渠道仓（${row.channelWarehouseName}），是否继续?`,
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        },
      )
        .then(() => {
          this.deleteApi({
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

      if (this.selectedRows.some(e => e.channelWarehouseStatus == true)) {
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
    /* 关闭弹窗 */
    handleClose() {
      this.setDialogVisible = false;
    },
    setApi(API_DATA) {
      channelApi.setOnlineShop(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
            this.reFresh();
          }, 500);
        }
      });
    },

    operationApi(API_DATA) {
      channelApi.operationOnlineShop(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    deleteApi(API_DATA) {
      channelApi.deleteOnlineShop(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      channelApi.batchDeleteOnlineShop(API_DATA).then(res => {
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
