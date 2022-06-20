<template>
  <div class="app-container bj-label-colon bj-el-input-disabled">
    <BjDivider>基础设置 </BjDivider>

    <el-table :data="tableData" stripe border :header-cell-style="$rowStyle.DEFAULT">
      <!-- meaning: "天猫"  orderSeq: 1  value: "TM" -->
      <el-table-column type="index" label="序号" />
      <el-table-column prop="meaning" label="渠道名称" />
      <el-table-column prop="onOff" label="开/关" width="120">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.onOff"
            :active-value="1"
            :inactive-value="0"
            disabled
            @change="onStatus($event, scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="bjTableAction" label="操作" width="300">
        <template slot-scope="props">
          <el-button type="text" @click="command(props.row, 'log')">操作记录</el-button>
          <el-button type="text" @click="command(props.row, 'field')">字段维护</el-button>
          <el-button type="text" @click="command(props.row, 'shop')">对账店铺维护</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 操作记录 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="操作记录"
      :visible.sync="dialog.log.visible"
      width="60%"
    >
      <Log />
    </el-dialog>
    <!-- 对账店铺维护 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="对账店铺维护"
      :visible.sync="dialog.shop.visible"
      width="60%"
    >
      <Shop @handleColse="dialog.shop.visible = false" />
    </el-dialog>
    <!-- 字段维护 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="字段维护"
      :visible.sync="dialog.field.visible"
      width="60%"
    >
      <Field @handleColse="dialog.field.visible = false" />
    </el-dialog>
  </div>
</template>
<script>
// import { settingApi } from '@/api';
// import { EXECUTOR_TYPE_LIST, EXECUTOR_TYPE_JSON } from '@/utils/constants';
import Log from './components/Log/index.vue';
import Field from './components/Field/index.vue';
import Shop from './components/Shop/index.vue';
export default {
  name: 'SettingFinanceStatementBaseIndex',
  components: { Log, Field, Shop },
  data() {
    return {
      // EXECUTOR_TYPE_LIST,
      // EXECUTOR_TYPE_JSON,
      /** */
      dialog: { log: { visible: false }, shop: { visible: false }, field: { visible: false } },
    };
  },
  computed: {
    SCHL_CHANNEL_TYPE_LIST() {
      return this.$store.state.values['SCHL_CHANNEL_TYPE'] || [];
    },
    tableData() {
      return this.SCHL_CHANNEL_TYPE_LIST.map(e => {
        return {
          onOff: 1,
          ...e,
        };
      });
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
