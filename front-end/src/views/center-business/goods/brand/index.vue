<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'80px'"
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
          <el-form-item label="品牌名称" prop="brandName">
            <el-input
              v-model.trim="queryParams.brandName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="品牌编码" prop="code">
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
          <el-form-item label="状态" prop="status">
            <BjSelectLov v-model="queryParams.status" :option-list="STATUS_ENABLED_NUM" />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            v-permission:BusinessGoodsBrandIndex_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >
          <el-button
            v-permission:BusinessGoodsBrandIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增品牌</el-button
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
              <BjKeyImage
                v-else-if="col.prop === 'logo'"
                :url="scope.row[col.prop]"
                height="80px"
              />
              <el-tag
                v-else-if="col.prop === 'status'"
                :type="scope.row.status ? 'success' : 'info'"
                >{{ STATUS_ENABLED_NUM.bjGet(scope.row.status) }}</el-tag
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
            <template slot-scope="scope">
              <el-button
                v-permission:BusinessGoodsBrandIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(scope.row)"
                >编辑</el-button
              >

              <el-button
                v-if="!scope.row.status"
                v-permission:BusinessGoodsBrandIndex_Status
                type="text"
                @click="onStatus(1, scope.row)"
                >启用</el-button
              >
              <el-button
                v-if="scope.row.status"
                v-permission:BusinessGoodsBrandIndex_Status
                type="text"
                @click="onStatus(0, scope.row)"
                >禁用</el-button
              >
              <el-button
                v-permission:BusinessGoodsBrandIndex_Delete
                :disabled="scope.row.status == 1"
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
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import DataForm from './components/DataForm.vue';
import { STATUS_ENABLED_NUM } from '@/utils/constants';
export default {
  name: 'BusinessGoodsBrandIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_NUM,
      colSpan: 6,
      queryApi: goodsApi.getPageBrand,
      // 查询参数
      queryParams: {
        brandName: null,
        code: null,
        status: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '品牌编码', prop: 'code', isCheck: true, disabled: false },

        { label: '品牌名称', prop: 'brandName', isCheck: true, disabled: false },
        { label: '品牌LOGO', prop: 'logo', isCheck: true, disabled: false },
        { label: '生产商', prop: 'manufacturer', isCheck: true, disabled: false },
        { label: '备注', prop: 'remark', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false, width: 80 },

        { label: '操作人员', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'createDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {
    // this.$refs.BjPage.tableData = [{}];
  },
  methods: {
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_NUM.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} ${row.brandName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            ids: [row.id],
            status: flag,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除 ${row.brandName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ ids: [row.id] });
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }
      if (this.selectedRows.some(e => e.status)) {
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
    /** 新建 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },

    operationApi(API_DATA) {
      goodsApi.operationBrand(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          this.reFresh();
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
      goodsApi.batchDeleteBrand(API_DATA).then(res => {
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
<style lang="scss" scoped></style>
