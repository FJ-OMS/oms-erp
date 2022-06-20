<template>
  <div>
    <BjDrawerForm
      ref="BjDrawerForm"
      :form="form"
      :rules="rules"
      label-width="120px"
      width="800px"
      @handleConfirm="handleConfirm"
    >
      <el-col :span="22">
        <el-form-item label="消息模板" prop="templateCode">
          <el-input
            v-model.trim="form.templateName"
            :maxlength="MAX_LENGTH.DEFAULT"
            readonly
            placeholder="点击右侧搜索选择"
          >
            <el-button
              slot="append"
              :disabled="isEdit"
              icon="el-icon-search"
              @click="$refs.dialogList.show()"
            />
          </el-input>
        </el-form-item>
      </el-col>
      <el-col :span="22">
        <el-form-item label="消息类型" prop="typeCode">
          <BjSelectLov v-model="form.typeCode" lov-code="HMSG.MESSAGE_TYPE" />
        </el-form-item>
      </el-col>
      <el-col :span="22">
        <el-form-item label="账户编码" prop="serverCode">
          <el-input
            v-model.trim="form.serverCode"
            :maxlength="MAX_LENGTH.DEFAULT"
            readonly
            :placeholder="form.typeCode ? '点击右侧搜索账户选择' : '请先选择消息类型'"
          >
            <el-button
              slot="append"
              :disabled="!form.typeCode"
              icon="el-icon-search"
              @click="$refs.dialogListAccount.show()"
            />
          </el-input>
        </el-form-item>
      </el-col>
      <el-col :span="22">
        <el-form-item label="账户名称" prop="serverName">
          <el-input
            v-model.trim="form.serverName"
            :maxlength="MAX_LENGTH.DEFAULT"
            readonly
            placeholder="点击右侧搜索账户选择"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="22">
        <el-form-item label="重试次数" prop="tryTimes">
          <el-input
            v-model.trim="form.tryTimes"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="22">
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model.trim="form.remark"
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

    <!-- 消息模板 -->
    <BjDialogList ref="dialogList" :config="dialogListConfig" @confirm="onConfirmDialogList" />
    <!-- 账户编码 -->

    <BjDialogList
      ref="dialogListAccount"
      :config="dialogListConfigAccount"
      @confirm="onConfirmDialogListAccount"
    />
  </div>
</template>
<script>
import { messageApi } from '@/api';

const getDefaultForm = () => {
  return {
    templateName: null,
    templateCode: null,
    typeCode: 'SMS',
    enabledFlag: 1,

    tempServerId: null,
    serverCode: null,
    serverName: null,
    tryTimes: null,
    remark: null,
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
        templateCode: [{ required: true, message: '请选择', trigger: 'change' }],
        typeCode: [{ required: true, message: '请选择', trigger: 'change' }],
        serverCode: [{ required: true, message: '请选择', trigger: 'change' }],
        serverName: [{ required: true, message: '请选择', trigger: 'change' }],
        // tryTimes: [{ required: true, message: '请输入', trigger: 'blur' }],
      },

      dialogListConfig: {
        title: '选择消息模板',
        columns: [
          {
            prop: 'templateCode',
            label: '消息模板编码',
          },
          {
            prop: 'templateName',
            label: '消息模板名称',
          },
          {
            prop: 'enabledFlag',
            label: '状态',
            constantKey: 'STATUS_ENABLED_NUM',
          },
        ],
        search: [
          {
            placeholder: '消息模板编码',
            key: 'templateCode',
          },
          {
            placeholder: '消息模板名称',
            key: 'templateName',
          },
        ],
        actionApi: messageApi.getTemplates,
        query: '',
      },
      isFirstInfo: false,
    };
  },
  computed: {
    dialogListConfigAccount() {
      return {
        title: '选择账户',
        columns: [
          {
            prop: 'serverCode',
            label: '账户编码',
          },
          {
            prop: 'serverName',
            label: '账户名称',
          },
          {
            prop: 'enabledFlag',
            label: '状态',
            constantKey: 'STATUS_ENABLED_NUM',
          },
        ],
        search: [
          {
            placeholder: '账户编码',
            key: 'serverCode',
          },
          {
            placeholder: '账户名称',
            key: 'serverName',
          },
        ],
        actionApi:
          this.form.typeCode == 'EMAIL'
            ? messageApi.getEmail
            : this.form.typeCode == 'SMS'
            ? messageApi.getSMS
            : () => {}, // getEmail
        query: '',
      };
    },
  },
  watch: {
    'form.typeCode'(v, o) {
      console.log(v, o);
      if (v && !this.isFirstInfo) {
        this.form.serverCode = null;
        this.form.serverName = null;
      }
    },
  },
  mounted() {},
  methods: {
    onConfirmDialogList(data) {
      this.form.templateName = data[0].templateName;
      this.form.templateCode = data[0].templateCode;
    },
    onConfirmDialogListAccount(data) {
      this.form.serverCode = data[0].serverCode;
      this.form.serverName = data[0].serverName;
    },
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑消息发送配置' : '新建消息发送配');
      this.isFirstInfo = true;
      this.form = Object.assign(getDefaultForm(), info || {});
      setTimeout(() => {
        this.isFirstInfo = false;
      }, 500);
    },
    handleClose() {
      this.$refs.BjDrawerForm.hide();
    },
    handleConfirm() {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      this.handleClose();
      this.$emit('handleConfirm', API_DATA);
    },
    reFresh() {
      this.$emit('reFresh');
    },

    // addApi(API_DATA) {
    //   messageApi.addSMS(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('保存成功');
    //       setTimeout(() => {
    //         this.handleClose();
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
    // updateApi(API_DATA) {
    //   messageApi.updateSMS(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('保存成功');
    //       setTimeout(() => {
    //         this.handleClose();
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
  },
};
</script>
<style scoped>
.el-tag {
  margin: 6px;
}
</style>
