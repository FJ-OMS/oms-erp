package org.skyer.order.infra.common;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2022/1/17 1:25 下午
 */
public class CommonConstants {

    public static final String STR_NUM_ONE ="1";
    public static final String STR_NUM_ZERO ="0";
    public static final String ORDER_SUSPEND_SUCCESS="订单挂起成功";
    public static final String ORDER_SUSPEND_FAILURE="订单挂起失败";
    public static final String AFTER_SALE_CREATE_KEY="afterSaleCreate_";

    public static class WayOfTakingDelivery{
        /**
         * 门店自提
         */
        public static final String WAY_OF_TAKING_DELIVERY_SELF="1";
        /**
         * 物流配送
         */
        public static final String WAY_OF_TAKING_DELIVERY_DELIVERY="2";
    }


    public static class ItemLineStatus {
        /**
         * 当前状态已完成
         */
        public static final String COMPLETE = "11000";
    }
    public static final Integer NUM_ONE =1;

    public static class RefundType {
        public static final String REFUND_ONLY = "1";
        public static final String REFUND_AND_RETURN_GOODS = "2";

    }
    public static class OrderTags {
        /**
         *  退货订单
         */
        public static final String RETURN ="SY004";
        /**
         *  退款订单
         */
        public static final String REFUND ="SY005";
        public static final String OUT_OF_STOCK ="SY007";
    }

    public static class RefundAudiStatus {
        /**
         * 待审核
         */
        public static final String WAIT_AUDIT = "0";
        /**
         * 待买家退货
         */
        public static final String WAIT_RETURN_GOODS = "1";
        /**
         * 待仓库验收
         */
        public static final String WAIT_WAREHOUSE_RECEIVE = "2";
        /**
         * 待财务退款
         */
        public static final String WAIT_REFUND = "3";
        /**
         * 已完成
         */
        public static final String COMPLETE = "4";
        /**
         * 已拒绝
         */
        public static final String REFUSE = "5";
        /**
         * 已取消
         */
        public static final String CANCEL = "6";
        /**
         * 转单异常
         */
        public static final String ORDER_EXCEPTION = "7";

    }

    public static class BpmnDeploy {
        public static final Integer NO = 0;

        public static final Integer YES = 1;
    }

    public static class CodeRule {
        public static final String INNER_ORDER_NO = "HPFM.COMPANY";
        public static final String ORDER_ITEM_CODE = "HPFM.COMPANY";
        public static final String PREEMP_NOTICE_NO = "HPFM.COMPANY";
        public static final String INNER_REFUND_NO = "HPFM.COMPANY";
        public static final String ITEM_LINE_CODE = "HPFM.COMPANY";
        public static final String PACK_PRICE_PACK = "HPFM.COMPANY";
        public static final String WAREHOUSING_NOTICE = "HPFM.COMPANY";
    }

    public static class LevelValue {
        public static final String INNER_ORDER_NO = "HPFM.COMPANY";
        public static final String ORDER_ITEM_NO = "HPFM.COMPANY";
        public static final String PREEMP_NOTICE_NO = "HPFM.COMPANY";
        public static final String INNER_REFUND_NO = "HPFM.COMPANY";
        public static final String ITEM_LINE_NO = "HPFM.COMPANY";
        public static final String PACK_PRICE_NO = "HPFM.COMPANY";
    }

    public static class WorkCode {
        public static final String NORMAL="200";
        public static final String SPLIT_ITEM_LINE_ERROR = "1000001";
    }

    public static class OrderStatus {
        public static final String  Complete = "1";
    }

    public static class OrderCancelStatus {
        public static final String NO = "0";
    }

    public static class OrderTradeType {
        /**
         * 销售单
         */
        public static final String SALE = "101";
    }

    public static class OrderActionType {
        /**
         * 普通转单
         */
        public static final String COMMON_RETURN = "101";

        /**
         * 手工单
         */
        public static final String HAND_ORDER = "201";
    }

	public static class SuspendType {
		/**
		 * 普通转单
		 */
		public static final Integer ARTIFICIAL = 1;

		/**
		 * 手工单
		 */
		public static final Integer SYSTEM = 2;
	}

    /**
     * 退款状态
     */
    public static class RefundStatus {
        public static final String NO = "0";
    }

