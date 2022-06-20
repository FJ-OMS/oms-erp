<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :before-reset-search="beforeResetSearch"
      :before-query-search="beforeQuerySearch"
      res="page"
      @afterFetchOriginalData="afterFetchOriginalData"
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="商品编码" prop="spuCode">
            <el-input
              v-model.trim="queryParams.spuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item> </el-col
        ><el-col :span="colSpan">
          <el-form-item label="商品名称" prop="spuName">
            <el-input
              v-model.trim="queryParams.spuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item> </el-col
        ><el-col :span="colSpan">
          <el-form-item label="SKU编码" prop="skuCode">
            <el-input
              v-model.trim="queryParams.skuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item> </el-col
        ><el-col :span="colSpan">
          <el-form-item label="商品分类" prop="categoryId">
            <BjGoodsTypeTreeSelect v-model="queryParams.categoryId" />
          </el-form-item> </el-col
        ><el-col :span="colSpan">
          <el-form-item label="仓库编码" prop="warehouseCode">
            <el-input
              v-model.trim="queryParams.warehouseCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item> </el-col
        ><el-col :span="colSpan">
          <el-form-item label="仓库名称">
            <BjSelectWarehouse v-model="queryParams.warehouseCode" value-code="code" />
          </el-form-item> </el-col
        ><el-col :span="colSpan">
          <el-form-item label="业务类型" prop="recordTypeCodeList">
            <BjSelectLov
              v-model="queryParams.recordTypeCodeList"
              multiple
              lov-code="STOCK.RECORD_TYPE"
            />
          </el-form-item> </el-col
        ><el-col :span="colSpan">
          <el-form-item label="仓库类型" prop="warehouseTypeCodeList">
            <BjSelectLov
              v-model="queryParams.warehouseTypeCodeList"
              multiple
              lov-code="STOCK.WAREHOUSE_TYPE"
            />
          </el-form-item> </el-col
        ><el-col :span="colSpan">
          <el-form-item label="变动时间">
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
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="相关单据" prop="recordCode">
            <el-input
              v-model.trim="queryParams.recordCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </template>

      <template v-slot:tableOperation>
        <!-- <el-col>
          <el-tag effect="plain">入库数：({{ totalData.putNum || 0 }})</el-tag>
          <el-tag effect="plain">出库数：({{ totalData.outNum || 0 }})</el-tag>
          <el-tag effect="plain">差异数：({{ totalData.differenceNum || 0 }})</el-tag>
        </el-col> -->
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
              <template v-else-if="col.prop === 'status'">
                {{ STOCK_LOCK_STATUS_LIST.bjGet(scope.row[col.prop]) }}
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
              <!--
                状态：

待审核，该记录可作废

已审核，该记录可停用

已作废，该记录不可操作

停用，该记录可启用(前提条件生效时间没失效)
               -->
              <el-button
                :disabled="scope.row.status !== 'success'"
                type="text"
                @click="onStatus('close', scope.row)"
                >禁用</el-button
              >
              <el-button
                :disabled="scope.row.status !== 'close'"
                type="text"
                @click="onStatus('start', scope.row)"
                >启用</el-button
              >
              <el-button
                :disabled="scope.row.status !== 'wait'"
                type="text"
                @click="onStatus('abandon', scope.row)"
                >作废</el-button
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
import { STOCK_TRANSFER_VERIFY_TYPE_LIST, STOCK_LOCK_STATUS_LIST } from '@/utils/constants';
export default {
  name: 'BusinessStockChangeIndex',
  components: {
    // DataForm,
  },
  mixins: [pageMiXin],

  data() {
    return {
      colSpan: 6,
      STOCK_TRANSFER_VERIFY_TYPE_LIST,
      STOCK_LOCK_STATUS_LIST,
      queryApi: stockApi.getChangeRecords,
      // 查询参数
      queryParams: {
        beginDate: null,

        endDate: null,

        recordTypeCodeList: [],
        categoryId: null,
        skuCode: null,
        // sort: {},
        spuCode: null,
        spuName: null,
        warehouseCode: null,
        warehouseName: null,
        warehouseTypeCodeList: [],
        recordCode: null,
      },

      labelList: [
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品分类', prop: 'categoryName', isCheck: true, disabled: false, width: 160 },
        { label: '仓库编码', prop: 'warehouseCode', isCheck: true, disabled: false, width: 160 },
        { label: '仓库名称', prop: 'warehouseName', isCheck: true, disabled: false, width: 160 },
        {
          label: '业务类型',
          prop: 'recordTypeMeaning',
          isCheck: true,
          disabled: false,
          width: 160,
        },
        {
          label: '仓库类型',
          prop: 'warehouseTypeMeaning',
          isCheck: true,
          disabled: false,
          width: '120',
        },
        { label: '库存类型', prop: 'qtyTypeMeaning', isCheck: true, disabled: false, width: '80' },
        { label: '库存变动前', prop: 'preRealNum', isCheck: true, disabled: false, width: '90' },
        { label: '库存变动数', prop: 'changeNum', isCheck: true, disabled: false, width: '90' },
        { label: '库存变动后', prop: 'aftRealNum', isCheck: true, disabled: false, width: '90' },
        { label: '相关单据', prop: 'recordCode', isCheck: true, disabled: false, width: 160 },
        { label: '操作人', prop: 'createdName', isCheck: true, disabled: false, width: 135 },
        { label: '操作时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
      ],
      queryParamsTime: null,
      totalData: {},
    };
  },
  computed: {},
  watch: {
    queryParamsTime(v) {
      this.queryParams.beginDate = (v || [])[0];
      this.queryParams.endDate = (v || [])[1];
    },
  },
  mounted() {},
  methods: {
    afterFetchOriginalData(res) {
      // console.log(res);
      // this.totalData = {
      //   differenceNum: res.differenceNum,
      //   outNum: res.outNum,
      //   putNum: res.putNum,
      // };
    },
    beforeQuerySearch(conditionObj) {
      return new Promise(resolve => {
        resolve(conditionObj);
      });
    },
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = this.STOCK_LOCK_STATUS_LIST.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} ${row.warehouseName}的库存变动，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            id: row.id,
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
      this.$toPage('BusinessStockLockAdd');
      // this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      // this.$refs.DataForm.show(this.$lodash.clone(row));
    },
    /** 查看 */
    onView(row) {
      this.$toPage('BusinessStockLockView', { id: row.id });
      // this.$refs.DataForm.show();
    },
    operationApi(API_DATA) {
      stockApi.statusReserveRecords(API_DATA).then(res => {
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
      stockApi.detailReserveRecords(API_DATA).then(res => {
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
<style lang="scss" scoped>
.el-tag {
  margin-right: 10px;
}
</style>
