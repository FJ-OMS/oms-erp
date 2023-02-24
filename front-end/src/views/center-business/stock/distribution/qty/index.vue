<template>
  <div class="app-container">
    <BjRouteTabs :route-list="STOCK_DISTRIBUTION_ROUTE_LIST" />
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
        <el-col :span="8">
          <el-form-item label="商品编码" prop="spuCode">
            <el-input
              v-model.trim="queryParams.spuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable.
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="商品名称" prop="spuName">
            <el-input
              v-model.trim="queryParams.spuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable.
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="SKU编码" prop="skuCode">
            <el-input
              v-model.trim="queryParams.skuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable.
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="供货仓编码" prop="supportWarehouseCode">
            <el-input
              v-model.trim="queryParams.supportWarehouseCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable.
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="供货仓名称" prop="supportWarehouseName">
            <el-input
              v-model.trim="queryParams.supportWarehouseName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable.
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="商品分类" prop="categoryId">
            <BjGoodsTypeTreeSelect v-model="queryParams.categoryId" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="接收仓编码" prop="receiveWarehouseCode">
            <el-input
              v-model.trim="queryParams.receiveWarehouseCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable.
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="接收仓名称" prop="receiveWarehouseName">
            <el-input
              v-model.trim="queryParams.receiveWarehouseName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable.
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </template>

      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:BusinessStockDistributionQtyAddSku
            type="primary"
            @click="onAddSku"
            >商品分配</el-button
          >
          <el-button
            v-permission:BusinessStockDistributionQtyAddWarehouse
            type="primary"
            @click="onAddWarehouse"
            >批量分配</el-button
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
              <span v-else-if="col.prop === 'code'" class="link" @click="onView(scope.row)">
                {{ scope.row[col.prop] }}
              </span>
              <template v-else-if="col.prop === 'qty'">
                {{ $infinityNumber(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'status'">
                {{ STOCK_DISTRIBUTION_PLAN_STATUS_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="['supportTypeCode', 'receiveTypeCode'].bjIncludes(col.prop)">
                {{ $store.state.values.STOCK_WAREHOUSE_TYPE.bjGet(scope.row[col.prop]) }}
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
                v-permission:BusinessStockDistributionQtyView
                type="text"
                @click="onView(scope.row)"
                >查看</el-button
              >
              <el-button
                v-permission:BusinessStockDistributionQtyEdit
                type="text"
                class="edit-btn"
                @click="onEdit(scope.row)"
                >编辑</el-button
              >
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
import {
  STOCK_DISTRIBUTION_ROUTE_LIST,
  STOCK_DISTRIBUTION_PLAN_STATUS_LIST,
} from '@/utils/constants';
export default {
  name: 'BusinessStockDistributionPlanIndex',
  components: {
    // DataForm,
  },
  mixins: [pageMiXin],

  data() {
    return {
      STOCK_DISTRIBUTION_ROUTE_LIST,
      STOCK_DISTRIBUTION_PLAN_STATUS_LIST,
      queryApi: stockApi.getDistributeSpaces,
      // 查询参数
      queryParams: {
        categoryId: null,
        receiveWarehouseCode: null,
        receiveWarehouseName: null,
        skuCode: null,
        spuCode: null,
        spuName: null,
        supportWarehouseCode: null,
        supportWarehouseName: null,
      },

      labelList: [
        { label: '商品分类', prop: 'categoryName', isCheck: true, disabled: false },
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: '供货仓类型', prop: 'supportTypeCode', isCheck: true, disabled: false },
        { label: '供货仓编码', prop: 'supportCode', isCheck: true, disabled: false },
        { label: '供货仓名称', prop: 'supportName', isCheck: true, disabled: false },

        { label: '仓位数量', prop: 'qty', isCheck: true, disabled: false },

        { label: '接收仓类型', prop: 'receiveTypeCode', isCheck: true, disabled: false },
        { label: '接收仓编码', prop: 'receiveCode', isCheck: true, disabled: false },
        { label: '接收仓名称', prop: 'receiveName', isCheck: true, disabled: false },

        { label: '操作人员', prop: 'lastUpdateName', isCheck: true, disabled: false, width: 135 },
        { label: '操作时间', prop: 'lastUpdateDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.$store.dispatch('values/getValueList', 'STOCK.WAREHOUSE_TYPE');
  },
  methods: {
    /** 添加 */
    onAddSku() {
      this.$toPage('BusinessStockDistributionQtyAddSku');
    },
    /** 添加 */
    onAddWarehouse() {
      this.$toPage('BusinessStockDistributionQtyAddWarehouse');
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$toPage('BusinessStockDistributionQtyEdit', { id: row.id });
    },
    /** 查看 */
    onView(row) {
      this.$toPage('BusinessStockDistributionQtyView', { id: row.id });
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

    // operationApi(API_DATA) {
    //   stockApi.statusDistributePlans(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
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
    // batchDeleteApi(API_DATA) {
    //   stockApi.detailDistributePlans(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
  },
};
</script>
<style lang="scss" scoped></style>
