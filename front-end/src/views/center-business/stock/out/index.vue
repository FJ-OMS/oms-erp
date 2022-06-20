<template>
  <div class="app-container">
    <BjPage
      ref="BjPage"
      :query-params="queryParams"
      :col-span="colSpan"
      :label-width="'135px'"
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
          <el-form-item label="商品信息" prop="spuCode">
            <el-input
              v-model.trim="queryParams.spuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入商品名称/商品编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="SKU编码" prop="skuCode">
            <el-input
              v-model.trim="queryParams.skuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入SKU编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <!-- <el-col :span="colSpan">
          <el-form-item label="是否优先补货" prop="priorityReplenish">
            <BjSelectLov v-model="queryParams.priorityReplenish" :option-list="STATUS_YES_TRUE" />
          </el-form-item>
        </el-col>

        <BjSelectChannelStore
          :span="colSpan"
          :query-params="queryParams"
          :query-props="['channelTypeCode', 'shopCode']"
          store-option-value="code"
        /> -->
      </template>
      <!-- <template v-slot:tableOperation>
        <el-col>
          <el-button type="primary" @click="onAdd()" class="main-btn">新增补货店铺</el-button>
          <el-button v-show="!ids.length" type="primary" @click="onEditAll()">批量编辑</el-button>
          <el-button v-show="ids.length" type="primary" @click="onSaveAll()">批量保存</el-button>
          <el-button v-show="ids.length" @click="onCancelAll()">取消批量编辑</el-button>
        </el-col>
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
            :show-overflow-tooltip="col.prop !== 'bjTableAction'"
          >
            <template slot-scope="scope">
              <template v-if="false" />
              <template v-else-if="col.prop === 'platformOrderCode'">
                <span class="link" @click="onView(scope.row, col.prop)">{{
                  scope.row[col.prop]
                }}</span>
              </template>
              <template v-else-if="col.prop === 'shopPriorityReplenish'">
                <BjSelectLov
                  v-show="ids.includes(scope.row.id) || id === scope.row.id"
                  v-model="scope.row[col.prop]"
                  :clearable="false"
                  :option-list="STATUS_YES_TRUE"
                />
                <span v-show="!ids.includes(scope.row.id) && id !== scope.row.id">{{
                  STATUS_YES_TRUE.bjGet(scope.row.shopPriorityReplenish)
                }}</span>
              </template>
              <template v-else-if="col.prop === 'shopPriorityGrade'">
                <el-select
                  v-show="ids.includes(scope.row.id) || id === scope.row.id"
                  v-model="scope.row[col.prop]"
                  class="full-width"
                >
                  <el-option v-for="i in 5" :key="i" :label="i" :value="i" />
                </el-select>
                <!-- <el-input-number :maxlength="MAX_LENGTH.DEFAULT"
                  v-show="ids.includes(scope.row.id)"
                  v-model="scope.row[col.prop]"
                  :precision="0"
                  :min="0"
                  :controls="false"

                  class="full-width"
                /> -->
                <span v-show="!ids.includes(scope.row.id) && id !== scope.row.id">{{
                  scope.row[col.prop]
                }}</span>
              </template>
              <template v-else-if="col.prop === 'shopPriorityReplenishQty'">
                <el-input-number
                  v-show="ids.includes(scope.row.id) || id === scope.row.id"
                  v-model="scope.row[col.prop]"
                  :maxlength="MAX_LENGTH.DEFAULT"
                  :precision="0"
                  :min="0"
                  :controls="false"
                  class="full-width"
                />
                <span v-show="!ids.includes(scope.row.id) && id !== scope.row.id">{{
                  scope.row[col.prop]
                }}</span>
              </template>
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
              <el-button v-show="id !== props.row.id" type="text" @click="onEdit(props.row)"
                >编 辑</el-button
              >
              <el-button v-show="id === props.row.id" type="text" @click="onSave(props.row)"
                >保 存</el-button
              >
              <el-button
                v-show="id === props.row.id"
                type="text"
                @click="onCancel(props.row, index)"
                >取 消</el-button
              >
            </template>
          </el-table-column>
        </template>
      </template>
    </BjPage>
  </div>
</template>
<script>
import pageMiXin from '@/utils/page-mixin.js';
import { stockApi } from '@/api';
import { STATUS_YES_TRUE } from '@/utils/constants';

