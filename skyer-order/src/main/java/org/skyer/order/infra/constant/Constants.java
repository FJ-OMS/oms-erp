package org.skyer.order.infra.constant;

import org.skyer.common.SkyerService;

/**
 * 平台常量
 *
 * @author chenzz
 */
public interface Constants {

    /**
     * api-success
     */
    String BAISONSERVICE_STATUS_SUCCESS = "api-success";

    /**
     * 值集禁止状态
     */
    String FORBIDDEN = "FORBIDDEN";

    /**
     * 服务简称
     */
    String APP_CODE = SkyerService.Platform.CODE;

    String SITE_LEVEL_UPPER_CASE = "SITE";
    String TENANT_LEVEL_UPPER_CASE = "TENANT";

    String CONFIG_CODE_FAVICON = "FAVICON";

    /**
     * 默认平台管理员角色
     */
    String DEFAULT_ROLE_ADMINISTRATOR = "role/site/default/administrator";
    /**
     * 默认平台管理员角色
     */
    String DEFAULT_ROLE_ORG_ADMINISTRATOR = "role/organization/default/administrator";
    /**
     * 默认模板角色
     */
    String DEFAULT_ROLE_ADMINISTRATOR_TEMPLATE = "role/organization/default/template/administrator";

    String HPFM_FILE_CONFIG_TYPE_CODE = "FILE";
    String HPFM_TEXT_CONFIG_TYPE_CODE = "TEXT";


    /**
     * kafka topic常量
     *
     * @author
     */
    interface KafkaTopics {
        /**
         * 企业注册
         */
        String HPFM_COMPANY_REGISTER = "hpfm_company_register";
        /**
         * 角色权限分配
         */
        String HPFM_HIAM_ROLE_ASSIGN = "hpfm_hiam_role_assign";
        /**
         * 企业注册后期处理
         */
        String COMPANY_REGISTER_POST_PROCESSING = "company_register_post_processing";
    }

    /**
     * 编码规则代码
     *
     * @author
     */
    interface RuleCodes {
        /**
         * 公司编码
         */
        String HPFM_COMPANY = "HPFM.COMPANY";
        /**
         * 集团编码
         */
        String HPFM_GROUP = "HPFM.GROUP";
        /**
         * 租户编码
         */
        String HPFM_TENANT = "HPFM.TENANT";
    }

    /**
     * DashboardLayout编码
     */
    interface DashboardLayout {
        /*
         * 默认租户
         */
        Long DEFAULT_TENANT_ID = 0L;
        /*
         * 默认用户ID
         */
        Long DEFAULT_USER_ID = 0L;
        /**
         * 默认角色ID
         */
        Long DEFAULT_ROLE_ID = 0L;
    }

    /**
     * 数据源常量
     */
    interface Datasource {
        /**
         * 数据库类型-Oracle
         */
        String DB_ORACLE = "ORACLE";
        /**
         * 数据库类型-Mysql
         */
        String DB_MYSQL = "MYSQL";
        /**
         * 数据库类型-Tidb
         */
        String DB_TIDB = "TIDB";
        /**
         * 数据库类型-SqlServer
         */
        String DB_MSSQL = "SQLSERVER";
        /**
         * 数据库类型-Hana
         */
        String DB_HANA = "HANA";

        /**
         * 数据库JDBC驱动
         */
        String DRIVER_CLASS_MYSQL = "com.mysql.jdbc.Driver";
        String DRIVER_CLASS_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String DRIVER_CLASS_ORACLE = "oracle.jdbc.driver.OracleDriver";
        /**
         * 数据库连接字符串
         */
        String DATA_SOURCE_URL_MYSQL = "jdbc:mysql://${host}:${port}/${database}?characterEncoding=${encoding}";
        String DATA_SOURCE_URL_SQLSERVER = "jdbc:sqlserver://${host};databaseName=${database}";
        String DATA_SOURCE_URL_ORACLE = "jdbc:oracle:thin:@${host}:${port}:${database}";
        String DATA_SOURCE_PURPOSE_DI = "DI";
        String DATA_SOURCE_PURPOSE_DT = "DT";
        String DATA_SOURCE_PURPOSE_DR = "DR";

        /**
         * 驱动类型（自定义，默认）
         */
        String CUSTOMIZE = "CUSTOMIZE";
        String DEFAULT = "DEFAULT";
    }

    /**
     * DashboardCard编码
     */
    interface DashboardCard {
        /**
         * 用户维度类型
         */
        String DIMENSION_TYPE_USER = "USER";
        /**
         * 租户维度类型
         */
        String DIMENSION_TYPE_TENANT = "TENANT";
    }

    /**
     * 属性归属
     */
    interface PropertyOwner {
        /**
         * 表格
         */
        String TABLE = "TABLE";
        /**
         * 列
         */
        String COLUMN = "COLUMN";
    }

    /**
     * 表格个性化
     */
    interface UiTable {

        interface SourceType {
            String TENANT = "TENANT";
            String ROLE = "ROLE";
            String USER = "USER";
        }
    }


    /**
     * 弹性域
     */
    interface Flex {

        /**
         * 条件
         */
        interface Symbol {
            String AND = "AND";
            String OR = "OR";
        }

        /**
         * 运算符
         */
        interface Operator {
            String EQUAL = "=";
            String LIKE = "LIKE";
        }

        /**
         * 层级
         */
        interface Scope {
            String TENANT = "T";
            String ROLE = "R";
            String USER = "U";
        }

        interface FieldType {
            String INPUT = "INPUT";
            String NUMBER = "NUMBER";
            String LOV = "LOV";
            String LANG = "LANG";
            String SELECT = "SELECT";
            String DATE = "DATE";
        }
    }

    /**
     * 表单个性化
     */
    interface Personality {
        /**
         * 层级
         */
        interface Scope {
            String TENANT = "T";
            String ROLE = "R";
            String USER = "U";
        }
    }

    /**
     * 服务器管理
     */
    interface Server {

        String SEPARATOR = "/";// File.separator;
        /**
         * 操作系统常量
         */
        String OS_LINUX = "LINUX";
    }

    /**
     * 个性化规则类型
     */
    interface CustomizeRuleType {
        String URL = "URL";
        String JAVA = "JAVA";
        String GROOVY = "GROOVY";
    }

    /**
     * 个性化规则位置
     */
    interface CustomizeRulePosition {
        String BEFORE = "BEFORE";
        String REPLACE = "REPLACE";
        String AFTER = "AFTER";
    }

    /**
     * 导入模板定义
     */
    interface ImportTemplateCode {
        /**
         * 平台多语言导入模板
         */
        String PROMPT_TEMP = "HPFM.PROMPT";
        /**
         * 返回消息管理模板
         */
        String MESSAGE_TEMP = "HPFM.MESSAGE";
        /**
         * 值集导入模板
         */
        String LOV_TEMP = "HPFM.LOV";
        /**
         * 值集值导入模板
         */
        String LOV_VALUE_TEMP = "HPFM.LOV_VALUE";
    }

    /**
     * 审批日志清理类型
     */
    interface ClearLogType {
        String CODE = "HPFM.AUDIT_LOG.CLEAR_TYPE";
        String THREE_DAY = "1";
        String ONE_WEEK = "2";
        String ONE_MONTH = "3";
        String THREE_MONTH = "4";
        String SIX_MONTH = "5";
        String ONE_YEAR = "6";
    }

    /**
     * 在线用户
     */
    interface OnlineUser {
        /**
         * 小时数
         */
        Integer HOURS = 24;
        /**
         * 初始在线人数
         */
        Long INITIAL_ONLINE = 0L;
    }

}
