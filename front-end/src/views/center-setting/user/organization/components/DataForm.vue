<template>
  <BjDrawerForm
    ref="BjDrawerForm"
    :form="form"
    :rules="rules"
    label-width="140px"
    @handleConfirm="handleConfirm"
  >
    <el-form-item label="公司名称" prop="orgName">
      <el-input v-model="form.orgName" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
    </el-form-item>

    <BjFormItemCode v-model="form.orgCode" :readonly="isEdit" label="公司编码" prop="orgCode" />

    <el-form-item label="所属集团">
      {{ group.groupName }}
    </el-form-item>
    <el-form-item label="所属上级公司" prop="pid">
      <BjCompanyTreeSelect v-model="form.pid" placeholder="请选择" />
    </el-form-item>

    <el-form-item label="法人姓名" prop="legalPerson">
      <el-input v-model="form.legalPerson" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
    </el-form-item>
    <el-form-item label="统一社会信用代码" prop="uniformSocialCreditCode">
      <el-input
        v-model="form.uniformSocialCreditCode"
        :maxlength="MAX_LENGTH.DEFAULT"
        placeholder="请输入"
      />
    </el-form-item>
    <el-form-item label="营业执照图片" prop="businessLicense">
      <BjUpload
        ref="upload"
        class="avatar-uploader"
        auto-upload
        accept="image/png,image/jpeg"
        :accept-list="['image/jpeg', 'image/png']"
        :limit-size="2048"
        @afterUpload="afterUpload"
      >
        <template slot="default">
          <BjKeyImage v-if="form.businessLicense" :url="form.businessLicense" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </template>
        <template slot="tip"> 支持JPG/JPEG/PNG格式，图片大小不超过2M </template>
      </BjUpload>
    </el-form-item>
  </BjDrawerForm>
</template>
<script>
import { channelApi } from '@/api';
import BjUpload from '@/components/BjUpload/index';
const getDefaultForm = () => {
  return {
    _innerMap: {},
    deleteFlag: false,
    id: null,
    legalPerson: null,
    orgCode: null,
    orgName: null,
    status: true, // 默认启用
    // TODO: 集团未完成
    groupId: 0,
    pid: null,
    uniformSocialCreditCode: null,
    objectVersionNumber: null,
    businessLicense: null,
    _token: null,
  };
};

export default {
  components: {
    BjUpload,
  },
  props: {
    group: {
      type: Object,
      default: () => {
        return {
          creationDate: null,
          createdBy: null,
          lastUpdateDate: null,
          lastUpdatedBy: null,
          objectVersionNumber: null,
          _token: null,
          id: '=gVBsVrVQVqb1IQBLeIDbC-mWrIVMJ7kUIy0HjF5CVQU==',
          groupName: null,
          groupShortName: null,
          logo: null,
          favicon: null,
        };
      },
    },
  },
  data() {
    return {
      // 表单参数
      isEdit: false,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        // orgCode: [{ required: true, max: 50, message: '请输入公司编码', trigger: 'blur' }],
        orgName: [{ required: true, max: 50, message: '请输入公司名称', trigger: 'blur' }],
        // legalPerson: [{ required: true, max: 50, message: '请输入营业执照名称', trigger: 'blur' }],
        // uniformSocialCreditCode: [
        //   { required: true, max: 50, message: '请输入统一社会信用代码', trigger: 'blur' },
        // ],
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  methods: {
    afterUpload(src) {
      this.form.businessLicense = src;
    },
    show(info) {
      this.isEdit = !!info;
      this.$refs.BjDrawerForm.show();
      this.$refs.BjDrawerForm.setTitle(this.isEdit ? '编辑公司' : '新建公司');
      this.form = Object.assign(getDefaultForm(), info || {});
    },

    handleConfirm() {
      // console.log('确认', this.form);

      const API_DATA = this.$lodash.clone(this.form);
      // groupId默认取值
      API_DATA['groupId'] = this.group.id;

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
      channelApi.updateSalesOrganization(API_DATA).then(res => {
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
      channelApi.addSalesOrganization(API_DATA).then(res => {
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
