<template>
  <div>
    <el-table
      :data="tableData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      max-height="520px"
      @selection-change="handleSelectionChange"
    >
      <!-- meaning: "天猫"  orderSeq: 1  value: "TM" -->
      <el-table-column type="selection" label="序号" />
      <el-table-column type="index" label="序号" />
      <el-table-column prop="name" label="店铺名称" />
      <el-table-column prop="code" label="店铺编码" />
    </el-table>

    <p class="tips">没有您想要设置对账的店铺？<span></span> 去新增店铺</p>
  </div>
</template>
<script>
// import { settingApi } from '@/api';
export default {
  name: 'SettingFinanceStatementBaseShop',
  data() {
    return {
      selectedRow: [],
      tableData: [
        {
          name: '天猫_九牧官方旗舰店_分销_2000',
          code: 'DP0001',
        },
      ],
    };
  },
  computed: {
    SCHL_CHANNEL_TYPE_LIST() {
      return this.$store.state.values['SCHL_CHANNEL_TYPE'] || [];
    },
  },
  watch: {},

  mounted() {
    this.$store.dispatch('values/getValueList', 'SCHL.CHANNEL_TYPE');
  },
  methods: {
    handleSelectionChange(d) {
      this.selectedRow = d;
      console.log(d);
    },
    command(row, key) {
      console.log(...arguments);
      this.dialog[key].visible = true;
    },
    onStatus(flag, row) {
      console.log(flag, row);
      // this.operationApi({
      //   ids: [row.id],
      //   status: flag,
      // });
    },
  },
};
</script>
<style lang="scss" scoped></style>
