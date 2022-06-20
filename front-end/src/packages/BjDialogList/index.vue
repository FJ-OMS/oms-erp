<template>
  <el-dialog
    v-if="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="true"
    :title="config.title"
    :visible.sync="visible"
    :before-close="handleClose"
    :width="width"
    append-to-body
    class="bj-dialog-list"
    :class="classID"
    :custom-class="customClass"
  >
    <el-row :gutter="20">
      <el-col :span="24 - rightSpan">
        <BjPage
          ref="BjPage"
          :query-params="queryParams"
          :selection="multiple"
          :radio="!multiple"
          :disabled="disabled"
          :col-span="colSpan"
          :get-action="config.actionApi"
          :auto-fetch="false"
          :auto-scroll="autoScroll"
          :serial="serial"
          :stripe="false"
          :row-key="rowKey"
          :row-keys="rowKeys"
          :row-style="rowStyleFun"
          :cell-class-name="cellClassName"
          :tag-name="tagName"
          :show-tags="showTags"
          :tag-x="tagX"
          :height="maxHeight"
          tree-select-auto-parent
          right-toolbar-hide
          :search-btn-hide="searchBtnHide"
          :reset-data="resetData"
          :before-reset-search="beforeResetSearch"
          :check-method="checkMethod"
          :lazy="cptLazy"
          :load="load"
          @handleSelectionChange="handleSelectionChange"
          @afterFetchData="afterFetchData"
        >
          <template #baseForm>
            <el-col v-for="(searchJson, i) in config.search" :key="i" :span="colSpan">
              <el-form-item label-width="0" :prop="searchJson.key">
                <component
                  :is="searchJson.component || 'el-input'"
                  v-model="queryParams[searchJson.key]"
                  :multiple="searchJson.multiple"
                  class="search-input"
                  :placeholder="searchJson.placeholder"
                  suffix-icon="el-icon-search"
                  size="mini"
                  :lov-code="searchJson.lovCode"
                  :option-list="searchJson.optionList"
                  :constant-key="searchJson.constantKey"
                  clearable
                />
              </el-form-item>
            </el-col>
            <template v-if="config.showChannelStore">
              <BjSelectChannelStore
                :span="colSpan"
                :label-width="['0', '0']"
                :query-labels="['', '']"
                :query-params="queryParams"
                :query-props="
                  Array.isArray(config.showChannelStore)
                    ? config.showChannelStore
                    : ['channelTypeCode', 'storeIdList']
                "
                multiple
              />
            </template>
          </template>
          <template #tableTop>
            <slot name="tableTop" />
          </template>

          <template #tableColumn>
            <template v-for="(column, i) in config.columns">
              <el-table-column
                :key="i"
                :prop="column.prop"
                :label="column.label"
                :width="column.width || 'auto'"
                align="center"
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
                  <template
                    v-else-if="
                      column.prop.toLocaleLowerCase().includes('amount') ||
                        column.prop.toLocaleLowerCase().includes('price') ||
                        column.prop.toLocaleLowerCase().includes('discount')
                    "
                  >
                    {{ $number2money(scope.row[column.prop]) }}
                  </template>

                  <template
                    v-else-if="
                      column.prop.toLocaleLowerCase().includes('mobile') ||
                        column.prop.toLocaleLowerCase().includes('phone')
                    "
                  >
                    {{ scope.row[column.prop] | phoneFilter }}
                  </template>

                  <template v-else>
                    {{ scope.row[column.prop] }}
                  </template>
                </template>
              </el-table-column>
            </template>
            <slot name="tableColumn" />
          </template>

          <template slot="paginationLeft">
            <slot name="paginationLeft" />
          </template>
        </BjPage>
      </el-col>
      <!-- 右侧 slot;  例： src\views\center-setting\order\source-strategy\parcel\index.vue -->
      <el-col v-if="rightSpan" :span="rightSpan" :style="`height:${maxHeight}`">
        <slot
          name="right"
          :tags="tags"
          :selectedRows="selectedRows"
          :pagesSelectedRows="pagesSelectedRows"
          :maxHeight="maxHeight"
          :visible="visible"
        ></slot>
      </el-col>
    </el-row>

    <!-- 按钮 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import ElTableColumn from '@/components/ElTableColumn/index.vue';
import pageMiXin from '@/utils/page-mixin.js';
import { getOffsetTop } from '@/utils/index';
// import { INPUT_BOX_TYPE_LIST } from '@/utils/constants';
import * as constants from '@/utils/constants';

