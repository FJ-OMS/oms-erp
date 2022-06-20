<template>
  <div class="app-container">
    <BjDivider>个人信息</BjDivider>
    <div class="top ml0">
      <div>
        <BjKeyImage
          class="avatar"
          :url="avatar || require('@/assets/image/logo-circle.png')"
          fit="cover"
        />
        <BjUpload
          ref="upload"
          class="upload mt20"
          auto-upload
          accept="image/png,image/jpeg"
          :accept-list="['image/jpeg', 'image/png']"
          :limit-size="100"
          @afterUpload="afterUpload"
        >
          <el-button plain>修改头像</el-button>
        </BjUpload>
      </div>
      <div class="form">
        <div class="form-item">
          <span class="form-label">登录账号：</span>
          <div>{{ userInfo.loginName }}</div>
        </div>
        <div class="form-item">
          <span class="form-label">名称：</span>
          <div>
            <div v-show="!show">
              {{ name }} <i class="el-icon-edit ml0" @click="showInput(name)" />
            </div>
            <div v-show="show">
              <el-input
                v-model="editName"
                :maxlength="MAX_LENGTH.DEFAULT"
                class="input"
                clearable
              />
              <span class="m20" @click="saveName">保 存</span>
              <span class="m20" @click="concalName">取 消</span>
            </div>
          </div>
        </div>
        <div class="form-item">
          <span class="form-label">所属租户：</span>
          <div>飞骥科技</div>
        </div>
        <div class="form-item">
          <span class="form-label">有效期从：</span>
          <div>2020-12-12</div>
        </div>
      </div>
      <!-- <el-form label-width="100px">
          <el-form-item label="登录账号：">
            {{ name }}
          </el-form-item>
          <el-form-item label="名称：">
            {{ name }}
          </el-form-item>
          <el-form-item label="所属租户：">
            {{ name }}
          </el-form-item>
          <el-form-item label="有效期从：">
            {{ name }}
          </el-form-item>
        </el-form> -->
    </div>
    <el-divider />
    <BjDivider>安全设置</BjDivider>
    <div class="secure">
      <row-text
        :title="'登录密码'"
        :content="'安全性高的密码可以使账号更安全。建议您定期更换密码，设置一个包含字母，符号或数字中至少两项长度超过6位的密码。'"
        :icon="userInfo.passwordResetFlag ? 'el-icon-check' : 'el-icon-warning-outline'"
        :icon-text="userInfo.passwordResetFlag ? '已设置' : '未设置'"
        :btn-type="userInfo.passwordResetFlag ? 'success' : 'warning'"
        :btn-text="'修 改'"
        @btnClick="change"
      />
      <el-divider />
      <row-text
        :title="'手机绑定'"
        :icon="userInfo.phoneCheckFlag ? 'el-icon-check' : 'el-icon-warning-outline'"
        :icon-text="userInfo.phoneCheckFlag ? '已验证' : userInfo.phone ? '未验证' : '未设置'"
        :btn-type="userInfo.phoneCheckFlag ? 'success' : 'warning'"
        :btn-text="'绑 定'"
        @btnClick="showCheckDialog(1)"
      >
        <template v-if="userInfo.phone" slot="content">
          {{ userInfo.phoneCheckFlag ? '已绑定' : '请验证' }}手机号<el-link type="warning">
            {{ userInfo.phone | phoneFilter }}</el-link
          >（手机号可用于登录、密码找回）
        </template>
      </row-text>
      <el-divider />
      <row-text
        :title="'邮箱绑定'"
        :icon="userInfo.emailCheckFlag ? 'el-icon-check' : 'el-icon-warning-outline'"
        :icon-text="userInfo.emailCheckFlag ? '已验证' : userInfo.email ? '未验证' : '未设置'"
        :btn-type="userInfo.emailCheckFlag ? 'success' : 'warning'"
        :btn-text="'绑 定'"
        @btnClick="showCheckDialog(2)"
      >
        <template v-if="userInfo.email" slot="content">
          {{ userInfo.emailCheckFlag ? '已绑定' : '请验证' }}邮箱<el-link type="success">
            {{ userInfo.email | phoneFilter }}</el-link
          >（邮箱可用于登录、密码找回）
        </template>
      </row-text>
      <el-divider />
    </div>
    <!-- 修改密码弹出层 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="修改密码"
      :visible.sync="passDialogVisible"
      width="420px"
      :before-close="handleClose"
    >
      <el-row>
        <el-col :span="22">
          <el-form ref="passForm" :model="passForm" :rules="passRules" label-width="100px">
            <el-form-item prop="originalPassword" label="原密码">
              <el-input
                v-model="passForm.originalPassword"
                :maxlength="MAX_LENGTH.DEFAULT"
                type="password"
                placeholder="请输入原密码"
                show-password
              />
            </el-form-item>
            <el-form-item prop="password" label="新密码">
              <el-input
                v-model="passForm.password"
                :maxlength="MAX_LENGTH.DEFAULT"
                type="password"
                placeholder="请输入新密码"
                show-password
              />
            </el-form-item>
            <el-form-item prop="checkPass" label="确认新密码">
              <el-input
                v-model="passForm.checkPass"
                :maxlength="MAX_LENGTH.DEFAULT"
                type="password"
                placeholder="请输入新密码"
                show-password
              />
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitPassForm('passForm')">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 绑定手机号弹出层 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="手机绑定"
      :visible.sync="phoneDialogVisible"
      width="420px"
      :before-close="handleClose"
    >
      <el-row>
        <el-col :span="22">
          <el-form ref="phoneForm" :model="phoneForm" :rules="phoneRules" label-width="100px">
            <BjFormItemMobile
              v-model.trim="phoneForm.phone"
              prop="phone"
              label="手机号"
              required
              clearable
            />
            <el-form-item prop="code" label="验证码">
              <el-input
                v-model.trim="phoneForm.code"
                type="text"
                placeholder="验证码"
                :maxlength="MAX_LENGTH.CODE"
              >
                <template slot="append">
                  <el-button :disabled="sendCodeDisabled" @click.native.prevent="sendCode">{{
                    codeText
                  }}</el-button>
                </template>
              </el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitPhoneForm('phoneForm')">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 绑定邮箱弹出层 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="邮箱绑定"
      :visible.sync="emailDialogVisible"
      width="420px"
      :before-close="handleClose"
    >
      <el-row>
        <el-col :span="22">
          <el-form ref="emailForm" :model="emailForm" :rules="emailRules" label-width="100px">
            <el-form-item prop="email" label="邮箱">
              <el-input
                v-model="emailForm.email"
                :maxlength="MAX_LENGTH.DEFAULT"
                placeholder="请输入"
              />
            </el-form-item>
            <el-form-item prop="code" label="验证码">
              <el-input
                v-model.trim="emailForm.code"
                type="text"
                placeholder="验证码"
                :maxlength="MAX_LENGTH.CODE"
              >
                <template slot="append">
                  <el-button
                    :disabled="sendCodeDisabledEmail"
                    @click.native.prevent="sendCodeEmail"
                    >{{ codeTextEmail }}</el-button
                  >
                </template>
              </el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitEmailForm('emailForm')">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 校验密码弹出层 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="验证密码"
      :visible.sync="checkDialogVisible"
      width="420px"
      :before-close="handleClose"
    >
      <el-row>
        <el-col :span="22">
          <el-form ref="checkForm" :model="checkForm" :rules="checkRules" label-width="100px">
            <el-form-item prop="pwd" label="密码验证">
              <el-input
                v-model="checkForm.pwd"
                :maxlength="MAX_LENGTH.DEFAULT"
                type="password"
                placeholder="请输入密码"
                show-password
              />
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitCheckForm('checkForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { userApi } from '@/api';
import { mapGetters } from 'vuex';
import rowText from './components/rowText';
import BjUpload from '@/components/BjUpload/index';
export default {
  components: {
    rowText,
    BjUpload,
  },
  filters: {
    phoneFilter(val) {
      if (val) {
        const reg = /^(.{3}).*(.{4})$/;
        return val.replace(reg, '$1****$2');
      }
    },
  },
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.passForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    const validateEmail = (rule, value, callback) => {
      const reg = /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/;
      if (value === '') {
        callback(new Error('请输入邮箱地址'));
      } else {
        if (!reg.test(value)) {
          callback(new Error('请输入正确的邮箱地址'));
        } else {
          callback();
        }
      }
    };
    return {
      show: false,
      editName: '',
      // 1 绑定手机号、2 绑定邮箱
      type: '',
      sendCodeDisabled: false,
      sendCodeDisabledEmail: false,
      codeText: '发送验证码',
      codeTextEmail: '获取验证码',
      passDialogVisible: false,
      phoneDialogVisible: false,
      emailDialogVisible: false,
      checkDialogVisible: false,
      passForm: {
        organizationId: null,
        originalPassword: null,
        password: null,
        checkPass: null,
      },
      passRules: {
        originalPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
        password: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
        checkPass: [{ required: true, validator: validatePass, trigger: 'blur' }],
      },
      phoneForm: {},
      phoneRules: {
        code: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
      },
      emailForm: {},
      emailRules: {
        email: [{ required: true, validator: validateEmail, trigger: 'blur' }],
        code: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
      },
      checkForm: {},
      checkRules: {
        pwd: [{ required: true, message: '请输入密码', trigger: 'blur' }],
      },
      lastCheckKey: null, // 前置-验证密码
      captchaKeyPhone: null, // 发送验证码后
      captchaKeyEmail: null, // 发送验证码后
    };
  },
  computed: {
    ...mapGetters(['avatar', 'name', 'userInfo']),
  },
  methods: {
    showInput(name) {
      this.editName = name;
      this.show = true;
    },
    saveName() {
      this.show = false;
      this.updateMyRealName(this.editName);
      // this.msgSuccess('修改成功' + this.editName);
    },
    concalName() {
      this.show = false;
    },
    change() {
      this.passDialogVisible = true;
    },
    showCheckDialog(type) {
      this.type = type;
      /** 清空密码校验输入框 */
      this.checkForm = {};
      this.checkDialogVisible = true;
    },
    submitPassForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        const API_DATA = this.$lodash.clone(this.passForm);
        delete API_DATA.checkPass;
        this.updateMyPassword(API_DATA);
      });
    },
    // 前置密码验证
    submitCheckForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.checkMyPassword(this.checkForm.pwd, () => {
          this.type === 1 ? (this.phoneDialogVisible = true) : (this.emailDialogVisible = true);
        });
      });
    },
    // 发送手机验证码
    sendCode() {
      const reg = /^1[0-9]{10}$/;
      if (!reg.test(this.phoneForm.phone) && this.phoneForm.phone != '') {
        this.$message.warning('请输入正确的手机号码');
      } else {
        this.sendCaptchaByPhone(this.phoneForm.phone, () => {
          let time = 60;
          const timePhone = setInterval(() => {
            if (time === 0) {
              clearInterval(timePhone);
              this.sendCodeDisabled = false;
              this.codeText = '获取验证码';
            } else {
              time--;
              this.sendCodeDisabled = true;
              this.codeText = '重新获取（' + time + '）';
            }
          }, 1000);
        });
      }
    },
    // 发送邮箱验证码
    sendCodeEmail() {
      const reg = /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/;
      if (!reg.test(this.emailForm.email) && this.emailForm.email != '') {
        this.$message.warning('请输入正确的邮箱地址');
      } else {
        this.sendCaptchaByEmail(this.emailForm.email, () => {
          let time = 60;
          const timerEmail = setInterval(() => {
            if (time === 0) {
              clearInterval(timerEmail);
              this.sendCodeDisabledEmail = false;
              this.codeTextEmail = '获取验证码';
            } else {
              time--;
              this.sendCodeDisabledEmail = true;
              this.codeTextEmail = '重新获取（' + time + '）';
            }
          }, 1000);
        }).catch(err => {
          console.err(err);
        });
      }
    },
    // 验证手机
    submitPhoneForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.checkCaptchaByPhone(this.phoneForm.phone, this.phoneForm.code);
      });
    },
    // 验证邮箱
    submitEmailForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.checkCaptchaByEmail(this.emailForm.email, this.emailForm.code);
      });
    },

    handleClose(done) {
      this.passDialogVisible = false;
      this.phoneDialogVisible = false;
      this.emailDialogVisible = false;
      this.checkDialogVisible = false;
    },
    afterUpload(res) {
      this.updateMyAvatar(res);
    },
    /** 上传图片操作 */
    // handleAvatarSuccess(res, file) {
    //   console.log(res, file);
    //   // this.avatar = res.data.url;
    //   // TODO:  图片变链接
    //   this.updateMyAvatar(
    //     'http://dp.gtimg.cn/discuzpic/0/discuz_x5_gamebbs_qq_com_forum_201306_19_1249187cz567ahchxxns6x.jpg/0',
    //   );
    // },
    beforeAvatarUpload(file) {
      const isJPGOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 0.5;

      if (!isJPGOrPng) {
        this.$message.error('上传图片只能是 JPG 或者 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 500kb!');
      }
      return isJPGOrPng && isLt2M;
    },
    /** ******* API *********** */
    updateMyRealName(realName) {
      const API_DATA = { realName: realName };
      userApi.updateMyRealName(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          this.$store.dispatch('user/getInfo');
        }
      });
    },
    updateMyPassword(API_DATA) {
      userApi.updateMyPassword(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          this.handleClose();
          this.$store.dispatch('user/getInfo');
        }
      });
    },
    checkMyPassword(password, fun) {
      const API_DATA = { password: password };
      userApi
        .checkMyPassword(API_DATA)
        .then(res => {
          if (res.lastCheckKey) {
            this.lastCheckKey = res.lastCheckKey;
            fun();
          } else {
            this.lastCheckKey = null;
          }
        })
        .catch(err => {
          console.log(err);
          this.lastCheckKey = null;
        });
    },
    sendCaptchaByPhone(phone, fun) {
      const API_DATA = { lastCheckKey: this.lastCheckKey, phone: phone };
      userApi.sendCaptchaByPhone(API_DATA).then(res => {
        if (
          res.id ||
          res._token ||
          res.content ||
          Array.isArray(res) ||
          res.failed === false ||
          (res.message && res.message.includes('已发送'))
        ) {
          this.captchaKeyPhone = res.captchaKey || null;
          this.$message.success(res.message || '发送成功');
          fun();
        }
      });
    },
    sendCaptchaByEmail(email, fun) {
      const API_DATA = { lastCheckKey: this.lastCheckKey, email: email };
      userApi.sendCaptchaByEmail(API_DATA).then(res => {
        if (
          res.id ||
          res._token ||
          res.content ||
          Array.isArray(res) ||
          res.failed === false ||
          (res.message && res.message.includes('已发送'))
        ) {
          this.captchaKeyEmail = res.captchaKey || null;
          this.$message.success(res.message || '发送成功');
          fun();
        }
      });
    },
    checkCaptchaByPhone(phone, captcha) {
      const API_DATA = {
        lastCheckKey: this.lastCheckKey, // 前置密码校验返回的
        captchaKey: this.captchaKeyPhone, // 给新手机号发送验证码返回的
        captcha: captcha,
        phone: phone,
      };
      userApi.checkCaptchaByPhone(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success(res.message || '操作成功');
          this.handleClose();
          this.$store.dispatch('user/getInfo');
        }
      });
    },
    checkCaptchaByEmail(email, captcha) {
      const API_DATA = {
        lastCheckKey: this.lastCheckKey, // 前置密码校验返回的
        captchaKey: this.captchaKeyEmail, // 给新邮箱发送验证码返回的
        captcha: captcha,
        email: email,
      };
      userApi.checkCaptchaByEmail(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success(res.message || '操作成功');
          this.handleClose();
          this.$store.dispatch('user/getInfo');
        }
      });
    },
    updateMyAvatar(avatar) {
      const API_DATA = { avatar: avatar };
      userApi.updateMyAvatar(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success(res.message || '操作成功');
          this.handleClose();
          this.$store.dispatch('user/getInfo');
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.top {
  display: flex;
  margin-top: 30px;
  .avatar {
    width: 80px !important;
    height: 80px !important;
    border-radius: 50%;
  }
}
.form {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  font-size: 14px;
  margin-left: 100px;
  .form-item {
    display: flex;
    align-items: center;
    .form-label {
      display: inline-block;
      width: 100px;
    }
    .m20 {
      cursor: pointer;
    }
  }
}
.form .el-input {
  height: 26px;
  line-height: 26px;
  width: 160px;
}
</style>
