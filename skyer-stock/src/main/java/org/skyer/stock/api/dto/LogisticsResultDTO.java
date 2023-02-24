package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author xurongfa 2022-04-24 9:30
 */
@Data
public class LogisticsResultDTO {
    @ApiModelProperty("发货时间-起始")
    private Date beginDate;
    @ApiModelProperty("发货时间-终止")
    private Date endDate;
    @ApiModelProperty("仓库id集合")
    @Encrypt
    private List<Long> warehouseIds;
    @ApiModelProperty("物流公司编码集合")
    @Encrypt
    private List<Long> logisticsCompanyIds;
    @ApiModelProperty("汇总方式 1.按仓库 2.按物流商")
    private Integer statsType;
}
