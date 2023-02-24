package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author xurongfa 2022-02-21 9:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "出库结果单列表查询")
public class ResultRecordPageDTO extends PageRequest {
    @ApiModelProperty(value = "出库结果单号/出库通知单号")
    private String queryCode;

    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;

    @ApiModelProperty(value = "店铺id集合")
    @Encrypt
    private List<Long> shopIdList;

    @ApiModelProperty(value = "逻辑仓编码")
    private String outLogicWarehouseCode;

    @ApiModelProperty(value = "起始创建时间")
    private Date beginCreationDate;

    @ApiModelProperty(value = "终止创建时间")
    private Date endCreationDate;

    @ApiModelProperty(value = "内部订单id")
    @Encrypt
    private Long orderId;
    @ApiModelProperty(value = "内部订单编码")
    private String orderCode;
}
