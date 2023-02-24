package org.skyer.tags.infra.constant;

/**
 * 基础模块常量
 *
 * @author
 */
public interface FndConstants {

    /**
     * 个性化页面值类型
     */
    interface AttributeType {

        /**
         * 字符串
         */
        String STRING = "STRING";

        /**
         * 数字
         */
        String NUMBER = "NUMBER";

        /**
         * boolean
         */
        String BOOLEAN = "BOOLEAN";
    }

    /**
     * 个性化页面应用层级
     */
    interface UiDimensionType {

        /**
         * 平台级
         */
        String SITE = "SITE";

        /**
         * 租户级
         */
        String TENANT = "TENANT";

        /**
         * 公司及
         */
        String COMPANY = "COMPANY";

        /**
         * 用户级
         */
        String USER = "USER";
    }

    /**
     * 缓存Key
     */
    interface CacheKey {

        /**
         * 模板配置缓存Key
         */
        String TEMPLATE_CACHE_KEY = Constants.APP_CODE + ":template:";

        /**
         * 模板配置缓存Key
         */
        String DEFAULT_TEMPLATE_CACHE_KEY = Constants.APP_CODE + ":default-template:";

        /**
         * 值集缓存
         */
        String LOV_KEY = Constants.APP_CODE + ":lov";

        /**
         * 配置维护
         */
        String PROFILE_KEY = Constants.APP_CODE + ":profile";

        /**
         * 事件
         */
        String EVENT_KEY = Constants.APP_CODE + ":event";

        /**
         * 个性化表格配置
         */
        String CUSTOM_TABLE_KEY = Constants.APP_CODE + ":custom_table";

        /**
         * 数据屏蔽
         */
        String PERMISSION_KEY = Constants.APP_CODE + ":permission";

        /**
         * 系统配置 服务级功能，所以不加fnd
         */
        String CONFIG_KEY = Constants.APP_CODE + ":config";

        /**
         * 多语言描述
         */
        String PROMPT_KEY = Constants.APP_CODE + ":prompt";

        /**
         * 数据权限数据库关系
         */
        String DATABASE_KEY = Constants.APP_CODE + ":database";

        /**
         * 数据源服务关系
         */
        String DATASOURCE_KEY = Constants.APP_CODE + ":datasource";

        /**
         * 静态文本
         */
        String STATIC_TEXT = Constants.APP_CODE + ":text";

        /**
         * 个性化页面
         */
        String Ui_PAGE = Constants.APP_CODE + ":page";

        /**
         * 静态文本头
         */
        String STATIC_TEXT_HEAD = STATIC_TEXT + ":head";

        /**
         * 静态文本行
         */
        String STATIC_TEXT_LINE = STATIC_TEXT + ":line";

        /**
         * 平台卡片
         */
        String DASHBOARD_CARD_KEY = Constants.APP_CODE + ":dashboard_card";

        /**
         * 租户卡片分配
         */
        String DASHBOARD_TENANT_CARD_KEY = Constants.APP_CODE + ":dashboard_tenant_card";
    }

    /**
     * 值集类型
     */
    interface LovTypeCode {
        /**
         * URL型
         */
        String URL = "URL";
        /**
         * 自定义SQL型
         */
        String SQL = "SQL";
        /**
         * 独立值集型
         */
        String INDEPENDENT = "IDP";
    }

    /**
     * 事件调用类型
     */
    interface CallType {
        /**
         * 方法
         */
        String METHOD = "M";
        /**
         * API
         */
        String API = "A";
    }

    /**
     * 配置维护值应用层级
     */
    interface ProfileLevelCode {
        /**
         * 配置维护值应用层级-角色级
         */
        String ROLE = "ROLE";

        /**
         * 配置维护值应用层级-用户级
         */
        String USER = "USER";

        /**
         * 配置维护值应用层级-全局(对应该租户下的所有)
         */
        String GLOBAL = "GLOBAL";
    }

    /**
     * 编码规则应用层级
     */
    interface CodeRuleLevelCode {

        /**
         * 全局级
         */
        String GLOBAL = "GLOBAL";

        /**
         * 公司
         */
        String COMPANY = "COM";
    }

    /**
     * 应用维度
     */
    interface Level {
        /**
         * 应用维度-租户级
         */
        String TENANT = "T";

        /**
         * 应用维度-平台级
         */
        String PLATFORM = "P";
    }

    /**
     * 编码规则段类型
     */
    interface FieldType {
        /**
         * 序列
         */
        String SEQUENCE = "SEQUENCE";

        /**
         * 常量
         */
        String CONSTANT = "CONSTANT";

        /**
         * 日期
         */
        String DATE = "DATE";

        /**
         * 变量
         */
        String VARIABLE = "VARIABLE";

        /**
         * uuid
         */
        String UUID = "UUID";
    }

    /**
     * 重置频率
     */
    interface ResetFrequency {

        /**
         * 从不
         */
        String NEVER = "NEVER";

        /**
         * 每年
         */
        String YEAR = "YEAR";

        /**
         * 每季
         */
        String QUARTER = "QUARTER";

        /**
         * 每月
         */
        String MONTH = "MONTH";

        /**
         * 每天
         */
        String DAY = "DAY";
    }

    /**
     * 序列
     */
    interface Sequence {

        /**
         * 自增步长
         */
        Long STEP = 1L;

        /**
         * 记录频率
         */
        Long STEP_NUM = 100L;
    }

    /**
     * 数据来源
     *
     * @author
     */
    interface DataSource {
        String SKYER = "SKYER";
        String SRM = "SRM";
        String ERP = "ERP";
    }

    /**
     * 数据屏蔽规则类型代码
     */
    interface PermissionRuleTypeCode {

        /**
         * sql
         */
        String SQL = "SQL";

        /**
         * 数据库前缀
         */
        String PREFIX = "PREFIX";
    }

    /**
     * 数据源用户密码返回值
     */
    interface DatasourcePassword {
        /**
         * 返回前端的密码信息
         */
        String RETURNEDSTRING = "******";
    }
}
