<!--
 * @Author: your name
 * @Date: 2021-09-18 11:52:03
 * @LastEditTime: 2021-09-18 13:44:45
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\order\abnormal\components\setWarnDialog.vue
-->
<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="title"
    :visible.sync="dialogVisible"
    width="500px"
  >
    <el-form ref="form" :model="form" :rules="rules">
      <div>
        <el-form-item label="审核超时">
          <el-select v-model="form.auditTime" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="onConfirm">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: '业务预警设置',
    },
  },
  data() {
    return {
      form: {
        auditTime: null,
        stockoutTime: null,
        deliveryTime: null,
      },
      rules: {},
      dialogVisible: false,
      options: [
        { value: '时', label: '时' },
        { value: '分', label: '分' },
        { value: '秒', label: '秒' },
        { value: '天', label: '天' },
      ],
    };
  },
  methods: {
    show(data) {
      this.form = data;
      this.dialogVisible = true;
    },
    hide() {
      this.dialogVisible = false;
    },
    onConfirm() {
      this.$refs.form.validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.dialogVisible = false;
        this.$emit('confirm', this.form);
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
