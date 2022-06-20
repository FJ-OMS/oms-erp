package org.skyer.order.infra.feign.entity.generate.odo.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 出库单
 *
 * @author lixiaoyang 2021-10-15 15:24:26
 */
@ApiModel("出库单")
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OutboundDeliveryOrder extends AuditDomain {


    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "订单编号")
    private String orderCode;
    @ApiModelProperty(value = "出库类型")
    private String outType;
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
    @ApiModelProperty(value = "发货实仓ID")
    private Long outRealWarehouseId;
    @ApiModelProperty(value = "发货实仓名称")
    private String outRealWarehouseName;
    @ApiModelProperty(value = "发货实仓编码")
    private String outRealWarehouseCode;
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
    @ApiModelProperty(value = "店铺id")
    private Long storeId;
    @ApiModelProperty(value = "销售店名")
    private String salesShopName;
    @ApiModelProperty(value = "前置业务单号")
    private String frontRecordCode;
    @ApiModelProperty(value = "平台订单号")
    private String platformOrderCode;
    @ApiModelProperty(value = "收发货结果单编号(多条结果单按照,隔开)")
    private String resultCode;
    @ApiModelProperty(value = "状态 0未出库 1部分出库 2全部出库 3出库失败")
    private Integer status;
    @ApiModelProperty(value = "出库失败附加状态 0退换货时 1中止发货时")
    private Integer failureStatus;

}
