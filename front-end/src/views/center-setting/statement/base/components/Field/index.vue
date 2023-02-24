<template>
  <div>
    <el-button type="primary" class="main-btn" @click="onAdd">新增字段</el-button>

    <el-table
      :data="tableData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      max-height="520px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="index" label="序号" />
      <el-table-column prop="name" label="字段名称">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.name"
            :maxlength="MAX_LENGTH.DEFAULT"
            @change="onStatus($event, scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="code" label="排序" width="80">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.code"
            :maxlength="MAX_LENGTH.DEFAULT"
            @change="onStatus($event, scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="state" label="状态" width="80" />
      <el-table-column prop="bjTableAction" label="操作" width="300">
        <template slot-scope="props">
          <el-button type="text" @click="command(props.row, '')">修改</el-button>
          <el-button type="text" @click="command(props.row, '0')">禁用</el-button>
          <el-button type="text" @click="command(props.row, '1')">启用</el-button>
        </template>
      </el-table-column>
    </el-table>

    <span slot="footer" class="footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </span>
  </div>
</template>
<script>
// import { settingApi } from '@/api';
export default {
  name: 'SettingFinanceStatementBaseField',
  data() {
    return {
      selectedRow: [],
      tableData: [
        {
          name: '账务流水号',
          code: '1',
          state: '启用',
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
    onAdd() {
      this.tableData.push({});
    },
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
    handleClose() {
      this.$emit('handleColse');
    },
    confirm() {
      this.$emit('handleColse');
    },
  },
};
</script>
<style lang="scss" scoped></style>
