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
        <el-col :span="colSpan"
          ><el-form-item label="SPU" prop="spuCode">
            <el-input
              v-model.trim="queryParams.spuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan"
          ><el-form-item label="商品名称" prop="spuName">
            <el-input
              v-model.trim="queryParams.spuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan"
          ><el-form-item label="安装服务编码" prop="installServiceCode">
            <el-input
              v-model.trim="queryParams.installServiceCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan"
          ><el-form-item label="安装服务名称" prop="installServiceName">
            <el-input
              v-model.trim="queryParams.installServiceName"
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
            v-permission:BusinessGoodsVirtualInstallServiceAdd
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
              <template v-else-if="col.prop === 'storeCount'">
                {{ scope.row.isAllStore ? '所有' : scope.row.storeCount }}
              </template>

              <span
                v-else-if="col.prop === 'installServiceName'"
                class="link"
                @click="onInstallServiceView(scope.row)"
              >
                {{ scope.row[col.prop] }}
              </span>

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
                v-permission:BusinessGoodsVirtualInstallServiceView
                type="text"
                @click="onView(props.row)"
                >查看</el-button
              >
              <el-button
                v-permission:BusinessGoodsVirtualInstallServiceEdit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-permission:BusinessGoodsVirtualInstallService_Delete
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
  name: 'BusinessGoodsVirtualInstallServiceList',
  mixins: [pageMiXin],
  data() {
    return {
      VIRTUAL_GOODS_ROUTE_LIST,
      queryApi: goodsApi.getSpuInstallServices,
      // 查询参数
      queryParams: {
        spuCode: null,
        spuName: null,
        installServiceCode: null,
        installServiceName: null,
      },
      labelList: [
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: '商品分类', prop: 'categoryText', isCheck: true, disabled: false },
        { label: '安装服务名称', prop: 'installServiceName', isCheck: true, disabled: false },
        { label: '套餐商品', prop: 'skuCount', isCheck: true, disabled: false },
        { label: '适用店铺', prop: 'storeCount', isCheck: true, disabled: false },
        { label: '创建人', prop: 'createdBy', isCheck: true, disabled: false, width: 135 },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
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
        ids = this.selectedRows.map(e => e.id);
      } else {
        ids.push(row.id);
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
    onInstallServiceView(row) {
      this.$toPage('BusinessWorksheetServiceView', { id: row.installServiceId });
    },

    /** 新建 */
    onAdd() {
      this.$router.push({ name: 'BusinessGoodsVirtualInstallServiceAdd' });
    },
    /** 查看 */
    onView(row) {
      this.$router.push({
        name: 'BusinessGoodsVirtualInstallServiceView',
        query: { id: row.id },
      });
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$router.push({
        name: 'BusinessGoodsVirtualInstallServiceEdit',
        query: { id: row.id },
      });
    },

    deleteApi(API_DATA) {
      goodsApi.deleteSpuInstallServices(API_DATA).then(res => {
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
        .updateSpuInstallServices({
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
