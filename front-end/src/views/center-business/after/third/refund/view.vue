<template>
  <div class="app-container bj-label-colon label-width-6 bj-el-input-disabled">
    <BjDivider right-fixed
      >仅退款单详情
      <template slot="right">
        <el-button @click="handleClose">返 回</el-button>
        <!-- <el-button  type="primary" @click="showLog">操作日志</el-button> -->
      </template>
    </BjDivider>

    <div class="bj-row mt20">
      <div class="bj-col bj-col-14">
        <!-- 转单状态 0- 正常 1- 异常 -->
        <div v-if="baseInfo.exceptionStatus == 0" class="bj-form-item">
          <i class="el-icon-circle-check status-icon-success"></i>
          <div class="flex align-items ml20">
            <div class="flex" style="font-weight: bold; font-size: 16px">转单正常</div>
            <div class="flex">已成功转为内部仅退款单</div>
          </div>
        </div>

        <div v-if="baseInfo.exceptionStatus == 1" class="bj-form-item">
          <i class="el-icon-warning-outline status-icon-err"></i>
          <div class="flex align-items ml20">
            <div class="flex" style="font-weight: bold; font-size: 16px">转单异常</div>
            <div class="flex">原因：{{ baseInfo.exceptionReason }}</div>
          </div>
        </div>
      </div>

      <!-- <div class="bj-col bj-col-24  border-top">
        <div class="bj-form-item">
          <el-button type="primary">转正常单</el-button>
          <el-button>作废</el-button>
        </div>
      </div> -->
    </div>

    <div class="bj-row mt20">
      <!-- 退款信息 -->
      <div class="bj-col bj-col-6">
        <div class="bj-form-title">基础信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">店铺</div>
          <div class="bj-form-item-value">
            {{ baseInfo.storeName }}
            <span class="F68710">（{{ SCHL_CHANNEL_TYPE.bjGet(baseInfo.channel) }}）</span>
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退款原因</div>
          <div class="bj-form-item-value">
            {{ BJ_AFTER_REFUND_ONLY_REASONS.bjGet(baseInfo.reason) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退款阶段</div>
          <div class="bj-form-item-value">
            {{ INTERNAL_AFTER_STAGE_STATUS.bjGet(baseInfo.stage) }}
          </div>
        </div>

        <div class="bj-form-item">
          <div class="bj-form-item-label">退款备注</div>
          <div class="bj-form-item-value">{{ baseInfo.remarks }}</div>
        </div>
      </div>
      <!-- 买家信息 -->
      <div class="bj-col bj-col-6">
        <div class="bj-form-title">买家信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家昵称</div>
          <div class="bj-form-item-value">{{ baseInfo.buyerNick }}</div>
        </div>
      </div>
      <!-- 店铺信息 -->
      <div class="bj-col bj-col-6">
        <div class="bj-form-title">关联单据</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方退款单号</div>
          <div class="bj-form-item-value">
            {{ baseInfo.outRefundNo }}
            <BjCopyIcon :copy="baseInfo.outRefundNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方订单号</div>
          <div class="bj-form-item-value">
            <span class="link" @click="$toBusinessOrderThirdView(baseInfo.outerOrderNo)">{{
              baseInfo.outerOrderNo
            }}</span>
            <BjCopyIcon :copy="baseInfo.outerOrderNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部退款单号</div>
          <div class="bj-form-item-value">
            <span
              class="link"
              @click="$toBusinessAfterInternalRefundView(baseInfo.innerRefundNo)"
              >{{ baseInfo.innerRefundNo }}</span
            >
            <BjCopyIcon :copy="baseInfo.innerRefundNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部订单号</div>
          <div class="bj-form-item-value">
            <span class="link" @click="$toBusinessOrderInternalView(baseInfo.innerOrderNo)">{{
              baseInfo.innerOrderNo
            }}</span>

            <BjCopyIcon :copy="baseInfo.innerOrderNo" />
          </div>
        </div>
      </div>
      <div class="bj-col bj-col-6 border-no">
        <div class="bj-form-title empty"></div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方退款时间</div>
          <div class="bj-form-item-value">
            {{ baseInfo.refundTime }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">创建时间</div>
          <div class="bj-form-item-value">
            {{ baseInfo.creationDate }}
          </div>
        </div>
      </div>
    </div>
    <div class="bj-row mt20">
      <!-- 退款商品信息 -->
      <div class="bj-col bj-col-24">
        <div class="bj-form-title">退款商品信息</div>
        <el-table :data="tableData" stripe border :header-cell-style="$rowStyle.DEFAULT">
          <el-table-column type="index" label="序号" />
          <el-table-column prop="spuName" label="商品名称" show-overflow-tooltip />
          <el-table-column prop="spuCode" label="商品编码" show-overflow-tooltip />
          <el-table-column prop="skuName" label="SKU名称" show-overflow-tooltip />
          <el-table-column prop="skuCode" label="SKU编码" show-overflow-tooltip />
          <el-table-column prop="outerSalePrice" label="三方销售单价">
            <template slot-scope="scope">
              {{ $number2money(scope.row.outerSalePrice) }}
            </template>
          </el-table-column>
          <el-table-column prop="quantity" label="退款数量" width="80" />
          <el-table-column prop="totalAmount" label="退款金额" width="120">
            <template slot-scope="scope">
              {{ $number2money(scope.row.totalAmount) }}
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { afterApi } from '@/api';
import {
  AFTER_ORDER_TYPES,
  AFTER_THIRD_REFUND_TRANSFER_STATUS,
  INTERNAL_AFTER_STAGE_STATUS,
} from '@/utils/constants';
const getDefaultForm = () => {
  return {
    thirdPartyAfterReceiverInfo: {
      address: null,
      city: null,
      cityCode: null,
      district: null,
      districtCode: null,
      id: null,
      mobile: null,
      outReturnNo: null,
      outerOrderNo: null,
      province: null,
      provinceCode: null,
      receiveAddress: null,
      receiveCity: null,
      receiveCityCode: null,
      receiveDistrict: null,
      receiveDistrictCode: null,
      receiveMobile: null,
      receiveName: null,
      receiveProvince: null,
      receiveProvinceCode: null,
      receiveTown: null,
      returnName: null,
      town: null,
    },
    thirdPartyAfterSaleDetailsVO: [
      // {
      //   _innerMap: {},
      //   id:null,
      //   outRefundNo: null,
      //   outerOrderNo: null,
      //   outerSalePrice:null,
      //   quantity:null,
      //   skuCode: null,
      //   skuName: null,
      //   spuCode: null,
      //   spuName: null,
      //   totalAmount:null,
      //   type: null,
      // },
    ],
    thirdPartyAfterSaleHeader: {
      buyerNick: null,
      buyerRemarks: null,
      channel: null,
      exceptionReason: null,
      exceptionStatus: null,
      goodsQuantity: null,
      id: null,
      outReceiveInfo: null,
      outReceiveInfoMd5: null,
      outRefundNo: null,
      outerOrderNo: null,
      refundTime: null,
      stage: null,
      storeCode: null,
      storeId: null,
      storeName: null,
      totalAmount: null,
      type: null,
    },
    thirdPartyAfterSaleLogistics: {
      id: null,
      innerOrderNo: null,
      innerRefundNo: null,
      logisticsCompanyId: null,
      logisticsCompanyName: null,
      logisticsNo: null,
      outerOrderNo: null,
      outerRefundNo: null,
    },
  };
};

export default {
  name: 'BusinessAfterThirdRefundView',
  mixins: [viewMiXin],
  props: {
    isEdit: {
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
    return {
      AFTER_ORDER_TYPES,
      AFTER_THIRD_REFUND_TRANSFER_STATUS,
      INTERNAL_AFTER_STAGE_STATUS,
      form: getDefaultForm(),
      // 表单校验
      rules: {
        tenantId: [{ required: true, message: '请选择', trigger: 'change' }],
        enabledFlag: [{ required: true, message: '请选择', trigger: 'change' }],
        concCode: [{ required: true, message: '请输入', trigger: 'blur' }],
        concName: [{ required: true, message: '请输入', trigger: 'blur' }],
        executableId: [{ required: true, message: '请选择', trigger: 'change' }],
      },

      /** */
      tableData: [],
    };
  },
  computed: {
    id() {
      return this.$route.query.id || '';
    },
    code() {
      return this.$route.query.code || '';
    },
    querySearch() {
      return this.id + this.code;
    },
    SCHL_CHANNEL_TYPE() {
      return this.$store.state.values.SCHL_CHANNEL_TYPE || [];
    },
    BJ_AFTER_REFUND_ONLY_REASONS() {
      return this.$store.state.values.BJ_AFTER_REFUND_ONLY_REASONS || [];
    },
    baseInfo() {
      return this.form.thirdPartyAfterSaleHeader || {};
    },
  },
  watch: {
    querySearch: {
      handler(v) {
        if (v) {
          this.reFresh();
        }
      },
      immediate: true,
    },
  },

  mounted() {
    this.$store.dispatch('values/getValueList', 'SCHL.CHANNEL_TYPE');
    this.$store.dispatch('values/getAfterRefundOnlyReasons');
  },
  methods: {
    onInvoice() {
      // TODO:
      this.$message.warning('暂无');
    },
    reFresh() {
      afterApi.detailThirdRefundOrder({ orderCode: this.code, orderId: this.id }).then(res => {
        this.form = Object.assign({}, this.form, res);
        this.tableData = res.thirdPartyAfterSaleDetailsVO || [];
      });
    },
    handleConfirm(formName) {
      const API_DATA = {
        description: this.form.description,
        id: this.$route.query.id,
        objectVersionNumber: this.form.objectVersionNumber,
      };

      this.$refs[formName].validate(valid => {
        if (!valid) return this.$scrollToFormError();
        this.updateApi(API_DATA);
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },

    /**
     *
     *
     */

    // 修改API
    updateApi(API_DATA) {
      afterApi.updateStockProcess(API_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('保存成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 新增API
    // addApi(API_DATA) {
    //   afterApi.addStockProcess(API_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('保存成功');
    //       setTimeout(() => {
    //         this.reFresh();
    //       }, 500);
    //     }
    //   });
    // },
  },
};
</script>
<style lang="scss" scoped>
.app-container {
  width: 100%;
}

.bj-col-14 {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
  padding-bottom: 16px;
}
</style>
