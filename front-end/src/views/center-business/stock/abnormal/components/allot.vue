<!--
 * @Author: your name
 * @Date: 2021-10-06 14:30:34
 * @LastEditTime: 2021-12-22 17:50:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\stock\abnormal\components\allot.vue
-->
<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="title"
    :visible.sync="dialogVisible"
    width="80%"
    :before-close="handleClose"
  >
    <BjDivider>基础信息</BjDivider>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" inline>
      <template v-if="!type">
        <el-form-item label="调拨单号" prop="value1">
          <BjInputCode v-model.trim="form.value1" />
        </el-form-item>
        <el-form-item label="移出逻辑仓" prop="value2">
          <BjSelectWarehouse
            v-model="form.warehouseId"
            :type-code="['logic']"
            :default-code="form.warehouseCode"
            @change="onChangeWarehouseId"
          />
        </el-form-item>
        <el-form-item label="移入逻辑仓" prop="">
          <BjSelectWarehouse
            v-model="form.warehouseId"
            :disabled="true"
            :type-code="['logic']"
            :default-code="form.warehouseCode"
            @change="onChangeWarehouseId"
          />
        </el-form-item>
      </template>
      <template v-else>
        <el-form-item label="移仓单号" prop="value1">
          <BjInputCode v-model.trim="form.value1" />
        </el-form-item>
        <el-form-item label="选择实仓" prop="value2">
          <BjSelectWarehouse v-model="form.warehouseId" :type-code="['real']" @change="getReal" />
        </el-form-item>
        <el-form-item label="移出虚仓：" prop="supportId">
          <BjSelectLov v-model="form.supportId" :option-list="logicList" @change="getSupportId" />
          <span>{{ form.supportName }}</span>
        </el-form-item>
        <el-form-item label="移入虚仓：" prop="receiveId">
          <BjSelectLov v-model="form.receiveId" :option-list="logicListEnter" />
          <span>{{ form.receiveName }}</span>
        </el-form-item>
      </template>
      <div>
        <el-form-item label="备 注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入"
            :maxlength="MAX_LENGTH.REMARK"
            show-word-limit
            style="width: 500px"
          />
        </el-form-item>
      </div>
    </el-form>
    <BjDivider>商品明细</BjDivider>
    <el-table :data="form.shopList" border>
      <el-table-column label="商品编码" prop="value1" align="center" />
      <el-table-column label="商品名称" prop="value2" align="center" />
      <el-table-column label="商品分类" prop="value3" align="center" />
      <el-table-column label="SKU编码" prop="value4" align="center" />
      <el-table-column label="异常数量" prop="value5" align="center" />
      <el-table-column label="可移仓数量" prop="value6" align="center" />
      <el-table-column label="移仓数量" prop="value7" align="center">
        <template slot-scope="scope">
          <el-input-number
            v-model="scope.row.value7"
            :maxlength="MAX_LENGTH.DEFAULT"
            :min="0"
            :max="scope.row.value6"
            :precision="0"
            :controls="false"
            class="full-width"
          />
        </template>
      </el-table-column>
    </el-table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="onConfirm">保 存</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { stockApi } from '@/api';
const getDefaultForm = () => {
  return {
    remark: null,
    shopList: [
      {
        value1: '6935898044253',
        value2: '智能座便器',
        value3: '',
        value4: 'ZS610-400',
        value5: '',
        value6: 0,
        value7: '',
      },
    ],
  };
};
export default {
  props: {},
  data() {
    return {
      dialogVisible: false,
      warehouseId: null,
      warehouseCode: null,
      form: getDefaultForm(),
      rules: {},
      type: 0,
      title: '',

      logicList: [], // 根据实仓筛选出的虚仓
      logicListEnter: [], // 移入虚仓
    };
  },
  methods: {
    show(type, title) {
      this.type = type;
      this.title = title;
      this.dialogVisible = true;
      this.form = getDefaultForm();
    },
    hide() {
      this.dialogVisible = false;
    },
    onChangeWarehouseId(v, item) {
      this.form.warehouseCode = item.code;
    },
    /** 选择实仓 */
    getReal(v, data) {
      this.form['realWarehouseId'] = data.id;
      this.form['realWarehouseName'] = data.name;
      /** type:关联类型 0比例 1数量 */
      if (data.id) {
        stockApi.getWarehouserBySupport({ supportId: data.id, type: 1 }).then(res => {
          this.logicList = (res || []).map(e => {
            return { label: e.name, value: e.id };
          });
        });
      }
    },
    /** 选择移出虚仓时调用接口获取移入虚仓数据 */
    getSupportId(val) {
      this.form.receiveId = null;
      /** type:关联类型 0比例 1数量 */
      stockApi.getWarehouserBySupport({ supportId: val, type: 1 }).then(res => {
        this.logicListEnter = (res || []).map(e => {
          let disabled = false;
          if (this.form.supportId === e.id) disabled = true;
          return { label: e.name, value: e.id, disabled };
        });
      });
    },
    onConfirm() {},
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
