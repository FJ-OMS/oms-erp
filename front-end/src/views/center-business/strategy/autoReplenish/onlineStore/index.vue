<!--
 * @Author: your name
 * @Date: 2021-10-04 14:43:19
 * @LastEditTime: 2021-10-05 17:50:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\strategy\autoReplenish\onlineStore\index.vue
-->
<template>
  <div class="app-container">
    <BjRouteTabs :route-list="STRATEGY_AUTO_REPLENISH_ROUTE_LIST" />
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
          <el-form-item label="网店编码" prop="code">
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
          <el-form-item label="网店名称" prop="storeName">
            <el-input
              v-model.trim="queryParams.storeName"
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

          <el-button type="primary" class="main-btn" @click="onAdd">新增网店</el-button>
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
              <el-button v-if="!props.row.releaseStatus" type="text" @click="onEdit(props.row)"
                >编 辑</el-button
              >
              <el-button v-if="!props.row.releaseStatus" type="text" @click="onDelete(props.row)"
                >删 除</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
    <!-- 新增店铺 -->
    <DataForm ref="DataForm" @reFresh="reFresh" />
    <TableData ref="tableData" :config="dialogListConfig">
      <template slot="sort" slot-scope="data">
        <el-input-number
          v-model="data.scope.row.sort"
          :maxlength="MAX_LENGTH.DEFAULT"
          :min="1"
          :max="10"
          :precision="0"
          :controls="false"
        />
      </template>
      <template slot="button" slot-scope="data">
        <el-button type="text" @click="handleDelete(data.scope)">删 除</el-button>
      </template>
    </TableData>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { goodsApi } from '@/api';
import DataForm from './components/DataForm.vue';
import TableData from '../components/tableData';
import { STRATEGY_AUTO_REPLENISH_ROUTE_LIST } from '@/utils/constants';
// const getDefaultForm = () => {
//   return {};
// };
// import tableData from './data.json';
export default {
  name: 'BusinessAfterAudit',
  components: { DataForm, TableData },
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
        storeName: null,
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
        { label: '排序', prop: 'sort', isCheck: true, disabled: false },
        { label: '网店编码', prop: 'code', isCheck: true, disabled: false },
        { label: '网店名称', prop: 'storeName', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],

      /** 选择商品 */
      dialogListConfig: {
        title: '选择商品',
        columns: [
          {
            prop: 'spuCode',
            label: '网店编码',
          },
          {
            prop: 'spuName',
            label: '网店名称',
          },
        ],
        search: [
          {
            placeholder: '请输入网店编码',
            key: 'spuCode',
          },
          {
            placeholder: '请输入网店名称',
            key: 'spuName',
          },
        ],
        actionApi: goodsApi.getSku,
        query: '',
        changeColumns: [
          {
            prop: 'spuName',
            label: '网店名称',
          },
          {
            prop: 'num',
            label: '排序',
            slot: 'sort',
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
        sort: 1,
        code: '5555',
        storeName: '京东自营',
      },
      {
        sort: 2,
        code: '6666',
        storeName: '天猫旗舰',
      },
      {
        sort: 3,
        code: '51555',
        storeName: '拼多多企业店',
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
    // /** 创建 */
    onAdd() {
      this.$refs.tableData.show();
    },
    onEdit(row) {
      this.$refs.DataForm.show(row);
    },
    handleDelete(scope) {
      console.log(scope);
    },
    // /** 创建 */
    // onAdd() {
    //   this.$refs.DataForm.show();
    // },

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
