package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 9:45 上午
 */
@Data
@ApiModel("品牌返回参数")
public class BrandPageResVO implements Cacheable {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty("品牌编码")
    private String code;

    @ApiModelProperty("品牌logo")
    private String logo;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("制造商")
    private String manufacturer;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建人员")
    private String createdBy;

    @ApiModelProperty(value = "创建人名称")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "createdBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String createdName;

    @ApiModelProperty("操作人员")
    private String lastUpdatedBy;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdateDate;

    @ApiModelProperty("版本号")
    private Long objectVersionNumber;

    @ApiModelProperty("状态 0 开启 1禁用")
    private Integer status;
}
