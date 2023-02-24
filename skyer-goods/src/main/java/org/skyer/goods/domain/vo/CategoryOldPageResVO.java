package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.goods.api.dto.LevelCategoryDTO;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;
import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/23 10:21 上午
 */
@Data
@ApiModel(description = "商品分类返回值")
public class CategoryOldPageResVO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty("分类编码")
    private String categoryCode;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("当前等级")
    private Integer level;
    @ApiModelProperty("分类级别")
    List<LevelCategoryDTO> levelCategoryDTOs;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("操作人员")
    private Long lastUpdatedBy;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("创建人员")
    private Long createdBy;

    @ApiModelProperty("最后操作时间")
    private Date lastUpdateDate;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否有下级")
    private Boolean haveNext;
}
