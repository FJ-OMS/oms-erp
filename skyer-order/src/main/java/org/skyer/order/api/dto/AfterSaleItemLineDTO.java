package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotNull;

/**
 * @author zhanglongbing
 */
@Data
public class AfterSaleItemLineDTO  {
    @ApiModelProperty(value = "商品ItemId")
    @NotNull
    @Encrypt
    private Long itemId;
}
