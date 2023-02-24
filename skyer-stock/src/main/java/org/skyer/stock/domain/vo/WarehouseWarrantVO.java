package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author xurongfa 2022-04-25 15:22
 */
@Data
public class WarehouseWarrantVO {
    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "入库单编号")
    private String code;

    @ApiModelProperty(value = "三方订单号")
    private String platformOrderCode;

    @ApiModelProperty(value = "内部订单号")
    private String orderCode;

    @ApiModelProperty(value = "前置业务单号类型")
    private String frontRecordType;
    @ApiModelProperty(value = "前置业务id")
    @Encrypt
    private Long frontRecordId;
    @ApiModelProperty(value = "前置业务单号")
    private String frontRecordCode;

    @ApiModelProperty(value = "入库类型编码 STOCK.INPUT_TYPE")
    @LovValue(lovCode = "STOCK.INPUT_TYPE",meaningField = "inputTypeMeaning")
    private String inputType;
    @ApiModelProperty(value = "入库类型名称")
    private String inputTypeMeaning;

    @ApiModelProperty(value = "状态 STOCK.INPUT_STATUS")
    @LovValue(lovCode = "STOCK.INPUT_STATUS",meaningField = "statusMeaning")
    private String status;
    @ApiModelProperty(value = "状态")
    private String statusMeaning;

    @ApiModelProperty("需入库数量,sku级别")
    private BigDecimal qty;

    @ApiModelProperty(value = "收货逻辑仓名称")
    private String receivingLogicWarehouseName;

    @ApiModelProperty(value = "收货逻辑仓编码")
    private String receivingLogicWarehouseCode;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty(value = "渠道类型")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE",meaningField = "channelTypeCodeMeaning")
    private String channelTypeCode;
    @ApiModelProperty(value = "渠道名称")
    private String channelTypeCodeMeaning;

    @ApiModelProperty(value = "创建时间")
    private Date creationDate;
}
