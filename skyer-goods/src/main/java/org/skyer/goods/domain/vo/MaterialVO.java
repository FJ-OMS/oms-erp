package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("商品素材")
public class MaterialVO extends AuditDomain implements Cacheable {
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    /**
     * 编码
     */
    @ApiModelProperty("编码")
    private String code;

    /**
     * 素材名称
     */
    @ApiModelProperty("素材名称")
    private String materialName;

    /**
     * 主图
     */
    @ApiModelProperty("主图")
    private List<String> mainImage;

    /**
     * 详情图
     */
    @ApiModelProperty("详情图")
    private List<String> detailImage;

    /**
     * 详情内容
     */
    @ApiModelProperty("详情内容")
    private String detailHtml;

    private String remark;


    @ApiModelProperty("创建人用户名")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "createdBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String createdName;
}
