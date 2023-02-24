/*
 * @Author: your name
 * @Date: 2021-06-23 10:00:11
 * @LastEditTime: 2022-06-10 15:04:18
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\utils\constants.js
 */
// bjTable里的表格最大高度 的最小值
export const BJ_TABLE_MINI_HEIGHT = 240;
// 售后的类型：  1-仅退款单 2- 退货退款
export const PAGE_SIZES = [20, 30, 50, 100, 200, 500, 1000];
// 售后的类型：  1-仅退款单 2- 退货退款
export const AFTER_ORDER_TYPES = {
  REFUND_ONLY: 1,
  REFUND_GOODS: 2,
};
// 商品中心-属性来源
export const GOODS_ATTRIBUTE_SOURCES_JSON = {
  SPU: 0, // 新增（分类属性 或 商品属性）
  SYS: 1, // 系统默认
  CLASSIFY: 2, // 复制上级 分类属性
  ATTACHED: 3, // 附加属性
};
// 输入框 max 长度
export const MAX_LENGTH = {
  DEFAULT: 50,
  CODE: 6, // 验证码
  NAME: 20, // 姓名、昵称
  REMARK: 200, // 备注、描述、说明等
  SHORT_NAME: 10, // 短短的名称
  PRIORITY: 999, // 优先级
  PRICE: 9, // 价格
};
// 商品类型 实物、虚拟
export const GOODS_TYPES = {
  REAL: 1,
  VIRTUAL: 2,
};
// 属性关联类型
export const ATTRIBUTE_RELATION_TYPES = {
  CLASSIFY: 1,
  GOODS: 2,
};
// ：执行器注册到服务的方式，包括自动注册和手动录入
export const EXECUTOR_TYPE_JSON = {
  AUTO: 0,
  MANUAL: 1,
};
// 表单类型
export const INPUT_BOX_TYPE_JSON = {
  RADIO: 1,
  CHECKBOX: 2,
  INPUT: 3,
  SELECT: 4,
};
/** ***************** ARRAY 与 JSON 分界线 ****************** */
const F = ar => {
  const a = [];
  new Map(ar).forEach((value, key) => {
    a.push({
      label: value,
      value: key,
    });
  });
  return a;
};
// 值集编码
export const LOV_CODE = F([
  ['SCHL.CHANNEL_TYPE', ' 渠道类型'],
  // ['SSRH.SOURCE_FROM_TYPE', '来源类型'],//索引列表
  ['STOCK.INPUT_STATUS', '入库状态'],
  ['STOCK.DISTRIBUTE_PLAN_STATUS', '分仓计划状态'],
  ['STOCK.ALLOT_STATUS', '调拨/移仓状态'],
  ['STOCK.OUT_TYPE', '出库类型'],
  ['STOCK.PROCESS_TYPE', '流程类型'],
  ['LOGISTICS.SETTLEMENT_METHOD', '结算方式'],
  ['STOCK.RECORD_STATUS', '单据状态类型'],
  ['STOCK.RECORD_TYPE', '单据类型'],
  ['STOCK.WAREHOUSE_TYPE', '仓库类型'],
  ['PRICE.ITEM.TYPE', '租户价格项'],
  ['SCHL.INVOICE_TYPE', ' 发票种类'],
  ['SCHL.SHOP_TYPE', ' 店铺类型'],
  ['HRPT.TEMPLATE_TYPE', '报表模板类型'],
  ['HRPT.REQUEST_STATUS', '请求状态'],
  ['HRPT.REPORT_TYPE', '报表类型'],
  ['HSDR.REQUEST.INTERVAL_TYPE', '时间间隔类型'],
  ['HSDR.PARAM_FORMAT', '参数格式'],
  ['HSDR.PARAM_EDIT_TYPE', '编辑类型'],
  ['HSDR.LOG.JOB_RESULT', '任务调度结果'],
  ['HSDR.LOG.CLIENT_RESULT', '客户端执行结果'],
  ['HSDR.JOB_STATUS', '任务状态'],
  ['HSDR.GLUE_TYPE', '调度任务类型'],
  ['HSDR.FAIL_STRATEGY', '失败策略'],
  ['HSDR.EXECUTOR_STRATEGY', '执行器策略'],
  ['HMSG.SMS_SERVER_TYPE', '短信服务类型'],
  ['HMSG.NOTICE.STATUS', '公告状态'],
  ['HMSG.NOTICE.RECERVER_TYPE', '公告发布对象类别'],
  ['HMSG.NOTICE.NOTICE_TYPE.CH', '公告类型-子集'],
  ['HMSG.NOTICE.NOTICE_CATEGORY', '公告类别'],
  ['HMSG.MESSAGE_TYPE', '消息模板类型'],
  ['HMSG.MESSAGE_SUBCATEGORY', '消息子分类'],
  ['HMSG.MESSAGE_CATEGORY', '消息分类'],
  ['HMSG.EMAIL_PROTOCOL', '邮件协议'],
  ['HMSG.EMAIL.FILTER_STRATEGY', '邮箱账户筛选策略'],
  ['HFLE.STORAGE_UNIT', '存储大小限制单位'],
  ['HFLE.PREFIX_STRATEGY', '文件名前缀策略'],
  ['HFLE.FILE_FORMAT', '文件格式'],
  ['HFLE.CONTENT_TYPE', '文件分类'],
  ['HFLE.CAPACITY.ACCESS_CONTROL', '存储配置目录权限'],
  ['HPFM.LANGUAGE', '语言'],
  ['HPFM.CODE_RULE.VARIABLE', '编码规则变量常量'],
  ['HPFM.CODE_RULE.DATE_MASK', '编码规则日期掩码'],
]);
export const STATUS_ENABLED_NUM = F([
  [0, '禁用'],
  [1, '启用'],
]);
export const STATUS_ENABLED_TRUE = F([
  [false, '禁用'],
  [true, '启用'],
]);
export const STATUS_YES_NUM = F([
  [0, '否'],
  [1, '是'],
]);
export const USE_FLAG_NUM = F([
  [0, '未使用'],
  [1, '已使用'],
]);
export const STATUS_YES_TRUE = F([
  [false, '否'],
  [true, '是'],
]);
export const STATUS_HAVE_TRUE = F([
  [false, '无'],
  [true, '有'],
]);
export const STATUS_HAVE_NUMBER = F([
  [0, '无'],
  [1, '有'],
]);
export const STATUS_INIT_LIST = F([
  [0, '未初始'],
  [1, '已初始'],
]);
export const USER_TYPE_LIST = F([
  ['P', '平台用户'],
  ['C', 'C端用户'],
]);
export const GENDER_LIST = F([
  [0, '男'],
  [1, '女'],
]);
export const GOODS_TYPE_LIST = F([
  [1, '实物商品'],
  [2, '虚拟商品'],
]);
// -商品中心-商品管理- 组合类型 (普通商品、套餐商品、子母件)
export const GOODS_COMBO_TYPES = {
  ORDINARY: 0,
  PACKAGE: 1,
  SON: 3,
};
export const GOODS_COMBO_TYPE_LIST = F([
  [0, '普通商品'],
  [1, '套餐商品'],
]);
// -商品中心-商品管理- 审核状态
export const GOODS_MANAGE_AUDIT_STATUS = F([
  [1, '待提交'],
  [2, '待审核'],
  [3, '审核通过'],
  [4, '被驳回'],
]);
// 商品中心-SKU-完整状态
export const GOODS_SKU_STATUS = F([
  [0, '草稿'],
  [1, '被驳回'],
  [2, '审核中'],
  [3, '已生效'],
  [4, '已生效-编辑中'],
  [5, '已生效-被驳回'],
]);
// 商品中心-SKU-状态 对应为 审核状态
export const GOODS_SKU_VIEW_AUDIT_STATUS = F([
  [0, '未提交审核'],
  [1, '被驳回'],
  [2, '审核中'],
  [3, '已完成'],
  [4, '审核中'],
  [5, '被驳回'],
]);
/** * 审核列表  对应 GOODS_SKU_STATUS */
// 默认： 1 2 3 4 5
// 待审核: 2 4
// 已生效 3
// 被驳回 1 5
export const GOODS_AUDIT_STATUS_LIST = F([
  [[2, 4], '待审核'],
  [[3], '已完成'],
  [[1, 5], '被驳回'],
]);
// 商品中心-SKU-状态（无草稿）
export const GOODS_SKU_STATUS_NORMAL = GOODS_SKU_STATUS.slice(1);
// 价格项，固定值;   'JG004', '内部分摊价'
export const GOODS_PRICE_ITEMS = F([
  ['costPrice', '成本价'],
  ['salesPrice', '销售价'],
  ['activityPrice', '活动价'],
  ['marketPrice', '市场价'],
  ['distributionPrice', '分销价'],
  ['wholesalePrice', '批发价'],
  ['clearingPrice', '结算价'],
]);
export const ATTRIBUTE_RELATION_TYPE_LIST = F([
  [1, '分类'],
  [2, '商品'],
]);
// 状态：
// 事务实例的运行状态。     共有进行中、完成、失败、没有消费者四种状态。
// 事务实例时任务的运行状态。共有进行中、完成、失败、队列中、等待被拉取五种种状态。
export const INSTANCE_RESULT_TYPE_LIST = F([
  ['RUNNING', '进行中'],
  ['FAILED', '失 败'],
  ['COMPLETED', '完 成'],
  ['NON_CONSUMER', '无消费者'],
  ['WAIT_TO_BE_PULLED', '等待被拉取'],
]);
// 存储类型
export const FILE_STORAGE_TYPE_LIST = F([
  [1, '阿里'],
  [2, '华为'],
  [3, 'Minio'],
  [4, '腾讯'],
  [5, '七牛'],
  [6, '本地'],
  [7, '京东云'],
  [8, 'AWS'],
  [9, '百度云'],
]);
// ：执行器注册到服务的方式，包括自动注册和手动录入
export const EXECUTOR_TYPE_LIST = F([
  [0, '自动注册'],
  [1, '手动录入'],
]);
export const INPUT_BOX_TYPE_LIST = F([
  [1, '单选框'],
  [2, '复选框'],
  [3, '文本框'],
  [4, '下拉框'],
]);
export const INDEXES_TYPE_LIST = F([
  [0, '失效'],
  [1, '有效'],
]);
export const FIELD_TYPE_LIST = F([
  [0, '正序'],
  [1, '倒序'],
]);
export const ORDER_CAM_ON_HIERARCHY = F([
  ['channel', '渠道仓'],
  ['virtual', '虚仓'],
]);
export const RADIX_POINT_COMPUTED_MODE_LIST = F([
  ['trunc', '向下舍取整'],
  ['plusOne', '向上舍取整'],
]);
export const SHOP_TYPE_LIST = F([
  ['OnlineShop', '网店'],
  ['PhysicalShop', '门店'],
]);
export const CODE_RULE_FIELD_TYPE_LIST = F([
  ['SEQUENCE', '序列'],
  ['CONSTANT', '常量'],
  ['DATE', '日期'],
  ['VARIABLE', '变量'],
  ['UUID', 'UUID'],
]);
export const UUID_LEN_LIST = F([
  [8, '8位'],
  [16, '16位'],
  [22, '22位'],
  [32, '32位'],
]);
/** 取自 STOCK.WAREHOUSE_TYPE 的子集*/
export const STOCK_WAREHOUSE_TYPE_SON_1_LIST = F([
  ['interline-virtual', '中转虚仓'],
  ['ratio-virtual', '比例虚仓'],
  ['qty-virtual', '数量虚仓'],
]);
/** 取自 STOCK.WAREHOUSE_TYPE 的子集*/
export const STOCK_WAREHOUSE_TYPE_SON_2_LIST = F([
  ['interline-virtual', '中转虚仓'],
  ['ratio-virtual', '比例虚仓'],
  ['qty-virtual', '数量虚仓'],
  ['default-virtual', '默认虚仓'],
  ['real', '实仓'],
  ['logic', '逻辑仓'],
]);
/** 取自 STOCK.WAREHOUSE_TYPE 的子集*/
export const STOCK_WAREHOUSE_TYPE_SON_3_LIST = F([
  ['qty-virtual', '数量虚仓'],
  ['interline-virtual', '中转虚仓'],
]);
/** 取自 STOCK.WAREHOUSE_TYPE 的子集*/
export const STOCK_WAREHOUSE_TYPE_SON_4_LIST = F([
  ['real', '实仓'],
  ['ratio-virtual', '比例虚仓'],
]);
export const REPLENISHMENT_ORDER_PRIORITY = F([
  ['order', '订单商品缺货优先'],
  ['shop', '店铺商品缺货优先'],
]);
export const RELEASE_STATUS_TRUE = F([
  [false, '未发布'],
  [true, '已发布'],
]);
export const CONFIGURE_STATUS_TRUE = F([
  [false, '未设置'],
  [true, '已设置'],
]);
export const TEMPLATE_DATA_TYPE_LIST = F([
  ['VARCHAR', 'VARCHAR'],
  ['BIGINT', 'BIGINT'],
  ['DECIMAL', 'DECIMAL'],
]);
export const STOCK_ADJUST_STATUS = F([
  [0, '未调整'],
  [1, '已调整'],
]);
export const STOCK_ADJUST_TYPE_LIST = F([
  [1, '增加'],
  [2, '减少'],
]);
/** 取自 STOCK.RECORD_STATUS 的子集*/
export const STOCK_LOCK_STATUS_LIST = F([
  ['wait', '待审核'],
  ['success', '审核成功'],
  ['fail', '审核失败'],
  ['abandon', '已作废'],
  ['close', '停用'],
]);
/** 取自 STOCK.RECORD_STATUS 的子集*/
export const STOCK_TRANSFER_VERIFY_TYPE_LIST = F([
  ['wait', '待审核'],
  ['success', '审核成功'],
  ['fail', '审核失败'],
]);
/** 取自 STOCK.RECORD_STATUS 的子集*/
export const STOCK_DISTRIBUTION_PLAN_STATUS_LIST = F([
  ['start', '开启'],
  ['stop', '暂停'],
  ['complete', '完成'],
  ['endTime', '到时截止'],
]);
export const VIRTUAL_GOODS_ROUTE_LIST = F([
  ['BusinessGoodsVirtualSkuGroupList', '组合商品'],
  ['BusinessGoodsVirtualSpuSonList', '子母件商品'],
  ['BusinessGoodsVirtualInstallServiceList', '安装服务'],
]);
export const STOCK_DELIVERY_ROUTE_LIST = F([
  ['BusinessStockDeliveryInformIndex', '出库通知单'],
  ['BusinessStockDeliveryResultIndex', '出库结果单'],
]);
export const STOCK_TRANSFER_ROUTE_LIST = F([
  ['BusinessStockTransferLogicIndex', '逻辑仓调拨'],
  ['BusinessStockTransferVirtualIndex', '虚仓移仓'],
]);
export const STOCK_VERIFY_ROUTE_LIST = F([
  ['BusinessStockVerifyLogicIndex', '逻辑仓调拨'],
  ['BusinessStockVerifyVirtualIndex', '虚仓移仓'],
  ['BusinessStockVerifyLockIndex', '库存预留'],
]);
export const STOCK_DISTRIBUTION_ROUTE_LIST = F([
  ['BusinessStockDistributionQtyIndex', '仓位数量分配'],
  ['BusinessStockDistributionRatioIndex', '仓位比例分配'],
  ['BusinessStockDistributionPlanIndex', '分仓计划'],
]);
export const LOGISTICS_COMPANY_ROUTE_LIST = F([
  ['BusinessLogisticsCompanyListIndex', '物流商列表'],
  ['BusinessLogisticsCompanyBusinessIndex', '物流业务管理'],
  ['BusinessLogisticsCompanyRelationIndex', '运费关联设置'],
  ['BusinessLogisticsCompanyFreightIndex', '运费模板'],
  ['BusinessLogisticsCompanyMapping', '物流映射'],
]);
export const LOGISTICS_ORDER_ROUTE_LIST = F([
  ['BusinessLogisticsOrderSale', '销售单物流'],
  ['BusinessLogisticsOrderAfter', '售后单物流'],
]);
export const SETTING_ORGANIZATION_ROUTE_LIST = F([
  ['SettingUserOrganizationGroup', '集团设置'],
  ['SettingUserOrganizationCompany', '下属公司'],
]);
export const LOGISTICS_ABNORMAL_ROUTE_LIST = F([
  ['BusinessLogisticsAbnormalDelivery', '派送异常'],
  ['BusinessLogisticsAbnormalDataAbort', '物流数据异常'],
]);
export const SALES_AFTER_INTERNAL_ROUTE_LIST = F([
  ['BusinessAfterInternalRefundIndex', '仅退款'],
  ['BusinessAfterInternalReturnIndex', '退货退款'],
  // ['BusinessAfterInternalExchangeIndex', '换货'],
]);
export const SALES_AFTER_THIRD_ROUTE_LIST = F([
  ['BusinessAfterThirdRefundIndex', '仅退款'],
  ['BusinessAfterThirdReturnIndex', '退货退款'],
  ['BusinessAfterThirdExchange', '换货'],
]);
export const FINANCE_PLATFORM_ROUTE_LIST = F([
  ['BusinessFinancePlatformTM', '天猫账单'],
  ['BusinessFinancePlatformJD', '京东账单'],
  ['BusinessFinancePlatformPDD', '拼多多账单'],
  ['BusinessFinancePlatformDY', '抖音账单'],
  ['BusinessFinancePlatformWPH', '唯品会账单'],
  ['BusinessFinancePlatformSNYG', '苏宁账单'],
  ['BusinessFinancePlatformXMYP', '小米账单'],
  ['BusinessFinancePlatformWYYX', '网易账单'],
]);
/** 物流商状态值集 */
export const LOGISTICS_CLAIM_STATUS_LIST = F([
  [0, '待审核'],
  [1, '待核销'],
  [2, '审核通过'], // 已审核
  [3, '审核拒绝'], // 已驳回
  [4, '已取消'],
]);
/** 物流商状态值集 */
export const LOGISTICS_CLAIM_CLAIMTYPE_LIST = F([
  [1, '破损'],
  [2, '丢件'],
]);
/** 物流商状态值集 */
export const LOGISTICS_CLAIM_STANDARD_LIST = F([
  [0, '普通标准售价'],
  [1, '内部分摊价'],
  [2, '出厂价'],
]);
export const EXPRESS_STATUS_LIST = F([
  ['0', '在途'],
  ['1', '揽收'],
  ['2', '疑难'],
  ['3', '签收'],
  ['4', '退签'],
  ['5', '派件'],
  ['6', '退回'],
  ['7', '转单'],
  ['10', '待清关'],
  ['11', '清关中'],
  ['12', '已清关'],
  ['13', '清关异常'],
  ['14', '拒签'],
]);
export const ABNORMAL_REASON_LIST = F([
  [1, '运费模板不匹配'],
  [2, '物流不匹配'],
  [3, '业务不匹配'],
]);
export const ABNORMAL_STATUS_LIST = F([
  [0, '未处理'],
  [1, '已处理'],
]);
/** 运费模板 */
export const LOGISTICS_COMPANY_FREGHHT_ADD_JFFS = F([
  [1, '按重量计费'],
  [2, '固定价格'],
]);
/** 运费模板 -> 理赔标准 */
export const LOGISTICS_COMPANY_COMPENSATE_LIST = F([['JG004', '内部分摊价']]);
export const SETTING_STOCK_PROCESS_TYPES = F([
  ['订单出库', '订单出库'],
  ['下架出库', '下架出库'],
  ['采购入库', '采购入库'],
  ['退货入库', '退货入库'],
]);
export const SETTING_STOCK_PROCESS_TYPE_JSON = {
  订单出库: 'out-stock-process',
  下架出库: 'out-stock-process',
  采购入库: 'put-stock-process',
  退货入库: 'put-stock-process',
};
export const ORDER_BACK_RES_LIST = F([
  ['0', '未回写'],
  ['1', '回写成功'],
  ['2', '回写失败'],
]);
export const THIRD_ORDER_RECEIVE_STATUS = F([
  ['0', '未收货'],
  ['1', '部分收货'],
  ['2', '已收货'],
]);
/** 商品行的寻源状态 */
export const ORDER_SKU_SOURCE_STATUS_LIST = F([
]);
/** 补货类型 */
export const ORDER_STOCKOUT_TYPE_LIST = F([
  [0, '订单补货'],
  [1, '店铺补货'],
]);
/** 退款单来源 */
export const AFTER_REFUND_SOURCE_STATUS = F([
  ['0', '手工单'],
  ['1', '普通转单'],
  ['2', '复制单'],
]);
/**
  13-仅退款	1-一审	 1-待审核	   31310
                    2-审核通过	31311
                    3-审核拒绝	31312
            2-二审	1-待审核	  31320
                    2-审核通过	31321
                    3-审核拒绝	31322
14-退货退款	1-一审	 1-待审核	   31410
                    2-审核通过	31411
                    3-审核拒绝	31412
            2-二审	1-待审核	  31420
                    2-审核通过	31421
                    3-审核拒绝	31422
 */
