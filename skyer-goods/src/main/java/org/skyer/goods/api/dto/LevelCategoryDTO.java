package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/6/25 5:29 下午
 */
@Data
public class LevelCategoryDTO {

    /**
     * 分类等级
     */
    @ApiModelProperty("分类等级")
    private Integer level;

    /**
     * 分类id
     */
    @ApiModelProperty("分类id")
    @Encrypt
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String name;

}
