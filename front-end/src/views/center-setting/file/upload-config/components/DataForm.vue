<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-form-item label="分组" prop="bucketName">
      <el-input
        v-model.trim="form.bucketName"
        :maxlength="MAX_LENGTH.DEFAULT"
        :disabled="isEdit"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="上传目录" prop="directory">
      <el-input
        v-model.trim="form.directory"
        :maxlength="MAX_LENGTH.DEFAULT"
        :disabled="isEdit"
        placeholder="请输入"
        clearable
      />
    </el-form-item>

    <el-form-item label="文件分类" prop="contentType">
      <BjSelectLov
        v-model="form_contentType"
        lov-code="HFLE.CONTENT_TYPE"
        filterable
        clearable
        multiple
      />
    </el-form-item>

    <el-form-item label="文件格式" prop="fileFormat">
      <BjSelectLov
        v-model="form_fileFormat"
        lov-code="HFLE.FILE_FORMAT"
        filterable
        clearable
        multiple
      />
    </el-form-item>

    <el-form-item label="文件大小限制" prop="storageSize" class="el-input-group__append-width-100">
      <el-input
        v-model.trim="form.storageSize"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
        clearable
      >
        <BjSelectLov slot="append" v-model="form.storageUnit" lov-code="HFLE.STORAGE_UNIT" />
      </el-input>
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import { settingApi } from '@/api';
import { EXECUTOR_TYPE_LIST, EXECUTOR_TYPE_JSON } from '@/utils/constants';
const getDefaultForm = () => {
  return {
    bucketName: null,
    contentType: null,
    directory: null,
    fileFormat: null,
    storageSize: 0,
    storageUnit: 'MB',
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
        bucketName: [{ required: true, message: '请输入', trigger: 'blur' }],
        storageSize: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
      form_contentType: null,
      form_fileFormat: null,
    };
  },
  computed: {},
  watch: {
    // 'form.orderSeq'(v) {
    //   this.form.orderSeq = Number(String(v).replace(/[^0-9]/g, ''));
    // },
    form_contentType(v) {
      if (v) this.form.contentType = v.join(',');
    },
    'form.contentType'(v) {
      if (v) this.form_contentType = v.split(',');
    },
    form_fileFormat(v) {
      if (v) this.form.fileFormat = v.join(',');
    },
    'form.fileFormat'(v) {
      if (v) this.form_fileFormat = v.split(',');
    },
  },

  mounted() {},
  methods: {
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑上传详细配置' : '新建上传详细配置');
      this.form = getDefaultForm();
      this.form_contentType = null;
      this.form_fileFormat = null;
      this.$nextTick(() => {
        this.form = Object.assign(getDefaultForm(), info || {});
      });
    },

    handleConfirm() {
      const API_DATA = this.$lodash.clone(this.form);
      if (this.isEdit) {
        API_DATA['_innerMap'] = {};
      } else {
        API_DATA['tenantId'] = 'default';
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
      settingApi.updateUploadConfigs(API_DATA).then(res => {
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
      settingApi.addUploadConfigs(API_DATA).then(res => {
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
