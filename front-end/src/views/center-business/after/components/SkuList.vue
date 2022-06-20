<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :get-action="queryApi"
      :serial="serial"
      :selection="selection"
      :stripe="false"
      search-btn-hide
      right-toolbar-hide
      no-auto-height
      :row-key="rowKey"
      :reset-data="resetDataFn"
      lazy
      :load="load"
      :row-style="$treeRowStyleFun"
      @handleSelectionChange="handleSelectionChange"
      @handleExpandTableSelectionChange="handleExpandTableSelectionChange"
    >
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

              <span v-else-if="col.prop === 'giftFlag'">
                {{ STATUS_YES_NUM.bjGet(scope.row[col.prop]) }}</span
              >
              <span v-else-if="col.prop.includes('Price')">
                {{ $number2money(scope.row[col.prop]) }}</span
              >
              <span v-else>{{ scope.row[col.prop] }}</span>
            </template>
          </el-table-column>
          <el-table-column
            v-if="col.prop === 'bjTableAction' && !readonly"
            :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            :fixed="col.fixed || false"
            align="center"
          >
            <template slot-scope="scope">
              <el-button
                v-for="(btn, btnIndex) in parentColumnsBtn"
                :key="btnIndex"
                :type="btn.type || 'text'"
                @click="btn.click(scope.row, scope.$index)"
                >{{ btn.text }}</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
      <template v-slot:paginationLeft>
        <div class="text-right">应退买家合计：{{ $number2money(totalPrice) }}</div>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import {
  STATUS_YES_NUM,
  ORDER_INTERNAL_LINE_PREEMPT_STATUS_LIST,
  ORDER_INTERNAL_LINE_SOURCE_STATUS_LIST,
  ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST,
} from '@/utils/constants';
export default {
  name: 'BusinessOrderPreemptDetailSkuList',
  components: {},
  mixins: [pageMiXin],
  props: {
    rowKey: {
      type: String,
      default: 'id',
    },
    expandRowKey: {
      type: String,
      default: 'id',
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    defaultQueryParams: {
      type: Object,
      default: () => {
        return {};
      },
    },
    parentLabelList: {
      type: Array,
      default: () => {
        return [
          { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false, width: 160 },
          { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
          { label: 'SKU名称', prop: 'skuName', isCheck: true, disabled: false, width: 160 },
          { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        ];
      },
    },
    parentColumnsBtn: {
      type: Array,
      default: () => {
        return [
          // { text: '删除', click: () => {} }
        ];
      },
    },
    parentQueryApi: Function,
    childrenQueryApi: Function,
  },
  data() {
    return {
      serial: true,
      selection: false,
      tableSortableDisabled: true,
      sortTableByOptionName: true,
      STATUS_YES_NUM,
      ORDER_INTERNAL_LINE_PREEMPT_STATUS_LIST,
      ORDER_INTERNAL_LINE_SOURCE_STATUS_LIST,
      ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST,
      // 查询参数
      queryParams: {
        id: null,
      },
      queryApi: this.parentQueryApi,
      labelList: this.parentLabelList,
      selectedRows: null,
      expandTableSelectedObj: null,
      totalPrice: 0,
    };
  },
  created() {
    if (this.defaultQueryParams) {
      this.queryParams = { ...this.queryParams, ...this.defaultQueryParams };
    }
  },
  methods: {
    resetDataFn(data) {
      this.totalPrice = 0;
      return data.map(e => {
        if (e.allPrice) {
          this.totalPrice += Number(e.allPrice || 0);
        }
        return { ...e };
      });
    },
    handleSelectionChange(data) {
      /** 商品 */
      console.log('SKU_LIST handleSelectionChange', data);
      this.selectedRows = data;
    },
    load({ row }) {
      // 异步加载子节点
      return new Promise((resolve, reject) => {
        if (row.BJ_SELECT_CHILD.length) {
          resolve(row.BJ_SELECT_CHILD);
          return;
        }
        this.childrenQueryApi(row).then(res => {
          console.log('res', res);

          row['BJ_SELECT_CHILD'] = (Array.isArray(res) ? res : []).map(e => {
            return {
              ...e,
              hasChildren: false,
              BJ_PARENT_ID: row[this.rowKey],
              refundableQuantity: 1,
              quantity: 1,
              unitPrice: this.$number2money(row.unitPrice || 0),
              allPrice: this.$number2money(row.unitPrice || 0),
            };
          });
          if (row.BJ_SELECT_CHILD.length) {
            resolve(row.BJ_SELECT_CHILD);
          } else {
            this.$message.warning('返回数据为空，请联系管理员');
            reject();
          }

          this.$emit('setSkuTableData', row);
        });
      });
    },

    onSkuLineLog() {},
  },
};
</script>
