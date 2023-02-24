import { goodsApi, channelApi, stockApi, orderApi, afterApi } from '@/api';
import { GOODS_TYPES } from '@/utils/constants';
import { GOODS_COMBO_TYPES } from '@/utils/constants';
// spu
export function spuVirtual(title = '选择虚拟商品', option) {
  return spuDefault(title, {
    defaultQuery: { type: GOODS_TYPES.VIRTUAL, status: 1 },
  });
}
export function spuReal(title = '选择实物商品', option) {
  return spuDefault(title, {
    defaultQuery: { type: GOODS_TYPES.REAL, status: 1 },
  });
}
export function spuPackage(title = '选择套餐商品', option) {
  return spuDefault(title, {
    defaultQuery: { setFlag: GOODS_COMBO_TYPES.PACKAGE, status: 1 },
  });
}
export function spuOrdinary(title = '选择普通商品', option) {
  return spuDefault(title, {
    defaultQuery: { setFlag: GOODS_COMBO_TYPES.ORDINARY, status: 1 },
  });
}
export function spuDefault(title = '选择商品', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'spuCode',
        label: '商品编码',
        width: 160,
      },
      {
        prop: 'name',
        label: '商品名称',
        width: 160,
      },
      {
        prop: 'categoryName',
        label: '商品分类',
      },
    ],
    search: option.search || [
      {
        placeholder: '请输入编码',
        key: 'spuCode',
      },
      {
        placeholder: '请输入名称',
        key: 'name',
      },
      {
        placeholder: '请选择分类',
        key: 'categoryIdList',
        component: 'BjGoodsTypeTreeSelect',
        multiple: true,
      },
    ],
    actionApi: option.actionApi || goodsApi.getPage,
    defaultQuery: option.defaultQuery || { status: 1 },
    query: option.query || '',
  };
}
// sku
export function skuReal(title = '选择实物商品', option) {
  return skuDefault(title, {
    defaultQuery: { spuType: GOODS_TYPES.REAL, status: [3, 4, 5] },
  });
}
export function skuDefault(title = '选择SKU', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'categoryName',
        label: '商品分类',
      },
      {
        prop: 'spuCode',
        label: '商品编码',
        width: 160,
      },
      {
        prop: 'spuName',
        label: '商品名称',
        width: 160,
      },
      {
        prop: 'skuCode',
        label: 'SKU编码',
        width: 160,
      },
      {
        prop: 'skuName',
        label: 'SKU名称',
        width: 160,
      },
      {
        prop: 'spuType',
        label: '商品类型',
        constantKey: 'GOODS_TYPE_LIST',
      },
    ],
    search: option.search || [
      {
        placeholder: '请输入商品名称',
        key: 'spuName',
      },
      {
        placeholder: '请输入商品编码',
        key: 'spuCode',
      },
      {
        placeholder: '请输入SKU编码',
        key: 'skuCode',
      },
    ],
    actionApi: option.actionApi || goodsApi.getSku,
    defaultQuery: option.defaultQuery || { status: [3, 4, 5] },
    query: option.query || '',
  };
}
// shop
export function shopDefault(title = '选择店铺', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'code',
        label: '店铺编码',
      },
      {
        prop: 'name',
        label: '店铺名称',
      },
      {
        prop: 'categoryType',
        label: '店铺类型',
        constantKey: 'SHOP_TYPE_LIST',
      },
    ],
    search: option.search || [
      {
        placeholder: '请输入编码',
        key: 'code',
      },
      {
        placeholder: '请输入名称',
        key: 'name',
      },
    ],
    actionApi: option.actionApi || channelApi.getAllShopPage,
    defaultQuery: option.defaultQuery || {},
    query: option.query || '',
  };
}
// shopOnline
export function shopOnline(title = '选择网店', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'code',
        label: '编码',
      },
      {
        prop: 'name',
        label: '名称',
      },
      {
        prop: 'channelTypeMeaning',
        label: '销售渠道',
      },
      {
        prop: 'onlineStoreTypeMeaning',
        label: '类型',
      },
    ],
    search: option.search || [
      {
        placeholder: '请输入网店编码',
        key: 'code',
      },
      {
        placeholder: '请输入网店名称',
        key: 'name',
      },

      {
        placeholder: '请选择渠道',
        key: 'channelTypeCode',
        component: 'BjSelectLov',
        lovCode: 'SCHL.CHANNEL_TYPE',
      },
    ],
    actionApi: option.actionApi || channelApi.getOnlineShopPage,
    defaultQuery: option.defaultQuery || { status: true },
    query: option.query || '',
  };
}
// channelApi.getWarehousePage
export function warehouseDefault(title = '选择仓库', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'code',
        label: '仓库编码',
      },
      {
        prop: 'name',
        label: '仓库名称',
      },
      {
        prop: 'detailAddress',
        label: '仓库地址',
      },
    ],
    search: option.search || [
      {
        placeholder: '仓库编码',
        key: 'code',
      },
      {
        placeholder: '仓库名称',
        key: 'name',
      },
    ],
    actionApi: option.actionApi || channelApi.getWarehousePage,
    //         // defaultQuery: { typeCode: ['real'] },
    defaultQuery: option.defaultQuery || { enableFlag: true },
    query: option.query || '',
  };
}
// stockApi.getWarehousesPage
export function stockWarehouseLogic(time, option) {
  return stockWarehouseDefault('选择逻辑仓', {
    defaultQuery: { typeCode: ['logic'] },
  });
}
export function stockWarehouseReal(time, option) {
  return stockWarehouseDefault('选择实仓', {
    defaultQuery: { typeCode: ['real'] },
  });
}
export function stockWarehouseDefault(title = '选择仓库', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'code',
        label: '仓库编码',
      },
      {
        prop: 'name',
        label: '仓库名称',
      },
    ],
    search: option.search || [
      {
        placeholder: '仓库编码',
        key: 'code',
      },
      {
        placeholder: '仓库名称',
        key: 'name',
      },
    ],
    actionApi: option.actionApi || stockApi.getWarehousesPage,
    defaultQuery: option.defaultQuery || {},
    query: option.query || '',
  };
}
// stockGoods
export function stockGoodsDefault(title = '选择商品', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'spuCode',
        label: '商品编码',
        width: 160,
      },
      {
        prop: 'spuName',
        label: '商品名称',
        width: 160,
      },
      {
        prop: 'skuCode',
        label: 'SKU编码',
        width: 160,
      },
      {
        prop: 'categoryName',
        label: '商品分类',
      },
      {
        prop: 'spuType',
        label: '商品类型',
        constantKey: 'GOODS_TYPE_LIST',
      },
      {
        prop: 'usableNum',
        label: '可用库存 ',
      },
    ],
    search: option.search || [
      {
        placeholder: '请输入商品名称',
        key: 'spuName',
      },
      {
        placeholder: '请输入商品编码',
        key: 'spuCode',
      },
      {
        placeholder: '请输入SKU编码',
        key: 'skuCode',
      },
    ],
    actionApi: option.actionApi || stockApi.getGoodsStockPage,
    // usableExcludeZeroFlag (boolean, optional): 可用库存是否大于零
    // warehouseId (Array[integer], optional): 仓库名称
    defaultQuery: option.defaultQuery || { warehouseId: [], usableExcludeZeroFlag: true },
    query: option.query || '',
  };
}

