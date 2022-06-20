package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author xurongfa 2022-04-26 14:21
 */
@Data
public class WarehouseResultPageQueryDTO extends PageRequest {
    @ApiModelProperty("单号 入库通知单号，入库结果单号，三方订单号，内部单号")
    private String queryCode;
    @ApiModelProperty("渠道类型编码")
    private String channelTypeCode;
    @ApiModelProperty("店铺id集合")
    @Encrypt
    private List<Long> storeIdList;
    @ApiModelProperty("入库状态 STOCK.INPUT_RESULT_STATUS")
    private String status;
    @ApiModelProperty("入库逻辑仓编码")
    private String receivingLogicWarehouseCode;

    @ApiModelProperty("创建时间-起始")
    private Date beginCreationDate;
    @ApiModelProperty("创建时间-终止")
    private Date endCreationDate;

}
