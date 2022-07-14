package org.skyer.tags.infra.constant;

/**
 * <p>
 * Hr 业务常量类
 * </p>
 *
 * @author qingsheng.chen 2018/6/22 星期五 14:54
 */
public class HrConstants {
    private HrConstants() {}

    public static class LovCode {
        /**
         * 数据来源Lov code
         */
        public static final String DATA_SOURCE = "HPFM.DATA_SOURCE";
    }


    /**
     * 维度类型
     *
     * @author
     */
    public static class DimensionType {
        /**
         * 平台级
         */
        public static final String DIMENSION_PLATFORM_TYPE = "platform";
        /**
         * 租户级
         */
        public static final String DIMENSION_TENANT_TYPE = "tenant";
        /**
         * 公司级
         */
        public static final String DIMENSION_COMPANY_TYPE = "company";
        /**
         * 用户级
         */
        public static final String DIMENSION_USER_TYPE = "user";
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
        public static final String RESERVE_RECORD_CODE ="HPFM.COMPANY";
        public static final String DISTRIBUTE_PLAN="HPFM.COMPANY";
        public static final String LOG_LEVEL = "GLOBAL";


    }
}
