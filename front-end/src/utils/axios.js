// import Vue from 'vue';
import axios from 'axios';
// import vueAxios from 'vue-axios';
import store from '@/store';
import { getToken } from '@/utils/auth';
import { VUE_APP_BASE_API } from '@/utils/index';

/** axios通用配置 */
axios.defaults.timeout = 30000;
axios.defaults.baseURL = VUE_APP_BASE_API();

/** http request 拦截器 */
axios.interceptors.request.use(
  config => {
    // do something
    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['X-Token'] = getToken();
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  },
);

// http response 拦截器
axios.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 401 清除token信息并跳转到登录页面
          window.location.href = error.response.headers.location;
      }
    }
    return Promise.reject(error.response.data);
  },
);

// Vue.use(vueAxios, axios); // Vue.axios/this.axios/this.$http使用axios，一次封装方便协作规范

/** 2021-9-8 13:57:49 线上npm build失败，先禁用vue-axios
 *  This dependency was not found:
 *  * vue-axios in ./src/utils/axios.js
 *  To install it, you can run: npm install --save vue-axios
 *  */

export default axios;
