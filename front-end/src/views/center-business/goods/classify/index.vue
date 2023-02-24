<template>
  <div class="app-container">
    <div class="flex">
      <div class="flex-left">
        <el-input v-model="filterText" placeholder="搜索分类"> </el-input>
        <h3 class="pointer" @click="handleNodeClick()">所有分类</h3>
        <div class="tree-box">
          <el-tree
            ref="tree"
            :data="treeData"
            :props="defaultProps"
            :filter-node-method="filterNode"
            :expand-on-click-node="false"
            @node-click="handleNodeClick"
          />
        </div>
      </div>
      <div class="flex-right">
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
        >
          <template v-slot:baseForm>
            <el-col :span="colSpan">
              <el-form-item label="分类名称" prop="name">
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
              <el-form-item label="分类编码" prop="code">
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
              <el-form-item label="类目类型" prop="subFlag">
                <BjSelectLov
                  v-model="queryParams.subFlag"
                  :option-list="GOODS_CLASSIFY_TYPES"
                  placeholder="请选择"
                />
              </el-form-item>
            </el-col>
            <el-col :span="colSpan">
              <el-form-item label="所属父类" prop="parentId">
                <BjGoodsTypeTreeSelect ref="BjGoodsTypeTreeSelect" v-model="queryParams.parentId" />
              </el-form-item>
            </el-col>
          </template>
          <template v-slot:tableOperation="tableOperationProp">
            <el-col>
              <el-button
                v-permission:BusinessGoodsClassify_Delete
                type="primary"
                :disabled="tableOperationProp.notSelected"
                class="delete-btn"
                @click="onDeleteSelected"
                >删除</el-button
              >

              <el-button
                v-permission:BusinessGoodsClassify_DownloadTemplate
                type="primary"
                @click="onDownload"
                >下载模板</el-button
              >

              <el-button
                v-permission:BusinessGoodsClassify_ImportData
                type="primary"
                @click="onLead"
                >导入数据</el-button
              >

              <el-button
                v-permission:BusinessGoodsClassify_Add
                type="primary"
                class="main-btn"
                @click="onAdd"
                >新增分类</el-button
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
                    v-else-if="col.prop === 'statusFlag'"
                    :type="scope.row[col.prop] ? 'success' : 'info'"
                    >{{ STATUS_ENABLED_TRUE.bjGet(scope.row[col.prop]) }}</el-tag
                  >
                  <span v-else-if="col.prop === 'subFlag'">{{
                    GOODS_CLASSIFY_TYPES.bjGet(scope.row[col.prop])
                  }}</span>
                  <span v-else-if="col.prop === 'attributeList'">
                    {{ scope.row.attributeList.length }}
                  </span>
                  <!-- <el-switch
                    v-else-if="col.prop === 'status'"
                    v-model="scope.row.status"
                    :active-value="1"
                    :inactive-value="0"
                    @change="onStatus($event, scope.row)"
                  /> -->
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
                <template slot-scope="scope">
                  <!-- <el-button
                  v-permission:BusinessGoodsClassify_Add
                    :disabled="scope.row.subFlag"
                    type="text"
                    @click="onAddChild(scope.row)"
                    class="main-btn"
                >新增子分类</el-button
                  > -->
                  <el-button
                    v-permission:BusinessGoodsClassify_Edit
                    type="text"
                    class="edit-btn"
                    @click="onEdit(scope.row)"
                    >编辑</el-button
                  >
                  <el-button
                    v-if="!scope.row.statusFlag"
                    v-permission:BusinessGoodsClassify_Status
                    type="text"
                    @click="onStatus(true, scope.row)"
                    >启用</el-button
                  >
                  <el-button
                    v-if="scope.row.statusFlag"
                    v-permission:BusinessGoodsClassify_Status
                    type="text"
                    @click="onStatus(false, scope.row)"
                    >禁用</el-button
                  ><el-button
                    v-permission:BusinessGoodsClassify_Delete
                    :disabled="scope.row.hasSubOrSPUFlag || scope.row.statusFlag == true"
                    text="hasSubOrSPUFlag: true 置灰（不可用） false 不置灰（可用）"
                    type="text"
                    class="delete-btn"
                    @click="onDelete(scope.row)"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </template>
          </template>
        </BjPage>
      </div>
    </div>
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFreshData" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import DataForm from './components/DataForm.vue';
import { GOODS_CLASSIFY_TYPES, STATUS_ENABLED_TRUE } from '@/utils/constants';

