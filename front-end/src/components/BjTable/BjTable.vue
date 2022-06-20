<template>
  <div class="bjTable-2022-2-23-16-56-17">
    <!-- `checked` 为 true 或 false -->
    <!--表格
    TODO:
          :row-id="rowKey"

           //中国行政区有用，需要重写
          :lazy="lazy"
           :load="load"

              :no-auto-height="noAutoHeight"
                  :show-summary="showSummary"
                     :summary-method="summaryMethod"
    -->

    <!-- serial-width:{{ serialWidth }} rowKey:{{ rowKey }}, height:{{ height }} ; cptTreeNode:{{ cptTreeNode }}<br />
    tags:<br />{{ tags.map(e => e.BJ_EBC_ID) }}<br /> -->
    <!--       :row-config="{ keyField: rowKey }"  自定义行数据唯一主键的字段名（默认自动生成）
string

_X_ROW_KEY-->
    <vxe-table
      :id="mainTableKey"
      ref="ElTable"
      :key="mainTableKey"
      v-loading="loading"
      size="mini"
      :data="list"
      :max-height="autoTableHeight"
      :height="height"
      :stripe="stripe"
      :border="border"
      :row-style="rowStyle"
      :row-class-name="rowClassName"
      :radio-config="cptRadioConfig"
      :checkbox-config="cptCheckbox"
      :tree-config="cptTreeConfig"
      :header-cell-style="$rowStyle.DEFAULT"
      :cell-class-name="cellClassName"
      :span-method="spanMethod"
      :sort-config="{ defaultSort: defaultSort }"
      :menu-config="cptMenuConfig"
      :column-config="{ resizable: true, useKey: true }"
      :row-config="{ keyField: cptRowKeys }"
      @radio-change="handleRadioChange"
      @checkbox-all="handleSelectAllEvent"
      @checkbox-change="handleSelectionChange"
      @resizable-change="handleHeaderDragend"
      @header-cell-menu="handleHeaderContextmenu"
      @sort-change="sortChange"
      @header-click="headerClick"
    >
      <vxe-column v-if="cptTreeNode && !serial" align="center" width="55" tree-node></vxe-column>
      <vxe-column
        v-if="serial && !cptTreeNode"
        type="seq"
        align="center"
        title="序号"
        :width="serialWidth || '55'"
      ></vxe-column>

      <vxe-column
        v-if="serial && cptTreeNode"
        type="seq"
        align="center"
        title="序号"
        :width="serialWidth || '80'"
        tree-node
      ></vxe-column>

      <vxe-column v-if="radio" type="radio" align="center" title="选择" width="55"></vxe-column>
      <vxe-column v-if="selection" type="checkbox" align="center" width="55"></vxe-column>
      <!-- 其它slot插入列 -->

      <slot name="tableColumn" />
    </vxe-table>

    <pagination
      v-if="cptIsPagination"
      ref="pagination"
      :auto-scroll="autoScroll"
      :total="pagingInfo.total"
      :page.sync="pagingInfo.page"
      :limit.sync="pagingInfo.size"
      :page-sizes="pageSizes"
      @pagination="getList"
    >
      <template slot="paginationLeft">
        <slot name="paginationLeft" />
      </template>

      <template slot="paginationRight">
        <slot name="paginationRight" />
      </template>
    </pagination>

    <slot name="paginationBottom" />

    <!-- 多选：选中的数据区域 -->
    <div v-if="(selection || radio) && showTags" class="tags">
      <el-tag
        v-for="(tag, index) in tags"
        :key="index"
        :closable="tagsClosable"
        @close="handleTagClose(tag, index)"
      >
        {{
          tag[tagName] ||
          tag.skuName ||
          tag.spuName ||
          tag.wareName ||
          tag.realName ||
          tag.groupName
        }}
        <span v-if="tagX"> x {{ tag.count }} </span>
      </el-tag>
      <el-button
        v-if="tags.length > 0 && this.selection && this.tagsClosable"
        class="xs"
        type="info"
        icon="el-icon-delete"
        @click="handleAllTagsClose"
        >清空</el-button
      >
    </div>
  </div>
