package org.skyer.tags.infra.constant;

/**
 * 维护返回异常信息
 *
 * @author
 */
public class HpfmMsgCodeConstants {

    /**
     * 数据源驱动已经被使用，不可删除
     */
    public static final String ERROR_DELETE_USED_DRIVER = "hpfm.error.deleteUsedDriver";

    /**
     * 缓存Key所需参数获取失败
     */
    public static final String ERROR_CACHE_DATA_NULL = "hpfm.error.cache_data.null";

    /**
     * 事件编码已存在
     */
    public static final String ERROR_EVENT_CODE_EXISTS = "hpfm.error.event.code_exists";

    /**
     * 值集错误：无效的路由名
     */
    public static final String ERROR_LOV_INVALID_ROUTE_NAME = "hpfm.error.lov.invalid_route_name";
    /**
     * 当前规则已经被引用，禁止删除
     */
    public static final String ERROR_PERMISSION_RULE_FORBID_DELETE = "hpfm.error.permission_rule_cited";
    /**
     * 编码规则未找到
     */
    public static final String ERROR_CODE_RULE_NOT_FOUNT = "hpfm.error.code_rule.not_found";
    /**
     * 屏蔽规则类型重复
     */
    public static final String ERROR_PERMISSION_REF_RULE_TYPE_CODE_REPEAT = "hpfm.error.blocking_rule.exist";
    /**
     * 租户编码或名称重复
     */
    public static final String ERROR_TENANT_NAME_REPEAT = "hpfm.error.tenant.name_repeat";

    /**
     * 连接异常：不能建立JDBC连接
     */
    public static final String ERROR_CONNECTION = "hpfm.error.jdbc_connection";
    /**
     * 查询异常: 数据库中存在多条记录
     */
    public static final String ERR_REPEAT = "hpfm.error.data_repeat";
    /**
     * 查询异常: 没有主键或代码
     */
    public static final String ERR_NO_PRIMARY_KEY = "hpfm.error.no_pk";

    /**
     * 起始日期 {0} 晚于终止日期 {1}
     */
    public static final String DATE_RANGE_ERROR = "hpfm.error.date.start_later_end";

    /**
     * 操作异常：不能对公有库进行插入操作
     */
    public static final String ERROR_INSERT_TO_DATABASE = "hpfm.error.can_not_insert";
    public static final String ERROR_UPDATE_TO_DATABASE = "hpfm.error.can_not_update";
    public static final String ERROR_DELETE_TO_DATABASE = "hpfm.error.can_not_delete";
    public static final String ERROR_ALTER_TO_PUBLIC = "hpfm.error.database.can_not_alter";
    public static final String ERROR_DROP_TO_PUBLIC = "hpfm.error.database.can_not_drop";
    public static final String ERROR_CREATE_INDEX_TO_PUBLIC = "hpfm.error.database.can_not_create_index";
    public static final String NO_OPERATION_PERMISSION = "hpfm.error.operation_without_permission";
    /**
     * 查询到表提示没有改表
     */
    public static final String ERROR_NOT_EXIST_TABLE = "hpfm.error.table.not_exist";
    public static final String ERROR_NOT_EXIST_FIELD = "hpfm.error.field.not_exist";

    /**
     * 同一文本编码有效期重叠
     */
    public static final String REPEAT_DATE = "hpfm.error.text_code.repeat_date";
    /**
     * 文本编码不存在
     */
    public static final String TEXT_CODE_NOT_EXISTS = "hpfm.error.text_code.not_exists";
    /**
     * 文本编码和语言不存在
     */
    public static final String TEXT_CODE_LANG_NOT_EXISTS = "hpfm.error.text_code_lang.not_exists";
    /**
     * saga 发送消息失败
     */
    public static final String SEND_SAGA_MESSAGE_FAIL = "hpfm.error.send_saga_message.fail";
    /**
     * 获取规则引擎配置失败
     */
    public static final String GET_RULE_FAILED = "hpfm.error.rule_script.get_rule_failed";
    /**
     * 数据为空
     */
    public static final String DASHBOARD_DATA_EMPTY = "hpfm.error.data.empty";
    /**
     * 卡片code重复
     */
    public static final String DASHBOARD_CODE_REPEAT = "hpfm.error.code_repeat";
    /**
     * 当前角色下已经存在该卡片
     */
    public static final String DASHBOARD_ROLE_CARD_EXISTS = "hpfm.error.role_card.exists";
    /**
     * 事件规则数据不匹配
     */
    public static final String ERROR_EVENT_NOT_MATCH = "hpfm.error.event.notMatch";

