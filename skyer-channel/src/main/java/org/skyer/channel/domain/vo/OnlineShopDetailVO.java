package org.skyer.channel.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * <p>
 * 网店
 * </p>
 *
 * @author lixiaoyang
 * @since 2021-06-16
 */
@ApiModel("网店")
@Data
public class OnlineShopDetailVO extends AuditDomain {
    public static final String ONLINE_SHOP = "OnlineShop";

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("租户ID")
    private Long tenantId;

    @ApiModelProperty("网店名称")
    private String name;

    /**
     * 处理后的含义字段放在哪个字段中<br/>
     * 不设置则进行默认映射,将注解所在字段名末尾的Code(如果有)替换为Meaning,例:<br/>
     * <ul>
     * <li>statusCode -> statusMeaning</li>
     * <li>processStatus -> processStatusMeaning</li>
     * <li>codeTypeCode -> codeTypeMeaning</li>
     * </ul>
     */

    @ApiModelProperty("渠道类型编码")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE",meaningField = "channelTypeMeaning")
    private String channelTypeCode;

    //非数据库字段
    @ApiModelProperty("渠道类型")
    private String channelTypeMeaning;

    @ApiModelProperty("网店类型编码")
    @LovValue(lovCode = "SCHL.SHOP_TYPE", meaningField = "onlineStoreTypeMeaning")
    private String onlineStoreTypeCode;

    //非数据库字段
    @ApiModelProperty("网店类型")
    private String onlineStoreTypeMeaning;

    @ApiModelProperty("负责人")
    private String manager;

    @ApiModelProperty("网店地址(url)")
    private String url;

    @ApiModelProperty("销售组织")
    @Encrypt
    private Long salesOrganizingId;

    @ApiModelProperty("发票种类编码")
    @LovValue(lovCode = "SCHL.INVOICE_TYPE", meaningField = "invoiceTypeCodeMeaning")
    private String invoiceTypeCode;

    //非数据库字段
    @ApiModelProperty("发票种类")
    private String invoiceTypeCodeMeaning;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("渠道仓使用状态 true是")
    private Boolean channelWarehouseStatus;

    @ApiModelProperty("渠道仓名称")
    private String channelWarehouseName;

    @ApiModelProperty("删除标识")
    private Boolean deleteFlag;
    // 1 = true
    @ApiModelProperty("状态 true启用")
    private Boolean status;

    @ApiModelProperty("自动确认发货（天）")
    private Integer autoConfirmDay;

    @ApiModelProperty("自动下载商品 true是 false否")
    private Boolean autoDownloadGoods;

    @ApiModelProperty("自动同步库存 true是 false否")
    private Boolean autoSyncInventory;

    @ApiModelProperty("网店关联的门店id")
    @Encrypt
    private Long relationPhysicalShopId;

    @ApiModelProperty("网店关联的门店名称")
    private String relationPhysicalShopName;
}
