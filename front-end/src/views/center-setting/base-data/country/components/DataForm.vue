<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-col :span="22">
      <el-form-item label="国家编码" prop="countryCode">
        <el-input
          v-model.trim="form.countryCode"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isEdit"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label=" 国家名称" prop="countryName">
        <el-input
          v-model.trim="form.countryName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="国家简称" prop="abbreviation">
        <el-input
          v-model.trim="form.abbreviation"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>

    <el-col :span="22">
      <el-form-item label="启用" prop="enabledFlag">
        <el-switch v-model="form.enabledFlag" :active-value="1" :inactive-value="0" />
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { baseDataApi } from '@/api';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    abbreviation: null,
    countryCode: null,
    countryId: null,
    countryName: null,
    enabledFlag: 0,
    quickIndex: null,
    tenantId: 'default',
  };
};

export default {
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        countryCode: [{ required: true, message: '请输入国家编码', trigger: 'blur' }],
        countryName: [{ required: true, message: '请输入国家名称', trigger: 'blur' }],
        enabledFlag: [{ required: true, message: '', trigger: 'blur' }],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    afterUpload(res) {
      this.form.logo = res;
    },
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑国家定义' : '新建国家定义');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }

      if (this.isEdit) {
        this.updateApi(API_DATA);
      } else {
        this.addApi(API_DATA);
      }
    },
    reFresh() {
      this.$emit('reFresh');
    },
    addApi(API_DATA) {
      baseDataApi.addCountry(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      baseDataApi.updateCountry(API_DATA).then(res => {
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
