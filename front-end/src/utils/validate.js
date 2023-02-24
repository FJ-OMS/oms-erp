/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor'];
  return valid_map.indexOf(str.trim()) >= 0;
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/;
  return reg.test(url);
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
  const reg = /^[a-z]+$/;
  return reg.test(str);
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
  const reg = /^[A-Z]+$/;
  return reg.test(str);
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
  const reg = /^[A-Za-z]+$/;
  return reg.test(str);
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export const patternEmail = /^[a-zA-Z0-9_.-]{1,30}@[a-zA-Z0-9-]{1,30}(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
export function validEmail(email) {
  // const reg =/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return patternEmail.test(email);
}
export const validatorEmail = function(rule, value, callback) {
  if (typeof value == 'number') value = String(value);
  if (!value) {
    if (rule.required) {
      callback(new Error('请输入邮箱'));
    } else {
      callback();
    }
  } else {
    if (!patternEmail.test(String(value))) {
      callback(new Error('邮箱格式错误'));
    } else {
      callback();
    }
  }
};

/**
 * @param {string} phone
 * @returns {Boolean}
 */
export const patternMobile = /^134[0-8]\d{7}$|^13[^4]\d{8}$|^14[5-9]\d{8}$|^15[^4]\d{8}$|^16[6]\d{8}$|^17[0-8]\d{8}$|^18[\d]{9}$|^19[89]\d{8}$/;
export function validPhone(phone) {
  return patternMobile.test(phone);
}

/**
 * 排除非数字和字母还有_.-其他禁止输入
 */
export const checkCode = /[^\a-\z\A-\Z0-9-_.]/g;
export const checkCodeWithComma = /[^\a-\z\A-\Z0-9-_.,]/g;
/**
 * 编码的规则
 */
export const patternCode = /^[A-Z0-9][A-Z0-9-_./]*$/;
export const patternCodeSimple = /^[A-Za-z0-9-_./]{0,50}$/;
export const validatorCodeSimple = function(rule, value, callback) {
  if (typeof value == 'number') value = String(value);
  if (!value) {
    if (rule.required) {
      callback(new Error('请输入编码'));
    } else {
      callback();
    }
  } else {
    if (!patternCodeSimple.test(String(value))) {
      callback(new Error('编码格式错误'));
    } else {
      callback();
    }
  }
};
export const validatorCodeSimpleLabel = function(rule, value, callback, label = '编码') {
  if (typeof value == 'number') value = String(value);
  if (!value) {
    if (rule.required) {
      callback(new Error('请输入' + label));
    } else {
      callback();
    }
  } else {
    if (!patternCodeSimple.test(String(value))) {
      callback(new Error(label + '格式错误'));
    } else {
      callback();
    }
  }
};
/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
  if (typeof str === 'string' || str instanceof String) {
    return true;
  }
  return false;
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]';
  }
  return Array.isArray(arg);
}
// 100以内的数字，最多两位小数
export const patternPercent = /(^[0-9]{1,2}$)|(^[0-9]{1,2}[\.]{1}[0-9]{0,2}$)/;
export function validPercent(v) {
  return patternPercent.test(v);
}

// 不能小于1
export const validatorMinOne = (rule, value, callback) => {
  if (value < 1) {
    callback(new Error(rule.message));
  } else {
    callback();
  }
};

/**
 * 账户验证规则
 */
export const validateLoginName = (rule, value, callback) => {
  if (rule.required && (!value || value === 0)) {
    callback(new Error('请输入账户'));
  } else {
    if (!/^[\u4E00-\u9FA5\uF900-\uFA2D|a-zA-Z0-9]{2,20}$/.test(value)) {
      callback(new Error('请输入正确的账户'));
    } else {
      callback();
    }
  }
};
/**
 * 手机号验证规则
 */
export const patternMobileSimple = /^1\d{10}$/;
export const validatorMobile = function(rule, value, callback) {
  if (typeof value == 'number') value = String(value);
  if (!value) {
    if (rule.required) {
      callback(new Error('请输入手机号'));
    } else {
      callback();
    }
  } else {
    if (!patternMobileSimple.test(String(value))) {
      callback(new Error('手机号格式错误'));
    } else {
      callback();
    }
  }
};
/**
 * 邮编验证规则
 */
export const patternPostCode = /\d{6}/;
export const validatorPostCode = function(rule, value, callback) {
  if (typeof value == 'number') value = String(value);
  if (!value) {
    if (rule.required) {
      callback(new Error('请输入邮编'));
    } else {
      callback();
    }
  } else {
    if (!patternPostCode.test(String(value))) {
      callback(new Error('邮编格式错误'));
    } else {
      callback();
    }
  }
};

/**
 * 区号-电话号码 验证规则
 */
export const patternPhone = /^\d{3,4}-\d{7,8}$/;
export const validatorPhone = function(rule, value, callback) {
  if (typeof value == 'number') value = String(value);
  if (!value) {
    if (rule.required) {
      callback(new Error('请输入固定电话号码'));
    } else {
      callback();
    }
  } else {
    if (!patternPhone.test(String(value))) {
      callback(new Error('固定电话号码格式错误'));
    } else {
      callback();
    }
  }
};

/**
 * 年龄 验证规则
 */
export const patternAge = /^[1-9][0-9]{0,2}$/;
export const validatorAge = function(rule, value, callback) {
  if (typeof value == 'number') value = String(value);
  if (!value) {
    if (rule.required) {
      callback(new Error('请输入年龄'));
    } else {
      callback();
    }
  } else {
    if (!patternAge.test(String(value))) {
      callback(new Error('年龄格式错误'));
    } else {
      callback();
    }
  }
};

// 密码
export const patternPassword = /^(\w){6,20}$/;
// 中文，英文字母，数字
export const validatorGoodsClassifyName = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
export const patternGoodsClassifyName = function(rule, value, callback) {
  if (!value) {
    return callback(new Error('请输入分类名称'));
  }
  if (!validatorGoodsClassifyName.test(value)) {
    callback(new Error('中文，英文字母，数字'));
  } else {
    callback();
  }
};
// 商品分类编码
export const patternGoodsClassifyCode = /^[a-zA-Z0-9]{0,20}$/;
export const validatorGoodsClassifyCode = function(rule, value, callback) {
  if (typeof value == 'number') value = String(value);
  if (!value) {
    if (rule.required) {
      callback(new Error('请输入'));
    } else {
      callback();
    }
  } else {
    if (!patternGoodsClassifyCode.test(String(value))) {
      callback(new Error('请输入不大于20位的数字及字母组合'));
    } else {
      callback();
    }
  }
};
