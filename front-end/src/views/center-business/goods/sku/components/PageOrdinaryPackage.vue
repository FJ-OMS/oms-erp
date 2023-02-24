<template>
  <div>
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :search-btn-span="setFlag != 'draft' ? colSpan * 2 : colSpan * 3"
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

        <el-col :span="colSpan">
          <el-form-item label="SKU名称" prop="skuName">
            <el-input
              v-model.trim="queryParams.skuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
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
        <el-col v-if="setFlag != 'draft'" :span="colSpan">
          <el-form-item label="状态">
            <BjSelectLov
              v-model="tempStatus"
              :option-list="GOODS_SKU_STATUS_NORMAL"
              placeholder="请选择"
              :collapse-tags="tempStatus.length > 2"
              multiple
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation="tableOperationProp">
        <el-col v-if="setFlag == GOODS_COMBO_TYPES.ORDINARY">
          <el-button
            v-permission:BusinessGoodsSkuOrdinary_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >
          <el-button
            v-permission:BusinessGoodsSkuOrdinary_DownloadTemplate
            type="primary"
            disabled
            @click="onDownload"
            >下载模板</el-button
          >
          <el-button
            v-permission:BusinessGoodsSkuOrdinary_ImportData
            type="primary"
            disabled
            @click="onLead"
            >导入数据</el-button
          >
          <el-button
            v-show="queryParams.setFlag == GOODS_COMBO_TYPES.ORDINARY"
            v-permission:BusinessGoodsSkuOrdinaryAdd
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增普通SKU</el-button
          >
          <el-button
            v-permission:BusinessGoodsSkuOrdinary_Audit
            type="primary"
            :disabled="tableOperationProp.notSelected"
            @click="onAudit()"
            >提交</el-button
          >
        </el-col>
        <el-col v-if="setFlag == GOODS_COMBO_TYPES.PACKAGE">
          <el-button
            v-permission:BusinessGoodsSkuPackage_Delete
            type="primary"
            :disabled="tableOperationProp.notSelected"
            class="delete-btn"
            @click="onDeleteSelected"
            >删除</el-button
          >
          <el-button
            v-permission:BusinessGoodsSkuPackage_DownloadTemplate
            type="primary"
            disabled
            @click="onDownload"
            >下载模板</el-button
          >
          <el-button
            v-permission:BusinessGoodsSkuPackage_ImportData
            type="primary"
            disabled
            @click="onLead"
            >导入数据</el-button
          >
          <el-button
            v-show="queryParams.setFlag == GOODS_COMBO_TYPES.PACKAGE"
            v-permission:BusinessGoodsSkuPackageAdd
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增套餐SKU</el-button
          >
          <el-button
            v-permission:BusinessGoodsSkuPackage_Audit
            type="primary"
            :disabled="tableOperationProp.notSelected"
            @click="onAudit()"
            >提交</el-button
          >
        </el-col>
        <el-col v-if="setFlag == 'draft'">
          <el-button
            v-permission:BusinessGoodsSkuDraft_Audit
            type="primary"
            class="main-btn"
            :disabled="tableOperationProp.notSelected"
            @click="onAudit()"
            >提交</el-button
          >
        </el-col>
      </template>
      <template v-slot:tableColumn>
        <template v-for="(col, index) in dropCols">
          <!-- 非草稿 才展示状态列 -->
          <el-table-column
            v-if="
              (col.prop !== 'bjTableAction' && col.prop !== 'status') ||
                (col.prop === 'status' && setFlag !== 'draft')
            "
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
              <div v-else-if="col.prop === 'materialMainImages'" class="flex justify-between">
                <BjViewUrl :url="(scope.row[col.prop] || [])[0]" height="80px" preview />
              </div>

              <template v-else-if="col.prop === 'skuName'">
                <span
                  v-if="setFlag == GOODS_COMBO_TYPES.ORDINARY"
                  class="link"
                  @click="showSku(scope.row)"
                >
                  {{ scope.row[col.prop] }}</span
                >

                <span v-else>
                  <el-tag v-if="setFlag == 'draft' && scope.row.setFlag == 1">套</el-tag>
                  {{ scope.row[col.prop] }}</span
                >
              </template>

              <el-tag v-else-if="col.prop === 'status'">{{
                GOODS_SKU_STATUS.bjGet(scope.row[col.prop])
              }}</el-tag>
              <!-- 价格 显示 -->
              <span v-else-if="col.prop.toLocaleLowerCase().includes('price')">{{
                $number2money(scope.row[col.prop])
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
            :fixed="col.fixed || 'right'"
            align="center"
          >
            <template slot-scope="props">
              <el-button
                v-permission="
                  props.row.setFlag == GOODS_COMBO_TYPES.ORDINARY
                    ? 'BusinessGoodsSkuOrdinaryView'
                    : 'BusinessGoodsSkuPackageView'
                "
                type="text"
                @click="onView(props.row)"
                >查看</el-button
              >
              <el-button
                v-permission="
                  props.row.setFlag == GOODS_COMBO_TYPES.ORDINARY
                    ? 'BusinessGoodsSkuOrdinaryEdit'
                    : 'BusinessGoodsSkuPackageEdit'
                "
                :disabled="!CAN_EDIT_STATUS.includes(Number(props.row.status))"
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-permission="
                  props.row.setFlag == GOODS_COMBO_TYPES.ORDINARY
                    ? 'BusinessGoodsSkuOrdinary_Delete'
                    : 'BusinessGoodsSkuPackage_Delete'
                "
                :disabled="!CAN_DELETE_STATUS.includes(Number(props.row.status))"
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
    <SkuRowData ref="SkuRowData" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import {
  GOODS_TYPE_LIST,
  GOODS_SKU_STATUS_NORMAL,
  GOODS_SKU_STATUS,
  GOODS_COMBO_TYPES,
} from '@/utils/constants';
import SkuRowData from './SkuRowData.vue';
/**
 *

                  创建 ——————————————————————————-
                    |                            |
                    |                            |
                    |<———————— ————              |
                    |             |              |
                    |保存          |          提交|
                    ↓             |              |
    ————————————> 0草稿————>编辑———|              |
    |               |                            |
    | 保存          |                            |
    |               | 提交                       |
    |    提交       |                            |
    |————————————>  |                            |
    |               |                            |
    |               ↓                            ↓
1 被驳回 <————————2审核中 <—————————————————————————
           N        |                            ↑
                    |                        提交|
                    | Y                          |
                    |                            |
                    ↓                            |
                  3已生效<———————————————————————
                    |                            |
                    |                            |
                    |编辑——提交                   |
                    |                            |
                    ↓                   Y        |
                4已生效(编辑中，即审核中)——————————
                    | ↑
                  N| |编辑——提交
                    | |
                    ↓ |
                5已生效(被驳回)

 */
// 可以提交审核的状态：0草稿  1被驳回  5已生效(被驳回)
const CAN_SUBMIT_STATUS = [0, 1, 5];
// 可以编辑的状态：0草稿  1被驳回   3已生效   5已生效(被驳回)
const CAN_EDIT_STATUS = [0, 1, 3, 5];
// 可以删除的状态：0草稿  1被驳回  2审核中
const CAN_DELETE_STATUS = [0, 1, 2];

let DEFAULT_STATUS, SET_FLAG;
export default {
  name: 'BusinessGoodsSkuPageOrdinaryPackage',
  components: {
    SkuRowData,
  },
  mixins: [pageMiXin],
  props: {
    setFlag: {
      type: [String, Number],
      default: null,
    },
  },
  data() {
    if (this.setFlag == 'draft') {
      DEFAULT_STATUS = [0];
      SET_FLAG = null;
    } else {
      DEFAULT_STATUS = [...GOODS_SKU_STATUS_NORMAL.map(e => e.value)];
      SET_FLAG = this.setFlag;
    }

    return {
      CAN_EDIT_STATUS,
      CAN_SUBMIT_STATUS,
      CAN_DELETE_STATUS,
      GOODS_COMBO_TYPES,
      /** */
      GOODS_TYPE_LIST,
      GOODS_SKU_STATUS,
      GOODS_SKU_STATUS_NORMAL,
      queryApi: goodsApi.getSku,
      // 查询参数
      tempStatus: [],
      queryParams: {
        spuCode: null,
        skuCode: null,
        spuName: null,
        categoryIdList: [],
        status: DEFAULT_STATUS,
        setFlag: SET_FLAG,
      },
      labelsVersion: 11,
      labelList: [
        { label: '商品分类', prop: 'categoryName', isCheck: true, disabled: false },
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: 'SKU名称', prop: 'skuName', isCheck: true, disabled: false, width: 160 },
        { label: '素材', prop: 'materialMainImages', isCheck: true, disabled: false },
        // TODO: 成本价 字段
        { label: '成本价', prop: 'costPrice', isCheck: true, disabled: false, width: 90 },
        { label: '销售价', prop: 'salesPrice', isCheck: true, disabled: false, width: 90 },
        { label: '备注', prop: 'goodsRemark', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      skuTableRowData: [],
    };
  },
  computed: {},
  watch: {
    tempStatus(v, o) {
      if (Array.isArray(v) && v.length) {
        this.queryParams.status = [...v];
      } else {
        this.queryParams.status = [...DEFAULT_STATUS];
      }
    },
  },
  mounted() {},
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.tempStatus = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    // SKU名称点击展示
    showSku(row) {
      const DATA = [
        {
          attributeName: 'SKU名称',
          attributeValue: row.skuName,
        },
      ].concat(row.skuAttributeList || []);
      this.$refs.SkuRowData.show(DATA);
    },
    /** 删除 */
    onDelete(row) {
      this.$confirm(`您正在删除  ${row.skuName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const API_DATA = { ids: [row.id] };
          this.deleteApi(API_DATA);
        })
        .catch(() => {});
    },
    /** 批量删除操作 */
    onDeleteSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }

      if (this.selectedRows.some(e => !CAN_DELETE_STATUS.includes(Number(e.status)))) {
        const TEXT = `请选择 ${CAN_DELETE_STATUS.map(e => GOODS_SKU_STATUS.bjGet(e)).join(
          '/',
        )} 的状态进行删除`;

        this.$message.warning(TEXT);
        return;
      }
      const ids = this.selectedRows.map(e => e.id);
      this.$confirm('您正在删除所选的数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const API_DATA = { ids: ids };
          this.deleteApi(API_DATA);
        })
        .catch(() => {});
    },
    /** 下载模板 */
    onDownload() {
      this.$message.warning('TODO:没接口');
    },
    /** 导入数据 */
    onLead() {
      this.$message.warning('TODO:没接口');
    },
    /** 提交审核 */
    onAudit(row) {
      let ids = [];
      if (!row) {
        ids = this.selectedRows.map(e => e.id);
      } else {
        ids.push(row.id);
      }
      if (!this.selectedRows.every(e => CAN_SUBMIT_STATUS.includes(Number(e.status)))) {
        const TEXT = `请选择 ${CAN_SUBMIT_STATUS.map(e => GOODS_SKU_STATUS.bjGet(e)).join(
          '/',
        )} 的状态进行提交`;
        this.$message.warning(TEXT);
        return;
      }
      this.$confirm('确认提交吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const API_DATA = { skuIds: ids };

          this.skuBatchSubmitAudit(API_DATA);
        })
        .catch(() => {});
    },
    /** 新建 */
    onAdd() {
      if (this.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
        this.$toPage('BusinessGoodsSkuOrdinaryAdd');
      } else if (this.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
        this.$toPage('BusinessGoodsSkuPackageAdd');
      } else {
        this.$toPage('BusinessGoodsSkuOrdinaryAdd');
      }
    },
    /** 查看 */
    onView(row) {
      // 通过row 判断 是套餐还是普通SKU
      if (row.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
        this.$toPage('BusinessGoodsSkuOrdinaryView', {
          id: row.id,
          skuCode: row.skuCode,
        });
      } else if (row.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
        this.$toPage('BusinessGoodsSkuPackageView', {
          id: row.id,
          skuCode: row.skuCode,
        });
      } else {
        this.$toPage('BusinessGoodsSkuOrdinaryView', {
          id: row.id,
          skuCode: row.skuCode,
        });
      }
    },
    /** 编辑操作 */
    onEdit(row) {
      // 通过row 判断 是套餐还是普通SKU
      if (row.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
        this.$toPage('BusinessGoodsSkuOrdinaryEdit', {
          id: row.id,
          skuCode: row.skuCode,
          setFlag: this.setFlag,
        });
      } else if (row.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
        this.$toPage('BusinessGoodsSkuPackageEdit', {
          id: row.id,
          skuCode: row.skuCode,
          setFlag: this.setFlag,
        });
      } else {
        this.$toPage('BusinessGoodsSkuOrdinaryEdit', {
          id: row.id,
          skuCode: row.skuCode,
          setFlag: this.setFlag,
        });
      }
    },
    /* 关闭弹窗 */
    handleClose() {
      this.dialogVisible = false;
    },
    skuBatchSubmitAudit(API_DATA) {
      // skuIds
      goodsApi.skuBatchSubmitAudit(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    deleteApi(API_DATA) {
      goodsApi.deleteSku(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    updateSkuStatus(row) {
      goodsApi
        .updateSkuStatus({
          id: row.id,
          showFlag: row.showFlag ? 0 : 1,
        })
        .then(res => {
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
