package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/8/16 5:22 下午
 */
@ApiModel("商品组合分页查找")
@Data
public class GroupPageResVO {

    @ApiModelProperty("商品组合id")
    @Encrypt
    private Long groupId;

    @ApiModelProperty("商品组合编码")
    private String groupCode;

    @ApiModelProperty("spu编码")
    @Encrypt
    private Long spuId;

    @ApiModelProperty("spu编码")
    private String spuCode;

    @ApiModelProperty("spu名称")
    private String spuName;

    @ApiModelProperty("分类")
    private String categoryName;

    @ApiModelProperty("套餐商品数")
    private BigDecimal goodsNum;

    @ApiModelProperty("适用店铺")
    private List<GroupStoreResVO> storeList;

    @ApiModelProperty("创建人员")
    private String createdBy;

    @ApiModelProperty("操作人员")
    private String lastUpdatedBy;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdateDate;

    @ApiModelProperty("创建时间")
    private Date createDate;
}
