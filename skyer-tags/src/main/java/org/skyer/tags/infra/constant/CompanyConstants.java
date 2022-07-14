package org.skyer.tags.infra.constant;


/**
 * 公司常量类
 *
 * @author
 */
public class CompanyConstants {

    /**
     * 企业审批后续处理类型
     *
     * @author
     */
    public static class CompanyApprovalProcessType {
        /**
         * 初审
         */
        public static final String FIRST_APPROVAL = "FIRST_APPROVAL";
        /**
         * 组织分配
         */
        public static final String ORG_ASSIGN = "ORG_ASSIGN";
        /**
         * 角色分配
         */
        public static final String ROLE_ASSIGN = "ROLE_ASSIGN";
    }

    /**
     * 企业审批后续处理状态
     *
     * @author
     */
    public static class CompanyApprovalProcessStatus {
        /**
         * 成功
         */
        public static final String SUCCESS = "S";
        /**
         * 错误
         */
        public static final String ERROR = "E";
    }

}
