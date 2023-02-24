package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.stock.api.dto.CodeAndIdDTO;

/**
 * @author xurongfa 2022-02-21 16:26
 */
@Data
public class OutboundDeliveryOrderListVO {
    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "出库单编号")
    private String code;

    @ApiModelProperty(value = "结果单号", hidden = true)
    private String resultCode;
    @ApiModelProperty(value = "收发货结果单编号和id集合")
    private List<CodeAndIdDTO> resultCodeAndIdList;

    @ApiModelProperty(value = "第三方平台订单号")
    private String platformOrderCode;

    @ApiModelProperty(value = "内部订单id")
    @Encrypt
    private Long orderId;
    @ApiModelProperty("内部订单号")
    private String orderCode;

    @ApiModelProperty(value = "前置业务单id")
    @Encrypt
    private Long frontRecordId;
    @ApiModelProperty(value = "前置业务单号")
    private String frontRecordCode;
    @ApiModelProperty(value = "前置业务单类型")
    private String frontRecordType;

    @ApiModelProperty(value = "出库类型编码")
    @LovValue(lovCode = "STOCK.OUT_TYPE",meaningField = "outTypeMeaning")
    private String outType;
    @ApiModelProperty(value = "出库类型名称")
    private String outTypeMeaning;

    @ApiModelProperty(value = "状态 0待通知WMS 1待出库 2通知WMS失败 3全部出库 4部分出库 5取消出库")
    private Integer status;

    @ApiModelProperty("商品数量")
    private BigDecimal qty;
    @ApiModelProperty("已出库数量")
    private BigDecimal alreadyQty;

    @ApiModelProperty(value = "发货逻辑仓id")
    @Encrypt
    private Long outLogicWarehouseId;
    @ApiModelProperty(value = "发货逻辑仓编码")
    private String outLogicWarehouseCode;
    @ApiModelProperty(value = "发货逻辑仓名称")
    private String outLogicWarehouseName;

    @ApiModelProperty(value = "店铺id")
    @Encrypt
    private Long shopId;
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;
    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty(value = "渠道类型")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE",meaningField = "channelTypeCodeMeaning")
    private String channelTypeCode;
    @ApiModelProperty(value = "渠道名称")
    private String channelTypeCodeMeaning;

    @ApiModelProperty(value = "创建时间")
    private Date creationDate;

    @ApiModelProperty(value = "物流商")
    private String logisticCompany;
    @ApiModelProperty(value = "物流商编号")
    private String logisticCompanyCode;
    @ApiModelProperty(value = "物流商ID")
    @Encrypt
    private Long logisticCompanyId;
    @ApiModelProperty(value = "物流业务")
    private String logisticBusiness;
    @ApiModelProperty(value = "物流业务编码")
    private String logisticBusinessCode;
    @ApiModelProperty(value = "物流业务ID")
    @Encrypt
    private Long logisticBusinessId;

}
