<!--
 * @Author: your name
 * @Date: 2021-09-18 10:54:21
 * @LastEditTime: 2021-09-18 11:35:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\components\BjDialogRefresh\index.vue
-->
<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="title"
    :visible.sync="dialogVisible"
    width="500px"
  >
    <el-form ref="timeForm" :model="form" :rules="rules">
      <el-form-item label="刷新间隔时间" prop="intervalTime">
        <el-input
          v-model="form.intervalTime"
          type="number"
          placeholder="请输入"
          style="width:200px;"
        >
          <template slot="append">秒</template>
        </el-input>
      </el-form-item>
      <el-form-item label="启用" prop="enabledFlag">
        <el-switch v-model="form.enabledFlag" />
      </el-form-item>
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
      default: '自动刷新设置',
    },
  },
  data() {
    var checkTime = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入时间'));
      }
      if (value < 1) {
        callback(new Error('请输入不小于1秒的时间'));
      } else {
        callback();
      }
    };
    return {
      form: {
        intervalTime: 10,
        enabledFlag: false,
      },
      rules: {
        intervalTime: [{ required: true, validator: checkTime, trigger: 'blur' }],
      },
      dialogVisible: false,
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
      this.$refs.timeForm.validate(valid => {
        if (valid) {
          this.dialogVisible = false;
          this.$emit('confirm', this.form);
        } else {
          return false;
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
