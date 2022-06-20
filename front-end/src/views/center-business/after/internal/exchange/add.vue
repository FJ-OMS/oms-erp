<!--
 * @Author: your name
 * @Date: 2021-09-30 10:21:32
 * @LastEditTime: 2022-04-27 15:37:29
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\after\internal\refund\add.vue
-->
<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" inline label-width="130px">
      <BjDivider>选择关联销售单</BjDivider>
      <InternalOrderList :data="form.orderList" />
      <BjDivider>基础信息</BjDivider>
      <el-form-item label="当前选中的订单">
        <el-input
          v-model="form.orderId"
          :maxlength="MAX_LENGTH.DEFAULT"
          disabled
          placeholder="请选择"
        />
      </el-form-item>
      <el-form-item label="三方换货单号" prop="value2">
        <BjInputCode v-model="form.value2" />
      </el-form-item>
      <el-form-item label="三方换货时间" prop="payTime">
        <el-date-picker v-model="form.payTime" type="datetime" placeholder="请选择" clearable />
      </el-form-item>
      <el-form-item label="退货类型" prop="value3">
        <el-select v-model="form.value3" placeholder="请选择" clearable>
          <el-option label="换货" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="退货原因" prop="value4">
        <el-select v-model="form.value4" placeholder="请选择" clearable>
          <el-option label="7天无理由退款" value="0" />
        </el-select>
      </el-form-item>
      <div>
        <el-form-item label="换货备注">
          <el-input
            v-model="form.value6"
            type="textarea"
            placeholder="请输入"
            :maxlength="MAX_LENGTH.REMARK"
            show-word-limit
            resize="none"
            size="medium"
          />
        </el-form-item>
      </div>
      <div>
        <el-form-item label="客服备注" class="mt20">
          <BjTextarea v-model="form.value7" placeholder="客服备注" />
        </el-form-item>
      </div>
      <BjDivider>退货商品信息</BjDivider>
      <el-button type="primary" class="mb16" @click="$refs.dialogList.show()"
        >添加退货商品</el-button
      >
      <el-table :data="form.shopList">
        <el-table-column label="商品名称" prop="value" align="center" />
        <el-table-column label="商品编码" prop="value1" align="center" />
        <el-table-column label="商品SKU编码" prop="value2" align="center" />
        <el-table-column label="是否赠品" prop="value3" align="center">
          <template slot-scope="scope" align="center">
            <BjSelectLov v-model="scope.row.value3" :option-list="STATUS_YES_NUM" />
          </template>
        </el-table-column>
        <el-table-column label="可退数量" prop="value4" align="center" />
        <el-table-column label="退货数量" prop="value5" align="center">
          <template slot-scope="scope">
            <BjInputNumber v-model="scope.row.value5" />
          </template>
        </el-table-column>
        <el-table-column label="三方平台单价" prop="value6" align="center" />
        <el-table-column label="内部售价" prop="value7" align="center" />
        <el-table-column label="平台均摊单价" prop="value8" align="center" />
        <el-table-column label="内部均摊单价" prop="value9" align="center" />
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="onDelete(form.shopList, scope.$index)">删 除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <BjDivider>退货物流信息</BjDivider>
      <el-form-item label="退货物流公司" prop="companyId">
        <BjSelectLogisticCompany v-model.trim="form.companyId" clearable />
      </el-form-item>
      <el-form-item label="物流单号" prop="companyNo">
        <BjInputCode v-model="form.companyNo" />
      </el-form-item>
      <el-form-item label="收货仓库" prop="supportWarehouseId">
        <BjSelectWarehouse v-model="form.supportWarehouseId" :type-code="['logic']" />
      </el-form-item>
      <BjDivider>换货商品信息</BjDivider>
      <el-button type="primary" class="mb16" @click="$refs.dialogList.show()"
        >添加换货商品</el-button
      >
      <el-table :data="form.shopList1">
        <el-table-column label="商品名称" prop="value" align="center" />
        <el-table-column label="商品编码" prop="value1" align="center" />
        <el-table-column label="商品SKU编码" prop="value2" align="center" />
        <el-table-column label="是否赠品" prop="value3" align="center">
          <template slot-scope="scope" align="center">
            <BjSelectLov v-model="scope.row.value3" :option-list="STATUS_YES_NUM" />
          </template>
        </el-table-column>
        <el-table-column label="库存" prop="value4" align="center" />
        <el-table-column label="换货数量" prop="value5" align="center">
          <template slot-scope="scope">
            <BjInputNumber v-model="scope.row.value5" />
          </template>
        </el-table-column>
        <el-table-column label="三方平台单价" prop="value6" align="center" />
        <el-table-column label="内部售价" prop="value7" align="center" />
        <el-table-column label="平台均摊单价" prop="value8" align="center" />
        <el-table-column label="内部均摊单价" prop="value9" align="center" />
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="onDelete(form.shopList1, scope.$index)">删 除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <BjDivider>换货物流信息</BjDivider>
      <el-form-item label="退货物流公司" prop="companyId">
        <BjSelectLogisticCompany v-model.trim="form.companyId1" clearable />
      </el-form-item>
      <el-form-item label="物流单号" prop="companyNo">
        <BjInputCode v-model="form.companyNo1" />
      </el-form-item>
      <el-form-item label="收货仓库" prop="supportWarehouseId">
        <BjSelectWarehouse v-model="form.supportWarehouseId1" :type-code="['logic']" />
      </el-form-item>
      <div class="mt20">
        <el-form-item label="图片附件" style="display: block; height: 100%">
          <BjUpload list-type="picture-card" @success="onSuccess" />
        </el-form-item>
      </div>
      <div class="text-center mt20 mb20">
        <el-button style="width: 150px" @click="cancel">取 消</el-button>
        <el-button type="primary" style="width: 150px" @click="onConfirm">确 定</el-button>
      </div>
    </el-form>
    <!-- 选择商品 -->
    <BjDialogList
      ref="dialogList"
      :config="$BjDialogListConfig.skuDefault()"
      multiple
      @confirm="onConfirmDialogList"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { STATUS_YES_NUM } from '@/utils/constants';
