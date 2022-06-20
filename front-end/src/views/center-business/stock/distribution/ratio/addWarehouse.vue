<template>
  <div class="app-container">
    <BjDivider
      >基础信息
      <template slot="right"
        ><el-button @click="tryCancel">取 消</el-button>
        <el-button type="primary" @click="handleConfirm">保 存</el-button>
      </template>
    </BjDivider>

    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="供货仓类型：" prop="supportWarehouseType">
            <BjSelectLov
              v-model="form.supportWarehouseType"
              constant-key="STOCK_WAREHOUSE_TYPE_SON_4_LIST"
              @change="onChangeSupportWarehouseType"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label=" 供货仓名称：" prop="supportWarehouseId">
            <BjSelectWarehouse
              v-model="form.supportWarehouseId"
              :type-code="supportTypeCodeList"
              @change="onChangeSupportWarehouseId"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="供货仓编码：" prop="supportWarehouseCode">
            <el-input :maxlength="MAX_LENGTH.DEFAULT" disabled :value="form.supportWarehouseCode" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <BjDivider
      >商品明细
      <template slot="button">
        <el-button type="primary" @click="dialogListGoodsShow()">添加商品</el-button>
      </template>
    </BjDivider>

    <el-table
      ref="multipleTable"
      :data="tableDataSku"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
    >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column type="index" width="55" align="center" label="序号" />
      <el-table-column prop="categoryName" label="商品分类" />
      <el-table-column prop="spuCode" label="商品编码" />
      <el-table-column prop="spuName" label="商品名称" />
      <el-table-column prop="skuCode" label="SKU编码" />
      <el-table-column fixed="right" label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" class="delete-btn" @click="onDeleteSku(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <BjDivider>接收仓库 </BjDivider>

    <el-table
      ref="multipleTable"
      :data="tableDataWarehouse"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
    >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- @selection-change="handleSelectionChange" -->
      <el-table-column type="index" width="55" align="center" label="序号" />
      <el-table-column prop="receiveTypeCode" label="仓库类型" align="center">
        <template slot-scope="scope">
          {{ $store.state.values.STOCK_WAREHOUSE_TYPE.bjGet(scope.row.receiveTypeCode) }}
        </template>
      </el-table-column>
      <el-table-column prop="receiveCode" label="仓库编码" align="center" />
      <el-table-column prop="receiveName" label="仓库名称" align="center" />
      <el-table-column prop="structureDistributionQty" label="仓库结构比例" align="center">
        <template slot-scope="props"> {{ props.row.structureDistributionQty }} % </template>
      </el-table-column>

      <el-table-column prop="distributionQty" label="商品仓位比例" align="center">
        <template slot-scope="props">
          <el-input
            v-model="props.row.distributionQty"
            :maxlength="MAX_LENGTH.DEFAULT"
            style="width: 50px"
          />
          %
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
            class="delete-btn"
            @click="onDeleteWarehouse(scope.row, scope.$index)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- <BjDialogList
      ref="dialogListWarehouse"
      :config="dialogListWarehouseConfig"
      multiple
      @confirm="onConfirmDialogList"
    /> -->
    <BjDialogList
      ref="dialogListGoods"
      :config="dialogListGoodsConfig"
      @confirm="onConfirmDialogListGoods"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';
const DEFAULT_SIZE = 30;
const getDefaultForm = () => {
  return {
    supportWarehouseId: null,
    supportWarehouseType: null,
    supportWarehouseTypeName: null,
    supportWarehouseCode: null,
    supportWarehouseName: null,
    skuId: null,
    freightSpaceList: [],
  };
};
export default {
  name: 'BusinessStockDistributionRatioAddWarehouse',
  components: {},
  mixins: [viewMiXin],
  data() {
    return {
      form: getDefaultForm(),
      rules: {
        supportWarehouseType: [{ required: true, message: '请选择', trigger: 'change' }],
        supportWarehouseId: [{ required: true, message: '请选择', trigger: 'change' }],
      },

      dialogListWarehouseConfig: {
        title: '选择仓库',
        columns: [
          {
            prop: 'code',
            label: '仓库编码',
          },
          {
            prop: 'name',
            label: '仓库名称',
          },
        ],
        search: [
          {
            placeholder: '请输入编码',
            key: 'receiveWarehouseCode',
          },
          {
            placeholder: '请输入名称',
            key: 'receiveWarehouseName',
          },
        ],
        defaultQuery: { receiveTypeCode: null, supportWarehouseId: null },
        actionApi: stockApi.getReceiveWarehouse,
        query: '',
      },

      dialogListGoodsConfig: {
        title: '选择商品',
        columns: [
          {
            prop: 'categoryName',
            label: '商品分类',
          },
          {
            prop: 'spuCode',
            label: '商品编码',
          },
          {
            prop: 'spuName',
            label: '商品名称',
          },
          {
            prop: 'skuCode',
            label: 'SKU编码',
          },
        ],
        search: [
          {
            placeholder: '请输入商品名称',
            key: 'spuName',
          },
          {
            placeholder: '请输入商品编码',
            key: 'spuCode',
          },
          {
            placeholder: '请输入SKU编码',
            key: 'skuCode',
          },

          {
            placeholder: '请选择分类',
            key: 'categoryId',
            component: 'BjGoodsTypeTreeSelect',
          },
        ],
        defaultQuery: { supportWarehouseId: null },
        actionApi: stockApi.getMayDistributeSpacesGoods,
        query: '',
      },

      tableDataSku: [],
      tableDataWarehouse: [],

      ScrollReceive: null,
    };
  },
  computed: {
    supportTypeCodeList() {
      return this.form.supportWarehouseType ? [this.form.supportWarehouseType] : null;
    },
  },
  watch: {},

  mounted() {
    this.$store.dispatch('values/getValueList', 'STOCK.WAREHOUSE_TYPE');

    this.ScrollReceive = new this.$ScrollLoad(this.loadFun, {
      el: this.$refs.multipleTable.bodyWrapper,
      page: 1,
    });
  },
  methods: {
    // 显示 选择商品
    dialogListGoodsShow() {
      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.dialogListGoodsConfig.defaultQuery.supportWarehouseId = this.form.supportWarehouseId;
        this.$refs.dialogListGoods.show();
      });
    },
    // 删除 商品
    onDeleteSku() {
      this.tableDataSku = [];
    },

    onChangeSupportWarehouseType(v, item) {
      // this.form.supportWarehouseTypeName = item.label;
      // 实仓 =》 比例虚仓
      // 比例虚仓 =》 渠道仓
      if (v === 'real') {
        this.form.receiveWarehouseType = 'ratio-virtual';
        this.form.receiveWarehouseTypeName = '比例虚仓';
      } else if (v === 'ratio-virtual') {
        this.form.receiveWarehouseType = 'channel';
        this.form.receiveWarehouseTypeName = '渠道仓';
      } else {
        this.form.receiveWarehouseType = '';
        this.form.receiveWarehouseTypeName = '';
      }

      // this.getReceiveWarehouse();
    },

    // 供货仓变更时，删除商品
    onChangeSupportWarehouseId(v, item) {
      this.form.supportWarehouseCode = item.code;
      this.form.supportWarehouseName = item.name;
      this.tableDataSku = [];
    },
    // 选中商品回写
    onConfirmDialogListGoods(data) {
      this.tableDataSku = data;
      this.form.skuId = this.tableDataSku[0].skuId;

      this.loadReceive(1);
      this.ScrollReceive.init();
    },

    // 删除 接收仓库
    onDeleteWarehouse(row, index) {
      if (row.id) {
        this.$confirm(`您正在删除 ${row.receiveName}，是否继续？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.tableDataWarehouse.splice(index, 1);
            this.deleteApi({
              ids: [row.id],
            });
          })
          .catch(() => {});
      } else {
        this.tableDataWarehouse.splice(index, 1);
      }
    },

    loadReceive(j) {
      if (j === undefined) {
        j = this.ScrollReceive.page();
      }
      console.log('loadSupport(): j=', j);
      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.getWarehousesReceive({
          skuId: this.form.skuId,
          supportWarehouseId: this.form.supportWarehouseId,
          page: j,
          size: DEFAULT_SIZE,
          flag: false,
        });
      });
    },

    // 已分配接收仓列表
    getWarehousesReceive(API_DATA) {
      stockApi.getDistributeSpacesWarehouse(API_DATA).then(res => {
        this.ScrollReceive.maxPage(res.totalPages); // 最大页码，控制滚动加载
        this.ScrollReceive.page(true); // 下一页
        if (res.number === 0) {
          this.tableDataWarehouse = res.content || [];
        } else {
          this.tableDataWarehouse = this.tableDataWarehouse.concat(res.content || []);
        }
      });
    },
    handleConfirm() {
      if (this.tableDataSku.length == 0) {
        this.$message.warning('请先选择商品');
        return;
      }
      if (this.tableDataWarehouse.length == 0) {
        this.$message.warning('请先选择接收仓库');
        return;
      }
      const API_DATA = {
        freightSpaceList: this.tableDataWarehouse.map(e => {
          return {
            qty: Number(e.distributionQty || 0),
            receiveWarehouseId: e.receiveWarehouseId,
          };
        }),
        skuId: this.tableDataSku[0].skuId,
        supportWarehouseId: this.form.supportWarehouseId,
      };
      console.log('确认', API_DATA);

      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.addApi(API_DATA);
      });
    },

    addApi(API_DATA) {
      stockApi.addBatchDistributeSpacesRatio(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete('BusinessStockDistributionRatioIndex');
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      this.addApi(API_DATA);
    },
    deleteApi(API_DATA) {
      stockApi.deleteDistributeSpacesGoods(API_DATA).then(res => {});
    },
  },
};
</script>
<style lang="scss" scoped>
.title {
  font-size: 14px;
  font-weight: 400;
}
.flex-box-center {
  align-items: center;
  justify-content: center;
  display: flex;
  flex-direction: column;
  padding-top: 30px;
}
.button-arrow {
  width: 60px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 20px auto;
  cursor: pointer;
  font-size: 14px;
  i {
    font-size: 32px;
    font-weight: bold;
  }
}
.el-form-item {
  margin-bottom: 16px;
}
</style>
