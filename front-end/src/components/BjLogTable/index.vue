<template>
  <div>
    <el-table
      v-if="tableData.length"
      ref="multipleTable"
      :data="tableData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
    >
      <el-table-column type="index" width="55" align="center" label="序号" />
      <el-table-column prop="createdBy" label="操作用户" />
      <el-table-column prop="message" label="操作内容" />
      <el-table-column prop="creationDate" label="操作时间" />
    </el-table>

    <BjPagingTable
      v-if="hasApi"
      ref="BjPagingTable"
      :auto-fetch="true"
      :api="tableApi"
      :default-params="defaultParams"
    >
      <el-table-column type="index" width="55" align="center" label="序号" />
      <el-table-column prop="createdBy" label="操作用户" />
      <el-table-column prop="message" label="操作内容" />
      <el-table-column prop="creationDate" label="操作时间" />
    </BjPagingTable>
  </div>
</template>
<script>
export default {
  name: 'BjLogTable',
  props: {
    /**  1.直接传入数据展示   */
    tableData: {
      type: Array,
      default: function() {
        return [];
      },
    },

    /**  2.从接口获取数据   */
    // 接口
    tableApi: Function,
    // 默认传参
    defaultParams: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  data() {
    return {};
  },
  computed: {
    hasApi() {
      return typeof this.tableApi === 'function';
    },
  },
};
</script>
