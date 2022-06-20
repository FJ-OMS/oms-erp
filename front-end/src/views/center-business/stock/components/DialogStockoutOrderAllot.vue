<template>
  <div>
    <BjDialog :title="title" :visible="visible" width="1100px" @close="hide">
      <BjPage
        ref="BjPage"
        :query-params="queryParams"
        :col-span="colSpan"
        label-width="70px"
        :get-action="queryApi"
        :serial="serial"
        :stripe="false"
        :selection="selection"
        right-toolbar-hide
        no-auto-height
        is-list
        :reset-data="resetData"
        @handleSelectionChange="handleSelectionChange"
      >
        <template v-slot:baseForm>
          <el-col :span="colSpan">
            <el-form-item label="订单号" prop="queryCode">
              <BjInputCode
                v-model.trim="queryParams.queryCode"
                @keyup.enter.native="handleQuery"
              /> </el-form-item
          ></el-col>
        </template>
        <template v-slot:tableTop>
          <div class="mb10">
            <span>可分配库存：{{ planNum }}</span>
          </div>
        </template>
        <template v-slot:tableColumn>
          <el-table-column
            label="三方订单号"
            prop="platformOrderCode"
            align="center"
          ></el-table-column>
          <el-table-column label="内部订单号" prop="orderCode" align="center"></el-table-column>
          <el-table-column
            label="三方下单时间"
            prop="orderCreationDate"
            align="center"
          ></el-table-column>
          <el-table-column label="缺货数量" prop="stockoutQty" align="center"></el-table-column>
          <el-table-column
            label="预计发货时间"
            prop="scheduledDeliveryTime"
            align="center"
          ></el-table-column>
          <el-table-column label="补货数量" prop="replenishQty" align="center">
            <template slot-scope="props">
              <BjInputNumber
                v-model="props.row.replenishQty"
                :disabled="isView"
                :max="props.row.stockoutQty"
                clearable
              />
            </template>
          </el-table-column>
          <el-table-column label="优先级" prop="priorityGrade" align="center">
            <template slot-scope="props">
              <BjInputNumber v-model="props.row.priorityGrade" :disabled="isView" />
            </template>
          </el-table-column>
        </template>
      </BjPage>
      <template #footer>
        <el-button @click="hide">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </template>
    </BjDialog>
  </div>
</template>

<script>
import pageMiXin from '@/utils/page-mixin.js';
import { stockApi } from '@/api';
export default {
  name: 'DialogStockoutOrderAllot',
  mixins: [pageMiXin],
  props: {
    title: {
      type: [String, Number],
      default: null,
    },
    isView: {
      type: Boolean,
      default: false,
    },
    isEdit: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      visible: false,
      colSpan: 8,
      selection: false,
      queryApi: stockApi.getDistributePlanShopStockoutList,
      queryParams: {
        queryCode: null,
      },
      form: {},
      rules: {},
      planNum: 0,
      oldList: [],
    };
  },
  methods: {
    // beforeQuerySearch(conditionObj) {
    //   return new Promise(resolve => {
    //     resolve(conditionObj);
    //   });
    // },
    show(info) {
      // 组件外（虚仓移仓、分仓计划）传入接口名称
      this.queryApi = stockApi[info.api];
      // 获取回写上次编辑的内容
      console.log(info.list);
      this.oldList = [].concat(info.list || []);
      delete info.list;
      // 获取请求接口需要的数据
      this.queryParams = Object.assign({}, this.queryParams, info.queryParams);
      // 区分接口类型（add edit detail）
      this.queryParams.handleFlag = this.isEdit ? 'edit' : this.isView ? 'detail' : 'add';
      // 可分配库存
      this.planNum = info.planNum;
      this.reFresh();
      this.visible = true;
    },
    hide() {
      this.visible = false;
    },
    resetData(data) {
      return data.map(e => {
        // 回写上次编辑的内容
        const FIND = this.oldList.find(item => item.stockoutId == e.stockoutId);
        console.log(FIND);
        return {
          ...e,
          replenishQty: FIND ? FIND.replenishQty : e.replenishQty,
          priorityGrade: FIND ? FIND.priorityGrade : e.priorityGrade,
        };
      });
    },
    confirm() {
      let num = 0;
      const DATA = this.$refs.BjPage.tableData.filter(item => item.replenishQty);
      DATA.forEach(e => {
        num += Number(e.replenishQty);
      });
      if (num > this.planNum) {
        this.$message.warning('补货数量之和不可大于可分配库存');
        return;
      }
      // 获取输入补货数量没输入优先级的内部订单号
      const TEXT = DATA.filter(item => typeof item.priorityGrade !== 'number')
        .map(item => {
          return item.orderCode;
        })
        .join();
      if (TEXT) {
        this.$message.warning(`请输入订单： ${TEXT} 的优先级`);
        return;
      }
      this.$emit('confirm', this.$refs.BjPage.tableData);
      this.hide();
    },
  },
};
</script>

<style></style>
