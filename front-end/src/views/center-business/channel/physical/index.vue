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
          <el-form-item label="门店名称" prop="name">
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
          <el-form-item label="门店类型" prop="typeCode">
            <BjSelectLov v-model="queryParams.typeCode" lov-code="SCHL.SHOP_TYPE" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="渠道仓状态" prop="status">
            <BjSelectLov v-model.trim="queryParams.status" :option-list="STATUS_ENABLED_TRUE" />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="负责人" prop="manager">
            <el-input
              v-model.trim="queryParams.manager"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
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
        <el-col :span="colSpan">
          <el-form-item prop="selfPickupFlag">
            <template #label>
              是否自提
              <el-tooltip effect="dark" placement="top-start" content="是否为O2O订单的自提门店">
                <i class="el-icon-question"></i>
              </el-tooltip>
            </template>
            <BjSelectLov v-model.trim="queryParams.selfPickupFlag" :option-list="STATUS_YES_TRUE" />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            v-permission:BusinessChannelPhysicalList_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >
          <el-button
            v-permission:BusinessChannelPhysicalList_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增门店</el-button
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
                v-else-if="col.prop === 'selfPickupFlag'"
                :type="scope.row.selfPickupFlag ? 'success' : 'info'"
                >{{ STATUS_YES_TRUE.bjGet(!!scope.row.selfPickupFlag) }}</el-tag
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
                v-permission:BusinessChannelPhysicalList_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-if="props.row.channelWarehouseStatus"
                v-permission:BusinessChannelPhysicalList_Status
                type="text"
                @click="onStatus(false, props.row)"
                >禁用</el-button
              >
              <el-button
                v-if="!props.row.channelWarehouseStatus"
                v-permission:BusinessChannelPhysicalList_Status
                type="text"
                @click="onStatus(true, props.row)"
                >启用</el-button
              >
              <el-button
                v-permission:BusinessChannelPhysicalList_Delete
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

    <!-- 新增/编辑门店弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { channelApi } from '@/api';
import { STATUS_ENABLED_TRUE, STATUS_YES_TRUE } from '@/utils/constants';

import DataForm from './components/DataForm.vue';

export default {
  name: 'BusinessChannelPhysicalIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_TRUE,
      STATUS_YES_TRUE,
      queryApi: channelApi.getPhysicalShopPage,
      // 查询参数
      queryParams: {
        name: null,
        typeCode: null,
        channelWarehouseStatus: null,
        selfPickupFlag: null,
        status: null,
        orgName: null,
        manager: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '门店编码', prop: 'code', isCheck: true, disabled: false },
        { label: '门店名称', prop: 'name', isCheck: true, disabled: false },
        // { label: '门店状态', prop: 'status', isCheck: true, disabled: false }, // true启用 false禁用
        { label: '所属公司', prop: 'orgName', isCheck: true, disabled: false },
        { label: '门店类型', prop: 'typeCodeMeaning', isCheck: true, disabled: false },
        { label: '门店地址', prop: 'address', isCheck: true, disabled: false },
        { label: '负责人', prop: 'manager', isCheck: true, disabled: false },
        { label: '渠道仓名称', prop: 'channelWarehouseName', isCheck: true, disabled: false },
        { label: '渠道仓状态', prop: 'channelWarehouseStatus', isCheck: true, disabled: false },
        {
          label: '是否为O2O订单自提门店',
          prop: 'selfPickupFlag',
          isCheck: true,
          disabled: false,
          width: 100,
        },
        { label: '创建人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },

  methods: {
    /** 新增门店 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      channelApi.getPhysicalShopDetails({ id: row.id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$refs.DataForm.show(this.$lodash.clone(res));
        }
      });
    },
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_TRUE.bjGet(flag);
      this.$confirm(
        `您正在 ${TEXT} 门店（${row.name}）的渠道仓（${row.channelWarehouseName}），是否继续?`,
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
        `您正在删除 门店（${row.name}）和渠道仓（${row.channelWarehouseName}），是否继续?`,
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
    // 禁用API
    operationApi(API_DATA) {
      channelApi.operationPhysicalShop(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 删除API
    deleteApi(API_DATA) {
      channelApi.deletePhysicalShop(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      channelApi.batchDeletePhysicalShop(API_DATA).then(res => {
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