</template>
<script>
// import BjLoading from '@/components/BjLoading';
// import ElTableColumn from '@/components/ElTableColumn/index.vue';
import * as constants from '@/utils/constants';
import { getOffsetTop } from '@/utils/index';
export default {
  name: 'BjTable',
  props: {
    // 查询参数
    queryParams: {
      type: Object,
      default: function () {
        return {};
      },
    },
    // 遮罩层
    loading: {
      type: Boolean,
      default: true,
    },
    // 表格数据
    list: {
      type: Array,
      default: () => {
        return [];
      },
    },
    // 选中数组
    ids: {
      type: Array,
      default: () => {
        return [];
      },
    },
    // 显示搜索条件
    showSearch: {
      type: Boolean,
      default: true,
    },
    // 表格多选
    selection: {
      type: Boolean,
      default: false,
    },
    // 表格 单选
    radio: {
      type: Boolean,
      default: false,
    },
    handleSelectable: {
      type: Function,
      default: () => {
        return true;
      },
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
    // 分页信息
    pagingInfo: {
      type: Object,
      default: function () {
        return {
          size: 20,
          page: 1,
          total: 20,
        };
      },
    },
    pageSizes: {
      type: Array,
      default() {
        return constants.PAGE_SIZES;
      },
    },
    rowStyle: Function,
    rowClassName: Function,
    cellClassName: Function,
    rowKey: {
      type: [String, Function],
      default: '',
    },
    transform: {
      type: Boolean,
      default: true,
    },
    treeProps: {
      type: Object,
      default: () => {
        return {
          rowField: 'id',
          parentField: 'BJ_PARENT_ID',
          hasChild: 'BJ_HAS_CHILD',
        };
      },
    },
    lazy: {
      type: Boolean,
      default: false,
    },
    load: Function,
    height: {
      type: [Number, String],
      default: null,
    },
    /** 源数据： 多选的数据 {key是页码，value是指定页的选中数据} */
    pageDataJson: {
      type: Object,
      default: () => {
        return {
          default: [],
        };
      },
    },
    /** 树状时 */
    tagTreeShowChildOnly: {
      type: Boolean,
      default: false,
    },
    /** 树状时 ,只显示1级数据作为tag，此时父级数据会自动选中*/
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
    /** 是否显示分页组件 */
    isPagination: {
      type: Boolean,
      default: true,
    },
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
      constants,
      // 单选的值
      radioValue: null,
      // 用于禁用: disable="notSingle"
      notSingle: true, // true 选中>1个，false 选中单个
      // 用于禁用: disable="notSelected"
      notSelected: true, // true 选中0个，false  选中>0个
      // 获取表格自适应高度
      autoTableHeight: constants.BJ_TABLE_MINI_HEIGHT,
      // 自定义多选的数据
      mainTableKey: 'table-id-000000001',
    };
  },
  computed: {
    cptMenuConfig() {
      return {
        // header: {
        //   options: [
        //     [
        //       { code: 'hideColumn', name: '隐藏列', disabled: false },
        //       { code: 'showAllColumn', name: '取消所有隐藏列', disabled: false },
        //     ],
        //   ],
        // },
        // visibleMethod: () => {
        //   return true;
        // },
      };
    },
    cptIsPagination() {
      return this.pagingInfo.total > 0 && this.isPagination;
    },
    cptRowKeys() {
      return this.rowKeys || this.rowKey;
    },
    cptTreeNode() {
      return typeof this.load === 'function';
    },
    cptTreeConfig() {
      if (!this.cptTreeNode) return null;
      typeof this.showTreeIcon == 'function';
      return {
        transform: this.transform,
        loadMethod: this.load,
        lazy: this.lazy,
        ...this.treeProps,
      };
    },
    cptCheckbox() {
      if (!this.selection) return {};
      return {
        strict: false,
        reserve: true,
        // checkField: rowKey,
        checkRowKeys: this.ids,
        checkStrictly: false,
        visibleMethod: row => {
          return true;
        },
        checkMethod: this.handleSelectable,
        trigger: 'default',
        highlight: true,
        showHeader: true,
        checkAll: false,
        range: !this.cptTreeNode,
      };
    },
    cptRadioConfig() {
      return {
        strict: false,
        reserve: true,
        labelField: null,
        checkRowKey: this.radioValue,
        visibleMethod: row => {
          return true;
        },
        checkMethod: this.handleSelectable,
        trigger: 'default',
        highlight: true,
      };
    },
    // pageDataJson 数据转换为 数组
    pagesSelectedRows() {
      const TAGS = [];
      Object.keys(this.pageDataJson).forEach(key => {
        const value = this.pageDataJson[key];
        TAGS.push(
          ...value.map(e => {
            return { ...e, BJ_TAG_PAGE: key };
          }),
        );
      });
      return TAGS;
    },
    // pageDataJson 数据转换为 数组 用于在tags中展示，并把key==page塞入每个行数据中
    tags() {
      let TAGS = [];
      // console.log('this.pageDataJson', this.pageDataJson);
      Object.keys(this.pageDataJson).forEach(key => {
        const value = this.pageDataJson[key];
        TAGS.push(
          ...value.map(e => {
            return { ...e, BJ_TAG_PAGE: key };
          }),
        );
      });
      if (this.treeSelectAutoParent && this.cptTreeNode) {
        TAGS = TAGS.filter(e => e.BJ_HAS_CHILD && !e.BJ_PARENT_ID);
      }
      if (this.tagTreeShowChildOnly && this.cptTreeNode) {
        TAGS = TAGS.filter(e => !e.BJ_HAS_CHILD && e.BJ_PARENT_ID);
      }
      if (this.tagX) {
        const tagObj = {};
        TAGS.forEach(tag => {
          const name =
            tag[this.tagName] ||
            tag.skuName ||
            tag.spuName ||
            tag.wareName ||
            tag.realName ||
            tag.groupName;
          if (tagObj[name]) {
            tagObj[name].count += 1;
          } else {
            tagObj[name] = { ...tag, count: 1 };
          }
        });
        const TEMP_TAG = [];
        Object.keys(tagObj).forEach(name => {
          TEMP_TAG.push(tagObj[name]);
        });

        return TEMP_TAG;
      }

      return TAGS;
    },
  },
  watch: {
    list() {
      this.mainTableKey = 'table-id-' + Math.random();
    },
    height() {
      this.setTableHeight();
    },
    cptIsPagination() {
      this.$nextTick(() => {
        this.setTableHeight();
      });
    },
    showSearch() {
      this.setTableHeight();
    },
  },
  created() {},
  mounted() {
    this.$nextTick(() => {
      this.setTableHeight();
    });
  },
  methods: {
    doHandleSelectionChange(list) {
      if (Array.isArray(list)) {
        this.handleSelectionChange({ records: list });
        return;
      }

      const RADIO = this.$refs.ElTable.getRadioRecord();
      const CHECKBOX = this.$refs.ElTable.getCheckboxRecords();

      const records = this.radio ? (RADIO ? [RADIO] : []) : CHECKBOX;

      this.handleSelectionChange({ records });
    },
    // 关闭tag，即反选数据;
    handleTagClose(tag, index) {
      if (this.radio) this.doHandleSelectionChange([]);
      this.$emit('handleTagClose', tag, index);
    },
    handleAllTagsClose() {
      this.$emit('handleAllTagsClose');
    },
    getList({ page, size }) {
      this.$emit('pagination', { page, size });
    },

    //  单选框
    handleRadioChange({ row }) {
      this.doHandleSelectionChange([row]);
      this.$emit('handleRadioChange', [row], row);
      this.radioValue = row[this.rowKey];
    },
    handleSelectAllEvent() {
      const records = this.$refs.ElTable.getCheckboxRecords();
      this.handleSelectionChange({ records });
    },
    // 多选框选中数据
    handleSelectionChange({ records }) {
      const data = this.list;

      const recordsWithParent = [...records];
      // 自动选中父级数据
      if (this.treeSelectAutoParent) {
        const PID_LIST = recordsWithParent
          .filter(e => !e.BJ_HAS_CHILD && e.BJ_PARENT_ID)
          .map(e => e.BJ_PARENT_ID);

        PID_LIST.forEach(pid => {
          // 找到父集
          const find = data.find(e => e[this.rowKey] == pid);
          // 去重复
          const find2 = recordsWithParent.find(e => e[this.rowKey] == pid);
          if (find && !find2) {
            // 加入选中的子集
            find['BJ_SELECT_CHILD'] = recordsWithParent.filter(e => e.BJ_PARENT_ID == pid);
            // 加入父集
            recordsWithParent.push(find);
          }
        });
      }

      const selection = [...recordsWithParent];

      const parents = selection.filter(e => e.BJ_HAS_CHILD && !e.BJ_PARENT_ID);
      const children = selection.filter(e => !e.BJ_HAS_CHILD && e.BJ_PARENT_ID);

      this.$emit('setPageDataJson', selection);

      this.$nextTick(() => {
        this.notSingle = selection.length !== 1; // true 多个，false 单个
        this.notSelected = !selection.length; // true 0个，false  1~2个

        const data = {
          notSingle: this.notSingle,
          notSelected: this.notSelected,
          selection: selection,
          tags: this.tags,
          pagesSelectedRows: this.pagesSelectedRows,
          parents,
          children,
        };
        this.$emit('handleSelectionChange', data);
      });
    },
    // 主表取消全选
    clearChange() {
      this.$refs.ElTable.clearRadioRow();

      this.$refs.ElTable.clearCheckboxRow();
    },

    indexMethod(index) {
      return index + 1;
    },
    /* 设置table高度 */
    setTableHeight(n) {
      if (!this.$refs.ElTable) {
        console.log('no ElTable');
        return;
      }
      // 以 prop.height 手动设置高度max-height
      if (this.height) {
        this.autoTableHeight = this.height;
        console.log('this.height', this.height);
        return;
      }
      // 以 入参 手动设置高度max-height
      if (typeof n === 'number') {
        this.autoTableHeight = n;
        console.warn('setTableHeight', n);
        return;
      }

      // 禁用自动高度
      if (this.noAutoHeight) {
        console.log('noAutoHeight');
        return;
      }
      // 自动计算并设置高度max-height

      console.log('autoTableHeight');
      const offsetTop = getOffsetTop(this.$refs.ElTable.$el);
      let paginationHeight = 0;
      if (this.cptIsPagination) {
        console.log('add pagination to compute');
        paginationHeight = this.$refs.pagination.$el.clientHeight;
      }

      const EL_FORM = document.querySelector('.bj-query-form-for-cal-height');
      let queryFormHeight = 0;
      if (EL_FORM && EL_FORM.clientHeight) {
        queryFormHeight = EL_FORM.clientHeight;
      }
      console.log('queryFormHeight', queryFormHeight);
      // this.autoTableHeight =
      //   document.documentElement.clientHeight -
      //   offsetTop +
      //   queryFormHeight -
      //   paginationHeight -
      //   10.1; // 底部有20px空白，来自 .app-container{ padding: 20px;}

      this.autoTableHeight =
        document.documentElement.clientHeight - offsetTop - paginationHeight - 10.1; // 底部有20px空白，来自 .app-container{ padding: 20px;}

      console.log(
        `${document.documentElement.clientHeight} - ${offsetTop} + ${queryFormHeight} - ${paginationHeight} - 10.1}=${this.autoTableHeight}`,
      );
      // max-height 的 最小值为 BJ_TABLE_MINI_HEIGHT
      if (this.autoTableHeight < constants.BJ_TABLE_MINI_HEIGHT) {
        this.autoTableHeight = constants.BJ_TABLE_MINI_HEIGHT;
      }
      console.log('最终：', this.autoTableHeight);
    },
    /** START 以下配合 ElementTableSortable 插件 */
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
    /** 选中某个数据 */
    toggleRowSelection() {
      // setCheckboxRow(rows, checked)
      this.$refs.ElTable.setCheckboxRow(...arguments);

      this.$nextTick(() => {
        this.doHandleSelectionChange();
      });
    },
    refreshColumn() {
      this.$refs.ElTable.refreshColumn();
    },
  },
  beforeDestory() {
    // window.removeEventListener('resize', this.setTableHeight);
  },
};
</script>

<style lang="scss">
.bjTable-2022-2-23-16-56-17 {
  .tags {
    margin-top: 10px;
    .el-tag {
      margin: 4px;
    }
  }
  .el-tag--mini .el-icon-close {
    color: red;
    font-weight: bold;
  }
  .hide-expand-icon .el-table__expand-icon {
    display: none;
  }
}
</style>
