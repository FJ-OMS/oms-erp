<template>
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
        <el-form-item label="商品名称" prop="name">
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
        <el-form-item label="商品类型" prop="type">
          <BjSelectLov
            v-model="queryParams.type"
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
    </template>
    <template v-slot:tableOperation>
      <el-col v-if="setFlag == GOODS_COMBO_TYPES.ORDINARY">
        <el-button
          v-permission:BusinessGoodsSpuOrdinary_DownloadTemplate
          type="primary"
          disabled
          @click="onDownload"
          >下载模板</el-button
        >

        <el-button
          v-permission:BusinessGoodsSpuOrdinary_ImportData
          type="primary"
          disabled
          @click="onLead"
          >导入数据</el-button
        >

        <el-button
          v-show="setFlag == GOODS_COMBO_TYPES.ORDINARY"
          v-permission:BusinessGoodsSpuOrdinaryAdd
          type="primary"
          class="main-btn"
          @click="onAdd"
          >新增普通商品</el-button
        >
      </el-col>
      <el-col v-if="setFlag == GOODS_COMBO_TYPES.PACKAGE">
        <el-button
          v-permission:BusinessGoodsSpuPackage_DownloadTemplate
          type="primary"
          disabled
          @click="onDownload"
          >下载模板</el-button
        >

        <el-button
          v-permission:BusinessGoodsSpuPackage_ImportData
          type="primary"
          disabled
          @click="onLead"
          >导入数据</el-button
        >
        <el-button
          v-show="setFlag == GOODS_COMBO_TYPES.PACKAGE"
          v-permission:BusinessGoodsSpuPackageAdd
          type="primary"
          class="main-btn"
          @click="onAdd"
          >新增套餐商品</el-button
        >

        <!-- <el-button type="primary"  @click="onDeleteSelected">批量删除</el-button> -->

        <!-- <el-button type="primary"  @click="onSubmitSelected">提交审核</el-button> -->
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
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span v-if="col.prop === 'code'" class="link" @click="onView(scope.row)">
              {{ scope.row[col.prop] }}
            </span>
            <div v-else-if="col.prop == 'mainImage'" class="flex justify-around">
              <BjViewUrl :url="scope.row[col.prop][0]" style="width: 90px" />
            </div>

            <span v-else-if="col.prop == 'type'">{{
              GOODS_TYPE_LIST.bjGet(scope.row[col.prop])
            }}</span>
            <!-- <span v-else-if="col.prop == 'todo1'">{{
                GOODS_MANAGE_AUDIT_STATUS.bjGet(scope.row[col.prop])
              }}</span> -->
            <el-tag
              v-else-if="col.prop === 'status'"
              :type="scope.row.status ? 'success' : 'info'"
              >{{ STATUS_ENABLED_NUM.bjGet(scope.row[col.prop]) }}</el-tag
            >
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
          <template slot-scope="props">
            <el-button
              v-permission:BusinessGoodsSpuOrdinaryView
              type="text"
              @click="onView(props.row)"
              >查看</el-button
            >
            <el-button
              v-permission:BusinessGoodsSpuOrdinaryEdit
              :disabled="props.row.todo1 == 2"
              type="text"
              class="edit-btn"
              @click="onEdit(props.row)"
              >编辑</el-button
            >
            <el-button
              v-permission:BusinessGoodsSpuOrdinary_Status
              type="text"
              @click="onStatus(props.row)"
              >{{ props.row.status ? '禁用' : '启用' }}</el-button
            >
            <!-- <el-button type="text"  @click="onSubmit(props.row)">提交</el-button> -->
            <el-button
              v-permission:BusinessGoodsSpuOrdinary_Delete
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
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import {
  GOODS_TYPE_LIST,
  GOODS_MANAGE_AUDIT_STATUS,
  STATUS_ENABLED_NUM,
  GOODS_COMBO_TYPE_LIST,
  GOODS_COMBO_TYPES,
} from '@/utils/constants';
export default {
  name: 'BusinessGoodsSpuOrdinaryIndex',
  mixins: [pageMiXin],
  props: {
    setFlag: {
      type: [String, Number],
      default: GOODS_COMBO_TYPES.ORDINARY,
    },
  },
  data() {
    return {
      selection: false,
      GOODS_TYPE_LIST,
      GOODS_MANAGE_AUDIT_STATUS,
      STATUS_ENABLED_NUM,
      GOODS_COMBO_TYPE_LIST,
      GOODS_COMBO_TYPES,
      queryApi: goodsApi.getPage,
      colSpan: 6,
      // 查询参数
      queryParams: {
        setFlag: this.setFlag,
        spuCode: null,
        name: null,
        type: null,
        categoryIdList: [],
      },
      labelsVersion: 11,
    };
  },
  computed: {
    labelList() {
      const TEMP = [
        { label: '商品分类', prop: 'categoryName', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'name', isCheck: true, disabled: false, width: 160 },
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品类型', prop: 'type', isCheck: true, disabled: false, width: 80 },
        { label: '品牌名称', prop: 'brandName', isCheck: true, disabled: false },
        { label: '素材名称', prop: 'materialName', isCheck: true, disabled: false },
        { label: '备注', prop: 'remark', isCheck: true, disabled: false },
        { label: '状态', prop: 'status', isCheck: true, disabled: false, width: 80 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 170 },
      ];
      // 套餐列表，没有品牌字段
      if (this.setFlag) {
        TEMP.splice(4, 1);
      }
      return TEMP;
    },
  },
  watch: {},
  mounted() {},
  methods: {
    /** 下载模板 */
    onDownload() {},
    /** 导入数据 */
    onLead() {},
    /** 新建 */
    onAdd() {
      if (this.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
        this.$toPage('BusinessGoodsSpuOrdinaryAdd');
      }
      if (this.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
        this.$toPage('BusinessGoodsSpuPackageAdd');
      }
    },
    /** 编辑操作 */
    onEdit(row) {
      if (this.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
        this.$toPage('BusinessGoodsSpuOrdinaryEdit', { id: row.id });
      }
      if (this.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
        this.$toPage('BusinessGoodsSpuPackageEdit', { id: row.id });
      }
    },

    /** 查看 */
    onView(row) {
      if (this.setFlag == GOODS_COMBO_TYPES.ORDINARY) {
        this.$toPage('BusinessGoodsSpuOrdinaryView', { id: row.id });
      }
      if (this.setFlag == GOODS_COMBO_TYPES.PACKAGE) {
        this.$toPage('BusinessGoodsSpuPackageView', { id: row.id });
      }
    },

    /** 禁用 */
    onStatus(row) {
      const TEXT = row.status ? '禁用' : '启用';
      this.$confirm(`您正在 ${TEXT} ${row.name}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            ids: [row.id],
            status: row.status ? 0 : 1,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在 删除 ${row.name}，是否继续？`, '提示', {
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

    onSubmitSelected() {
      if (!this.selectedRows.length) {
        this.$message.warning('请选择');
        return;
      }
      const ids = this.selectedRows.map(e => e.id);
      this.$confirm('您正在所选的数据提交审核，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          console.log(ids);
          this.$message.info('TODO:接口  SP001商品信息未完整，请完善商品信息');
          // this.batchDeleteApi({ ids: ids });
        })
        .catch(() => {});
    },
    operationApi(API_DATA) {
      goodsApi.operation(API_DATA).then(res => {
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
      goodsApi.batchDelete(API_DATA).then(res => {
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
.el-form-item {
  .el-image {
    width: 200px;
  }
}
</style>
