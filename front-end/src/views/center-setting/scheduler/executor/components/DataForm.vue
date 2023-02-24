<template>
  <BjDrawerForm ref="BjDrawerForm" :form="form" :rules="rules" @handleConfirm="handleConfirm">
    <el-form-item label="租户" prop="tenantId">
      <BjSelectTenant v-model.trim="form.tenantId" placeholder="请输入" clearable />
    </el-form-item>

    <el-form-item label="排序" prop="orderSeq">
      <el-input
        v-model.trim="form.orderSeq"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="执行器编码" prop="executorCode">
      <el-input
        v-model.trim="form.executorCode"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
        :disabled="isEdit"
      />
    </el-form-item>

    <el-form-item label="执行器名称" prop="executorName">
      <el-input
        v-model.trim="form.executorName"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="注册方式" prop="executorType">
      <el-select
        v-model.trim="form.executorType"
        class="full-width"
        placeholder="请选择"
        :disabled="isEdit"
        clearable
      >
        <el-option
          v-for="(item, index) in EXECUTOR_TYPE_LIST"
          :key="index"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-form-item>

    <!-- （注册方式选择手动录入带出）：执行器的IP地址+端口，多台机器时使用,隔开。 -->

    <el-form-item
      v-show="form.executorType == EXECUTOR_TYPE_JSON.MANUAL"
      label="OnLine机器地址"
      prop="addressList"
    >
      <el-input
        v-model.trim="form.addressList"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="执行器的IP地址+端口，多台机器时使用,隔开"
        clearable
      />
    </el-form-item>

    <!-- 服务（注册方式选择自动注册带出）：即选择注册到某个服务上。 -->

    <el-form-item
      v-show="form.executorType == EXECUTOR_TYPE_JSON.AUTO"
      label="服务"
      prop="serverName"
    >
      <el-input
        v-model.trim="form.serverName"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="自动注册到某个服务上"
        clearable
      />
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import { settingApi } from '@/api';
import { EXECUTOR_TYPE_LIST, EXECUTOR_TYPE_JSON } from '@/utils/constants';
const getDefaultForm = () => {
  return {
    addressList: null,
    executorCode: null,
    executorId: null,
    executorName: null,
    executorType: null,
    orderSeq: null,
    serverName: null,
    status: null,
    tenantId: 'default',
  };
};

export default {
  data() {
    const validate1 = (rule, value, callback) => {
      console.log(rule);
      if (!value && this.form.executorType == this.EXECUTOR_TYPE_JSON.MANUAL) {
        callback(new Error('请输入'));
      } else {
        callback();
      }
    };
    const validate2 = (rule, value, callback) => {
      console.log(rule);
      if (!value && this.form.executorType == this.EXECUTOR_TYPE_JSON.AUTO) {
        callback(new Error('请输入'));
      } else {
        callback();
      }
    };

    return {
      EXECUTOR_TYPE_LIST,
      EXECUTOR_TYPE_JSON,

      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        tenantId: [{ required: true, message: '请选择', trigger: 'change' }],
        orderSeq: [{ required: true, message: '请输入', trigger: 'blur' }],
        executorCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        executorName: [{ required: true, message: '请输入', trigger: 'blur' }],
        executorType: [{ required: true, message: '请选择', trigger: 'change' }],
        addressList: [{ required: false, validator: validate1, trigger: 'blur' }],
        serverName: [{ required: false, validator: validate2, trigger: 'blur' }],
      },
    };
  },
  watch: {
    'form.orderSeq'(v) {
      this.form.orderSeq = Number(String(v).replace(/[^0-9]/g, ''));
    },
    'form.executorType'(v) {
      if (v == this.EXECUTOR_TYPE_JSON.AUTO) {
        this.form.addressList = null;
      }
      if (v == this.EXECUTOR_TYPE_JSON.MANUAL) {
        this.form.serverName = null;
      }
    },
  },
  methods: {
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑执行器' : '新建执行器');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm() {
      this.formatAddressList();
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
    formatAddressList() {
      const inputValue = this.form.addressList || '';
      const VALUES = inputValue.split(/[|,、，/\s+]/).filter(address => {
        return !!address;
      });
      this.form.addressList = Array.from(new Set([...VALUES])).join(',');
    },
    reFresh() {
      this.$emit('reFresh');
    },
    // 修改API
    updateApi(API_DATA) {
      settingApi.updateExecutors(API_DATA).then(res => {
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
    addApi(API_DATA) {
      settingApi.addExecutors(API_DATA).then(res => {
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