    /**
     * 退款标识：0-正常，1-退款
     */
    public static class RefundFlag {
        public static final String NO = "0";
        public static final Integer YES = 1;
        public static final Integer NUM_NO = 0;
    }

    /**
     * 退货状态
     */
    public static class ReturnStatus {
        public static final String NO = "0";
    }

    /**
     * 退货标识：0-正常，1-退货
     */
    public static class ReturnFlag {
        public static final String NO = "0";
        public static final Integer NUM_YES = 1;
        public static final Integer NUM_NO = 0;
    }
    /**
     * 挂起状态
     */
    public static class SuspendStatus {
        public static final String NORMAL = "31200";
        public static final String ITEM_LINE_NORMAL = "11210";
        public static final String SUSPEND_SYSTEM_SUCCESS = "11211";
        public static final String SUSPEND_ARTIFICIAL_SUCCESS = "11221";
    }

    /**
     * 审核状态
     */
    public static class EndorseStatus {
        public static final String NORMAL = "0";
        public static final String SUCCESS = "1";
    }

    /**
     * 预占状态
     */
    public static class PreemptionStatus {
        public static final String PREEMPTION_WAIT = "30101";
        public static final String ITEM_PREEMPTION_WAIT = "20101";
        public static final String ITEM_LINE_PREEMPTION_WAIT = "10101";
    }

    /**
     * 寻源状态
     */
    public static class SourceStatus {
        public static final String SOURCING_WAIT = "30301";
        public static final String ITEM_SOURCING_WAIT = "20301";
        public static final String ITEM_LINE_SOURCING_WAIT = "10301";
    }

    /**
     * 发货状态
     */
    public static class SendStatus {
        public static final String SEND_INIT = "30510";
        public static final String ITEM_SEND_INIT = "20510";
        public static final String ITEM_LINE_SEND_INIT = "10510";
    }
    /**
     * 收货状态
     */
    public static class ReceivingStatus {
        public static final String RECEIVING_INIT = "30520";
        public static final String ITEM_RECEIVING_INIT = "20520";
        public static final String ITEM_LINE_RECEIVING_INIT = "10520";
    }

    /**
     * 同步预占信息到平台
     */
    public static class SyncPreempStatus {
        public static final String PREEMPTION_WRITE_WAIT = "30111";
        public static final String ITEM_PREEMPTION_WRITE_WAIT = "20111";
        public static final String ITEM_LINE_PREEMPTION_WRITE_WAIT = "10111";
    }

    /**
     * 同步收发货状态到平台
     */
    public static class SyncSendStatus {
        public static final String SEND_INIT = "30531";
        public static final String ITEM_SEND_INIT = "20531";
        public static final String ITEM_LINE_PREEMPTION_WRITE_WAIT = "10531";
    }

    /**
     * 异常状态
     */
    public static class ExceptionFlag {
        public static final String NORMAL = "0";

        public static final String EXCEPTION = "1";
    }

    public static class PreemptionNoticeStatus {
        /**
         * 待预占
         */
        public static final Integer WAIT = 0;
        /**
         * 预占成功
         */
        public static final Integer SUCCESS = 1;
        /**
         * 部分预占成功
         */
        public static final Integer PART_SUCCESS = 2;
        /**
         * 预占失败
         */
        public static final Integer FAIL = 3;
        /**
         * 预占取消
         */
        public static final Integer CANCEL = 4;

        public static final Integer ING = 5;
    }

    /**
     * 订单聚合状态
     */
    public static class OrderAggrStatus {

        public static final String NO_COMPLETE = "0";

        public static final String COMPLETE = "1";

        public static final String CLOSE = "2";

        public static final String EXCEPTION = "3";
    }

    /**
     * 异常处理code
     */
    public static class WorkMessageCode {
        public static final String CHANG_ITEM_LINE_NORMAL = "chang_item_line_normal";
        public static final String AUDIT_RECEIVED = "audit-received";
        public static final String SOURCE_COMPLETE = "source-complete";
    }

    public static class AuditStatusName {
        public static final String ORDER_STATUS = "order_status";

        public static final String ORDER_ITEM_STATUS = "order_item_status";

        public static final String ORDER_ITEM_LINE_STATUS = "order_item_line_status";

        public static final String IS_AUDIT_END = "is_audit_end";
        public static final String END_AUDIT = "end_audit";

