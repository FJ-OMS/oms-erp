import axios from './axios.js';
// import Vue from 'vue';
import router from '@/router';
import { Message } from 'element-ui';
import store from '@/store';
import { getToken } from '@/utils/auth';
import _ from 'lodash';

function sleep(time) {
  return new Promise(resolve => setTimeout(resolve, time));
}

const CONTENT_TYPES = {
  1: 'application/json',
  2: 'multipart/form-data',
  3: 'application/x-www-form-urlencoded',
};
const RESPONSE_TYPES = {
  0: 'text',
  1: 'json',
  2: 'blob',
  3: 'buffer',
};
// console.log('http1', Vue.prototype.$http.options);
// console.log('axios', axios);
// console.log('aa', Vue.prototype.$http.prototype);
// // create an axios instance

const service = axios.create({
  // withCredentials: true, // send cookies when cross-domain requests
});

// // request interceptor
service.interceptors.request.use(
  config => {
    if (!config.loadingHide) store.dispatch('app/addLoadingCount');
    if (getToken()) {
      config.headers['Authorization'] = 'Bearer ' + getToken();
    }
    // console.log(config);
    return config;
  },
  error => {
    // do something with request error
    // console.error(error); // for debug
    return Promise.reject(error);
  },
);

// // response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  async response => {
    const config = response && response.config;

    await sleep(config.loadingMore);

    if (config.loadingMore) console.log('主动延迟' + config.loadingMore + 'ms', config.url);

    if (!config.loadingHide) store.dispatch('app/addLoadingCount', false);

    const OLD_RESPONSE_DATA = response.data;
    let NEW_RESPONSE_DATA = null;

    if (
      (response.status === 204 || response.status === 200) &&
      (typeof OLD_RESPONSE_DATA === 'string' || typeof OLD_RESPONSE_DATA === 'number')
    ) {
      NEW_RESPONSE_DATA = {
        failed: false,
        message: OLD_RESPONSE_DATA || '操作成功',
      };
    }

    if (
      (response.status === 204 || response.status === 200) &&
      typeof OLD_RESPONSE_DATA === 'boolean'
    ) {
      NEW_RESPONSE_DATA = {
        failed: !OLD_RESPONSE_DATA,
        message: OLD_RESPONSE_DATA ? '操作成功' : 'Waring',
      };
    }

    const res = NEW_RESPONSE_DATA || OLD_RESPONSE_DATA || response;
    /**
     * 统一处理 ： 接口连接正常，但 res.failed==true 的情况
     */
    if (res && res.failed && !config.errorHide) {
      Message({
        message: (res && res.message) || 'api error', // error.message
        type: 'error',
        duration: 2 * 1000,
      });
    }

    return res;
    // if the custom code is not 0, it is judged as an error.
    // if (res.code === null || (res.code && res.code !== 0)) {
    //   if (res.code === 100008005 && res.data && Array.isArray(res.data) && res.data.length > 0) {
    //     Message({
    //       message: res.data[0].msg || 'Error',
    //       type: 'error',
    //       duration: 5 * 1000,
    //     });
    //   } else {
    //     Message({
    //       message: res.message || 'Error',
    //       type: 'error',
    //       duration: 5 * 1000,
    //     });
    //   }
    //   // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
    //   if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
    //     // to re-login
    //     MessageBox.confirm(
    //       'You have been logged out, you can cancel to stay on this page, or log in again',
    //       'Confirm logout',
    //       {
    //         confirmButtonText: 'Re-Login',
    //         cancelButtonText: 'Cancel',
    //         type: 'warning',
    //       },
    //     ).then(() => {
    //       store.dispatch('app/user/resetToken').then(() => {
    //         location.reload();
    //       });
    //     });
    //   }
    //   if (store.getters.globalPartialLoading) {
    //     setTimeout(() => {
    //       store.dispatch('app/toggleGlobalLoading', false);
    //     }, 700);
    //   }
    //   return Promise.reject(new Error(res.message || 'Error'));
    // } else {
    //   if (store.getters.globalPartialLoading) {
    //     setTimeout(() => {
    //       store.dispatch('app/toggleGlobalLoading', false);
    //     }, 700);
    //   }

    //   return res;
    // }
  },
  error => {
    const config = error && error.config;
    if (config && !config.loadingHide) store.dispatch('app/addLoadingCount', false);
    console.log(error); // for debug
    // if (store.getters.globalPartialLoading) {
    //   setTimeout(() => {
    //     store.dispatch('app/toggleGlobalLoading', false);
    //   }, 700);
    // }

    if (error.message.includes('timeout')) {
      // 判断请求异常信息中是否含有超时timeout字符串

      Message({
        message: '请求服务器超时，请刷新重试', // error.message
        type: 'error',
        duration: 2 * 1000,
      });
      return Promise.reject({
        failed: true,
        message: '',
      });
    }

    if (error && error.response && error.response.status) {
      console.log(error.response.status);
      switch (error.response.status) {
        case 500:
        case 503:
          // do something...
          Message({
            message: `请求失败，请稍候重试(${error.response.status})`,
            type: 'error',
            duration: 5 * 1000,
          });
          break;
        case 404:
          // do something...
          Message({
            message: `请求资源不存在(${error.response.status})`,
            type: 'error',
            duration: 5 * 1000,
          });
          break;
        case 403:
          // do something...
          Message({
            message: `服务器拒绝或者禁止了您的访问(${error.response.status})`,
            type: 'error',
            duration: 5 * 1000,
          });
          break;
        case 401:
          // do something...
          if (!process.env.VUE_APP_ACCESS_TOKEN) {
            Message({
              message: '请重新登录', // error.message
              type: 'error',
              duration: 2 * 1000,
            });
            setTimeout(() => {
              store.dispatch('user/resetInfo').then(() => {
                router.replace({
                  path: '/login',
                });
              });
            }, 1000);
          }

          break;
        default:
          // do something...
          // Message({
          //   message: '服务器开小差了~', // error.message
          //   type: 'error',
          //   duration: 5 * 1000,
          // });
          break;
      }
    } else {
      // Message({
      //   message: '服务器开小差了~', // error.message
      //   type: 'error',
      //   duration: 5 * 1000,
      // });
    }
    return Promise.reject({
      failed: true,
      message: 'Api Http Status Code : ' + (error.response && error.response.status),
    });
  },
);

