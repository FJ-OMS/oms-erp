/**
 * @author zhengxinquan
 * @description  从api或缓存中获取值集
 * @调用 await this.$store.dispatch('values/getValueList', 'SCHL.CHANNEL_TYPE');
 *             <el-option
              v-for="(item, index) in SCHL_SHOP_TYPE"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
 */
import { commonApi } from '@/api';
import { LOV_CODE } from '@/utils/constants';

const getDefaultState = () => {
  const TEMP = {};
  LOV_CODE.forEach(item => {
    TEMP[item.value.replace(/\./g, '_')] = [];
  });
  return Object.assign(TEMP, {
    // 非值集——API列表
    BJ_EXECUTOR_ID: [], // 执行器列表  BJ_EXECUTOR_ID
    BJ_EXECUTABLE_ID: [], // 可执行定义列表  BJ_TENANTS
    BJ_TENANTS: [], // 租户列表  BJ_EXECUTABLE_ID
    BJ_LANGUAGE: [], // 语言  BJ_LANGUAGE
    BJ_INSTALL_SERVICES: [], // 安装服务  BJ_INSTALL_SERVICES
    BJ_TAGS: [], // 标签  BJ_TAGS
    BJ_AFTER_REFUND_ONLY_REASONS: [], // 售后原因 仅退款 BJ_AFTER_REFUND_ONLY_REASONS
    BJ_AFTER_REFUND_GOODS_REASONS: [], // 售后原因 退货退款 BJ_AFTER_REFUND_GOODS_REASONS
  });
};
const state = getDefaultState();

const getMutations = () => {
  const obj = {};
  Object.keys(state).forEach(key => {
    const SET_KEY = 'SET_' + key;
    obj[SET_KEY] = (state, v) => {
      state[key] = v;
    };
  });
  return obj;
};

const mutations = getMutations();

const actions = {
  getValueList({ commit, state }, POST_KEY) {
    let isCache = true;
    if (typeof POST_KEY == 'object') {
      // 当 isCache = false 时，刷新值集的缓存
      POST_KEY = POST_KEY.POST_KEY;
      isCache = POST_KEY.isCache;
    }
    const KEY = POST_KEY.replace(/\./g, '_');
    const SET_KEY = 'SET_' + KEY;
    if (state[KEY].length && isCache) {
      return state[KEY];
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getLovsValue({
          lovCode: POST_KEY,
        })
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              label: Object.prototype.hasOwnProperty.call(e, 'label') ? e.label : e.meaning,
              ...e,
            };
          });
          commit(SET_KEY, data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  getLanguagesList({ commit, state }, isCache) {
    if (state.BJ_LANGUAGE.length && !isCache) {
      return state.BJ_LANGUAGE;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getLanguages({})
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              value: e.code,
              label: e.name,
              ...e,
            };
          });
          commit('SET_BJ_LANGUAGE', data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  getExecutorList({ commit, state }, isCache) {
    if (state.BJ_EXECUTOR_ID.length && !isCache) {
      return state.BJ_EXECUTOR_ID;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getExecutorList({ status: 'ONLINE' })
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              value: e.executorId,
              label: e.executorName,
              ...e,
            };
          });
          commit('SET_BJ_EXECUTOR_ID', data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  getExecutableList({ commit, state }, isCache) {
    if (state.BJ_EXECUTABLE_ID.length && !isCache) {
      return state.BJ_EXECUTABLE_ID;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getExecutableList({})
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              value: e.executorId,
              label: e.executorName,
              ...e,
            };
          });
          commit('SET_BJ_EXECUTABLE_ID', data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  getTenants({ commit, state }, isCache) {
    if (state.BJ_TENANTS.length && !isCache) {
      return state.BJ_TENANTS;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getTenants({})
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              value: e.tenantId,
              label: e.tenantName,
              ...e,
            };
          });
          commit('SET_BJ_TENANTS', data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  getInstallServices({ commit, state }, isCache) {
    if (state.BJ_INSTALL_SERVICES.length && !isCache) {
      return state.BJ_INSTALL_SERVICES;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getInstallServices({})
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              value: e.installServiceCode,
              label: e.name,
              ...e,
            };
          });
          commit('SET_BJ_INSTALL_SERVICES', data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  getTags({ commit, state }, isCache) {
    if (state.BJ_TAGS.length && !isCache) {
      return state.BJ_TAGS;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getTags()
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              value: e.labelCode,
              label: e.textContent,
              ...e,
            };
          });
          commit('SET_BJ_TAGS', data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  getAfterRefundOnlyReasons({ commit, state }, isCache) {
    if (state.BJ_AFTER_REFUND_ONLY_REASONS.length && !isCache) {
      return state.BJ_AFTER_REFUND_ONLY_REASONS;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getAfterSalesReasons({ type: 0 })
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              value: e.reasonCode,
              label: e.reasonDescribe,
              ...e,
            };
          });
          commit('SET_BJ_AFTER_REFUND_ONLY_REASONS', data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
  getAfterRefundGoodsReasons({ commit, state }, isCache) {
    if (state.BJ_AFTER_REFUND_GOODS_REASONS.length && !isCache) {
      return state.BJ_AFTER_REFUND_GOODS_REASONS;
    }
    return new Promise((resolve, reject) => {
      commonApi
        .getAfterSalesReasons({ type: 1 })
        .then(res => {
          let list = [];
          if (Array.isArray(res)) {
            list = res;
          } else {
            list = res.content || [];
          }
          const data = list.map(e => {
            return {
              value: e.reasonCode,
              label: e.reasonDescribe,
              ...e,
            };
          });
          commit('SET_BJ_AFTER_REFUND_GOODS_REASONS', data);
          resolve(data);
        })
        .catch(err => {
          console.error(err);
          reject(err);
        });
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
