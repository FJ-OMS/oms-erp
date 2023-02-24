package org.skyer.channel.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;
import java.util.List;

@Data
public class ChannelSaleOrderDTO {

    @ApiModelProperty("开始时间")
    private Date startDate;

    @ApiModelProperty("结束时间")
    private Date endDate;

    @ApiModelProperty("渠道")
    private String channel;

    @ApiModelProperty("店铺名")
    @Encrypt
    private List<Long> storeIds;

    @ApiModelProperty("网店类型")
    private String storeType;

    @ApiModelProperty("所属公司")
    private String organization;
}