    /**
     * saga调用失败回调
     */
    public static final String ERROR_SAGA_START_FAILED = "hpfm.error.saga.start";

    /**
     * 当前租户与传入值集租户信息不匹配
     */
    public static final String ERROR_LOV_TENANT_NOT_MATCH = "hpfm.error.lov_tenant.not_match";

    /**
     * 当前租户与传入多语言租户信息不匹配
     */
    public static final String ERROR_PROMPT_TENANT_NOT_MATCH = "hpfm.error.prompt_tenant.not_match";

    /**
     * 当前租户与传入规则引擎租户信息不匹配
     */
    public static final String ERROR_RULE_SCRIPT_TENANT_NOT_MATCH = "hpfm.error.rule_script.not_match";

    /**
     * 当前租户与传入数据权限租户信息不匹配
     */
    public static final String ERROR_PERMISSION_RULE_TENANT_NOT_MATCH = "hpfm.error.permission_rule.not_match";
    /**
     * 卡片分配租户传入数据存在重复信息
     */
    public static final String ERROR_CARD_TENANT_REPEAT = "hpfm.error.card_tenant.repeat";
    public static final String ERROR_CARD_CLAUSE_REPEAT = "hpfm.error.card_clause.repeat";
    /**
     * 起始时间不能大于截止时间
     */
    public static final String ERROR_DATE_IRREGULARITY = "hpfm.error.date.irregularity";
    public static final String ERROR_DATA_DISABLED = "hpfm.error.data.disabled";

    /**
     * 当前租户与传入静态文本租户信息不匹配
     */
    public static final String ERROR_STATIC_TEXT_TENANT_NOT_MATCH = "hpfm.error.static_text.not_match";

    public static final String ERROR_EVENT_TENANT_NOT_MATCH = "hpfm.error.event.not_match";

    /**
     * 弹性域模型被使用，不可删除
     */
    public static final String FLEX_MODEL_DELETE = "hpfm.flex.model.delete";
    /**
     * 弹性域模型字段被使用，不可删除
     */
    public static final String FLEX_MODEL_FIELD_DELETE = "hpfm.flex.model_field.delete";
    /**
     * 弹性域规则明细被使用，不可删除
     */
    public static final String FLEX_RULE_DETAIL_DELETE = "hpfm.flex.rule_detail.delete";
    /**
     * 弹性域字段初始化失败
     */
    public static final String FLEX_MODEL_FIELD_INIT = "hpfm.flex.model_field.init";
    public static final String ERROR_LOV_TENANT_REPEAT = "hpfm.lov.tenant.repeat";
    public static final String ERROR_LOV_SITE_COPY_FAIL = "hpfm.lov.site.copy_fail";
    public static final String ERROR_LOV_VIEW_TENANT_REPEAT = "hpfm.lov_view.tenant.repeat";
    public static final String ERROR_LOV_VIEW_SITE_COPY_FAIL = "hpfm.lov_view.site.copy_fail";
    /**
     * 模板配置已经存在，不可重复添加
     */
    public static final String ERROR_TEMPLATE_CONFIG_EXISTS = "hpfm.template_config.exists";
    /**
     * 当前数据下已经分配该模板，不可重复添加
     */
    public static final String ERROR_TEMPLATE_ASSIGN_EXISTS = "hpfm.template_assign.exists";
    public static final String ERROR_FORM_HEADER_NULL = "hpfm.error.form_header.null";
    public static final String ERROR_TEMPLATE_ASSIGN_DELETE_DEFAULT = "hpfm.error.template_assign.delete_default";

