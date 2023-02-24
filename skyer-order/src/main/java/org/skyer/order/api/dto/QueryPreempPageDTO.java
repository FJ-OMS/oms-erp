package org.skyer.order.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-02-14
 * @description:
 */
@Data
public class QueryPreempPageDTO extends PageReqDTO{

	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "渠道")
	private String channel;

	@ApiModelProperty(value = "店铺")
	@Encrypt
	private Long storeId;

	@ApiModelProperty(value = "预占单状态：0-待预占，1-全部预占成功，2-部分预占成功，3-全部预占失败")
	private Integer status;

	@ApiModelProperty(value = "第三方下单起始时间")
	private Date orderStartTime;

	@ApiModelProperty(value = "第三方下单结束时间")
	private Date orderEndTime;

	@ApiModelProperty(value = "内部单创建起始时间")
	private Date creationDateStartTime;

	@ApiModelProperty(value = "内部单创建结束时间")
	private Date creationDateEndTime;

	@JsonIgnore
	@ApiModelProperty(value = "订单号")
	private List<String> orderNoList;
}
