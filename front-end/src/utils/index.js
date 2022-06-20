/**
 * Created by PanJiaChen on 16/11/18.
 */
import html2pdf from 'html2pdf.js';
/**
 * Parse the time to string
 * @param {(Object|string|number)} time
 * @param {string} cFormat
 * @returns {string | null}
 */
export function parseTime(time, cFormat) {
  if (arguments.length === 0 || !time) {
    return null;
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
  let date;
  if (typeof time === 'object') {
    date = time;
  } else {
    if (typeof time === 'string') {
      if (/^[0-9]+$/.test(time)) {
        // support "1548221490638"
        time = parseInt(time);
      } else {
        // support safari
        // https://stackoverflow.com/questions/4310953/invalid-date-in-safari
        time = time.replace(new RegExp(/-/gm), '/');
      }
    }

    if (typeof time === 'number' && time.toString().length === 10) {
      time = time * 1000;
    }
    date = new Date(time);
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay(),
  };
  const time_str = format.replace(/{([ymdhisa])+}/g, (result, key) => {
    const value = formatObj[key];
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value];
    }
    return value.toString().padStart(2, '0');
  });
  return time_str;
}

/**
 * @param {number} time
 * @param {string} option
 * @returns {string}
 */
export function formatTime(time, option) {
  if (('' + time).length === 10) {
    time = parseInt(time) * 1000;
  } else {
    time = +time;
  }
  const d = new Date(time);
  const now = Date.now();

  const diff = (now - d) / 1000;

  if (diff < 30) {
    return '刚刚';
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前';
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前';
  } else if (diff < 3600 * 24 * 2) {
    return '1天前';
  }
  if (option) {
    return parseTime(time, option);
  } else {
    return (
      d.getMonth() + 1 + '月' + d.getDate() + '日' + d.getHours() + '时' + d.getMinutes() + '分'
    );
  }
}

/**
 * @param {string} url
 * @returns {Object}
 */
export function getQueryObject(url) {
  url = url == null ? window.location.href : url;
  const search = url.substring(url.lastIndexOf('?') + 1);
  const obj = {};
  const reg = /([^?&=]+)=([^?&=]*)/g;
  search.replace(reg, (rs, $1, $2) => {
    const name = decodeURIComponent($1);
    let val = decodeURIComponent($2);
    val = String(val);
    obj[name] = val;
    return rs;
  });
  return obj;
}

/**
 * @param {string} input value
 * @returns {number} output value
 */
export function byteLength(str) {
  // returns the byte length of an utf8 string
  let s = str.length;
  for (var i = str.length - 1; i >= 0; i--) {
    const code = str.charCodeAt(i);
    if (code > 0x7f && code <= 0x7ff) s++;
    else if (code > 0x7ff && code <= 0xffff) s += 2;
    if (code >= 0xdc00 && code <= 0xdfff) i--;
  }
  return s;
}

/**
 * @param {Array} actual
 * @returns {Array}
 */
export function cleanArray(actual) {
  const newArray = [];
  for (let i = 0; i < actual.length; i++) {
    if (actual[i]) {
      newArray.push(actual[i]);
    }
  }
  return newArray;
}

/**
 * @param {Object} json
 * @returns {Array}
 */
export function param(json) {
  if (!json) return '';
  return cleanArray(
    Object.keys(json).map(key => {
      if (json[key] === undefined) return '';
      return encodeURIComponent(key) + '=' + encodeURIComponent(json[key]);
    }),
  ).join('&');
}

/**
 * @param {string} url
 * @returns {Object}
 */
export function param2Obj(url) {
  const search = decodeURIComponent(url.split('?')[1]).replace(/\+/g, ' ');
  if (!search) {
    return {};
  }
  const obj = {};
  const searchArr = search.split('&');
  searchArr.forEach(v => {
    const index = v.indexOf('=');
    if (index !== -1) {
      const name = v.substring(0, index);
      const val = v.substring(index + 1, v.length);
      obj[name] = val;
    }
  });
  return obj;
}

/**
 * @param {string} val
 * @returns {string}
 */
export function html2Text(val) {
  const div = document.createElement('div');
  div.innerHTML = val;
  return div.textContent || div.innerText;
}

