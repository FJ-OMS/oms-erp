package org.skyer.order.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;
import java.util.List;

/**
 * @author zhanglongb
 */
@Data
public class RefundOnlyPageDTO extends  PageReqDTO{

    @ApiModelProperty(value = "退款单号/订单号")
    private String orderNo;
    @ApiModelProperty(value = "退货单来源：普通转单、手工单、复制单、导入单")
    private String actionType;
    @ApiModelProperty(value = "退款单类型")
    private String orderRefundType;
    @ApiModelProperty(value = "买家昵称")
    private String buyerNick;
    @ApiModelProperty(value = "渠道")
    private String channelTypeCode;
    @ApiModelProperty(value = "店铺id")
    @Encrypt
    private List<Long> storeIdList;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "内部退款/退货单号")
    private String innerRefundNo;
    @ApiModelProperty(value = "三方退款/退回退款时间 开始")
    private Date outReturnTimeStart;
    @ApiModelProperty(value = "三方退款/退回退款时间 结束")
    private Date outReturnTimeEnd;
}
