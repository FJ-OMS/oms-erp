<template>
  <BjDrawerForm ref="BjDrawerForm" :form="form" :rules="rules" @handleConfirm="handleConfirm">
    <BjDivider>配置</BjDivider>
    <el-row>
      <el-col :span="21">
        <el-form-item label="分组" prop="bucketName">
          <el-input
            v-model.trim="form.bucketName"
            :maxlength="MAX_LENGTH.DEFAULT"
            :disabled="isEdit"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="21">
        <el-form-item label="上传目录" prop="directory">
          <el-input
            v-model.trim="form.directory"
            :maxlength="MAX_LENGTH.DEFAULT"
            :disabled="isEdit"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-col>
      <el-col :span="21">
        <el-form-item label="文件分类" prop="docType">
          <BjSelectLov
            v-model="form.docType"
            class="full-width"
            placeholder="请选择"
            filterable
            clearable
            lov-code="HFLE.CONTENT_TYPE"
          />
        </el-form-item>
      </el-col>
      <el-col :span="21">
        <el-form-item label="存储配置编码" prop="storageCode">
          <el-input
            v-model.trim="form.storageCode"
            :maxlength="MAX_LENGTH.DEFAULT"
            placeholder="请输入"
            clearable
          />
        </el-form-item>
      </el-col>
    </el-row>

    <BjDivider>文件</BjDivider>
    <el-row>
      <BjUpload
        ref="upload"
        :limit="8"
        multiple
        show-file-list
        list-type="picture-card"
        :query="form"
        @afterUpload="afterUpload"
      >
        <template slot="default"> <i class="el-icon-plus avatar-uploader-icon" /> </template
      ></BjUpload>
    </el-row>
  </BjDrawerForm>
</template>
<script>
import BjUpload from '@/components/BjUpload/index';
const getDefaultForm = () => {
  return {
    bucketName: null,
    directory: null,
    fileName: null,
    docType: null,
    storageCode: null,
  };
};

export default {
  components: {
    BjUpload,
  },
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        bucketName: [{ required: true, message: '请输入', trigger: 'blur' }],
        storageSize: [{ required: true, message: '请输入', trigger: 'blur' }],
      },
    };
  },
  computed: {},
  watch: {
    // 'form.orderSeq'(v) {
    //   this.form.orderSeq = Number(String(v).replace(/[^0-9]/g, ''));
    // },
  },

  mounted() {},
  methods: {
    afterUpload(res) {
      console.log('图片链接', res);
      this.$refs.BjDrawerForm.hide();
      this.reFresh();
    },
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.form = Object.assign(getDefaultForm(), info || {});
      this.form_contentType = null;
      this.form_fileFormat = null;
    },
    handleConfirm() {
      this.$refs.upload.submitUpload();
    },
    reFresh() {
      this.$emit('reFresh');
    },
  },
};
</script>
