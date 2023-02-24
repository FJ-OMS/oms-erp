<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-col :span="22">
      <el-form-item label="原因编码" prop="reasonCode">
        <el-input
          v-model.trim="form.reasonCode"
          :maxlength="MAX_LENGTH.DEFAULT"
          disabled
          placeholder="自动生成"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="原因描述" prop="reasonDescribe">
        <el-input
          v-model.trim="form.reasonDescribe"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { baseDataApi } from '@/api';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    reasonCode: null,
    reasonDescribe: null,
    type: null,
    status: 1,
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
        reasonDescribe: [{ required: true, message: '请输入原因描述', trigger: 'blur' }],
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
      if (info) {
        this.isEdit = !!info.id;
      }
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑售后原因' : '新建售后原因');
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
      baseDataApi.addAfterSalesReasons(API_DATA).then(res => {
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
      baseDataApi.updateAfterSalesReasons(API_DATA).then(res => {
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
