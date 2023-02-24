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
      :auto-scroll="false"
      row-key="id"
      :reset-data="resetDataFn"
      lazy
      :load="loadMethod"
      :row-style="treeRowStyleFun"
      :check-method="checkMethod"
      @handleTableDataChange="handleTableDataChange"
      @handleSelectionChange="handleSelectionChange"
    >
      <template v-slot:tableColumn>
        <el-table-column
          label="商品名称"
          prop="spuName"
          align="center"
          width="160"
        ></el-table-column>
        <el-table-column label="商品编码" prop="spuCode" align="center" width="160" />
        <el-table-column label="SKU名称" prop="skuName" align="center" width="160" />
        <el-table-column label="SKU编码" prop="skuCode" align="center" width="160" />
        <el-table-column label="数量" prop="quantity" align="center" width="80">
          <template slot-scope="scope">
            <el-input-number
              v-if="!scope.row.isChildSku"
              v-model="scope.row.quantity"
              :maxlength="MAX_LENGTH.DEFAULT"
              class="full-width"
              :min="0"
              :precision="0"
              :controls="false"
              @blur="onBlurQuantity(scope.row)"
            />
            <span v-if="scope.row.isChildSku">{{ scope.row.num }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否赠品" prop="giftFlag" align="center" width="80">
          <template slot-scope="scope" align="center">
            <BjSelectLov
              v-if="!scope.row.isChildSku"
              v-model="scope.row.giftFlag"
              :option-list="SKU_GIFT_TYPE_LIST_FOR_CREATE"
              :clearable="false"
              @change="onChangeGiftFlag(scope.row)"
            />
            <span v-if="scope.row.isChildSku">{{
              SKU_GIFT_TYPE_LIST_FOR_CREATE.bjGet(scope.row.giftFlag)
            }}</span>
          </template>
        </el-table-column>
        <!--  salesPrice->outerSingleSaleAmount  -->
        <el-table-column
          label="三方平台销售单价"
          prop="outerSingleSaleAmount"
          align="center"
          width="80"
        >
          <template slot-scope="scope">
            <span v-if="!scope.row.isChildSku">
              {{ $number2money(scope.row.outerSingleSaleAmount) }}
            </span>
            <span v-if="scope.row.isChildSku">
              {{ $number2money(scope.row.salesPrice) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="三方平台均摊总价" prop="outerSaleAmount" align="center" width="160">
          <template slot-scope="scope">
            <BjInputMoney
              v-if="!scope.row.isChildSku"
              v-model="scope.row.outerSaleAmount"
              :disabled="scope.row.giftFlag == 1"
            />

            <span v-if="scope.row.isChildSku">
              {{ $number2money(scope.row.salesPrice) }}
            </span>
          </template>
        </el-table-column>
        <!--  salesPrice->innerSingleSaleAmount  -->
        <el-table-column label="内部销售价" prop="innerSingleSaleAmount" align="center" width="80">
          <template slot-scope="scope">
            <span v-if="!scope.row.isChildSku">
              {{ $number2money(scope.row.innerSingleSaleAmount) }}
            </span>
            <span v-if="scope.row.isChildSku">
              {{ $number2money(scope.row.salesPrice) }}
            </span>
          </template>
        </el-table-column>
        <!-- innerSaleAmount-->
        <el-table-column label="内部均摊总价" prop="innerSaleAmount" align="center" width="80">
          <template slot-scope="scope">
            <span v-if="!scope.row.isChildSku">
              {{
                $number2money((scope.row.innerSingleSaleAmount || 0) * (scope.row.quantity || 0))
              }}
            </span>
            <span v-if="scope.row.isChildSku">
              {{ $number2money(scope.row.salesPrice) }}
            </span>
          </template>
        </el-table-column>

        <el-table-column label="成本单价" prop="costPrice" align="center" width="80">
          <template slot-scope="scope">
            {{ $number2money(scope.row.costPrice) }}
          </template>
        </el-table-column>
        <el-table-column label="成本总价" prop="costAmount" align="center" width="80">
          <template slot-scope="scope">
            <span v-if="!scope.row.isChildSku">
              {{
                $number2money(Number(scope.row.costPrice || 0) * Number(scope.row.quantity || 0))
              }}
            </span>
            <span v-if="scope.row.isChildSku">
              {{ $number2money(scope.row.salesPrice) }}
            </span>
          </template>
        </el-table-column>

        <el-table-column label="运费" prop="transPrice" align="center">
          <template slot-scope="scope">
            <BjInputMoney
              v-if="!scope.row.isChildSku"
              v-model="scope.row.transPrice"
              :disabled="scope.row.giftFlag == 1"
            />
            <span v-if="scope.row.isChildSku"> - </span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="left" fixed="right" width="120">
          <template slot-scope="scope">
            <!-- 子商品不可单独删除 -->
            <el-button v-show="!scope.row.isChildSku" type="text" @click="onDelete(scope.row)"
              >删 除</el-button
            >
          </template>
        </el-table-column>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { SKU_GIFT_TYPE_LIST_FOR_CREATE } from '@/utils/constants';
import skuApi from '@/api/goodsApi/skuApi';
export default {
  name: 'BusinessOrderInternalOrderItemTable',
  components: {},
  mixins: [pageMiXin],
  props: {
    tableData: {
      type: Array,
      default: function () {
        return [];
      },
    },
    defaultQueryParams: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      serial: true,
      selection: true,
      SKU_GIFT_TYPE_LIST_FOR_CREATE,
      // 查询参数
      queryParams: {
        id: null,
      },

      selectedRows: null,
      expandTableSelectedObj: null,
    };
  },
  watch: {},
  created() {
    if (this.defaultQueryParams) {
      this.queryParams = { ...this.queryParams, ...this.defaultQueryParams };
    }
  },
  methods: {
    treeRowStyleFun({ row, rowIndex }) {
      return this.$treeRowStyleFun({ row, rowIndex }, 'isChildSku', true);
    },
    checkMethod(row) {
      return !row.isChildSku;
    },
    // 更新父组件数据
    handleTableDataChange(v) {
      const TEMP = this.tableData.map(e => {
        const FIND = (v || []).find(ee => ee.BJ_EBC_ID == e.BJ_EBC_ID);
        if (FIND) {
          return FIND;
        }
        return e;
      });
      this.$emit('update:tableData', TEMP);
    },
    // 主表查询API
    queryApi(queryParams) {
      const start = queryParams.currentPage * queryParams.pageSize;
      const end = queryParams.page * queryParams.pageSize;
      return new Promise(resolve => {
        resolve({
          content: this.tableData.slice(start, end),
          totalElements: this.tableData.length, // 数据总数
        });
      });
    },

    // 子表查询API
    childrenQueryApi(queryParams) {
      return skuApi.getMealsGoodsList(queryParams);
    },
    onDelete(row) {
      this.$emit('delete', row);
    },
    handleSelectionChange(d) {
      this.$emit('selection-change', d);
    },
    onChangeGiftFlag(row) {
      this.$nextTick(() => {
        if (row.giftFlag == 1) {
          row.outerSaleAmount = '0.00';
          row.transPrice = '0.00';
        } else {
          this.onBlurQuantity(row);
        }
      });
    },
    onBlurQuantity(row) {
      console.warn('onBlurQuantity', row);
      row.outerSaleAmount = this.$number2money(
        (row.outerSingleSaleAmount || 0) * (row.quantity || 0),
      );
      row.innerSaleAmount = this.$number2money(
        (row.innerSingleSaleAmount || 0) * (row.quantity || 0),
      );
      row.costAmount = this.$number2money((row.costPrice || 0) * (row.quantity || 0));
    },
    /** * */
    resetDataFn(data) {
      return data.map(e => {
        return { BJ_HAS_CHILD: true, BJ_PARENT_ID: null, ...e };
      });
    },
    // onAction(action) {
    //   const hasChildren = true;
    //   this.$emit('onAction', action, hasChildren, this.selectedRows);
    // },
    loadMethod({ row }) {
      // 异步加载子节点
      return new Promise((resolve, reject) => {
        this.childrenQueryApi([row.skuCode]).then(res => {
          console.log('res', res);
          const TEMP = (Array.isArray(res) ? res : []).map(e => {
            return { ...e, BJ_HAS_CHILD: false, BJ_PARENT_ID: row.BJ_EBC_ID, isChildSku: true };
          });

          if (TEMP.length) {
            resolve(TEMP);
          } else {
            this.$message.warning('返回数据为空，请联系管理员');
            reject();
          }
        });
      });
    },

    onSkuLineLog() {},
  },
};
</script>
