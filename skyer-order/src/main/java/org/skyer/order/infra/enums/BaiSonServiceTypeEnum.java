package org.skyer.order.infra.enums;

/**
 * 百胜接口请求地址枚举
 */
public enum BaiSonServiceTypeEnum {
    //获取平台商品列表
    LIST_TYPE(1, "outer.sku.list.get.ext"),
    //平台商品上下架
    DROP_OFF_TYPE(2, "outer.sku.change.ext"),
    //库存同步
    STOCK_SYNC_TYPE(3, "syncKc"),
    //获取订单列表
    LIST_ORDER(4, "order.list.get.ext"),
    //敏感信息解密
    DECRYPT(5, "decrypt.get.ext"),
    //修改商家备注
    SELLER_MEMO(6, "update.seller.memo"),
    //获取快递列表
    LIST_SHIPPING(7, "shipping.list.get"),
    //销售订单发货出库
    UPDATE_SALES_ORDER(8, "order.fhck.ext"),
    //商品档案添加
    PRODUCTS_ADD(9, "products.add"),
    //sku商品添加
    SKUS_ADD(10, "skus.add"),
    //条码添加
    BARCODES_ADD(17, "barcodes.add"),
    //置无效可退款
    INVALIDATE(11, "order_zwx_ext"),
    //退款换货
    REFUND_REPLACEMENT(12, "return.list.get.ext"),
    //更新销售订单入库状态
    UPDATE_SALES_REFUND(13, "UpdateSalesRefund"),
    //获取待转入退单
    REFUSED_LIST_GET_ALL(14, "refused.list.get.all"),
    //百胜审核退款单
    JD_REPLY_REFUND(15, "jd.refundapply.replyrefund"),
    //京东获取优惠明细
    COUPON_DETAIL(16, "jd.order.coupondetail"),
    //获取售后赔付列表
    QUERY_COMPENSATE_LIST(18, "jd.compensate.queryCompensateList"),
    //获取店铺来源类型
    STORE_SOURCE_TYPE(19, "get.sd.lylx"),

    //淘宝 OAID解密
    OAID_DECRYPT(21, "top.oaid.decrypt"),

    //通过订单号去获取物流流转
    WMS_CNC_TRANCE_SEARCH(23, "taobao.logistics.trace.search"),


    ORDER_DOWN_BAI_SON(40, "download.trade.to.ec"),

    CANCEL(30, "write.autorefund.cancle.delivery"),
    //单点登录
    SIGN_IN_SSO_LOGIN(50, "sso.login"),
    DESTORY_API_SUCCESS(60, "api-success");

    private Integer type;
    private String typeName;

    BaiSonServiceTypeEnum(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 根据type取对象
     *
     * @param type
     * @return
     */
    public static BaiSonServiceTypeEnum getTypeEnum(Integer type) {
        for (BaiSonServiceTypeEnum s : BaiSonServiceTypeEnum.values()) {
            if (type.equals(s.getType())) {
                return s;
            }
        }
        return null;
    }
}
