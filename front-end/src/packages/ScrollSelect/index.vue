<template>
  <el-select
    ref="select"
    v-model.trim="id"
    class="full-width"
    value-key="value"
    :popper-class="classID"
    :placeholder="remote && (!list || list.length == 0) ? noDataText : placeholder"
    :multiple="multiple"
    :disabled="disabled"
    filterable
    clearable
    :size="size"
    :collapse-tags="collapseTags"
    :remote="remote"
    :remote-method="remoteMethod"
    :no-data-text="noDataText"
    :loading="loading"
    @clear="remoteMethod"
    @blur="onBlur"
    @change="onChange"
  >
    <!--
    默认显示的选项，用于回写数据，不可选择
    已经过滤 与 下方list重复的数据
   -->
    <el-option
      v-for="(item, index) in defaultOptionsFilter"
      :key="'defaultOptionsFilter_' + index"
      :label="item.label"
      :value="item.value"
      :disabled="item.disabled"
    />

    <!--
       特殊处理 :  placeholder="remote && (!list || list.length == 0) ? '暂无数据' : placeholder"
       原   因 :  当 remote = true，且 list =[] 时，点击select，走的是搜索远程数据的逻辑（认为你要在input中输入内容），不会显示组件自带的"暂无数据""
       -->
    <el-option
      v-for="(item, index) in list"
      :key="index"
      :label="item.label"
      :value="item.value"
      :disabled="item.disabled"
    />
  </el-select>
</template>
<script>
/**
 *
 * 前置插件:ScrollLoad;
 * 按关键词搜索: remote=true ; searchKey='name' ;
 */
const DEFAULT_PAGE = 1;
export default {
  name: 'ScrollSelect',
  props: {
    value: {
      type: [String, Number, Array],
      default: null,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    pageSize: {
      type: Number,
      default: 10,
    },
    size: {
      type: String,
      default: 'mini',
    },
    /** 多选时是否将选中值按文字的形式展示 */
    collapseTags: {
      type: Boolean,
      default: false,
    },
    placeholder: {
      type: String,
      default: '请选择',
    },
    // 按关键词搜索
    remote: {
      type: Boolean,
      default: true,
    },
    searchKey: {
      type: String,
      default: 'name',
    },
    /* *** 接口 */
    api: Function,
    resKey: {
      type: String,
      default: 'content',
    },
    optionLabel: {
      type: String,
      default: 'name',
    },
    optionValue: {
      type: String,
      default: 'id',
    },
    // 表示禁用的key，当item[optionDisabled] == true 时，，此选项禁用
    optionDisabled: {
      type: String,
      default: '',
    },
    // 表示可用的key，当item[optionEnabled] == false 时，，此选项禁用
    optionEnabled: {
      type: String,
      default: '',
    },
    // 默认传参
    defaultParams: {
      type: Object,
      default() {
        return {};
      },
    },
    noDataText: {
      type: String,
      default: '无数据',
    },
    // 默认添加的选项
    defaultOptions: {
      type: [Array, Object],
      default() {
        return [];
      },
    },
  },
  data() {
    return {
      id: null,
      list: [],
      ScrollLoad: null,
      searchWord: null, // 远程搜索，关键词
      loading: false,
    };
  },
  computed: {
    classID() {
      return (
        'bj-scroll-select-' +
        Number(Math.random().toString().substr(3, 12) + Date.now())
          .toString(36)
          .substr(0, 8)
          .toUpperCase()
      );
    },
    // 过滤LIST里已有的选项
    defaultOptionsFilter() {
      const DEFAULT_OPTIONS = Array.isArray(this.defaultOptions)
        ? [...this.defaultOptions]
        : [{ ...this.defaultOptions }];
      return DEFAULT_OPTIONS.map(e => {
        const disabled = Object.prototype.hasOwnProperty.call(e, 'disabled') ? e.disabled : true;
        const label = e.label || e[this.optionLabel];
        const value = e.value || e[this.optionValue];
        return {
          ...e,
          label,
          value,
          disabled,
        };
      }).filter(e => e.label && this.list.every(ee => ee.value != e.value));
    },
  },
  watch: {
    value: {
      handler(v) {
        this.id = v;
      },
      immediate: true,
    },
    id(v) {
      this.$emit('input', v);
    },
    defaultParams(o, n) {
      if (JSON.stringify(o) !== JSON.stringify(n)) {
        this.remoteMethod();
      }
    },
    list: {
      handler(o, n) {
        // console.log('-+-', o, n);
      },
      deep: true,
    },
  },
  mounted() {
    const el = document.querySelector(
      `.${this.classID} .el-select-dropdown__wrap.el-scrollbar__wrap`,
    );
    this.ScrollLoad = new this.$ScrollLoad(this.loadFun, {
      el: el,
      page: DEFAULT_PAGE,
      autoLoad: true,
    });
  },
  methods: {
    remoteMethod(query) {
      this.searchWord = query;
      this.resetSearch();
      this.loadFun();
    },
    onBlur() {
      this.$emit('focus');
    },
    onChange(v) {
      if (Array.isArray(v)) {
        const ITEM = this.list.filter(e => v.includes(e[this.optionValue]));
        this.$emit('change', v, ITEM || []);
      } else {
        const ITEM = this.list.find(e => e[this.optionValue] === v);
        this.$emit('change', v, ITEM || {});
      }
    },
    resetSearch() {
      this.ScrollLoad.init();
    },
    loadFun() {
      const CURRENT_PAGE = this.ScrollLoad.page();
      const API_DATA = { page: CURRENT_PAGE, size: this.pageSize, ...this.getQueryParams() };

      API_DATA['BjAjaxLoadingHide'] = true;

      if (this.searchWord) API_DATA[this.searchKey] = this.searchWord;
      this.loading = true;
      this.api(API_DATA).then(res => {
        this.loading = false;
        // 不是规范格式的返回值处理
        if (this.resKey) {
          const resKeyArray = this.resKey.split('/');
          resKeyArray.forEach((k, i) => {
            res = res && res[k];
          });
        }

        const LIST = (res || []).map(e => {
          const DISABLED = this.optionDisabled
            ? !!e[this.optionDisabled]
            : this.optionEnabled
            ? !e[this.optionEnabled]
            : false;
          return {
            ...e,
            label: e[this.optionLabel],
            value: e[this.optionValue],
            disabled: DISABLED,
          };
        });
        if (CURRENT_PAGE === 1) {
          this.list = LIST;
        } else {
          this.list = this.list.concat(LIST);
        }

        this.ScrollLoad.maxPage(res.totalPages || res.pages | res.total || res.length); // 最大页码，控制滚动加载
        this.ScrollLoad.page(true); // 下一页
      });
    },
    getQueryParams() {
      const conditionObj = {};
      // 基础搜索
      for (const key in this.defaultParams) {
        if (Object.prototype.hasOwnProperty.call(this.defaultParams, key)) {
          const value = this.defaultParams[key];
          if (
            key == 'tenantId' ||
            (value !== undefined && value != null && value !== '' && value.length !== 0)
          ) {
            conditionObj[key] = this.defaultParams[key];
          }
        }
      }
      // console.log(this.defaultParams);
      return conditionObj;
    },
  },
};
</script>
