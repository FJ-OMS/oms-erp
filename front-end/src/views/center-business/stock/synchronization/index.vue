<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :auto-fetch="false"
      :before-reset-search="beforeResetSearch"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="逻辑仓编码" prop="warehouseCode">
            <el-input
              v-model.trim="queryParams.warehouseCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>
        <el-col :span="colSpan">
          <el-form-item label="逻辑仓名称" prop="warehouseName">
            <el-input
              v-model.trim="queryParams.warehouseName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            /> </el-form-item
        ></el-col>

        <el-col :span="colSpan">
          <el-form-item label="状态" prop="status">
            <BjSelectLov
              v-model="queryParams.status"
              multiple
              lov-code="STOCK.RECORD_STATUS"
            /> </el-form-item
        ></el-col>

        <el-col :span="colSpan">
          <el-form-item label="同步时间">
            <el-date-picker
              v-model="queryParamsTime"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              :picker-options="$pickerOptions()"
              align="right"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']"
            /> </el-form-item
        ></el-col>
      </template>

      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:BusinessStockSynchronizationAdd
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
              <template v-else-if="col.prop === 'status'">
                {{ $store.state.values.STOCK_RECORD_STATUS.bjGet(scope.row[col.prop]) }}
              </template>

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
                v-permission:BusinessStockSynchronizationIndex_Pass
                :disabled="scope.row.status == 'success'"
                type="text"
                @click="onStatus('success', scope.row)"
                >审核通过</el-button
              >
              <!-- <el-button type="text"  @click="onEdit(scope.row)" class='edit-btn'>编辑</el-button>
              <el-button
                :disabled="scope.row.status"
                type="text"

                @click="onDelete(scope.row)"
                 class='delete-btn'>删除</el-button
              > -->
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 新增/编辑弹窗 -->
    <!-- <DataForm ref="DataForm" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" /> -->
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { stockApi } from '@/api';
// import DataForm from './components/DataForm.vue';
// import {  } from '@/utils/constants';
export default {
  name: 'BusinessStockSynchronizationIndex',
  components: {
    // DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      queryApi: stockApi.getAdjustRecords,
      // 查询参数
      queryParams: {
        adjustCode: null,
        endTime: null,
        startTime: null,
        status: [],
        warehouseCode: null,
        warehouseName: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '数据同步单号', prop: 'adjustCode', isCheck: true, disabled: false },
        { label: '逻辑仓编码', prop: 'warehouseCode', isCheck: true, disabled: false },
        { label: '逻辑仓名称', prop: 'warehouseName', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },

        { label: '操作人员', prop: 'lastUpdateName', isCheck: true, disabled: false, width: 135 },
        { label: '操作时间', prop: 'lastUpdateDate', isCheck: true, disabled: false, width: 135 },
        { label: '备注', prop: 'remark', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],

      queryParamsTime: null,
    };
  },
  computed: {},
  watch: {
    queryParamsTime(v) {
      console.log(v);
      this.queryParams.startTime = (v || [])[0];
      this.queryParams.endTime = (v || [])[1];

      console.log(this.queryParams.timeStart, this.queryParams.timeEnd);
    },
  },
  mounted() {
    this.queryParams.ruleId = this.$route.query.id || '';

    this.$refs.BjPage.fetchData();
  },
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 禁用 */
    onStatus(flag, row) {
      this.$confirm(`您正在 审核 ${row.adjustCode}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            id: row.adjustId,
            status: flag,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    // onDelete(row) {
    //   this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.batchDeleteApi(row);
    //     })
    //     .catch(() => {});
    // },
    // /** 批量删除操作 */
    // onDeleteSelected() {
    //   if (!this.selectedRows.length) {
    //     this.$message.warning('请选择');
    //     return;
    //   }
    //   const ids = this.selectedRows.map(e => e.id);
    //   this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.batchDeleteApi({ ids: ids });
    //     })
    //     .catch(() => {});
    // },
    /** 新建 */
    onAdd() {
      this.$toPage('BusinessStockSynchronizationAdd');
      // this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      // this.$refs.DataForm.show(this.$lodash.clone(row));
    },

    operationApi(API_DATA) {
      stockApi.statusAdjustRecords(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // deleteApi(API_DATA) {
    //   stockApi.deleteOnlineShop(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    batchDeleteApi(API_DATA) {
      stockApi.deleteSurplusQtyReplenish(API_DATA).then(res => {
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
