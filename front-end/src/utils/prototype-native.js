/*eslint no-extend-native: ["error", { "exceptions": ["Array"] }]*/
function getLabelByValue(list, value, valueString = 'value', labelString = 'label') {
  if (Array.isArray(list)) {
    const FIND_ITEM = list.find(item => {
      const VALUE = item[valueString];
      // 兼容 item.value为数组的情况
      return VALUE == value || (Array.isArray(VALUE) && VALUE.some(n => n == value));
    });
    if (FIND_ITEM) return FIND_ITEM[labelString];
  }
  return value;
}
Array.prototype.bjGet = function(value, valueString = 'value', labelString = 'label') {
  return getLabelByValue(this, value, valueString, labelString);
};
Array.prototype.bjIncludes = function(value, valueKey) {
  return this.some(e => {
    if (valueKey) {
      return e[valueKey] == value;
    }
    return e == value;
  });
};
/**
 *
 * @param {*} val //要查找的值
 * @param {*} key // 如果数组元素是一个object，需要提供对应的key
 */
Array.prototype.remove = function(val, key) {
  let index;
  if (typeof key == 'string') {
    index = this.findIndex(e => e[key] == val);
  } else {
    index = this.indexOf(val);
  }
  if (index > -1) {
    this.splice(index, 1);
  }
};
