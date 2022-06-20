<template>
  <!--表格设置弹窗-->
  <div>
    <el-dialog
      v-if="dialogVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="dialogVisible"
      width="55%"
      :destroy-on-close="true"
      :before-close="dialogClose"
      append-to-body
    >
      <el-checkbox :value="checkAll" :indeterminate="isIndeterminate" @change="changeCheckAll"
        >全选</el-checkbox
      >
      <div style="margin: 15px 0" />

      <template v-for="(item, index) in columnList">
        <el-checkbox
          :key="index"
          v-model="columnList[index].isCheck"
          :label="item.label"
          :disabled="item.disabled"
        />
      </template>

      <span slot="footer" class="dialog-footer">
        <el-button @click="resetTableColumn">恢复默认</el-button>
        <el-button @click="dialogClose">取 消</el-button>
        <el-button type="primary" @click="dialogConfirm">确 定</el-button>
      </span>
    </el-dialog>

    <ul
      v-show="fixedConfig.visible"
      :style="{ left: fixedConfig.left + 'px', top: fixedConfig.top + 'px' }"
      :class="contextMenuClass"
    >
      <li @click="setColumn('fixed', 'left')">将此列位置固定在 左侧</li>
      <li @click="setColumn('fixed', 'right')">将此列位置固定在 右侧</li>
      <li @click="setColumn('fixed', '')">此列解除固定</li>
      <li @click="setColumn('width', 'auto')">此列宽度设为 自适应</li>
    </ul>
  </div>
