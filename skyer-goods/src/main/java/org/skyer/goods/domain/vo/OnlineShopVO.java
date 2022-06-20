package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @description
 * @Author wusc
 * @create 2021/8/16 4:14 下午
 */
@Data
public class OnlineShopVO {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("租户ID")
    @Encrypt
    private Long tenantId;

    @ApiModelProperty("网店名称")
    @NotNull
    private String name;

    @ApiModelProperty("渠道类型编码")
    @NotNull
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE",meaningField = "channelTypeMeaning")
    private String channelTypeCode;

    @ApiModelProperty("渠道类型")
    private String channelTypeMeaning;

    @ApiModelProperty("网店类型编码")
    @NotNull
    @LovValue(lovCode = "SCHL.SHOP_TYPE", meaningField = "onlineStoreTypeMeaning")
    private String onlineStoreTypeCode;

    @ApiModelProperty("网店类型")
    private String onlineStoreTypeMeaning;

    @ApiModelProperty("负责人")
    @NotNull
    private String manager;

    @ApiModelProperty("网店地址(url)")
    @NotNull
    private String url;

    @ApiModelProperty("销售组织")
    @NotNull
    @Encrypt
    private Long salesOrganizingId;

    @ApiModelProperty("发票种类编码")
    @NotNull
    @LovValue(lovCode = "SCHL.INVOICE_TYPE", meaningField = "invoiceTypeCodeMeaning")
    private String invoiceTypeCode;

    @ApiModelProperty("发票种类")
    private String invoiceTypeCodeMeaning;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("渠道仓使用状态 true是")
    private Boolean channelStorehouseStatus;

    @ApiModelProperty("删除标识")
    private Boolean deleteFlag;

    @ApiModelProperty("状态 true禁用")
    private Boolean status;

    @ApiModelProperty("自动确认发货（天）")
    private Integer autoConfirmDay;

    @ApiModelProperty("自动下载商品 true是 false否")
    private Boolean autoDownloadGoods;

    @ApiModelProperty("自动同步库存 true是 false否")
    private Boolean autoSyncInventory;
}
