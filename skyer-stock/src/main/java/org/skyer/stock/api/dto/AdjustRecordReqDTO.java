package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/8/9 11:58 上午
 */
@Data
@ApiModel(description = "数据同步请求参数")
public class AdjustRecordReqDTO {

    @ApiModelProperty("主键id")
    @Encrypt
    private Long id;

    @ApiModelProperty("仓库code")
    private String warehouseCode;

    @ApiModelProperty("仓库id")
    @Encrypt
    private Long warehouseId;

    @ApiModelProperty("调整单code")
    private String adjustCode;

    @ApiModelProperty("调整单商品详情")
    private List<AdjustDetailsReqDTO> adjustDetailsReqDTOList;

    @ApiModelProperty("备注")
    private String remark;
}
