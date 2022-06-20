<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <BjDivider>基本信息</BjDivider>
    <el-col :span="24">
      <el-form-item label="名称" prop="meaning">
        <el-input
          v-model.trim="form.meaning"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <BjFormItemCode v-model="form.value" label="编码" prop="value" />
    </el-col>

    <el-col :span="24">
      <el-form-item label="排序" prop="orderSeq">
        <BjInputNumber v-model.trim="form.orderSeq" placeholder="请输入" clearable />
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { baseDataApi } from '@/api';

const getDefaultForm = () => {
  return {
    _innerMap: {},
    description: null,
    enabledFlag: 1,
    endDateActive: null,
    lovCode: null,
    lovId: null,
    lovValueId: null,
    meaning: null,
    orderSeq: null,
    parentMeaning: null,
    parentValue: null,
    startDateActive: null,
    tag: null,
    tenantId: null,
    tenantName: null,
    value: null,
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
        meaning: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        value: [{ required: true, message: '请选输入编码', trigger: 'blur' }],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!(info && info.meaning);
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑' : '新建');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
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
      baseDataApi.addLovValues(API_DATA).then(res => {
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
      baseDataApi.updateLovValues(API_DATA).then(res => {
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
