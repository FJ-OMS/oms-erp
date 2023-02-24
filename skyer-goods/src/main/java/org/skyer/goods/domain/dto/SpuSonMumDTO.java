package org.skyer.goods.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 子母商品
 *
 * @author chenzz 2021-08-16 15:04:38
 */
@ApiModel("子母商品")
@Data
public class SpuSonMumDTO {

    @ApiModelProperty("主键id")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "spu编码(主)", required = true)
    @NotBlank
    private String spuCode;

    @ApiModelProperty(value = "是否适用于全部店铺")
    private Boolean isAllStore;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "商品sku明细")
    private List<AddSonMumSkuDTO> sonMumSkuDTOS;

    @ApiModelProperty(value = "售卖门店，店铺ID")
    @Encrypt
    private List<Long> spuStoreIds;

    private Long objectVersionNumber;
}
