<!--
 * @Author: your name
 * @Date: 2021-09-18 10:07:41
 * @LastEditTime: 2022-06-10 14:30:50
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\views\center-business\after\internal\exchange\view.vue
-->
<template>
  <div class="app-container bj-label-colon label-width-6 bj-el-input-disabled">
    <BjDivider right-fixed
      >退货单详情
      <template slot="right">
        <el-button @click="reFresh">刷 新</el-button>
        <el-button @click="handleClose">返 回</el-button>
        <el-button type="primary" @click="showLog">操作日志</el-button>
      </template>
    </BjDivider>
    <div class="bj-row mt20">
      <template v-if="cptStatus < 5">
        <div
          class="bj-col"
          style="padding-left: 0"
          :class="cptStatus != 0 ? 'bj-col-24' : 'bj-col-16'"
        >
          <div class="bj-form-item">
            <!-- 最后一个审核完成的时间  ,买家退货时间，仓库验收时间，退款时间，完成时间-->
            <BjOrderStatus
              :status="cptStatus == 4 ? 99 : cptStatus"
              constant-key="AFTER_RETURN_TRADE_STATUS_FOR_VIEW"
              :times="[
                refundBaseInfoVO.auditCompleteTime,
                refundBaseInfoVO.buyerReturnTime,
                refundBaseInfoVO.warehouseReceivingTime,
                refundBaseInfoVO.refundCompleteTime,
                refundBaseInfoVO.completeDate,
              ]"
              style="width: 100%"
            />
          </div>
        </div>
        <div v-if="cptStatus == 0" class="bj-col bj-col-8 flex-center">
          <div class="bj-form-item flex-center">
            <el-button type="primary" class="main-btn" @click="command('审核通过')">
              {{ cptAuditLvlBtnText }}审通过</el-button
            >
            <el-button @click="command('审核拒绝')"> {{ cptAuditLvlBtnText }}审拒绝</el-button>
          </div>
        </div>
        <!-- <div v-if="cptStatus == 2" class="bj-col bj-col-8 flex-center">
          <div class="bj-form-item flex-center">
            <el-button type="primary" class="main-btn" @click="command('已全部入库')"
              >已全部入库</el-button
            >
          </div>
        </div> -->
      </template>
      <!-- 审核拒绝 -->
      <template v-if="cptStatus == 5">
        <div class="bj-col bj-col-16" style="padding-left: 0">
          <div class="bj-form-item">
            <i class="el-icon-circle-close status-icon-err"></i>
            <div class="flex align-items ml20">
              <div class="flex" style="font-weight: bold; font-size: 16px">已拒绝</div>
              <div class="flex">原因：{{ refundBaseInfoVO.endorseRefuseReason }}</div>
            </div>
          </div>
        </div>
        <div class="bj-col bj-col-8 flex-column justify-center">
          <div class="bj-form-item">
            <div class="bj-form-item-label">审核人</div>
            <div class="bj-form-item-value">{{ refundBaseInfoVO.auditOperator }}</div>
          </div>
          <div class="bj-form-item">
            <div class="bj-form-item-label">审核时间</div>
            <div class="bj-form-item-value">{{ refundBaseInfoVO.auditCompleteTime }}</div>
          </div>
        </div>
      </template>
      <!-- 订单取消 -->
      <template v-if="cptStatus == 6">
        <div class="bj-col bj-col-24" style="padding-left: 0">
          <div class="bj-form-item">
            <i class="el-icon-circle-close status-icon-err"></i>
            <div class="flex align-items ml20">
              <div class="flex" style="font-weight: bold; font-size: 16px">已取消</div>
              <div class="flex">原因：{{ refundBaseInfoVO.cancelReason }}</div>
            </div>
          </div>
        </div>
      </template>
      <!-- 转单异常 -->
      <template v-if="cptStatus == 7">
        <div class="bj-col bj-col-24" style="padding-left: 0">
          <div class="bj-form-item">
            <i class="el-icon-warning-outline status-icon-err"></i>
            <div class="flex align-items ml20">
              <div class="flex" style="font-weight: bold; font-size: 16px">转单异常</div>
              <div class="flex">原因：{{ refundBaseInfoVO.exceptionReason }}</div>
            </div>
          </div>
        </div>
      </template>
    </div>
    <div class="bj-row mt20">
      <div class="bj-col bj-col-6">
        <div class="bj-form-title">
          基础信息
          <span v-if="cptStatus == 0" text="待审核才能修改" class="link right" @click="openInfoData"
            >修改</span
          >
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货单类型</div>
          <div class="bj-form-item-value">
            {{ INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST.bjGet(refundBaseInfoVO.orderRefundType) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">店 铺</div>
          <div class="bj-form-item-value">
            {{ refundBaseInfoVO.storeName }}
            <span class="F68710">（{{ SCHL_CHANNEL_TYPE.bjGet(refundBaseInfoVO.channel) }}）</span>
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货类型</div>
          <div class="bj-form-item-value">
            {{ INTERNAL_AFTER_RETURN_TYPE_LIST.bjGet(refundBaseInfoVO.refundType) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货原因</div>
          <div class="bj-form-item-value">
            {{ BJ_AFTER_REFUND_GOODS_REASONS.bjGet(refundBaseInfoVO.reason) }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货阶段</div>
          <div class="bj-form-item-value">
            {{ INTERNAL_AFTER_STAGE_STATUS.bjGet(refundBaseInfoVO.stage) }}
          </div>
        </div>
        <div class="bj-form-item">
          <!-- 取三方退货退款单的备注 -->
          <div class="bj-form-item-label">退货备注</div>
          <el-tooltip
            class="bj-form-item-value"
            effect="dark"
            :content="refundBaseInfoVO.buyerRemarks"
            placement="top-start"
          >
            <span class="ellipsis">{{ refundBaseInfoVO.buyerRemarks }}</span>
          </el-tooltip>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">客服备注</div>
          <el-tooltip
            class="bj-form-item-value"
            effect="dark"
            :content="refundBaseInfoVO.serviceRemarks"
            placement="top-start"
          >
            <span class="ellipsis">{{ refundBaseInfoVO.serviceRemarks }}</span>
          </el-tooltip>
          <!-- <div v-if="cptStatus > 0" class="bj-form-item-right link ml5" @click="onEditRemarks">
            修改
          </div> -->
        </div>
      </div>
      <div class="bj-col bj-col-6">
        <div class="bj-form-title">买家信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">买家昵称</div>
          <div class="bj-form-item-value">
            {{ refundBuyerInfoVO.buyerNick }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货人</div>
          <div class="bj-form-item-value">
            {{ refundBuyerInfoVO.receiverName }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货人手机号</div>
          <div class="bj-form-item-value">
            <span class="link">{{ refundBuyerInfoVO.mobile }}</span>
            <BjCopyIcon :copy="refundBuyerInfoVO.mobile" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货人地址</div>
          <div class="bj-form-item-value">
            {{ refundBuyerInfoVO.address }}
          </div>
        </div>
      </div>
      <div class="bj-col bj-col-6">
        <div class="bj-form-title">
          退货物流
          <!-- 待审核  待买家退货 -->
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">物流信息</div>
          <div class="bj-form-item-value">
            <div v-for="(item, index) in refundLogisticsInfoVO.logisticsList || []" :key="index">
              {{ item }}
            </div>
          </div>
          <span
            v-if="cptStatus <= 1"
            text="待审核、待买家退货才能修改"
            class="link right"
            @click="openLogistics"
            >修改</span
          >
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货仓库</div>
          <div class="bj-form-item-value">
            {{ refundLogisticsInfoVO.receivingWarehouseName }}
          </div>
          <span
            v-if="cptStatus <= 1"
            text="待审核、待买家退货才能修改"
            class="link right"
            @click="openReceivingWarehouse"
            >修改</span
          >
        </div>
        <!-- <div class="bj-form-item">
          <div class="bj-form-item-label">收货状态</div>
          <div class="bj-form-item-value">
            {{
              AFTER_INTERNAL_RETURN_WAREHOUSE_RECEIVING_STATUS.bjGet(
                refundLogisticsInfoVO.receivingWarehouseStatus,
              )
            }}
          </div>
        </div> -->
        <div class="bj-form-item">
          <div class="bj-form-item-label">入库通知单</div>
          <div class="bj-form-item-value">
            <span
              class="link"
              @click="$toBusinessStockEntryInformView(refundLogisticsInfoVO.warehousingNotice)"
            >
              {{ refundLogisticsInfoVO.warehousingNotice }}
            </span>
            <BjCopyIcon :copy="refundLogisticsInfoVO.warehousingNotice" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">入库结果单</div>
          <div class="bj-form-item-value">
            <span
              class="link"
              @click="$toBusinessStockEntryResultView(refundLogisticsInfoVO.warehousingResult)"
            >
              {{ refundLogisticsInfoVO.warehousingResult }}
            </span>
            <BjCopyIcon :copy="refundLogisticsInfoVO.warehousingResult" />
          </div>
        </div>
      </div>
      <div class="bj-col bj-col-6">
        <div class="bj-form-title">财务信息</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">结算方式</div>
          <div class="bj-form-item-value">
            {{ AFTER_INTERNAL_RETURN_PAY_LIST.bjGet(refundBaseInfoVO.payType) }}
          </div>
        </div>
      </div>
    </div>
    <!-- 标签
    标签说明：标签分为手工标签和系统标签，需通过颜色或者其它区分，带X为手工标签可移除，系统标签不可移除
    -->
    <div class="bj-row mt20 tags">
      <BjTagList :tag-code-array="cptOrderTagCodeList" closable @close="handleCloseTag">
        <el-button @click="onAddTags">添加标签</el-button></BjTagList
      >
    </div>
    <div class="bj-row mt20">
      <div class="bj-col bj-col-8">
        <div class="bj-form-title">关联单据</div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部退货单号</div>
          <div class="bj-form-item-value">
            <span
              class="link"
              @click="$toBusinessAfterInternalReturnView(refundBaseInfoVO.innerRefundNo)"
              >{{ refundBaseInfoVO.innerRefundNo }}</span
            >
            <BjCopyIcon :copy="refundBaseInfoVO.innerRefundNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方退换单号</div>
          <div class="bj-form-item-value">
            <span
              class="link"
              @click="$toBusinessAfterThirdReturnView(refundBaseInfoVO.outerRefundNo)"
              >{{ refundBaseInfoVO.outerRefundNo }}</span
            >
            <BjCopyIcon :copy="refundBaseInfoVO.outerRefundNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方订单号</div>
          <div class="bj-form-item-value">
            <span class="link" @click="$toBusinessOrderThirdView(refundBaseInfoVO.outerOrderNo)">{{
              refundBaseInfoVO.outerOrderNo
            }}</span>
            <BjCopyIcon :copy="refundBaseInfoVO.outerOrderNo" />
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部订单号</div>
          <div class="bj-form-item-value">
            <span class="link" @click="$toBusinessOrderInternalView(refundBaseInfoVO.innerOrderNo)">
              {{ refundBaseInfoVO.innerOrderNo }}</span
            >
            <BjCopyIcon :copy="refundBaseInfoVO.innerOrderNo" />
          </div>
        </div>
      </div>
      <div class="bj-col bj-col-8 border-no">
        <div class="bj-form-title empty"></div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">内部创建时间</div>
          <div class="bj-form-item-value">
            {{ refundBaseInfoVO.creationDate }}
            <span class="F68710"
              >（{{ ORDER_ACTION_TYPE_LIST.bjGet(refundBaseInfoVO.actionType) }}）</span
            >
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">三方退货时间</div>
          <div class="bj-form-item-value">
            {{ refundBaseInfoVO.outReturnTime }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">退货入库时间</div>
          <div class="bj-form-item-value">
            {{ refundBaseInfoVO.warehouseReceivingTime }}
          </div>
        </div>
        <div class="bj-form-item">
          <div class="bj-form-item-label">完成时间</div>
          <div class="bj-form-item-value">
            {{ refundBaseInfoVO.completeDate }}
          </div>
        </div>
      </div>
      <div class="bj-col">
        <div class="bj-form-title">
          图片附件 <span v-if="cptStatus == 0" class="link right" @click="showUpload()">上传</span>
        </div>
        <div class="bj-form-item" style="display: block">
          <div v-for="(accessory, i) in refundBaseInfoAccessory" :key="i" class="bj-form-item-tips">
            <span class="img-url link" @click="handleAccessoryView(i)">{{ accessory }}</span
            ><i
              v-if="cptStatus == 0"
              class="link right el-icon-close"
              @click="handleAccessoryDelete(i)"
            ></i>
          </div>
        </div>
      </div>
    </div>
    <BjDivider
      >商品信息
      <template v-if="cptStatus == 0 && !cptTransferAction" #button>
        <el-button type="primary" class="xs mb16" @click="handleShowFnForDialogSkuList"
          >添加退货退款商品</el-button
        >
      </template>
    </BjDivider>
    <SkuList
      v-if="cptStatus"
      ref="SkuList"
      :row-key="ROW_KEY"
      :readonly="cptStatus > 0 || cptTransferAction"
      :parent-query-api="parentQueryApi"
      :children-query-api="childrenQueryApi"
      :parent-label-list="parentLabelList"
      :parent-columns-btn="parentColumnsBtn"
      @setSkuTableData="setSkuTableData"
    />
    <!-- 修改基础信息  修改客服备注 -->
    <BaseInfoDialog ref="baseRef" is-refund-goods @confirm="editBaseInfoApi" />
    <!-- 修改退货仓库 -->
    <ReceivingWarehouseDialog ref="ReceivingWarehouseRef" @confirm="updateWarehouseApi" />
    <!-- 修改物流 -->
    <LogisticsDialog ref="logisticsRef" @confirm="editBaseInfoApi" />
    <!-- 上传附件 -->
    <UploadImgDialog ref="UploadImgDialog" @confirm="editBaseInfoApi" />
    <!-- 添加标签 -->
    <EditTags
      :id="id"
      ref="EditTags"
      :tags="cptOrderTagCodeList"
      @editBaseInfoApi="editBaseInfoApi"
    />
    <BjPreviewList ref="BjPreviewList" />
    <!-- 选择商品 -->
    <BjDialogList
      ref="BjDialogSkuList"
      width="80%"
      :config="
        $BjDialogListConfig.refundGoodsDefault('选择退货退款的商品', {
          defaultQuery: { innerOrderNo: refundBaseInfoVO.innerOrderNo },
        })
      "
      :row-keys="ROW_KEY"
      :reset-data="resetDataFnForDialogSkuList"
      multiple
      search-btn-hide
      :check-method="checkMethod"
      :cell-class-name="cellClassNameForDialogSkuList"
      :load="loadMethodDialogSkuList"
      :row-style="$treeRowStyleFun"
      @confirm="confirmForDialogSkuList"
    >
      <template #tableColumn>
        <el-table-column prop="other" label="其它" min-width="400">
          <template slot-scope="scope">
            <div v-if="scope.row.BJ_HAS_CHILD">
              <span class="table-other-td-item">已发货数量： {{ scope.row.quantityShipped }}</span>
              <span class="table-other-td-item">已退货数量： {{ scope.row.quantityReturned }}</span>
              <span class="table-other-td-item"
                >可退货数量： {{ scope.row.returnableQuantity }}</span
              >
            </div>
            <div v-if="!scope.row.BJ_HAS_CHILD">
              <span class="table-other-td-item">
                发货状态：{{
                  ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST.bjGet(scope.row.deliveryStatus)
                }}</span
              >
            </div>
          </template>
        </el-table-column>
      </template>
    </BjDialogList>
  </div>
</template>
<script>
import ElTableColumn from '@/components/ElTableColumn/index.vue';
import viewMiXin from '@/utils/view-mixin.js';
import { afterApi } from '@/api';
import orderlog from '@/views/center-business/order/components/LogTable/index.js';
import BjOrderStatus from '@/views/center-business/order/components/BjOrderStatus/index';
import EditTags from '@/views/center-business/order/components/EditTags';
import ReceivingWarehouseDialog from '@/views/center-business/after/components/ReceivingWarehouseDialog.vue';
import LogisticsDialog from '@/views/center-business/after/components/LogisticsInfoDialog.vue';
import UploadImgDialog from '@/views/center-business/after/components/UploadImgDialog.vue';
import BaseInfoDialog from '@/views/center-business/after/components/BaseInfoDialog.vue';
import SkuList from '@/views/center-business/after/components/SkuList';
import {
  CHINESE_NUM,
  INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
  AFTER_INTERNAL_RETURN_PAY_LIST,
  STATUS_YES_NUM,
  INTERNAL_AFTER_RETURN_TYPE_LIST,
  INTERNAL_AFTER_STAGE_STATUS,
  ORDER_ACTION_TYPE_LIST,
  ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
} from '@/utils/constants';
const ROW_KEY = 'BJ_EBC_ID';
const PARENT_KEY = 'originItemId';
export default {
  name: 'BusinessAfterInternalReturnView',
  components: {
    ElTableColumn,
    BjOrderStatus,
    UploadImgDialog,
    BaseInfoDialog,
    ReceivingWarehouseDialog,
    LogisticsDialog,
    EditTags,
    SkuList,
  },
  mixins: [viewMiXin],
  props: {},
  data() {
    return {
      ROW_KEY,
      INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST,
      AFTER_INTERNAL_RETURN_PAY_LIST,
      STATUS_YES_NUM,
      INTERNAL_AFTER_RETURN_TYPE_LIST,
      INTERNAL_AFTER_STAGE_STATUS,
      ORDER_ACTION_TYPE_LIST,
      ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST,
      /** ************* BjDialogSkuList 展开表格的配置  START **************** */
      /** ************* BjDialogSkuList 展开表格的配置  END **************** */
      /** ************* SkuList 配置  START **************** */
      skuTableData: [], // 数据
      parentColumnsBtn: [{ text: '删除', click: this.handleSkuLineDeleteFnForDialogSkuList }], // 主表操作按钮
      /** ************* SkuList 配置  END **************** */
      /** */
      editIndex: null,
      info: {
        items: [
          {
            _innerMap: {},
            deleteFlag: null,
            giftFlag: null,
            id: null,
            innerOrderNo: null,
            originItemId: null,
            outerOrderNo: null,
            outerRefundNo: null,
            quantity: null,
            skuCode: null,
            skuId: null,
            skuName: null,
            spuCode: null,
            spuName: null,
            spuType: null,
            type: null,
            unitPrice: null,
          },
        ],
        refundBaseInfoVO: {
          accessory: null,
          buyerRemarks: null,
          completeDate: null,
          creationDate: null,
          endorseStatus: null,
          innerOrderNo: null,
          innerRefundNo: null,
          outReturnTime: null,
          outerOrderNo: null,
          outerRefundNo: null,
          payType: null,
          reason: null,
          refundType: null,
          returnWarehousingTime: null,
          serviceRemarks: null,
          status: null,
          stage: null,
          storeId: null,
          storeName: null,
          tags: null,
        },
        refundBuyerInfoVO: {
          address: null,
          buyerNick: null,
          mobile: null,
          receiverName: null,
        },
        refundLogisticsInfoVO: {
          logisticsList: [],
          receivingNotice: null,
          receivingResult: null,
          receivingWarehouseId: null,
          receivingWarehouseName: null,
          receivingWarehouseStatus: null,
          warehousingNotice: null,
          warehousingResult: null,
        },
      },
    };
  },
  computed: {
    parentLabelList() {
      const temp = [
        { label: '商品编码', prop: 'spuCode', isCheck: true, disabled: false, width: 160 },
        { label: '商品名称', prop: 'spuName', isCheck: true, disabled: false },
        { label: 'SKU名称', prop: 'skuName', isCheck: true, disabled: false },
        { label: 'SKU编码', prop: 'skuCode', isCheck: true, disabled: false, width: 160 },
        { label: '是否赠品', prop: 'giftFlag', isCheck: true, disabled: false },
        { label: '商品行数量', prop: 'totalQuantity', isCheck: true, disabled: false, width: 90 },
        { label: '退货数量', prop: 'quantity', isCheck: true, disabled: false, width: 90 },
        { label: '退款单价', prop: 'unitPrice', isCheck: true, disabled: false },
        { label: '退款合计', prop: 'allPrice', isCheck: true, disabled: false },
      ];
      if (this.cptStatus == 0) {
        temp.push({
          label: '操作',
          prop: 'bjTableAction',
          isCheck: true,
          disabled: false,
          width: '150px',
        });
      }
      return temp;
    },
    SCHL_CHANNEL_TYPE() {
      return this.$store.state.values.SCHL_CHANNEL_TYPE || [];
    },
    BJ_AFTER_REFUND_GOODS_REASONS() {
      return this.$store.state.values.BJ_AFTER_REFUND_GOODS_REASONS || [];
    },
    items() {
      return this.info.items || [];
    },
    refundBaseInfoVO() {
      return this.info.refundBaseInfoVO || {};
    },
    id() {
      return this.refundBaseInfoVO.id || null;
    },
    refundBuyerInfoVO() {
      return this.info.refundBuyerInfoVO || {};
    },
    refundLogisticsInfoVO() {
      return this.info.refundLogisticsInfoVO || {};
    },
    cptOrderTagCodeList() {
      return this.refundBaseInfoVO.tags ? this.refundBaseInfoVO.tags.split(',') : [];
    },
    cptStatus() {
      return this.refundBaseInfoVO.status;
    },
    refundBaseInfoAccessory() {
      return this.refundBaseInfoVO.accessory ? this.refundBaseInfoVO.accessory.split(',') : [];
    },
    cptAuditLvlBtnText() {
      const LVL = this.getAuditLvl(this.refundBaseInfoVO.endorseStatus);
      return CHINESE_NUM[Number(LVL)];
    },
    cptTransferAction() {
      return this.refundBaseInfoVO.actionType == '101';
    },
  },
  watch: {},
  created() {},
  mounted() {
    this.$store.dispatch('values/getValueList', 'SCHL.CHANNEL_TYPE');
    this.$store.dispatch('values/getAfterRefundGoodsReasons');
    this.reFresh();
  },
  methods: {
    getAuditLvl(endorseStatus) {
      return endorseStatus.slice(3, 4);
    },
    getMessageName(endorseStatus) {
      // 取 审核状态码的第四位，1为一审，2为二审，以此类推
      return 'receive' + this.getAuditLvl(endorseStatus);
    },
    setSkuTableData(row) {
      const index = this.skuTableData.findIndex(e => e[ROW_KEY] == row[ROW_KEY]);
      this.skuTableData.splice(index, 1, row);
      console.log(' this.skuTableData', JSON.stringify(this.skuTableData));
    },
    reFresh() {
      afterApi
        .detailRefundOrderByIdCode({
          orderId: this.$route.query.id,
          orderCode: this.$route.query.code,
        })
        .then(res => {
          this.info = { ...this.info, ...(res || {}) };
          this.skuTableData = [
            ...this.items.map(e => {
              return {
                BJ_EBC_ID: e[PARENT_KEY],
                BJ_HAS_CHILD: true,
                BJ_PARENT_ID: null,
                BJ_SELECT_CHILD: [],
                ...e,
                allPrice: Number(e.quantity || 1) * Number(e.unitPrice || 0),
              };
            }),
          ];
          this.$nextTick(() => {
            this.$refs.SkuList.reFresh();
          });
        });
    },
    command(action) {
      switch (action) {
        case '审核通过':
          this.onApprovedOrder();
          break;
        case '审核拒绝':
          this.onRefuseOrder();
          break;
        case '取消':
          this.onCancelOrder();
          break;
        // case '已全部入库':
        //   this.onHaveBeenWarehoused();
        //   break;
        case '其他':
          break;
        default:
      }
    },
    onApprovedOrder() {
      this.$confirm('您确认要通过审核吗？')
        .then(_ => {
          this.auditRefundOrderApi({
            failReason: '',
            status: 1,
          });
        })
        .catch(_ => {});
    },
    onRefuseOrder() {
      this.$prompt('审核拒绝', {
        inputPlaceholder: '请输入拒绝原因',
        closeOnClickModal: false,
        inputType: 'textarea',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        // inputPattern: /\S/,
        inputErrorMessage: '请输入',
      })
        .then(({ value }) => {
          this.auditRefundOrderApi({
            failReason: value,
            status: 0,
          });
        })
        .catch(() => {});
    },
    onCancelOrder() {
      this.$prompt('您确认要取消该退款单吗？', '取消', {
        inputPlaceholder: '取消原因',
        closeOnClickModal: false,
        inputType: 'textarea',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        // inputPattern: /\S/,
        inputErrorMessage: '请输入',
      })
        .then(({ value }) => {
          this.$message({
            type: 'success',
            message: '取消成功 ',
          });
        })
        .catch(() => {});
    },
    // onHaveBeenWarehoused() {
    //   this.$confirm('您确认 已全部入库 吗？')
    //     .then(_ => {
    //       this.confirmRefundOrderWare({
    //         orderId: this.id,
    //       });
    //     })
    //     .catch(_ => {});
    // },
    /* 关闭弹窗 */
    handleClose() {
      this.cancel();
    },
    showLog() {
      orderlog(this.$route.query.id);
    },
    onEdit(row) {
      this.editIndex = row.shop3;
    },
    onDelete() {
      this.info.shopList.splice(null, 1);
    },
    /** 修改 基础信息 */
    openInfoData() {
      this.$refs.baseRef.show('修改基础信息', this.refundBaseInfoVO);
    },
    /** 修改客服备注 */
    onEditRemarks() {
      this.$refs.baseRef.show('修改客服备注', this.refundBaseInfoVO);
    },
    /** 修改退货物流 */
    openLogistics() {
      this.$refs.logisticsRef.show(this.id);
    },
    /** 修改退货仓库 */
    openReceivingWarehouse() {
      this.$refs.ReceivingWarehouseRef.show(this.refundLogisticsInfoVO.receivingWarehouseId);
    },
    /** 上传附件 */
    showUpload() {
      this.$refs.UploadImgDialog.show(this.refundBaseInfoAccessory);
    },
    handleAccessoryDelete(i) {
      this.$confirm('是否删除此附件？')
        .then(_ => {
          const TEMP = this.$lodash.clone(this.refundBaseInfoAccessory);
          TEMP.splice(i, 1);
          this.editBaseInfoApi({ accessory: TEMP.join() });
        })
        .catch(_ => {});
    },
    handleAccessoryView(index) {
      this.$refs.BjPreviewList.show(this.refundBaseInfoAccessory, index);
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
          this.editBaseInfoApi({ id: this.id, tags: POST_DATA.join() });
        })
        .catch(_ => {});
    },
    /** *****         BjDialogSkuList    START ********* */
    // 删除
    handleSkuLineDeleteFnForDialogSkuList(row) {
      console.log('handleSkuLineDeleteFnForDialogSkuList', row);
      this.$confirm('是否确定删除该数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 主表
        let parentRow = null;
        let childRow = null;
        if (row.BJ_HAS_CHILD) {
          parentRow = this.$lodash.cloneDeep(row);
          // 展开的子表
        } else {
          parentRow = this.skuTableData.find(e => e[ROW_KEY] == row.BJ_PARENT_ID);
          childRow = this.$lodash.clone(row);
        }
        delete parentRow.BJ_SELECT_CHILD;
        const CHILD_LIST = childRow ? [childRow] : [];
        const API_DATA = {
          isOnlyRefund: false,
          itemList: [
            {
              ...parentRow,
              itemLines: CHILD_LIST,
            },
          ],
        };
        this.deleteItemLineApi(API_DATA);
      });
    },
    // BjDialogSkuList 选择商品 show
    handleShowFnForDialogSkuList() {
      this.$refs.BjDialogSkuList.show();
    },
    // BjDialogSkuList 选择商品 重置数据，加 BJ_SELECT_CHILD 字段
    resetDataFnForDialogSkuList(data) {
      return data.map(e => {
        const OLD_ROW = this.skuTableData.find(oldItem => e[PARENT_KEY] == oldItem[ROW_KEY]);
        if (OLD_ROW) {
          const FLAG = OLD_ROW.quantity < e.returnableQuantity;
          console.log(FLAG);
        }
        return {
          BJ_EBC_ID: e[PARENT_KEY],
          BJ_HAS_CHILD: true,
          BJ_PARENT_ID: null,
          BJ_SELECT_CHILD: OLD_ROW?.BJ_SELECT_CHILD || [],
          ...e,
        };
      });
    },
    checkMethod(row) {
      return row.returnableQuantity != 0;
    },
    cellClassNameForDialogSkuList({ row, column, rowIndex, columnIndex }) {
      if (column.type == 'expand' && row.returnableQuantity == 0) {
        return 'bj-cell-expand-hide';
      }
      return 'bj-cell-expand-show';
    },
    // BjDialogSkuList 选择商品 当用户对某一行展开或者关闭的时候会触发该事件（展开行时，回调的第二个参数为 expandedRows；树形表格时第二参数为 expanded）
    loadMethodDialogSkuList({ row }) {
      const PARENT_ROW = { ...row };
      console.log('loadMethodDialogSkuList', JSON.parse(JSON.stringify(PARENT_ROW)));
      // 异步加载子节点
      return new Promise((resolve, reject) => {
        console.log('loadMethodDialogSkuList inPromise');
        if (row.returnableQuantity == 0) {
          this.$message.warning('可退货数量为0');
          resolve([]);
          return;
        }
        afterApi
          .getRefundGoodsItemListByItemId({
            itemId: PARENT_ROW[PARENT_KEY],
          })
          .then(res => {
            const TEMP = (Array.isArray(res) ? res : []).map(e => {
              return {
                ...e,
                BJ_EBC_ID: e['originItemLineId'],
                BJ_HAS_CHILD: false,
                BJ_PARENT_ID: PARENT_ROW[ROW_KEY],
                returnableQuantity: 1,
                quantity: 1,
                unitPrice: this.$number2money(row.unitPrice || 0),
                allPrice: this.$number2money(row.unitPrice || 0),
              };
            });
            if (TEMP.length) {
              resolve(TEMP);
            } else {
              this.$message.warning('返回数据为空，请联系管理员');
              reject();
            }
          });
      });
    },
    // 主表查询API
    parentQueryApi(queryParams) {
      const start = queryParams.currentPage * queryParams.pageSize;
      const end = queryParams.page * queryParams.pageSize;
      return new Promise(resolve => {
        resolve({
          content: this.skuTableData.slice(start, end),
          totalElements: this.skuTableData.length, // 数据总数
        });
      });
    },
    // SkuList展开查询API
    childrenQueryApi(PARENT_ROW) {
      return afterApi.getRefundDetailItemListByItemId({
        innerRefundNo: this.refundBaseInfoVO.innerRefundNo,
        originItemId: PARENT_ROW[PARENT_KEY],
      });
    },
    // BjDialogSkuList 选择商品 confirm
    confirmForDialogSkuList(tags, selectedObj) {
      const items = tags.map(e => {
        // e.BJ_SELECT_CHILD 选中的子集
        // 退款数量： 当有选中商品行时，以选中商品行length为退款数量，没有选中时，以可退数量为退款数量
        const QUANTITY = Number(e.BJ_SELECT_CHILD.length || e.returnableQuantity);
        return {
          ...e,
          BJ_HAS_CHILD: true,
          itemLines: e.BJ_SELECT_CHILD,
          quantity: QUANTITY,
          allPrice: this.$number2money(QUANTITY * Number(e.unitPrice || 0)),
          id: null,
          BJ_SELECT_CHILD: null,
        };
      });
      this.addRefundOrderApi({
        id: this.id,
        innerOrderNo: this.refundBaseInfoVO.innerOrderNo,
        innerRefundNo: this.refundBaseInfoVO.innerRefundNo,
        outerOrderNo: this.refundBaseInfoVO.outerOrderNo,
        outerRefundNo: this.refundBaseInfoVO.outerRefundNo,
        items,
      });
    },
    /** *****         BjDialogSkuList    END ********* */
    auditRefundOrderApi(POST_DATA) {
      if (!POST_DATA.auditId) {
        POST_DATA.auditId = this.id;
      }
      if (!POST_DATA.messageName) {
        POST_DATA.messageName = this.getMessageName(this.refundBaseInfoVO.endorseStatus);
      }
      // 放到数组中，转批量接口
      afterApi.batchAuditRefundOrder([POST_DATA]).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    editBaseInfoApi(POST_DATA) {
      if (!POST_DATA.id) {
        POST_DATA.id = this.id;
      }
      afterApi.updateRefundOrder(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          // this.$refs.EditRemarks.dialogVisible = false;
          // this.$refs.EditReceiverInfo.dialogVisible = false;
          this.$refs.EditTags.dialogVisible = false;
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    updateWarehouseApi(POST_DATA) {
      if (!POST_DATA.id) {
        POST_DATA.id = this.id;
      }
      afterApi.updateRefundOrderWarehouse(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          this.$refs.EditTags.dialogVisible = false;
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    deleteItemLineApi(POST_DATA) {
      afterApi.deleteRefundDetailItemListByItemId(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    addRefundOrderApi(POST_DATA) {
      afterApi.addSkuToRefundGoodsOrder(POST_DATA).then(res => {
        if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
          this.$message.success('操作成功');
          setTimeout(() => {
            this.reFresh();
          }, 500);
        }
      });
    },
    // confirmRefundOrderWare(POST_DATA) {
    //   afterApi.confirmRefundOrderWare(POST_DATA).then(res => {
    //     if (res.id || res._token || res.content || Array.isArray(res) || res.failed === false) {
    //       this.$message.success('操作成功');
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
.box {
  border: 1px solid #d7dae2;
  border-radius: 4px;
  padding: 10px 20px 20px 20px;
}
.bj-form-item-tips {
  margin: 4px 0;
  .img-url {
    display: inline-block;
    width: 90%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
}
</style>
