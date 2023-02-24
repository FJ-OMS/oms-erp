package org.skyer.goods.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 子母商品
 *
 * @author chenzz 2021-08-16 15:04:38
 */
@ApiModel("服务地区DTO")
@Data
public class ServiceZoneDTO {

    @ApiModelProperty("国家id")
    private Long countryId;

    @ApiModelProperty("国家")
    private String countryName;

    @ApiModelProperty("省id")
    private Long provinceId;

    @ApiModelProperty("省")
    private String provinceName;

    @ApiModelProperty("城市id")
    private Long cityId;

    @ApiModelProperty("城市")
    private String cityName;


}
