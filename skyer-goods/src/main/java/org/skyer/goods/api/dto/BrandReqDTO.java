package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 9:59 上午
 */
@Data
@ApiModel("新增或者编辑商品品牌")
public class BrandReqDTO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty("品牌编码")
    private String code;

    @ApiModelProperty("品牌名称")
    private String  brandName;

    @ApiModelProperty("品牌logo")
    private String logo;

    @ApiModelProperty("制造商")
    private String manufacturer;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

}
