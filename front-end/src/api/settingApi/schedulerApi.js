/**
 * JobInfo : 任务
 *

 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/ssdr',
  items: [
    /**
     * 任务列表
     */
    { key: 'getJobInfo', url: '/v1/{organizationId}/job-info', method: 'Get' },
    /**
     * 任务创建
     */
    { key: 'createJobInfo', url: '/v1/{organizationId}/job-info', method: 'POST' },
    /**
     * 任务修改
     */
    { key: 'updateJobInfo', url: '/v1/{organizationId}/job-info', method: 'PUT' },
    /**
     * 批量任务删除
     */
    { key: 'deleteJobInfo', url: '/v1/{organizationId}/job-info', method: 'DELETE' },
    /**
     * 任务明细
     */
    { key: 'getJobInfoById', url: '/v1/{organizationId}/job-info/{jobId}', method: 'GET' },
    /**
     * 任务挂起
     */
    {
      key: 'pauseJobInfo',
      url: '/v1/{organizationId}/job-info/pause',
      method: 'POST',
      type: 'query',
    },
    /**
     * 恢复任务
     */
    {
      key: 'resumeJobInfo',
      url: '/v1/{organizationId}/job-info/resume',
      method: 'POST',
      type: 'query',
    },
    /**
     * 终止任务
     */
    {
      key: 'stopJobInfo',
      url: '/v1/{organizationId}/job-info/stop',
      method: 'POST',
      type: 'query',
    },
    /**
     * 立即执行
     */
    {
      key: 'triggerJobInfo',
      url: '/v1/{organizationId}/job-info/trigger',
      method: 'POST',
      type: 'query',
    },
    /** ******** Job Log : 任务日志 ************* */
    /**
     * 任务日志列表
     */
    { key: 'getJobLogs', url: '/v1/{organizationId}/job-logs', method: 'Get' },
    /**
     * 单条删除日志
     */
    { key: 'deleteJobLogs', url: '/v1/{organizationId}/job-logs', method: 'DELETE' },

    // POST /v1/{organizationId}/job-logs/callback
    // 客户端日志回写

    // DELETE /v1/{organizationId}/job-logs/clear
    // 日志清理

    // GET /v1/{organizationId}/job-logs/{jobId}/logs
    // 单个任务日志列表

    // GET /v1/{organizationId}/job-logs/{logId}/error-detail
    // 查询任务异常详情

    // GET /v1/{organizationId}/job-logs/{logId}/log-detail
    // 查询任务日志详情

    // GET /v1/{organizationId}/job-logs/{logId}/progress
    // 查询任务进度

    /**
     *  Executor(Site Level) : 任务执行器(平台级)
     *
     */

    /**
     * 删除执行器
     */
    { key: 'deleteExecutors', url: '/v1/executors', method: 'DELETE' },

    /**
     * 查询执行器列表
     */
    { key: 'getExecutors', url: '/v1/executors', method: 'GET' },
    /**
     * 创建执行器
     */
    { key: 'addExecutors', url: '/v1/executors', method: 'POST' },
    /**
     * 更新执行器
     */
    { key: 'updateExecutors', url: '/v1/executors', method: 'PUT' },
    /** 配置执行器 addressList */

    /**
     * 执行器配置列表
     */
    { key: 'getExecutorsConfig', url: '/v1/executor-configs/{executorId}/config', method: 'GET' },

    /**
     * 更新及新建执行器配置
     */
    { key: 'updateExecutorsConfig', url: '/v1/executor-configs', method: 'POST' },

    /**
     * 删除执行器配置
     */
    { key: 'deleteExecutorsConfig', url: '/v1/executor-configs', method: 'DELETE' },

    /**
     *
     * Executable(Site Level) : 可执行定义(平台级)
     *
     */
    /**
     * 删除并发可执行
     */
    { key: 'deleteExecutables', url: '/v1/executables', method: 'DELETE' },
    /**
     * 并发可执行列表
     */
    { key: 'getExecutables', url: '/v1/executables', method: 'GET' },
    /**
     * 创建并发可执行
     */
    { key: 'addExecutables', url: '/v1/executables', method: 'POST' },
    /**
     * 修改并发可执行
     */
    { key: 'updateExecutables', url: '/v1/executables', method: 'PUT' },
    /**
     * 并发可执行明细
     */
    { key: 'getExecutablesDetail', url: '/v1/executables/', method: 'GET' },

    /**
     * SettingSchedulerRequest: Concurrent(Site Level) : 请求定义(平台级)
     */

    /**
     * 列表
     */
    { key: 'getRequest', url: '/v1/concurrents', method: 'GET' },
    /**
     * 创建
     */
    { key: 'addRequest', url: '/v1/concurrents', method: 'POST' },
    /**
     * 修改
     */
    { key: 'updateRequest', url: '/v1/concurrents', method: 'PUT' },
    /**
     * 明细
     */
    { key: 'detailRequest', url: '/v1/concurrents/', method: 'GET' },
    /**
     * 用户可选并发程序列表
     */
    { key: 'getUsableRequest', url: '/v1/concurrents/usable', method: 'GET' },

    /**
     * Concurrent Permission: 请求权限定义(平台级)
     */
    /**
     * 列表
     */
    { key: 'getRequestPermissions', url: '/v1/conc-permissions/{concurrentId}', method: 'GET' },
    /**
     * 创建
     */
    { key: 'addRequestPermissions', url: '/v1/conc-permissions', method: 'POST' },
    /**
     * 修改
     */
    { key: 'updateRequestPermissions', url: '/v1/conc-permissions', method: 'PUT' },
    /**
     *Concurrent Request: 并发请求(平台级)
     */
    /**
     * 明细
     */
    { key: 'detailConcurrent', url: '/v1/concurrent-requests/{requestId}', method: 'GET' },
    /**
     * 创建
     */
    { key: 'addConcurrent', url: '/v1/concurrent-requests', method: 'POST' },
    /**
     * 列表
     */
    { key: 'getConcurrent', url: '/v1/concurrent-requests', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
