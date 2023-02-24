<template>
  <el-dialog
    v-if="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="config.title"
    :visible.sync="visible"
    :before-close="handleClose"
    :width="width"
    append-to-body
    class="bj-dialog-list"
    :class="classID"
  >
    <!-- 表格 -->
    <el-row :gutter="20">
      <el-col :span="12" class="line-r">
        <!-- 查询 -->
        <el-row :gutter="10" class="top">
          <el-col v-for="(searchJson, i) in config.search" :key="i" :span="6">
            <component
              :is="searchJson.component || 'el-input'"
              v-model="obj[searchJson.key]"
              :multiple="searchJson.multiple"
              class="search-input"
              :placeholder="searchJson.placeholder"
              suffix-icon="el-icon-search"
              :lov-code="searchJson.lovCode"
              :option-list="searchJson.optionList"
              :constant-key="searchJson.constantKey"
            />
          </el-col>
          <el-col :span="searchSpan" class="search-span">
            <el-button @click="reset">重 置</el-button>
            <el-button type="primary" @click="search">查 询</el-button>
          </el-col>
        </el-row>
        <el-table
          ref="multipleTable"
          class-name="xxxxxxxxxxxxxxxxxxxxxxxxxxxx"
          :data="tableData"
          stripe
          border
          :header-cell-style="$rowStyle.DEFAULT"
          style="width: 100%"
          :max-height="maxHeight"
          @selection-change="handleSelectionChange"
          @select="handleSelectClick"
          @select-all="handleSelectClick"
        >
          <el-table-column
            v-if="multiple"
            type="selection"
            :selectable="checkSelectable"
            width="55"
            align="center"
          />

          <el-table-column v-if="!multiple" label="选择" width="55" align="center">
            <template slot-scope="scope">
              <el-radio
                v-model="radio"
                :label="scope.$index"
                @change.native="getCurrentRow(scope.row)"
              >
                {{ '' }}
              </el-radio>
            </template>
          </el-table-column>

          <template v-for="(column, i) in config.columns">
            <el-table-column
              :key="i"
              :prop="column.prop"
              :label="column.label"
              align="center"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <template v-if="column.constantKey">{{
                  constants[column.constantKey].bjGet(scope.row[column.prop])
                }}</template>
                <template v-else-if="column.array">{{
                  (scope.row[column.prop] || [])
                    .map(e => (e[column.array] === undefined ? e : e[column.array]))
                    .join(',')
                }}</template>
                <template v-else>
                  {{ scope.row[column.prop] }}
                </template>
              </template>
            </el-table-column>
          </template>
        </el-table>
        <!-- 分页 -->
        <div v-if="pagingInfo.total > 0" class="pagination">
          <el-pagination
            :small="true"
            :background="background"
            :current-page.sync="pagingInfo.page"
            :page-size.sync="pagingInfo.size"
            :layout="layout"
            :page-sizes="pageSizes"
            :total="pagingInfo.total"
            :pager-count="5"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-col>
      <el-col class="line-l" :span="12">
        <el-row :gutter="10" class="top">
          <el-col :span="12">
            <el-button type="text">已选择：</el-button>
          </el-col>
          <el-col :span="12" class="search-span">
            <el-button @click="onClear">清 空</el-button>
            <el-button @click="onDelete">删 除</el-button>
          </el-col>
        </el-row>
        <el-table
          :data="seleceTable"
          stripe
          border
          :header-cell-style="$rowStyle.DEFAULT"
          style="width: 100%"
          :max-height="maxHeight"
        >
          <el-table-column
            type="selection"
            :selectable="checkSelectable"
            width="55"
            align="center"
          />
          <template v-for="(column, i) in config.changeColumns">
            <el-table-column
              :key="i"
              :label="column.label"
              :prop="column.prop"
              align="center"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <template v-if="column.constantKey">{{
                  constants[column.constantKey].bjGet(scope.row[column.prop])
                }}</template>
                <template v-else-if="column.array">{{
                  (scope.row[column.prop] || [])
                    .map(e => (e[column.array] === undefined ? e : e[column.array]))
                    .join(',')
                }}</template>
                <template v-else-if="column.slot">
                  <slot :name="column.slot" :scope="scope" />
                </template>
                <template v-else>
                  {{ scope.row[column.prop] }}
                </template>
              </template>
            </el-table-column>
          </template>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <slot name="button" :scope="scope" />
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <!-- 多选：选中的数据区域 -->
    <!-- <div v-if="multiple" class="tags">
      <el-tag
        v-for="(tag, index) in tags"
        :key="index"
        closable
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
      </el-tag>
    </div> -->
    <!-- 按钮 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { getOffsetTop } from '@/utils/index';
