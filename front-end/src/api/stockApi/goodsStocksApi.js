/* jshint esversion: 9 */
/**
 * Goods Stock Manage : 库存管理
 *
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '/stock/v1/goods-stocks',
  items: [
    // 查看仓库库存
    { key: 'getGoodsStockList', url: '/stock-list', method: 'POST' },
    // 分页  仓库库存表列表
    { key: 'getGoodsStockPage', url: '/page', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
