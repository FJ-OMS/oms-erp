package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 组合商品
 * @description
 * @Author wusc
 * @create 2021/8/16 9:40 上午
 */
@Data
public class SkuGroupResVO {

    @ApiModelProperty("id")
    @Encrypt
    private Long id;

    @ApiModelProperty("skugroup编码")
    private String skuGroupCode;

    @ApiModelProperty("spuId")
    @Encrypt
    private Long spuId;
    @ApiModelProperty("spu编码")
    private String spuCode;

    @ApiModelProperty("spu名称")
    private String spuName;

    @ApiModelProperty("分类")
    private String categoryName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("类别")
    private Boolean isAllStore;

    @ApiModelProperty("店铺类型")
    private List<GroupStoreResVO> groupStoreResVOList;

    @ApiModelProperty("sku列表")
    private List<SkuGroupSkuResVO> skuGroupSkuResVOList;
}
