package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/8/10 11:42 上午
 */
@Data
public class AdjustRecordResVO {

    @ApiModelProperty("调整单编码")
    private String adjustCode;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("仓库名")
    private String warehouseName;

    @ApiModelProperty("调整单详情")
    private List<AdjustDetailResVO> adjustDetailResVOList;
}
