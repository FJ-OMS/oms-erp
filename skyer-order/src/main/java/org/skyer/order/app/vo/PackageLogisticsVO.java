package org.skyer.order.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PackageLogisticsVO {

    // @ApiModelProperty("id主键")
    // @Id
    // @GeneratedValue
    // private Long id;
    //
    // @ApiModelProperty(value = "第三方订单号")
    // private String orderSn;
    //
    // @ApiModelProperty(value = "包裹内部编码")
    // private String packageSn;

    @ApiModelProperty(value = "物流订单号")
    private String logisticsOrderNo;

    @ApiModelProperty(value = "逻辑仓库")
    private String logicStorehouse;

    @ApiModelProperty(value = "物流公司编码")
    private String logisticsCompanyNo;

    @ApiModelProperty(value = "物流公司名称")
    private String logisticsCompanyName;

    @ApiModelProperty(value = "收货地址")
    private String receiveAddress;

    @ApiModelProperty(value = "收货人手机")
    private String receivePhone;

    private List<PackageSku> packageSkuList;

    //-----------------------

    @ApiModelProperty(value = "出库通知单单号")
    private String sorOutNoticeId;

    @ApiModelProperty(value = "出库时间")
    private String creationDate;

}
