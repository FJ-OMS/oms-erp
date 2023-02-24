package org.skyer.order.infra.feign.entity.select.logistics.postage.out;

import lombok.Data;
import org.skyer.order.infra.feign.entity.select.logistics.postage.in.LogicWarehouseVO2;

import java.math.BigDecimal;

@Data
public class PackageSkuLogistics {

    /**
     * 分包资料
     */
    private PackageSku packageSku;

    /**
     * 物流公司
     */
    private String logisticCompany;

    /**
     * 物流公司编码
     */
    private String logisticCompanyCode;

    /**
     * 物流业务
     */
    private String logisticBusiness;

    /**
     * 邮费
     */
    private BigDecimal postageCharge;

    /**
     * 发货逻辑仓
     */
    private LogicWarehouseVO2 logicWarehouse;
}