/** 内部销售单 */
export function innerOrderDefault(title = '选择关联销售单', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'innerOrderNo',
        label: '内部订单号',
        width: 160,
      },
      {
        prop: 'outerOrderNo',
        label: '三方订单号',
        width: 160,
      },
      {
        prop: 'businessType',
        label: '订单类型',
        constantKey: 'ORDER_BUSINESS_TYPE_LIST',
      },
      {
        prop: 'orderStatus',
        label: '订单状态',
        constantKey: 'ORDER_INTERNAL_ORDER_STATUS_LIST',
      },
      {
        prop: 'storeName',
        label: '销售店铺',
      },
      {
        prop: 'channelTypeMeaning',
        label: '销售渠道',
      },
      {
        prop: 'payStatus',
        label: '支付状态',
        constantKey: 'ORDER_INTERNAL_PAYMENT_STATUS_LIST',
      },
      {
        prop: 'goodsQuatity',
        label: '商品行数量',
      },
      {
        prop: 'totalAmount',
        label: '商品总金额',
      },
      {
        prop: 'buyerPay',
        label: '买家应付',
      },
      {
        prop: 'costPrice',
        label: '商品总成本',
      },
      {
        prop: 'buyerRealPrice',
        label: '买家实付',
      },
      {
        prop: 'deliveryStatus',
        label: '发货状态',
        constantKey: 'ORDER_INTERNAL_DELIVER_STATUS_LIST',
        width: 100,
      },
      {
        prop: 'receivingStatus',
        label: '收货状态',
        constantKey: 'ORDER_INTERNAL_RECEIVING_STATUS_LIST',
        width: 100,
      },
      {
        prop: 'buyerNick',
        label: '买家昵称',
      },
      {
        prop: 'receiverName',
        label: '收货人姓名',
      },
      {
        prop: 'mobile',
        label: '手机号',
        width: 100,
      },
      {
        prop: 'address',
        label: '收货地址',
      },
      {
        prop: 'orderTime',
        label: '三方下单时间',
        width: 135,
      },
      {
        prop: 'payTime',
        label: '支付时间',
        width: 135,
      },
      {
        prop: 'creationDate',
        label: '创建时间',
        width: 135,
      },
    ],
    search: option.search || [
      {
        placeholder: '三方订单号/内部订单号',
        key: 'orderNo',
      },
    ],
    showChannelStore: ['channel', 'storeIdList'],
    actionApi: option.actionApi || orderApi.getInternalPage,
    defaultQuery: option.defaultQuery || {},
    query: option.query || '',
  };
}
/** 退款商品 */
export function refundGoodsDefault(title = '选择退款商品', option = {}) {
  const list = title.includes('退货')
    ? [
        // {
        //   prop: 'quantityShipped',
        //   label: '已发货数量',
        // },
        // {
        //   prop: 'quantityReturned',
        //   label: '已退货数量',
        // },
        // {
        //   prop: 'returnableQuantity',
        //   label: '可退货数量',
        // },
      ]
    : [
        // {
        //   prop: 'undeliveredQuantity',
        //   label: '未发货数量',
        // },
        // {
        //   prop: 'quantityRefunded',
        //   label: '已退款数量',
        // },
        // {
        //   prop: 'refundableQuantity',
        //   label: '可退款数量',
        // },
      ];

  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'spuCode',
        label: '商品编码',
        width: 160,
      },
      {
        prop: 'spuName',
        label: '商品名称',
        width: 160,
      },
      {
        prop: 'skuCode',
        label: 'SKU编码',
        width: 160,
      },
      {
        prop: 'skuName',
        label: 'SKU名称',
        width: 160,
      },
      {
        prop: 'giftFlag',
        label: '是否赠品',
        constantKey: 'STATUS_YES_NUM',
        width: 80,
      },
      {
        prop: 'totalQuantity',
        label: '商品行数量',
        width: 80,
      },
      ...list,
    ],
    search: option.search || [],
    actionApi: option.actionApi || afterApi.getRefundPageByInnerNo,
    defaultQuery: option.defaultQuery || { innerOrderNo: null },
    query: option.query || '',
  };
}

