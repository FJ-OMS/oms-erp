<!--
 * @Author: your name
 * @Date: 2021-10-04 17:05:18
 * @LastEditTime: 2021-10-04 17:20:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\strategy\autoReplenish\order\components\DialogForm.vue
-->
<template>
  <el-dialog :close-on-click-modal="false" :close-on-press-escape="false" title="设置优先级" :visible.sync="dialogVisible" width="500px">
    <el-form ref="form" :model="form" :rules="rules">
      <p class="title">缺货商品到货，根据优先级补货</p>
      <el-form-item label="补货优先级">
        <el-radio-group v-model="form.radio">
          <el-radio :label="1">订单商品缺货优先</el-radio>
          <el-radio :label="2">网店商品缺货优先</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="concal">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      form: {
        radio: null,
      },
      rules: {},
      dialogVisible: false,
    };
  },
  methods: {
    show() {
      this.dialogVisible = true;
    },
    hide() {
      this.dialogVisible = false;
    },
    concal() {
      this.hide();
    },
    confirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('confirm', this.form.radio);
        } else {
          return false;
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.title {
    color: #E6A23C;
}
</style>
