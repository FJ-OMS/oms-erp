<!--
 * @Author: your name
 * @Date: 2021-10-13 13:45:20
 * @LastEditTime: 2021-12-20 13:37:27
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
          <el-form-item label="收货区域" prop="collect">
            <BjSelectChinaArea
              v-model="queryParams.collect"
              placeholder="请选择"
              clearable
              class="full-width"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="发货区域" prop="sendOut">
            <BjSelectChinaArea
              v-model="queryParams.sendOut"
              placeholder="请选择"
              clearable
              class="full-width"
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary" @click="onAdd">添 加</el-button>
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
              <!-- <el-button v-if="activeName === 2" type="text"  @click="onEdit(props.row)"
                >编 辑</el-button
              > -->
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
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
// import { goodsApi } from '@/api';
import { STRATEGY_SOURCE_ROUTE_LIST } from '@/utils/constants';
// import DataForm from '../components/DataForm.vue';
// const getDefaultForm = () => {
//   return {};
// };
import viewForm from '../components/viewForm';
export default {
  name: 'SettingOrderSourceStrategyArea',
  components: {
    viewForm,
    // DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 8,
      selection: false,
      /** */
      STRATEGY_SOURCE_ROUTE_LIST,
      /** */
      queryApi: null, // settingApi.getStockProcess,
      // 查询参数
      queryParams: {
        collect: null,
        sendOut: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '收货区域', prop: 'collect', isCheck: true, disabled: false },
        { label: '发货区域', prop: 'sendOut', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.$refs.BjPage.tableData = [
      {
        collect: '福建省',
        sendOut: '福建省',
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
    /** 创 建 */
    onAdd() {
      // this.$refs.DataForm.show();
    },
    /** 流程设置  */
    setProcess(row) {
      this.$toPage('SettingProcessOrderForwardSetProcess', { id: row.id });
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
