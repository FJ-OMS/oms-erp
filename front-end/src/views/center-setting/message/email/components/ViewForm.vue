<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="租户" prop="tenantId">
            <el-input
              v-model.trim="form.tenantId"
              :maxlength="MAX_LENGTH.DEFAULT"
              disabled
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
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
        <el-col :span="8">
          <el-form-item label="账户名称" prop="serverName">
            <el-input
              v-model.trim="form.serverName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model.trim="form.username"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="密码" prop="passwordEncrypted">
            <el-input
              v-model.trim="form.passwordEncrypted"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="邮件服务器" prop="host">
            <el-input
              v-model.trim="form.host"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="端口" prop="port">
            <el-input
              v-model.trim="form.port"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="协议" prop="protocol">
            <BjSelectLov v-model="form.protocol" lov-code="HMSG.EMAIL_PROTOCOL" />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="重试次数" prop="tryTimes">
            <el-input
              v-model.trim="form.tryTimes"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="发送人" prop="sender">
            <el-input
              v-model.trim="form.sender"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="安全策略" prop="filterStrategy">
            <BjSelectLov v-model="form.filterStrategy" lov-code="HMSG.EMAIL.FILTER_STRATEGY" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="启用" prop="enabledFlag ">
            <el-switch v-model.trim="form.enabledFlag" :active-value="1" :inactive-value="0" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="*">
            <p class="tips">使用163邮箱时，用户名 发送人都要是邮箱账号，邮件才能正常发送</p>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <BjDivider />
    <div class="mb10">
      <el-button type="primary" @click="onConifgFormShow">新建服务器配置项</el-button>
      <el-button
        :disabled="tableSelected.length == 0"
        type="primary"
        class="delete-btn"
        @click="onConifgFormDelete"
        >删除</el-button
      >
    </div>

    <el-table
      ref="multipleTable"
      :data="tableData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      style="width: 100%"
      row-key="BJ_EBC_ID"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" show-overflow-tooltip />

      <el-table-column prop="propertyCode" label="属性编码" />

      <el-table-column prop="propertyValue" label="属性值" />
      <!-- <el-table-column label="操作" width="100" align="center" /> -->
    </el-table>

    <BjDivider />

    <span slot="footer" class="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleConfirm('form')">保 存</el-button>
    </span>
    <ConfigDialog ref="ConfigDialog" @confirm="handleConfigConfirm" />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { messageApi } from '@/api';
import ConfigDialog from './ConfigDialog.vue';
import { randomID } from '@/utils';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    emailProperties: [
      // {
      //   _innerMap: {},
      //   propertyCode: 'string',
      //   propertyId: 0,
      //   propertyValue: 'string',
      //   serverId: 0,
      //   tenantId: 0,
      // },
    ],
    enabledFlag: 0,
    filterStrategy: null,
    host: null,
    passwordEncrypted: null,
    port: null,
    protocol: null,
    sender: null,
    serverCode: null,
    serverName: null,
    tenantId: 'default',
    tryTimes: null,
    username: null,
  };
};

export default {
  name: 'SettingMessageEmailViewForm',
  components: {
    ConfigDialog,
  },
  mixins: [viewMiXin],
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      form: getDefaultForm(),
      // 表单校验
      rules: {
        serverCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        serverName: [{ required: true, message: '请输入', trigger: 'blur' }],
        tryTimes: [{ required: true, message: '请输入', trigger: 'blur' }],
        username: [{ required: true, message: '请输入', trigger: 'blur' }],
        sender: [{ required: true, message: '请输入', trigger: 'blur' }],
        port: [{ required: true, message: '请输入', trigger: 'blur' }],
        host: [{ required: true, message: '请输入', trigger: 'blur' }],

        enabledFlag: [{ required: true, message: '请选择', trigger: 'change' }],
        protocol: [{ required: true, message: '请选择', trigger: 'change' }],
      },
      tableData: [],
      tableSelected: [],
    };
  },
  computed: {},
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
          this.tableData = (info.emailProperties || []).map(obj => {
            return {
              BJ_EBC_ID: randomID(),
              ...obj,
            };
          });
        }
      },
    },
  },

  mounted() {},
  methods: {
    onConifgFormShow() {
      this.$refs.ConfigDialog.show();
    },
    onConifgFormDelete() {
      this.tableSelected.forEach(row => {
        const TEMP = row.BJ_EBC_ID;
        this.tableData.splice(
          this.tableData.findIndex(e => e.BJ_EBC_ID == TEMP),
          1,
        );
      });
    },
    handleConfigConfirm(obj) {
      this.tableData.push({
        BJ_EBC_ID: randomID(),
        ...obj,
      });
    },
    handleSelectionChange(data) {
      console.log(data);
      this.tableSelected = [].concat(data);
    },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      }
      API_DATA['emailProperties'] = this.tableData || [];
      console.log(API_DATA);
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isEdit) {
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },

    /**
     *
     *
     */

    // 修改API
    updateApi(API_DATA) {
      messageApi.updateEmail(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
        }
      });
    },
    // 新增API
    addApi(API_DATA) {
      messageApi.addEmail(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.handleClose();
          }, 500);
        }
      });
    },
  },
};
</script>
