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
    <BjRouteTabs :route-list="STOCK_DELIVERY_ROUTE_LIST" />
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
            <BjInputCode v-model.trim="queryParams.queryCode" @keyup.enter.native="handleQuery" />
          </el-form-item>
        </el-col>
        <BjSelectChannelStore
          :span="colSpan"
          :query-params="queryParams"
          :query-props="['channelTypeCode', 'shopIdList']"
          store-option-value="id"
          multiple
        />
        <el-col :span="colSpan">
          <el-form-item label="出库逻辑仓" prop="outLogicWarehouseCode">
            <el-input
              v-model.trim="queryParams.outLogicWarehouseCode"
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
              <div v-if="['odoCode', 'resultCode'].includes(col.prop)">
                <span class="link" @click="onView(scope.row, col.prop)">
                  {{ scope.row[col.prop] }}
                </span>
                <BjCopyIcon
                  v-if="col.prop !== 'resultCode'"
                  class="ml10"
                  :copy="scope.row[col.prop]"
                />
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
import { STOCK_DELIVERY_ROUTE_LIST, ORDER_STOCK_RESULT_STATUS_LIST } from '@/utils/constants';
export default {
  name: 'BusinessStockDeliveryResultIndex',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 8,
      STOCK_DELIVERY_ROUTE_LIST,
      ORDER_STOCK_RESULT_STATUS_LIST,
      queryApi: stockApi.getResultRecordsPage,
      // 查询参数
      queryParams: {
        queryCode: null,
        channelTypeCode: null,
        shopIdList: [],
        outLogicWarehouseCode: null,
        beginCreationDate: null,
        endCreationDate: null,
      },
      queryParamsTime: null,
      labelsVersion: 11,
      labelList: [
        { label: '出库结果单号', prop: 'resultCode', isCheck: true, disabled: false, width: '160' },
        { label: '出库通知单号', prop: 'odoCode', isCheck: true, disabled: false, width: '160' },
        { label: '出库类型', prop: 'outTypeMeaning', isCheck: true, disabled: false },
        { label: '出库状态', prop: 'status', isCheck: true, disabled: false },
        {
          label: '已发货数量',
          prop: 'alreadyOutQty',
          isCheck: true,
          disabled: false,
          width: '100',
        },
        {
          label: '出库逻辑仓编码',
          prop: 'outLogicWarehouseCode',
          isCheck: true,
          disabled: false,
          width: '160',
        },
        {
          label: '出库逻辑仓名称',
          prop: 'outLogicWarehouseName',
          isCheck: true,
          disabled: false,
          width: '160',
        },
        { label: '销售店铺', prop: 'shopName', isCheck: true, disabled: false },
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
      this.queryParams.shopIdList = null;
      this.queryParamsTime = null;
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 跳转 */
    onView(row, type) {
      /** 结果单详情 */
      if (type === 'resultCode') {
        this.$toBusinessStockDeliveryResultView(row.id);
      }
      /** 通知单详情 */
      if (type === 'odoCode') {
        this.$toBusinessStockDeliveryInformView(row.odoId);
      }
    },
  },
};
</script>
<style lang="scss" scoped></style>
