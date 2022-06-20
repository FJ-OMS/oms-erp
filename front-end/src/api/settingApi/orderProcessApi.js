/*
 * @Author: your name
 * @Date: 2021-10-14 13:51:56
 * @LastEditTime: 2021-11-02 11:32:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\settingApi\processOrderApi.js
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sord/v1/order-flow-config',
  items: [
    /** 创建 */
    { key: 'addOrderFlowConfig', url: '', method: 'POST' },
    /* 删除订单流程配置(不允许删除) */
    { key: 'deleteOrderFlowConfig', url: '', method: 'DELETE' },
    /* 订单流程配置列表分页 */
    { key: 'getOrderFlowConfig', url: '', method: 'GET' },
    /* 保存订单流程配置 */
    { key: 'saveOrderFlowConfig', url: '', method: 'PUT' },
    /* 订单流程配置明细 */
    { key: 'getOrderFlowConfigDetail', url: '/{id}', method: 'GET' },
    /* 流程发布 */
    { key: 'releaseFlow', url: '/publish/{id}', method: 'PUT' },
    /* 流程下架 */
    { key: 'undercarriageRelease', url: '/undercarriage/{id}', method: 'PUT' },
  ],
};

export default {
  ...generate(config),
};
