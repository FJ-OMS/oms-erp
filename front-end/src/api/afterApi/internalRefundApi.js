/*
 * @Author: your name
 * @Date: 2021-11-03 17:08:17
 * @LastEditTime: 2022-02-24 13:28:45
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: 内部售后订单
 * @FilePath: \newBj-admin\src\api\orderApi\audit.js
 */
import { generate } from '@/utils/request';

const config = {
  prefix: '/sord/v1',
  items: [
    // 创建退货退款单
    {
      key: 'addRefundGoodsOrder',
      url: '/after-sale-headers/create-refund-goods',
      method: 'POST',
      loadingMore: 999,
    },
    // 创建仅退款单
    {
      key: 'addRefundOnlyOrder',
      url: '/after-sale-headers/create-refund-only',
      method: 'POST',
      loadingMore: 999,
    },
    // 退货退款单审核
    { key: 'auditRefundOrder', url: '/after-sale-headers/audit-refund', method: 'PUT' },
    // 退货退款单修改/打标
    { key: 'updateRefundOrder', url: '/after-sale-headers/refund-goods-edit', method: 'PUT' },
    // 退款单详情
    {
      key: 'detailRefundOrder',
      url: '/after-sale-headers/refund-goods-detail/{orderId}',
      method: 'GET',
    },
    // 内部售后单详情订单基本信息通过code和Id
    {
      key: 'detailRefundOrderByIdCode',
      url: '/after-sale-headers/order-base-detail-by-id-code',
      method: 'POST',
    },
    // 退货退款单列表
    { key: 'pageRefundGoodsOrder', url: '/after-sale-headers/refund-goods-page', method: 'POST' },
    // 仅退款单列表
    { key: 'pageRefundOnlyOrder', url: '/after-sale-headers/refund-only-page', method: 'POST' },

    // 详情 新增加退货退款商品
    {
      key: 'addSkuToRefundGoodsOrder',
      url: '/after-sale-headers/add-refund-goods',
      method: 'POST',
    },
    // 详情 新增加仅退款商品
    { key: 'addSkuToRefundOnlyOrder', url: '/after-sale-headers/add-refund-only', method: 'POST' },

    // 根据innerNo查询可退款/退货商品

    {
      key: 'getRefundPageByInnerNo',
      url: '/after-sale-items/refund-page-by-innerNo',
      method: 'POST',
    },

    // 查询可退货商品行 itemId
    {
      key: 'getRefundGoodsItemListByItemId',
      url: '/after-sale-item-lines/refund-goods-item-list/',
      method: 'GET',
      loadingHide: true,
    },
    // 查询可退款商品行 itemId
    {
      key: 'getRefundOnlyItemListByItemId',
      url: '/after-sale-item-lines/refund-only-item-list/',
      method: 'GET',
      loadingHide: true,
    },
    // 详情页 查询商品行 itemId
    {
      key: 'getRefundDetailItemListByItemId',
      url: '/after-sale-item-lines/item-line-details',
      method: 'POST',
      loadingHide: true,
    },
    // 详情里的退货物流LIST
    {
      key: 'getAfterSaleLogistics',
      url: '/after-sale-headers/refund-logistics/{orderId}',
      method: 'GET',
    },

    // 详情页 删除 商品行 itemId
    {
      key: 'deleteRefundDetailItemListByItemId',
      url: '/after-sale-headers/refund-delete-item',
      method: 'DELETE',
    },
    // 退货退款单审核
    {
      key: 'auditRefundOrder',
      url: '/after-sale-headers/audit-refund',
      method: 'PUT',
    },
    // 批量 退货退款单审核
    {
      key: 'batchAuditRefundOrder',
      url: '/after-sale-headers/batch-audit-refund',
      method: 'PUT',
      loadingMore: 999,
    },

    // 退货退款单审核列表
    {
      key: 'getAuditRefundOrderPage',
      url: '/after-sale-headers/refund-audit-page',
      method: 'POST',
    },
    // 退货退款单 已全部入库
    {
      key: 'confirmRefundOrderWare',
      url: '/after-sale-headers/confirm-ware/{orderId}',
      method: 'PATCH',
    },
    // 退货退款单 修改退货仓库

    {
      key: 'updateRefundOrderWarehouse',
      url: '/after-sale-headers/update-warehouse',
      method: 'PUT',
    },
  ],
};

export default {
  ...generate(config),
};
