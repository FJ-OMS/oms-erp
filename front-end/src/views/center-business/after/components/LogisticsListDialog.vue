<template>
  <el-dialog
    v-if="visible"
    title="查看退货物流信息"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :visible.sync="visible"
    width="680px"
  >
    <el-table :data="tableData" stripe border :header-cell-style="$rowStyle.DEFAULT">
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column prop="logisticsCompanyName" label="物流公司" align="center" />
      <el-table-column prop="logisticsNo" label="物流单号" align="center" />
      <el-table-column prop="bjTableAction" label="操作" width="120">
        <template slot-scope="props">
          <BjCopyIcon type="el-button" :copy="props.row.logisticsNo" />
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script>
import { afterApi } from '@/api';
export default {
  name: 'AfterLogisticsListDialog',
  data() {
    return {
      visible: false,
      tableData: [],
    };
  },
  methods: {
    show(id) {
      this.visible = true;
      // TODO:  赋值有问题
      afterApi.getAfterSaleLogistics({ orderId: id }).then(res => {
        if (Array.isArray(res)) {
          this.tableData = [...res];
        } else {
          this.tableData = [];
        }
      });
    },
    hide() {
      this.visible = false;
    },
  },
};
</script>

<style></style>
