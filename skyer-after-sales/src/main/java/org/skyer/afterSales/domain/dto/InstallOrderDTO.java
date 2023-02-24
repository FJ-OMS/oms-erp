package org.skyer.afterSales.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 物流商
 *
 * @author chenzz 2021-08-16 15:04:38
 */
@ApiModel("物流商")
@Data
public class InstallOrderDTO {

    @ApiModelProperty(value = "安装工单单号", required = true)
    @NotBlank
    private String installOrderCode;

    @ApiModelProperty(value = "安装状态,1待安装,2取消安装,3安装完成", required = true)
    @NotNull
    private Integer status;

    @ApiModelProperty(value = "内部订单号", required = true)
    @NotBlank
    private String innerOrderNo;

    @ApiModelProperty(value = "外部第三方订单号", required = true)
    @NotBlank
    private String outerOrderNo;

    @ApiModelProperty(value = "出库通知单单号", required = true)
    @NotBlank
    private String outStockNoticeNo;

    @ApiModelProperty(value = "店铺名称", required = true)
    @NotBlank
    private String shopName;

    @ApiModelProperty(value = "渠道名称", required = true)
    @NotBlank
    private String channelName;

    @ApiModelProperty(value = "客户姓名", required = true)
    @NotBlank
    private String customerName;

    @ApiModelProperty(value = "客户联系方式", required = true)
    @NotBlank
    private String customerPhone;

    @ApiModelProperty(value = "客户联系地址", required = true)
    @NotBlank
    private String customerAddress;

    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;

    private Long objectVersionNumber;

}
