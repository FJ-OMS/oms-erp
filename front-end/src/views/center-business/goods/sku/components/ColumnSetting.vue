<!--  设置表格的列显示 ----->
<template>
  <el-dialog
    v-if="dialogVisible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    title="设置属性展示"
    :visible.sync="dialogVisible"
    width="55%"
    :destroy-on-close="true"
    :before-close="dialogClose"
    append-to-body
  >
    <el-checkbox :value="checkAll" :indeterminate="isIndeterminate" @change="changeCheckAll"
      >全选</el-checkbox
    >
    <div style="margin: 15px 0;" />

    <template v-for="(item, index) in columnList">
      <el-checkbox
        :key="index"
        v-model="columnList[index].isCheck"
        :label="item.label"
        :disabled="item.disabled"
      />
    </template>

    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogClose">取 消</el-button>
      <el-button type="primary" @click="dialogConfirm">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
const copy = arr => {
  return JSON.parse(JSON.stringify(arr));
};
export default {
  name: 'ColumnSetting',
  props: {
    defaultLabels: {
      type: Array,
      default: () => {
        return [];
      },
    },
    handleColumn: Function, // column变更时
  },
  data() {
    return {
      tempString: [],
      dialogVisible: false,
      checkedValues: [],
      isIndeterminate: true,
      checkAll: false,
      columnList: [],
    };
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
      },
      deep: true,
      immediate: true,
    },
  },
  created() {},
  mounted() {
    this.setList(this.defaultLabels);
  },
  methods: {
    // 给外部设置里面的columList
    setList(d) {
      this.columnList = copy(d);
    },
    // 打开
    show(d) {
      this.dialogVisible = true;

      if (d) {
        this.setList(d);
      }

      this.tempString = JSON.stringify(this.columnList);
    },
    /* 关闭弹窗 */
    dialogClose() {
      this.dialogVisible = false;
      this.setList(JSON.parse(this.tempString));
    },

    // 确定保存
    dialogConfirm() {
      if (this.columnList.length > 0) {
        this.dialogVisible = false;
        this.$emit('handleColumn', this.columnList);
      } else {
        this.$message({
          message: '至少选择一列属性显示在表格中',
          type: 'warning',
        });
      }
    },

    // 全选事件
    changeCheckAll(flag) {
      this.isIndeterminate = false;
      this.columnList.forEach(item => {
        console.log(item);
        if (item.disabled) {
          item.isCheck = true;
        } else {
          item.isCheck = flag;
        }
      });
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
</style>
