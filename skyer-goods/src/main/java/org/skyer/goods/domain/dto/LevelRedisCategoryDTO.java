package org.skyer.goods.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/29 10:08 上午
 */
@Data
public class LevelRedisCategoryDTO {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @Encrypt
    private Long id;

    /**
     * 分类级别
     */
    @ApiModelProperty("分类级别")
    private Integer level;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String name;

    /**
     * 下级类目
     */
    @ApiModelProperty("下级分类")
    private List<LevelRedisCategoryDTO> sonLevelCategory;
}
