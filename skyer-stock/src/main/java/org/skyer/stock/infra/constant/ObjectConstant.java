package org.skyer.stock.infra.constant;

/**
 * @description
 * @Author wusc
 * @create 2021/6/25 3:27 下午
 */
public class ObjectConstant {
    // 默认租户 0
    public static final Long DEFAULT_TENANT_ID = 0L;

    public static class LovRecordTypeCode {
        // 调整单
        public static final String ADJUST_RECORD = "adjustRecord";
        // 调拨单
        public static final String ALLOT_RECORD = "allotRecord";
        // 移仓单
        public static final String MOVE_RECORD = "moveRecord";
        // 移仓单
        public static final String MOVE_ROLLBACK = "moveRollback";
        // 预留单
        public static final String RESERVE_RECORD = "reserveRecord";
        // 预占记录
        public static final String PREEMPTION_RECORD = "preemptionRecord";
        // 解占记录
        public static final String RESCISSION_PREEMPTION_RECORD = "rescissionPreemptionRecord";
        // 分仓计划
        public static final String DISTRIBUTE_PLAN = "distributePlan";
        // 库存同步
        public static final String ASYNC = "async";
        // 仓位
        public static final String FREIGHT_SPACE = "freightSpace";
        // 通知记录
        public static final String NOTICE_RECORD = "noticeRecord";
        // 结果记录
        public static final String RESULT_RECORD = "resultRecord";
        // 寻源记录
        public static final String SOURCING_RECORD = "sourcingRecord";
        // 寻源回滚
        public static final String SOURCING_ROLLBACK = "sourcingRollback";
        // 解除寻源记录
        public static final String RELIEVE_SOURCING_RECORD = "relieveSourcingRecord";
    }

    /**
     * 出库类型
     *
     * @author lixiaoyang
     * @date 2022/4/2 14:42
     */
    public static class OutType {
        // 订单销售出库
        public static final String OUT_TYPE_ORDER_SALES_OUT_STOCK = "order-sales-out-stock";
        // 仓库调整出库
        public static final String OUT_TYPE_ADJUST_OUT_STOCK = "adjust-out-stock";
        // 仓库盘点出库
        public static final String OUT_TYPE_COUNT_OUT_STOCK = "stock-count-out-stock";
    }

    public static class StockChange {

        /**
         * 增加
         */
        public static final Integer ADD = 1;

        /**
         * 减少
         */
        public static final Integer REDUCE = 2;
    }


    public static class CodeRule {
        public static final String QD_WAREHOUSE_CODE = "HPFM.COMPANY";
        public static final String MOVE_RECORD_CODE = "HPFM.COMPANY";
        public static final String ADJUST_RECORD_CODE = "HPFM.COMPANY";
        public static final String DISTRIBUTE_PLAN = "HPFM.COMPANY";
        public static final String RESERVE_RECORD_CODE = "HPFM.COMPANY";
        public static final String LOG_CODE = "HPFM.COMPANY";

    }

    public static class LevelValue {
        public static final String QD_WAREHOUSE_CODE = "HPFM.COMPANY";
        public static final String ADJUST_RECORD_CODE = "HPFM.COMPANY";
        public static final String MOVE_RECORD_CODE = "HPFM.COMPANY";
        public static final String RESERVE_RECORD_CODE = "HPFM.COMPANY";
        public static final String DISTRIBUTE_PLAN = "HPFM.COMPANY";
        public static final String LOG_LEVEL = "GLOBAL";


    }

    public static class LovStockQty {
        // 在库
        public static final String STOCK_NUM = "stockNum";
        // 可用
        public static final String USABLE_NUM = "usableNum";
        // 预留
        public static final String RESERVED_NUM = "reservedNum";
        // 预占
        public static final String PREEMPTION_NUM = "preemptionNum";
        // 锁定
        public static final String LOCK_NUM = "lockNum";
        //在途
        public static final String WAY_NUM = "wayNum";
    }

    public static class EsKey {
        public static final String STOCK_ES = "ssrh_open_stock";
    }


    /**
     * 仓库类型
     *
     * @author lixiaoyang
     * @date 2021/8/12 9:34
     */
    public static class WarehouseTypeCode {
        // 渠道仓
        public static final String CHANNEL = "channel";
        // 逻辑仓
        public static final String LOGIC = "logic";
    }

    /**
     * 软删
     *
     * @author lixiaoyang
     * @date 2022/6/8 14:49
     */
    public static class DeleteFlag {
        /**
         * 未删除
         */
        public static final Boolean NO = Boolean.FALSE;

        /**
         * 删除
         */
        public static final Boolean YES = Boolean.TRUE;
    }

    /**
     * 审核状态
     *
     * @author lixiaoyang
     * @date 2022/6/8 14:54
     */
    public static class AuditStatus {
        /**
         * 待审核
         */
        public static final String WAIT_STATUS = "wait";

        /**
         * 审核成功
         */
        public static final String SUCCESS_STATUS = "success";

        /**
         * 启用
         */
        public static final String START_STATUS = "start";
        /**
         * 审核失败
         */
        public static final String FAIL_STATUS = "fail";
        /**
         * 关闭
         */
        public static final String CLOSE_STATUS = "close";

        /**
         * 废弃
         */
        public static final String ABANDON_STATUS = "abandon";

        /**
         * 在途
         */
        public static final String WAY_STATUS = "way";

        /**
         * 完成
         */
        public static final String COMPLETE_STATUS = "complete";

        /**
         * 暂停
         */
        public static final String STOP = "stop";

        /**
         * 到时结束
         */
        public static final String END_TIME = "endTime";
    }

    public static class InOutType {
        /**
         * 入库
         */
        public static final Integer IN = 1;

        /**
         * 出库
         */
        public static final Integer OUT = 2;
    }

    public static class EnableFlag {
        /**
         * 启用
         */
        public static final Boolean ENABLE = Boolean.TRUE;

        /**
         * 禁用
         */
        public static final Boolean DISABLED = Boolean.FALSE;
    }


    public static class DeliveryType {
        /**
         * 发
         */
        public static final Integer SEND = 0;

        /**
         * 收
         */
        public static final Integer RECEIVE = 1;
    }

    /**
     * 前置业务单类型
     *
     * @author lixiaoyang
     * @date 2022/4/27 10:42
     */
    public static class FrontRecordType {
        // 售后单-退货退款单
        public static final String FRONT_RECORD_TYPE_AFTER_SALE_2 = "after_sale_2";
    }

}
