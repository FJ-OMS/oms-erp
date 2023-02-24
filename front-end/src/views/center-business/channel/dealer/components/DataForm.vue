<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-form-item label="经销商名称" prop="dealerName">
      <el-input v-model="form.dealerName" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
    </el-form-item>
    <BjFormItemCode
      v-model="form.dealerCode"
      :readonly="isEdit"
      label="经销商编码"
      prop="dealerCode"
    />
    <el-form-item label="联系人姓名" prop="contactName">
      <el-input v-model="form.contactName" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
    </el-form-item>

    <BjFormItemMobile
      v-model.trim="form.contactPhone"
      prop="contactPhone"
      label="联系人手机"
      clearable
    />

    <el-form-item label="描述" prop="description">
      <el-input v-model="form.description" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import { channelApi } from '@/api';

const getDefaultForm = () => {
  return {
    _innerMap: {},
    contactName: null,
    contactcontactPhone: null,
    dealerCode: null,
    dealerName: null,
    deleteFlag: false,
    description: null,
    id: null,
    status: false,
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
        // dealerCode: [{ required: true, max: 50, message: '请输入经销商编码', trigger: 'blur' }],
        dealerName: [{ required: true, max: 50, message: '请输入经销商名称', trigger: 'blur' }],
        username: [{ required: true, max: 50, message: '请输入联系人姓名', trigger: 'blur' }],
        contactPhone: [{ required: true, max: 11, message: '请输入联系人手机', trigger: 'blur' }],
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
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑经销商' : '新建经销商');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm() {
      console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }

      if (this.isEdit) {
        this.updateDealer(API_DATA);
      } else {
        this.addDealer(API_DATA);
      }
    },
    reFresh() {
      this.$emit('reFresh');
    },

    // 修改API
    updateDealer(API_DATA) {
      channelApi.updateDealer(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    // 新增API
    addDealer(API_DATA) {
      channelApi.addDealer(API_DATA).then(res => {
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
