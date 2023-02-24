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
      <template v-slot:baseForm>
        <el-col :span="colSpan">
          <el-form-item label="渠道" prop="channelTypeCode">
            <BjSelectLov v-model="queryParams.channelTypeCode" lov-code="SCHL.CHANNEL_TYPE" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="策略名称" prop="name">
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
          <el-form-item label="状态" prop="enabledFlag">
            <BjSelectLov
              v-model="queryParams.enabledFlag"
              :option-list="STATUS_ENABLED_TRUE"
              clearable
            />
          </el-form-item>
        </el-col>
      </template>
      <template v-slot:tableOperation="">
        <el-col>
          <el-button
            v-permission:SettingFinanceAnalysisStrategy_Add
            type="primary"
            class="main-btn"
            @click="onAdd"
            >新增策略</el-button
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
            :show-overflow-tooltip="col.prop !== 'bjTableAction'"
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <template v-else-if="col.prop === 'type'">
                {{ FINANCE_PARSESTRATEGY_BULL_TYPES.bjGet(scope.row[col.prop]) }}
              </template>
              <!-- 金额取值区分固定字段和计算公式显示 -->
              <template v-else-if="col.prop === 'amountType'">
                {{ scope.row.amountStr }}
              </template>
              <el-tag
                v-else-if="col.prop === 'enabledFlag'"
                :type="scope.row[col.prop] ? 'success' : 'info'"
                >{{ STATUS_ENABLED_TRUE.bjGet(scope.row[col.prop]) }}</el-tag
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
            <template slot-scope="props">
              <el-button
                v-permission:SettingFinanceAnalysisStrategy_Edit
                type="text"
                class="edit-btn"
                @click="onEdit(props.row)"
                >编辑</el-button
              >
              <el-button
                v-if="!props.row.enabledFlag"
                v-permission:SettingFinanceAnalysisStrategy_Status
                type="text"
                @click="onStatus(props.row, true)"
                >启用</el-button
              >
              <el-button
                v-if="props.row.enabledFlag"
                v-permission:SettingFinanceAnalysisStrategy_Status
                type="text"
                @click="onStatus(props.row, false)"
                >禁用</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>

    <!-- 新增/编辑弹窗 -->
    <DataForm ref="DataForm" @reFresh="reFresh" @batchDeleteApi="batchDeleteApi" />
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { financeApi } from '@/api';
import {
  STATUS_ENABLED_TRUE,
  FINANCE_PARSESTRATEGY_BULL_TYPES,
  FINANCE_PARSESTRATEGY_PRICE_VALUE_TYPES,
} from '@/utils/constants';
import DataForm from './components/DataForm.vue';

export default {
  name: 'SettingFinanceAnalysisStrategyIndex',
  components: {
    DataForm,
  },
  mixins: [pageMiXin],
  data() {
    return {
      selection: false,
      STATUS_ENABLED_TRUE,
      FINANCE_PARSESTRATEGY_BULL_TYPES,
      FINANCE_PARSESTRATEGY_PRICE_VALUE_TYPES,
      queryApi: financeApi.getParsingStrategysPage,
      // 查询参数
      queryParams: {
        channelTypeCode: null,
        name: null,
        enabledFlag: null,
      },
      labelsVersion: 11,
      labelList: [
        { label: '销售渠道', prop: 'channelTypeCodeMeaning', isCheck: true, disabled: false },
        { label: '策略名称', prop: 'name', isCheck: true, disabled: false },
        { label: '账单类型', prop: 'type', isCheck: true, disabled: false },
        { label: '取值字段', prop: 'filterFieldMeaning', isCheck: true, disabled: false },
        { label: '匹配关键字', prop: 'filterKeyword', isCheck: true, disabled: false },
        { label: '金额取值', prop: 'amountType', isCheck: true, disabled: false, width: '360px' },
        { label: '订单号取值', prop: 'orderNoFieldMeaning', isCheck: true, disabled: false },
        { label: '退款单号取值', prop: 'refundNoFieldMeaning', isCheck: true, disabled: false },
        { label: '状态', prop: 'enabledFlag', isCheck: true, disabled: false },
        { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129 },
      ],
    };
  },
  watch: {},
  methods: {
    /** 新增网店 */
    onAdd() {
      this.$refs.DataForm.show();
    },
    onEdit(row) {
      this.$refs.DataForm.show(row);
    },
    onStatus(row, enabledFlag) {
      financeApi
        .updateParsingStrategysStatus({
          enabledFlag,
          ids: [row.id],
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
    batchDeleteApi(data) {},
  },
};
</script>
