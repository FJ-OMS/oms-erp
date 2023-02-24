<!--
 * @Author: your name
 * @Date: 2021-10-18 18:10:38
 * @LastEditTime: 2021-10-18 19:02:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\source\parcel\components\dialogForm.vue
-->
<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    title="排除条件设置"
    :visible.sync="dialogVisible"
    width="600px"
    :before-close="handleClose"
  >
    <el-form>
      <el-form-item label="订单一个商品购买数量">
        <el-col :span="6">
          <el-form-item prop="date1">
            <el-select v-model="form.value" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="6">
          <el-form-item prop="date2">
            <el-input-number
              v-model="form.num"
              :maxlength="MAX_LENGTH.DEFAULT"
              :min="0"
              :precision="0"
              :controls="false"
            />
          </el-form-item>
        </el-col>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      dialogVisible: false,
      form: {},
      rules: {},
      options: [
        {
          value: '1',
          label: '大于',
        },
        {
          value: '2',
          label: '小于',
        },
        {
          value: '3',
          label: '等于',
        },
      ],
    };
  },
  methods: {
    show() {
      this.dialogVisible = true;
    },
    hide() {
      this.dialogVisible = false;
    },
    handleClose() {
      this.dialogVisible = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.line {
  text-align: center;
}
</style>
