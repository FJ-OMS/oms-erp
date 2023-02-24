<template>
  <div class="app-container bj-label-colon">
    <BjRouteTabs :route-list="SETTING_ORGANIZATION_ROUTE_LIST" />
    <BjDivider right-fixed
      >集团信息

      <template slot="right">
        <el-button
          v-if="!isEdit"
          v-permission:SettingUserOrganizationGroup_Edit
          type="primary"
          @click="isEdit = true"
          >编 辑</el-button
        >
        <el-button v-if="isEdit" type="primary" @click="submitForm('form')">保 存</el-button>
        <el-button v-if="isEdit" @click="resetForm('form')">取 消</el-button>
      </template>
    </BjDivider>
    <el-form ref="form" :model="form" :rules="rules" class="app-container" label-width="100px">
      <el-form-item label="集团全称" prop="groupName">
        <el-input
          v-if="isEdit"
          v-model="form.groupName"
          :maxlength="MAX_LENGTH.DEFAULT"
          clearable
        />
        <span v-else>{{ form.groupName }}</span>
      </el-form-item>
      <!-- 该名称显示在系统顶部
      ① 不允许为空，为空无法保存

②  限制不能超过60个字符

③  修改后点击保存才生效
-->
      <el-form-item label="集团简称" prop="groupShortName">
        <el-input
          v-if="isEdit"
          v-model="form.groupShortName"
          :maxlength="MAX_LENGTH.DEFAULT"
          clearable
        />
        <span v-else>{{ form.groupShortName }}</span>
      </el-form-item>
      <el-form-item label="LOGO" prop="logo">
        <BjUpload
          ref="upload"
          class="avatar-uploader"
          auto-upload
          :disabled="!isEdit"
          :accept-list="['image/jpeg', 'image/png']"
          :limit-size="500"
          @afterUpload="afterUpload_logo"
        >
          <template slot="default">
            <BjKeyImage v-if="form.logo" :url="form.logo" class="avatar" />
            <template v-else>
              <i v-if="isEdit" class="el-icon-plus avatar-uploader-icon" />
              <i v-else class="el-icon-picture-outline avatar-uploader-icon" />
            </template>
          </template>
          <template slot="tip"> 支持PNG/JPG格式，且不超过500KB </template>
        </BjUpload>
      </el-form-item>
      <el-form-item label="Favicon">
        <BjUpload
          ref="upload"
          class="avatar-uploader"
          auto-upload
          :disabled="!isEdit"
          :accept-list="['image/x-icon']"
          :limit-size="200"
          @afterUpload="afterUpload_ico"
        >
          <template slot="default">
            <BjKeyImage v-if="form.favicon" :url="form.favicon" class="avatar" />
            <template v-else>
              <i v-if="isEdit" class="el-icon-plus avatar-uploader-icon" />
              <i v-else class="el-icon-picture-outline avatar-uploader-icon" />
            </template>
          </template>
          <template slot="tip"> 支持.ico格式，且不超过200KB </template>
        </BjUpload>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { SETTING_ORGANIZATION_ROUTE_LIST } from '@/utils/constants';
import BjUpload from '@/components/BjUpload/index';
import { channelApi } from '@/api';
export default {
  name: 'SettingManageOrganizationGroup',
  components: {
    BjUpload,
  },
  data() {
    return {
      SETTING_ORGANIZATION_ROUTE_LIST,
      // 原始数据
      protoData: {},
      form: {
        _innerMap: {},
        groupName: null,
        groupShortName: null,
        logo: null,
        favicon: null,
      },
      // 表单验证
      rules: {
        groupName: [
          { required: true, message: '请输入集团全称', trigger: 'blur' },
          { min: 1, max: 60, message: '长度在 1 到 60 个字符', trigger: 'blur' },
        ],
        groupShortName: [
          { required: true, message: '请输入集团简称', trigger: 'blur' },
          { min: 1, max: 60, message: '长度在 1 到 60 个字符', trigger: 'blur' },
        ],
      },
      isEdit: false,
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      channelApi
        .getDefaultCompany()
        .then(res => {
          this.form = Object.assign(this.form, res);
          this.protoData = this.$lodash.clone(this.form);
        })
        .catch(err => {
          console.log(err);
        });
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        channelApi
          .updateCompanyInfo(this.form)
          .then(res => {
            this.msgSuccess('操作成功');
            this.isEdit = false;
            this.form = res;
          })
          .catch(err => {
            console.log(err);
          });
      });
    },
    resetForm(formName) {
      // this.$confirm('是否取消该操作？', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning',
      // })
      //   .then(() => {})
      //   .catch(() => {});
      this.isEdit = false;
      this.form = this.$lodash.clone(this.protoData);
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      });
    },
    /** LOGO上传图片操作 */
    afterUpload_logo(res) {
      this.form.logo = res;
    },
    /** Favicon上传图片操作 */
    afterUpload_ico(res) {
      this.form.favicon = res;
    },
  },
};
</script>
<style scoped>
.el-input {
  width: 260px;
}
</style>
