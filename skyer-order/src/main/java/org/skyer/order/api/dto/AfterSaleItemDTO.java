package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

import javax.validation.constraints.NotNull;

/**
 * @author zhanglongbing
 */
@Data
public class AfterSaleItemDTO extends PageRequest {

    @ApiModelProperty(value = "内部订单号")
    @NotNull
    private String innerOrderNo;
}
