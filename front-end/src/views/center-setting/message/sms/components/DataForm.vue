<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    width="800px"
    @handleConfirm="handleConfirm"
  >
    <el-col :span="22">
      <el-form-item label="账户编码" prop="serverCode">
        <el-input
          v-model.trim="form.serverCode"
          :maxlength="MAX_LENGTH.DEFAULT"
          :disabled="isEdit"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="账户名称" prop="serverName">
        <el-input
          v-model.trim="form.serverName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="短信签名" prop="signName">
        <el-input
          v-model.trim="form.signName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="服务类型" prop="serverTypeCode">
        <BjSelectLov v-model="form.serverTypeCode" lov-code="HMSG.SMS_SERVER_TYPE" />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="EndPoint" prop="endPoint">
        <el-input
          v-model.trim="form.endPoint"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="AccessKey" prop="accessKey">
        <el-input
          v-model.trim="form.accessKey"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <el-form-item label="AccessKeySecret" prop="accessKeySecret">
        <el-input
          v-model.trim="form.accessKeySecret"
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
import { messageApi } from '@/api';

const getDefaultForm = () => {
  return {
    accessKey: null,
    accessKeySecret: null,
    enabledFlag: 1,
    endPoint: null,
    serverCode: null,
    serverId: null,
    serverName: null,
    serverTypeCode: null,
    signName: null,
    tenantId: 'default',
    type: null,
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
        accessKey: [{ required: true, message: '请输入', trigger: 'blur' }],
        serverCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        serverName: [{ required: true, message: '请输入', trigger: 'blur' }],
        serverTypeCode: [{ required: true, message: '请选择', trigger: 'change' }],
        signName: [{ required: true, message: '请输入', trigger: 'blur' }],
      },

      afterUploadCount: 0,
    };
  },
  computed: {},
  watch: {
    afterUploadCount(v) {
      console.log('afterUploadCount', v);
      if (v >= 2) {
        this.toConfirmApi();
      }
    },
  },
  mounted() {},
  methods: {
    show(info) {
      this.afterUploadCount = 0;
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑短信账户配置' : '新建短信账户配置');
      this.form = Object.assign(getDefaultForm(), info || {});
    },
    handleClose() {
      this.$refs.BjDrawerForm.hide();
    },
    handleConfirm() {
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
      messageApi.addSMS(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
            this.reFresh();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      messageApi.updateSMS(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
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
