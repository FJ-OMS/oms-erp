<template>
  <div class="app-container bj-label-colon">
    <BjDivider
      >基础信息
      <template slot="right">
        <template v-if="!isView">
          <el-button @click="tryCancel">取 消</el-button>
          <el-button type="primary" @click="handleConfirm('form')">确 定</el-button>
        </template>

        <el-button v-if="isView" @click="cancel">返 回</el-button>

        <template v-if="isView && $route.query.isVerify">
          <el-button
            v-if="info.status == 'wait'"
            type="primary"
            class="main-btn"
            @click="onStatus('pass', info)"
            >审核通过</el-button
          ><el-button v-if="info.status == 'wait'" type="danger" @click="onStatus('reject', info)"
            >审核拒绝</el-button
          ></template
        ></template
      >
    </BjDivider>

    <el-form
      ref="form"
      :model="form"
      label-width="130px"
      style="width: 600px"
      :rules="rules"
      class="gray"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="调拨单号" prop="code">
            <BjInputCode v-model="form.code" :disabled="isView" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="移出逻辑仓" prop="supportWarehouseId">
            <BjSelectWarehouse
              v-model="form.supportWarehouseId"
              :disabled="isView"
              :type-code="['logic']"
              @change="getSupport"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="移入逻辑仓" prop="receiveWarehouseId">
            <BjSelectWarehouse
              v-model="form.receiveWarehouseId"
              :disabled="isView"
              :type-code="['logic']"
              @change="getReceive"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="form.remark"
              :maxlength="MAX_LENGTH.DEFAULT"
              :disabled="isView"
              type="textarea"
              show-word-limit
              :autosize="{ minRows: 2, maxRows: 4 }"
              placeholder="请输入"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <BjDivider
      >商品明细
      <template v-if="!isView" v-slot:button>
        <el-button type="primary" class="xs" @click="dialogListGoodsShow()">添加商品</el-button>
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
      :data="form.detailList"
      stripe
      border
      :header-cell-style="$rowStyle.DEFAULT"
      row-key="BJ_EBC_ID"
      @selection-change="handleSelectionChange"
    >
      <el-table-column v-if="!isView" type="selection" width="55" align="center" />
      <el-table-column type="index" width="55" align="center" label="序号" />
      <el-table-column prop="spuName" label="商品名称" align="center" />
      <el-table-column prop="spuCode" label="商品编码" align="center" width="160" />
      <el-table-column prop="skuName" label="SKU名称" align="center" />
      <el-table-column prop="skuCode" label="SKU编码" align="center" width="160" />
      <el-table-column prop="outStockNum" label="移出逻辑仓在库库存" align="center" width="90" />
      <el-table-column prop="outStockUsableNum" label="可调拨数量" align="center" width="90" />
      <el-table-column prop="inStockNum" label="移入逻辑仓在库库存" align="center" width="90" />
      <el-table-column
        prop="inStockUsableNum"
        label="移入逻辑仓可用库存"
        align="center"
        width="90"
      />
      <el-table-column prop="planNum" label="调拨数量" align="center" width="90">
        <template slot-scope="props">
          <!-- 调拨数量之和不可大于移出逻辑仓可调拨数量 -->
          <BjInputNumber
            v-model="props.row.planNum"
            :disabled="isView"
            :max="props.row.outStockUsableNum"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="props">
          <el-button
            type="text"
            :disabled="isView"
            class="delete-btn"
            @click="onDelete(props.row, props.$index)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <BjDialogList
      ref="dialogListGoods"
      :config="dialogListConfigGoods"
      row-keys="skuCode"
      multiple
      @confirm="onConfirmDialogListGoods"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';
