<!--
 * @Author: your name
 * @Date: 2021-10-13 13:45:20
 * @LastEditTime: 2021-12-20 13:37:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\process-order\forward\index.vue
-->
<template>
  <div class="app-container">
    <BjRouteTabs :route-list="STRATEGY_SOURCE_ROUTE_LIST"> </BjRouteTabs>
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :search-btn-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :before-reset-search="beforeResetSearch"
      @handleSelectionChange="handleSelectionChange"
    >
      <!-- <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="所属租户" prop="tenantId">
            <BjSelectTenant v-model="queryParams.tenantId" />
          </el-form-item>
        </el-col>
      </template> -->
      <template v-slot:beforeSearchBtn>
        <el-col :span="24 - colSpan">
          <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
            <el-tab-pane label="单品发包" name="0" />
            <el-tab-pane label="套餐发包" name="1" />
            <el-tab-pane label="套餐拆单品发包" name="2" />
          </el-tabs>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary" @click="onAdd">添 加</el-button>
          <el-button type="danger" @click="onClear">清 空</el-button>
          <el-button type="primary" @click="set">排除条件设置</el-button>
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
            :show-overflow-tooltip="col.prop !== 'bjTableAction'"
          >
            <template slot-scope="scope">
              <template v-if="false" />
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
              <el-button v-if="activeName === '2'" type="text" @click="onEdit(props.row)"
                >编 辑</el-button
              >
              <el-button v-if="!props.row.setStatus" type="text" @click="onDelete(props.row)"
                >删 除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 新增/编辑弹窗 -->
    <!-- <DataForm ref="DataForm" @reFresh="reFresh" /> -->
    <BjDivider>寻源设置</BjDivider>
    <viewForm ref="viewForm" />
    <sendOut ref="sendOut" :title="title" />
    <setMealSeparate ref="setMealSeparate" />
    <!-- 排除条件设置 -->
    <dialogForm ref="dialogForm" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { strategyApi } from '@/api';
import { STRATEGY_SOURCE_ROUTE_LIST } from '@/utils/constants';
// import DataForm from '../components/DataForm.vue';
// const getDefaultForm = () => {
//   return {};
// };
import viewForm from '../components/viewForm';
import sendOut from './components/sendOut';
import setMealSeparate from './components/setMealSeparate';
import dialogForm from './components/dialogForm';
export default {
  name: 'SettingOrderSourceStrategyParcel',
  components: {
    viewForm,
    sendOut,
    setMealSeparate,
    dialogForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 6,
      selection: false,
      /** */
      STRATEGY_SOURCE_ROUTE_LIST,
      /** */
      queryApi: strategyApi.getPackageSettingSingleGoods, // settingApi.getStockProcess,
      // 查询参数
      queryParams: {},
      activeName: 0,
      labelsVersion: 11,
      tableSortableDisabled: true,
      labelList: [
        { label: '商品编码', prop: 'spu', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: '分包数量', prop: 'packageAmount', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      tabList3: [
        { label: '套餐商品编号', prop: 'groupCode', isCheck: true, disabled: false },
        { label: '套餐商品名称', prop: 'groupName', isCheck: true, disabled: false },
        { label: '商品编码', prop: 'code', isCheck: true, disabled: false },
        { label: 'SKU编码', prop: 'sku', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'name', isCheck: true, disabled: false },
        { label: '分包数量', prop: 'num', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
      title: '',
    };
  },
  computed: {},
  watch: {
    activeName: {
      handler(v) {
        this.dropCols = v === '2' ? this.tabList3 : this.labelList;
      },
      immediate: true,
    },
  },
  created() {},
  mounted() {
    this.$refs.BjPage.tableData = [
      {
        code: 'SP202020222',
        name: '水龙头A款',
        num: 5,
      },
      {
        code: 'SP2020202211',
        name: '马桶B款',
        num: 1,
      },
      {
        code: 'SP202020223',
        name: '花洒增压款',
        num: null,
      },
    ];
  },
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      return new Promise(resolve => {
        resolve();
      });
    },
    handleClick() {
      this.reFresh();
    },
    /** 创 建 */
    onAdd() {
      this.title = this.activeName === '0' ? '添加商品' : '添加组合商品';
      this.activeName === '2' ? this.$refs.setMealSeparate.show() : this.$refs.sendOut.show();
    },
    onClear() {},
    set() {
      this.$refs.dialogForm.show();
    },
    /** 流程设置  */
    setProcess(row) {
      this.$toPage('SettingProcessOrderForwardSetProcess', { id: row.id });
    },
    showViewForm(boolean) {
      this.$refs.viewForm.show(boolean);
    },
    // /** 禁用 */
    // onStatus(flag, row) {
    //   console.log(flag, row);
    //   this.operationApi({
    //     flag: flag,
    //     id: row.id,
    //     objectVersionNumber: row.objectVersionNumber,
    //   });
    // },

    // /** 删除 */
    // onDelete(row) {
    //   this.$confirm(`您正在删除该数据，是否继续？`, '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning',
    //   })
    //     .then(() => {
    //       this.batchDeleteApi({ id: row.id });
    //     })
    //     .catch(() => {});
    // },
    // operationApi(API_DATA) {
    //   settingApi.operationStockProcess(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    // batchDeleteApi(API_DATA) {
    //   settingApi.deleteStockProcess(API_DATA).then(res => {
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
