<!--
 * @Author: your name
 * @Date: 2021-08-19 17:54:44
 * @LastEditTime: 2022-01-17 16:22:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-setting\stock-structure\components\UpdataForm.vue
-->
<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    width="400px"
    label-width="130px"
    :append-to-body="false"
    @handleConfirm="handleConfirm"
  >
    <BjDivider>选择连接仓库</BjDivider>
    <!-- <el-form-item v-if="typeCode === 'interline-virtual'" label="渠道仓">
      <el-select v-model="form.channelIds" multiple clearable @remove-tag="onRemove">
        <template v-for="item in channelDataList">
          <el-option :key="item.id" :label="item.name" :value="item.id" />
        </template>
      </el-select>
    </el-form-item> -->

    <el-form-item
      v-if="typeCode === 'channel' || typeCode === 'interline-virtual'"
      label="数量虚仓"
    >
      <BjSelectLov
        v-model="form.qtyVirtualIds"
        :option-list="qtyDataList.filter(e => e.typeCode == 'qty-virtual')"
        item-label="name"
        item-value="id"
        multiple
      />
    </el-form-item>

    <el-form-item v-if="typeCode === 'channel'" label="比例虚仓">
      <BjSelectLov
        v-model="form.ratioVirtualIds"
        :option-list="ratioDataList.filter(e => e.typeCode == 'ratio-virtual')"
        item-label="name"
        item-value="id"
        multiple
      />
    </el-form-item>

    <el-form-item
      v-if="typeCode === 'qty-virtual' || typeCode === 'ratio-virtual'"
      label="实仓"
      prop="ids"
    >
      <el-select
        v-model="form.ids"
        multiple
        :multiple-limit="1"
        clearable
        @change="handleChangeLogic"
      >
        <template v-for="item in list">
          <el-option :key="item.id" :label="item.name" :value="item.id" />
        </template>
      </el-select>
    </el-form-item>

    <el-form-item v-if="typeCode === 'interline-virtual'" label="实仓">
      <el-select v-model="form.realId" clearable>
        <el-option
          v-for="item in realDataList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-form-item>

    <el-form-item v-if="typeCode === 'real'" label="逻辑仓">
      <el-select v-model="form.ids" multiple clearable>
        <template v-for="item in list">
          <el-option :key="item.id" :label="item.name" :value="item.id" />
        </template>
      </el-select>
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import { stockApi } from '@/api';
const getDefaultForm = () => {
  return {
    // /** 渠道仓值集 */
    channelIds: [],
    // qtyVirtualIds: [],
    // ratioVirtualIds: [],
    // /** 仓库结构id */
    // structureId: null,
    // /** 仓库id */
    // warehouseId: null,
    realId: null,
  };
};
export default {
  props: {},
  data() {
    return {
      typeCode: null,
      changeTypeCode: null,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        ids: [],
      },
      /** 渠道仓/店仓集 */
      channelDataList: [],
      /** 数量虚仓集 */
      qtyDataList: [],
      /** 比例虚仓集 */
      ratioDataList: [],
      /** 实仓集 */
      realDataList: [],
      list: [],
    };
  },
  methods: {
    show(data) {
      this.form = getDefaultForm();
      // console.log(data.typeCode);
      this.typeCode = data.typeCode;
      this.form.structureId = data.structureId;
      this.form.warehouseId = data.id;
      this.$refs.BjDrawerForm.show();
      // 渠道仓 => 获取数量和比例虚仓连接数据
      if (this.typeCode === 'channel') {
        this.getChannelRelation(data.structureId, data.id);
      }
      /** 数量虚仓、比例虚仓、实仓关系表 */
      if (
        this.typeCode === 'qty-virtual' ||
        this.typeCode === 'ratio-virtual' ||
        this.typeCode === 'real'
      ) {
        this.getVirtualRelation(data.structureId, this.typeCode, data.id);
      }
      // /** 中转虚仓关系表 */
      if (this.typeCode === 'interline-virtual') {
        this.getInterlineRelation(data.structureId, data.id);
      }
      if (this.typeCode === 'logic') {
        this.rules.ids.push({ required: true, message: '请选择实仓', trigger: 'change' });
      }
    },
    handleChangeLogic(val) {
      this.changeTypeCode = 'real';
    },
    handleClose() {
      this.$refs.BjDrawerForm.hide();
    },
    handleConfirm() {
      /** 逻辑仓关系添加 */
      if (this.typeCode === 'channel') {
        this.addChannel(this.form);
      } else if (this.typeCode === 'interline-virtual') {
        this.addInterline(this.form);
      } else {
        this.form.receiveTypeCode = this.changeTypeCode;
        this.addRelation(this.form);
      }
    },
    /**
     * @description: 渠道仓关系详情
     * @param {*} structureId 仓库结构id
     * @param {*} warehouseId 渠道仓id
     * @return {*}
     */
    getChannelRelation(structureId, warehouseId) {
      stockApi
        .getWarehouseStructuresChannelDetail({
          structureId,
          warehouseId,
        })
        .then(res => {
          this.qtyDataList = res.qtyDataList;
          this.ratioDataList = res.ratioDataList;
          this.qtyDataList.forEach(item => {
            if (item.check) this.form.qtyVirtualIds.push(item.id);
          });
          this.ratioDataList.forEach(item => {
            if (item.check) this.form.ratioVirtualIds.push(item.id);
          });
        });
    },
    /**
     * @description: 接收仓关系详情
     * @param {*} structureId 仓库结构id
     * @param {*} typeCode 点击仓库类型
     * @param {*} warehouseId id
     * @return {*}
     */
    getVirtualRelation(structureId, typeCode, warehouseId) {
      stockApi
        .getWarehouseStructureslReceiveDetail({
          structureId,
          typeCode,
          warehouseId,
        })
        .then(res => {
          this.list = res.dataList;
          this.list.forEach(item => {
            if (item.check) this.form.ids.push(item.id);
          });
        });
    },

    /**
     * @description: 中转虚仓关系详情
     * @param {*} structureId
     * @param {*} warehouseId
     * @return {*}
     */
    getInterlineRelation(structureId, warehouseId) {
      stockApi.getWarehouseStructureslInterlineDetail({ structureId, warehouseId }).then(res => {
        // 渠道仓值集
        this.channelDataList = res.channelDataList;
        this.qtyDataList = res.qtyDataList;
        this.realDataList = res.realDataList;
        this.qtyDataList.forEach(item => {
          if (item.check) this.form.qtyVirtualIds.push(item.id);
        });
        this.channelDataList.forEach(item => {
          if (item.check) this.form.channelIds.push(item.id);
        });
        // 实仓不是数组
        this.realDataList.forEach(item => {
          if (item.check) this.form.realId = item.id;
        });
      });
    },
    /** 渠道仓关系添加 */
    addChannel(data) {
      stockApi.addWarehouseStructuresChannel(data).then(res => {
        if (!res.failed) {
          this.msgSuccess('添加成功');
          this.$emit('handleConfirm');
          this.$refs.BjDrawerForm.hide();
        }
      });
    },
    /** 数量虚仓、比例虚仓、实仓关系添加 */
    addRelation(data) {
      // 接收仓为虚仓时，供货仓为实仓，接收仓为实仓时，供货仓为逻辑仓
      data.supportTypeCode =
        this.typeCode === 'qty-virtual' || this.typeCode === 'ratio-virtual' ? 'real' : 'logic';
      // 设置接收仓
      data.receiveTypeCode = this.typeCode;
      stockApi.addWarehouseStructuresRelation(data).then(res => {
        if (!res.failed) {
          this.msgSuccess('添加成功');
          this.$emit('handleConfirm');
          this.$refs.BjDrawerForm.hide();
        }
      });
    },
    /** 中转虚仓添加 */
    addInterline(data) {
      stockApi.addWarehouseStructuresInterline(data).then(res => {
        if (!res.failed) {
          this.msgSuccess('添加成功');
          this.$emit('handleConfirm');
          this.$refs.BjDrawerForm.hide();
        }
      });
    },
    /** 中转仓取消渠道仓关系校验 */
    onRemove(val) {
      /**
       * @description: 中转仓取消渠道仓关系校验接口
       * @param {*} receiveWarehouseId 渠道仓id
       * @param {*} supportWarehouseId 中转仓id
       */
      stockApi
        .checkoutWarehouseCancelChannel({
          receiveWarehouseId: val,
          supportWarehouseId: this.form.warehouseId,
        })
        .then(res => {
          if (res.failed) this.form.channelIds.push(val);
        });
    },
    reFresh() {
      this.$emit('reFresh');
    },
  },
};
</script>
<style lang="scss" scoped>
.flex-item {
  width: 200px;
  justify-content: space-between;
  align-items: flex-start;
}
</style>
