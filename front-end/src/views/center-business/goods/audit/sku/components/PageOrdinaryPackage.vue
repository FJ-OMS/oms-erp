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
      @handleSelectionChange="handleSelectionChange"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
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
        <el-col :span="colSpan">
          <el-form-item label="商品类别" prop="spuType">
            <BjSelectLov
              v-model="queryParams.spuType"
              :option-list="GOODS_TYPE_LIST"
              placeholder="请选择"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="商品分类" prop="categoryIdList">
            <BjGoodsTypeTreeSelect v-model="queryParams.categoryIdList" :limit="1" multiple />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="状态">
            <BjSelectLov
              v-model="tempStatus"
              :option-list="GOODS_AUDIT_STATUS_STRING_LIST"
              placeholder="请选择"
              multiple
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation> </template>
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
            :show-overflow-tooltip="col.prop !== 'bjTableAction'"
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <!-- <template v-else-if="col.prop === 'skuName'">
                <span class="link">{{ scope.row[col.prop] }}</span>
              </template> -->
              <template v-else-if="col.prop === 'spuType'">
                {{ GOODS_TYPE_LIST.bjGet(scope.row[col.prop]) }}
              </template>
              <template v-else-if="col.prop === 'materialMainImages'">
                <BjViewUrl height="80px" :url="(scope.row[col.prop] || [])[0]" preview />
              </template>
              <el-tag v-else-if="col.prop === 'status'">
                {{ GOODS_AUDIT_STATUS_LIST.bjGet(scope.row[col.prop]) }}
              </el-tag>
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
                v-permission:BusinessGoodsAuditSkuOrdinaryView
                type="text"
                @click="onLook(props.row)"
                >查 看</el-button
              >
              <el-button
                v-if="props.row.status == 2 || props.row.status == 4"
                v-permission:BusinessGoodsAuditSkuOrdinaryView
                type="text"
                @click="onAudit(props.row)"
                >审 核</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
  </div>
</template>

<script>
import { goodsApi } from '@/api';
import pageMiXin from '@/utils/page-mixin.js';
import { GOODS_TYPE_LIST, GOODS_AUDIT_STATUS_LIST, GOODS_COMBO_TYPES } from '@/utils/constants';
const DEFAULT_STATUS = [...GOODS_AUDIT_STATUS_LIST[0].value];
const GOODS_AUDIT_STATUS_STRING_LIST = [
  ...GOODS_AUDIT_STATUS_LIST.map(e => {
    return {
      ...e,
      value: e.value + '',
    };
  }),
];
const DEFAULT_STATUS_STRING = [GOODS_AUDIT_STATUS_STRING_LIST[0].value];
export default {
  name: 'BusinessGoodsAuditSkuPageOrdinaryPackage',
  mixins: [pageMiXin],
  props: {
    setFlag: {
      type: [String, Number],
      default: null,
    },
  },
  data() {
    return {
      colSpan: 6,
      selection: false,
      GOODS_TYPE_LIST,
      GOODS_AUDIT_STATUS_LIST,
      GOODS_AUDIT_STATUS_STRING_LIST,
      queryApi: goodsApi.getSkuAuditList, // settingApi.getStockProcess,
      // 查询参数
      tempStatus: DEFAULT_STATUS_STRING,
      queryParams: {
        skuCode: null,
        spuName: null,
        categoryIdList: [],
        spuType: null,
        status: DEFAULT_STATUS,
        setFlag: this.setFlag,
      },
      srcList: [],
      labelsVersion: 11,
      labelList: [
        { label: '商品分类', prop: 'categoryName', isCheck: true, disabled: false },
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU名称', prop: 'skuName', isCheck: true, disabled: false, width: 160 },
        { label: '类别', prop: 'spuType', isCheck: true, disabled: false },
        { label: '素材名称', prop: 'materialName', isCheck: true, disabled: false },
        { label: '素材', prop: 'materialMainImages', isCheck: true, disabled: false },
        { label: '备注', prop: 'goodsRemark', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
        { label: '审核时间', prop: 'auditCommitDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {
    tempStatus(v, o) {
      console.log(v);
      if (Array.isArray(v) && v.length) {
        const TEMP = [];
        v.forEach(e => {
          e = e.split(',');
          TEMP.push(...e);
        });
        this.queryParams.status = TEMP.map(e => Number(e));
      } else {
        this.queryParams.status = [];
        GOODS_AUDIT_STATUS_LIST.map(e => e.value).forEach(e => {
          this.queryParams.status.push(...e);
        });
      }
    },
  },
  mounted() {},
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.tempStatus = DEFAULT_STATUS_STRING;
      return new Promise(resolve => {
        resolve();
      });
    },
    // beforeQuerySearch(conditionObj) {
    //   console.log(conditionObj);
    //   return new Promise(resolve => {
    //     resolve(conditionObj);
    //   });
    // },
    /** 查 看 */
    onLook(row) {
      const { status, id } = row;
      const NAME =
        this.setFlag == GOODS_COMBO_TYPES.PACKAGE
          ? 'BusinessGoodsAuditSkuPackageView'
          : 'BusinessGoodsAuditSkuOrdinaryView';
      this.$router.push({
        name: NAME,
        query: {
          id,
          status,
        },
      });
    },
    /** 审 核 */
    onAudit(row) {
      const { status, id } = row;
      const NAME =
        this.setFlag == GOODS_COMBO_TYPES.PACKAGE
          ? 'BusinessGoodsAuditSkuPackageAudit'
          : 'BusinessGoodsAuditSkuOrdinaryAudit';
      this.$router.push({
        name: NAME,
        query: {
          id,
          status,
        },
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
