<!--
 * @Author: your name
 * @Date: 2021-10-04 15:57:54
 * @LastEditTime: 2021-10-04 16:19:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\strategy\autoReplenish\onlineStore\components\DataForm.vue
-->
<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-col :span="span">
      <el-form-item label="网店编码" prop="code">
        <el-input v-model="form.code" :maxlength="MAX_LENGTH.DEFAULT" disabled />
      </el-form-item>
      <el-form-item label="网店名称" prop="storeName">
        <el-input v-model="form.storeName" :maxlength="MAX_LENGTH.DEFAULT" disabled />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number
          v-model="form.sort"
          :maxlength="MAX_LENGTH.DEFAULT"
          :min="1"
          :precision="0"
          :controls="false"
          :max="10"
        />
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { settingApi } from '@/api';
const getDefaultForm = () => {
  return {};
};

export default {
  data() {
    return {
      span: 22,
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        code: [{ required: true, message: '请输入网店编码', trigger: 'blur' }],
        storeName: [{ required: true, message: '请输入网店名称', trigger: 'blur' }],
        sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    show(info) {
      console.log(info);
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑' : '新建');
      this.form = Object.assign(getDefaultForm(), info || {});
      console.log(this.form);
    },
    handleConfirm() {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      this.saveApi(API_DATA);
    },
    reFresh() {
      this.$emit('reFresh');
    },
    saveApi(API_DATA) {
      settingApi.updateStrategyOrderAmounts(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style scoped>
.el-tag {
  margin: 6px;
}
</style>
