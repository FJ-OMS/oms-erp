package org.skyer.order.infra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @Author wusc
 * @create 2022/2/10 1:12 下午
 */
@AllArgsConstructor
@Getter
public enum PriceTypeEnum {
    /**
     * 商品总金额
     */
    GOODS_TOTAL_PRICE("1","商品总金额"),
    /**
     * 成本总金额
     */
    COST_TOTAL_PRICE("2","成本总金额"),
    /**
     * 买家实付金额
     */
    BUYER_REAL_PRICE("3","买家实付金额"),
    /**
     * 买家应付金额
     */
    BUYER_NEED_PRICE("4","买家应付金额"),

    /**
     * 外部销售金额
     */
    OUT_SALE_PRICE("5","外部销售金额"),

    /**
     * 平台平摊价
     */
    OUT_BALANCE_PRICE("6","平台平摊价"),

    /**
     * 内部销售金额
     */
    INNER_SALE_PRICE("7","内部销售金额"),

    /**
     * 内部平摊价
     */
    INNER_BALANCE_PRICE("8","内部平摊价"),

    /**
     * 运费
     */
    TRANS_PRICE("9","运费");


    private String type;

    private String typeName;
}