export const AFTER_REFUND_AUDIT_TABS_STATUS = F([
  ['31310,31320,31312,31322', '全部'],
  ['31310', '待一审'],
  ['31320', '待二审'],
  ['31312,31322', '审核拒绝'],
]);
export const AFTER_RETURN_AUDIT_TABS_STATUS = F([
  ['31410,31420,31412,31422', '全部'],
  ['31410', '待一审'],
  ['31420', '待二审'],
  ['31412,31422', '审核拒绝'],
]);
// 仅退款的审核状态 endorseStatus
export const AFTER_REFUND_ENDORSE_STATUS = F([
  ['31310', '待一审'],
  ['31311', '一审通过'],
  ['31312', '一审拒绝'],
  ['31320', '待二审'],
  ['31321', '二审通过'],
  ['31322', '二审拒绝'],
]);
// 退货退款的审核状态 endorseStatus
export const AFTER_RETURN_ENDORSE_STATUS = F([
  ['31410', '待一审'],
  ['31411', '一审通过'],
  ['31412', '一审拒绝'],
  ['31420', '待二审'],
  ['31421', '二审通过'],
  ['31422', '二审拒绝'],
]);
export const AFTER_REFUND_AUDIT_PERMISSION_RULES = [
  // 全部
  ['BusinessAfterAuditRefund_FirstAudit', 'BusinessAfterAuditRefund_SecondAudit'],
  // 待一审
  ['BusinessAfterAuditRefund_FirstAudit'],
  // 待二审
  ['BusinessAfterAuditRefund_SecondAudit'],
  // 审核拒绝
  ['BusinessOrderAuditIndex_ItIsAwayNot'],
];
export const AFTER_RETURN_AUDIT_PERMISSION_RULES = [
  // 全部
  ['BusinessAfterAuditReturn_FirstAudit', 'BusinessAfterAuditReturn_SecondAudit'],
  // 待一审
  ['BusinessAfterAuditReturn_FirstAudit'],
  // 待二审
  ['BusinessAfterAuditReturn_SecondAudit'],
  // 审核拒绝
  ['BusinessOrderAuditIndex_ItIsAwayNot'],
];
/** 内部仅退款状态 */
export const AFTER_REFUND_TRADE_STATUS = F([
  ['0', '待审核'],
  ['3', '待退款'],
  ['4', '已完成'],
  ['5', '已拒绝'],
  ['6', '已取消'],
  ['7', '转单异常'],
]);
export const AFTER_REFUND_TRADE_STATUS_FOR_VIEW = AFTER_REFUND_TRADE_STATUS.slice(0, 3);
/** 内部退货退款状态 */
export const AFTER_RETURN_TRADE_STATUS = F([
  ['0', '待审核'],
  ['1', '待买家退货'],
  ['2', '待仓库验收'],
  ['3', '待退款'],
  ['4', '已完成'],
  ['5', '已拒绝'],
  ['6', '已取消'],
  ['7', '转单异常'],
]);
export const AFTER_RETURN_TRADE_STATUS_FOR_VIEW = AFTER_RETURN_TRADE_STATUS.slice(0, 5);
export const AFTER_EXCHANGE_TRADE_STATUS = F([
  ['10', '待审核'],
  ['11', '待买家退货'],
  ['12', '待仓库验收'],
  ['13', '待仓库发货'],
  ['14', '待买家收货'],
  ['15', '已完成'],
]);
/** 业务中心/销售管理/业务预警-异常订单管理 */
export const ORDER_ABNORMAL_ROUTE_LIST = F([
  ['BusinessOrderAbnormalWarning', '业务预警'],
  ['BusinessOrderAbnormalSyncAbnormal', '数据异常'],
]);
/** 预警类型  异常类型 */
export const ORDER_ABNORMAL_TYPE_LIST = F([
  [0, '审核超时'],
  [1, '缺货超时'],
  [2, '发货超时'],
  [3, '订单更新超时'],
]);
/** 退货退款状态 */
export const AFTER_THIRD_RETURN_STATUS_LIST = F([
  [0, '待审核'],
  [1, '待买家退货'],
  [2, '待仓库验收'],
  [3, '待退款'],
  [4, '退款完成'],
  [5, '买家取消'],
  [6, '关闭退货退款'],
  [7, '拒绝退货'],
]);
/** 换货状态 */
export const AFTER_THIRD_EXCHANGE_STATUS_LIST = F([
  [0, '待审核'],
  [1, '待买家退货'],
  [2, '待发货'],
  [3, '待买家收货'],
  [4, '完成'],
  [5, '买家取消'],
  [6, '关闭换货'],
  [7, '拒绝换货'],
]);
/** 三方售后单/ 结算方式 */
export const AFTER_INTERNAL_RETURN_PAY_LIST = F([[1, '网店平台退款']]);
/** 三方售后单/ 转单状态 */
export const AFTER_THIRD_REFUND_TRANSFER_STATUS = F([
  [0, '正常'],
  [1, '异常'],
]);
/** 三方售后单/ 仓库收货状态 */
// export const AFTER_INTERNAL_RETURN_WAREHOUSE_RECEIVING_STATUS = F([[1, '未收货']]);
/** 待审核售后单 */
export const AFTER_AUDIT_ROUTE_LIST = F([
  ['BusinessAfterAuditRefundIndex', '仅退款'],
  ['BusinessAfterAuditReturnIndex', '退货退款'],
]);
/** 待审核售后单-退货退款/ 售后类型 */
export const AFTER_AUDIT_RETURN_TYPE_LIST = F([
  [0, '退货'],
  [1, '换货'],
]);
/** 物流策略-设置订单金额 */
/** 待审核售后单 */
export const STRATEGY_AUTO_REPLENISH_ROUTE_LIST = F([
  ['BusinessStrategyAutoReplenishOrder', '订单商品缺货'],
  ['BusinessStrategyAutoReplenishOnlineStore', '网店商品缺货'],
]);
/** 异常库存管理 router */
export const STOCK_ABNORMAL_ROUTE_LIST = F([
  ['BusinessStockAbnormalOrder', '订单缺货'],
  ['BusinessStockAbnormalWarehouse', '仓库缺货'],
]);
/** 支付状态 */
export const STOCK_ABNORMAL_ORDER_STATUS_LIST = F([
  [0, '待处理'],
  [1, '已采购'],
  [2, '已调拨'],
  [3, '已移仓'],
]);
/* ************************************** 寻源策略部分 start ****************************************** */
/** 异常库存管理 router */
export const STRATEGY_SOURCE_ROUTE_LIST = F([
]);
/* ************************************** 寻源策略部分 end ****************************************** */
/* ************************************** 内部销售单 start ****************************************** */
/** 订单 —— 商品 是否赠品 */
export const SKU_GIFT_TYPE_LIST = F([
  ['0', '否'],
  ['1', '赠品'],
  ['2', '随品'],
]);
export const SKU_GIFT_TYPE_LIST_FOR_CREATE = F([
  ['0', '否'],
  ['1', '是'],
]);
/** 订单 —— 交易类型 tradeType */
export const ORDER_TRADE_TYPE_LIST = F([['101', '销售单']]);
/** 订单 —— 业务类型 businessType */
export const ORDER_BUSINESS_TYPE_LIST = F([
  ['common', '普通订单'],
  ['o2o', 'O2O订单'],
]);
export const ORDER_BUSINESS_TYPES = {
  O2O: 'o2o',
  COMMON: 'common',
};
/** 订单 —— 提货方式 takingDeliveryWay */
export const ORDER_TAKING_DELIVERY_TYPE_LIST = F([
  ['1', '门店自提'],
  ['2', '物流配送'],
]);
/** 订单 —— 创建类型 / 订单来源 actionType  */
export const ORDER_ACTION_TYPE_LIST = F([
  ['101', '普通转单'],
  ['201', '手工单'],
  ['202', '复制单'],
]);
/** 内部销售单 --> 订单聚合状态 */
export const ORDER_INTERNAL_ORDER_STATUS_LIST = F([
  ['0', '未完成'],
  ['1', '已完成'],
  ['2', '已关闭'],
  ['3', '异常'],
]);
/** 内部销售O2O订单 --> O2O 订单聚合状态 */
export const ORDER_INTERNAL_O2O_STATUS_LIST = F([
  ['11', '待审核'],
  ['22', '待确认收货'],
  ['33', '已完成'],
]);
/** 三方销售单 --> 订单聚合状态  取自内部单，文字修改 */
export const ORDER_THIRD_ORDER_STATUS_LIST = F([
  ['0', '待发货'],
  ['1', '已完成'],
  ['2', '已关闭'],
  ['3', '异常'],
]);
// 内部销售单 --> 寻源前待审核状态
export const ORDER_INTERNAL_ORDER_AUDIT_TABS_BEFORE_SOURCE = F([
]);
// 内部销售单 --> 发货前待审核状态
export const ORDER_INTERNAL_ORDER_AUDIT_TABS_BEFORE_PREEMPT = F([
  ['30411,30421,30413,30423', '全部'],
  ['30411', '待一审'],
  ['30421', '待二审'],
  ['30413,30423', '审核拒绝'],
]);
// 内部普通销售单 --> 审核权限； 与 路由表 对应
export const ORDER_INTERNAL_ORDER_AUDIT_PERMISSION_RULES = [
  // 发货前
  [
    // 全部
    ['BusinessOrderAuditIndex_PreemptFirst', 'BusinessOrderAuditIndex_PreemptSecond'],
    // 待一审
    ['BusinessOrderAuditIndex_PreemptFirst'],
    // 待二审
    ['BusinessOrderAuditIndex_PreemptSecond'],
    // 审核拒绝
    ['BusinessOrderAuditIndex_ItIsAwayNot'],
  ],
];
// 内部o2o销售单 --> 审核权限； 与 路由表 对应
export const ORDER_INTERNAL_O2O_AUDIT_PERMISSION_RULES = [
  [
    // 全部
    ['BusinessO2oAuditIndex_First', 'BusinessO2oAuditIndex_Second'],
    // 待一审
    ['BusinessO2oAuditIndex_First'],
    // 待二审
    ['BusinessO2oAuditIndex_Second'],
    // 审核拒绝
    ['BusinessOrderAuditIndex_ItIsAwayNot'],
  ],
];
/** 订单 —— 预占状态 */
export const ORDER_INTERNAL_PREEMPT_STATUS_LIST = F([
  ['30101', '全部待预占'],
  ['30102', '全部预占成功'],
  ['30103', '全部预占失败'],
  ['30104', '部分预占'],
  ['31010', '取消预占'], // 订单取消时，预占也同时取消，状态码与订单取消状态相同
]);
export const ORDER_INTERNAL_SKU_PREEMPT_STATUS_LIST = F([
  ['20101', '全部待预占'],
  ['20102', '全部预占成功'],
  ['20103', '全部预占失败'],
  ['20104', '部分预占'],
  ['21010', '取消预占'], // 订单取消时，预占也同时取消，状态码与订单取消状态相同
]);
export const ORDER_INTERNAL_LINE_PREEMPT_STATUS_LIST = F([
  ['10101', '待预占'],
  ['10102', '预占成功'],
  ['10103', '预占失败'],
  ['10122', '解占成功'],
  ['11010', '取消预占'], // 订单取消时，预占也同时取消，状态码与订单取消状态相同
]);
// 预占管理-预占状态
export const ORDER_ADVANCE_PREEMPT_STATUS_LIST = F([
  [0, '全部待预占'],
  [1, '全部预占成功'],
  [3, '全部预占失败'],
  [2, '部分预占'],
  [4, '取消预占'],
]);
/** 订单 —— 预占回写状态 */
export const ORDER_INTERNAL_PREEMPT_BACK_STATUS_LIST = F([
  ['30111', '全部待回写'],
  ['30112', '全部回写成功'],
  ['30113', '全部回写失败'],
  ['30114', '部分回写'],
]);
export const ORDER_INTERNAL_SKU_PREEMPT_BACK_STATUS_LIST = F([
  ['20111', '全部待回写'],
  ['20112', '全部回写成功'],
  ['20113', '全部回写失败'],
  ['20114', '部分回写'],
]);
export const ORDER_INTERNAL_LINE_PREEMPT_BACK_STATUS_LIST = F([
  ['10111', '待回写'],
  ['10112', '回写成功'],
  ['10113', '回写失败'],
]);
/** 订单 —— 寻源状态 */
export const ORDER_INTERNAL_SOURCE_STATUS_LIST = F([
]);
export const ORDER_INTERNAL_SKU_SOURCE_STATUS_LIST = F([
]);
export const ORDER_INTERNAL_LINE_SOURCE_STATUS_LIST = F([
]);
/** 订单 —— 发货状态 */
export const ORDER_INTERNAL_DELIVER_STATUS_LIST = F([
  ['30510', '初始'],
  ['30511', '全部待发货'],
  ['30512', '全部发货成功'],
  ['30513', '全部发货失败'],
  ['30514', '部分发货'],
]);
export const ORDER_INTERNAL_SKU_DELIVER_STATUS_LIST = F([
  ['20510', '初始'],
  ['20511', '全部待发货'],
  ['20512', '全部发货成功'],
  ['20513', '全部发货失败'],
  ['20514', '部分发货'],
]);
export const ORDER_INTERNAL_LINE_DELIVER_STATUS_LIST = F([
  ['10510', '初始'],
  ['10511', '待发货'],
  ['10512', '发货成功'],
  ['10513', '发货失败'],
]);
/** 订单 —— 收货状态 */
export const ORDER_INTERNAL_RECEIVING_STATUS_LIST = F([
  ['30520', '初始'],
  ['30521', '待收货'],
  ['30522', '全部已收货'],
  ['30523', '部分收货'],
]);
export const ORDER_INTERNAL_SKU_RECEIVING_STATUS_LIST = F([
  ['20520', '初始'],
  ['20521', '待收货'],
  ['20522', '全部已收货'],
  ['20523', '部分收货'],
]);
export const ORDER_INTERNAL_LINE_RECEIVING_STATUS_LIST = F([
  ['10520', '初始'],
  ['10521', '待收货'],
  ['10522', '已收货'],
]);
/** 订单 —— 库存回写状态 */
export const ORDER_INTERNAL_STOCK_BACK_STATUS_LIST = F([
  ['30531', '全部待回写'],
  ['30532', '全部回写成功'],
  ['30533', '全部回写失败'],
  ['30534', '部分回写'],
]);
export const ORDER_INTERNAL_SKU_STOCK_BACK_STATUS_LIST = F([
  ['20531', '全部待回写'],
  ['20532', '全部回写成功'],
  ['20533', '全部回写失败'],
  ['20534', '部分回写'],
]);
export const ORDER_INTERNAL_LINE_STOCK_BACK_STATUS_LIST = F([
  ['10531', '待回写'],
  ['10532', '回写成功'],
  ['10533', '回写失败'],
]);
/** 订单 —— 不再履约 */
export const ORDER_INTERNAL_OVER_STATUS_LIST = F([
  ['31000', '正常履约完成'],
  ['31010', '订单取消'],
]);
/** 订单 —— 挂起状态 */
export const ORDER_INTERNAL_HAND_UP_STATUS_LIST = F([
  ['31200', '未挂起'],
  ['31201', '全部挂起成功'],
  ['31202', '全部挂起失败'],
  ['31203', '部分挂起'],
]);
export const ORDER_INTERNAL_SKU_HAND_UP_STATUS_LIST = F([
  ['21200', '未挂起'],
  ['21201', '全部挂起成功'],
  ['21202', '全部挂起失败'],
  ['21203', '部分挂起'],
]);
export const ORDER_INTERNAL_LINE_HAND_UP_STATUS_LIST = F([
  ['11210', ''],
  ['11200', '未挂起'],
  ['11211', '系统挂起成功'],
  ['11212', '系统挂起失败'],
  ['11213', '系统解挂成功'],
  ['11214', '系统解挂失败'],
  ['11221', '人工挂起成功'],
  ['11222', '人工挂起失败'],
  ['11223', '人工解挂成功'],
  ['11224', '人工解挂失败'],
]);
/** 退款状态 */
export const ORDER_INTERNAL_REFUND_STATUS_LIST = F([
  [0, '未退款'],
  [1, '部分退款'],
  [2, '全部退款'],
]);
/** 审核状态 */
export const ORDER_INTERNAL_AUDIT_STATUS_LIST = F([
  [30211, '待一审'],
  [30221, '待二审'],
  [30212, '一审通过'],
  [30222, '二审通过'],
  [30213, '一审不通过'],
  [30223, '二审不通过'],
  [30411, '待一审'],
  [30421, '待二审'],
  [30412, '一审通过'],
  [30422, '二审通过'],
  [30413, '一审不通过'],
  [30423, '二审不通过'],
]);
/** 支付方式 */
export const ORDER_INTERNAL_PAYMENT_LIST = F([
  ['alipay', '支付宝'],
  ['tenpay', '财付通'],
  ['chinabank', '网银在线'],
  ['cod', '货到付款'],
  ['bank', '银行汇款/转账'],
  ['other', '其他'],
]);
/** 支付状态 */
export const ORDER_INTERNAL_PAYMENT_STATUS_LIST = F([
  [0, '未支付'],
  [1, '已支付'],
]);
/* ************************************** 内部销售单 end ****************************************** */
/* ************************************** 内部售后单 start ****************************************** */
/** 退款单类型 */
export const INTERNAL_AFTER_REFUND_ORDER_TYPE_LIST = F([[0, '普通退单']]);
/** 退款类型 */
export const INTERNAL_AFTER_REFUND_TYPE_LIST = F([[1, '未发货仅退款']]);
export const INTERNAL_AFTER_RETURN_TYPE_LIST = F([[1, '退货退款']]);
/** 退款阶段 */
export const INTERNAL_AFTER_STAGE_STATUS = F([
  [0, '售中'],
  [1, '售后'],
]);
/** 退货原因 */
export const INTERNAL_AFTER_REASON_STATUS = F([[0, '7天无理由退货']]);
/* ************************************** 内部售后单 end ****************************************** */
/* ************************************** 三方销售单 start ****************************************** */
/** 支付状态 */
export const ORDER_THIRD_PAYMENT_STATUS_LIST = F([[1, '已支付']]);
/** 平台收货状态 */
export const ORDER_THIRD_RECEIVING_STATUS_LIST = F([
  [0, '未收货'],
  [1, '已收货'],
]);
/* ************************************** 三方销售单 end ****************************************** */
/* ************************************** 出库通知单 start ****************************************** */
export const ORDER_STOCK_OUT_TYPE_LIST = F([
  [0, '待通知WMS'],
  [1, '待出库'],
  [2, '通知WMS失败'],
  [3, '全部出库'],
  [4, '部分出库'],
  [5, '取消出库'],
]);
/* ************************************** 出库通知单 end ****************************************** */
/* ************************************** 出库结果单 start ****************************************** */
export const ORDER_STOCK_RESULT_STATUS_LIST = F([[0, '全部出库']]);
/* ************************************** 出库结果单 end ****************************************** */
/* ************************************** 销售审核策略 start ****************************************** */
/** 销售审核策略 router */
export const STRATEGY_MARKETINGAUDIT_ROUTE_LIST = F([
  ['SettingOrderAuditStrategy', '订单审核策略'],
  ['SettingOrderAuditGoods', '订单商品行审核策略'],
]);
/* ************************************** 销售审核策略 end ****************************************** */
/* ************************************** 财务管理 对账 start ****************************************** */
export const FINANCE_ORIGINAL_ROUTE_LIST = F([
  ['BusinessFinancePlatformOriginalTmallIndex', '天猫账单'],
  ['BusinessFinancePlatformOriginalJdcomIndex', '京东账单'],
]);
// 解析状态
export const FINANCE_PLATFORM_PARSING_STATUS = F([
  [0, '解析成功'],
  [1, '无需解析'],
]);
export const FINANCE_PLATFORM_PARSING_STATUS1 = F([
  [0, '解析失败'],
  [1, '解析成功'],
  [2, '无需解析'],
]);
// 对账状态
export const FINANCE_PLATFORM_CHECKING_STATUS = F([
  [0, '已对账'],
  [1, '未对账'],
  [2, '无需对账'],
]);
export const FINANCE_PLATFORM_CHECKING_STATUS1 = F([
  [1, '已对账'],
  [0, '未对账'],
]);
// 业务类型
export const FINANCE_PLATFORM_BUSINESS_TYPES = F([
  [0, '货款'],
  [1, '佣金'],
]);
// 流水类型
export const FINANCE_PLATFORM_FLOW_TYPES = F([
  ['0', '正流水'],
  ['1', '负流水'],
]);
// 业务中心-财务管理-对账管理-已对账列表 状态
export const FINANCE_CHECKING_CHECKED_STATUS = F([
  [true, '已对账'],
  [false, '金额异常'],
]);
// 财务管理-对账管理-物流对账  物流状态
export const FINANCE_CHECKING_LOGISTIC_SIGN_STATUS = F([
  [0, '已签收'],
  [1, '已退回'],
  [2, '拒签'],
]);
// 财务管理-对账管理-物流对账  结算状态
export const FINANCE_CHECKING_LOGISTIC_SETTLE_STATUS = F([
  [0, '已结算'],
  [1, '未结算'],
]);
// 财务管理-对账管理-物流对账  tab
export const FINANCE_CHECKING_LOGISTIC_COST_DETAIL_TYPES = F([
  [null, '所有费用明细'],
  ['1', '匹配费用明细'],
  ['2', '未匹配费用明细'],
]);
// 财务管理-对账管理-物流对账  匹配状态
export const FINANCE_CHECKING_LOGISTIC_MATCHING_TYPES = F([
  [0, '匹配'],
  [1, '匹配(金额差异)'],
  [2, '不匹配'],
]);
// 财务管理-对账管理-物流对账-内部物流单列表  费用确认状态
export const FINANCE_CHECKING_LOGISTIC_COST_CONFIRM_STATUS = F([
  [0, '已确认'],
  [1, '未确认'],
  [2, '金额差异'],
]);
/* ************************************** 财务管理 对账 end ****************************************** */
/* ************************************** 财务管理 平台账单解析策略设置  Start ****************************************** */
// 账单类型
export const FINANCE_PARSESTRATEGY_BULL_TYPES = F([
  [0, '销售收入'],
  [1, '退款支出'],
]);
// 金额取值
export const FINANCE_PARSESTRATEGY_PRICE_VALUE_TYPES = F([
  [0, '取固定字段'],
  [1, '按公式计算'],
]);
/* ************************************** 财务管理 平台账单解析策略设置  end ****************************************** */
/* ************************************** 财务管理 结算 Start ****************************************** */
// 财务管理-结算管理-结算列表  订单类型
export const FINANCE_SETTLEMENT_ORDER_TYPES = F([
  [0, '销售单'],
  [1, '仅退款单'],
  [2, '退货退款单'],
  [3, '换货单'],
]);
// 财务管理-结算管理-结算列表  售后单类型
export const FINANCE_SETTLEMENT_AFTER_TYPES = F([
  [1, '仅退款单'],
  [2, '退货退款单'],
  [3, '换货单'],
]);
// 财务管理-结算管理-结算列表  状态
export const FINANCE_SETTLEMENT_UNSETTLED_STATUS = F([
  [0, '已确认'],
  [1, '待确认'],
]);
// 财务管理-结算管理-结算列表  生成方式
export const FINANCE_SETTLEMENT_UNSETTLED_CREATE_TYPES = F([
  [0, '手动'],
  [1, '自动'],
]);
// 财务管理-结算管理-未结算列表  对账状态
export const FINANCE_SETTLEMENT_UNSETTLED_CHECKED_STATUS = F([
  [true, '正常'],
  [false, '异常'],
]);
// 结算管理-策略配置-结算策略配置-订单结算策略-新增 - 满足条件
export const FINANCE_SETTLEMENT_STRATEGY_ORDER_CONDITIONS = F([
  ['all', '满足以下所有条件'],
  ['one', '满足以下任一条件'],
]);
// 结算管理-策略配置-结算策略配置-订单结算策略-新增 - 满足条件
export const FINANCE_SETTLEMENT_STRATEGY_ORDER_CONDITION_TYPES = F([
  [0, '销售渠道'],
  [1, '订单来源'],
  [2, '订单类型'],
  [3, '订单标记'],
]);
// 结算管理-策略配置-结算策略配置-订单结算策略-新增 - 订单来源
export const FINANCE_SETTLEMENT_STRATEGY_ORDER_SOURCE = F([
  [0, '三方平台下载'],
  [1, '手工创建'],
  [2, '文件导入'],
]);
// 结算管理-策略配置-结算策略配置-订单结算策略-新增 - 订单来源
export const FINANCE_SETTLEMENT_STRATEGY_ORDER_TAGS = F([
  [0, '缺货'],
  [1, '异常'],
]);
// 结算管理-策略配置-结算策略配置-订单结算策略-新增 - 触发节点
export const FINANCE_SETTLEMENT_STRATEGY_ORDER_NODES = F([
  [0, '订单完成'],
  [1, '发货完成'],
]);
// 结算管理-策略配置-结算策略配置-售后单结算策略-新增
export const FINANCE_SETTLEMENT_STRATEGY_AFTER_CONDITION_TYPES = F([
  [0, '售后渠道'],
  [1, '售后单来源'],
  [2, '售后单类型'],
]);
// 结算管理-策略配置-结算策略配置-售后单结算策略-新增 - 触发节点
export const FINANCE_SETTLEMENT_STRATEGY_AFTER_NODES = F([
  [0, '售后完成'],
  [1, '审核完成'],
]);
/* ************************************** 财务管理 结算 end ****************************************** */
/* ************************************** 业务中心-营销管理-营销商品管理 Start ****************************************** */
export const MARKETING_COMMODITY_MANAGEMENT_ROUTER = F([
  ['BusinessMarketingGoodsGiftRule', '赠品规则管理'],
  ['BusinessMarketingGoodsWithPackageRule', '随包规则管理'],
]);
export const MARKETING_COMMODITY_MANAGEMENT_STATUS = F([
  [0, '未开始'],
  [1, '进行中'],
  [2, '已结束'],
  [3, '已中止'],
]);
export const MARKETING_COMMODITY_MANAGEMENT_TIME_TYPES = F([
  [0, '三方下单时间'],
  [1, '支付时间'],
]);
export const MARKETING_COMMODITY_MANAGEMENT_GIFT_STORE_TYPES = F([
  [1, '所有店铺'],
  [0, '指定店铺'],
]);
export const MARKETING_COMMODITY_MANAGEMENT_GIFT_GOODS_TYPES = F([
  [1, '店铺内所有商品'],
  [0, '指定商品'],
]);
/** 赠送条件 */
export const MARKETING_COMMODITY_MANAGEMENT_GIFT_TYPES = F([
  // [0, '满元赠'],
  [1, '满件赠'],
]);
export const MARKETING_COMMODITY_MANAGEMENT_PACKAGE_STATUS = F([
  [0, '未开始'],
  [1, '进行中'],
]);
/* ************************************** 业务中心-营销管理-营销商品管理 end ****************************************** */
/* ************************************** 业务中心-标签管理-系统标签 Start ****************************************** */
// 标签类型
export const LABEL_SYS_TYPES = F([
  [0, '手工单'],
  [1, '复制单'],
  [2, '补发单'],
  [3, '退货单'],
  [4, '退款单'],
  [5, '换货单'],
  [6, '订单缺货'],
  [7, '订单异常'],
]);
// BjTag 组件的样式
export const LABEL_HAND_COLORS = F([
  // [ value ,   name  ],
  ['orange', 'orange橙色'],
  ['yellow-dark', 'yellow-dark暗黄'],
  ['purple-light', 'purple-light淡紫'],
  ['brown-light', 'brown-light淡棕'],
  ['blue-purple', 'blue-purple蓝紫'],
  ['blue-light', 'blue-light淡蓝'],
  ['green-light', 'green-light淡绿'],
  ['orange-red', 'orange-red橘黄'],
]);
/* ************************************** 业务中心-标签管理-系统标签 end ****************************************** */
// 业务中心/销售管理/订单缺货管理
export const ORDER_SHORT_ROUTER = F([
  ['BusinessOrderShortIndexSetting', '订单缺货设置'],
  ['BusinessOrderShortIndexWaiting', '订单待发货'],
]);
// 商品中心-分类管理-类型
export const GOODS_CLASSIFY_TYPES = F([
  [false, '普通类目'],
  [true, '叶子类目'],
]);
// 商品中心-属性来源  有  GOODS_ATTRIBUTE_SOURCES_JSON
export const GOODS_ATTRIBUTE_SOURCES = F([
  [0, '新增'], // 商品属性
  [1, '系统默认'],
  [2, '父级分类'], // 分类属性
  [3, '附加属性'],
]);
// SKU 的 属性 分类
export const GOODS_ATTRIBUTE_TYPES = F([
  [0, '商品属性'],
  [1, '系统默认'],
  [2, '分类属性'],
  [3, '附加属性'],
]);
export const BPMN_STATUS_LIST = F([
  [0, '未布署'],
  [1, '已布署'],
]);
/** 标签管理 */
export const BUSINESS_LABEL_ROUTE_LIST = F([
  ['BusinessLabelSysIndex', '系统标签'],
  ['BusinessLabelHandIndex', '人工标签'],
]);
export const SETTING_PERMISSION_TYPE_LIST = F([
  ['api', '菜单'],
  ['btn', '按钮'],
  ['page', '页面'],
  ['tab', '标签'],
]);
export const SETTING_PERMISSION_TYPE_COLOR_LIST = F([
  ['api', 'info'],
  ['btn', 'warning'],
  ['tab', 'danger'],
  ['page', 'success'],
]);
export const ENDORSE_STATUS_LIST = F([
  ['0', '待审核'],
  ['1', '已审核'],
]);
/**                              报表管理                                       */
export const BUSINESS_REPORT_SALE_ROUTE_LIST = F([
  ['BusinessReportSaleCensusIndex', '销售统计表'],
  ['BusinessReportSaleAnalysisIndex', '销售分析表'],
  ['BusinessReportSaleGoodsIndex', '销售商品统计表'],
  ['BusinessReportSaleShopIndex', '店铺销售表'],
]);
export const BUSINESS_REPORT_STOCK_ROUTE_LIST = F([
  ['BusinessReportStockDeliverIndex', '发货汇总表'],
  ['BusinessReportStockOutIndex', '缺货统计表'],
]);
export const REPORT_SALE_STATISTICS_TIME_LIST = F([
  [1, '按三方下单时间'],
  [2, '按内部创建时间'],
]);
export const REPORT_SALE_STATISTICS_SUMMARY_LIST = F([
  [1, '按渠道'],
  [2, '按月份'],
  [3, '按地区'],
]);
/**                              报表管理end                                    */
export const CHINESE_NUM = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九'];
