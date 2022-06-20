package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * 商品详情规格和价格信息
 * @description
 * @Author wusc
 * @create 2021/6/24 1:49 下午
 */
@Data
@ApiModel(description = "商品详情规格和价格信息")
public class DetailSpecifResVO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long specificationId;

    private String name;


    private List<SkuInfoResVO> skuInfo;
}
