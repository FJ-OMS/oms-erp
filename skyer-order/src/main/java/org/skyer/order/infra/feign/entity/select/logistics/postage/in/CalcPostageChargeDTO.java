package org.skyer.order.infra.feign.entity.select.logistics.postage.in;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenzz
 */
@Data
public class CalcPostageChargeDTO {

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 收货地址
     */
    private Address receiveAddress;

    /**
     * 逻辑仓列表
     */
    private List<LogicWarehouseVO2> logicWarehouseVO2s;

    /**
     * 分包信息
     */
    private List<PackageSku> packageSkus;
}
