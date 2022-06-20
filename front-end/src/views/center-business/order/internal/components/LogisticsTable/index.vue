<template>
  <div class="bj-col bj-col-24 ">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="发货单" name="0">
        <DeliverTable />
      </el-tab-pane>
      <el-tab-pane label="包 裹" name="1">
        <PackageTable />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import DeliverTable from './DeliverTable';
import PackageTable from './PackageTable';
export default {
  components: { DeliverTable, PackageTable },
  data() {
    return {
      activeName: '1',
    };
  },
  created() {},
  methods: {},
};
</script>