// import { INPUT_BOX_TYPE_LIST } from '@/utils/constants';
import * as constants from '@/utils/constants';

const defaultPage = function () {
  return {
    total: 0,
    page: 1,
    size: constants.PAGE_SIZES[0],
  };
};
export default {
  name: 'BjDialogList',
  props: {
    config: {
      type: Object,
      default: () => {
        return {
          title: '选择商品',
          columns: [
            {
              prop: 'code',
              label: '商品编码(spuCode)',
            },
            // 用@/utils/constantKeys解析值
            {
              prop: 'type',
              label: '表单方式',
              constantKey: 'INPUT_BOX_TYPE_LIST',
            },
            // 取json数组中key为value的值，用逗号连接
            {
              prop: 'attributeValueResVOList',
              label: '属性值',
              array: 'value',
            },
          ],
          search: [
            {
              placeholder: '请输入编码',
              key: 'spuCode',
              component: 'el-input', // 这是默认值，可不填
            },
            //  除input外的其它组件
            {
              placeholder: '请选择分类',
              key: 'categoryIdList',
              component: 'BjGoodsTypeTreeSelect',
              multiple: true,
            },
          ],
          defaultQuery: {},
          actionApi: '',
          query: '',
          changeColumn: [
            {
              prop: 'code',
              label: '商品编码(spuCode)',
            },
          ],
        };
      },
    },
    width: {
      type: String,
      default: '1200px',
    },
    /** ***** 分页  *********** */
    background: {
      type: Boolean,
      default: true,
    },
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper',
    },
    pageSizes: {
      type: Array,
      default() {
        return constants.PAGE_SIZES;
      },
    },
    autoScroll: {
      type: Boolean,
      default: true,
    },
    /** ********************* 多选  start ****************************** */
    /** 多选 */
    multiple: {
      type: Boolean,
      default: true, // 默认单选
    },
    /** 显示的tag名称字段 */
    tagName: {
      type: String,
      default: 'name', // 默认按name skuName spuName wareName realName groupName
    },
    /** 多选禁用项 */
    disabled: {
      type: Array,
      default: () => {
        return [];
      },
    },
    /** 用区别选中行的key，一般取id; 如果需要多个key共同确定，中间用/分隔 */
    rowKeys: {
      type: String,
      default: 'id',
    },
    /** ************************* end ************************* */
  },
  data() {
    return {
      constants,

      tableData: [],
      seleceTable: [
        { spuName: '名称1', sort: 5 },
        { spuName: '名称2', sort: 6 },
      ],
      visible: false,
      selectedData: [],
      /** 搜索内容 */
      obj: {},
      pagingInfo: defaultPage(),
      /** 单选*/
      radio: null,

      maxHeight: '480px',

      /** 源数据： 多选的数据 {key是页码，value是指定页的选中数据} */
      pageDataJson: {
        default: [],
      },

      /** */
      DEFAULT_SELECTED_ROWS: [], // 不变  // pageDataJson['default']: [...DEFAULT_SELECTED_ROWS], //跟随 删除操作 变动
    };
  },
  computed: {
    classID() {
      return (
        'bj-dialog-list-' +
        Number(Math.random().toString().substr(3, 12) + Date.now())
          .toString(36)
          .substr(0, 8)
          .toUpperCase()
      );
    },
    searchSpan() {
      return 24 - ((this.config.search.length * 6) % 24);
    },
    // pageDataJson 数据转换为 数组 用于在tags中展示，并把key==page塞入每个行数据中
    // tags() {
    //   const TAGS = [];
    //   Object.keys(this.pageDataJson).forEach(key => {
    //     const value = this.pageDataJson[key];
    //     TAGS.push(
    //       ...value.map(e => {
    //         return { ...e, BJ_TAG_PAGE: key };
    //       }),
    //     );
    //   });
    //   return TAGS;
    // },
  },
  watch: {
    visible() {
      if (this.visible) {
        this.$nextTick(() => {
          this.reset();

          this.maxHeight =
            document.documentElement.clientHeight -
            getOffsetTop(`.${this.classID} .el-table__header-wrapper`) * 2 -
            100 +
            'px';
        });
      }
    },
  },
  created() {},
  mounted() {},
  methods: {
    show(d) {
      this.visible = true;
      // 获取默认值
      if (d) {
        this.DEFAULT_SELECTED_ROWS = [...d];
      }
    },
    resetSelected() {
      this.$refs.multipleTable && this.$refs.multipleTable.clearSelection();
      this.selectedData = [];
      this.radio = null;
    },
    /** 重置 */
    reset() {
      // console.log('重置');
      this.resetSelected();
      this.config.search.forEach(element => {
        this.$set(this.obj, element.key, null);
      });
      this.pagingInfo = defaultPage();
      this.dialogSearch(this.pagingInfo);
      this.pageDataJson = { default: [...this.DEFAULT_SELECTED_ROWS] };
    },
    /** 查询 */
    search() {
      this.resetSelected();
      this.dialogSearch(this.pagingInfo);
    },
    /** 选中数据 */
    handleSelectionChange(data) {
      this.selectedData = [...data];
      // console.log('BjDialogList：多选-选中数据', this.selectedData);
    },
    getCurrentRow(row) {
      this.selectedData = [row];
      // console.log('BjDialogList：单选-选中数据', this.selectedData);
    },
    /** 设置禁止选中的条件 */
    checkSelectable(row, index) {
      return !this.disabled.includes(row[this.rowKeys]);
    },
    /** *********  翻页 *********** */
    handleSizeChange(val) {
      this.pagingInfo.size = val;

      this.search();

      if (this.autoScroll) {
        scrollTo(0, 800);
      }
    },
    handleCurrentChange(val) {
      this.pagingInfo.page = val;
      this.search();
      if (this.autoScroll) {
        scrollTo(0, 800);
      }
    },
    /* ************************************* */
    /** 以下是自定义组件函数 */
    /* 关闭弹窗 */
    handleClose() {
      this.visible = false;
    },
    /** 切换每页显示数目 */
    getPageList(pagingInfo) {
      // console.log('获取page数据');
      const data = { ...pagingInfo };
      this.searchApi(data);
    },

    /** 自定义弹窗搜索 */
    dialogSearch(pagingInfo, vale) {
      // console.log('搜索');

      const data = { ...pagingInfo, ...(this.config.defaultQuery || {}) };
      this.config.search.forEach(element => {
        data[element.key] = this.obj[element.key];
      });

      this.searchApi(data);
    },
    /** 清空 */
    onClear() {
      this.$confirm('您正在永久删除该数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.seleceTable = [];
          this.msgSuccess('操作成功');
        })
        .catch(() => {});
      console.log('清空');
    },
    /** 删除  */
    onDelete(index) {
      this.$confirm('您正在永久删除该数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.msgSuccess('操作成功');
        })
        .catch(() => {});
    },
    /** 确定 */
    confirm() {
      const CONFIRM_DATA = this.multiple ? this.tags : this.selectedData;

      if (CONFIRM_DATA.length == 0) {
        this.$message.warning('请选择');
        return;
      }
      // console.log('确定', CONFIRM_DATA);

      this.visible = false;
      this.$emit('confirm', CONFIRM_DATA);
    },
    searchApi(data) {
      const POST_DATA = this.$lodash.clone(data);
      if (this.config.query) {
        POST_DATA[this.config.query] = this.$route.query[this.config.query];
      }
      this.config.actionApi(POST_DATA).then(res => {
        this.radio = null;
        this.tableData = res.content;
        this.pagingInfo.total = res.totalElements || res.content.length;

        // 回写选中数据
        if (this.multiple) this.seletedDataByPage();
      });
    },
    /** ******************** 多选  start ****************************** */
    // 一个方法： 根据rowKeys，从SELECTED中查找ROW
    findRow(data, row, type = 'find') {
      return data[type](selectedItem =>
        this.rowKeys.split('/').every(key => selectedItem[key] === row[key]),
      );
    },
    // 关闭tag，即反选数据;
    handleTagClose(tag, index) {
      // 1.如果是当前页，则多选框反选
      if (tag.BJ_TAG_PAGE == this.pagingInfo.page) {
        const row = this.findRow(this.tableData, tag);
        this.$refs.multipleTable.toggleRowSelection(row, false);
      } else {
        // console.log(typeof tag.BJ_TAG_PAGE, typeof this.pagingInfo.page);
      }
      // 2.从this.pageDataJson数据中清除此tag
      const FIND_INDEX = this.findRow(this.pageDataJson[tag.BJ_TAG_PAGE], tag, 'findIndex');
      if (FIND_INDEX >= 0) this.pageDataJson[tag.BJ_TAG_PAGE].splice(FIND_INDEX, 1);
    },
    // 手动选择的回调
    handleSelectClick(selection, row) {
      // console.log(selection, row);
      this.$set(this.pageDataJson, this.pagingInfo.page, [...selection]);
    },
    // 翻页后，回写已选数据
    seletedDataByPage() {
      /** 当前页 赋 默认值 */
      const key = this.pagingInfo.page;
      const value = [];
      if (!this.pageDataJson[key] || this.pageDataJson[key].length == 0) {
        this.tableData.forEach(row => {
          const FIND_INDEX = this.findRow(this.pageDataJson.default, row, 'findIndex');
          if (FIND_INDEX > -1) {
            value.push({ ...row, isBjDefaultRow: true });
            this.pageDataJson.default.splice(FIND_INDEX, 1);
          }
        });
        this.$set(this.pageDataJson, key, [...value]);
      }
      /** 回写 */
      this.$nextTick(() => {
        if (!this.$refs.multipleTable) {
          // console.error('seletedDataByPage Error');
          return;
        }
        const SELECTED = this.pageDataJson[this.pagingInfo.page] || [];
        this.tableData
          .filter(e => this.findRow(SELECTED, e))
          .forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row, true);
          });
      });
    },
    /** ********************* end *********************** */
  },
};
</script>
<style lang="scss">
.bj-dialog-list {
  .top {
    margin-bottom: 8px;
    .el-col {
      margin-bottom: 16px;
    }
  }
  .el-radio {
    padding: 2px 8px;
  }
  .el-radio .el-radio__label {
    display: none;
  }
  .search-span {
    // display: flex;
    // justify-content: flex-end;
  }
  .search-input {
    width: 100%;
  }
  .search-input:first-child {
    margin-left: 0;
  }
  .pagination {
    display: flex;
    justify-content: flex-end;
    padding-top: 16px;
  }
  //   .tags {
  //     margin-top: 10px;
  //     .el-tag {
  //       margin: 4px;
  //     }
  //   }
}
</style>