/**
 * Merges two objects, giving the last one precedence
 * @param {Object} target
 * @param {(Object|Array)} source
 * @returns {Object}
 */
export function objectMerge(target, source) {
  if (typeof target !== 'object') {
    target = {};
  }
  if (Array.isArray(source)) {
    return source.slice();
  }
  Object.keys(source).forEach(property => {
    const sourceProperty = source[property];
    if (typeof sourceProperty === 'object') {
      target[property] = objectMerge(target[property], sourceProperty);
    } else {
      target[property] = sourceProperty;
    }
  });
  return target;
}

/**
 * @param {HTMLElement} element
 * @param {string} className
 */
export function toggleClass(element, className) {
  if (!element || !className) {
    return;
  }
  let classString = element.className;
  const nameIndex = classString.indexOf(className);
  if (nameIndex === -1) {
    classString += '' + className;
  } else {
    classString =
      classString.substr(0, nameIndex) + classString.substr(nameIndex + className.length);
  }
  element.className = classString;
}

/**
 * @param {string} type
 * @returns {Date}
 */
export function getTime(type) {
  if (type === 'start') {
    return new Date().getTime() - 3600 * 1000 * 24 * 90;
  } else {
    return new Date(new Date().toDateString());
  }
}

/**
 * @param {Function} func
 * @param {number} wait
 * @param {boolean} immediate
 * @return {*}
 */
export function debounce(func, wait, immediate) {
  let timeout, args, context, timestamp, result;

  const later = function () {
    // 据上一次触发时间间隔
    const last = +new Date() - timestamp;

    // 上次被包装函数被调用时间间隔 last 小于设定时间间隔 wait
    if (last < wait && last > 0) {
      timeout = setTimeout(later, wait - last);
    } else {
      timeout = null;
      // 如果设定为immediate===true，因为开始边界已经调用过了此处无需调用
      if (!immediate) {
        result = func.apply(context, args);
        if (!timeout) context = args = null;
      }
    }
  };

  return function (...args) {
    context = this;
    timestamp = +new Date();
    const callNow = immediate && !timeout;
    // 如果延时不存在，重新设定延时
    if (!timeout) timeout = setTimeout(later, wait);
    if (callNow) {
      result = func.apply(context, args);
      context = args = null;
    }

    return result;
  };
}
/**
 * This is just a simple version of deep copy
 * Has a lot of edge cases bug
 * If you want to use a perfect deep copy, use lodash's _.cloneDeep
 * @param {Object} source
 * @returns {Object}
 */
export function deepClone(source) {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments', 'deepClone');
  }
  const targetObj = source.constructor === Array ? [] : {};
  Object.keys(source).forEach(keys => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = deepClone(source[keys]);
    } else {
      targetObj[keys] = source[keys];
    }
  });
  return targetObj;
}

/**
 * @param {Array} arr
 * @returns {Array}
 */
export function uniqueArr(arr) {
  return Array.from(new Set(arr));
}

/**
 * @returns {string}
 */
export function createUniqueString() {
  const timestamp = +new Date() + '';
  const randomNum = parseInt((1 + Math.random()) * 65536) + '';
  return (+(randomNum + timestamp)).toString(32);
}

/**
 * Check if an element has a class
 * @param {HTMLElement} elm
 * @param {string} cls
 * @returns {boolean}
 */
export function hasClass(ele, cls) {
  return !!ele.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}

/**
 * Add class to element
 * @param {HTMLElement} elm
 * @param {string} cls
 */
export function addClass(ele, cls) {
  if (!hasClass(ele, cls)) ele.className += ' ' + cls;
}

/**
 * Remove class from element
 * @param {HTMLElement} elm
 * @param {string} cls
 */