    public static final String ERROR_DATASOURCE_REGISTER_FAIL = "hpfm.error.datasource_register.fail";
    public static final String ERROR_GET_CONNECTION_FAIL = "hpfm.error.get_connection.fail";
    public static final String ERROR_RELATIONAL_PARAM_ILLEGAL = "hpfm.relational_param.illegal";
    public static final String ERROR_DATASOURCE_ID_NULL = "hpfm.error.datasource_id.null";

    /**
     * 数据组，数据已经存在
     */
    public static final String ERROR_GROUP_DATA_EXISTS = "hpfm.error.group_data_exists";
    public static final String ERROR_TEMPLATE_ALREADY_REFERENCED = "hpfm.error.template.is_referenced";
    /**
     * 平台多语言参数校验失败
     */
    public static final String ERROR_PROMPT_PARAM_NOT_NULL = "hpfm.error.prompt.param_not_null";

    /**
     * 导出错误信息
     */
    public static final String ERROR_EXPORT_TASK_NOT_EXISTS = "hpfm.error.export_task.not_exists";
    public static final String ERROR_EXPORT_TASK_CANCEL_FAILED = "hpfm.error.export.cancel_failed";
    public static final String ERROR_DRIVER_EXISTS = "hpfm.error.driver.exists";
    /**
     * 自定义驱动无法测试连接
     */
    public static final String ERROR_CUSTOM_DRIVER = "hpfm.error.custom_driver";
    /**
     * 父级未启用或父级不允许操作
     */
    public static final String ERROR_PARENT_DISABlE = "error.parent.disable";

    public static final String SEND_SOCKET = "hpfm.error.send_socket";

    public static final String REFRESH_CACHE = "hpfm.error.prompt.refresh_cache";
    /**
     * 数据异常：名称重复
     */
    public static final String ERROR_MULTIPLE_NAME = "error.multiple.name";
    /**
     * 值集没有父级值集
     */
    public static final String ERROR_LOV_IMPORT_HAVE_NOT_PARENT = "error.lov.import.have_not_parent";
    /**
     * 父级值集值不存在或未启用
     */
    public static final String ERROR_LOV_IMPORT_PARENT_VALUE_INVALID = "error.lov.import.parent_value_invalid";
    /**
     * 数据层级配置 下拉列表 数量超过最大配置数量
     */
    public static final String ERROR_DATA_HIERARCHY_SELECT_EXCEEDS_MAXIMUM_NUMBER_LIMIT =
                    "error.data_hierarchy.select.exceeds_maximum_number_limit";
    /**
     * 数据层级配置显示样式与父级层级不匹配
     */
    public static final String ERROR_PARENT_DISPLAY_NOT_MATCH = "error.data_hierarchy.display.not_match_parent";
    /**
     * 数据层级配置显示样式与父级层级不匹配
     */
    public static final String ERROR_CLUSTER_ASSIGN_SERVERS = "hpfm.error.clusterAssignServers";
    /**
     * 值集编码为: {0} 的父级值集不存在
     */
    public static final String ERROR_PARENT_LOV_NOT_NULL = "hpfm.error.lov.parentLovNotNull";
    /**
     * 值集错误：URL 值集 URL 内容不可为空
     */
    public static final String ERROR_LOV_URL_NOT_NULL = "hpfm.error.lov.urlNotNull";
    /**
     * 值集错误：SQL 值集 SQL 内容不可为空
     */
    public static final String ERROR_LOV_SQL_NOT_NULL = "hpfm.error.lov.sqlNotNull";
    /**
     * 值集错误：导入值集时不允许更新LovType内容
     */
    public static final String ERROR_LOV_TYPE_NOT_MATCH = "hpfm.error.lovTypeNotMatch";
}
