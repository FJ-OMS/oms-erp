<template>
  <div class="app-container">
    <BjDivider right-fixed>
      <span>密码安全策略</span>
      <template #right>
        <el-button v-permission:SettingUserAccountStrategy @click="reFresh">重 置</el-button>
        <el-button
          v-permission:SettingUserAccountStrategy
          type="primary"
          @click="submitForm('form')"
          >保 存</el-button
        >
      </template>
    </BjDivider>

    <el-form ref="form" :model="form" :rules="rules" label-width="200px" inline class="form">
      <!-- <h4>账号生成策略</h4> -->
      <!-- <el-row>
        <el-col :span="colSpan">
          <el-form-item label="账号最小长度" prop="accountPolicy.minLength">
            <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model="form.accountPolicy.minLength" placeholder="请输入" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="账号最大长度" prop="accountPolicy.maxLength">
            <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model="form.accountPolicy.maxLength" placeholder="请输入" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="最少数字数" prop="accountPolicy.minNumberSize">
            <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model="form.accountPolicy.minNumberSize" placeholder="请输入" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="border">
        <el-col :span="colSpan">
          <el-form-item label="最少小写字母数" prop="accountPolicy.minMinuscule">
            <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model="form.accountPolicy.minMinuscule" placeholder="请输入" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="最少大写字母数" prop="accountPolicy.minMajuscule">
            <el-input :maxlength="MAX_LENGTH.DEFAULT"    v-model="form.accountPolicy.minMajuscule" placeholder="请输入" />
          </el-form-item>
        </el-col>
      </el-row> -->

      <!-- <BjDivider>密码安全策略</BjDivider> -->
      <el-row>
        <el-col :span="colSpan">
          <el-form-item label="新用户默认密码" prop="originalPassword">
            <el-input
              v-model="form.originalPassword"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="最小密码长度" prop="minLength">
            <el-input
              v-model="form.minLength"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="最长密码长度" prop="maxLength">
            <el-input
              v-model="form.maxLength"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="最少小写字母数" prop="lowercaseCount">
            <el-input
              v-model="form.lowercaseCount"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="最少大写字母数" prop="uppercaseCount">
            <el-input
              v-model="form.uppercaseCount"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="最少特殊字符数" prop="specialCharCount">
            <el-input
              v-model="form.specialCharCount"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="修改密码允许与旧密码相同" prop="isPassword">
            <el-switch v-model="form.isPassword" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="密码允许与登录名相同" prop="notUsername">
            <el-switch v-model="form.notUsername" />
          </el-form-item>
        </el-col>
      </el-row>

      <BjDivider>登录安全策略</BjDivider>
      <el-row class="border">
        <el-col :span="colSpan">
          <el-form-item label="最大密码错误次数" prop="maxErrorTime">
            <el-input
              v-model="form.maxErrorTime"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="最大近期密码数" prop="notRecentCount">
            <el-input
              v-model="form.notRecentCount"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="密码错误次数启用验证码" prop="maxCheckCaptcha">
            <el-input
              v-model="form.maxCheckCaptcha"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="启用图形验证码" prop="enableCaptcha">
            <el-switch v-model="form.enableCaptcha" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="允许锁定用户" prop="enableLock">
            <el-switch v-model="form.enableLock" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="锁定时长(秒)" prop="lockedExpireTime">
            <el-input
              v-model="form.lockedExpireTime"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="PC端允许多地登录" prop="enableWebMultipleLogin">
            <el-switch v-model="form.enableWebMultipleLogin" />
          </el-form-item>
        </el-col>

        <el-col :span="colSpan">
          <el-form-item label="移动端允许多地登录" prop="enableAppMultipleLogin">
            <el-switch v-model="form.enableAppMultipleLogin" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="强制修改初始密码" prop="forceModifyPassword">
            <el-switch v-model="form.forceModifyPassword" />
          </el-form-item>
        </el-col>
        <el-col :span="colSpan">
          <el-form-item label="修改密码后重新登录" prop="enableReLogin">
            <el-switch v-model="form.enableReLogin" />
          </el-form-item>
        </el-col>

        <!-- 是否开启登录安全策略/非必填 , -->
        <el-col :span="colSpan">
          <el-form-item label="登录二次校验" prop="enableSecurity">
            <el-switch v-model="form.enableSecurity" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
