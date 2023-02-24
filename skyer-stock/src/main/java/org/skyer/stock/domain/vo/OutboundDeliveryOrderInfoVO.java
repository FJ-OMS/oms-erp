package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.stock.api.dto.CodeAndIdDTO;

/**
 * @author xurongfa 2022-02-22 03:11
 */
@Data
public class OutboundDeliveryOrderInfoVO implements Cacheable {
    @ApiModelProperty("主键")
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "内部订单号")
    private String orderCode;
    @ApiModelProperty(value = "内部订单ID")
    @Encrypt
    private Long orderId;
    @ApiModelProperty(value = "出库类型编码")
    @LovValue(lovCode = "STOCK.OUT_TYPE",meaningField = "outTypeMeaning")
    private String outType;
    @ApiModelProperty(value = "出库类型名称")
    private String outTypeMeaning;
    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;
    @ApiModelProperty(value = "收货人地址")
    private String receiverAddress;
    @ApiModelProperty(value = "收货人电话")
    private String receiverTel;
    @ApiModelProperty(value = "收货人手机")
    private String receiverMobile;
    @ApiModelProperty(value = "国家")
    private String receiverCountry;
    @ApiModelProperty(value = "省")
    private String receiverProvince;
    @ApiModelProperty(value = "市")
    private String receiverCity;
    @ApiModelProperty(value = "区")
    private String receiverDistrict;
    @ApiModelProperty(value = "买家昵称")
    private String sorBuyerNikeName;
    @ApiModelProperty(value = "计划发货时间")
    private Date scheduledDeliveryTime;
    @ApiModelProperty(value = "订单备注")
    private String orderMsg;
    @ApiModelProperty(value = "客户留言")
    private String buyMsg;
    @ApiModelProperty(value = "供货虚仓ID")
    private Long supportVirtualWarehouseId;
    @ApiModelProperty(value = "供货虚仓名称")
    private String supportVirtualWarehouseName;
    @ApiModelProperty(value = "供货虚仓编码")
    private String supportVirtualWarehouseCode;
    @ApiModelProperty(value = "收货逻辑仓ID")
    private Long receivingLogicWarehouseId;
    @ApiModelProperty(value = "收货逻辑仓名称")
    private String receivingLogicWarehouseName;
    @ApiModelProperty(value = "收货逻辑仓编码")
    private String receivingLogicWarehouseCode;
    @ApiModelProperty(value = "店铺ID")
    @Encrypt
    private Long shopId;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;
    @ApiModelProperty(value = "前置业务单号")
    private String frontRecordCode;
    @ApiModelProperty(value = "包裹编码")
    private String packageCode;
    @ApiModelProperty(value = "三方平台订单号")
    private String platformOrderCode;

    @ApiModelProperty(value = "收发货结果单编号和id集合")
    private List<CodeAndIdDTO> resultCodeAndIdList;

    @ApiModelProperty(value = "状态 0待通知WMS 1待出库 2通知WMS失败 3全部出库 4部分出库 5取消出库")
    private Integer status;

    @ApiModelProperty(value = "发货逻辑仓ID")
    private Long outLogicWarehouseId;
    @ApiModelProperty(value = "发货逻辑仓名称")
    private String outLogicWarehouseName;
    @ApiModelProperty(value = "发货逻辑仓编码")
    private String outLogicWarehouseCode;
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

    @ApiModelProperty(value = "创建时间")
    private Date creationDate;

    @ApiModelProperty(value = "创建人ID")
    @Encrypt
    private Long createdBy;

    @ApiModelProperty(value = "创建人名称")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "createdBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String createdName;

}
