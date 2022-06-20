/*
 * @Author: your name
 * @Date: 2021-08-06 13:46:48
 * @LastEditTime: 2021-10-19 13:46:07
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\channelApi\shopManageApi.js
 */
/**
 * Physical Shop Manage : 门店管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/schl/v1',
  items: [
    /**
     * 查询列表
     */
    { key: 'getAllShopPage', url: '/shop/page', method: 'POST' },
  ],
};

export default {
  ...generate(config),
};
