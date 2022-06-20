/*
 * @Author: your name
 * @Date: 2021-06-24 18:36:56
 * @LastEditTime: 2022-04-24 18:21:34
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\utils\page-mixin.js
 */
import { commonApi } from '@/api';

import ElTableColumn from '@/components/ElTableColumn/index.vue';

export default {
  components: {
    ElTableColumn,
  },
  data() {
    return {
      /** * 拖拽自定义表头 */
      colSpan: 6,
      labelsVersion: 11, // 不能为 0 或 false
      SortableTable: {
        show: () => {},
        handleHeaderDragend: () => {},
        handleHeaderContextmenu: () => {},
      },
      dropCols: [],
      //
      selectedDataObj: {},
      tags: [], // 选中项 转为 tags ，在表格下方
      pagesSelectedRows: [], // 各页的选中项集合
      selectedRows: [], // 当前页的选中项
      treeTableChildrenSelection: [], //
      expandTableSelectedObj: [],
      selection: true,
      serial: true,
      /** 禁止拖动 */
      sortableTableCode: '', // 同一个路由页面下，多tab页列表的保存Code
      sortTableByOptionName: false,
      tableSortableDisabled: false,

      routerName: '',
    };
  },
  filters: {
    phoneFilter(val) {
      if (val) {
        const reg = /^(.{3}).*(.{4})$/;
        return val.replace(reg, '$1****$2');
      }
    },
  },
  computed: {},
  watch: {
    '$route.name'(v) {
      this.routerName = v;
    },
  },
  created() {},
  mounted() {
    this.routerName = this.$route.name;
    // 注册刷新事件
    this.$eventHub.$on('refresh' + this.routerName, this.reFresh);
    /** 拖拽自定义表头 */
    let startIndex = 0;
    if (this.selection) startIndex++;
    if (this.serial) startIndex++;
    // 保存的唯一code
    if (!this.sortableTableCode) {
      // 默认取路由name。
      this.sortableTableCode = this.$route.name;
      if (this.sortTableByOptionName) {
        // 取组件名称
        this.sortableTableCode = this.$options.name;
      }
    }
    // console.log('tableSortableDisabled = ', this.tableSortableDisabled);
    // console.log('sortableTableCode = ', this.sortableTableCode);
    this.SortableTable = this.$initElementTableSortable({
      el: '.body--wrapper>.vxe-table--header .vxe-header--row', // '.el-table__header-wrapper tr:last-child',
      tableSortableDisabled: this.tableSortableDisabled,
      dragElement: '.vxe-header--column', // 默认是 className : draggable  .item
      defaultLabels: this.labelList,
      startIndex: startIndex,
      sortableTableCode: this.sortableTableCode, // 唯一key
      labelsVersion: this.labelsVersion, // 当前版本号
      setRemoteApi: commonApi.saveTabulation, // 非必填
      getRemoteApi: commonApi.getTabulation, // 非必填
      handleColumn: this.handleColumn,
    });
    // this.setSortTableCodeAndHeight();

    // 对整个页面滚轮进行监听，每发生一次滚轮事件，执行一次方法
    // window.addEventListener('scroll', this.scrollCallBack, true);
  },
  beforeDestroy() {
    this.$eventHub.$off('refresh' + this.routerName);
  },
  methods: {
    scrollCallBack() {
      // 兼容性，获取页面滚动距离
      const scrollTop =
        window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;

      const IS_BOTTOM = document.body.scrollHeight - scrollTop - document.body.clientHeight < 3;

      this.$refs.BjPage.showSearch = !IS_BOTTOM;
    },
    // setSortTableCodeAndHeight() {
    //   this.$nextTick(() => {
    //     // 根据列表code，获取列表的自定义高度（用于max-height）
    //     const LAST_HEIGHT = Number(localStorage.getItem(this.sortableTableCode) || 0);
    //     // 把 code 和 height 传入 bjPage
    //     if (LAST_HEIGHT) {
    //       this.$refs.BjPage?.setSortTableCodeAndHeight(this.sortableTableCode, LAST_HEIGHT);
    //     }
    //   });
    // },
    /** 重置按钮操作 */
    beforeResetSearch() {
      this.handleSelectionChange({});
      return new Promise(resolve => {
        resolve();
      });
    },
    /** 拖拽自定义表头 */
    handleColumn(data) {
      this.dropCols = data;
      if (this.tableSortableDisabled) return;
      this.$nextTick(() => {
        this.$refs.BjPage.$refs.BjTable && this.$refs.BjPage.$refs.BjTable.refreshColumn();
      });
    },
    reFresh() {
      this.$refs.BjPage && this.$refs.BjPage.fetchData();
    },
    handleSelectionChange(data) {
      this.selectedDataObj = { ...data };
      this.selectedRows = data.selection || [];
      this.tags = data.tags || [];
      // console.log('this.tags', this.tags);
      this.treeTableChildrenSelection = data.children || [];
      this.pagesSelectedRows = data.pagesSelectedRows || [];
    },
    handleQuery() {
      this.reFresh();
    },
    // 子多选框选中数据
    handleExpandTableSelectionChange(arg) {
      // const {
      //   childrenSelection,
      //   parentRow,
      //   parentIndex,
      //   expandTableIndexSelection,
      //   treeTableChildrenSelection,
      // } = arg;
      this.expandTableSelectedObj = arg;
      console.log(this.expandTableSelectedObj);
    },
  },
};
