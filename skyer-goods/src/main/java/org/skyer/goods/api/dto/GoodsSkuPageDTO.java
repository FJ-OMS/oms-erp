package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * 商品规格表
 *
 * @author chenzz 2021-07-06 10:35:43
 */
@ApiModel("商品SKU")
@Data
public class GoodsSkuPageDTO extends PageRequest {

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "商品编码")
    private String spuCode;

    @ApiModelProperty(value = "商品类别")
    private String spuType;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;

    @ApiModelProperty("目录编码")
    @Encrypt
    private List<Long> categoryIdList;

    @ApiModelProperty(value = "0草稿 1被驳回 2审核中 3已生效 4已生效(修改中) ")
    private List<String> status;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "skuCode")
    private String skuCode;

}
