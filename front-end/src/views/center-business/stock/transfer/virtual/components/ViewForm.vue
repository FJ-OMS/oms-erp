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
            v-if="info.status === 'wait'"
            type="primary"
            class="main-btn"
            @click="onStatus('pass', info)"
            >审核通过</el-button
          ><el-button v-if="info.status === 'wait'" type="danger" @click="onStatus('reject', info)"
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
        <el-col :span="24">
          <el-form-item label="移仓单号" prop="code">
            <BjInputCode v-model="form.code" :disabled="isView" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="移出虚仓" prop="supportWarehouseId">
            <BjSelectWarehouse
              v-model="form.supportWarehouseId"
              :disabled="isView"
              :type-code="['default-virtual', 'qty-virtual', 'interline-virtual']"
              @change="getSupport"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="移入虚仓" prop="receiveWarehouseId">
            <BjSelectWarehouse
              v-model="form.receiveWarehouseId"
              :disabled="isView"
              :type-code="['default-virtual', 'qty-virtual', 'interline-virtual']"
              @change="getReceive"
              @clear="onClear"
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
      <el-table-column prop="outStockNum" label="移出虚仓在库库存" align="center" width="90" />
      <el-table-column prop="outStockUsableNum" label="可移仓数量" align="center" width="90" />
      <el-table-column prop="inStockNum" label="移入虚仓在库库存" align="center" width="90" />
      <el-table-column prop="inStockUsableNum" label="移入虚仓可用库存" align="center" width="90" />
      <el-table-column prop="stockOutNum" label="缺货数量" align="center" width="90" />
      <el-table-column prop="planNum" label="移仓数量" align="center" width="90">
        <template slot-scope="props">
          <!-- 调拨数量之和不可大于移出逻辑仓可调拨数量 -->
          <BjInputNumber
            v-model="props.row.planNum"
            :disabled="isView"
            :max="props.row.outStockUsableNum"
          />
        </template>
      </el-table-column>
      <!-- 移入虚仓为数量虚仓时显示 -->
      <el-table-column
        prop="priorityReplenishStatus"
        label="是否优先补缺货订单"
        align="center"
        width="160"
      >
        <template slot-scope="props">
          <!-- 缺货数量大于0时可选 -->
          <BjSelectLov
            v-model="props.row.priorityReplenishStatus"
            :disabled="isView || props.row.stockOutNum == 0 || form.typeCode != 'qty-virtual'"
            :option-list="STATUS_YES_TRUE"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="props">
          <el-button
            v-if="
              (form.typeCode == 'qty-virtual' || form.receiveWarehouseTypeCode == 'qty-virtual') &&
                props.row.stockOutNum > 0
            "
            type="text"
            :disabled="props.row.planNum <= 0"
            text="移入虚仓为数量虚仓且有缺货的时候显示"
            @click="onAllot(props.row, props.$index)"
            >缺货订单分配</el-button
          >
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

    <!-- 添加商品弹出层 -->
    <BjDialogList
      ref="dialogListGoods"
      :config="dialogListConfigGoods"
      multiple
      row-keys="skuCode"
      @confirm="onConfirmDialogListGoods"
    />
    <!-- 缺货订单分配弹出层 -->
    <DialogStockoutOrderAllot
      ref="allotRef"
      :is-view="isView"
      :is-edit="isEdit"
      @confirm="getStockoutOrderAllotData"
    />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { stockApi } from '@/api';
// import { randomID } from '@/utils/index';
import DialogStockoutOrderAllot from '@/views/center-business/stock/components/DialogStockoutOrderAllot.vue';
import { STATUS_YES_TRUE } from '@/utils/constants';
const getDefaultForm = () => {
  return {
    id: null,
    code: null,
    typeCode: null,
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
  name: 'BusinessStockTransferVirtualViewForm',
  components: { DialogStockoutOrderAllot },
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
        callback(new Error('请选择移入虚仓'));
      } else if (value === this.form.supportWarehouseId) {
        callback(new Error('移出虚仓和移入虚仓不能是同一个'));
      } else {
        callback();
      }
    };
    return {
      STATUS_YES_TRUE,
      dialogListType: null, // 前端自定义判断点击  receive / support
      dialogListConfigGoods: this.$BjDialogListConfig.stockGoodsDefault('添加商品'),
      form: getDefaultForm(),
      // 表单校验
      rules: {
        receiveWarehouseId: [{ required: true, validator: validate, trigger: 'change' }],
        supportWarehouseId: [{ required: true, message: '请选择移出虚仓', trigger: 'change' }],
      },
      selectedRow: [],
      chooseIndex: 0,
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
          .auditDistributeVirtuals({
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
          usableExcludeZeroFlag: true,
        };
        // 需要添加nextTick否则参数传不进组件
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
      this.form.typeCode = data.typeCode;
      this.form.receiveWarehouseCode = data.code;
      this.form.receiveWarehouseName = data.name;
      this.form.detailList = [];
    },
    onClear() {
      this.form.typeCode = null;
    },
    handleSelectionChange(data) {
      this.selectedRow = [].concat(data);
    },
    onConfirmDialogListGoods(data) {
      if (data.length) {
        stockApi
          .getDistributeVirtualsSkuStock({
            inStockId: this.form.receiveWarehouseId, // 移入仓库id
            outStockId: this.form.supportWarehouseId, // 移出仓库id
            skuCodeList: data.map(e => e.skuCode), // sku编码集合
          })
          .then(res => {
            this.form.detailList = data.map(item => {
              return {
                ...item,
                ...res.find(e => e.skuCode === item.skuCode),
                planNum: 0, // 移仓数量
                priorityReplenishStatus: true, //  (boolean, optional): 是否优先补缺货订单 true是 false否 ,
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
    /** 缺货订单分配 */
    onAllot(row, index) {
      this.$refs.form.validateField('receiveWarehouseId', valid => {
        if (!valid) {
          this.chooseIndex = index;
          // 是否是查询或编辑页面
          const info = {
            api: 'getTransferStockoutDetailsList',
            queryParams: {
              transferDetailId: row.id, // 分仓计划详情id
              skuCode: row.skuCode, // sku编码
              warehouseId: this.form.receiveWarehouseId, // 移入仓id
            },
            planNum: row.planNum, // 计划供应数量
            list: row.stockoutOrderList || [], // 回写上次编辑的内容
          };
          this.$refs.allotRef.show(info);
        } else {
          this.$message.warning(valid);
        }
      });
    },
    /** 分配缺货订单回调 */
    getStockoutOrderAllotData(data) {
      // console.log('获取缺货数据', data);
      this.form.detailList[this.chooseIndex].stockoutOrderList = data;
    },
    reFresh() {
      this.$emit('reFresh');
    },
    handleConfirm(formName) {
      // 判断调拨数量是否符合提交要求（不小于1）
      const checkList = this.form.detailList.filter(item => item.planNum < 1);
      if (checkList.length) {
        this.$message.warning('移仓数量不能小于1');
        return;
      }

      const API_DATA = this.$lodash.clone(this.form);
      // 补货数量为0或者不填的数据不提交
      API_DATA.detailList = API_DATA.detailList.filter(item => item.replenishQty);
      console.log('确认', API_DATA);
      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        if (this.isEdit) {
          this.updateApi(API_DATA);
        } else {
          this.addApi(API_DATA);
        }
      });
    },
    addApi(API_DATA) {
      stockApi.saveDistributeVirtuals(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.complete('BusinessStockTransferVirtualIndex');
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
