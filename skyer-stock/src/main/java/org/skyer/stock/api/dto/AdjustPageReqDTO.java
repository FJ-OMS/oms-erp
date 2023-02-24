package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.boot.platform.lov.annotation.LovValue;

/**
 * @description
 * @Author wusc
 * @create 2021/8/9 4:02 下午
 */
@Data
@ApiModel(description = "数据同步列表请求参数")
public class AdjustPageReqDTO extends PageReqDTO{

    @ApiModelProperty("调整单编码")
    private String adjustCode;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("状态 STOCK.RECORD_STATUS")
    @LovValue("STOCK.RECORD_STATUS")
    private List<String> status;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;
}