</template>
<script>
import Sortable from 'sortablejs';
const copy = arr => {
  return JSON.parse(JSON.stringify(arr));
};
export default {
  name: 'ElementTableSortable',
  props: {
    labelsVersion: {
      type: [Number, String],
      default: 1,
    },
    defaultLabels: {
      type: Array,
      default: () => {
        return [];
      },
    },
    title: {
      type: String,
      default: '设置',
    },
    sortableTableCode: {
      type: String,
      default: '',
    },
    dataIdAttr: {
      type: String,
      default: 'data-id',
    },
    dragElement: {
      type: String,
      default: '.draggable',
    },
    dragFilter: {
      type: String,
      default: '.ignore-elements',
    },
    fixedConfigTop: {
      type: Number,
      default: 0,
    },
    contextMenuClass: {
      type: String,
      default: 'context-menu',
    },
    el: {
      type: [HTMLElement, String],
      default: function () {
        return document.body;
      },
    },
    startIndex: {
      type: Number,
      default: 0,
    },
    tableSortableDisabled: {
      type: Boolean,
      default: false,
    },
    handleColumn: Function, // column变更时
    handleSet: Function, // 保存API时
    handleGet: Function, // 获取API时
    setRemoteApi: Function,
    getRemoteApi: Function,
  },
  data() {
    return {
      tempString: [],
      dialogVisible: false,
      checkedValues: [],
      isIndeterminate: true,
      checkAll: false,
      columnList: [],
      /** * context-menu */
      fixedConfig: {
        top: 0,
        left: 0,
        visible: false,
        property: null,
      },
    };
  },
  computed: {
    dropCols() {
      return this.columnList.filter(item => item.isCheck);
    },
  },
  watch: {
    columnList: {
      handler() {
        this.checkedValues = this.columnList.filter(item => item.isCheck).map(e => e.label);

        const checkedCount = this.checkedValues.length;
        const maxCount = this.columnList.length;

        // 赋值
        this.checkAll = checkedCount === maxCount;
        this.isIndeterminate = checkedCount > 0 && checkedCount < maxCount;

        // 回调
        this.$nextTick(() => {
          this.handleColumn && this.handleColumn(this.dropCols);
        });
      },
      deep: true,
      immediate: true,
    },
    'fixedConfig.visible'(value) {
      if (value) {
        document.body.addEventListener('click', this.closeFixedConfig);
      } else {
        document.body.removeEventListener('click', this.closeFixedConfig);
      }
    },
  },
  created() {},
  mounted() {
    this.setList(this.defaultLabels);

    setTimeout(() => {
      // 生成 dom 后，再触发，预计200ms以上
      this.startColumnDropFn();
    }, 500);
    this.getTabulation();
    if (!this.getRemoteApi) {
      console.log('getRemoteApi not found. Use localStorage.getItem');
    }
    if (!this.setRemoteApi) {
      console.log('setRemoteApi not found. Use localStorage.setItem');
    }
  },
  methods: {
    setApi() {
      if (this.setRemoteApi) {
        return this.setRemoteApi(...arguments);
      }
      const OBJ = arguments[0];
      return new Promise((resolve, reject) => {
        try {
          localStorage.setItem(OBJ.code, JSON.stringify(OBJ.tabulationTitleDTOS || []));
          resolve('localStorage.setItem: OK');
        } catch (error) {
          console.error(error);
          reject([]);
        }
      });
    },
    getApi() {
      if (this.getRemoteApi) {
        return this.getRemoteApi(...arguments);
      }

      const OBJ = arguments[0];
      return new Promise((resolve, reject) => {
        try {
          const value = JSON.parse(localStorage.getItem(OBJ.code) || '[]');
          resolve(value);
        } catch (error) {
          console.error(error);
          reject([]);
        }
      });
    },
    /** *** */
    // 给外部设置里面的columList
    setList(d) {
      this.columnList = copy(d);
    },
    // 打开
    show() {
      this.dialogVisible = true;
      this.tempString = JSON.stringify(this.columnList);
    },
    // 恢复默认
    resetTableColumn() {
      this.dialogClose();
      this.$nextTick(() => {
        this.saveTabulation();
      });
    },
    /* 关闭弹窗 */
    dialogClose() {
      this.dialogVisible = false;
      this.setList(JSON.parse(this.tempString));
    },
    // 确定保存
    dialogConfirm() {
      this.dialogVisible = false;
      this.saveTabulation(this.columnList);
    },
    // 全选事件
    changeCheckAll(flag) {
      this.isIndeterminate = false;
      this.columnList.forEach(item => {
        item.isCheck = flag;
      });
    },
    /** ************************************************************************ */
    /**
     * 获取表头设置
     * @description  默认取columnList
     * */
    getTabulation() {
      this.getApi({ code: this.sortableTableCode })
        .then(res => {
          let titleList = [];
          const remoteVersion = res.flag || 1;
          if (Array.isArray(res)) {
            titleList = res;
          }
          if (Array.isArray(res.titleList)) {
            titleList = res.titleList;
          }
          if (titleList.length) {
            this.handleGet && this.handleGet(res, titleList);
            // 判断版本号，不一致时，不使用远程数据
            if (
              titleList.length &&
              remoteVersion === this.labelsVersion &&
              !this.tableSortableDisabled
            ) {
              this.setList(titleList);
            }
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    /**
     * 设置表头设置
     * @description  将变更后的columnList储存
     * */
    saveTabulation(v) {
      // v为空时，恢复默认的列设置（以前端的默认值为准)
      if (!v) {
        console.log('v为空时，恢复默认的列设置');
        this.setList(this.defaultLabels);
        v = copy(this.defaultLabels);
      }
      // 最新的index为sort上传
      const DATA = v.map((e, i) => {
        return { ...e, sort: i };
      });
      this.setApi({
        code: this.sortableTableCode,
        tabulationTitleDTOS: DATA,
        flag: this.labelsVersion || 1,
      }).then(res => {
        this.handleSet && this.handleSet(res);
      });
    },
    /* 列拖拽 */
    startColumnDropFn() {
      let element = this.el;
      if (this.tableSortableDisabled) {
        console.warn('startColumnDropFn Stope');
        return;
      } else {
        console.warn('startColumnDropFn');
      }
      if (typeof element !== 'object') {
        element = document.querySelector(element);
      }
      // console.log(element);
      // console.log(this.dragElement);

      Sortable.create(element, {
        disabled: this.tableSortableDisabled,
        sort: true,
        filter: this.dragFilter,
        draggable: this.dragElement,
        dataIdAttr: this.dataIdAttr,
        animation: 180,
        delay: 0,
        onEnd: evt => {
          let [oldIndex, newIndex] = [evt.oldIndex, evt.newIndex];

          oldIndex = evt.oldIndex - this.startIndex;
          newIndex = evt.newIndex - this.startIndex;

          const OLD_INDEX = this.columnList.findIndex(e => e.prop == this.dropCols[oldIndex].prop);
          const NEW_INDEX = this.columnList.findIndex(e => e.prop == this.dropCols[newIndex].prop);

          const TEMP = copy(this.columnList);
          const ITEM_ARRAY = TEMP.splice(OLD_INDEX, 1);
          TEMP.splice(NEW_INDEX, 0, ITEM_ARRAY[0]);

          this.setList(TEMP);

          this.saveTabulation(this.columnList);
        },
      });
    },
    // 当拖动表头改变了列的宽度的时候会触发该事件
    handleHeaderDragend({ $rowIndex, column, cell, columnIndex, $columnIndex, $event }) {
      console.log(...arguments);
      const newWidth = cell.clientWidth;
      // if (Math.abs(newWidth - oldWidth) > 5) {
      this.setColumn('width', newWidth, column.field);
      // }
    },

    /** * context-menu */
    // 当某一列的表头被鼠标右键点击时触发该事件
    handleHeaderContextmenu({ type, column, columnIndex, $event }) {
      const e = $event;
      e.preventDefault();
      const menuMinWidth = 105;
      const offsetLeft = this.$el.getBoundingClientRect().left; // container margin left
      const offsetWidth = this.$el.offsetWidth; // container width
      const maxLeft = offsetWidth - menuMinWidth; // left boundary
      const left = e.clientX - offsetLeft + 15; // 15: margin right

      if (left > maxLeft) {
        this.fixedConfig.left = maxLeft;
      } else {
        this.fixedConfig.left = left;
      }

      this.fixedConfig.top = e.clientY - this.fixedConfigTop;
      this.fixedConfig.visible = true;
      this.fixedConfig.property = column.property;
    },
    closeFixedConfig() {
      this.fixedConfig.visible = false;
    },
    setColumn(key, value, property) {
      property = property || this.fixedConfig.property;
      const INDEX = this.columnList.findIndex(col => col.prop == property);
      const ITEM = copy(this.columnList[INDEX]);
      ITEM[key] = value;
      const TEMP = copy(this.columnList);
      TEMP[INDEX] = ITEM;
      this.setList(TEMP);

      this.saveTabulation(this.columnList);
    },
  },
};
</script>
<style lang="scss" scoped>
.flex {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
.top-right-btn {
  margin-bottom: 10px;
}
::v-deep .el-checkbox {
  margin-bottom: 10px;
}
::v-deep .el-checkbox__label {
  min-width: 150px;
}
@media (max-width: 550px) {
  .flex {
    display: block;
  }
}

/*** context-menu */
.context-menu {
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
