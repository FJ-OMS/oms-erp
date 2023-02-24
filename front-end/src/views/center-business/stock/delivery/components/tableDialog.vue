<template>
  <div>
    <BjDialog title="查看出库结果单号" :visible="visible" width="500px">
      <el-table :data="data" border stripe style="width: 100%">
        <el-table-column type="index" label="序号" />
        <el-table-column prop="code" label="出库结果单号" align="center">
          <template slot-scope="scope">
            <span class="link">
              <span @click="onView(scope.row)">{{ scope.row.code }}</span>
            </span>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <el-button type="primary" @click="comfirm">确 定</el-button>
      </template>
    </BjDialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data: [],
      visible: false,
    };
  },
  methods: {
    show(data) {
      this.data = data;
      this.visible = true;
    },
    hide() {
      this.visible = false;
    },
    onView(row) {
      this.$toPage('BusinessStockDeliveryResultView', { id: row.id });
    },
    comfirm() {
      this.visible = false;
    },
  },
};
</script>

<style></style>
