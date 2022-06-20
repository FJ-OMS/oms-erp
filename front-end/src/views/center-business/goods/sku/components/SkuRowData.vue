<template>
  <el-dialog
    v-if="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
    append-to-body
    class="bj-dialog-list"
    :class="classID"
  >
    <el-table
      ref="multipleTable"
      :data="tableData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
    >
      <el-table-column v-for="(col, i) in columns" :key="i" :prop="col.prop" :label="col.label">
      </el-table-column>
    </el-table>
  </el-dialog>
</template>
<script>
import { getOffsetTop } from '@/utils/index';

export default {
  name: 'SkuRowData',
  props: {},
  data() {
    return {
      title: '生成SKU的属性查看',
      tableData: [],
      columns: [],
      /** * */
      visible: false,
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
  },
  watch: {
    visible() {
      if (this.visible) {
        this.$nextTick(() => {
          this.maxHeight =
            document.documentElement.clientHeight -
            getOffsetTop(`.${this.classID} .el-table__header-wrapper`) * 2 -
            100 +
            'px';
        });
      }
    },
  },

  mounted() {},
  methods: {
    show(d) {
      // 获取默认值
      this.columns = [];
      this.tableData = [];
      if (d) {
        const row = {};
        d.forEach(e => {
          row[e.attributeName] = e.attributeValue;
          this.columns.push({
            label: e.attributeName,
            prop: e.attributeName,
          });
        });
        this.tableData.push(row);
      }

      this.visible = true;
    },
    /* 关闭弹窗 */
    handleClose() {
      this.visible = false;
    },
  },
};
</script>
<style lang="scss"></style>
