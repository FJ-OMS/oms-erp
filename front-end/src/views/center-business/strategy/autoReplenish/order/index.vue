<!--
 * @Author: your name
 * @Date: 2021-10-04 14:43:27
 * @LastEditTime: 2021-12-17 16:38:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\strategy\autoReplenish\order\index.vue
-->
<template>
  <div class="app-container">
    <BjRouteTabs :route-list="STRATEGY_AUTO_REPLENISH_ROUTE_LIST">
      <el-button type="primary" @click="$refs.DialogForm.show()">设置优先级</el-button>
    </BjRouteTabs>
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'80px'"
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
          <el-form-item label="商品编码" prop="code">
            <el-input
              v-model.trim="queryParams.code"
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
          <el-button>删 除</el-button>

          <el-button type="primary" class="main-btn" @click="onAdd">新增商品</el-button>
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
              <el-button
                v-if="!props.row.releaseStatus"
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
    <!-- 新增弹窗 -->
    <!-- <DataForm ref="DataForm" @reFresh="reFresh" /> -->
    <DialogForm ref="DialogForm" @confirm="onConfirm" />
    <TableData ref="tableData" :config="dialogListConfig">
      <template slot="button" slot-scope="data">
        <el-button type="text" @click="handleDelete(data.scope)">删 除</el-button>
      </template>
    </TableData>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import TableData from '../components/tableData';
import DialogForm from './components/DialogForm';
import { STRATEGY_AUTO_REPLENISH_ROUTE_LIST } from '@/utils/constants';
// const getDefaultForm = () => {
//   return {};
// };
// import tableData from './data.json';
export default {
  name: 'SettingLogisticsOrderStockout',
  components: { DialogForm, TableData },
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 6,
      selection: false,
      /** */
      STRATEGY_AUTO_REPLENISH_ROUTE_LIST,
      /** */
      queryApi: null, // settingApi.getStockProcess,
      // 查询参数
      queryParams: {
        code: null,
        spuName: null,
      },
      //   title: '',
      //   dialogVisible: false,
      // 表单参数
      //   form: getDefaultForm(),
      //   // 表单校验
      //   rules: {
      //     name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
      //     typeCode: [{ required: true, message: '请选择所属分类', trigger: 'change' }],
      //   },
      labelsVersion: 11,
      labelList: [
        { label: '商品编码', prop: 'code', isCheck: true, disabled: false },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],

      /** 选择商品 */
      dialogListConfig: {
        title: '选择商品',
        columns: [
          {
            prop: 'spuCode',
            label: '商品编码',
          },
          {
            prop: 'spuName',
            label: '商品名称',
          },
        ],
        search: [
          {
            placeholder: '请输入商品编码',
            key: 'spuCode',
          },
          {
            placeholder: '请输入商品名称',
            key: 'spuName',
          },
        ],
        actionApi: goodsApi.getSku,
        query: '',
        changeColumns: [
          {
            prop: 'spuName',
            label: '商品名称',
          },
        ],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.$refs.BjPage.tableData = [
      {
        code: '898989',
        spuName: '卫浴A',
      },
      {
        code: '898989',
        spuName: '卫浴A',
      },
      {
        code: '898989',
        spuName: '卫浴A',
      },
    ];
  },
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParamsTime_create = null;
      this.queryParamsTime_pay = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    onConfirm(data) {
      console.log(data);
    },
    // /** 创建 */
    onAdd() {
      this.$refs.tableData.show();
    },
    handleDelete(scope) {
      console.log(scope.$index);
      console.log(scope.row);
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
