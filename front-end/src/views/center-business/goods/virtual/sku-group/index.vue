<template>
  <div class="app-container">
    <BjRouteTabs :route-list="VIRTUAL_GOODS_ROUTE_LIST" />
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
        <el-col :span="colSpan">
          <el-form-item label="SPU" prop="spuCode">
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
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:BusinessGoodsVirtualSkuGroupAdd
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
              <template v-else-if="col.prop === 'storeList'">
                {{ (scope.row[col.prop] || []).length || '所有' }}
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
            <template slot-scope="props">
              <el-button
                v-permission:BusinessGoodsVirtualSkuGroupView
                type="text"
                @click="onView(props.row)"
                >查看</el-button
              >
              <el-button
                v-permission:BusinessGoodsVirtualSkuGroupEdit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-permission:BusinessGoodsVirtualSkuGroup_Delete
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
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import { VIRTUAL_GOODS_ROUTE_LIST } from '@/utils/constants';
export default {
  name: 'BusinessGoodsVirtualSkuGroupList',
  mixins: [pageMiXin],
  data() {
    return {
      VIRTUAL_GOODS_ROUTE_LIST,
      queryApi: goodsApi.getSkuGroup,
      // 查询参数
      queryParams: {
        spuCode: null,
        spuName: null,
      },
      labelList: [
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: '商品分类', prop: 'categoryName', isCheck: true, disabled: false },
        { label: '套餐商品', prop: 'goodsNum', isCheck: true, disabled: false },
        { label: '适用店铺', prop: 'storeList', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdBy', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'createDate', isCheck: true, disabled: false, width: 135 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    /** 删除 */
    onDelete(row) {
      let ids = [];
      if (!row) {
        ids = this.selectedRows.map(e => e.groupId);
      } else {
        ids.push(row.groupId);
      }
      this.$confirm('确认删除吗？', '提示', {
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

    /** 新建 */
    onAdd() {
      this.$router.push({ name: 'BusinessGoodsVirtualSkuGroupAdd' });
    },
    /** 查看 */
    onView(row) {
      this.$router.push({
        name: 'BusinessGoodsVirtualSkuGroupView',
        query: { id: row.groupId },
      });
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$router.push({
        name: 'BusinessGoodsVirtualSkuGroupEdit',
        query: { id: row.groupId },
      });
    },

    deleteApi(API_DATA) {
      goodsApi.deleteSkuGroup(API_DATA).then(res => {
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
        .updateSkuGroup({
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
