<template>
  <div class="app-container">
    <BjRouteTabs :route-list="SETTING_ORGANIZATION_ROUTE_LIST" />
    <div class="flex">
      <div class="group-tree">
        <h3 class="pointer" @click="handleNodeClick()">{{ group.groupName }}</h3>
        <el-tree
          ref="tree"
          :data="treeData"
          :props="defaultProps"
          :expand-on-click-node="false"
          class="show-tree"
          @node-click="handleNodeClick"
        >
          <template slot-scope="{ data }">
            <span :title="data.orgName" v-text="data.orgName" />
          </template>
        </el-tree>
      </div>
      <div style="width: 100%">
        <BjPage
          ref="BjPage"
          :query-params="queryParams"
          :col-span="colSpan"
          :label-width="'120px'"
          :get-action="queryApi"
          :serial="serial"
          :selection="selection"
          :before-reset-search="reFreshLeftTree"
          @handleSelectionChange="handleSelectionChange"
          @showSetting="SortableTable.show"
          @header-dragend="SortableTable.handleHeaderDragend"
          @header-contextmenu="SortableTable.handleHeaderContextmenu"
          @afterFetchData="afterFetchData"
        >
          <template v-slot:baseForm>
            <el-col :span="colSpan">
              <el-form-item label="公司名称" prop="orgName">
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
              <el-form-item label="状态" prop="status">
                <el-select v-model.trim="queryParams.status" placeholder="请选择" clearable>
                  <el-option
                    v-for="item in STATUS_ENABLED_TRUE"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="所属上级公司" prop="pid">
                <BjCompanyTreeSelect
                  ref="BjCompanyTreeSelect"
                  v-model="queryParams.pid"
                  class="full-width"
                  placeholder="请选择"
                  :flag="false"
                />
              </el-form-item>
            </el-col>
          </template>
          <template v-slot:tableOperation="tableOperationProp">
            <el-col>
              <el-button
                v-permission:SettingUserOrganizationCompany_Delete
                type="primary"
                :disabled="tableOperationProp.notSelected"
                class="delete-btn"
                @click="onDeleteSelected"
                >删除</el-button
              >
              <el-button
                v-permission:SettingUserOrganizationCompany_Add
                type="primary"
                class="main-btn"
                @click="onAdd"
                >新增公司</el-button
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
                    v-permission:SettingUserOrganizationCompany_Edit
                    type="text"
                    class="edit-btn"
                    @click="onEdit(props.row)"
                    >编辑</el-button
                  >
                  <el-button
                    v-if="props.row.status"
                    v-permission:SettingUserOrganizationCompany_Status
                    type="text"
                    @click="onStatus(false, props.row)"
                    >禁用</el-button
                  >
                  <el-button
                    v-if="!props.row.status"
                    v-permission:SettingUserOrganizationCompany_Status
                    type="text"
                    @click="onStatus(true, props.row)"
                    >启用</el-button
                  >
                  <el-button
                    v-permission:SettingUserOrganizationCompany_Delete
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
    </div>
    <DataForm ref="DataForm" :group="group" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { channelApi } from '@/api';
import { STATUS_ENABLED_TRUE, SETTING_ORGANIZATION_ROUTE_LIST } from '@/utils/constants';

import DataForm from './components/DataForm.vue';
export default {
  name: 'SettingManageOrganizationCompany',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_TRUE,
      SETTING_ORGANIZATION_ROUTE_LIST,
      queryApi: channelApi.getSalesOrganizationPage,
      // 查询参数
      queryParams: {
        orgName: null,
        pid: null,
        status: null,
      },
      group: {
        creationDate: null,
        createdBy: null,
        lastUpdateDate: null,
        lastUpdatedBy: null,
        objectVersionNumber: null,
        _token: null,
        id: '=gVBsVrVQVqb1IQBLeIDbC-mWrIVMJ7kUIy0HjF5CVQU==',
        groupName: null,
        groupShortName: null,
        logo: null,
        favicon: null,
      },

      labelList: [
        { label: '公司编码', prop: 'orgCode', isCheck: true, disabled: false },
        { label: '公司名称', prop: 'orgName', isCheck: true, disabled: false },
        { label: '所属上级公司', prop: 'superiorName', isCheck: true, disabled: false },
        { label: '集团', prop: 'groupName', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
        // { label: '创建人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        // { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],

      /** * 左侧树 */

      defaultProps: {
        children: 'children',
        label: 'orgName',
      },
      treeData: [],
      needAfterFetchData: true,
    };
  },
  computed: {},

  mounted() {
    this.getGroup();
    this.reFreshLeftTree();
  },
  methods: {
    /** 获取组织架构 */
    getGroup() {
      channelApi
        .getDefaultCompany()
        .then(res => {
          this.group = res;
        })
        .catch(err => {
          console.log(err);
        });
    },
    handleNodeClick(data) {
      this.needAfterFetchData = false;
      this.queryParams.pid = data ? data.id : null;
      this.reFresh();
    },
    /** 获取组织架构树 */
    reFreshLeftTree() {
      channelApi.getSalesOrganizationTree({ flag: false }).then(res => {
        this.treeData = (res || []).map(e => {
          if (!e.children) {
            delete e.children;
          }
          if (e.children && !e.children.length) {
            delete e.children;
          }
          return e;
        });
      });

      return new Promise(resolve => {
        resolve();
      });
    },
    // 更新列表时，同时更新 公司树
    afterFetchData() {
      if (this.needAfterFetchData) {
        this.reFreshLeftTree();
        this.$refs.BjCompanyTreeSelect.reFresh();
      }
      this.needAfterFetchData = true;
    },
    /** 新增门店 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      channelApi.getSalesOrganizationDetails({ id: row.id }).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$refs.DataForm.show(this.$lodash.clone(res));
        }
      });
    },

    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_TRUE.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} ${row.orgName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationPhysicalShop({
            id: row.id,
            objectVersionNumber: row.objectVersionNumber,
            flag: flag,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除 ${row.orgName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ ids: [row.id] });
          // this.deletePhysicalShop({
          //   id: row.id,
          //   objectVersionNumber: row.objectVersionNumber,
          // });
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      if (this.selectedRows.some(e => e.status == 1)) {
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
    /* **********************新建销售组织************************** */
    /**  */
    handleClose() {
      this.dialogVisible = false;
    },

    // 禁用API
    operationPhysicalShop(API_DATA) {
      channelApi.operationSalesOrganization(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 删除API
    deletePhysicalShop(API_DATA) {
      channelApi.deleteSalesOrganization(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    batchDeleteApi(API_DATA) {
      channelApi.batchDeleteSalesOrganization(API_DATA).then(res => {
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

.flex {
  flex-wrap: nowrap;
  .group-tree {
    width: 250px;
    overflow: auto;
  }
}
.show-tree {
  font-size: 14px;
}
::v-deep .el-tree .el-tree-node__content {
  width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: block;
}
</style>
