<template>
  <div
    class="app-container bj-label-colon label-width-7 bj-el-input-disabled app-container-2022-2-9-11-42-08"
  >
    <div :class="{ isFixed: isFixed }" class="tabs">
      <div class="tabs-btn">
        <!-- O2O订单的操作 -->

        <el-button v-if="cptO2oStatus == 11" type="primary" @click="onCancel">取消订单</el-button>
        <!-- 无法判断 待收货 挂起状态 ?  暂时 未完成 的可以确认收货 -->
        <el-button v-if="cptO2oStatus == 22" type="primary" @click="onConfirmReceipt"
          >确认收货</el-button
        >
        <el-button type="primary" @click="onCopy">复制订单</el-button>

        <el-button @click="handleClose">返 回</el-button>
        <!-- <el-button type="primary" @click="showLog">操作日志</el-button> -->
      </div>

      <el-tabs v-model="activeName" @tab-click="tabClick">
        <el-tab-pane
          v-for="item in catalogue"
          :key="item.id"
          :label="item.name"
          :name="item.id"
        ></el-tab-pane>
      </el-tabs>
    </div>
    <div id="placeholderTabs" class="placeholder-tabs"></div>
    <BjDivider :id="catalogue[0].id" right-fixed
      >{{ catalogue[0].name }}
      <template #right> </template>
    </BjDivider>

    <!-- O2O订单 -->
    <div class="bj-row mt20">
      <!-- 正常流转状态 -->
      <template v-if="cptO2oStatus > 10">
        <div class="bj-col bj-col-24" style="padding-left: 0">
          <div class="bj-form-item">
            <!-- 最后一个审核完成的时间  ,买家退款时间，仓库验收时间，退款时间，完成时间-->
            <BjOrderStatus
              :status="cptO2oStatus"
              constant-key="ORDER_INTERNAL_O2O_STATUS_LIST"
              :times="[
                orderInfoVO.auditCompleteTime,
                orderInfoVO.refundCompleteTime,
                orderInfoVO.completeDate,
              ]"
              style="width: 100%"
            />
          </div>
        </div>
      </template>
      <template v-if="cptO2oStatus == 5">
        <div class="bj-col bj-col-16" style="padding-left: 0">
          <div class="bj-form-item">
            <i class="el-icon-circle-close"></i>
            <div class="flex align-items ml20">
              <div class="flex" style="font-weight: bold; font-size: 16px">已拒绝</div>
              <div class="flex">原因：{{ orderInfoVO.endorseRefuseReason }}</div>
            </div>
          </div>
        </div>
        <div class="bj-col bj-col-8 flex-column justify-center">
          <div class="bj-form-item">
            <div class="bj-form-item-label">审核人</div>
            <div class="bj-form-item-value">{{ orderInfoVO.auditOperator }}</div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">审核时间</div>
            <div class="bj-form-item-value">{{ orderInfoVO.auditCompleteTime }}</div>
          </div>
        </div>
      </template>
      <template v-if="cptO2oStatus == 2">
        <div class="bj-col bj-col-24" style="padding-left: 0">
          <div class="bj-form-item">
            <i class="el-icon-circle-close status-icon-err"></i>
            <div class="flex ml20">
              <div class="flex justify-start" style="font-weight: bold; font-size: 16px">
                已关闭
              </div>
              <div class="flex justify-start">原因：{{ orderInfoVO.cancelReason }}</div>
            </div>
          </div>
        </div>
      </template>
    </div>

    <!-- 标签
    标签说明：标签分为手工标签和系统标签，需通过颜色或者其它区分，带X为手工标签可移除，系统标签不可移除
    -->
    <div class="bj-row mt20 tags">
      <BjTagList :tag-code-array="cptOrderTagCodeList" closable @close="handleCloseTag">
        <el-button @click="onAddTags">添加标签</el-button></BjTagList
      >
    </div>

    <!-- <div class="animation-title-box"> -->

    <div class="bj-row mt20 padding-no fix">
      <div class="bj-col bj-col-8">
        <div class="bj-form-title">O2O信息</div>

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
        <div class="bj-form-item">
          <div class="bj-form-item-label">销售员工</div>
          <div class="bj-form-item-value">{{ orderInfoVO.salesEmployeeRealName }}</div>
        </div>
      </div>
      <div class="bj-col bj-col-16 border-no">
        <div class="bj-form-title empty"></div>

        <div class="bj-form-item">
          <div class="bj-form-item-label">说明</div>
          <div class="bj-form-item-value">{{ orderInfoVO.description }}</div>
        </div>

        <div class="bj-form-item">
          <div class="bj-form-item-label">消费凭证</div>
          <div class="bj-form-item-value">
            <div v-for="(accessory, i) in cptOrderInfoVOVoucher" :key="i" class="bj-form-item-tips">
              <span class="img-url link" @click="handleAccessoryView(i)">{{ accessory }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 订单信息 -->
    <div class="bj-row mt20 padding-no fix">
      <div class="bj-col bj-col-p20">
        <div class="bj-form-title">基础信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">订单类型</div>
          <div class="bj-form-item-value">
            {{ ORDER_BUSINESS_TYPE_LIST.bjGet(orderInfoVO.businessType) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方订单号</div>
          <div class="bj-form-item-value">
            <span
              class="link"
              @click="$toBusinessOrderThirdView(orderInfoVO.outerOrderNo, false, businessType)"
              >{{ orderInfoVO.outerOrderNo }}</span
            >
            <BjCopyIcon :copy="orderInfoVO.outerOrderNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部订单号</div>
          <div class="bj-form-item-value">
            <span>{{ orderInfoVO.innerOrderNo }}</span>
            <BjCopyIcon :copy="orderInfoVO.innerOrderNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">销售店铺</div>
          <div class="bj-form-item-value">
            {{ orderInfoVO.storeName }}
            <span class="F68710">（{{ SCHL_CHANNEL_TYPE.bjGet(orderInfoVO.channel) }}）</span>
          </div>
        </div>

        <div class="bj-form-item">
          <div class="bj-form-item-label">三方下单时间</div>
          <div class="bj-form-item-value">{{ orderInfoVO.orderTime }}</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部创建时间</div>
          <div class="bj-form-item-value">
            {{ orderInfoVO.creationDate }}
            <!-- <span class="F68710">（三方下载）</span> -->
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家备注</div>
          <el-tooltip
            class="bj-form-item-value"
            effect="dark"
            :content="orderInfoVO.buyerRemarks"
            placement="top-start"
          >
            <span class="ellipsis">{{ orderInfoVO.buyerRemarks }}</span>
          </el-tooltip>
          <div class="bj-form-item-right link ml5" @click="onEditRemarks('buyerRemarks')">修改</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">商家备注</div>
          <el-tooltip
            class="bj-form-item-value"
            effect="dark"
            :content="orderInfoVO.orderRemarks"
            placement="top-start"
          >
            <span class="ellipsis">{{ orderInfoVO.orderRemarks }}</span>
          </el-tooltip>
          <div class="bj-form-item-right link ml5" @click="onEditRemarks('orderRemarks')">修改</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">客服备注</div>
          <el-tooltip
            class="bj-form-item-value"
            effect="dark"
            :content="orderInfoVO.serviceRemarks"
            placement="top-start"
          >
            <span class="ellipsis">{{ orderInfoVO.serviceRemarks }}</span>
          </el-tooltip>
          <div class="bj-form-item-right link ml5" @click="onEditRemarks('serviceRemarks')">
            修改
          </div>
        </div>
      </div>
      <div class="bj-col bj-col-p20">
        <div class="bj-form-title">
          收货信息<span
            v-if="orderInfoVO.orderStatus == 0"
            class="link right"
            @click="onEditReceiverInfo()"
            >修改</span
          >
        </div>

        <div class="bj-form-item">
          <div class="bj-form-item-label">买家昵称</div>
          <div class="bj-form-item-value">{{ receiverInfoVO.buyerNick }}</div>
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
            {{
              receiverInfoVO.province +
              receiverInfoVO.city +
              receiverInfoVO.district +
              receiverInfoVO.address
            }}
          </div>
        </div>
      </div>
      <div class="bj-col bj-col-p20">
        <div class="bj-form-title">金额信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">商品总金额</div>
          <div class="bj-form-item-value">{{ $number2money(amountInfoVO.goodsTotalAmount) }}元</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家应付总金额</div>
          <div class="bj-form-item-value">{{ $number2money(amountInfoVO.buyerPayAmount) }}元</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">成本总金额</div>
          <div class="bj-form-item-value">{{ $number2money(amountInfoVO.costAmount) }}元</div>
        </div>
      </div>
      <div class="bj-col bj-col-p20">
        <div class="bj-form-title">支付信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">支付方式</div>
          <div class="bj-form-item-value">
            <!-- {{ paymentInfoVO.payType }} -->
            {{ ORDER_INTERNAL_PAYMENT_LIST.bjGet(paymentInfoVO.payType) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">支付状态</div>
          <div class="bj-form-item-value">
            <!-- {{ paymentInfoVO.payStatus }} -->
            {{ ORDER_INTERNAL_PAYMENT_STATUS_LIST.bjGet(paymentInfoVO.payStatus) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">支付时间</div>
          <div class="bj-form-item-value">{{ paymentInfoVO.payTime }}</div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家实付金额</div>
          <div class="bj-form-item-value">{{ $number2money(paymentInfoVO.payAmount) }}元</div>
        </div>
      </div>
      <div class="bj-col bj-col-p20">
        <div class="bj-form-title">
          发票信息
          <span v-if="info.invoiceType" class="link right" @click="onInvoice">查看详情</span>
        </div>
        <template v-if="info.invoiceType">
          <div class="bj-form-item">
            <div class="bj-form-item-label">发票类型</div>
            <div class="bj-form-item-value">{{ info.invoiceType }}</div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">发票抬头</div>
            <div class="bj-form-item-value">{{ info.invoiceHeader }}</div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">发票内容</div>
            <div class="bj-form-item-value">{{ info.invoiceContent }}</div>
          </div>
        </template>
        <div v-else class="bj-form-item mt20">
          <div class="bj-form-tips text-center">暂无开票申请</div>
        </div>
      </div>
    </div>
    <BjDivider :id="catalogue[1].id">{{ catalogue[1].name }} </BjDivider>
    <div class="bj-row mt20">
      <div class="bj-col bj-col-24">
        <GoodsInfoTab
          v-if="tabVisible"
          ref="GoodsInfoTab"
          :order-id="id"
          :inner-order-no="orderInfoVO.innerOrderNo"
          :order-status="orderInfoVO.orderStatus"
          :has-btn="false"
          :business-type="businessType"
          @reFresh="reFresh"
        />
      </div>
    </div>

    <EditRemarks
      :id="id"
      ref="EditRemarks"
      :info="orderInfoVO"
      @editBaseInfoApi="editBaseInfoApi"
    />
    <EditReceiverInfo
      :id="id"
      ref="EditReceiverInfo"
      :info="receiverInfoVO"
      @editBaseInfoApi="editBaseInfoApi"
    />
    <SuspendReason :id="id" ref="SuspendReason" @reFresh="reFresh" />
    <EditTags
      :id="id"
      ref="EditTags"
      :tags="cptOrderTagCodeList"
      @editBaseInfoApi="editBaseInfoApi"
    />
    <O2oConfirmReceipt
      :id="id"
      ref="O2oConfirmReceipt"
      @onO2oConfirmReceipt="o2oConfirmReceiptApi"
    />
    <BjPreviewList ref="BjPreviewList" />
  </div>
</template>
<script>
import viewMiXin from '@/utils/view-mixin.js';
import { orderApi } from '@/api';
import orderLog from '@/views/center-business/order/components/LogTable/index.js';
// import FiveFlag from '@/views/center-business/order/components/FiveFlag/index';

import GoodsInfoTab from './GoodsInfoTab';

import EditRemarks from './EditRemarks';
import EditReceiverInfo from './EditReceiverInfo';
import SuspendReason from './SuspendReason';
import EditTags from '@/views/center-business/order/components/EditTags';
import O2oConfirmReceipt from './O2oConfirmReceipt';
import BjOrderStatus from '@/views/center-business/order/components/BjOrderStatus/index';
import {
  ORDER_TRADE_TYPE_LIST,
  ORDER_BUSINESS_TYPE_LIST,
  ORDER_ACTION_TYPE_LIST,
  STATUS_YES_NUM,
  STATUS_HAVE_TRUE,
  ORDER_INTERNAL_ORDER_STATUS_LIST,
  ORDER_INTERNAL_PREEMPT_STATUS_LIST,
  ORDER_INTERNAL_PREEMPT_BACK_STATUS_LIST,
  ORDER_INTERNAL_SOURCE_STATUS_LIST,
  ORDER_INTERNAL_DELIVER_STATUS_LIST,
  ORDER_INTERNAL_RECEIVING_STATUS_LIST,
  ORDER_INTERNAL_STOCK_BACK_STATUS_LIST,
  ORDER_INTERNAL_HAND_UP_STATUS_LIST,
  ORDER_INTERNAL_REFUND_STATUS_LIST,
  //
  ORDER_INTERNAL_PAYMENT_LIST,
  ORDER_INTERNAL_PAYMENT_STATUS_LIST,
  ORDER_TAKING_DELIVERY_TYPE_LIST,
  ORDER_BUSINESS_TYPES,
} from '@/utils/constants';
import { getOffsetTop } from '@/utils';
const getDefaultForm = () => {
  return {
    orderStatusTotalVO: {
      finishCount: null,
      unfinishedCount: null,
      cancelCount: null,
    },
    unfinishedTotalVO: {
      outStockCount: null,
      failSeekCount: null,
      waitSendCount: null,
      preempAuditCount: null,
      seekAuditCount: null,
    },
    waitDealTotalVO: {
      outStockCount: null,
      failSeekCount: null,
      failSyncPreempCount: null,
      failSyncSendCount: null,
      waitAudit: null,
    },
    orderInfoVO: {
      id: null,
      orderStatus: null,
      tradeType: null,
      outerOrderNo: null,
      innerOrderNo: null,
      storeName: null,
      orderTime: null,
      creationDate: null,
      orderRemarks: null,
      buyerRemarks: null,
      serviceRemarks: null,
    },
    receiverInfoVO: {
      buyerNick: null,
      receiverName: null,
      phone: null,
      mobile: null,
      province: null,
      city: null,
      district: null,
      address: null,
    },
    amountInfoVO: {
      goodsTotalAmount: null,
      buyerPayAmount: null,
      costAmount: null,
    },
    paymentInfoVO: {
      payType: null,
      payStatus: null,
      payTime: null,
      payAmount: null,
    },
  };
};

export default {
  name: 'BusinessOrderInternalViewForm',
  components: {
    GoodsInfoTab,
    EditRemarks,
    EditReceiverInfo,
    SuspendReason,
    EditTags,
    O2oConfirmReceipt,
    BjOrderStatus,
  },
  mixins: [viewMiXin],
  props: {
    businessType: {
      type: String,
      default: '',
    },
  },
  provide() {
    return { getInfo: () => this.info };
  },
  data() {
    return {
      ORDER_TRADE_TYPE_LIST,
      ORDER_BUSINESS_TYPE_LIST,
      ORDER_BUSINESS_TYPES,
      ORDER_ACTION_TYPE_LIST,
      STATUS_YES_NUM,
      STATUS_HAVE_TRUE,
      ORDER_INTERNAL_ORDER_STATUS_LIST,
      ORDER_INTERNAL_PREEMPT_STATUS_LIST,
      ORDER_INTERNAL_PREEMPT_BACK_STATUS_LIST,
      ORDER_INTERNAL_SOURCE_STATUS_LIST,
      ORDER_INTERNAL_DELIVER_STATUS_LIST,
      ORDER_INTERNAL_RECEIVING_STATUS_LIST,
      ORDER_INTERNAL_STOCK_BACK_STATUS_LIST,
      ORDER_INTERNAL_HAND_UP_STATUS_LIST,
      ORDER_INTERNAL_REFUND_STATUS_LIST,
      //
      ORDER_INTERNAL_PAYMENT_LIST,
      ORDER_INTERNAL_PAYMENT_STATUS_LIST,

      ORDER_TAKING_DELIVERY_TYPE_LIST,

      info: getDefaultForm(),
      // 表单校验
      rules: {},
      activeName: 'orderInfo',
      isFixed: false,
      catalogue: [
        {
          id: 'orderInfo',
          name: '订单信息',
        },
        {
          id: 'goodsInfo',
          name: '商品信息',
        },
      ],
      tabVisible: true,
    };
  },
  computed: {
    id() {
      return this.info.orderInfoVO.id;
    },
    SCHL_CHANNEL_TYPE() {
      return this.$store.state.values.SCHL_CHANNEL_TYPE || [];
    },
    BJ_TAGS() {
      return this.$store.state.values.BJ_TAGS || [];
    },
    percentage() {
      const done =
        this.orderStatusTotalVO.finishCount || 0 + this.orderStatusTotalVO.cancelCount || 0;
      const all = done + this.orderStatusTotalVO.unfinishedCount || 0 || 1; // 0 不能为除数，默认为1
      return Math.floor((done / all) * 100);
    },
    orderStatusTotalVO() {
      return this.info.orderStatusTotalVO || {};
    },
    unfinishedTotalVO() {
      return this.info.unfinishedTotalVO || {};
    },
    waitDealTotalVO() {
      return this.info.waitDealTotalVO || {};
    },
    orderInfoVO() {
      return this.info.orderInfoVO || {};
    },
    receiverInfoVO() {
      return this.info.receiverInfoVO || {};
    },
    amountInfoVO() {
      return this.info.amountInfoVO || {};
    },
    paymentInfoVO() {
      return this.info.paymentInfoVO || {};
    },
    cptOrderTagCodeList() {
      return this.orderInfoVO.tags ? this.orderInfoVO.tags.split(',') : [];
    },
    cptOrderInfoVOVoucher() {
      return this.orderInfoVO.voucher ? this.orderInfoVO.voucher.split(',') : [];
    },
    cptO2oStatus() {
      //   ['1', '已完成'  99 ],
      if (this.orderInfoVO.orderStatus == 1) {
        return '99';
      }
      //  ['0', '未完成'],   11,22
      if (this.orderInfoVO.orderStatus == 0) {
        if (this.unfinishedTotalVO.waitReceiveCount > 0) {
          return '22';
        }
        if (this.unfinishedTotalVO.preempAuditCount > 0) {
          return '11';
        }
      }
      // ['2', '已关闭'], ['3', '异常'],
      return this.orderInfoVO.orderStatus;
    },
  },
  watch: {},
  created() {
    // 会被接口数据覆盖
    this.orderInfoVO.businessType = this.businessType;
  },
  mounted() {
    this.reFresh();
    this.$store.dispatch('values/getValueList', 'SCHL.CHANNEL_TYPE');

    // 对整个页面滚轮进行监听，每发生一次滚轮事件，执行一次方法
    window.addEventListener('scroll', this.scrollCallBack, true);
  },
  destroyed() {
    // 解绑滚轮监听事件
    window.removeEventListener('scroll', this.scrollCallBack, true);
  },
  methods: {
    handleScroll(vertical, horizontal, nativeEvent) {
      console.log(vertical, horizontal, nativeEvent);
    },
    scrollCallBack() {
      // 兼容性，获取页面滚动距离
      const scrollTop =
        window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
      // 获取浏览器高度
      const windowHeight = window.innerHeight;

      // 判断开始浮动
      const StartFixedHeight = getComputedStyle(
        document.getElementsByClassName('app-container')[0],
      )['paddingTop'].replace('px', '');
      this.isFixed = scrollTop > StartFixedHeight;

      // tabs浮动后，占位
      const placeholderTabs = document.getElementById('placeholderTabs'); // 这是一个占位元素，高度与tab相等，用于tab脱离文档流后，用此占位，防止抖动
      const TabsHeight = document.getElementsByClassName('tabs')[0].offsetHeight || 0;
      placeholderTabs.style.height = this.isFixed ? TabsHeight + 'px' : '0px';

      // 标签的active回写
      this.catalogue.forEach((v, i) => {
        // 获取元素本身离文档顶部高度（这个数据不会改变）
        const offsetTop = getOffsetTop(document.getElementById(`${v.id}`));
        // 如果  元素本身离文档顶部高度offsetTop  - dom滚动距离 =  元素当前离浏览器（可视）顶部的距离
        // 元素当前离浏览器（可视）顶部的距离如果不到2/3屏（到底部为1/3屏），则激活标签
        if (offsetTop - scrollTop < windowHeight / 2) {
          // 导航栏背景色选中
          this.activeName = v.id;
        }
      });
    },
    /* ************************ 按钮事件 *****************************/
    // 取消订单
    onCancel() {
      this.$confirm('是否取消此订单，是否继续？')
        .then(_ => {
          orderApi
            .cancelInternalO2oOrder({ innerOrderNo: this.orderInfoVO.innerOrderNo })
            .then(res => {
              if (res.failed === false) {
                this.$message.success('操作成功');
                setTimeout(() => {
                  this.reFresh();
                }, 500);
              }
            });
        })
        .catch(_ => {});
    },

    command(TEXT) {
      this.$confirm(`您正在 ${TEXT}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          if (TEXT === '创建仅退款单') {
            this.$router.push({
              name: 'BusinessAfterInternalRefundAdd',
              query: { innerOrderNo: this.orderInfoVO.innerOrderNo },
            });
          }
          if (TEXT === '创建退货单') {
            console.log('TODO:');
          }
          if (TEXT === '创建退货退款单') {
            this.$router.push({
              name: 'BusinessAfterInternalReturnAdd',
              query: { innerOrderNo: this.orderInfoVO.innerOrderNo },
            });
          }
        })
        .catch(() => {});
    },
    tabClick({ name }) {
      const fixedHeaderHeight =
        document.getElementsByClassName('fixed-header')[0].offsetHeight || 0;
      const tabsHeight = document.getElementsByClassName('tabs')[0].offsetHeight || 0;

      const setDocumentScrollFn = v => {
        document.documentElement.scrollTop = v;
      };
      const ANCHOR = document.getElementById(name);
      const TO_DISTANCE = getOffsetTop(ANCHOR) - fixedHeaderHeight - tabsHeight;
      const FROM_DISTANCE = document.documentElement.scrollTop;
      let nowDistance = FROM_DISTANCE;
      const step = 50;
      smoothFn();
      function smoothFn() {
        if (FROM_DISTANCE < TO_DISTANCE) {
          nowDistance += step;
          if (nowDistance < TO_DISTANCE) {
            setTimeout(smoothFn, 10);
          } else {
            nowDistance = TO_DISTANCE;
          }
          setDocumentScrollFn(nowDistance);
        } else {
          nowDistance -= step;
          setDocumentScrollFn(nowDistance);
          if (nowDistance > TO_DISTANCE) {
            setTimeout(smoothFn, 10);
          } else {
            nowDistance = TO_DISTANCE;
          }
          setDocumentScrollFn(nowDistance);
        }
      }
    },
    /* ************************ 按钮事件 *****************************/

    showLog() {
      orderLog(this.id);
    },
    reFresh(goodsInfoActiveTab) {
      const orderId = this.$route.query.id;
      const orderCode = this.$route.query.code;

      orderApi
        .getInternalDetailByIdCode({
          orderCode,
          orderId,
        })
        .then(res => {
          this.info = { ...this.info, ...(res || {}) };
        });

      this.reTabs(goodsInfoActiveTab);
    },
    changeTabs(tab, event) {
      this.$refs.carousel.setActiveItem(tab.name);
    },
    reTabs(goodsInfoActiveTab) {
      this.tabVisible = false;
      this.$nextTick(() => {
        this.tabVisible = true;
        this.$nextTick(() => {
          if (goodsInfoActiveTab) this.$refs.GoodsInfoTab.goodsInfoActiveTab = goodsInfoActiveTab;
        });
      });
    },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },
    /** 挂起/解挂 */
    onHangUp(action) {
      this.$refs.SuspendReason.onEdit(action);
    },

    /** 添加标签 */
    onAddTags(action) {
      this.$refs.EditTags.onEdit();
    },
    /** 删除一个标签 */
    handleCloseTag(code) {
      this.$confirm('是否删除此标签？')
        .then(_ => {
          const POST_DATA = this.cptOrderTagCodeList.filter(e => e !== code);
          this.editBaseInfoApi({ orderId: this.id, tags: POST_DATA.join() });
        })
        .catch(_ => {});
    },
    onCopy() {
      if (this.orderInfoVO.innerOrderNo) {
        this.$router.push({
          name: 'BusinessO2oInternalAdd',
          query: { innerOrderNo: this.orderInfoVO.innerOrderNo },
        });
      } else {
        this.$message.warning(
          '订单信息错误：orderInfoVO.innerOrderNo=' + this.orderInfoVO.innerOrderNo,
        );
      }
    },
    // 修改备注
    onEditRemarks(key) {
      this.$refs.EditRemarks.onEdit(key);
    },
    // 修改收货信息
    onEditReceiverInfo(key) {
      this.$refs.EditReceiverInfo.onEdit();
    },
    onConfirmReceipt() {
      // O2O需要上传凭证，只做订单级的确认收货
      this.$refs.O2oConfirmReceipt.onEdit();
    },
    handleAccessoryView(index) {
      this.$refs.BjPreviewList.show(this.cptOrderInfoVOVoucher, index);
    },
    // 修改基础信息
    editBaseInfoApi(POST_DATA) {
      orderApi.updateOrderBase(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          this.$refs.EditRemarks.dialogVisible = false;
          this.$refs.EditReceiverInfo.dialogVisible = false;
          this.$refs.EditTags.dialogVisible = false;
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // 订单级-确认收货
    confirmReceiptByOrderIdApi(POST_DATA) {
      orderApi.confirmReceiptByOrderId(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    o2oConfirmReceiptApi(POST_DATA) {
      if (!POST_DATA.orderId) POST_DATA.orderId = this.id;
      orderApi.o2oConfirmReceiptByOrderId(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          this.$refs.O2oConfirmReceipt.dialogVisible = false;
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-carousel__arrow {
  display: none !important;
}
.app-container-2022-2-9-11-42-08 {
  .status-now {
    font-size: 16px;
    div {
      font-size: 16px;
    }
    .bj-form-item-label {
      width: auto;
    }
    .bj-form-item-value {
      font-size: 18px;
    }
  }
  .status-count {
    height: 100px;
    span {
      text-align: center;
      line-height: 50px;
      font-size: 24px;
      font-weight: bold;
    }
  }
  .status-count span:after {
    content: '';
    display: block;
    width: 45px;
    border-bottom: 4px solid #534f4e;
    margin: 0 auto;
  }
  .status-count span.border-orange:after {
    border-color: #f79c01;
  }
  .status-count span.border-green:after {
    border-color: #58d071;
  }
  // 标签
  // .tags {
  // }
  .el-carousel__item:nth-child(2n) {
    background: none;
  }
  .el-carousel__item:nth-child(2n + 1) {
    background: none;
  }
  // 滚动标题
  .animation-title-box {
    // display: flex;
    // margin-top: 30px;

    .animation-title {
      // width: 80px;

      #animation-title-ul {
        // position: absolute;

        padding: 0;
        // top: 60%;
        // left: 6px;
        // display: flex;
        // flex-direction: column;

        font-size: 12px;
        background: rgba($color: #ffffff, $alpha: 1);
        // box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

        list-style: none;
        li {
          display: inline-block;
          padding: 6px 4px;
        }
      }
      .fixed {
        position: fixed;
        top: 10px;
        z-index: 9;
        // right: 20px;
      }
    }
    .animation-content {
      width: calc(100% - 80px);
    }
  }

  // 订单
  .bj-col-p20:first-child {
    width: 24% !important;
  }
  .bj-col-p20 {
    width: 19% !important;
  }

  .app-container {
    width: 100%;
  }

  .bj-col-14 {
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
  .item {
    margin-right: 40px;
  }
  .flex {
    align-items: stretch;
    justify-content: space-around;
    .flex-item {
      display: flex;
      align-items: center;
      justify-content: center;
      flex-wrap: nowrap;
      flex-direction: column;
      border: 1px solid #dcdfe6;
      border-radius: 4px;
      padding: 10px 15px;
      font-size: 14px;
      color: #606266;
      line-height: 28px;
      box-sizing: border-box;
      font-weight: 700;
      max-height: 120px;
      margin-top: 16px;
      label {
        display: inline-block;
        width: 88px;
      }
    }
  }
  .flex-direction {
    flex-direction: column;
    align-items: center;
  }
  .circle {
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
    border-radius: 50%;
    border: 1px solid #dcdfe6;
    font-size: 20px;
  }
  .cell .el-tag {
    margin-right: 5px;
  }
  .foot-table {
    min-height: 220px;
    max-height: 480px;
    overflow-y: auto;
  }
}
.tabs {
  background-color: white;
}
.tabs .tabs-btn {
  position: absolute;
  right: 20px;
  margin-top: 6px;
  z-index: 1;
}
.isFixed {
  position: fixed;
  top: 86px;
  left: 180px;
  right: 0;
  z-index: 5; // 要小于顶部导航栏的 z-index: 9
  padding: 0 20px;
}
</style>
