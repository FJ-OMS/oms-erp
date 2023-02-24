/**
 * Brand Manage : 渠道仓库管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sfin/v1',
  items: [
    // 平台账单(原始)-天猫
    { key: 'getOriginalBillTmallsPage', url: '/original/bill/tmalls/page', method: 'POST' },
    // 获取查询条件栏数据-天猫
    {
      key: 'getOriginalBillTmallsQueryData',
      url: '/original/bill/tmalls/query/data',
      method: 'GET',
    },

    // 自动导入到正式表，一次执行三个流程(同步)
    {
      key: 'importOriginalBillTmalls',
      url: '/{organizationId}/import/data/sync/auto-import?templateCode=SFIN.BILL_TM',
      method: 'POST',
      type: 'form',
    },
    // 平台账单(原始)-京东
    { key: 'getOriginalBillJdsPage', url: '/original/bill/jds/page', method: 'POST' },
    // 获取查询条件栏数据-京东
    { key: 'getOriginalBillJdsQueryData', url: '/original/bill/jds/query/data', method: 'GET' },
    // 自动导入到正式表，一次执行三个流程(同步)
    {
      key: 'importOriginalBillJds',
      url: '/{organizationId}/import/data/sync/auto-import?templateCode=SFIN.BILL_JD',
      method: 'POST',
      type: 'form',
    },

    // 平台账单管理
    { key: 'getBillsPage', url: '/bills/page', method: 'POST' },
    // 获取查询条件栏数据-平台账单管理
    { key: 'getBillsQueryData', url: '/bills/query/data', method: 'GET' },

    // 新增、修改解析规则
    { key: 'saveParsingStrategys', url: '/parsing-strategys/save', method: 'PUT' },
    // 分页
    { key: 'getParsingStrategysPage', url: '/parsing-strategys/page', method: 'POST' },
    // 详情
    { key: 'getParsingStrategysDetail', url: '/parsing-strategys/detail/{id}', method: 'GET' },
    // 启用/禁用
    {
      key: 'updateParsingStrategysStatus',
      url: '/parsing-strategys/batch/enable',
      method: 'PATCH',
    },
    // 根据渠道类型编码，获取字段集
    {
      key: 'getBasicSettingFieldList',
      url: '/basic/setting/field/list',
      method: 'GET',
    },
  ],
};

export default {
  ...generate(config),
};
