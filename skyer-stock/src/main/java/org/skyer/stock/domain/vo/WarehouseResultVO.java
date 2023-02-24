package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author xurongfa 2022-04-26 14:39
 */
@Data
public class WarehouseResultVO {
    @ApiModelProperty("主键")
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "入库结果单号")
    private String code;
    @ApiModelProperty(value = "入库通知单号")
    private String warehouseWarrantCode;

    @ApiModelProperty(value = "入库类型编码")
    @LovValue(lovCode = "STOCK.INPUT_TYPE",meaningField = "inputTypeMeaning")
    private String inputType;
    @ApiModelProperty(value = "入库类型名称")
    private String inputTypeMeaning;

    @ApiModelProperty(value = "状态 STOCK.INPUT_STATUS")
    @LovValue(lovCode = "STOCK.INPUT_RESULT_STATUS",meaningField = "statusMeaning")
    private String status;
    @ApiModelProperty(value = "状态 STOCK.INPUT_STATUS")
    private String statusMeaning;

    @ApiModelProperty(value = "收货逻辑仓名称")
    private String receivingLogicWarehouseName;
    @ApiModelProperty(value = "收货逻辑仓编码")
    private String receivingLogicWarehouseCode;
    @ApiModelProperty(value = "店铺名")
    private String storeName;
    @ApiModelProperty(value = "渠道类型")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE",meaningField = "channelTypeCodeMeaning")
    private String channelTypeCode;
    @ApiModelProperty(value = "渠道名称")
    private String channelTypeCodeMeaning;

    @ApiModelProperty(value = "已入库数量")
    private BigDecimal alreadyQty;

    @ApiModelProperty(value = "创建时间")
    private Date creationDate;

}