export function removeClass(ele, cls) {
  if (hasClass(ele, cls)) {
    const reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
    ele.className = ele.className.replace(reg, ' ');
  }
}
export const sessionStorageTagViews = {
  add: function (view) {
    const newView = {
      fullPath: view.fullPath,
      hash: view.hash,
      meta: view.meta,
      name: view.name,
      params: view.params,
      path: view.path,
      query: view.query,
    };
    const OLD_VISITED_VIEWS = this.get();
    OLD_VISITED_VIEWS.push(newView);
    sessionStorage.setItem('visitedViews', JSON.stringify(OLD_VISITED_VIEWS));
  },
  delete: function () {},
  set: function (visitedViews) {
    const VIEWS = visitedViews.map(view => {
      return {
        fullPath: view.fullPath,
        hash: view.hash,
        meta: view.meta,
        name: view.name,
        params: view.params,
        path: view.path,
        query: view.query,
      };
    });
    sessionStorage.setItem('visitedViews', JSON.stringify(VIEWS));
  },
  clear: function (params) {
    sessionStorage.setItem('visitedViews', '[]');
  },
  get: function () {
    return JSON.parse(sessionStorage.getItem('visitedViews') || '[]');
  },
};
/**
 * 数字转为带两位小数的金额显示
 * @param {string/Number} d //数字
 * @param {string/Number} n //几位小数
 * @returns {string}
 */
export function number2money(d = 0, n = 2) {
  let zero = '';
  let ten = 1;
  for (let i = 0; i < n; i++) {
    zero += '0';
    ten = ten * 10;
  }
  if (d === undefined || d === null) {
    d = 0;
  }
  if (!d) {
    d = 0;
  }
  d = Math.round(d * ten) / ten;
  const price = (d + '').split('.');
  price[1] = price[1] ? `${(price[1] + zero).substring(0, n)}` : zero;
  return price.join('.');
}

export function downloadBlob(blob, fileName) {
  if (!fileName) {
    alert('文件名不正确');
    return;
  }
  // FileReader主要用于将文件内容读入内存
  var reader = new FileReader();
  reader.readAsDataURL(blob);
  // onload当读取操作成功完成时调用
  reader.onload = function (e) {
    var a = document.createElement('a');
    a.download = fileName;
    a.href = e.target.result;
    console.log(e.target.result);
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  };
}
export function getBlobURL(blob) {
  //  var binaryData = [];
  // binaryData.push(file);
  // new Blob(binaryData, { type: 'application/zip' })
  let url = null;
  if (window.createObjectURL !== undefined) {
    // basic
    url = window.createObjectURL(blob);
  } else if (window.webkitURL !== undefined) {
    // webkit or chrome
    try {
      url = window.webkitURL.createObjectURL(blob);
    } catch (error) {
      console.log(error);
    }
  } else if (window.URL !== undefined) {
    // mozilla(firefox)
    try {
      url = window.URL.createObjectURL(blob);
    } catch (error) {
      console.log(error);
    }
  }
  return url;
}
export function openWin(url, iWidth = 1200, iHeight = 800, name = '') {
  // 转向网页的地址;
  // 网页名称，可为空;
  // 弹出窗口的宽度;
  // 弹出窗口的高度;
  // 获得窗口的垂直位置
  const iTop = (window.screen.availHeight - 30 - iHeight) / 2;
  // 获得窗口的水平位置
  const iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
  return window.open(
    url,
    name,
    'height=' +
      iHeight +
      ',,innerHeight=' +
      iHeight +
      ',width=' +
      iWidth +
      ',innerWidth=' +
      iWidth +
      ',top=' +
      iTop +
      ',left=' +
      iLeft +
      ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no',
  );
}
/**
 * 返回一个十位的随机字符串 BJ 开头
 * @returns  {string}
 */
export function randomID() {
  return (
    'BJ' +
    Number(Math.random().toString().substr(3, 12) + Date.now())
      .toString(36)
      .substr(0, 8)
      .toUpperCase()
  );
}
/**
 * offsetTop 是一个只读属性，返回当前元素相对于 offsetParent 节点顶部边界的偏移像素值。
 * 通过循环 offsetParent 读取 offsetTop ，累加，最终获得元素至文档顶部的真实距离
 * @param { Dom, String} element
 * @returns Number
 */
export function getOffsetTop(element) {
  element = typeof element == 'string' ? document.querySelector(element) : element;
  let realTop = element.offsetTop;
  let next = element.offsetParent;
  while (next !== null) {
    realTop += next.offsetTop;
    next = next.offsetParent;
  }
  return realTop;
}
/**
 * this.$scrollToFormError() 调用，this请指向vue
 * 页面滚动到表单检验报错的地方
 */
