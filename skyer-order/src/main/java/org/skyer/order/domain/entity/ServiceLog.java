package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.starter.keyencrypt.core.Encrypt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单中心与其他中心交互日志
 *
 * @author linzhuanghuang@ebc.com 2022-02-28 13:24:39
 */
@ApiModel("订单中心与其他中心交互日志")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_service_log")
@Data
public class ServiceLog extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_REQUEST = "request";
	public static final String FIELD_RESPONSE = "response";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_RETRY_NUM = "retryNum";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("ID主键")
	@Id
	@GeneratedValue
	@Encrypt
	private Long id;
	@ApiModelProperty(value = "平台订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "类型：1-预占，2-寻源，3-发货，4-收货")
	private Integer type;
	@ApiModelProperty(value = "请求体")
	private String request;
	@ApiModelProperty(value = "响应体")
	private String response;
	@ApiModelProperty(value = "请求结果状态位：0-失败，1-成功")
	private Integer status;
	@ApiModelProperty(value = "重试次数，默认值为0")
	private Long retryNum;


}