/** 三方销售单 */
export function thirdOrderDefault(title = '选择关联三方订单', option = {}) {
  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'outerOrderNo',
        label: '三方订单号',
        width: 160,
      },
      {
        prop: 'innerOrderNo',
        label: '内部订单号',
        width: 160,
      },
      {
        prop: 'businessType',
        label: '订单类型',
        constantKey: 'ORDER_BUSINESS_TYPE_LIST',
      },
      {
        prop: 'storeName',
        label: '销售店铺',
        width: 160,
      },
      {
        prop: 'channelTypeMeaning',
        label: '销售渠道',
      },
      {
        prop: 'circulationStatus',
        label: '流转状态',
        constantKey: 'AFTER_THIRD_REFUND_TRANSFER_STATUS',
      },
      {
        prop: 'syncStatus',
        label: '回写状态',
        constantKey: 'ORDER_BACK_RES_LIST',
      },
      {
        prop: 'receivingStatus',
        label: '平台收货状态',
        constantKey: 'ORDER_THIRD_RECEIVING_STATUS_LIST',
      },
      // {
      //   prop: 'buyerRemarks',
      //   label: '买家留言',
      // },
      // {
      //   prop: 'serviceRemarks',
      //   label: '客服备注',
      // },
      {
        prop: 'goodsQuantity',
        label: '商品数',
      },
      {
        prop: 'goodsTotalAmount',
        label: '商品总金额',
      },
      {
        prop: 'totalDiscount',
        label: '优惠合计',
      },
      {
        prop: 'amountReceivable',
        label: '应收总金额',
      },
      {
        prop: 'buyerPayAmount',
        label: '买家实付',
      },
      {
        prop: 'businessDiscountAmount',
        label: '商家承担优惠金额',
      },
      {
        prop: 'thirdDiscountAmount',
        label: '三方承担优惠金额',
      },
      {
        prop: 'buyerNick',
        label: '买家昵称',
      },
      {
        prop: 'receiverName',
        label: '收货人姓名',
      },

      {
        prop: 'mobile',
        label: '手机号',
        width: 100,
      },
      // {
      //   prop: 'address',
      //   label: '收货地址',
      // },
      {
        prop: 'orderTime',
        label: '三方下单时间',
        width: 135,
      },
      {
        prop: 'payTime',
        label: '支付时间',
        width: 135,
      },
      {
        prop: 'creationDate',
        label: '拉单时间',
        width: 135,
      },
    ],
    search: option.search || [
      {
        placeholder: '三方订单号/内部订单号',
        key: 'orderNo',
      },
    ],
    showChannelStore: ['channel', 'storeIdList'],
    actionApi: option.actionApi || orderApi.getThirdOrderPage,
    defaultQuery: option.defaultQuery || {},
    query: option.query || '',
  };
}
/** 三方退款商品： 三方销售单详情的thirdItemVO字段 */
export function thirdRefundGoodsDefault(title = '选择退款商品', option = {}) {
  const list = title.includes('退货') ? [] : [];

  // outerSaleAmount: 199
  // outerSingleSaleAmount: 199
  // quantity: 1

  return {
    title: title,
    columns: option.columns || [
      {
        prop: 'spuCode',
        label: '商品编码',
        width: 160,
      },
      {
        prop: 'spuName',
        label: '商品名称',
        width: 160,
      },
      {
        prop: 'skuCode',
        label: 'SKU编码',
        width: 160,
      },
      {
        prop: 'skuName',
        label: 'SKU名称',
      },
      {
        prop: 'quantity',
        label: '商品行数量',
      },
      ...list,
    ],
    search: option.search || [],
    actionApi: option.actionApi || afterApi.getRefundPageByInnerNo,
    defaultQuery: option.defaultQuery || { innerOrderNo: null },
    query: option.query || '',
  };
}
