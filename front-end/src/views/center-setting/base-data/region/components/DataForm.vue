<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-col v-if="form.parentCode" :span="22">
      <el-form-item label="上级地区" prop="parentName">
        <el-input
          v-model.trim="form.parentName"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isEdit"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="地区编码" prop="areaCode">
        <el-input
          v-model.trim="form.areaCode"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isEdit"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label=" 地区名称" prop="name">
        <el-input
          v-model.trim="form.name"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="简称" prop="shortName">
        <el-input
          v-model.trim="form.shortName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { commonApi } from '@/api';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    parentName: null,
    parentCode: null,
    areaCode: null,
    name: null,
    shortName: null,
    id: null,
    level: 0,
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
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑中国行政区定义' : '新建中国行政区定义');
      this.form = Object.assign(getDefaultForm(), info || {});
    },
    setForm(data) {
      this.form = {
        ...this.form,
        ...data,
      };
    },
    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      delete API_DATA.parentName;

      if (!API_DATA.parentCode) API_DATA.parentCode == 0;
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
      commonApi.addArea(API_DATA).then(res => {
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
      commonApi.updateArea(API_DATA).then(res => {
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