export function scrollToFormError() {
  this.$nextTick(() => {
    const DOM = document.querySelector('.el-form-item__error');
    const TOP = getOffsetTop(DOM);
    const fixedHeaderHeight = document.querySelector('.fixed-header').clientHeight || 0; // .fixed-header 高度
    const PARENT_HEIGHT = DOM.parentElement ? DOM.parentElement.clientHeight || 0 : 0; // .el-form-item__content 高度
    if (DOM && TOP) {
      document.documentElement.scrollTop = TOP - PARENT_HEIGHT - fixedHeaderHeight;
    }
  });
}
export function getFileTypeByUrl(url) {
  const TYPE_STRING = url.split('?')[0].split('.').slice(-1)[0].toLowerCase();
  const TYPE_JSON = {
    image: ['bmp', 'jpg', 'jpeg', 'gif', 'png', 'tif', 'tiff', 'tga', 'psd', 'jfif', 'webp'],
    video: ['avi', 'dv', 'mp4', 'mpeg', 'mpg', 'mov', 'wm', 'flv', 'mkv', 'ogg', 'ogv', 'webm'],
    text: [
      'txt',
      'php',
      'html',
      'htm',
      'js',
      'css',
      'rtf',
      'rtfd',
      'py',
      'java',
      'rb',
      'sh',
      'pl',
      'sql',
      'xml',
      'log',
      'docx',
    ],
    audio: ['mp3', 'mid', 'ogg', 'mpga', 'mp4a', 'wav', 'wma'],
    application: [
      'ai',
      'eps',
      'exe',
      'doc',
      'xls',
      'ppt',
      'pps',
      'pdf',
      'xml',
      'odt',
      'swf',
      'gz',
      'tgz',
      'bz',
      'bz2',
      'tbz',
      'zip',
      'rar',
      'tar',
      '7z',
    ],
  };
  return Object.keys(TYPE_JSON).find(key => TYPE_JSON[key].includes(TYPE_STRING));
}
/**
 * 排列组合
 * @returns
 */
export function cartesianProductOf() {
  return Array.prototype.reduce.call(
    arguments,
    function (a, b) {
      var ret = [];
      a.forEach(function (a) {
        b.forEach(function (b) {
          ret.push(a.concat([b]));
        });
      });
      return ret;
    },
    [[]],
  );
}
/**
 * 导出表格为 HTML、WORD、PDF、EXCEL
 * @param {String} FILE_NAME 文件名 带后缀格式
 * @param {*} HTML_OR_DATA  表格数据 / HTML字符串 / html元素
 * @param {Array} LABEL_LIST  表格的表头；  ['日期', '姓名', '地址']
 * @param {String} WORK_SHEET  excel 的 sheet名称；默认 Sheet1
 */
export function exportFile(FILE_NAME, HTML_OR_DATA, LABEL_LIST, WORK_SHEET) {
  if (!FILE_NAME) {
    console.error('exportFile need FILE_NAME');
    return;
  }
  if (FILE_NAME.indexOf('.') == -1) {
    console.error('FILE_NAME need File Type');
    return;
  }
  const TYPE = FILE_NAME.split('.').slice(-1)[0].toLowerCase();
  if (TYPE === 'html') {
    //   downloadHtml(htmlTemplate(this.$refs.table.innerHTML), 'a.html');
    downloadHtml(htmlTemplate(HTML_OR_DATA), FILE_NAME);
  }
  if (TYPE === 'docx') {
    //  downloadHtml(tableTemplate(this.tableData, ['日期', '姓名', '地址']), 'a.docx');
    downloadHtml(tableTemplate(HTML_OR_DATA, LABEL_LIST), FILE_NAME);
  }
  if (TYPE === 'pdf') {
    //  downloadHtml(this.$refs.table.innerHTML, 'aaaa.PDF');
    downloadHtml(HTML_OR_DATA, FILE_NAME);
  }
  if (TYPE === 'xlsx') {
    // downloadHtml(excelTemplate(this.tableData, ['日期', '姓名', '地址'], 'worksheet'), 'a.xlsx');
    downloadHtml(excelTemplate(HTML_OR_DATA, LABEL_LIST, WORK_SHEET), FILE_NAME);
  }
}
/**
 * 下载 html 片段，转存为文件
 * @param {String,Element} fileHtml
 * @param {String} fileName
 * @returns
 */
