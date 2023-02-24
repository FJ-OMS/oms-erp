<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'115px'"
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
          <el-form-item label="商品编码" prop="spuCode">
            <el-input
              v-model.trim="queryParams.spuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品名称" prop="spuName">
            <el-input
              v-model.trim="queryParams.spuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <template>
          <el-col :span="colSpan">
            <el-form-item label="仓库编码" prop="warehouseCode">
              <el-input
                v-model.trim="queryParams.warehouseCode"
                :maxlength="MAX_LENGTH.DEFAULT"
                placeholder="请输入"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </el-col>
          <el-col :span="colSpan">
            <el-form-item label="仓库名称" prop="warehouseId">
              <BjSelectWarehouse v-model="queryParams.warehouseId" multiple />
            </el-form-item>
          </el-col>
        </template>
        <el-col :span="colSpan">
          <el-form-item label="SKU编码" prop="skuCode">
            <el-input
              v-model.trim="queryParams.skuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品分类" prop="categoryIdList">
            <BjGoodsTypeTreeSelect v-model="queryParams.categoryIdList" multiple />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="仓库类型" prop="warehouseTypeCode">
            <BjSelectLov
              v-model="queryParams.warehouseTypeCode"
              multiple
              lov-code="STOCK.WAREHOUSE_TYPE"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="库存是否大于0" prop="exceedZero">
            <BjSelectLov v-model="queryParams.exceedZero" constant-key="STATUS_YES_TRUE" />
          </el-form-item>
        </el-col>
      </template>
      <!-- <template v-slot:tableOperation>
        <el-col>
            <el-button type="primary"  @click="onAdd">添 加</el-button>
            <el-button type="primary"  @click="onDown">下载模板</el-button>
          </el-col>
        </div>
      </template> -->
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
              <span v-else-if="col.prop === 'typeCode'">{{
                $store.state.values.STOCK_WAREHOUSE_TYPE.bjGet(scope.row[col.prop])
              }}</span>
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <el-table-column
            v-if="col.prop === 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            align="center"
          >
            <!-- <template slot-scope="props"> -->
            <!-- <el-button type="text"  @click="onUpdata(props.row)">更 新</el-button>
              <el-button type="text"  @click="onSync(props.row)">同 步</el-button> -->
            <!-- </template> -->
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 添加库存初始化弹窗 -->
    <!-- <DataForm ref="DataForm" @reFresh="reFresh" /> -->
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { stockApi } from '@/api';
// import { STOCK_WAREHOUSE_TYPE_SON_2_LIST } from '@/utils/constants';
// import DataForm from './components/DataForm.vue';
const getDefaultForm = () => {
  return {};
};
export default {
  name: 'BusinessStockSearchIndex',
  // components: { DataForm },
  mixins: [pageMiXin],
  data() {
    return {
      // STOCK_WAREHOUSE_TYPE_SON_2_LIST,
      queryApi: stockApi.getGoodsStockPage,
      colSpan: 6,
      // 查询参数
      queryParams: {
        categoryIdList: [],
        exceedZero: null,
        skuCode: null,
        spuCode: null,
        spuName: null,
        warehouseCode: null,
        warehouseId: [],
        warehouseTypeCode: [],
      },
      // 表单参数
      form: getDefaultForm(),
      // 表单校验
      rules: {},
      labelsVersion: 11,
      labelList: [
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        {
          label: '仓库编码',
          prop: 'warehouseCode',
          isCheck: true,
          disabled: false,
          width: '160px',
        },
        {
          label: '仓库名称',
          prop: 'warehouseName',
          isCheck: true,
          disabled: false,
          width: '160px',
        },
        { label: '在库库存', prop: 'stockNum', isCheck: true, disabled: false, width: '80px' },
        { label: '仓库类型', prop: 'typeCode', isCheck: true, disabled: false, width: '160px' },
        { label: '商品分类', prop: 'categoryName', isCheck: true, disabled: false, width: '160px' },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: '品牌名称', prop: '', isCheck: true, disabled: false },
        // { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129},
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.$refs.BjPage.tableData = [{}];
  },
  methods: {
    handleConfirm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        alert('submit!');
      });
    },
    /** 创建库存 */
    onAdd() {
      // this.$refs.DataForm.show();
    },
    /** 下载模板 */
    onDown() {},
    // /** 更新 */
    // onUpdata() {},
    // /** 同步 */
    // onSync() {},
  },
};
</script>
