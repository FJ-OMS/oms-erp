<template>
  <div class="app-container bj-label-colon">
    <BjDivider
      >基础信息
      <template v-slot:button>
        <el-button type="primary" class="xs" @click="$refs.dialogList.show()">选择逻辑仓</el-button>
      </template>

      <template v-slot:right>
        <el-button @click="tryCancel">取 消</el-button>
        <el-button type="primary" @click="handleConfirm('form')">保 存</el-button>
      </template>
    </BjDivider>

    <el-form label-width="130px" style="width: 600px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="逻数据同步单号">
            <span class="tips">系统自动生成</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="逻辑仓编码">
            {{ info.code }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="逻辑仓名称">
            {{ info.name }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注">
            <el-input
              v-model="info.remark"
              :maxlength="MAX_LENGTH.DEFAULT"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4 }"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <BjDivider
      >商品明细
      <template v-slot:button>
        <el-button type="primary" class="xs" @click="dialogListGoodsShow()">选择商品</el-button>

        <el-button
          :disabled="selectedRow.length == 0"
          type="primary"
          class="xs"
          @click="onDelSelected()"
          >删除商品</el-button
        >
      </template></BjDivider
    >
    <el-table
      ref="multipleTable"
      :data="tableData"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column type="index" width="55" align="center" label="序号" />

      <el-table-column prop="categoryName" label="商品分类" />
      <el-table-column prop="spuCode" label="商品编码" />

      <el-table-column prop="spuName" label="商品名称" />
      <el-table-column prop="skuCode" label="SKU编码" />
      <el-table-column prop="type" label="调整类型">
        <template slot-scope="props">
          <BjSelectLov v-model="props.row.type" constant-key="STOCK_ADJUST_TYPE_LIST" />
        </template>
      </el-table-column>
      <el-table-column prop="num" label="调整差异数量">
        <template slot-scope="props">
          <el-input v-model="props.row.num" :maxlength="MAX_LENGTH.DEFAULT" />
        </template>
      </el-table-column>
    </el-table>

    <BjDialogList
      ref="dialogList"
      :config="$BjDialogListConfig.stockWarehouseLogic()"
      @confirm="onConfirmDialogList"
    />
    <BjDialogList
      ref="dialogListGoods"
      :config="$BjDialogListConfig.skuDefault()"
      multiple
      @confirm="onConfirmDialogListGoods"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';

export default {
  name: 'BusinessStockSynchronizationAdd',
  mixins: [viewMiXin],
  data() {
    return {
      info: {
        name: null,
        code: null,
        remark: null,
      },

      tableData: [],

      selectedRow: [],
    };
  },
  computed: {},
  methods: {
    dialogListGoodsShow() {
      // this.dialogListConfigGoods.defaultQuery.warehouseCode = this.info.code;
      this.$refs.dialogListGoods.show();
    },
    handleSelectionChange(d) {
      console.log(d);
      this.selectedRow = [].concat(d);
    },
    // 获取info
    onConfirmDialogList(data) {
      this.info.id = data[0].id;
      this.info.code = data[0].code;
      this.info.name = data[0].name;
    },
    onConfirmDialogListGoods(data) {
      data.forEach(row => {
        const FLAG = this.tableData.find(e => e.id == row.id);
        if (!FLAG) {
          this.tableData.push({ ...row, num: null });
        }
      });
    },

    onDelSelected() {
      console.log(this.selectedRow.map(e => e.skuCode));
      this.selectedRow.forEach(row => {
        this.tableData.splice(
          this.tableData.findIndex(e => e.id == row.id),
          1,
        );
      });
      // 清除选中状态
      this.$refs.multipleTable.clearSelection();
    },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      if (!this.info || !this.info.id || !this.info.code) {
        this.$message.warning('请先选择逻辑仓');
        return;
      }

      if (this.tableData.length == 0) {
        this.$message.warning('请先选择商品');
        return;
      }

      if (this.tableData.some(e => !e.type || !e.num || e.num == 0)) {
        this.$message.warning('请将 调整类型 和 差异数量(不能为0) 填写完整');
        return;
      }
      const API_DATA = {
        // adjustCode: 'string',
        adjustDetailsReqDTOList: this.tableData.map(e => {
          return {
            num: e.num,
            skuCode: e.skuCode,
            tenantId: this.$store.getters.tenantId,
            type: e.type,
          };
        }),
        // id: 0,
        remark: this.info.remark,
        warehouseCode: this.info.code,
        warehouseId: this.info.id,
      };
      console.log('确认', API_DATA);

      this.addApi(API_DATA);
    },

    addApi(API_DATA) {
      stockApi.addAdjustRecords(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete('BusinessStockSynchronizationIndex');
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