        public static final String RETURN_STATUS = "return_status";
    }


    public static class EsConstant {
        /**
         * 商品sku es索引
         */
        public static final String GOODS_SPU_INDEX = "goods_spu_index_01";

        /**
         *  订单审核列表索引
         * */
        public static final String ORDER_AUDIT_INDEX = "skyer_order_audit";

        public static final String ORDER_INDEX = "skyer_order";

        public static final String ORDER_THIRD_INDEX = "skyer_third_order";
        // 精确匹配的字段
        /**
         * 内部外部单号
         */
        public static final String ORDER_NO = "orderNo";
        /**
         * 店铺ID
         */
        public static final String STORE_ID = "storeId";
        /**
         * 订单交易类型：销售单、仅退款单、退货退款单、换货单
         */
        public static final String TRADE_TYPE = "tradeType";
        /**
         * 订单创建类型：普通转单、手工单、复制单、导入单
         */
        public static final String ACTION_TYPE = "actionType";
        /**
         * 有无赠品
         */
        public static final String IS_GIFT = "isGift";
        /**
         * 收货人手机号
         */
        public static final String MOBILE = "mobile";
        /**
         * 聚合商品行的预占状态
         */
        public static final String PREEMPTION_STATUS = "preemptionStatus";
        /**
         * 聚合商品行的寻源状态
         */
        public static final String SOURCE_STATUS = "sourceStatus";
        /**
         * 聚合商品行的发（收）货状态
         */
        public static final String DELIVERY_STATUS = "deliveryStatus";
        /**
         * 订单级-挂起状态：0-正常，1-挂起成功，2-挂起失败，3-解挂成功，4-解挂失败
         */
        public static final String SUSPEND_STATUS = "suspendStatus";
        /**
         * 是否缺货
         */
        public static final String STOCK_FLAG = "stockFlag";
        /**
         * 渠道
         */
        public static final String CHANNEL = "channel";
        /**
         * 买家是否有备注
         */
        public static final String IS_BUYER_REMARKS = "isBuyerRemarks";
        /**
         * 商家是否有备注
         */
        public static final String IS_SERVICE_REMARKS = "isServiceRemarks";
        /**
         * 标签
         */
        public static final String TAGS = "tags";

        public static List<String> exactMatchFiles() {
            return Arrays.asList(STORE_ID,TRADE_TYPE,ACTION_TYPE,IS_GIFT,MOBILE,PREEMPTION_STATUS,
                    SOURCE_STATUS,DELIVERY_STATUS,SUSPEND_STATUS,STOCK_FLAG,CHANNEL,IS_BUYER_REMARKS,
                    IS_SERVICE_REMARKS);
        }


        // 模糊匹配字段
        /**
         * 买家昵称
         */
        public static final String BUYER_NIKE="buyerNick";

        /**
         * 收货人姓名
         */
        public static final String RECEIVER_NAME= "receiverName";

        /**
         * 买家备注/留言
         */
        public static final String BUYER_REMARKS="buyerRemarks";
        /**
         * 客服备注
         */
        public static final String SERVICE_REMARKS= "serviceRemarks";

        public static List<String> fuzzyMatchFiles() {
            return Arrays.asList(BUYER_NIKE,RECEIVER_NAME,BUYER_REMARKS,SERVICE_REMARKS);
        }



        // 区间匹配字段
        /**
         * 三方下单起始时间
         */
        public static final String ORDER_START_TIME= "orderStartTime";

        /**
         * 三方下单结束时间
         */
        public static final String ORDER_END_TIME= "orderEndTime";

        /**
         * 支付区间起始时间
         */
        public static final String PAY_START_TIME=  "payStartTime";

        /**
         * 支付区间结束时间
         */
        public static final String PAY_END_TIME= "payEndTime";

        /**
         * 内部单创建起始时间
         */
        public static final String CREATE_START_DATE="creationStartDate";

        /**
         * 内部单创建结束时间
         */
        public static final String CREATE_END_DATE="creationEndDate";

        /**
         * 预计发货起始时间
         */
        public static final String PREDICT_DELIVERY_START_TIME="predictDeliveryStartTime";

        /**
         * 预计发货结束时间
         */
        public static final String PREDICT_DELIVERY_END_TIME="predictDeliveryEndTime";

