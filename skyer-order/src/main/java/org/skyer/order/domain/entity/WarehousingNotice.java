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

/**
 * 入库通知单
 *
 * @author linzhuanghuang@ebc.com 2022-04-27 15:33:02
 */
@ApiModel("入库通知单")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_warehousing_notice")
@Data
public class WarehousingNotice extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_CODE = "code";
	public static final String FIELD_INNER_REFUND_NO = "innerRefundNo";
	public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_PARAM = "param";
	public static final String FIELD_RESULT = "result";
	public static final String FIELD_DELETE_FLAG = "deleteFlag";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("主键id")
	@Id
	@GeneratedValue
	private Long id;
	@ApiModelProperty(value = "编码", required = true)
	@NotBlank
	private String code;
	@ApiModelProperty(value = "内部退货单号")
	private String innerRefundNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "预占单状态：0-待入库 1-已全部入库  ")
	private String status;
	@ApiModelProperty(value = "请求参数")
	private String param;
	@ApiModelProperty(value = "返回结果")
	private String result;
	@ApiModelProperty(value = "删除标识")
	private Integer deleteFlag;

	@ApiModelProperty(value = "平台订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "第三方退款/退货单号")
	private String outerRefundNo;
}
