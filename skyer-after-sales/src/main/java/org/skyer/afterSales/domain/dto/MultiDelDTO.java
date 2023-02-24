package org.skyer.afterSales.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description
 * @Author chenzz
 * @create 2021/8/19 10:04 上午
 */
@Data
@ApiModel("删除id")
public class MultiDelDTO {

    @ApiModelProperty("主键id")
    private List<Long> ids;
}