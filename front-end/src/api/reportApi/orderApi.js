import { generate } from '@/utils/request';
const config = {
  prefix: '/sord/v1',
  items: [
    // 订单销售统计表
    { key: 'getOrderSaleReport', url: '/order-report/order-sale-report', method: 'POST' },
    // 订单销售分析表（订单每日统计表）
    { key: 'getOrderDailyReport', url: '/order-report/order-daily-report', method: 'POST' },
    // 渠道销售统计表
    { key: 'getChannelSaleReport', url: '/order-report/channel-sale-report', method: 'POST' },
    // 订单商品统计表
    { key: 'getOrderItemReport', url: '/order-report/order-item-report', method: 'POST' },
  ],
};
export default {
  ...generate(config),
};
