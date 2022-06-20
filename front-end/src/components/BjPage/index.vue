<template>
  <div class="bj-page">
    <bj-form
      ref="BjForm"
      :label-position="labelPosition"
      :show-search="cptShowSearch"
      :query-params="queryParams"
      :inline="inline"
      :col-span="colSpan"
      :search-btn-span="searchBtnSpan"
      :label-width="labelWidth"
      :search-btn-hide="searchBtnHide"
      :reset-btn-hide="resetBtnHide"
      :btn-search-style="btnSearchStyle"
      :btn-reset-style="btnResetStyle"
      :rules="rules"
      @handleQuery="onSearch"
      @resetQuery="onResetQuery"
    >
      <template slot="baseForm">
        <slot name="baseForm" />
      </template>
      <template slot="seniorForm">
        <slot name="seniorForm" />
      </template>

      <template slot="beforeSearchBtn">
        <slot name="beforeSearchBtn" />
      </template>

      <template slot="startBtnGroup">
        <slot name="startBtnGroup" />
      </template>
      <template slot="endBtnGroup">
        <slot name="endBtnGroup" />
      </template>
    </bj-form>
    <slot name="tableTop" />
    <right-toolbar
      v-if="!rightToolbarHide"
      :show-search.sync="showSearch"
      :has-query-params="'{}' !== JSON.stringify(queryParams)"
      @queryTable="fetchData"
      @showSetting="showSetting"
    >
      <template slot="tableOperation">
        <slot
          name="tableOperation"
          :notSelected="notSelected"
          :notSingle="notSingle"
          :tableData="tableData"
        />
      </template>
    </right-toolbar>
    <bj-table
      ref="BjTable"
      :loading="loading"
      :list="Array.isArray(filterTableData) ? filterTableData : tableData"
      :selection="selection"
      :custom-selection="customSelection"
      :radio="radio"
      :serial="serial"
      :stripe="stripe"
      :border="border"
      :height="height"
      :serial-width="serialWidth"
      :paging-info="pagingInfo"
      :page-sizes="pageSizes"
      :row-style="rowStyle"
      :row-class-name="rowClassName"
      :cell-class-name="cellClassName"
      :row-key="rowKey"
      :tree-props="treeProps"
      :lazy="lazy"
      :load="load"
      :page-data-json="pageDataJson"
      :tag-name="tagName"
      :show-tags="showTags"
      :tag-tree-show-child-only="tagTreeShowChildOnly"
      :tree-select-auto-parent="treeSelectAutoParent"
      :tag-x="tagX"
      :tags-closable="tagsClosable"
      :no-auto-height="noAutoHeight"
      :auto-scroll="autoScroll"
      :is-pagination="isPagination"
      :span-method="spanMethod"
      :show-summary="showSummary"
      :show-search="showSearch"
      :summary-method="summaryMethod"
      :default-sort="defaultSort"
      :handle-selectable="handleSelectable"
      @handleRadioChange="handleRadioChange"
      @handleSelect="handleSelect"
      @handleSelectAll="handleSelectAll"
      @handleSelectionChange="handleSelectionChange"
      @handleTagClose="handleTagClose"
      @handleAllTagsClose="handleAllTagsClose"
      @handleExpandTableSelect="handleExpandTableSelect"
      @handleExpandTableSelectAll="handleExpandTableSelectAll"
      @handleExpandTableSelectionChange="handleExpandTableSelectionChange"
      @pagination="handleSizeChange"
      @header-dragend="handleHeaderDragend"
      @header-contextmenu="handleHeaderContextmenu"
      @sort-change="sortChange"
      @header-click="headerClick"
      @setPageDataJson="setPageDataJson"
    >
      <template slot="tableColumn">
        <slot name="tableColumn" />
      </template>

      <template slot="paginationLeft">
        <slot name="paginationLeft" />
      </template>

      <template slot="paginationRight">
        <!-- <el-tooltip
          class="item"
          effect="dark"
          placement="left"
          content="点击设置当前表格的最大高度"
        >
          <el-button circle icon="el-icon-set-up" @click="showSettingHeight" />
        </el-tooltip> -->
      </template>

      <template slot="paginationBottom">
        <slot name="paginationBottom" />
      </template>
    </bj-table>
    <!-- 弹窗 -->
    <!-- <el-dialog
      title="设置当前表格的最大高度"
      :visible.sync="settingHeightVisible"
      width="30%"
      :before-close="closeSettingHeight"
    >
      <el-slider
        v-model="h"
        :min="BJ_TABLE_MINI_HEIGHT"
        :max="1000"
        @input="setTableHeight"
      ></el-slider>
    </el-dialog> -->
  </div>
