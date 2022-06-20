/**
 * : 商品审核
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1',
  items: [
    /**
     * 获取商品审核
     */
    { key: 'getSkuAudit', url: '/sku-audit-settings/default', method: 'GET' },
    /**
     * 修改审核
     */
    { key: 'updateSkuAudit', url: '/sku-audit-settings/update', method: 'PUT' },
    /**
     * 获取sku审核列表
     */
    { key: 'getSkuAuditList', url: '/goods/sku/page', method: 'POST' },
    /**
     * 获取sku审核详情
     */
    { key: 'getSkuAuditDetail', url: '/sku/audit/detail', method: 'POST' },
    /**
     * sku审核
     */
    { key: 'skuAudit', url: '/sku/audit', method: 'POST', loadingMore: 100 },
  ],
};

export default {
  ...generate(config),
};
