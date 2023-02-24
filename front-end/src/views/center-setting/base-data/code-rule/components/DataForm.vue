<template>
  <div>
    <BjDrawerForm
      ref="BjDrawerForm"
      :form="form"
      :rules="rules"
      width="800px"
      @handleConfirm="handleConfirm"
    >
      <el-col :span="22">
        <el-form-item label="规则编码" prop="ruleCode">
          <el-input
            v-model.trim="form.ruleCode"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
        <el-form-item label="规则名称" prop="ruleName">
          <el-input
            v-model.trim="form.ruleName"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
        <el-form-item label="租户" prop="tenantId">
          <BjSelectTenant v-model.trim="form.tenantId" clearable />
        </el-form-item>
      </el-col>
    </BjDrawerForm>
  </div>
</template>
<script>
import { baseDataApi } from '@/api';

const getDefaultForm = () => {
  return {
    _innerMap: {},
    description: null,
    objectVersionNumber: null,
    ruleCode: null,
    ruleId: null,
    ruleLevel: null,
    ruleName: null,
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
        ruleCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        ruleName: [{ required: true, message: '请输入', trigger: 'blur' }],
        tenantId: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑' : '设置剩余数量补货优先');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      API_DATA['ruleId'] = this.$route.query.id || '';
      // if (this.isEdit) {
      //   this.updateApi(API_DATA);
      // } else {
      this.addApi(API_DATA);
      // }
    },
    reFresh() {
      this.$emit('reFresh');
    },
    addApi(API_DATA) {
      baseDataApi.addCodeRule(API_DATA).then(res => {
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
      baseDataApi.addCodeRule(API_DATA).then(res => {
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
