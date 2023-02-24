<template>
  <div class="login">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginType === 'password' ? passwordRules : codeRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <el-tabs v-model.trim="loginType" :stretch="true">
        <el-tab-pane label="账号密码" name="password" style="padding-top: 20px">
          <el-form-item prop="name">
            <el-input
              ref="name"
              v-model.trim="loginForm.name"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="账号 / 手机号 / 邮箱"
              name="name"
              type="text"
              tabindex="1"
              auto-complete="on"
              size="medium"
            >
              <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input
              :key="passwordType"
              ref="password"
              v-model.trim="loginForm.password"
              :maxlength="MAX_LENGTH.DEFAULT"
              :type="passwordType"
              placeholder="密码"
              name="password"
              tabindex="2"
              auto-complete="on"
              @keyup.enter.native="handlePasswordLogin"
            >
              <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
            </el-input>
            <span class="show-pwd" @click="showPwd">
              <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
            </span>
          </el-form-item>

          <el-button
            :loading="loading"
            type="primary"
            style="width: 100%; margin-bottom: 30px"
            @click.native.prevent="handlePasswordLogin"
            >登录</el-button
          >
        </el-tab-pane>
        <el-tab-pane label="手机验证码" name="code" style="padding-top: 20px">
          <el-form-item prop="phone">
            <el-input
              ref="phone"
              v-model.trim="loginForm.phone"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="手机号码"
              name="phone"
              type="text"
              tabindex="1"
              auto-complete="on"
            >
              <svg-icon slot="prefix" icon-class="phone" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>

          <el-form-item prop="code">
            <el-input
              ref="phoneCode"
              v-model.trim="loginForm.phoneCode"
              type="text"
              placeholder="验证码"
              name="phoneCode"
              tabindex="2"
              :maxlength="MAX_LENGTH.CODE"
              auto-complete="on"
              style="width: 63%"
              @keyup.enter.native="handleCodeLogin"
            >
              <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
            </el-input>
            <div class="login-code">
              <el-button plain :disabled="sendCodeDisabled" @click.native.prevent="sendCode">{{
                codeText
              }}</el-button>
            </div>
          </el-form-item>
          <el-button
            :loading="loading"
            type="primary"
            style="width: 100%; margin-bottom: 30px"
            @click.native.prevent="handleCodeLogin"
            >登录</el-button
          >
        </el-tab-pane>
      </el-tabs>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2021 飞骥科技 All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
const PAUSE_SECOND = 60; // 短信重新发送间隔
const isPhone = (rule, value, callback) => {
  if (!/^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\d{8}$/.test(value)) {
    return callback(new Error('请输入正确的手机号码!'));
  } else {
    callback();
  }
};
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        name: '',
        password: '',
        phone: '',
        phoneCode: '',
      },
      codeText: '发送验证码',
      loginType: 'password',
      secondInterval: null,
      second: PAUSE_SECOND,
      sendCodeDisabled: false,
      passwordRules: {
        name: [
          {
            required: true,
            trigger: 'blur',
            message: '账号不能为空！',
          },
        ],
        password: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入密码！',
          },
        ],
      },
      codeRules: {
        phone: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入手机号！',
          },
          {
            trigger: 'blur',
            validator: isPhone,
          },
        ],
        phoneCode: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入验证码！',
          },
        ],
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
    };
  },
  watch: {
    loginType: function(val) {
      this.$nextTick(() => {
        this.$refs.loginForm.resetFields();
      });
    },
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  methods: {
    /**
     * 发送验证码
     */
    sendCode() {
      if (!this.loginForm.phone) {
        this.msgError('手机号不能为空！');
        return false;
      }
      // userApi.sendCode({ mobile: this.loginForm.phone, codeType: 0, expire: 60 }).then(response => {
      //   this.$message.success('发送成功！')
      //   this.sendCodeDisabled = true
      //   this.secondInterval = setInterval(this.secondDoLoop, 1000)
      // })
    },
    /**
     * 重新发送验证码倒计时
     */
    secondDoLoop() {
      this.second--;
      if (this.second > 0) {
        this.codeText = this.second + '秒后可重新获取';
      } else {
        clearInterval(this.secondInterval); // 清除js定时器
        this.sendCodeDisabled = false;
        this.codeText = '发送验证码';
        this.second = PAUSE_SECOND; // 重置时间
      }
    },
    /**
     * 查看密码
     */
    showPwd() {
      this.passwordType = this.passwordType === '' ? 'password' : '';
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    /**
     * 账号密码登录
     */
    handlePasswordLogin() {
      this.$refs.loginForm.validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.loading = true;
        this.$store
          .dispatch('user/login', this.loginForm)
          .then(() => {
            this.$router.push({
              path: this.redirect || '/',
            });
            this.loading = false;
          })
          .catch(() => {
            this.loading = false;
          });
      });
    },
    /**
     * 手机号验证码登录
     */
    handleCodeLogin() {
      this.$refs.loginForm.validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.loading = true;
        this.$store
          .dispatch('user/loginByCode', this.loginForm)
          .then(() => {
            this.$router.push({
              path: this.redirect || '/',
            });
            this.loading = false;
          })
          .catch(() => {
            this.loading = false;
          });
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
$dark_gray: #889aa4;

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url('../../assets/image/login-background.jpg');
  background-size: cover;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 42px;

    input {
      height: 42px;
      padding-right: 35px;
    }
  }

  .input-icon {
    height: 42px;
    width: 14px;
    margin-left: 2px;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.show-pwd {
  position: absolute;
  right: 10px;
  top: 2px;
  font-size: 16px;
  color: $dark_gray;
  cursor: pointer;
  user-select: none;
}

.login-code {
  width: 33%;
  height: 42px;
  float: right;

  button {
    height: 42px;
  }
}
</style>
