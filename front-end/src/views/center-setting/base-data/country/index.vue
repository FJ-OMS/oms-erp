<template>
  <div class="app-container">
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
      <template v-slot:tableOperation>
        <el-col>
          <el-button
            v-permission:SettingBaseDataCountryIndex_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增国家</el-button
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

              <el-tag
                v-else-if="col.prop === 'enabledFlag'"
                :type="scope.row.enabledFlag ? 'success' : 'info'"
                >{{ STATUS_ENABLED_NUM.bjGet(scope.row.enabledFlag) }}</el-tag
              >
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
              <!-- <el-button type="text"  @click="toRegion(scope.row)">区域设置</el-button> -->
              <el-button
                v-permission:SettingBaseDataCountryIndex_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(scope.row)"
                >编辑</el-button
              >
              <el-button
                v-show="!scope.row.enabledFlag"
                v-permission:SettingBaseDataCountryIndex_Status
                type="text"
                @click="onStatus(1, scope.row)"
                >启用</el-button
              >
              <el-button
                v-show="scope.row.enabledFlag"
                v-permission:SettingBaseDataCountryIndex_Status
                type="text"
                @click="onStatus(0, scope.row)"
                >禁用</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { baseDataApi } from '@/api';
import DataForm from './components/DataForm.vue';
import { STATUS_ENABLED_NUM } from '@/utils/constants';
export default {
  name: 'SettingBaseDataCountryIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      STATUS_ENABLED_NUM,
      selection: false,
      queryApi: baseDataApi.getCountry,
      // 查询参数
      queryParams: {},

      labelList: [
        { label: '国家编码', prop: 'countryCode', isCheck: true, disabled: false },
        { label: '国家名称', prop: 'countryName', isCheck: true, disabled: false },
        { label: '简称', prop: 'abbreviation', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false, width: 100 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 160 },
      ],
    };
  },
  computed: {},
  watch: {},
  mounted() {
    // this.$refs.BjPage.tableData = [{}];
  },
  methods: {
    toRegion(row) {},
    /** 禁用 */
    onStatus(flag, row) {
      console.log(flag, row);
      this.operationApi({
        ...row,
        enabledFlag: flag,
      });
    },
    /** 删除操作 */
    // onDelete(row) {
    //   this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.batchDeleteApi({ ids: [row.id] });
    //     })
    //     .catch(() => {});
    // },
    /** 批量删除操作 */
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
      this.$refs.DataForm.show();
    },
    /** 编辑操作 */
    onEdit(row) {
      this.$refs.DataForm.show(this.$lodash.clone(row));
    },

    operationApi(API_DATA) {
      baseDataApi.updateCountry(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // deleteApi(API_DATA) {
    //   baseDataApi.deleteOnlineShop(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    // batchDeleteApi(API_DATA) {
    //   baseDataApi.batchDeleteBrand(API_DATA).then(res => {
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
