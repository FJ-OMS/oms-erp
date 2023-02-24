<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-form-item label="租户" prop="tenantId">
      <BjSelectTenant v-model.trim="form.tenantId" placeholder="请输入" clearable />
    </el-form-item>

    <el-form-item label="可执行编码" prop="executableCode">
      <el-input
        v-model.trim="form.executableCode"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        :disabled="isEdit"
        clearable
      />
    </el-form-item>

    <el-form-item label="可执行名称" prop="executableName">
      <el-input
        v-model.trim="form.executableName"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="可执行类型" prop="exeTypeCode">
      <BjSelectLov v-model="form.exeTypeCode" lov-code="HSDR.GLUE_TYPE" />
    </el-form-item>

    <el-form-item
      label="JobHandler"
      prop="jobHandler"
      :rules="{
        required: form.exeTypeCode == 'SIMPLE',
        message: '请输入Jobhandler',
        trigger: 'blur',
      }"
    >
      <el-input
        v-model.trim="form.jobHandler"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="可执行描述" prop="executableDesc">
      <el-input
        v-model.trim="form.executableDesc"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="执行器" prop="executorId">
      <BjSelectLov v-model="form.executorId" lov-code="BJ_EXECUTOR_ID" />
    </el-form-item>

    <el-form-item label="执行器策略" prop="executorStrategy">
      <BjSelectLov v-model="form.executorStrategy" lov-code="HSDR.EXECUTOR_STRATEGY" />
    </el-form-item>

    <el-form-item label="失败处理策略" prop="failStrategy">
      <BjSelectLov v-model="form.failStrategy" lov-code="HSDR.FAIL_STRATEGY" />
    </el-form-item>

    <el-form-item label="状态" prop="enabledFlag">
      <el-switch v-model="form.enabledFlag" :active-value="1" :inactive-value="0" />
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import { settingApi } from '@/api';
import { EXECUTOR_TYPE_LIST, EXECUTOR_TYPE_JSON } from '@/utils/constants';
const getDefaultForm = () => {
  return {
    enabledFlag: 0, // 状态：启用和禁用两种状态。当为禁用时不能使用该并发可执行（请求定义无法选择）
    exeTypeCode: null, // 可执行类型：对应调度任务页面的运行模式。
    executableName: null, // 可执行名称：并发可执行的名称。
    executableCode: null, // 可执行编码：并发可执行唯一标识编码。
    executableDesc: null, // 可执行描述：并发可执行的描述信息。
    // executableId: null,
    executorId: null, // 执行器：对应调度任务页面的执行器。
    executorStrategy: null, // 执行器策略：对应调度任务页面的执行器策略。
    failStrategy: null, // 失败处理策略：对应调度任务页面的失败处理策略。
    jobHandler: null, // JobHandler：对应调度任务页面的JobHandler。
    strategyParam: null,
    tenantId: 'default',
  };
};

export default {
  data() {
    return {
      EXECUTOR_TYPE_LIST,
      EXECUTOR_TYPE_JSON,

      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        tenantId: [{ required: true, message: '请选择', trigger: 'change' }],
        enabledFlag: [{ required: true, message: '请选择', trigger: 'change' }],
        executableCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        executableName: [{ required: true, message: '请输入', trigger: 'blur' }],
        exeTypeCode: [{ required: true, message: '请选择', trigger: 'change' }],
        // executableDesc: [{ required: true, message: '请输入', trigger: 'blur' }],
        executorId: [{ required: true, message: '请选择', trigger: 'change' }],
        executorStrategy: [{ required: true, message: '请选择', trigger: 'change' }],
        failStrategy: [{ required: true, message: '请选择', trigger: 'change' }],
        strategyParam: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
    };
  },
  computed: {},
  watch: {
    // 'form.orderSeq'(v) {
    //   this.form.orderSeq = Number(String(v).replace(/[^0-9]/g, ''));
    // },
    'form.executableCode'(v) {
      this.form.executableCode = this.form.executableCode.replace(/\s+/g, '');
    },
  },
  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑可执行定义' : '新建可执行定义');
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
      settingApi.updateExecutables(API_DATA).then(res => {
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
      settingApi.addExecutables(API_DATA).then(res => {
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
