<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:tableOperation="tableOperationProp">
        <el-col>
          <el-button
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >

          <el-button type="primary" class="main-btn" @click="onAdd">新增信息项</el-button>
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
              <span v-if="col.prop === 'xxxcode'" class="link" @click="toDetail(scope.row)">
                {{ scope.row[col.prop] }}
              </span>

              <span v-else-if="col.prop === 'type'">{{
                INPUT_BOX_TYPE_LIST.bjGet(scope.row[col.prop])
              }}</span>

              <el-switch
                v-else-if="col.prop === 'status'"
                v-model="scope.row[col.prop]"
                :active-value="true"
                :inactive-value="false"
                @change="patchAttributeStatus($event, scope.row)"
              />
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
              <el-button type="text" class="edit-btn" @click="onEdit(props.row)">编辑</el-button>
              <el-button type="text" class="delete-btn" @click="onDelete(props.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import { INPUT_BOX_TYPE_LIST, STATUS_YES_TRUE } from '@/utils/constants';

export default {
  name: 'BusinessGoodsOtherInfo',
  mixins: [pageMiXin],
  data() {
    return {
      INPUT_BOX_TYPE_LIST,
      STATUS_YES_TRUE,
      queryApi: goodsApi.getOtherInfo,
      // 查询参数
      queryParams: {
        attributeCode: null,
        attributeName: null,
      },
      labelList: [
        { label: '信息项编码', prop: 'code', isCheck: true, disabled: false, width: '100px' },
        { label: '信息项名称', prop: 'name', isCheck: true, disabled: false },
        { label: '表单方式', prop: 'type', isCheck: true, disabled: false, width: '100px' },
        { label: '信息内容', prop: 'value', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false, width: '80px' },
        { label: '操作人员', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
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
    /** 更新状态 */
    patchAttributeStatus(v, row) {
      const API_DATA = {
        id: row.id,
        flag: v,
        objectVersionNumber: row.objectVersionNumber,
      };
      this.patchAttributeStatusApi(API_DATA);
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ ids: [row.id], objectVersionNumber: row.objectVersionNumber });
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
      this.$toPage('BusinessGoodsOtherInfoAdd');
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$toPage('BusinessGoodsOtherInfoEdit', { id: row.id });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.dialogVisible = false;
    },
    /* 跳转详情页 */
    toDetail(row) {
      console.log('跳转详情页', row);
    },

    batchDeleteApi(API_DATA) {
      goodsApi.deleteOtherInfo(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    patchAttributeStatusApi(API_DATA) {
      goodsApi.operationOtherInfo(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          // setTimeout(() => {
          //   this.reFresh();
          // }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