export default {
  name: 'BjDialogList',
  components: {
    ElTableColumn,
  },
  mixins: [pageMiXin],
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
          showChannelStore: false,
          defaultQuery: {},
          actionApi: '',
          query: '',
        };
      },
    },
    width: {
      type: String,
      default: '960px',
    },
    rightSpan: {
      type: Number,
      default: 0, // 0-24  默认单位%
    },
    searchBtnHide: {
      type: Boolean,
      default: false,
    },
    customClass: {
      type: String,
      default: '',
    },
    /** ********************* 多选  start ****************************** */
    /** 多选 */
    multiple: {
      type: Boolean,
      default: false, // 默认单选
    },
    /** 默认禁用的项，仅支持 id数组，不是row data  ； 支持 多选 和 单选*/
    disabled: {
      type: Array,
      default: () => {
        return [];
      },
    },
    checkMethod: Function,
    rowKey: {
      type: String,
      default: 'id',
    },
    /** 用区别选中行的key，一般取id; 如果需要多个key共同确定，中间用/分隔 */
    rowKeys: {
      type: String,
      default: 'id',
    },
    /** 显示的tag名称字段 */
    tagName: {
      type: String,
      default: 'name', // 默认按name skuName spuName wareName realName groupName
    },
    /** 是否以tag形式在表格底部显示选中项 */
    showTags: {
      type: Boolean,
      default: true,
    },
    /** tag 以 【tagName x 数量】  聚合*/
    tagX: {
      type: Boolean,
      default: false,
    },
    /** ************************* end ************************* */
    // 点击确认前 执行的方法 。返回 Promise
    beforeConfirm: Function,
    autoScroll: {
      type: Boolean,
      default: false,
    },
    resetData: Function,

    cellClassName: Function,

    load: Function,
  },
  data() {
    return {
      constants,

      tableSortableDisabled: true,

      // 查询参数
      queryParams: {
        channelTypeCode: null,
        storeIdList: null,
      },

      tableData: [],
      visible: false,
      selectedRows: [],
      tags: [],
      /** 单选*/
      radio: null,

      maxHeight: null,
    };
  },
  computed: {
    classID() {
      return (
        'bj-dialog-list-' +
        Number(
          Math.random()
            .toString()
            .substr(3, 12) + Date.now(),
        )
          .toString(36)
          .substr(0, 8)
          .toUpperCase()
      );
    },
    searchSpan() {
      return 24 - ((this.config.search.length * 6) % 24);
    },
    cptLazy() {
      return typeof this.load === 'function';
    },
  },
  watch: {
    visible() {
      if (this.visible) {
        this.$nextTick(() => {
          this.$refs.BjPage.onResetQuery();
          // el-table__header-wrapper
          // this.$nextTick(() => {
          this.maxHeight =
            document.documentElement.clientHeight -
            getOffsetTop(`.${this.classID} .vxe-table--header-wrapper`) * 2 -
            40;
          // });
        });
      } else {
        // 关闭时，清空搜索项
        this.$refs.BjPage.$refs.BjForm.resetForm();
      }
    },
  },
  created() {
    this.config.search.forEach(e => {
      this.$set(this.queryParams, e.key, null);
    });
  },
  mounted() {},
  methods: {
    /** 重置按钮操作 */
    beforeResetSearch() {
      // 多选的入参 手动重置 为空数据
      const LIST = this.config?.search;
      if (Array.isArray(LIST)) {
        LIST.forEach(form => {
          if (form.multiple) {
            this.queryParams[form.key] = [];
          }
        });
      }

      return new Promise(resolve => {
        resolve();
      });
    },
    // 禁用的标颜色  #c0c4cc
    rowStyleFun({ row, rowIndex }) {
      const DISABLED_FLAG = !this.$refs.BjPage.handleSelectable({ row, index: rowIndex });
      const styleJson = {};
      if (DISABLED_FLAG) {
        styleJson.color = '#c0c4cc';
      }
      const treeStyle = this.$treeRowStyleFun({ row, rowIndex });
      return { ...styleJson, ...treeStyle };
    },
    show(d) {
      console.log('BjDialogList.show', d);
      Object.keys(this.config.defaultQuery || {}).forEach(key => {
        this.$set(this.queryParams, key, this.config.defaultQuery[key]);
      });
      this.visible = true;
      // 获取默认值
      if (Array.isArray(d) || d?.spuId) {
        this.$nextTick(() => {
          this.$refs.BjPage.setDefaultSelectedRows(Array.isArray(d) ? [...d] : [d]);
        });
      }
    },

    /* ************************************* */
    /** 以下是自定义组件函数 */
    /* 关闭弹窗 */
    handleClose() {
      this.visible = false;
    },
    /** 获取数据后执行的函数 */
    afterFetchData(data) {
      this.$emit('afterFetchData', data);
    },

    /** 确定 */
    confirm() {
      console.log('this.tags', this.tags);
      console.log('this.selectedRows', this.selectedRows);
      const CONFIRM_DATA = this.multiple ? this.tags : this.selectedRows;

      if (CONFIRM_DATA.length == 0) {
        this.$message.warning('请选择');
        return;
      }

      if (typeof this.beforeConfirm == 'function') {
        this.beforeConfirm(CONFIRM_DATA)
          .then(RES_CONFIRM_DATA => {
            this.visible = false;
            this.$emit('confirm', RES_CONFIRM_DATA);
          })
          .catch(e => {
            this.$message.warning(e);
          });
      } else {
        this.visible = false;
        //                     外层选中table   展开的内层选中table        所有数据
        this.$emit('confirm', CONFIRM_DATA, this.treeTableChildrenSelection, this.selectedDataObj);
      }
    },

    // 关闭tag，即反选数据;
    handleTagClose(tag) {
      this.$refs.BjPage.handleTagClose(tag);
    },
  },
};
</script>
<style lang="scss">
.bj-dialog-list {
  .el-dialog__header {
    padding-bottom: 0;
  }
  .el-dialog__body {
    padding-top: 10px;
    padding-bottom: 10px;
  }
  .el-dialog__footer {
    padding-top: 0;
  }
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
    display: flex;
    justify-content: flex-end;
  }
  .el-form-item__content {
    padding-right: 4px;
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
  .tags {
    margin-top: 10px;
    .el-tag {
      margin: 4px;
    }
  }
}
.xs.el-button {
  margin: 4px;
  height: 20px;
  padding: 0 5px;
  line-height: 19px;
}
</style>