export default {
  name: 'BusinessStockOutList',
  components: {},
  mixins: [pageMiXin],
  data() {
    return {
      colSpan: 8,
      selection: false,
      STATUS_YES_TRUE,
      queryApi: stockApi.getStockOutShopPage, //
      // 查询参数
      queryParams: {
        channelTypeCode: null,
        shopCode: null,
        priorityReplenish: null,
        skuCode: null,
        spuCode: null,
        // "tenantId": 0
      },

      labelsVersion: 11,
      labelList: [
        {
          label: '商品名称',
          prop: 'spuName',
          isCheck: true,
          disabled: false,
        },

        {
          label: '商品编码',
          prop: 'spuCode',
          isCheck: true,
          disabled: false,
        },

        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false },

        { label: '缺货订单数', prop: 'orderStockoutNum', isCheck: true, disabled: false },

        { label: '缺货数量', prop: 'stockoutNum', isCheck: true, disabled: false },

        // { label: '店铺编码', prop: 'shopCode', isCheck: true, disabled: false },
        // { label: '店铺名称', prop: 'shopName', isCheck: true, disabled: false },
        // { label: '销售渠道', prop: 'channelTypeMeaning', isCheck: true, disabled: false },
        // { label: '是否优先补货', prop: 'shopPriorityReplenish', isCheck: true, disabled: false },
        // { label: '优先级', prop: 'shopPriorityGrade', isCheck: true, disabled: false },
        // { label: '优先补货数量', prop: 'shopPriorityReplenishQty', isCheck: true, disabled: false },
        // { label: '操作', prop: 'bjTableAction', isCheck: true, disabled: true, width: 129},
      ],
      ids: [], // TODO: 列表翻页时，需要清空ids
      id: null,
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    onAdd() {
      this.$toPage('BusinessStockOutAdd');
    },
    /** 重置按钮操作 */
    beforeResetSearch() {
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 批量编辑 */
    onEditAll() {
      this.ids = this.$refs.BjPage.tableData.map(e => e.id);
      this.id = null;
    },
    onSaveAll() {
      if (this.ids.length) {
        const settingList = [];
        this.ids.forEach(e => {
          const data = this.$refs.BjPage.tableData.find(item => item.id === e);
          if (data) {
            settingList.push({
              id: data.id,
              priorityGrade: data.shopPriorityGrade,
              priorityReplenish: data.shopPriorityReplenish,
              priorityReplenishQty: data.shopPriorityReplenishQty,
            });
          } else {
            console.warn(this.ids);
          }
        });
        this.save(false, settingList).then(res => {
          this.$refs.BjPage.clearChange();
          this.ids = [];
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        });
      }
    },
    onCancelAll() {
      this.$refs.BjPage.clearChange();
      this.ids = [];
      this.id = null;
    },
    /** 编辑 */
    onEdit(row) {
      this.ids.push(row.id);
      this.id = row.id;
    },
    /** 保存 */
    onSave(row) {
      if (row && row.id) {
        const settingList = {
          id: row.id,
          priorityGrade: row.shopPriorityGrade,
          priorityReplenish: row.shopPriorityReplenish,
          priorityReplenishQty: row.shopPriorityReplenishQty,
        };
        this.save(false, [settingList]).then(res => {
          const INDEX = this.ids.findIndex(e => this.id == e);
          if (INDEX > -1) this.ids.splice(INDEX, 1);
          this.id = null;
          this.$message.success(res.message || '操作成功');
        });
      }
    },
    /** 取消编辑 */
    onCancel(row, index) {
      this.$refs.BjPage.clearChange();
      const INDEX = this.ids.findIndex(e => this.id == e);
      if (INDEX > -1) this.ids.splice(INDEX, 1);
      this.id = null;
    },
    /** 详情 */
    /**
     * @description: 跳转
     * @param {*} row 数据
     * @param {*} type 0：总缺货数量、1：缺货网店数量
     * @return {*}
     */
    onView(row) {
      // this.$toPage(pageName, { id: row.innerOrderSn });
    },
    /**
     * @description: 保存接口
     * @param {*} flag true 订单 false 店铺
     * @param {*} settingList
     * @return {*}
     */
    save(flag, settingList) {
      return new Promise((resolve, reject) => {
        stockApi.stockOutBatSetting({ flag, settingList }).then(res => {
          if (res) {
            resolve(res);
          } else {
            reject(res);
          }
        });
      });
    },
  },
};
</script>
