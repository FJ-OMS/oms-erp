package org.skyer.stock.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopDTO {
    @ApiModelProperty("表ID，主键，供其他表做外键")
    private Long id;

    @ApiModelProperty("店铺编码")
    private String code;

    @ApiModelProperty(value = "店铺类别：区分网店还是实体店")
    private String categoryType;

    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;

    @ApiModelProperty("渠道仓使用状态 true是")
    private Boolean channelWarehouseStatus;

    @ApiModelProperty("渠道仓名称")
    private String channelWarehouseName;

    @ApiModelProperty("租户ID")
    private Long tenantId;
}
