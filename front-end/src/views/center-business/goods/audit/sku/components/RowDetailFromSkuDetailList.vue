<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" width="600px" :before-close="handleClose">
    <el-table :data="tableData" border :show-header="false" :cell-style="handleCellStyle">
      <el-table-column prop="name" align="center" />
      <el-table-column prop="value" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.prop.includes('Price')">
            {{ $number2money(scope.row.value) }}
          </span>
          <span v-else>{{ scope.row.value }}</span>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script>
export default {
  name: 'RowDetailFromSkuDetailList',
  props: {
    cols: {
      type: Array,
      default: () => {
        return [];
      },
    },
    handleCellStyle: Function,
  },
  data() {
    return {
      dialogVisible: false,
      title: 'SKU 详情',
      tableData: null,
    };
  },
  methods: {
    handleClose() {
      this.dialogVisible = false;
    },
    show(row) {
      this.tableData = this.cols.map(col => {
        return {
          name: col.label,
          value: row[col.prop],
          prop: col.prop,
        };
      });

      this.dialogVisible = true;
    },
  },
};
</script>

<style></style>
