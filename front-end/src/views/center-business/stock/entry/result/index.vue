<!--
 * @Author: your name
 * @Date: 2021-08-26 17:15:19
 * @LastEditTime: 2022-04-07 11:32:06
 * @LastEditTime: 2021-08-30 16:53:11
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\stock\out\index.vue
-->
<template>
  <div class="app-container">
    <BjRouteTabs />
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="false"
      :before-reset-search="beforeResetSearch"
      @showSetting="SortableTable.show"
      @header-dragend="SortableTable.handleHeaderDragend"
      @header-contextmenu="SortableTable.handleHeaderContextmenu"
    >
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="单号" prop="queryCode">
            <BjInputCode
              v-model.trim="queryParams.queryCode"
              placeholder="入库通知单号/入库结果单号/三方订单号/内部单号"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <BjSelectChannelStore
          :span="colSpan"
          :query-params="queryParams"
          :query-props="['channelTypeCode', 'storeIdList']"
          store-option-value="id"
          multiple
        />
        <el-col :span="colSpan">
          <el-form-item label="入库状态" prop="status">
            <BjSelectLov v-model="queryParams.status" lov-code="STOCK.INPUT_STATUS" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="入库逻辑仓" prop="receivingLogicWarehouseCode">
            <el-input
              v-model.trim="queryParams.receivingLogicWarehouseCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入逻辑仓编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="创建时间" prop="beginCreationDate">
            <el-date-picker
              v-model="queryParamsTime"
              class="full-width"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              :picker-options="$pickerOptions()"
              align="right"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']"
            />
          </el-form-item>
        </el-col>
      </template>
      <!-- <template v-slot:tableOperation>
        <el-col>
            <el-button type="primary" >按 钮</el-button>
          </el-col>
        </div>
      </template> -->
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
              <div v-if="['code', 'warehouseWarrantCode'].includes(col.prop)">
                <span class="link" @click="onView(scope.row, col.prop)">
                  {{ scope.row[col.prop] }}
                </span>
                <BjCopyIcon class="ml10" :copy="scope.row[col.prop]" />
              </div>
              <span v-else-if="col.prop === 'status'">{{
                ORDER_STOCK_RESULT_STATUS_LIST.bjGet(scope.row[col.prop])
              }}</span>
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column
            v-if="col.prop === 'bjTableAction'"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || 'right'"
            align="center"
          >
            <template slot-scope="props">
              <el-button type="text" >按钮</el-button>
            </template>
          </el-table-column> -->
        </template>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { stockApi } from '@/api';
import { ORDER_STOCK_RESULT_STATUS_LIST } from '@/utils/constants';
export default {
  name: 'BusinessStockEntryResultIndex',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 8,

      ORDER_STOCK_RESULT_STATUS_LIST,
      queryApi: stockApi.getEntryResult,
      // 查询参数
      queryParams: {
        queryCode: null,
        channelTypeCode: null,
        storeIdList: [],
        receivingLogicWarehouseCode: null,
        beginCreationDate: null,
        endCreationDate: null,
        status: null,
      },
      queryParamsTime: null,
      labelsVersion: 11,
      labelList: [
        { label: '入库结果单号', prop: 'code', isCheck: true, disabled: false, width: '160' },
        {
          label: '入库通知单号',
          prop: 'warehouseWarrantCode',
          isCheck: true,
          disabled: false,
          width: '160',
        },
        { label: '入库类型', prop: 'inputTypeMeaning', isCheck: true, disabled: false },
        { label: '入库状态', prop: 'statusMeaning', isCheck: true, disabled: false },
        {
          label: '已入库数量',
          prop: 'alreadyQty',
          isCheck: true,
          disabled: false,
          width: '100',
        },
        {
          label: '入库逻辑仓编码',
          prop: 'receivingLogicWarehouseCode',
          isCheck: true,
          disabled: false,
          width: '160',
        },
        {
          label: '入库逻辑仓名称',
          prop: 'receivingLogicWarehouseName',
          isCheck: true,
          disabled: false,
          width: '160',
        },
        { label: '销售店铺', prop: 'storeName', isCheck: true, disabled: false },
        { label: '销售渠道', prop: 'channelTypeCodeMeaning', isCheck: true, disabled: false },
        { label: '创建时间', prop: 'creationDate', isCheck: true, disabled: false, width: 135 },
        // { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129},
      ],
    };
  },
  computed: {},
  watch: {
    queryParamsTime(v) {
      this.queryParams.beginCreationDate = (v || [])[0];
      this.queryParams.endCreationDate = (v || [])[1];
    },
  },
  created() {},
  mounted() {},
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.queryParams.storeIdList = null;
      this.queryParamsTime = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 跳转 */
    onView(row, type) {
      /** 结果单详情 */
      if (type === 'code') {
        this.$toBusinessStockEntryResultView(row[type]);
      }
      /** 通知单详情 */
      if (type === 'warehouseWarrantCode') {
        this.$toBusinessStockEntryInformView(row[type]);
      }
    },
  },
};
</script>
<style lang="scss" scoped></style>