export default {
  name: 'BusinessGoodsClassifyIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 8,
      GOODS_CLASSIFY_TYPES,
      STATUS_ENABLED_TRUE,
      queryApi: goodsApi.getPageCategory,
      // 查询参数
      queryParams: {
        name: null,
        code: null,
        subFlag: null,
        parentId: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '分类名称', prop: 'name', isCheck: true, disabled: false },
        { label: '分类编码', prop: 'code', isCheck: true, disabled: false },
        { label: '所属父类', prop: 'parentName', isCheck: true, disabled: false },
        { label: '类型', prop: 'subFlag', isCheck: true, disabled: false },
        { label: '绑定属性数量', prop: 'attributeList', isCheck: true, disabled: false },
        { label: '备注', prop: 'remark', isCheck: true, disabled: false },
        { label: '状态', prop: 'statusFlag', isCheck: true, disabled: false },

        // { label: '操作人员', prop: 'createdBy',isCheck: true, disabled: false, width: 135 },
        // { label: '创建时间', prop: 'createDate', isCheck: true, disabled: false, width: 135 },
        // { label: '状态', prop: 'status', isCheck: true, disabled: false },
        // { label: '备注', prop: 'remark', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      /** * 左侧树 */

      defaultProps: {
        children: 'children',
        label: 'name',
      },
      treeData: [],
      filterText: '',
      names: '所有分类',
    };
  },
  computed: {},
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  mounted() {
    // this.$refs.BjPage.tableData = [{}];
    this.reFreshLeftTree();
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    reFreshLeftTree() {
      this.queryParams.parentId = null;
      this.names = '所有分类';
      this.filterText = null;
      goodsApi.getAllCategory().then(res => {
        // 更新分类组件
        this.$refs.BjGoodsTypeTreeSelect.getData();
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
    /**
     * 单击选中：右侧表格显示该公司的所有下属公司
     */
    handleNodeClick(data, node) {
      const names = [];
      while (node) {
        if (node.data.name) names.push(node.data.name);
        node = node.parent;
      }
      names.reverse();
      this.names = names.join(' > ') || '所有分类';

      this.queryParams.parentId = data ? data.id : null;
      this.reFresh();
    },

    onDownload() {},
    onLead() {},
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = flag ? '启用' : '禁用';
      this.$confirm(`您正在 ${TEXT} ${row.name}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      })
        .then(() => {
          this.operationApi({
            id: row.id,
            statusFlag: flag,
            objectVersionNumber: row.objectVersionNumber,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除  ${row.name}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi([row.id]);
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      //   :disabled="scope.row.hasSubOrSPUFlag || scope.row.statusFlag == true"
      if (this.selectedRows.some(e => e.statusFlag)) {
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
          this.batchDeleteApi(ids);
        })
        .catch(() => {});
    },
    /** 添加 */
    onAddChild(row) {
      this.$refs.DataForm.show();
      this.$refs.DataForm.setParentId(row.id);
    },
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      console.log(this.$lodash.clone(row));
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },
    /* 关闭弹窗 */
    handleClose() {
      this.dialogVisible = false;
    },

    operationApi(API_DATA) {
      goodsApi.operationCategory(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');

          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // deleteApi(API_DATA) {
    //   goodsApi.deleteOnlineShop(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    batchDeleteApi(API_DATA) {
      goodsApi.batchDeleteCategory(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFreshData();
          }, 500);
        }
      });
    },
    reFreshData() {
      this.reFresh();
      this.reFreshLeftTree();
    },
  },
};
</script>
<style lang="scss" scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}

.flex {
  flex-wrap: nowrap;
  .flex-left {
    width: 300px;
    height: 100%;
    overflow: auto;
  }
  .flex-right {
    width: calc(100% - 300px);
  }
}

.tree-box {
  overflow: auto;
  min-height: 100%;
}
.org-name {
  margin: 0;
  margin-top: 6px;
  color: #606266;
  font-size: 14px;
}
</style>
