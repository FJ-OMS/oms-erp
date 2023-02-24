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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lzh 2022-03-18 16:26:17
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_after_sale_logistics")
@Data
public class AfterSaleLogistics extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_LOGISTICS_COMPANY_CODE = "logisticsCompanyCode";
	public static final String FIELD_LOGISTICS_COMPANY = "logisticsCompany";
	public static final String FIELD_LOGISTICS_NO = "logisticsNo";
	public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_OUTER_REFUND_NO = "outerRefundNo";
	public static final String FIELD_INNER_REFUND_NO = "innerRefundNo";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("")
	@Id
	@GeneratedValue
	private Long id;
	@ApiModelProperty(value = "退货物流公司id")
	private Long logisticsCompanyId;
	@ApiModelProperty(value = "退货物流公司名称")
	private String logisticsCompanyName;
	@ApiModelProperty(value = "退货物流单号")
	private String logisticsNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "平台订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "第三方退款/退货单号")
	private String outerRefundNo;
	@ApiModelProperty(value = "内部退款/退货单号")
	private String innerRefundNo;


}
