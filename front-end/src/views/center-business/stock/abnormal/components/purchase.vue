<!--
 * @Author: your name
 * @Date: 2021-10-06 14:30:14
 * @LastEditTime: 2021-10-06 17:18:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\stock\abnormal\components\purchase.vue
-->
<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    title="订单缺货-采购"
    :visible.sync="dialogVisible"
    width="80%"
    :before-close="handleClose"
  >
    <BjDivider>基础信息</BjDivider>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" inline>
      <el-form-item label="逻辑仓名称" prop="warehouseId">
        <BjSelectWarehouse
          v-model="form.warehouseId"
          :type-code="['logic']"
          :default-code="form.warehouseCode"
          @change="onChangeWarehouseId"
        />
      </el-form-item>
      <el-form-item label="逻辑仓编码" prop="warehouseCode">
        <el-input
          v-model.trim="form.warehouseCode"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        >
          <el-button slot="append">搜索</el-button>
        </el-input>
      </el-form-item>
    </el-form>
    <BjDivider>商品明细</BjDivider>
    <el-table :data="form.shopList" border>
      <el-table-column label="商品编码" prop="value1" align="center" />
      <el-table-column label="商品名称" prop="value2" align="center" />
      <el-table-column label="商品分类" prop="value3" align="center" />
      <el-table-column label="SKU编码" prop="value4" align="center" />
      <el-table-column v-if="status" label="异常类型" prop="value" align="center" />
      <el-table-column label="仓库编码" prop="value5" align="center" />
      <el-table-column label="仓库名称" prop="value6" align="center" />
      <el-table-column label="异常数量" prop="value7" align="center" />
      <el-table-column label="采购入库数量" prop="value8" align="center">
        <template slot-scope="scope">
          <el-input-number
            v-model="scope.row.value8"
            :maxlength="MAX_LENGTH.DEFAULT"
            :min="0"
            :max="scope.row.value7"
            :precision="0"
            :controls="false"
            class="full-width"
          />
        </template>
      </el-table-column>
    </el-table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">保 存</el-button>
    </span>
  </el-dialog>
</template>
<script>
const getDefaultForm = () => {
  return {
    warehouseId: null,
    warehouseCode: null,
    shopList: [
      {
        value: '商品库存不足',
        value1: '6935898044253',
        value2: '智能座便器',
        value3: '',
        value4: 'ZS610-400',
        value5: 'QDU2021041100000003',
        value6: '小米有品_九牧官方旗舰店_2000',
        value7: 50,
        value8: null,
      },
    ],
  };
};
export default {
  props: {
    status: {
      type: [String, Number],
      default: 0,
    },
  },
  data() {
    return {
      dialogVisible: false,
      form: getDefaultForm(),
      rules: {},
    };
  },
  methods: {
    show() {
      this.dialogVisible = true;
    },
    hide() {
      this.dialogVisible = false;
    },
    onChangeWarehouseId(v, item) {
      this.form.warehouseCode = item.code;
    },
    handleClose(done) {
      this.dialogVisible = false;
      //   this.$confirm('确认关闭？')
      //     .then(_ => {
      //       done();
      //     })
      //     .catch(_ => {});
    },
  },
};
</script>
