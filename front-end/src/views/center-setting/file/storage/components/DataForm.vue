<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="140px"
    @handleConfirm="handleConfirm"
  >
    <el-form-item label="租户" prop="tenantId">
      <BjSelectTenant v-model.trim="form.tenantId" placeholder="请输入" clearable />
    </el-form-item>

    <el-form-item label="存储类型" prop="storageType">
      <el-select v-model.trim="form.storageType" class="full-width" placeholder="请选择" clearable>
        <el-option
          v-for="(item, index) in FILE_STORAGE_TYPE_LIST"
          :key="index"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-form-item>
    <BjFormItemCode
      v-model="form.storageCode"
      :readonly="isEdit"
      label="存储编码"
      prop="storageCode"
    />
    <el-form-item label="文件名前缀策略" prop="prefixStrategy">
      <BjSelectLov v-model="form.prefixStrategy" lov-code="HFLE.PREFIX_STRATEGY" />
    </el-form-item>

    <el-form-item label="域名(Domain)" prop="domain">
      <el-input
        v-model.trim="form.domain"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="EndPoint" prop="endPoint">
      <el-input
        v-model.trim="form.endPoint"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="AccessKeyId" prop="accessKeyId">
      <el-input
        v-model.trim="form.accessKeyId"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="AccessKeySecret" prop="accessKeySecret">
      <el-input
        v-model.trim="form.accessKeySecret"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item
      label=" appId"
      prop="appId"
      :rules="{
        required: form.storageType == 4,
        message: '请输入',
        trigger: 'blur',
      }"
    >
      <el-input
        v-model.trim="form.appId"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item
      label=" bucket所属地区"
      prop="region"
      :rules="{
        required: form.storageType == 4,
        message: '请输入',
        trigger: 'blur',
      }"
    >
      <el-input
        v-model.trim="form.region"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="bucket权限控制" prop="accessControl">
      <BjSelectLov v-model="form.accessControl" lov-code="HFLE.CAPACITY.ACCESS_CONTROL" />
    </el-form-item>

    <el-form-item label="bucket前缀" prop="bucketPrefix">
      <el-input
        v-model.trim="form.bucketPrefix"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <!-- 0:不启用，1:启用 -->
    <el-form-item label="自动创建bucket" prop="createBucketFlag">
      <el-switch v-model="form.createBucketFlag" :active-value="1" :inactive-value="0" />
    </el-form-item>

    <el-form-item label="默认" prop="defaultFlag">
      <el-switch v-model="form.defaultFlag" :active-value="1" :inactive-value="0" />
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import { settingApi } from '@/api';
import { FILE_STORAGE_TYPE_LIST } from '@/utils/constants';

const getDefaultForm = () => {
  return {
    tenantId: 'default',
    storageType: null,
    storageCode: null,
    prefixStrategy: null,
    domain: null,
    endPoint: null,
    accessKeyId: null,
    accessKeySecret: null,
    accessControl: null,
    bucketPrefix: null,
    createBucketFlag: null,
    defaultFlag: null,
    /** 腾讯 */
    region: null,
    appId: null,
  };
};

export default {
  data() {
    return {
      FILE_STORAGE_TYPE_LIST,

      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        tenantId: [{ required: true, message: '请选择', trigger: 'change' }],
        storageType: [{ required: true, message: '请选择', trigger: 'change' }],
        storageCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        prefixStrategy: [{ required: true, message: '请选择', trigger: 'change' }],
        domain: [{ required: true, message: '请输入', trigger: 'blur' }],
        accessKeyId: [{ required: true, message: '请输入', trigger: 'blur' }],
        accessKeySecret: [{ required: true, message: '请输入', trigger: 'blur' }],
        endPoint: [{ required: true, message: '请输入', trigger: 'blur' }],
        accessControl: [{ required: true, message: '请选择', trigger: 'blur' }],
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
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑存储配置' : '新建存储配置');
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
      settingApi.createFileStorageConfigs(API_DATA).then(res => {
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
      settingApi.createFileStorageConfigs(API_DATA).then(res => {
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
