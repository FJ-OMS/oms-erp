/**
 *
 * @param {*} loadFun 加载回调函数
 * @param {*} options 配置 {}
 * @param {*} options.el          null, 滚动元素的父级
 * @param {*} options.offset:     0,    滚动计算的偏差
 * @param {*} options.autoLoad    false,首次自动加载
 * @param {*} options.page:       0,    当前页
 * @param {*} options.maxPage:    0,    最大页（page>maxPage时，不再触发loadFun）
 * @param {*} options.autoPage:   false,内部自动累加page（会在loadFun前触发）
 * @param {*} options.isEnd:      false,停止触发loadFun
 */
export default function(loadFun, options = {}) {
  const _THIS = this;
  const DEFAULT_OPTIONS = {
    log: false,
    el: null,
    offset: 0,
    autoLoad: false,
    page: 0,
    maxPage: 0,
    autoPage: false,
    isEnd: false,
  };
  _THIS.defaultOptions = Object.assign({}, DEFAULT_OPTIONS, options);
  _THIS.options = Object.assign({}, _THIS.defaultOptions, {});

  /** *debug */
  function log(v) {
    if (_THIS.options.log) console.log(v);
  }
  log(_THIS.options);

  _THIS.init = function(options) {
    _THIS.options = Object.assign({}, _THIS.defaultOptions, options || {});
  };
  /**
   *
   * @param {*} value  为数字时，设置值; 为空时，返回当前page; 为true时，累加
   * @returns
   */
  _THIS.page = function(value) {
    if (value === undefined) {
      return _THIS.options.page;
    }
    if (value === true) {
      _THIS.options.page++;
      return _THIS.options.page;
    }
    _THIS.options.page = value;
    return _THIS.options.page;
  };
  /**
   *
   * @param {*} value   设置值; 为空时，返回当前maxPage；
   * @returns
   */
  _THIS.maxPage = function(value) {
    if (value === undefined) {
      return _THIS.options.maxPage;
    }
    _THIS.options.maxPage = value;
    return _THIS.options.maxPage;
  };
  /**
   *
   * @param {*} value  设置值; 为空时，返回当前isEnd；
   * @returns
   */
  _THIS.isEnd = function(value) {
    if (value === undefined) {
      return _THIS.options.isEnd;
    }
    _THIS.options.isEnd = value;
    return _THIS.options.isEnd;
  };

  _THIS.options.el.addEventListener('scroll', function(res) {
    const TARGET = res.target;
    if (TARGET.clientHeight + TARGET.scrollTop + _THIS.options.offset >= TARGET.scrollHeight) {
      if (_THIS.isEnd()) {
        log(`ScrollLoad.js：_THIS.isEnd`);
        return;
      }

      if (_THIS.maxPage() && _THIS.page() > _THIS.maxPage()) {
        log(`ScrollLoad.js：End  By _THIS.maxPage ( ${_THIS.maxPage()} ) `);
        return;
      }

      log('加载下一页');
      if (_THIS.options.autoPage) {
        _THIS.page(true);
        log(`ScrollLoad.js：_THIS.autoPage`);
      }
      log(`ScrollLoad.js：_THIS.Page  ${_THIS.page()}/${_THIS.maxPage()} `);
      loadFun();
    }
  });
  if (_THIS.options.autoLoad) {
    setTimeout(() => {
      loadFun();
    }, 100);
  }
}
