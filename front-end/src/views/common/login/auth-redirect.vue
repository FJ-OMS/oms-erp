<template>
  <div v-loading.fullscreen.lock="fullscreenLoading" class="login-container">
    <div class="title">
      <div>电商中心管理后台</div>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        width="48px"
        height="60px"
        viewBox="0 0 24 30"
        style="enable-background:new 0 0 50 50"
        xml:space="preserve"
      >
        <rect x="0" y="9.22656" width="4" height="12.5469" fill="#FF6700">
          <animate
            attributeName="height"
            attributeType="XML"
            values="5;21;5"
            begin="0s"
            dur="0.6s"
            repeatCount="indefinite"
          />
          <animate
            attributeName="y"
            attributeType="XML"
            values="13; 5; 13"
            begin="0s"
            dur="0.6s"
            repeatCount="indefinite"
          />
        </rect>
        <rect x="10" y="5.22656" width="4" height="20.5469" fill="#FF6700">
          <animate
            attributeName="height"
            attributeType="XML"
            values="5;21;5"
            begin="0.15s"
            dur="0.6s"
            repeatCount="indefinite"
          />
          <animate
            attributeName="y"
            attributeType="XML"
            values="13; 5; 13"
            begin="0.15s"
            dur="0.6s"
            repeatCount="indefinite"
          />
        </rect>
        <rect x="20" y="8.77344" width="4" height="13.4531" fill="#FF6700">
          <animate
            attributeName="height"
            attributeType="XML"
            values="5;21;5"
            begin="0.3s"
            dur="0.6s"
            repeatCount="indefinite"
          />
          <animate
            attributeName="y"
            attributeType="XML"
            values="13; 5; 13"
            begin="0.3s"
            dur="0.6s"
            repeatCount="indefinite"
          />
        </rect>
      </svg>
    </div>
  </div>
</template>

<script>
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
import { setToken, removeToken } from '@/utils/auth';
import { VUE_APP_BASE_API } from '@/utils/index';
export default {
  name: 'Auth',
  data() {
    return {
      fullscreenLoading: false,
    };
  },
  created() {
    console.log('========= VUE_APP_CONFIG  ======', process.env);

    this.fullscreenLoading = true; // 全屏加载
    NProgress.start(); // 进度条加载
    const DEFAULT_ACCESS_TOKEN = process.env.VUE_APP_ACCESS_TOKEN;
    const { access_token } = this.$route.query; // 获取access_token
    const ACCESS_TOKEN = access_token || DEFAULT_ACCESS_TOKEN;
    console.log('========= ACCESS_TOKEN  ======', ACCESS_TOKEN);
    console.log('=======window.location========', window.location.href);
    if (!ACCESS_TOKEN) {
      console.log('========= 没有token直接重定向到中心登录页  ======', ACCESS_TOKEN);
      // 没有token直接重定向到中心登录页
      const authApiHost = VUE_APP_BASE_API();
      const clientId = process.env.VUE_APP_CLIENT_ID;
      const redirectUri = encodeURIComponent(
        `${window.location.origin}/#/login?BJtime=${Math.random()}`,
      );
      const urlHref = `${authApiHost}/oauth/oauth/authorize?response_type=token&client_id=${clientId}&redirect_uri=${redirectUri}`;
      console.log('========= 中心登录页 是 ======', urlHref);
      window.location.replace(urlHref);
      removeToken(); // 移除token
      return;
    }
    console.log('========= 有token存储token  进入首页 ======', ACCESS_TOKEN);
    this.$store.dispatch('user/setToken', ACCESS_TOKEN);
    setToken(ACCESS_TOKEN); // 有的话存储token
    // 增加请求用户信息的接口
    NProgress.done();
    this.$message.success(`登录成功 `);

    this.$store.dispatch('user/getInfo').then(res => {
      console.log('auth-redirect----user/getInfo');
      console.log('ACCESS_TOKEN', ACCESS_TOKEN);
      console.log('res', res);
      NProgress.done();
      this.$router.replace({ path: '/' }); // 进入首页
    });
    this.$router.replace({ path: '/' }); // 进入首页
  },
  methods: {},
};
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-tabs__item {
    color: white;
    &:hover,
    &.is-active {
      color: #4c83ff;
    }
    &.is-focus {
      border: none;
      outline: none;
    }
  }
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
.vercode {
  ::v-deep .el-form-item__content {
    display: flex;
  }
}
</style>