import { settingApi } from '@/api';
export default {
  name: 'SettingUserAccountStrategy',
  data() {
    return {
      colSpan: 8,
      rules: {
        /** 账号生成策略表单验证 */
        // 'accountPolicy.minLength': { required: true, message: '账号最小长度', trigger: 'blur' },
        // 'accountPolicy.maxLength': { required: true, message: '账号最大长度', trigger: 'blur' },
        // 'accountPolicy.minNumberSize': { required: true, message: '最少数字数', trigger: 'blur' },
        // 'accountPolicy.minMinuscule': {
        //   required: true,
        //   message: '最少小写字母数',
        //   trigger: 'blur',
        // },
        // 'accountPolicy.minMajuscule': {
        //   required: true,
        //   message: '最少大写字母数',
        //   trigger: 'blur',
        // },

        /** 密码安全策略表单验证 */

        originalPassword: { required: true, message: '新用户默认密码', trigger: 'blur' },
        minLength: { required: true, message: '最小密码长度', trigger: 'blur' },
        maxLength: { required: true, message: '最长密码长度', trigger: 'blur' },
        lowercaseCount: { required: true, message: '最少小写字母数', trigger: 'blur' },
        uppercaseCount: { required: true, message: '最少大写字母数', trigger: 'blur' },
        specialCharCount: { required: true, message: '账号最小长度', trigger: 'blur' },

        /** 登录安全策略表单验证 */

        maxErrorTime: { required: true, message: '请输入最大密码错误次数', trigger: 'blur' },
        lockedExpireTime: { required: true, message: '请输入锁定时长', trigger: 'blur' },
      },

      form: {
        _innerMap: {},
        objectVersionNumber: 1,
        _token:
          'b4oZXzhYrKJv03uYjpXxJJNoqyPeH6W3TZhL0zelcHGhnZpNgiAGkCjJMWIS6QFztQgQ6z/MRDP5AxI02Wu5FQ==',
        id: '=ubimsK41X3ZCK_TWqAVnHw===',
        code: 'HZERO',
        name: 'HZERO平台',
        originalPassword: 'admin1234',
        minLength: 6,
        maxLength: 30,
        maxErrorTime: 5,
        digitsCount: 0,
        lowercaseCount: 0,
        uppercaseCount: 0,
        specialCharCount: 0,
        notUsername: false,
        isPassword: false,
        regularExpression: null,
        notRecentCount: 0,
        enablePassword: true,
        enableSecurity: true,
        enableLock: true,
        lockedExpireTime: 86400,
        enableCaptcha: true,
        maxCheckCaptcha: 3,
        enableWebMultipleLogin: true,
        enableAppMultipleLogin: true,
        passwordUpdateRate: 0,
        passwordReminderPeriod: 0,
        forceModifyPassword: false,
        enableReLogin: false,
        // accountPolicy: {
        //   minLength: 6,
        //   maxLength: 12,
        //   digitsCountberSize: 1,
        //   minMinuscule: 1,
        //   minMajuscule: 1,
        // },
      },
    };
  },
  mounted() {
    this.reFresh();
  },
  methods: {
    reFresh() {
      settingApi.getPasswordPolicy().then(res => {
        this.form = res || {};
      });
    },
    updatPasswordPolicy(API_DATA) {
      delete API_DATA.createdBy;
      delete API_DATA.creationDate;
      delete API_DATA.lastUpdateDate;
      delete API_DATA.lastUpdatedBy;

      delete API_DATA.organizationId;

      delete API_DATA.accountPolicy.id;
      delete API_DATA.accountPolicy.objectVersionNumber;
      delete API_DATA.accountPolicy.regularExpression;
      delete API_DATA.accountPolicy.tenantId;
      delete API_DATA.accountPolicy._token;
      // console.log(API_DATA);
      settingApi.updatPasswordPolicy(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.msgSuccess('操作成功');
          this.reFresh();
        }
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        const API_DATA = this.$lodash.cloneDeep(this.form);
        this.updatPasswordPolicy(API_DATA);
      });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>
<style scoped>
.border {
  border-bottom: 1px solid #d7dae2;
}
</style>
