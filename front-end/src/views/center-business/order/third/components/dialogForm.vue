<!--
 * @Author: your name
 * @Date: 2021-10-04 15:14:04
 * @LastEditTime: 2021-10-05 14:07:45
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\order\third\components\dialogForm.vue
-->
<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    title="下载订单"
    :visible.sync="dialogVisible"
    width="520px"
  >
    <el-form ref="form" :model="form" :rules="rules" :label-width="type ? '100px' : '100px'">
      <el-form-item>
        <p>{{ type === 0 ? '按订单号下载（支持多个订单，逗号隔开）' : '按时间下载' }}</p>
      </el-form-item>
      <el-form-item label="店铺" prop="storeId">
        <BjSelectStore v-model="form.storeId" />
      </el-form-item>
      <el-form-item v-show="!type" label="订单号" prop="orderNo" :required="!type">
        <el-input v-model="form.orderNo" :maxlength="MAX_LENGTH.DEFAULT" type="textarea" />
      </el-form-item>
      <el-form-item v-show="type" label="时间类型" prop="timeType" :required="type">
        <el-select v-model="form.timeType" class="full-width">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item v-show="type" label="时间" prop="endTime" :required="type">
        <el-date-picker
          v-model="time"
          class="full-width"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetimerange"
          :picker-options="$pickerOptions()"
          align="right"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']"
        />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="onConfirm">保 存</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  name: 'BusinessOrderThirdDialog',
  data() {
    return {
      form: {},
      time: null,
      rules: {
        storeId: [{ required: true, message: '请选择店铺', trigger: 'change' }],
        orderNo: [{ required: true, message: '请输入订单号', trigger: 'blur' }],
        timeType: [{ required: true, message: '请选择时间类型', trigger: 'change' }],
        startTime: [{ required: true, message: '请选择时间', trigger: 'change' }],
        endTime: [{ required: true, message: '请选择时间', trigger: 'change' }],
      },
      dialogVisible: false,
      type: 0,
      options: [
        { label: '下单时间', value: 2 },
        { label: '支付时间', value: 3 },
        { label: '通知配货时间', value: 4 },
        { label: '最后修改时间', value: 5 },
      ],
    };
  },
  watch: {
    time(v) {
      this.form.startTime = (v || [])[0];
      this.form.endTime = (v || [])[1];
    },
  },
  methods: {
    show(type) {
      this.type = type;
      this.dialogVisible = true;
    },
    hide() {
      this.dialogVisible = false;
    },
    onConfirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('confirm', this.form);
        } else {
          return false;
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.el-form-item {
  width: 450px;
}
</style>
