<template>
  <BjDrawerForm ref="BjDrawerForm" :form="form" :rules="rules" @handleConfirm="handleConfirm">
    <BjFormItemCode v-model="form.jobCode" :readonly="isEdit" label="任务编码" prop="jobCode" />

    <el-form-item label="任务描述" prop="description">
      <el-input
        v-model.trim="form.description"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="租户" prop="tenantId">
      <BjSelectTenant v-model.trim="form.tenantId" placeholder="请输入" clearable />
    </el-form-item>

    <el-form-item label="执行策略" prop="executorStrategy">
      <BjSelectLov v-model="form.executorStrategy" lov-code="HSDR.EXECUTOR_STRATEGY" />
    </el-form-item>

    <el-form-item label="失败处理策略" prop="failStrategy">
      <BjSelectLov v-model="form.failStrategy" lov-code="HSDR.FAIL_STRATEGY" />
    </el-form-item>

    <el-form-item label="Cron" prop="jobCron">
      <el-input
        v-model.trim="form.jobCron"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="执行器" prop="executorId">
      <BjSelectLov v-model="form.executorId" lov-code="BJ_EXECUTOR_ID" />
    </el-form-item>

    <el-form-item label="报警邮件" prop="alarmEmail">
      <el-input
        v-model.trim="form.alarmEmail"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="任务类型" prop="glueType">
      <BjSelectLov v-model="form.glueType" lov-code="HSDR.GLUE_TYPE" />
    </el-form-item>

    <el-form-item label="有效时间从" prop="startDate">
      <el-date-picker
        v-model="form.startDate"
        class="full-width"
        value-format="yyyy-MM-dd HH:mm:ss"
        type="datetime"
        placeholder="请选择"
      />
    </el-form-item>

    <el-form-item
      label="JobHandler"
      prop="jobHandler"
      :rules="{
        required: form.glueType == 'SIMPLE',
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

    <el-form-item label="有效时间至" prop="endDate">
      <el-date-picker
        v-model="form.endDate"
        class="full-width"
        value-format="yyyy-MM-dd HH:mm:ss"
        type="datetime"
        placeholder="请选择"
      />
    </el-form-item>

    <el-form-item label="任务参数" prop="jobParam">
      <el-input
        v-model.trim="form.jobParam"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <!-- 1周期 0非周期 -->
    <el-form-item label="周期性" prop="cycleFlag">
      <el-switch v-model="form.cycleFlag" :active-value="1" :inactive-value="0" />
    </el-form-item>

    <!-- integer -->
    <el-form-item label="串行任务" prop="serial">
      <el-switch v-model="form.serial" :active-value="1" :inactive-value="0" />
    </el-form-item>

    <!-- integer -->
    <el-form-item label="自动初始化" prop="initFlag">
      <el-switch v-model="form.initFlag" :active-value="1" :inactive-value="0" />
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import { settingApi } from '@/api';

const getDefaultForm = () => {
  return {
    jobCode: null,
    tenantId: 'default',
    description: null,
    executorStrategy: null,
    failStrategy: null,
    jobCron: null,
    executorId: null,
    alarmEmail: null,
    glueType: null,
    startDate: null,
    endDate: null,
    jobHandler: null,
    jobParam: null,
    cycleFlag: 0,
    serial: 0,
    initFlag: 0,
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
        jobCode: [{ required: true, message: '请输入任务编码', trigger: 'blur' }],
        tenantId: [{ required: true, message: '请输入租户名称', trigger: 'blur' }],
        executorStrategy: [{ required: true, message: '请选择策略', trigger: 'change' }],
        failStrategy: [{ required: true, message: '请选择策略', trigger: 'change' }],
        executorId: [{ required: true, message: '请选择执行器', trigger: 'change' }],
        glueType: [{ required: true, message: '请选择任务类型', trigger: 'change' }],
      },
    };
  },
  computed: {},
  watch: {
    'form.orderSeq'(v) {
      this.form.orderSeq = Number(String(v).replace(/[^0-9]/g, ''));
    },
    'form.valueCode'(v) {
      this.form.valueCode = this.form.valueCode.replace(/\s+/g, '');
    },
  },

  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑任务' : '新建任务');
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
    // 修改API
    updateApi(API_DATA) {
      settingApi.updateJobInfo(API_DATA).then(res => {
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
      settingApi.createJobInfo(API_DATA).then(res => {
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
