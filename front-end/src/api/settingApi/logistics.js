/*
 * @Author: your name
 * @Date: 2021-09-26 17:56:19
 * @LastEditTime: 2021-09-29 18:00:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\settingApi\logistics.js
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/lgit',
  items: [
    { key: 'updateStrategyLogistics', url: '/v1/logistics-rule-setting', method: 'PUT' },
    { key: 'getStrategyLogistics', url: '/v1/logistics-rule-setting/detail', method: 'GET' },
    /** 订单金额 */
    { key: 'getStrategyOrderAmounts', url: '/v1/strategy-order-amounts', method: 'GET' },
    { key: 'addStrategyOrderAmounts', url: '/v1/strategy-order-amounts', method: 'POST' },
    { key: 'deleteStrategyOrderAmounts', url: '/v1/strategy-order-amounts', method: 'DELETE' },
    { key: 'updateStrategyOrderAmounts', url: '/v1/strategy-order-amounts', method: 'PUT' },
    /** 指定商品 */
    { key: 'getStrategyGoods', url: '/v1/strategy-goods', method: 'GET' },
    { key: 'addStrategyGoods', url: '/v1/strategy-goods', method: 'POST' },
    { key: 'updateStrategyGoods', url: '/v1/strategy-goods', method: 'PUT' },
    { key: 'deleteStrategyGoods', url: '/v1/strategy-goods', method: 'DELETE' },
    /** 指定省市区 */
    { key: 'getStrategyZones', url: '/v1/strategy-zones', method: 'GET' },
    { key: 'addStrategyZones', url: '/v1/strategy-zones', method: 'POST' },
    { key: 'updateStrategyZones', url: '/v1/strategy-zones', method: 'PUT' },
    { key: 'deleteStrategyZones', url: '/v1/strategy-zones', method: 'DELETE' },
    { key: 'getStrategyZonesDetail', url: '/v1/strategy-zones/{id}', method: 'GET' },
    { key: 'getStrategyZonesHasSetArea', url: '/v1/strategy-zones/hasSetArea', method: 'GET' },
    /** 指定仓库 */
    { key: 'getStrategyStoreHouses', url: '/v1/strategy-storehouses', method: 'GET' },
    { key: 'addStrategyStoreHouses', url: '/v1/strategy-storehouses', method: 'POST' },
    { key: 'updateStrategyStoreHouses', url: '/v1/strategy-storehouses', method: 'PUT' },
    { key: 'deleteStrategyStoreHouses', url: '/v1/strategy-storehouses', method: 'DELETE' },
  ],
};

export default {
  ...generate(config),
};
