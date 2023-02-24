<!--
 * @Author: your name
 * @Date: 2021-11-03 15:13:35
 * @LastEditTime: 2022-04-08 09:54:59
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\order\internal\components\parcelDetails.vue
-->
<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    title="包裹明细"
    :visible.sync="dialogVisible"
    width="960"
    :before-close="handleClose"
  >
    <el-form inline label-width="120px">
      <el-form-item label="物流公司">{{ info.logisticCompany }}</el-form-item>
      <el-form-item label="物流单号">{{ info.shippingNo }}</el-form-item>
      <el-form-item label="发货通知单">{{ info.odoCode }}</el-form-item>
    </el-form>
    <el-table ref="multipleTable" :data="data" border stripe :max-height="maxHeight">
      <el-table-column label="序号" type="index" align="center" />
      <el-table-column label="商品名称" prop="spuName" align="center"></el-table-column>
      <el-table-column label="商品编码" prop="spuCode" align="center"></el-table-column>
      <el-table-column label="SKU编码" prop="skuCode" align="center"></el-table-column>
      <el-table-column label="数量" prop="realNum" width="80" align="center"></el-table-column>
    </el-table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="onSubmit">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { stockApi } from '@/api';
import { getOffsetTop } from '@/utils/index';
export default {
  data() {
    return {
      dialogVisible: false,
      /** 省份列表 */
      info: {},
      data: [],
      maxHeight: '480px',
    };
  },
  watch: {
    dialogVisible() {
      if (this.dialogVisible) {
        this.$nextTick(() => {
          this.maxHeight =
            document.documentElement.clientHeight -
            getOffsetTop(`.el-table__header-wrapper`) -
            100 +
            'px';
        });
      }
    },
  },
  created() {},
  methods: {
    show(info) {
      this.info = info || {};
      stockApi
        .getResultDetailPage({ resultId: this.info.id })
        .then(res => {
          this.data = res.content;
        })
        .catch(err => {
          console.log(err);
        });
      this.dialogVisible = true;
    },
    hide() {
      this.dialogVisible = false;
    },
    handleClose(done) {
      this.hide();
    },
    /** 确定 */
    onSubmit() {
      this.hide();
    },
  },
};
</script>
<style lang="scss" scoped></style>
