<template>
  <el-table :data="tableData" stripe border :header-cell-style="$rowStyle.DEFAULT">
    <!-- meaning: "天猫"  orderSeq: 1  value: "TM" -->
    <el-table-column type="index" label="序号" />
    <el-table-column prop="content" label="操作内容" />
    <el-table-column prop="time" label="操作时间" width="165" />
    <el-table-column prop="createName" label="操作人" width="165" />
  </el-table>
</template>
<script>
// import { settingApi } from '@/api';
export default {
  name: 'SettingFinanceStatementBaseLog',
  data() {
    return {
      tableData: [
        {
          content: '开启渠道',
          time: '  2021-05-14 17:06:43',
          createName: '老大哥',
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
