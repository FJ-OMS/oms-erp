<template>
  <div class="app-container bj-label-colon label-width-6 bj-el-input-disabled">
    <BjDivider right-fixed
      >订单详情
      <template slot="right">
        <el-button @click="handleClose">返 回</el-button>
        <el-button type="primary" @click="showLog">操作日志</el-button>
      </template>
    </BjDivider>

    <div class="bj-row mt20">
      <!-- O2O订单 无需流转 -->
      <template v-if="cptIsO2O">
        <div class="bj-col bj-col-14 circulation-status">
          <div class="el-icon-circle"><i class="el-icon-circle-check" /></div>
          <div class="flex justify-between align-items">
            <div class="bj-form-item margin-no">
              <div class="bj-form-item-label">流转状态</div>
              <div class="bj-form-item-value">无需流转</div>
            </div>
            <div class="bj-form-item">
              <div class="bj-form-item-label">订单进度</div>
              <div class="bj-form-item-value">
                {{ ORDER_THIRD_ORDER_STATUS_LIST.bjGet(orderInfoVO.orderStatus) }}
              </div>
            </div>
          </div>
        </div>
      </template>
      <!-- 正常流转状态 -->
      <template v-if="!cptIsO2O && orderInfoVO.circulationStatus">
        <div class="bj-col bj-col-14 circulation-status">
          <div class="el-icon-circle"><i class="el-icon-circle-check" /></div>
          <div>
            <div class="bj-form-item">
              <div class="bj-form-item-label auto-width">流转状态</div>
              <div class="bj-form-item-value">正常流转</div>
            </div>

            <div class="bj-form-tips">三方订单已成功转单内部销售订单进行发货流转</div>
          </div>
        </div>
      </template>
      <!--暂停流转 状态 -->
      <template v-if="!cptIsO2O && !orderInfoVO.circulationStatus">
        <div class="bj-col bj-col-14 circulation-status">
          <div class="el-icon-circle"><i class="el-icon-warning-outline"></i></div>
          <div>
            <div class="bj-form-item">
              <div class="bj-form-item-label">流转状态</div>
              <div class="bj-form-item-value">暂停流转</div>
            </div>
            <div class="bj-form-item">
              <div class="bj-form-item-label">原因</div>
              <div class="bj-form-item-value">{{ orderInfoVO.failureReason }}</div>
            </div>
          </div>
        </div>
      </template>
    </div>

    <div class="bj-row mt20">
      <!-- 订单信息 -->
      <div class="bj-col">
        <div class="bj-form-title">订单信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">订单类型</div>
          <div class="bj-form-item-value">
            {{ ORDER_BUSINESS_TYPE_LIST.bjGet(orderInfoVO.businessType) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方订单号</div>
          <div class="bj-form-item-value">
            <span>{{ orderInfoVO.outerOrderNo }}</span>
            <BjCopyIcon :copy="orderInfoVO.outerOrderNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部订单号</div>
          <div class="bj-form-item-value">
            <span
              class="link"
              @click="$toBusinessOrderInternalView(orderInfoVO.innerOrderNo, false, businessType)"
              >{{ orderInfoVO.innerOrderNo }}</span
            >
            <BjCopyIcon :copy="orderInfoVO.innerOrderNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">销售店铺</div>
          <div class="bj-form-item-value">
            {{ orderInfoVO.storeName
            }}<span class="F68710">（{{ SCHL_CHANNEL_TYPE.bjGet(orderInfoVO.channel) }}）</span>
          </div>
        </div>

        <template v-if="cptIsO2O">
          <div class="bj-form-item">
            <div class="bj-form-item-label">销售员工</div>
            <div class="bj-form-item-value">{{ orderInfoVO.salesEmployeeRealName }}</div>
          </div>
        </template>

        <div class="bj-form-item">
          <div class="bj-form-item-label">下单时间</div>
          <div class="bj-form-item-value">{{ orderInfoVO.orderTime }}</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">拉单时间</div>
          <div class="bj-form-item-value">{{ orderInfoVO.creationDate }}</div>
        </div>
      </div>
      <!-- 订单金额 -->
      <div class="bj-col label-width-8">
        <div class="bj-form-title">订单金额</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">商品总金额</div>
          <div class="bj-form-item-value">
            <span>{{ $number2money(amountInfoVO.totalAmount) }}元</span>
          </div>
        </div>
        <template v-if="!cptIsO2O">
          <div class="bj-form-item">
            <div class="bj-form-item-label">优惠金额</div>
            <div class="bj-form-item-value">
              <span>{{ $number2money(amountInfoVO.totalDiscount) }}元</span>
            </div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">商家承担优惠金额</div>
            <div class="bj-form-item-value">
              {{ $number2money(amountInfoVO.businessDiscountAmount) }}元
            </div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">三方承担优惠金额</div>
            <div class="bj-form-item-value">
              {{ $number2money(amountInfoVO.thirdDiscountAmount) }}元
            </div>
          </div></template
        >
        <div class="bj-form-item">
          <div class="bj-form-item-label">应收总金额</div>
          <div class="bj-form-item-value">{{ $number2money(amountInfoVO.amountReceivable) }}元</div>
        </div>
      </div>
      <!-- 备注 -->
      <div class="bj-col">
        <div class="bj-form-title">备注</div>
        <!-- <div class="bj-form-item">
          <div class="bj-form-item-label">买家昵称</div>
          <div class="bj-form-item-value">{{ orderInfoVO.buyerNick }}</div>
        </div> -->
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家备注</div>
          <div class="bj-form-item-value">{{ orderInfoVO.buyerRemarks }}</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">商家备注</div>
          <div class="bj-form-item-value">{{ orderInfoVO.orderRemarks }}</div>
        </div>
        <!-- <div class="bj-form-item" style="padding-left:1em">
          <FiveFlag :value="orderInfoVO.fiveFlag" />
        </div> -->
      </div>
    </div>
    <div class="bj-row mt20">
      <!-- 收货信息 -->
      <div class="bj-col">
        <div class="bj-form-title">收货信息</div>

        <template v-if="cptIsO2O">
          <div class="bj-form-item">
            <div class="bj-form-item-label">提货方式</div>
            <div class="bj-form-item-value">
              {{ ORDER_TAKING_DELIVERY_TYPE_LIST.bjGet(orderInfoVO.takingDeliveryWay) }}
            </div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">提货店铺</div>
            <div class="bj-form-item-value">{{ orderInfoVO.takingDeliveryStoreName }}</div>
          </div>
        </template>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家昵称</div>
          <div class="bj-form-item-value">{{ orderInfoVO.buyerNick }}</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">收货人</div>
          <div class="bj-form-item-value">{{ receiverInfoVO.receiverName }}</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">手机</div>
          <div class="bj-form-item-value">
            {{ receiverInfoVO.mobile | phoneFilter }}
            <BjCopyIcon :copy="receiverInfoVO.mobile" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">地址</div>
          <div class="bj-form-item-value">
            {{ cptAddress }}
          </div>
        </div>
      </div>
      <!-- 支付信息 -->
      <div class="bj-col">
        <div class="bj-form-title">支付信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">支付方式</div>
          <div class="bj-form-item-value">
            {{ ORDER_INTERNAL_PAYMENT_LIST.bjGet(paymentInfoVO.payType) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">支付状态</div>
          <div class="bj-form-item-value">
            {{ ORDER_THIRD_PAYMENT_STATUS_LIST.bjGet(paymentInfoVO.payStatus) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">支付时间</div>
          <div class="bj-form-item-value">{{ paymentInfoVO.payTime }}</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家实付金额</div>
          <div class="bj-form-item-value">{{ $number2money(amountInfoVO.buyerPayAmount) }}元</div>
        </div>
      </div>
      <!-- 发票信息 -->
      <div class="bj-col">
        <div class="bj-form-title">
          发票信息
          <!-- <span v-if="invoiceType" class="link right" @click="onInvoice">查看详情</span> -->
        </div>
        <!-- <template v-if="invoiceType">
          <div class="bj-form-item">
            <div class="bj-form-item-label">发票类型</div>
            <div class="bj-form-item-value">{{ invoiceType }}</div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">发票抬头</div>
            <div class="bj-form-item-value">{{ invoiceHeader }}</div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">发票内容</div>
            <div class="bj-form-item-value">{{ invoiceContent }}</div>
          </div>
        </template> -->
        <div class="bj-form-item mt20">
          <div class="bj-form-tips text-center">暂无开票申请</div>
        </div>
      </div>
    </div>

    <div class="bj-row mt20">
      <!-- 商品清单 -->
      <div class="bj-col bj-col-24">
        <div class="bj-form-title">商品清单</div>
        <el-table :data="thirdItemVO" stripe border :header-cell-style="$rowStyle.DEFAULT">
          <el-table-column type="index" label="序号" align="center" />
          <el-table-column prop="spuName" label="商品名称" show-overflow-tooltip align="center" />
          <el-table-column prop="spuCode" label="商品编码" show-overflow-tooltip align="center" />
          <el-table-column prop="skuCode" label="SKU编码" show-overflow-tooltip align="center" />
          <el-table-column prop="giftFlag" label="是否赠品" align="center">
            <template slot-scope="scope">
              {{ SKU_GIFT_TYPE_LIST_FOR_CREATE.bjGet(scope.row.giftFlag) }}
            </template>
          </el-table-column>
          <el-table-column prop="quantity" label="数量" align="center" />
          <el-table-column prop="outerSingleSaleAmount" label="三方平台单价" align="center">
            <template slot-scope="scope">{{
              $number2money(scope.row.outerSingleSaleAmount)
            }}</template>
          </el-table-column>
          <el-table-column prop="outerSaleAmount" label="三方平台总价" align="center">
            <template slot-scope="scope">{{ $number2money(scope.row.outerSaleAmount) }}</template>
          </el-table-column>
        </el-table>
        <div class="bj-form-tips" style="margin-top: 15px">
          商品总数：{{ skuDetail.totalSkuNum }} ，SKU总数：{{ thirdItemVO.length }}
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { orderApi } from '@/api';
import {
  EXECUTOR_TYPE_LIST,
  EXECUTOR_TYPE_JSON,
  ORDER_INTERNAL_ORDER_STATUS_LIST,
  ORDER_INTERNAL_PAYMENT_LIST,
  ORDER_THIRD_PAYMENT_STATUS_LIST,
  SKU_GIFT_TYPE_LIST_FOR_CREATE,
  ORDER_BUSINESS_TYPE_LIST,
  ORDER_THIRD_ORDER_STATUS_LIST,
  ORDER_TAKING_DELIVERY_TYPE_LIST,
} from '@/utils/constants';
import orderlog from '@/views/center-business/order/components/LogTable/index.js';
// import FiveFlag from '@/views/center-business/order/components/FiveFlag/index';

const getDefaultForm = () => {
  return {
    amountInfoVO: {
      amountReceivable: null,
      businessDiscountAmount: null,
      buyerPayAmount: null,
      thirdDiscountAmount: null,
      totalAmount: null,
      totalDiscount: null,
    },
    orderInfoVO: {
      businessType: null,
      buyerNick: null,
      buyerRemarks: null,
      channel: null,
      channelTypeMeaning: null,
      circulationStatus: null,
      creationDate: null,
      failureReason: null,
      id: null,
      innerOrderNo: null,
      orderTime: null,
      outerOrderNo: null,
      payTime: null,
      receivingStatus: null,
      serviceRemarks: null,
      storeName: null,
    },
    paymentInfoVO: {
      payAmount: null,
      payStatus: null,
      payTime: null,
      payType: null,
    },
    receiverInfoVO: {
      address: null,
      buyerNick: null,
      city: null,
      cityCode: null,
      district: null,
      districtCode: null,
      mobile: null,
      phone: null,
      province: null,
      provinceCode: null,
      receiverName: null,
    },
    thirdItemVO: [
      {
        deleteFlag: null,
        exceptionFlag: null,
        exceptionReason: null,
        giftFlag: null,
        id: null,
        innerOrderNo: null,
        mainItemCode: null,
        multiFlag: null,
        orderItemCode: null,
        outerItemId: null,
        outerOrderNo: null,
        outerSaleAmount: null,
        outerSingleSaleAmount: null,
        quantity: null,
        setFlag: null,
        skuCode: null,
        skuId: null,
        skuName: null,
        spuCode: null,
        spuName: null,
        spuType: null,
      },
    ],
  };
};

export default {
  name: 'BusinessOrderThirdViewForm',
  mixins: [viewMiXin],
  props: {
    businessType: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      EXECUTOR_TYPE_LIST,
      EXECUTOR_TYPE_JSON,
      ORDER_INTERNAL_ORDER_STATUS_LIST,
      ORDER_INTERNAL_PAYMENT_LIST,
      ORDER_THIRD_PAYMENT_STATUS_LIST,
      SKU_GIFT_TYPE_LIST_FOR_CREATE,
      ORDER_BUSINESS_TYPE_LIST,
      ORDER_THIRD_ORDER_STATUS_LIST,
      ORDER_TAKING_DELIVERY_TYPE_LIST,

      info: getDefaultForm(),

      /** 商品行 */
      skuDetail: {
        visible: false,
        totalSkuNum: 0,
      },
    };
  },
  computed: {
    id() {
      return this.$route.query.id || null;
    },
    code() {
      return this.$route.query.code || null;
    },
    SCHL_CHANNEL_TYPE() {
      return this.$store.state.values.SCHL_CHANNEL_TYPE || [];
    },
    receiverInfoVO() {
      return this.info.receiverInfoVO || {};
    },
    amountInfoVO() {
      return this.info.amountInfoVO || {};
    },
    orderInfoVO() {
      return this.info.orderInfoVO || {};
    },
    paymentInfoVO() {
      return this.info.paymentInfoVO || {};
    },
    thirdItemVO() {
      return this.info.thirdItemVO || [];
    },
    cptIsO2O() {
      return this.orderInfoVO.businessType == 'o2o';
    },
    cptAddress() {
      let text = '';
      text += this.receiverInfoVO.province || '';
      text += this.receiverInfoVO.city || '';
      text += this.receiverInfoVO.district || '';
      text += this.receiverInfoVO.address || '';
      return text;
    },
  },
  watch: {},
  created() {
    // 会被接口数据覆盖
    this.orderInfoVO.businessType = this.businessType;
  },
  mounted() {
    this.reFresh();
  },
  methods: {
    onInvoice() {
      // TODO:
      this.$message.warning('暂无');
    },
    showLog() {
      orderlog(this.orderInfoVO.id);
    },
    reFresh() {
      orderApi.getThirdOrderDetail({ id: this.id, outerOrderNo: this.code }).then(res => {
        console.log(res);
        this.info = Object.assign({}, this.info, res);

        this.thirdItemVO.forEach(e => {
          this.skuDetail.totalSkuNum += e.quantity;
        });
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },
  },
};
</script>
<style lang="scss" scoped>
.app-container {
  width: 100%;
}

.circulation-status {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
  padding-bottom: 16px;
  .el-icon-circle {
    margin-right: 16px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 52px;
  }
}
</style>
