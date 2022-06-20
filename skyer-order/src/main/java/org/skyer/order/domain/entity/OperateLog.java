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
 * 订单操作日志
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@ApiModel("订单操作日志")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_operate_log")
@Data
public class OperateLog extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
	public static final String FIELD_NODE = "node";
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_ORIG_VALUE = "origValue";
	public static final String FIELD_CUR_VALUE = "curValue";
	public static final String FIELD_DELETE_FLAG = "deleteFlag";

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
	@ApiModelProperty(value = "平台订单号", required = true)
	@NotBlank
	private String outerOrderNo;
	@ApiModelProperty(value = "内部订单号", required = true)
	@NotBlank
	private String innerOrderNo;
	@ApiModelProperty(value = "当前节点", required = true)
	private String node;
	@ApiModelProperty(value = "动作", required = true)
	private String action;
	@ApiModelProperty(value = "修改前的值")
	private String origValue;
	@ApiModelProperty(value = "修改后的值")
	private String curValue;
	@ApiModelProperty(value = "系统字段：删除标识", required = true)
	@NotNull
	private Integer deleteFlag;
	@ApiModelProperty(value = "修改内容")
	private String message;

}
