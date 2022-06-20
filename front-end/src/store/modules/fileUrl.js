/**
 * @author zhengxinquan
 * @description  从api或缓存中获取授权的图片
 * @调用 await this.$store.dispatch('values/getValueList', 'SCHL.CHANNEL_TYPE');
 *             <el-option
              v-for="(item, index) in SCHL_SHOP_TYPE"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
 */
import { settingApi } from '@/api';

const DEFAULT_EXPIRES = 1 * 60 * 60;
const DEFAULT_BUCKET_NAME = process.env.VUE_APP_UPLOAD_BUCKET_NAME;
const getDefaultState = () => {
  return {
    FILES: [
      {
        bucketName: '',
        url: '',
        key: '',

        // new Date().getTime()/1000
        create: '', // 秒

        expires: '', // 秒
        fileTokenUrl: '', //
      },
    ],
  };
};
const state = getDefaultState();

const mutations = {
  ADD_FILES: (state, FILE) => {
    const index = state.FILES.findIndex(e => {
      if (FILE.url) {
        return e.bucketName == FILE.bucketName && e.url == FILE.url;
      }
      if (FILE.key) {
        return e.bucketName == FILE.bucketName && e.key == FILE.key;
      }
      return false;
    });
    if (index == -1) {
      state.FILES.push(FILE);
    } else {
      state.FILES.splice(index, 1, FILE);
    }
  },
};

const actions = {
  getFileTokenUrl({ commit, state }, { url, bucketName, expires }) {
    const EXPIRES = expires || DEFAULT_EXPIRES;
    const BUCKET_NAME = bucketName || DEFAULT_BUCKET_NAME;
    const URL = url;
    const finder_file = state.FILES.find(e => {
      const NOW_TIME = new Date().getTime() / 1000;
      const TIME_FLAG = e.create + e.expires > NOW_TIME;
      return e.bucketName == BUCKET_NAME && e.url == URL && TIME_FLAG;
    });

    return new Promise((resolve, reject) => {
      if (finder_file) {
        resolve(finder_file.fileTokenUrl);
      } else {
        // 去授权
        settingApi
          .getFileUrlByUrl({
            bucketName: BUCKET_NAME,
            url: URL,
            expires: EXPIRES,
            // 授权有效时长(单位秒)
          })
          .then(res => {
            if (res.failed !== true) {
              let url = res.fileTokenUrl || res.message || res;
              if (url.indexOf('http') !== 0) {
                url = 'http://' + url;
              }
              const FILE = {
                bucketName: BUCKET_NAME,
                url: URL,
                key: '',
                create: new Date().getTime() / 1000,
                expires: EXPIRES, // 秒
                fileTokenUrl: url,
              };
              commit('ADD_FILES', FILE);
              resolve(url);
            } else {
              reject(url);
            }
          });
      }
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
