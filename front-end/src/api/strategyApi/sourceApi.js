/*
 * @Author: your name
 * @Date: 2021-10-28 20:58:39
 * @LastEditTime: 2021-11-17 17:27:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\strategyApi\marketingAuditApi.js
 */
/**
 * Postage Template : 寻源策略
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: 'sord/v1',
  items: [
    /** 删除单品发包设置 */
    { key: 'deletePackageSettingSingleGoods', url: '/package-setting-single-goods', method: 'DELETE' },
    /** 单品发包列表 */
    { key: 'getPackageSettingSingleGoods', url: '/package-setting-single-goods', method: 'GET' },
    /** 创建单品发包设置 */
    { key: 'addPackageSettingSingleGoods', url: '/package-setting-single-goods', method: 'POST' },
    /** 修改单品发包设置 */
    { key: '', url: '/package-setting-single-goods', method: 'PUT' },
    // /** 单品发包设置明细 */
    { key: '', url: '/package-setting-single-goods/{id}', method: 'GET' },
  ],
};
export default {
  ...generate(config),
};
