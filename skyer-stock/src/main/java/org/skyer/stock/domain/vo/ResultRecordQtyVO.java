package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xurongfa 2022-02-23 17:27
 */
@Data
public class ResultRecordQtyVO {
    @ApiModelProperty("已发货数量")
    private Integer alreadyOutQty;
}
