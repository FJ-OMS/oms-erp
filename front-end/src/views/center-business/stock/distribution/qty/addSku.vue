<template>
  <div class="app-container">
    <BjDivider
      >仓位数量分配添加
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
              :disabled="true"
              @change="onChangeSupportWarehouseType"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label=" 供货仓名称：" prop="supportWarehouseId">
            <BjSelectWarehouse
              v-model="form.supportWarehouseId"
              :type-code="['real']"
              @change="onChangeSupportWarehouseId"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="接收仓类型：" prop="receiveWarehouseType">
            <BjSelectLov
              v-model="form.receiveWarehouseType"
              constant-key="STOCK_WAREHOUSE_TYPE_SON_3_LIST"
              @change="onChangeReceiveWarehouseType"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="接收仓名称：" prop="receiveWarehouseId">
            <BjSelectLov
              v-model="form.receiveWarehouseId"
              :option-list="receiveWarehouseList"
              @change="onChangeReceiveWarehouseId"
            />
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="商品编码：" prop="spuCode">
            <el-input
              v-model="form.spuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="商品名称：" prop="spuName">
            <el-input
              v-model="form.spuName"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="商品分类：" prop="categoryId">
            <BjGoodsTypeTreeSelect v-model="form.categoryId" placeholder="请选择" clearable />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="SKU编码：" prop="skuCode">
            <el-input
              v-model="form.skuCode"
              :maxlength="MAX_LENGTH.DEFAULT"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
        </el-col>
      </el-row>
      <div class="flex justify-end">
        <el-button type="cyan" icon="el-icon-search" @click="onSearch()">查询</el-button>
        <el-button icon="el-icon-refresh" @click="onReset()">重置</el-button>
      </div>
    </el-form>
    <el-row type="flex">
      <!-- 左 -->

      <el-col :span="11">
        <p class="title">
          供货仓商品列表
          <span class="tips">
            仓库类型:实仓；仓库编码:{{ form.supportWarehouseCode }}；仓库名称:{{
              form.supportWarehouseName
            }}
          </span>
        </p>

        <el-table
          ref="tableSupport"
          :data="tableDataSupport"
          stripe
          border
          :header-cell-style="$rowStyle.DEFAULT"
          row-key="BJ_EBC_ID"
          height="413.55px"
          @selection-change="handleSelectionChangeSupport"
        >
          <el-table-column
            :selectable="selectableSupport"
            type="selection"
            width="55"
            align="center"
          />
          <el-table-column type="index" width="55" align="center" label="序号" />
          <el-table-column prop="categoryName" label="商品分类" />
          <el-table-column prop="spuCode" label="商品编码" />
          <el-table-column prop="spuName" label="商品名称" />
          <el-table-column prop="skuCode" label="SKU编码" />
          <!-- <el-table-column prop="qty" label="仓位数量" /> -->
        </el-table>
      </el-col>

      <!-- 中 -->

      <el-col :span="2" class="flex-box-center">
        <div class="button-arrow" @click="onSupport2ReceiveFn">
          <i class="el-icon-arrow-right" />
          <span>分配</span>
        </div>
        <div class="button-arrow" @click="onReceive2SupportFn">
          <i class="el-icon-arrow-left" />
          <span>撤回</span>
        </div>
      </el-col>

      <!-- 右 -->

      <el-col :span="11">
        <p class="title">
          接收仓商品列表
          <span class="tips">
            仓库类型:{{ form.receiveWarehouseTypeName }}；仓库编码:{{
              form.receiveWarehouseCode
            }}；仓库名称:{{ form.receiveWarehouseName }}
          </span>

          <el-button type="primary" @click="setQty">批量设置</el-button>
        </p>
        <el-table
          ref="tableReceive"
          :data="tableDataReceive"
          stripe
          border
          :header-cell-style="$rowStyle.DEFAULT"
          row-key="BJ_EBC_ID"
          height="400px"
          @selection-change="handleSelectionChangeReceive"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column type="index" width="55" align="center" label="序号" />
          <el-table-column prop="categoryName" label="商品分类" />
          <el-table-column prop="spuCode" label="商品编码" />
          <el-table-column prop="spuName" label="商品名称" />
          <el-table-column prop="skuCode" label="SKU编码" />
          <el-table-column prop="qty" label="仓位数量">
            <template slot-scope="props">
              <el-input v-model="props.row.qty" :maxlength="MAX_LENGTH.DEFAULT" />
              <!-- <span>{{ props.row.qty }}</span> -->
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';
const DEFAULT_PAGE = 1;
const DEFAULT_SIZE = 30; // 大一点，要超过高度必须
const getDefaultForm = () => {
  return {
    supportWarehouseId: null,
    supportWarehouseType: 'real',
    supportWarehouseTypeName: null,
    supportWarehouseCode: null,
    supportWarehouseName: null,

    receiveWarehouseId: null,
    receiveWarehouseType: null,
    receiveWarehouseTypeName: null,
    receiveWarehouseCode: null,
    receiveWarehouseName: null,

    categoryId: null,
    skuCode: null,
    spuCode: null,
    spuName: null,
  };
};
export default {
  name: 'BusinessStockDistributionQtyAddSku',
  components: {},
  mixins: [viewMiXin],
  data() {
    return {
      form: getDefaultForm(),
      rules: {
        receiveWarehouseType: [{ required: true, message: '请选择', trigger: 'change' }],
        receiveWarehouseId: [{ required: true, message: '请选择', trigger: 'change' }],
        supportWarehouseId: [{ required: true, message: '请选择', trigger: 'change' }],
      },
      selectedRowSupport: [],
      tableDataSupport: [],

      selectedRowReceive: [],
      tableDataReceive: [],

      receiveWarehouseList: [],

      // receivePage: DEFAULT_PAGE,
      // receiveEnd: false,
      size: DEFAULT_SIZE,

      ScrollSupport: null,
      ScrollReceive: null,
    };
  },
  computed: {},
  watch: {
    'form.supportWarehouseType'() {
      this.form.supportWarehouseId = null;
    },
    'form.receiveWarehouseType'() {
      this.form.receiveWarehouseId = null;
      this.getReceiveWarehouse();
    },
    'form.supportWarehouseId'() {
      this.form.receiveWarehouseId = null;
      this.getReceiveWarehouse();
    },
  },

  mounted() {
    this.ScrollSupport = new this.$ScrollLoad(this.loadSupport, {
      el: this.$refs.tableSupport.bodyWrapper,
      page: DEFAULT_PAGE,
    });
    this.ScrollReceive = new this.$ScrollLoad(this.loadReceive, {
      el: this.$refs.tableReceive.bodyWrapper,
      page: DEFAULT_PAGE,
    });
  },

  methods: {
    // 批量设置
    setQty() {
      if (this.selectedRowReceive.length == 0) {
        this.$message.warning('请选择 右侧 接收仓 商品');
        return;
      }
      this.$prompt('请输入仓位数量', '批量设置仓位数量', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^[0-9]+$/,
        inputErrorMessage: '数量格式不正确',
        closeOnClickModal: false,
      })
        .then(({ value }) => {
          this.tableDataReceive.map(row => {
            row.qty = Number(value || 0);
          });
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入',
          });
        });
    },
    // 供货仓多选：可选逻辑
    selectableSupport(row, index) {
      return !this.tableDataReceive.find(e => e.skuCode === row.skuCode);
    },
    // 分配 按钮 onSupport2ReceiveFn
    onSupport2ReceiveFn() {
      if (this.selectedRowSupport.length <= 0) {
        this.$message.warning('请选择 左侧 供货仓 商品');
        return;
      }
      this.selectedRowSupport.forEach(row => {
        if (!this.tableDataReceive.find(e => e.skuCode === row.skuCode)) {
          this.tableDataReceive.push({
            ...row,
            qty: null,
          });
        }
      });

      this.$refs.tableSupport.clearSelection();
    },
    // 撤回 按钮 onReceive2SupportFn
    onReceive2SupportFn() {
      if (this.selectedRowReceive.length <= 0) {
        this.$message.warning('请选择 右侧 接收仓 商品');
        return;
      }
      this.selectedRowReceive.forEach(row => {
        this.tableDataReceive.splice(
          this.tableDataReceive.findIndex(e => e.skuCode == row.skuCode),
          1,
        );
      });
      this.deleteApi({
        ids: this.selectedRowReceive.filter(e => e.id).map(e => e.id),
      });
      this.$refs.tableReceive.clearSelection();
    },
    // 点击查询
    onSearch(i, j) {
      this.tableDataSupport = [];
      this.tableDataReceive = [];

      this.ScrollSupport.init();
      this.ScrollSupport.init();

      this.loadSupport(i || DEFAULT_PAGE);
      this.loadReceive(j || DEFAULT_PAGE);
    },
    // 点击重置
    onReset() {
      this.form = getDefaultForm();
      this.tableDataSupport = [];
      this.tableDataReceive = [];
      this.ScrollSupport.isEnd = false;
      this.receiveEnd = false;
      this.ScrollSupport.page = 0;
      this.receivePage = 0;
    },
    onChangeSupportWarehouseType(v, item) {
      this.form.supportWarehouseTypeName = item.label;
    },
    // 接收仓类型选择 的回调
    onChangeReceiveWarehouseType(v, item) {
      this.form.receiveWarehouseTypeName = item.label;
    },
    // 供货仓选择 的回调
    onChangeSupportWarehouseId(v, item) {
      this.form.supportWarehouseCode = item.code;
      this.form.supportWarehouseName = item.name;
    },
    // 接收仓选择 的回调
    onChangeReceiveWarehouseId(v, item) {
      this.form.receiveWarehouseCode = item.code;
      this.form.receiveWarehouseName = item.name;
    },
    handleSelectionChangeSupport(data) {
      this.selectedRowSupport = [].concat(data);
    },
    handleSelectionChangeReceive(data) {
      this.selectedRowReceive = [].concat(data);
    },
    // 保存
    handleConfirm() {
      if (this.tableDataReceive.length == 0) {
        this.$message.warning('请先选择商品');
        return;
      }
      const API_DATA = {
        freightSpaceList: this.tableDataReceive.map(e => {
          return {
            qty: this.$infinityNumber(e.qty),
            skuId: e.skuId,
            skuCode: e.skuCode,
          };
        }),
        receiveWarehouseId: this.form.receiveWarehouseId,
        supportWarehouseId: this.form.supportWarehouseId,
      };
      console.log('确认', API_DATA);

      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isEdit) {
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    loadSupport(i) {
      console.log(11111111);
      if (i === undefined) {
        i = this.ScrollSupport.page();
      }
      console.log('loadSupport(): i=', i);
      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.getGoodsSupport({
          categoryId: this.form.categoryId,
          skuCode: this.form.skuCode,
          spuCode: this.form.spuCode,
          spuName: this.form.spuName,
          supportWarehouseId: this.form.supportWarehouseId,
          page: i,
          size: this.size,
        });
      });
    },
    loadReceive(j) {
      if (j === undefined) {
        j = this.ScrollReceive.page();
      }
      console.log('loadSupport(): j=', j);
      this.$refs['form'].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.getGoodsReceive({
          categoryId: this.form.categoryId,
          skuCode: this.form.skuCode,
          spuCode: this.form.spuCode,
          spuName: this.form.spuName,
          receiveWarehouseId: this.form.receiveWarehouseId,
          supportWarehouseId: this.form.supportWarehouseId,
          page: j,
          size: this.size,
        });
      });
    },
    getGoodsSupport(API_DATA) {
      stockApi.getMayDistributeSpacesGoods(API_DATA).then(res => {
        this.ScrollSupport.maxPage(res.totalPages); // 最大页码，控制滚动加载
        this.ScrollSupport.page(true); // 下一页
        if (res.number === 0) {
          this.tableDataSupport = res.content || [];
        } else {
          this.tableDataSupport = this.tableDataSupport.concat(res.content || []);
        }
      });
    },
    getGoodsReceive(API_DATA) {
      stockApi.getDistributeSpacesGoods(API_DATA).then(res => {
        this.ScrollReceive.maxPage(res.totalPages); // 最大页码，控制滚动加载
        this.ScrollReceive.page(true); // 下一页
        const DATA = (res.content || []).map(e => {
          return {
            ...e,
            qty: this.$infinityNumber(e.qty),
          };
        });
        if (res.number === 0) {
          this.tableDataReceive = [].concat(DATA);
        } else {
          this.tableDataReceive = this.tableDataReceive.concat(DATA);
        }
      });
    },
    // 接收仓的仓库列表
    getReceiveWarehouse() {
      if (!this.form.receiveWarehouseType || !this.form.supportWarehouseId) {
        return;
      }
      stockApi
        .getReceiveWarehouse({
          receiveTypeCode: this.form.receiveWarehouseType,
          supportWarehouseId: this.form.supportWarehouseId,
          page: 0,
          size: 1000,
        })
        .then(res => {
          this.receiveWarehouseList = (res.content || []).map(e => {
            return {
              label: e.name,
              value: e.id,
            };
          });
        });
    },
    addApi(API_DATA) {
      stockApi.addDistributeSpacesQty(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete('BusinessStockDistributionPlanIndex');
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
