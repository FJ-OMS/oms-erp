package org.skyer.goods.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 9:50 上午
 */
@Data
@ApiModel("商品品牌查询条件")
public class BrandPageReqDTO extends PageReqDTO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;


    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("品牌状态")
    private Integer status;

    @ApiModelProperty("品牌编码")
    private String code;

    @ApiModelProperty("租户Id")
    @JsonIgnore
    private Long tenantId;

}