import InternalOrderList from '@/views/center-business/after/components/InternalOrderList';
export default {
  name: 'BusinessAfterInternalReturnAdd',
  components: {
    InternalOrderList,
  },
  mixins: [viewMiXin],
  data() {
    return {
      STATUS_YES_NUM,
      form: {
        orderId: null,
        shopList: [
          {
            value: '双把淋浴花洒',
            value1: 'A2422-127X-2',
            value2: '6957210222095',
            value3: 0,
            value4: 1,
            value5: 1,
            value6: '89.90',
            value7: '40.00',
            value8: '0.00',
            value9: '0.00',
          },
        ],
        shopList1: [
          {
            value: '双把淋浴花洒',
            value1: 'A2422-127X-2',
            value2: '6957210222095',
            value3: 0,
            value4: 1,
            value5: 1,
            value6: '89.90',
            value7: '40.00',
            value8: '49.90',
            value9: '49.90',
          },
        ],
      },
      rules: {
        value2: [{ required: true, message: '请输入三方换货单号', trigger: 'blur' }],
        payTime: [{ required: true, message: '请选择三方退货时间', trigger: 'change' }],
        value3: [{ required: true, message: '请选择退货类型', trigger: 'change' }],
        value4: [{ required: true, message: '请选择退货原因', trigger: 'change' }],
      },
    };
  },
  methods: {
    /** 选中订单 */
    handleSeleceOrder(data) {
      this.form.orderId = data.id;
    },
    onDelete(data, index) {
      this.$confirm('是否确定删除该数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        data.splice(index, 1);
        this.msgSuccess('删除成功');
      });
    },
    onConfirmDialogList(data) {},
    /** 图片上传成功 */
    onSuccess(res, file) {
      console.log(res);
      console.log(file);
    },
    /** 确定 */
    onConfirm() {
      this.$refs.form.validate(valid => {
        if (!valid) return this.$scrollToFormError();

        this.msgSuccess('提交成功');
      });
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-textarea__inner {
  width: 500px;
}
</style>