export function ajax(method = 'post', url, options) {
  // console.log(`%c ajax url: ${url}`, 'color:blue;');
  // console.log(options.data);

  method = method.toLowerCase();
  options.data = options.data || {};
  let requestType = (options.type || '').toLowerCase();
  // data参数指定隐藏loading动画
  const BjAjaxLoadingHide = options.data.BjAjaxLoadingHide;
  delete options.data.BjAjaxLoadingHide;
  const opts = {
    url,
    method,
    errorHide: !!options.errorHide,
    loadingHide: !!options.loadingHide || BjAjaxLoadingHide, // 此请求隐藏loading动画
    loadingMore: options.loadingMore || 0, // 此请求延迟loading，主动延迟返回（后端异步的情况）
    headers: {
      'Content-type': CONTENT_TYPES[options.cType || '1'],
    },
    responseType: RESPONSE_TYPES[options.rType || '0'],
  };
  /**
   * 兼容rest风格（请求参数拼接在路径上）
   */
  let restfulUrl = '';
  if (url[url.length - 1] === '/') {
    // rest 风格的get 请求 需要api文件的url 最后拼接上/
    let restAPI = Object.values(options.data); // 取参数value数组
    if (typeof options.data === 'string') {
      restAPI = [options.data];
    }
    restfulUrl = opts.url + restAPI.join('/'); // 把参数拼接在url后面
    requestType = 'restful';
  }
  /**
   * 没有指定请求的类型，自动判断请求方式
   */
  if (!requestType) {
    if (method === 'get') {
      requestType = 'query';
    } else {
      requestType = 'body';
    }
  }
  if (requestType === 'query') {
    opts.params = options.data;
  }
  if (requestType === 'body') {
    opts.data = options.data;
  }
  if (requestType === 'form' && options.data) {
    const formData = new FormData();
    Object.keys(options.data).forEach(key => {
      formData.append(key, options.data[key]);
    });
    opts.data = formData;
  }
  if (requestType === 'restful') {
    opts.url = restfulUrl;
  }

  /** 特定奇葩的传参方式 */
  if (options.data && options.data.BjBody) {
    opts.data = options.data.BjBody;
  }
  if (options.data && options.data.BjQuery) {
    opts.params = options.data.BjQuery;
  }

  // console.log('options.opts', opts);
  return service(opts);
}

export function generate(config) {
  const map = {};
  const items = config.items;
  for (let i = 0, len = items.length; i < len; i++) {
    map[items[i].key] = function(data) {
      /**
       * 把data里的值 替换到url上{}里，并删除data对应key
       */
      let tempUrl = (items[i].prefix || config.prefix || '') + items[i].url;
      const REG = /\{(.+?)\}/g;
      const __matchKey = tempUrl.match(REG); // 带{}花括号

      // 链接{}花括号参数处理
      if (Array.isArray(__matchKey)) {
        const matchKey = __matchKey.map(e => e.replace('{', '').replace('}', '')); // 去{}花括号
        matchKey.forEach((key, index) => {
          const __key = __matchKey[index];
          let __value;
          // {}花括号 从data中取值（如果有data.key的话）
          if (data && Object.prototype.hasOwnProperty.call(data, key)) {
            __value = data[key];
            tempUrl = tempUrl.replace(__key, __value);
            delete data[key];
          } else {
            // data 没有 给 organizationId、tenantId 赋值，从store中获取，默认为0租户
            if (key === 'organizationId' || key === 'tenantId') {
              __value = store.getters.tenantId || 0;
              tempUrl = tempUrl.replace(__key, __value);
            } else {
              console.error(`Can't find ${key} from the ${items[i].method} data`, data);
            }
          }
        });
      }
      // 默认值
      if (
        data &&
        Object.prototype.hasOwnProperty.call(data, 'tenantId') &&
        data.tenantId == 'default'
      ) {
        data.tenantId = store.getters.tenantId || 0;
      }
      if (
        data &&
        Object.prototype.hasOwnProperty.call(data, 'organizationId') &&
        data.organizationId == 'default'
      ) {
        data.organizationId = store.getters.tenantId || 0;
      }
      /** END */

      /**
       *  TODO: 从0开始的分页page */
      if (data && Object.prototype.hasOwnProperty.call(data, 'page') && data.page > 0) {
        data.page = data.page - 1;
      }
      /** END */
      return ajax(items[i].method, tempUrl, {
        data,
        ...(_.omit(items[i], ['key', 'url', 'method', 'prefix']) || {}),
      });
    };
  }
  return map;
}

export default {};