export function downloadHtml(fileHtml, fileName) {
  if (!fileName) {
    console.error('exportFile need fileName');
    return;
  }
  if (fileName.indexOf('.') == -1) {
    console.error('fileName need File Type');
    return;
  }
  fileName = fileName.replace(/\./g, '_' + new Date().getTime() + '.');
  // 转PDF下载
  if (fileName.split('.').slice(-1)[0].toLowerCase() === 'pdf') {
    console.log('转PDF下载');
    html2pdf(fileHtml, {
      margin: 1,
      filename: fileName,
      image: { type: 'jpeg', quality: 0.98 },
      html2canvas: { scale: 2 },
      jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' },
    });
    return;
  }
  // 其它HTML直接下载
  const a = document.createElement('a');
  const url = window.URL.createObjectURL(new Blob([fileHtml], { type: '' }));
  a.href = url;
  a.download = fileName;
  a.click();
  window.URL.revokeObjectURL(url);
}
/**
 * html模板
 * @param {String,Array} d  必填，table data / html 字符串
 * @param {Array} label 非必填，表头
 * @returns
 */
export function htmlTemplate(d, label) {
  let innerHTML = d;
  if (Array.isArray(d)) {
    innerHTML = tableTemplate(d, label);
  }
  const html = `
      <!DOCTYPE html>
      <html>
      <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width,initial-scale=1.0">
      <title>FeiJi</title>
      <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
      </head>
      <body>
      <div style="padding:16px">
      ${innerHTML}
      </div>
      </body>
      </html>`;
  return html;
}
/**
 * excel模板
 * @param {Array} data 表数据
 * @param {Array} label 表头
 * @param {String} worksheet  excel sheet 名称
 * @returns
 */
export function excelTemplate(data, label, worksheet) {
  const TABLE_HTML = tableTemplate(data, label);
  // Worksheet名
  worksheet = worksheet || 'Sheet1';
  // 下载的表格模板数据
  return `<html xmlns:o="urn:schemas-microsoft-com:office:office"
  xmlns:x="urn:schemas-microsoft-com:office:excel"
  xmlns="http://www.w3.org/TR/REC-html40">
  <head><meta charset="UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>
    <x:Name>${worksheet}</x:Name>
    <x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>
    </x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
    </head><body>${TABLE_HTML}</body></html>`;
}
/**
 * table模板
 * @param {Array} TABLE_DATA  表数据
 * @param {Array} LABEL_LIST  表头
 * @returns
 */
export function tableTemplate(TABLE_DATA, LABEL_LIST) {
  if (!TABLE_DATA) {
    console.error('tableTemplate: need table data');
    return;
  }
  if (!Array.isArray(TABLE_DATA)) {
    console.error('tableTemplate: table data must be Array');
    return;
  }
  if (!LABEL_LIST) {
    console.error('tableTemplate: need table header');
    return;
  }
  if (!Array.isArray(LABEL_LIST)) {
    console.error('tableTemplate: table header  must be Array');
    return;
  }
  // 表头
  let str =
    '<tr>' +
    LABEL_LIST.map(
      e =>
        `<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #dedede;">${e}</td>`,
    ).join('') +
    '</tr>';
  // 循环遍历，每行加入tr标签，每个单元格加td标签
  for (let i = 0; i < TABLE_DATA.length; i++) {
    str += '<tr>';
    for (const item in TABLE_DATA[i]) {
      // 增加\t为了不让表格显示科学计数法或者其他格式
      str += `<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;">${
        TABLE_DATA[i][item] + '\t'
      }</td>`;
    }
    str += '</tr>';
  }
  return `<table style="font-family: verdana,arial,sans-serif;font-size:11px;color:#333333;border-width: 1px;border-color: #666666;border-collapse: collapse;">${str}</table>`;
}