</template>
<script>
import { PAGE_SIZES, BJ_TABLE_MINI_HEIGHT } from '@/utils/constants';
export default {
  name: 'BjPage',
  filters: {
    phoneFilter(val) {
      if (val) {
        const reg = /^(.{3}).*(.{4})$/;
        return val.replace(reg, '$1****$2');
      }
    },
  },
  props: {
    // 表单域标签的位置，如果值为 left 或者 right 时，则需要设置 label-width
    labelPosition: {
      type: String,
      default: 'right',
    },
    // 表单域标签的后缀
    labelSuffix: {
      type: String,
      default: '',
    },
    btnSearchStyle: {
      type: Object,
      default: () => {
        return { text: '搜索', type: 'primary', icon: 'el-icon-search' };
      },
    },
    btnResetStyle: {
      type: Object,
      default: () => {
        return { text: '重置', type: 'default', icon: 'el-icon-refresh' };
      },
    },
    /* 表单数据对象 */
    queryParams: {
      type: Object,
      default: () => {
        return {};
      },
    },
    /** 表单校验对象 */
    rules: {
      type: Object,
      default: () => {
        return {};
      },
    },
    /* 行内表单模式 */
    inline: {
      type: Boolean,
      default: false,
    },
    /** 搜索重置按钮的宽度样式 */
    searchBtnSpan: {
      type: Number,
      default: 0,
    },
    /** formItem的宽度样式 */
    colSpan: {
      type: Number,
      default: 6,
    },
    searchBtnHide: {
      type: Boolean,
      default: false,
    },
    resetBtnHide: {
      type: Boolean,
      default: false,
    },
    rightToolbarHide: {
      type: Boolean,
      default: false,
    },
    /* 表单域标签的宽度，例如 '50px'。作为 Form 直接子元素的 form-item 会继承该值。支持 auto */
    labelWidth: {
      type: String,
      default: '120px',
    },
    size: {
      type: String,
      default: 'mini',
    },
    // 表格多选
    selection: {
      type: Boolean,
      default: false,
    },
    customSelection: {
      type: Boolean,
      default: false,
    },
    // 表格 单选
    radio: {
      type: Boolean,
      default: false,
    },
    // 是否显示border
    border: {
      type: Boolean,
      default: true,
    },
    // 是否显示stripe
    stripe: {
      type: Boolean,
      default: true,
    },
    // 是否显示序号
    serial: {
      type: Boolean,
      default: false,
    },
    serialWidth: {
      type: [Number, String],
      default: null,
    },
    // table高度
    height: {
      type: [Number, String],
      default: null,
    },
    autoFetch: {
      type: Boolean,
      default: true,
    },

    getAction: Function,
    // list值集模式，不分页
    isList: {
      type: Boolean,
      default: false,
    },
    // 参与排序的key
    sort: {
      type: String,
      default: '',
    },
    // 排序-反序
    reverse: {
      type: Boolean,
      default: false,
    },
    // 点击重置后，搜索数据前 执行的方法 。返回 Promise
    beforeResetSearch: Function,

    // 搜索数据前 执行的方法 。返回 Promise
    beforeQuerySearch: Function,

    // 返回值res所在key
    res: {
      type: String,
      default: '',
    },
    rowStyle: Function,
    rowClassName: Function,
    cellClassName: Function,
    rowKey: {
      type: [String, Function],
      default: 'id',
    },
    treeProps: {
      type: Object,
    },
    lazy: {
      type: Boolean,
      default: false,
    },
    load: Function,
    /** 一个方法，返回新的tableData，支持异步 Promise  */
    resetData: Function,
    /** 用区别选中行的key，一般取id; 如果需要多个key共同确定，中间用/分隔 */
    rowKeys: {
      type: String,
      default: null,
    },
    /** 树状时 */
    tagTreeShowChildOnly: {
      type: Boolean,
      default: false,
    },
    /** 树状时 ,只显示1级数据作为tag*/
    treeSelectAutoParent: {
      type: Boolean,
      default: false,
    },
    /** 显示的tag名称字段 */
    tagName: {
      type: String,
      default: 'name', // 默认按name skuName spuName wareName realName groupName
    },
    /** 是否以tag形式在表格底部显示选中项 */
    showTags: {
      type: Boolean,
      default: false,
    },
    /** tag 以 【tagName x 数量】  聚合*/
    tagX: {
      type: Boolean,
      default: false,
    },
    /** tags 可以关闭*/
    tagsClosable: {
      type: Boolean,
      default: true,
    },
    /** 禁止自动计算table高度 */
    noAutoHeight: {
      type: Boolean,
      default: false,
    },
    autoScroll: {
      type: Boolean,
      default: true,
    },
    /** 默认禁用的项，仅支持 id数组，不是row data  ； 支持 多选 和 单选*/
    disabled: {
      type: Array,
      default: () => {
        return [];
      },
    },
    checkMethod: Function,
    hideSearch: {
      type: Boolean,
      default: false,
    },
    // 是否显示分页组件
    isPagination: {
      type: Boolean,
      default: true,
    },
    getCustomIndeterminate: Function,
    spanMethod: Function,
    showSummary: {
      type: Boolean,
      default: false,
    },
    summaryMethod: Function,
    defaultSort: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      settingHeightVisible: false,
      // 是否显示表格加载动画
      loading: true,
      // 显示搜索条件
      showSearch: true,

      // 表格数据
      tableData: [],
      filterTableData: null,

      // 用于禁用: disable="notSingle"
      notSingle: true, // true 选中>1个，false 选中单个
      // 用于禁用: disable="notSelected"
      notSelected: true, // true 选中0个，false  选中>0个

      // 是否更新table
      // tableReset: true,
      // table高度
      autoTableHeight: 480,

      pagingInfo: {
        // 分页信息
        size: PAGE_SIZES[0],
        page: 1,
        total: 0,
      },
      pageSizes: PAGE_SIZES,
      /** 源数据： 多选的数据 {key是页码，value是指定页的选中数据} */
      pageDataJson: {
        default: [],
      },
      /** 默认选中的项  ； 支持 多选 和 单选  */
      DEFAULT_SELECTED_ROWS: [], // 不变  // pageDataJson['default']: [...DEFAULT_SELECTED_ROWS], //跟随 删除操作 变动

      sortableTableCode: '',
      BJ_TABLE_MINI_HEIGHT,
      h: BJ_TABLE_MINI_HEIGHT,
    };
  },
  computed: {
    cptRowKeys() {
      return this.rowKeys || this.rowKey;
    },
    cptShowSearch() {
      return this.showSearch && !this.hideSearch;
    },
  },
  watch: {
    // 'pagingInfo.total'() {
    //   this.$nextTick(() => {
    //     this.h = Number(localStorage.getItem(this.sortableTableCode) || BJ_TABLE_MINI_HEIGHT);
    //     if (this.h) {
    //       this.$refs.BjTable.setTableHeight(this.h);
    //     } else {
    //       this.$refs.BjTable.setTableHeight();
    //     }
    //   });
    // },
    tableData: {
      handler(val) {
        this.$emit('handleTableDataChange', val);
      },
      deep: true,
    },
  },
  created() {},
  mounted() {
    // 第一次加载 默认值  ？？？ 待修正
    this.pageDataJson = { default: [...this.DEFAULT_SELECTED_ROWS] };
    this.loading = false;
    if (this.autoFetch) {
      this.fetchData();
    }
  },
  methods: {
    // closeSettingHeight() {
    //   this.settingHeightVisible = false;
    // },
    // showSettingHeight() {
    //   this.h = Number(localStorage.getItem(this.sortableTableCode) || BJ_TABLE_MINI_HEIGHT);
    //   this.settingHeightVisible = true;
    // },
    // setSortTableCodeAndHeight(CODE, HEIGHT) {
    //   this.sortableTableCode = CODE;
    //   this.setTableHeight(HEIGHT);
    // },
    // setTableHeight(v) {
    //   this.$nextTick(() => {
    //     // 将此列表的自定义高度储存在本地缓存
    //     localStorage.setItem(this.sortableTableCode, v);
    //     // 设置 bjTable 的 max-height
    //     this.$refs.BjTable.setTableHeight(v);
    //   });
    // },
    // 前端过滤筛选数据，建议当返回值不分页时使用
    // 由父级调用此函数，例：src\views\center-business\finance\checking\action\index.vue
    setFilter(NEW_TABLE_DATA) {
      console.log('filterTableData', NEW_TABLE_DATA);
      if (Array.isArray(NEW_TABLE_DATA)) {
        this.filterTableData = [...NEW_TABLE_DATA];
      } else {
        this.filterTableData = null;
      }
    },
    /** 设置禁止选中的条件 */
    handleSelectable({ row, index }) {
      // 仅支持 this.cptRowKeys 是单个key的情况
      if (typeof this.checkMethod == 'function') {
        return this.checkMethod(row);
      }
      return !this.disabled.includes(row[this.cptRowKeys]);
    },
    setDefaultSelectedRows(d) {
      console.log('setDefaultSelectedRows', d);
      this.DEFAULT_SELECTED_ROWS = [].concat(d);
      this.pageDataJson = { default: [...this.DEFAULT_SELECTED_ROWS] };
      if (this.tableData.length) this.selectedDataByPage();
      this.$nextTick(() => {
        // 根据 pageDataJson 回写选中的数据
        console.log(
          ' 根据 pageDataJson 回写选中的数据',
          JSON.parse(JSON.stringify(this.pageDataJson)),
        );
        this.$refs.BjTable.doHandleSelectionChange();
      });
    },
    /**
     * 分页改变事件
     */
    handleSizeChange({ page, size }) {
      this.pagingInfo.size = size;
      this.pagingInfo.page = page;
      this.fetchData();
    },
    /** 搜索按钮操作 */
    onSearch() {
      this.fetchData(1);
    },

    /** 重置按钮操作 */
    onResetQuery() {
      this.pageDataJson = { default: [...this.DEFAULT_SELECTED_ROWS] };
      if (typeof this.beforeResetSearch == 'function') {
        this.beforeResetSearch().then(() => {
          this.onSearch();
        });
      } else {
        this.onSearch();
      }
    },
    /** 清空选择 */
    clearChange() {
      this.$refs.BjTable.clearChange();
    },
    /**
     * pageIndex
     */
    fetchData(pageIndex) {
      if (!this.getAction) {
        this.loading = false;
        console.error('获取数据失败，因为对应的getAction没有设置');
        return;
      }

      // 清空数据
      // this.tableData = [];
      this.pagingInfo.page = pageIndex || this.pagingInfo.page || 1;

      this.loading = true;

      /** TODO:临时处理 loadingHide=true; 不使用api的全局loading，使用组件内部的表格loading */
      let conditionObj = Object.assign({ BjAjaxLoadingHide: true }, this.getQueryParams());
      if (!this.isList) {
        /** TODO:临时处理 pageSize currentPage入参 */
        const PAGE_TEMP = {
          pageSize: this.pagingInfo.size,
          currentPage: this.pagingInfo.page - 1,
        };
        /** 合并条件 */
        conditionObj = Object.assign(conditionObj, this.pagingInfo, PAGE_TEMP);
      }

      if (typeof this.beforeQuerySearch == 'function') {
        this.beforeQuerySearch(conditionObj).then(resConditionObj => {
          this.doAction(resConditionObj);
        });
      } else {
        this.doAction(conditionObj);
      }
    },
    // 请求数据
    doAction(conditionObj) {
      this.getAction(conditionObj)
        .then(async res => {
          this.loading = false;
          // 回调
          this.$emit('afterFetchOriginalData', res);
          // 不是规范格式的返回值处理
          if (this.res) {
            const RES_LIST = this.res.split('/');
            RES_LIST.forEach(key => {
              res = res[key] || res;
            });
          }

          // 分页模式
          if (!this.isList && res && res.content && Array.isArray(res.content)) {
            this.tableData = res.content;
            this.pagingInfo.total = res.totalElements || res.content.length;
          }
          // List值集模式
          if (this.isList && res && Array.isArray(res)) {
            this.tableData = res;
            this.pagingInfo.total = 0;
          }
          // 重新处理tableData
          if (typeof this.resetData == 'function') {
            const RES_TEMP = this.resetData(this.tableData);
            console.log('RES_TEMP', RES_TEMP);
            if (Array.isArray(RES_TEMP)) {
              // RES_TEMP 是一个数组
              this.tableData = [...RES_TEMP];
            } else {
              // RES_TEMP 是一个异步的 Promise
              this.tableData = await RES_TEMP;
            }
          }
          // 排序
          if (this.sort) {
            const n = this.reverse ? -1 : 1;
            this.tableData = this.tableData.sort((a, b) => {
              return (a[this.sort] - b[this.sort]) * n;
            });
          }
          // 回调
          this.$emit('afterFetchData', res);
          // 回写选中数据
          this.selectedDataByPage();
        })
        .catch(error => {
          this.tableData = [];
          if (error.message) this.$message.error(`page:获取数据列表失败 ${error.message || ''}`);
          this.loading = false;
        });
    },
    /**
     * 获取请求的参数
     * @param pageIndex
     */
    getQueryParams() {
      const conditionObj = {};
      // 基础搜索
      for (const key in this.queryParams) {
        if (Object.prototype.hasOwnProperty.call(this.queryParams, key)) {
          const value = this.queryParams[key];
          if (
            key == 'tenantId' ||
            (value !== undefined && value != null && value !== '' && value.length !== 0)
          ) {
            conditionObj[key] = this.queryParams[key];
          }
        }
      }
      // // 添加排序条件
      // if (this.sortInfo) {
      //   conditionObj.sort = this.sortInfo;
      // }
      // // 添加自定义条件
      // Object.assign(conditionObj, this.getActonWhere);
      return conditionObj;
    },
    // 单选
    handleRadioChange(selection, row) {
      this.$emit('handleRadioChange', selection, row);
      this.pageDataJson = {};
      this.setPageDataJson([...selection]);
    },
    handleSelect(selection, row) {
      this.$emit('handleSelect', selection, row);
      this.setPageDataJson([...selection]);
    },
    handleSelectAll(selection) {
      this.$emit('handleSelectAll', selection);
      this.setPageDataJson([...selection]);
    },
    /** 多选操作 */
    handleSelectionChange(data) {
      this.notSingle = data.notSingle;
      this.notSelected = data.notSelected;
      this.$emit('handleSelectionChange', data);
      this.setPageDataJson([...data.selection]);
    },
    setPageDataJson(page, selection) {
      if (Array.isArray(page)) {
        selection = page;
        page = this.pagingInfo.page;
      }
      this.$set(this.pageDataJson, page || this.pagingInfo.page, [...selection]);
    },
    /** ******************** 多选  start ****************************** */
    // 一个方法： 根据cptRowKeys，从SELECTED中查找ROW
    findRow(data, row, type = 'find') {
      if (!row) return null;
      return data[type](selectedItem =>
        this.cptRowKeys.split('/').every(key => selectedItem[key] === row[key]),
      );
    },
    // 关闭tag，即反选数据;
    handleTagClose(tag, index) {
      if (this.selection) {
        // 1.如果是当前页，则多选框反选
        if (tag.BJ_TAG_PAGE == this.pagingInfo.page) {
          const row = this.findRow(this.tableData, tag);
          this.$refs.BjTable.toggleRowSelection(row, false);
        }
        // 2.从this.pageDataJson数据中清除此tag
        const FIND_INDEX = this.findRow(this.pageDataJson[tag.BJ_TAG_PAGE], tag, 'findIndex');
        if (FIND_INDEX >= 0) this.pageDataJson[tag.BJ_TAG_PAGE].splice(FIND_INDEX, 1);
      }
      if (this.radio) {
        this.$refs.BjTable.radioValue = null;
        this.pageDataJson = {
          default: [],
        };
      }
      this.$nextTick(() => {
        // 根据 pageDataJson 回写选中的数据
        this.$refs.BjTable.doHandleSelectionChange();
      });
    },
    handleAllTagsClose() {
      //  1.用于多选表格，清空用户的选择
      this.$refs.BjTable.clearChange();
      //  2.从this.pageDataJson数据中清除
      this.pageDataJson = {
        default: [],
      };
      this.$nextTick(() => {
        // 根据 pageDataJson 回写选中的数据
        this.$refs.BjTable.doHandleSelectionChange();
      });
    },
    // 手动选择的回调
    handleSelectClick(selection, row) {
      this.setPageDataJson([...selection]);
    },
    selectedDataByPage() {
      console.log('this.pageDataJson', this.pageDataJson);
      /** 当前页 赋 默认值 */
      const key = this.pagingInfo.page;
      const value = [];
      if (!this.pageDataJson[key] || this.pageDataJson[key].length == 0) {
        this.tableData.forEach(row => {
          const FIND_INDEX = this.findRow(this.pageDataJson.default || [], row, 'findIndex');
          if (FIND_INDEX > -1) {
            value.push({ ...row, isBjDefaultRow: true });
            this.pageDataJson.default.splice(FIND_INDEX, 1);
          }
        });
        this.setPageDataJson(key, [...value]);
      }

      /** 单选，回写已经选择的数据 */
      if (this.radio) {
        this.$nextTick(() => {
          const SELECTED = this.pageDataJson[this.pagingInfo.page] || [];
          if (Array.isArray(SELECTED) && SELECTED.length == 1) {
            console.warn('单选，回写已经选择的数据');
            this.$refs.BjTable.$refs.ElTable.setRadioRow(SELECTED[0]);
            this.$refs.BjTable.doHandleSelectionChange(SELECTED);
          }
        });
        return;
      }
      /** 多选，回写已经选择的数据 */
      if (this.selection) {
        console.log('多选，回写已经选择的数据', this.pageDataJson);

        /** 回写 */
        this.$nextTick(() => {
          if (!this.$refs.BjTable) {
            // console.error('selectedDataByPage Error');
            return;
          }
          // 选中项
          const SELECTED = this.pageDataJson[this.pagingInfo.page] || [];

          this.tableData.forEach((row, index) => {
            const IS_FIND = this.findRow(SELECTED, row);
            // 选中项打勾
            this.$refs.BjTable.toggleRowSelection(row, !!IS_FIND);
          });

          // this.$refs.BjTable.doHandleSelectionChange();
        });
      }
    },
    /** ********************* end *********************** */
    /** START 以下配合 ElementTableSortable 插件 */
    // 当点击表格上方设置按钮的时候会触发该事件
    showSetting() {
      this.$emit('showSetting');
    },
    // 当拖动表头改变了列的宽度的时候会触发该事件
    handleHeaderDragend() {
      this.$emit('header-dragend', ...arguments);
    },
    handleHeaderContextmenu() {
      this.$emit('header-contextmenu', ...arguments);
    },
    sortChange() {
      this.$emit('sort-change', ...arguments);
    },
    headerClick() {
      this.$emit('header-click', ...arguments);
    },
    /** END */
    // 展开子表的回调： 批量选中/不选中子表所有数据
    trySelectChildrenRefs() {
      this.$refs.BjTable.trySelectChildrenRefs(...arguments);
    },
    /** ****  展开行 子表格 Start ********/
    // 选择
    handleExpandTableSelect(arg) {
      this.$emit('handleExpandTableSelect', arg);
    },
    // 全选
    handleExpandTableSelectAll(arg) {
      this.$emit('handleExpandTableSelectAll', arg);
    },
    // 多选框选中数据
    handleExpandTableSelectionChange(arg) {
      this.$emit('handleExpandTableSelectionChange', arg);
    },
    /** ****  展开行 子表格 End ********/
    /** 选中某个数据 */
    toggleRowSelection() {
      this.$refs.BjTable.toggleRowSelection(...arguments);
    },
  },
};
</script>
<style lang="scss" scoped>
.contextmenu {
  margin: 0;
  background: #fff;
  z-index: 3000;
  position: absolute;
  list-style-type: none;
  padding: 5px 0;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 400;
  color: #333;
  box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
  li {
    margin: 0;
    padding: 7px 16px;
    cursor: pointer;
    &:hover {
      background: #eee;
    }
  }
}
</style>
