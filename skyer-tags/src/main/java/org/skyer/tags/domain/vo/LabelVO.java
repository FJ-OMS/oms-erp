package org.skyer.tags.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;

/**
 * @author xurongfa 2022-01-22 17:49
 */
@Data
@ApiModel("标签对象")
public class LabelVO implements Cacheable {

    @ApiModelProperty("id")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "标签类型")
    private String labelType;

    @ApiModelProperty(value = "模块")
    private String moduleCode;

    @ApiModelProperty(value = "标签code")
    private String labelCode;

    @ApiModelProperty(value = "状态")
    private Boolean status;

    @ApiModelProperty(value = "标签文本内容")
    private String textContent;

    @ApiModelProperty(value = "标签文本颜色")
    private String textColor;

    @ApiModelProperty(value = "是否系统标签")
    private Boolean systemLabelFlag;

    @ApiModelProperty(value = "是否可挪移")
    private Boolean changeFlag;

    @Encrypt
    @ApiModelProperty(value = "创建人id")
    private Long createdBy;

    @ApiModelProperty(value = "创建人")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "createdBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    private Date creationDate;


}
