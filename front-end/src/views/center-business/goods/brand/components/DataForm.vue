<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="120px"
    @handleConfirm="handleConfirm"
  >
    <el-col :span="22">
      <el-form-item label="品牌名称" prop="brandName">
        <el-input
          v-model.trim="form.brandName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
          clearable
        />
      </el-form-item>
    </el-col>
    <el-col :span="22">
      <BjFormItemCode v-model="form.code" label="品牌编码" prop="code" />
    </el-col>
    <el-col :span="22">
      <el-form-item label="生产商" prop="manufacturer">
        <el-input
          v-model.trim="form.manufacturer"
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
      <el-form-item label="品牌LOGO" prop="logo">
        <BjUpload
          ref="upload"
          class="avatar-uploader"
          auto-upload
          accept="image/png,image/jpeg,image/gif"
          :accept-list="['image/jpeg', 'image/png', 'image/gif']"
          :limit-size="50"
          out-size-text="您上传的LOGO图片大小超过50KB，请重新选择"
          @afterUpload="afterUpload"
        >
          <template slot="default">
            <BjKeyImage v-if="form.logo" :url="form.logo" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </template>
          <template slot="tip">
            建议尺寸：800*800像素，只能上传jpg/png/gif格式文件，文件不能超过50KB
          </template>
        </BjUpload>
      </el-form-item>
    </el-col>
  </BjDrawerForm>
</template>
<script>
import { goodsApi } from '@/api';

import BjUpload from '@/components/BjUpload/index';
const getDefaultForm = () => {
  return {
    brandName: null,
    id: null,
    code: null,
    logo: null,
    manufacturer: null,
    remark: null,
    status: 1,
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
        brandName: [{ required: true, message: '请输入品牌名称', trigger: 'blur' }],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    afterUpload(res) {
      this.form.logo = res;
    },
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑品牌' : '新建品牌');
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
    addApi(API_DATA) {
      goodsApi.addBrand(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.$refs.BjDrawerForm.hide();
            this.reFresh();
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      goodsApi.updateBrand(API_DATA).then(res => {
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