        /**
         * 发货起始时间
         */
        public static final String DELIVERY_START_TIME="deliveryStartTime";

        /**
         * 发货结束时间
         */
        public static final String DELIVERY_END_TIME="deliveryEndTime";

        /**
         * 确认收货起始时间
         */
        public static final String RECEIVED_START_TIME="receivedStartTime";

        /**
         * 确认收货结束时间
         */
        public static final String RECEIVED_END_TIME="receivedEndTime";

        /**
         * 商品总金额最小金额
         */
        public static final String TOTAL_AMOUNT_MIN="totalAmountMin";

        /**
         * 商品总金额最大金额
         */
        public static final String TOTAL_AMOUNT_MAX="totalAmountMax";

        /**
         * 总成本最小金额
         */
        public static final String COST_PRICE_MIN="costPriceMin";

        /**
         * 总成本最大金额
         */
        public static final String COST_PRICE_MAX="costPriceMax";

        /**
         * 买家应付最小金额
         */
        public static final String BUYER_PAY_MIN="buyerPayMin";

        /**
         * 买家应付最大金额
         */
        public static final String BUYER_PAY_MAX="buyerPayMax";

        public static List<String> rangeMatchFiles() {
            return Arrays.asList(ORDER_START_TIME,ORDER_END_TIME,PAY_START_TIME,PAY_END_TIME,CREATE_END_DATE,
                    PREDICT_DELIVERY_START_TIME,PREDICT_DELIVERY_END_TIME,DELIVERY_START_TIME,DELIVERY_END_TIME,
                    RECEIVED_START_TIME,RECEIVED_END_TIME,TOTAL_AMOUNT_MIN,TOTAL_AMOUNT_MAX,COST_PRICE_MIN,
                    COST_PRICE_MAX,BUYER_PAY_MIN,BUYER_PAY_MAX);
        }

    }
	/**
	 * 不可挂起状态
	 */
	public static class ForbiddenSuspend {
		/**
		 * 已收货
		 */
		public static final String RECEIVE_SUCCESS = "15022";
		/**
		 * 已发货
		 */
		public static final String SEND_SUCCESS = "10512";

		/**
		 * 待收货
		 */
		public static final String RECEIVE_WAIT = "10521";
		/**
		 * 已完成
		 */
		public static final String COMPLETE = "11000";
		/**
		 * 发货失败
		 */
		public static final String SEND_FAIL = "10513";
        /**
         * 挂起成功
         */
		public static final String SUSPEND_ARTIFICIAL_SUCCESS="11211";

        public static final String CANCEL = "11010";
		public static List<String> forbiddenSuspendFiles() {
			return Arrays.asList(RECEIVE_SUCCESS,SEND_SUCCESS,RECEIVE_WAIT,COMPLETE,SEND_FAIL,SUSPEND_ARTIFICIAL_SUCCESS,CANCEL);
		}

	}

	public static class preemptionAction {

	    public static final String PREEMPTION_TYPE = "0";

	    public static final String UN_PREEMPTION = "1";
    }

    public static class SagaRefType {

        public static final String CREATE_OUTER_ORDER = "create_outer_order";

        public static final String CREATE_INNER_ORDER = "create_inner_order";

        public static final String O2O_AUDIT_PROCESS = "o2o_audit_process";

        public static final String ORDER_PREEMPTION = "order_preemption";

        public static final String SOURCE_PROCESS = "source_process";

        public static final String ORDER_UN_PREEMPTION = "order_un_preemption";

        public static final String CREATE_THIRD_PARTY_REFUND_ORDER = "create_third_party_refund_order";

        public static final String CREATE_THIRD_PARTY_RETURN_ORDER = "create_third_party_return_order";

        public static final String CREATE_INNER_RETURN_ORDER = "create_inner_return_order";

        public static final String CREATE_INNER_REFUND_ORDER = "create_inner_refund_order";

        public static final String REFUND_PROCESS = "refund_process";

        public static final String RETURN_PROCESS = "return_process";

        public static final String RETURN_GOODS_WAREHOUSING = "return-goods-warehousing-warrant";
    }

    public static class OrderBusinessType {
        public static final String COMMON ="common";

        public static final String O2O ="o2o";
    }

    public static class AfterSaleActionType {
        /**
         * 普通转单
         */
        public static final String GENERAL = "201";

    }
}