/**
 * element时间组件参数：picker-options
 * @param {Date} startDate 只当选中 startDate 以后的时间，之前的禁选
 * @returns
 */
export function disabledPickerDate(startDate) {
  const TEMP_DATE = startDate ? new Date(startDate) : new Date();
  return {
    disabledDate: time => {
      return time.getTime() < TEMP_DATE.getTime() - 8.64e7 + 1;
    },
  };
}

/**
 * element时间范围datetime range 组件参数：picker-options
 * @param {option} pickerOptions 参数，会覆盖默认的shortcuts，可传入 disabledPickerDate() ,用于禁用过去的时间
 * @returns 默认的最近时间快捷范围
 */
export function pickerOptions(option = {}) {
  return {
    firstDayOfWeek: option.firstDayOfWeek || 7,
    cellClassName: option.cellClassName || null,
    disabledDate: option.disabledDate || null,
    shortcuts: option.shortcuts || [
      {
        text: '最近一周',
        onClick(picker) {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
          picker.$emit('pick', [start, end]);
        },
      },
      {
        text: '最近一个月',
        onClick(picker) {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
          picker.$emit('pick', [start, end]);
        },
      },
      {
        text: '最近三个月',
        onClick(picker) {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
          picker.$emit('pick', [start, end]);
        },
      },
    ],
  };
}

export function pickerOption(option = {}) {
  return {
    firstDayOfWeek: option.firstDayOfWeek || 7,
    cellClassName: option.cellClassName || null,
    disabledDate: option.disabledDate || null,
    shortcuts: option.shortcuts || [
      {
        text: '今天',
        onClick(picker) {
          picker.$emit('pick', new Date());
        },
      },
      {
        text: '昨天',
        onClick(picker) {
          const date = new Date();
          date.setTime(date.getTime() - 3600 * 1000 * 24);
          picker.$emit('pick', date);
        },
      },
      {
        text: '一周前',
        onClick(picker) {
          const date = new Date();
          date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
          picker.$emit('pick', date);
        },
      },
    ],
  };
}

export function VUE_APP_BASE_API() {
  let api = process.env.VUE_APP_BASE_API;
  if (process.env.NODE_ENV === 'production') {
    // 生产环境的API动态取值（在二级域名后，加 api）,如果最终api有www的话直接去掉
    // 例1：  https://nice.domain.com  => https://niceapi.domain.com
    // 例2：  http://www.domain.com  => http://api.domain.com
    // 例2：  http://www666.domain.com  => http://666api.domain.com
    const URL_ARRAY = location.origin.split('.');
    URL_ARRAY[0] = URL_ARRAY[0] + 'api';
    api = URL_ARRAY.join('.');
    api = api.replace('www', '');
  }
  return api;
}

export function infinityNumber(n) {
  if (!n) {
    if (n === 0 || n === '0') {
      return 0;
    }
    return 999999999999999;
  }
  if (n === '不限' || n === '-') {
    return 999999999999999;
  }
  if (n === 999999999999999) {
    return '不限';
  }
  return Number(n);
}
export function isSameArray(a, b) {
  const la = a.length;
  const lb = b.length;
  if (la == lb) {
    return a.every(aa => b.some(bb => bb === aa)) && b.every(bb => a.some(aa => bb === aa));
  } else {
    return false;
  }
}

export function btnLetterAutoSpacing() {
  document.getElementsByTagName('button').forEach(btn => {
    if (btn.textContent.length == 3) {
      btn.classList.add('letter-spacing-8');
    } else if (btn.textContent.length <= 2) {
      btn.classList.add('letter-spacing-16');
    }
  });
}
// 子商品行背景着色
export function treeRowStyleFun({ row, rowIndex }, key = 'BJ_HAS_CHILD', flag = false) {
  const styleJson = {};
  if (row[key] == flag) {
    styleJson.background = '#f5f5f5';
  }
  return styleJson;
}
export function repeatArrayFun(d, times = 10000) {
  const d2 = [];
  for (let i = 0; i < times; i++) {
    d2.push(...d);
  }
  return d2;
}