// import { randomID } from '@/utils/index';
const getDefaultForm = () => {
  return {
    id: null,
    code: null,
    receiveWarehouseId: null,
    receiveWarehouseCode: null,
    receiveWarehouseName: null,
    remark: null,
    supportWarehouseId: null,
    supportWarehouseCode: null,
    supportWarehouseName: null,
    detailList: [], // Array[调拨单商品详情]
  };
};
export default {
  name: 'BusinessStockTransferLogicViewForm',
  mixins: [viewMiXin],
  props: {
    isVerify: {
      type: Boolean,
      default: false,
    },
    isAdd: {
      type: Boolean,
      default: false,
    },
    isEdit: {
      type: Boolean,
      default: false,
    },
    isView: {
      type: Boolean,
      default: false,
    },
    info: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    const validate = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请选择移入逻辑仓'));
      } else if (value === this.form.supportWarehouseId) {
        callback(new Error('移出逻辑仓和移入逻辑仓不能是同一个'));
      } else {
        callback();
      }
    };
    return {
      dialogListType: null, // 前端自定义判断点击  receive / support
      form: getDefaultForm(),
      dialogListConfigGoods: this.$BjDialogListConfig.stockGoodsDefault('添加商品'),
      // 表单校验
      rules: {
        supportWarehouseId: [{ required: true, message: '请选择移出逻辑仓', trigger: 'change' }],
        receiveWarehouseId: [{ required: true, validator: validate, trigger: 'change' }],
      },
      selectedRow: [],
    };
  },
  computed: {
    isSelected() {
      return !!this.selectedRow.length;
    },
  },
  watch: {
    info: {
      handler(info) {
        if (info) {
          this.form = this.$lodash.clone(info);
        }
      },
    },
  },
  methods: {
    /** 审核 */
    onStatus(status) {
      this.$confirm(`是否审核${status === 'pass' ? '通过' : '不通过'}该数据？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        stockApi
          .auditAllotRecords({
            id: this.info.id,
            status,
          })
          .then(res => {
            if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
              this.$message.success('操作成功');
              setTimeout(() => {
                this.cancel();
              }, 500);
            }
          });
      });
    },
    dialogListGoodsShow() {
      this.$refs.form.validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.dialogListConfigGoods.defaultQuery = {
          warehouseId: [this.form.supportWarehouseId],
          // warehouseCode: this.form.supportWarehouseCode,
          usableExcludeZeroFlag: true,
        };
        // 需要添加nextTick否则参数传不进组件
        console.log('this.form.detailLis', this.form.detailLis);
        this.$refs.dialogListGoods.show(this.form.detailList);
      });
    },
    /** 获取移出仓其他数据 */
    getSupport(v, data) {
      this.form.supportWarehouseCode = data.code;
      this.form.supportWarehouseName = data.name;
      this.form.detailList = [];
    },
    /** 获取移入仓其他数据 */
    getReceive(v, data) {
      this.form.receiveWarehouseCode = data.code;
      this.form.receiveWarehouseName = data.name;
      this.form.detailList = [];
    },
    handleSelectionChange(data) {
      this.selectedRow = [].concat(data);
    },
    onConfirmDialogListGoods(data) {
      if (data.length) {
        stockApi
          .getAllotRecordsSkuStock({
            inStockId: this.form.receiveWarehouseId,
            outStockId: this.form.supportWarehouseId,
            skuCodeList: data.map(e => e.skuCode),
          })
          .then(res => {
            this.form.detailList = data.map(item => {
              return {
                ...item,
                ...res.find(e => e.skuCode === item.skuCode),
                planNum: item.planNum || 0,
              };
            });
          });
      }
    },

    onDelSelected() {
      this.$confirm('是否确定批量删除选中的数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        console.log(this.selectedRow.map(e => e.skuCode));
        this.selectedRow.forEach(row => {
          this.form.detailList.splice(
            this.form.detailList.findIndex(e => e.skuCode == row.skuCode),
            1,
          );
        });
        // 清除选中状态
        this.$refs.multipleTable.clearSelection();
        this.msgSuccess('删除成功');
      });
    },
    /** 删除 */
    onDelete(row, index) {
      this.$confirm('是否确定删除该数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.form.detailList.splice(index, 1);
        this.msgSuccess('删除成功');
      });
    },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      // 判断调拨数量是否符合提交要求（不小于1）
      const checkList = this.form.detailList.filter(item => item.planNum < 1);
      if (checkList.length) {
        this.$message.warning('调拨数量不能小于1');
        return;
      }

      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        const API_DATA = this.$lodash.clone(this.form);
        console.log('确认', API_DATA);
        if (this.isEdit) {
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    addApi(API_DATA) {
      stockApi.saveAllotRecords(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete('BusinessStockTransferLogicIndex');
          }, 500);
        }
      });
    },
    updateApi(API_DATA) {
      this.addApi(API_DATA);
    },
  },
};
</script>
<style lang="scss" scoped></style>
